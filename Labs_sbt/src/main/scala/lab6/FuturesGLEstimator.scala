package lab6

import scala.concurrent.{Await, Future}
import scala.math.pow
import scala.language.postfixOps


object FuturesGLEstimator extends App {
  // Scala Compiler requires an implicit execution context defined, recommends the global one as default
  implicit val ec: scala.concurrent.ExecutionContext = scala.concurrent.ExecutionContext.global

  // Partitions and Range based on System and MaxInt
  //   Overhead increases if using more partitions than threads due to swapping
  val NUM_PARTITIONS = Runtime.getRuntime.availableProcessors
  val RANGE = Int.MaxValue / NUM_PARTITIONS

  // Prior to execution of the series
  val start_time = System.nanoTime()

  val futures = for (k <- 0L until NUM_PARTITIONS) yield Future {
    // Define the range based on the current partition being operated on
    val LOWER = (k * RANGE) + 1
    val UPPER = (k + 1) * RANGE
    val bounds = LOWER to UPPER

    // Calculate the partial sum of the subrange based on the Gregory-Leibniz series
    val sum = bounds.foldLeft(0.0) { (pi, index) => pi + (pow(-1, index) / (2.0 * index + 1)) }
    sum * 4
  }

  // Reduce the futures to resolve the value of Pi
  // Start at 4.0 since the LOWER part of the range has an offset of 1 (see above)
  val pi = futures.foldLeft(4.0) { (sum, future) =>
    import scala.concurrent.duration._
    val pi = Await.result(future, 200 seconds)
    sum + pi
  }

  // After the value of Pi has been obtained
  val end_time = System.nanoTime()

  // Deliverables
  val T1 =  195.77 // From Lab 5
  val TN = (end_time - start_time) / 1_000_000_000.0
  val N = Runtime.getRuntime.availableProcessors
  val R = T1 / TN
  val e = R / N
  val overhead = 1 - e

  println("Pi = " + pi)
  println("T1 = " + T1)
  println("TN = " + String.format("%.2f s", TN))
  println("N = "  + N)
  println("R = "  + String.format("%.2f", R))
  println("e = "  + String.format("%.2f", e))

}
