
package object assign2 {

  /**
   * Case class produced by the Dispatcher to provide workers
   *   partitions of the numerical range of a candidate number
   * @param start Beginning of the range
   * @param end   End of the range
   * @param candidate Number to calculate factors for
   */
  case class Partition(start: Long, end: Long, candidate: Long) extends Serializable

  /**
   * Case class produced by the Worker to provide results back to the Dispatcher
   * @param sum  Sum of factors in the range that was provided by the Partition
   * @param t0   Beginning of the serial time such that t1 - t0 is the serial time
   * @param t1   End of the serial time
   */
  case class Result(sum: Long, t0: Long, t1: Long) extends Serializable

}
