package Driver
import Entities.RandomPlayer
  object HelloWorld {
  def main(args: Array[String]): Unit = {
    val playerCount: Int = args(0).toInt;
    System.out.println(playerCount);
    
    var gameState:GameState = new GameState;
    
    for(player <- 1 to playerCount) {
        gameState.players = gameState.players:+ new RandomPlayer(player)
    }

  }
}
  