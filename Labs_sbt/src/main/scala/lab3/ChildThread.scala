package lab3

class ChildThread extends Thread {

  private var _no = 0

  def this(no : Int) {
    this()
    this._no = no
  }

  override def run(): Unit = {
    println("child: " + _no + " " + this.getId)
  }

}
