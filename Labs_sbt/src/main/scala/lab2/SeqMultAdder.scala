package lab2

object SeqMultAdder extends App {
  // 1) Declare a collection to work with
  val nums = List(1, 3, 4, 5, 12, 2, 7, 9, 7)

  // 2) Output collection by applying the println function with foreach
  println("\n Vanilla foreach, defined lambda \n")
  val f = { n: Int => println(n) }
  nums.foreach(f)

  // 3) Alternative function definition
  println("\n Vanilla foreach, Function object defined  \n")
  def f_alt(n: Int): Unit = println(n)
  nums.foreach(f_alt)

  // 4) Again, but this time with Anonymous function defs!
  println("\n Vanilla foreach, with anonymous functions \n")
  nums.foreach { n => println(n) }

  // 5) Finally, last way we can do this
  // Dark magic of underscore explained:
  //     https://stackoverflow.com/questions/8000903/what-are-all-the-uses-of-an-underscore-in-scala
  println("\n Vanilla foreach, with underscore magic \n")
  nums.foreach(println(_))

  // Part III
  println("\n Part III \n")

  // 2) Filter the even numbers and store in 'odds'
  println("\n Finding all the odds in a collection \n")
  val odds = nums.filter { n => ((n % 2) != 0) }
  odds.foreach(println(_))

  // Part IV
  println("\n Part IV: Apply foldleft method \n")
  val initial_total = 0 // This is the first argument to fold, a value to initialize the operation to
  val total = odds.foldLeft(initial_total) { (running_total, entry) => running_total + 2*entry }
  println("Sum of doubles of odds: " + total)

}
