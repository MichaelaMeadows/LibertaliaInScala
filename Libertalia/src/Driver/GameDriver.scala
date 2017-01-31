package Driver
import Entities.RandomPlayer
import scala.util.Random
  object HelloWorld {
  def main(args: Array[String]): Unit = {
    val playerCount: Int = 6;// Hard coding this for now args(0).toInt;
    val roundCount:Int = 3;
    val turnCount:Int = 6;
    var playDeck:Array[Int] = Array[Int](30);
    System.out.println(playerCount);
    

    for (pirateNum <- 1 to 30) {
      playDeck(pirateNum) = pirateNum;
      // Use this for pirate selection
      var randomizedDeck = Random.shuffle(playDeck.toSeq);
    }
    
    
    var gameState:GameState = new GameState;
    for(playerNum <- 1 to playerCount) {
      var player = new RandomPlayer(playerNum, true);
      player.innitDeck();
      gameState.players = gameState.players:+ player;
    }
    
    
    
    
    // Three rounds of six turns I believe
    for (round <- 0 to roundCount) {
      for (turn <- 0 to turnCount) {
        gameState.nextTurn();
      }
      gameState.endOfVoyage();
    }
    // Order players by final score and we're done!
  }
}
  