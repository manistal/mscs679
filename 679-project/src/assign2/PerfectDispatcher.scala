package assign2

import org.apache.log4j.Logger
import parascale.actor.last.{Dispatcher, Task}
import parascale.util._
import parascale.future.perfect.candidates


/**
  * Spawns a dispatcher to connect to multiple workers.
  */
object PerfectDispatcher extends App {
  val LOG = Logger.getLogger(getClass)
  LOG.info("started")

  // For initial testing on a single host, use this socket.
  // When deploying on multiple hosts, use the VM argument,
  // -Dsocket=<ip address>:9000 which points to the second
  // host.
  val socket2 = getPropertyOrElse("socket","localhost:9000")

  // Construction forks a thread which automatically runs the actor act method.
  new PerfectDispatcher(List("localhost:8000", socket2))
}

/**
  * Template dispatcher which tests readiness of
  * @param sockets
  */
class PerfectDispatcher(sockets: List[String]) extends Dispatcher(sockets) {
  import PerfectDispatcher._

  /**
   * Function to take a candidate and determine if its a perfect number
   *   Farms out subpartitions of the candidate to remote workers
   * @param candidate Number to determine if its a perfect number
   * @return row of the table for the reporting
   */
  def calcPerfectNumbers(candidate: Long): String = {
    val start_time = System.nanoTime()

    // Partition the candidate based on the number of workers we have
    //   and send the partition off to its respective worker
    val RANGE = candidate / sockets.length
    sockets.indices.foreach { k =>
      val start: Long = (k * RANGE) + 1
      val end: Long = candidate min (k + 1) * RANGE
      val partition = Partition(start, end, candidate)
      workers(k) ! partition
    }

    // Accumulate the responses from the workers
    //   Note: Should receive only one response per worker
    //   and response should be a Result - If not, something went wrong
    val results: Seq[Result] = for (k <- sockets.indices) yield {
      val partial_result = receive.payload match {
        case result: Result => result
        case _ => throw new IllegalStateException()
      }
      partial_result
    }

    // Reduce the partial sums to a total for the determination of perfectness
    val result = results.reduce { (a, b) =>
      Result(
        a.sum +  b.sum,
        a.t0  +  b.t0,
        a.t1  +  b.t1
      )
    }

    val end_time = System.nanoTime()

    // Generate the report row for this candidate
    val is_perfect = if ((2*candidate) == result.sum) "YES" else "no"
    val T1 = (result.t1 - result.t0) / 1000000000.0
    val TN = (end_time - start_time) / 1000000000.0
    val R = T1 / TN
    val e = R / sockets.length
    f"$candidate%-13s $is_perfect%10s $T1%10.2fs $TN%10.2fs $R%10.2f $e%10.2f"
  }

  /**
    * Handles actor startup after construction.
    */
  def act: Unit = {
    LOG.info("sockets to workers = " + sockets)
    // Apply the PNF algorithm to the set of candidates
    //   and collect the results into a sequence
    val report_table = for (index <- candidates.indices) yield {
      calcPerfectNumbers(candidates(index))
    }

    // Displays the report table generated above with header
    println("Candidate           Perfect       T1         TN           R          e")
    report_table.foreach { row =>
      println(row)
    }
  }

}

