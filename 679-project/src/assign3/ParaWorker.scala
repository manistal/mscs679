package assign3

import parascale.actor.last.Worker
import parascale.util._
import parabond.cluster.Partition
import parascale.parabond.util.Result


/**
 * Entrypoint for ParaWorker.main()
 */
object ParaWorker extends App {
  // a. If worker running on a single host, spawn two workers
  //   else spawn one worker.
  val nhosts = getPropertyOrElse("nhosts", 1)

  // Set the node, default to basic node
  val prop =    getPropertyOrElse("node","parabond.cluster.BasicNode")
  val clazz = Class.forName(prop)
  import parabond.cluster.Node
  val node = clazz.newInstance.asInstanceOf[Node]

  // One-port configuration
  val port1 = getPropertyOrElse("port", 8000)

  // If there is 1 host, then ports include 9000 by default
  // Otherwise, if there are two hosts in this configuration,
  // use just one port which must be specified by VM options
  val ports =    if (nhosts == 1) List(port1, 9000) else List(port1)

  // Spawn the worker(s).
  // Note: for initial testing with a single host, "ports"
  // contains two ports. When deploying on two hosts, "ports"
  // will contain one port per host.
  for (port <- ports) {
    // Start up new worker.
    println(port)
    new ParaWorker(port)
  }
}


/**
 * Worker class for the ParaBond Portfolio Analysis
 * @param port Port for the worker to listen on
 */
class ParaWorker(port: Int) extends Worker(port) {
  import ParaWorker._

  /**
   * Handles actor startup after construction.
   */
  override def act: Unit = {
    // Action loop, waiting for messages from Dispatcher
    //   TODO: Needs a break condition or handler, infinite loops are bad
    while (true) {
      receive.payload match {
        // Payload is a Partition case class sent from the dispatcher
        //   Analyze the partition using the Node object and then
        //   accumulate time measurements to return to the dispatcher
        //   NOTE: analyze updates the mongoDB as a _side effect_
        case partition: Partition =>
          val analysis = node analyze(partition)
          val dt = analysis.results.foldLeft(0L) { (sum, job) => sum + (job.result.t1 - job.result.t0) }
          sender ! Result(dt)

        // Always throw for an illegal state, especially in event loop
        case _ => throw new IllegalStateException()
      }
    }
  }
}