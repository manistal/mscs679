package lab7

import scala.math.pow
import scala.collection.parallel.CollectionConverters._
// Above import required with Scala 2.13 or later
//   see documentation at: https://github.com/scala/scala-parallel-collections

object ParGLEstimator extends App {
  // Partitions and Range based on System and MaxInt
  //   Overhead increases if using more partitions than threads due to swapping
  val NUM_PARTITIONS = Runtime.getRuntime.availableProcessors
  val RANGE = Int.MaxValue / NUM_PARTITIONS

  // Prior to execution of the series
  val start_time = System.nanoTime()

  // Serial collection of 2-tuple ranges
  val ranges = for(k <- 0 to NUM_PARTITIONS) yield {
    val lower: Int = k * RANGE + 1
    val upper: Int = Int.MaxValue min (k + 1) * RANGE
    (lower, upper)
  }

  // Use the Parallel Map Collection to Calculate the Partial sum for each range
  val partials = ranges.par.map { partial =>
    val (lower, upper) = partial
    val  bounds = lower to upper
    val sum = bounds.foldLeft(0.0) { (pi, index) => pi + (pow(-1, index) / (2.0 * index + 1)) }
    sum * 4
  }

  // Reduce the result from the partial sums of the ranges to the value of Pi
  val pi = partials.foldLeft(4.0){ (sum, partial) => sum + partial }

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
