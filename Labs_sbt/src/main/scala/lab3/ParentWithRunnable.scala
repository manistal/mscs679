package lab3

object ParentWithRunnable extends App {
  val numCores = Runtime.getRuntime.availableProcessors

  val children = for(no <- 0 until numCores) yield {
    val child = new Thread(new ChildRunnable(no))
    child.start
    child
  }
  val numThreads = Thread.activeCount
  val threadId = Thread.currentThread.getId

  println("Cores: " + numCores)
  println("Active Threads: " + numThreads)
  println("Thread ID: " + threadId)

  for(child <- children) yield {
    child.join()
    println("Child Done! " + child.getId())
  }
}
