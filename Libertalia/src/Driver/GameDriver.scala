package Driver

  object HelloWorld {
  def main(args: Array[String]): Unit = {
    val playerCount: Int = args(0).toInt;
    System.out.println(playerCount);
  }
}
  