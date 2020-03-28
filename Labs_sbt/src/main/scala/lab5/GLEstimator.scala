package lab5

import scala.math.pow


object GLEstimator extends App {
  // Range from 0 -> Upper Bound for Iterations of the Series
  val range = 0 to Int.MaxValue

  val start_time = System.nanoTime()

  // Leibniz Formula https://en.wikipedia.org/wiki/Leibniz_formula_for_%CF%80
  //    Note: Multiply the result of the Fold Operation by 4 to account for Pi/4 in the series result
  val pi = range.foldLeft(0.0) { (pi, index) => pi + (pow(-1, index) / (2.0*index + 1)) } * 4

  val end_time = System.nanoTime()

  // Deliverable: Display Time Elapsed and Result
  //     Note: Converting from Nanoseconds to Seconds => Divide by 1 billion
  val elapsed_time = (end_time - start_time) / 1_000_000_000.0
  println("Pi = " +  pi)
  println("dt = " + String.format("%.2f s", elapsed_time))

  // Updated results with Int.MaxValue
  // Pi = 3.1415926531226095
  // dt = 195.77 s
}
