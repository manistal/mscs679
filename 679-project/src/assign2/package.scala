

package object assign2 {

  case class Partition(start: Long, end: Long, candidate: Long) extends Serializable

  case class Result(sum: Long, t0: Long, t1: Long) extends Serializable


}
