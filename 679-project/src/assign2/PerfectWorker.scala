package assign2

import org.apache.log4j.Logger
import parascale.actor.last.{Task, Worker}
import parascale.util._
import parascale.future.perfect._sumOfFactorsInRange



/**
  * Spawns workers on the localhost.
  */
object PerfectWorker extends App {
  val LOG = Logger.getLogger(getClass)

  LOG.info("started")

  // Number of threads that this worker can exploit
  val nthreads = Runtime.getRuntime.availableProcessors

  // Number of hosts in this configuration
  val nhosts = getPropertyOrElse("nhosts",1)

  // One-port configuration
  val port1 = getPropertyOrElse("port", 8000)

  // If there is just one host, then the ports will include 9000 by default
  // Otherwise, if there are two hosts in this configuration, use just one
  // port which must be specified by VM options
  val ports = if(nhosts == 1) List(port1, 9000) else List(port1)

  // Spawn the worker(s).
  // Note: for initial testing with a single host, "ports" contains two ports.
  // When deploying on two hosts, "ports" will contain one port per host.
  for(port <- ports) {
    // Construction forks a thread which automatically runs the actor act method.
    new PerfectWorker(port)
  }
}

/**
  * Template worker for finding a perfect number.
  * @param port Localhost port this worker listens to
  */
class PerfectWorker(port: Int) extends Worker(port) {
  import PerfectWorker._

  /**
   * Further subdivides the partition and returns a partial sum and timing statistics
   * @param partition  Partition from the dispatcher to operate on
   * @return Result of the subpartitions
   */
  def calculatePartialPerfect(partition: Partition): Result = {
    // Create a parallel structure based on the number of threads we have
    //   NOTE: The ternary operation for nparts ensures that
    //   each thread has a non-zero range to operate on
    val partition_range = (partition.end - partition.start)
    val nparts = if (partition_range < nthreads*2) partition_range / 2 else nthreads
    val partitions = (0L until nparts).par
    val RANGE = (partition_range.toDouble / nparts).ceil.toInt

    // Re-partition the original range for this worker into a set of subranges
    val ranges = for (k <- partitions) yield {
      val lower: Long = k * RANGE + partition.start
      val upper: Long = partition.end min ((k + 1) * RANGE + partition.start)
      (lower, upper)
    }

    // Unpack the ranges and calculate the set of partial sums
    val results = ranges.par.map { lowerUpper =>
      val t0 = System.nanoTime()
      val (lower, upper) = lowerUpper
      val sum = _sumOfFactorsInRange(lower, upper, partition.candidate)
      val t1 = System.nanoTime()
      Result(sum, t0, t1)
    }

    // Reduce the set of partial sums to the result for this partition
    results.reduce { (a, b) =>
      Result(
        a.sum +  b.sum,
        a.t0  +  b.t0,
        a.t1  +  b.t1
      )
    }
  }

  /**
    * Handles actor startup after construction.
    */
  override def act: Unit = {
    val name = getClass.getSimpleName
    LOG.info("started " + name + " (id=" + id + ")")

    // Action loop, waiting for messages from Dispatcher
    //   TODO: Needs a break condition or handler, infinite loops are bad
    while (true) {
      receive.payload match {
        case partition: Partition =>
          sender ! calculatePartialPerfect(partition)

        case msg: String =>
          LOG.info("got task = " + msg + " sending reply")
          sender ! name + " READY (id=" + id + ")"
      }
    }
  }
}
