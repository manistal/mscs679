package lab3

class ChildRunnable extends Runnable {
  private var _no = 0

  def this(no : Int) {
    this()
    this._no = no
  }

  override def run(): Unit = {
    val threadId = Thread.currentThread.getId
    println("child: " + _no + " " + threadId)
  }
}
