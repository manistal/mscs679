package assign3

import org.apache.log4j.Logger
import parascale.actor.last.{Dispatcher, Task}
import parascale.util._
import parabond.cluster.{Partition, checkReset, check}
import parascale.future.perfect.candidates
import parascale.parabond.casa.MongoHelper
import parascale.parabond.util.Result


object ParaDispatcher extends App {
  // For initial testing on a single host, use this socket.
  // When deploying on multiple hosts, use the VM argument,
  // -Dsocket=<ip address>:9000 which points to the second
  // host.
  val socket2 = getPropertyOrElse("socket","localhost:9000")

  // This spawns a list of relay workers at the sockets
  new ParaDispatcher(List("localhost:8000", socket2))
}

class ParaDispatcher(sockets: List[String]) extends Dispatcher(sockets) {

  def analyzeAndReport(rung: Int): String = {
    val start_time = System.nanoTime()

    // Invoke the checkreset function to reset the check portfolio prices
    val portfIds = checkReset(rung, 0)

    // Create two partitions A: (rung/2, 0) and B: (rung/2, rung/2)
    // and send partitions to workers 0 and 1 respectively
    workers(0) ! Partition(rung/2, 0)
    workers(1) ! Partition(rung/2, rung/2)

    // Wait for results
    // Accumulate the responses from the workers
    //   Note: Should receive only one response per worker
    //   and response should be a Result - If not, something went wrong
    val results = for (k <- sockets.indices) yield {
      val partial_result = receive.payload match {
        case result: Result => result
        case _ => throw new IllegalStateException()
      }
      partial_result
    }

    // Reduce the partial sums to a total for the determination of perfectness
    val result = results.reduce { (a, b) =>
      Result(
        a.t0 + b.t0,
        a.t1 + b.t1
      )
    }

    val end_time = System.nanoTime()

    // Generate the report row for this rung
    val missed_portfIds = check(portfIds).length
    val T1 = (result.t1 - result.t0) / 1000000000.0
    val TN = (end_time - start_time) / 1000000000.0
    val R = T1 / TN
    val e = R / sockets.length
    f"$rung%-5s $missed_portfIds%6s $T1%7.2f $TN%8.2f $R%6.2f $e%6.2f"
  }

  def act: Unit = {
    // Constants implemented as part of construction, used in reporting
    val workers_info = sockets.foldLeft("") { (result_str, entry) => result_str + entry + "(worker), " }
    val mongo_info = MongoHelper.getHost + " (mongo)"
    val ladder = List(1000, 2000, 4000, 8000, 16000, 32000)
    /*val ladder = List(1000, 2000, 4000, 8000, 16000, 32000, 64000, 100000 )*/

    // Main Dispatcher Loop
    //  For each rung described in the ladder, analyze and report the results
    val result_table = for (index <- ladder.indices) yield {
      analyzeAndReport(ladder(index))
    }

    // Generate a report for the Results
    println("")
    println("ParaBond Analysis")
    println("By Miguel Nistal")
    println("20 Apr 2020")
    println("BasicNode")
    println("Workers: " + sockets.length)
    println("Hosts: localhost (dispatcher), " + workers_info + mongo_info)
    println("Cores: " + Runtime.getRuntime.availableProcessors)
    println("   N  missed      T1       TN      R      e")
    result_table.foreach { row =>
      println(row)
    }

  }
}