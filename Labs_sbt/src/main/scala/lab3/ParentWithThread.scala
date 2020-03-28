package lab3

object ParentWithThread extends App {
  val numCores = Runtime.getRuntime.availableProcessors
  val child = new Thread(new ChildThread(0))
  child.start()
  val numThreads = Thread.activeCount
  val threadId = Thread.currentThread.getId

  println("Cores: " + numCores)
  println("Active Threads: " + numThreads)
  println("Thread ID: " + threadId)

  child.join()
}
