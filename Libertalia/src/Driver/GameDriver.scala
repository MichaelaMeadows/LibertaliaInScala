package Driver
import Entities.RandomPlayer
import Pirates.PirateState._
import scala.util.Random
  object HelloWorld {
  def main(args: Array[String]): Unit = {
    val playerCount: Int = 6;// Hard coding this for now args(0).toInt;
    val roundCount:Int = 3;
    val turnCount:Int = 6;
    var playDeck:List[Int] = (1 to 30).toList
    var sliceStop = 9;
    playDeck = Random.shuffle(playDeck);  
    
    var gameState:GameState = new GameState;
    for(playerNum <- 1 to playerCount) {
      var player = new RandomPlayer(playerNum, true);
      player.innitDeck();
      // Each player stars with the same 9 cards
      player.addCardsToHand(playDeck.slice(0, sliceStop));
      gameState.addPlayer(player);
    }
    gameState.innitVoyageTreasure();

    // Three rounds of six turns I believe
   // for (round <- 0 to 1) {
   //   System.out.println("Round: " + round);
      for (turn <- 0 to turnCount - 1) {
        System.out.println("Turn: " + turn);
        gameState.nextTurn();
      }
      gameState.endOfVoyage();
      
      for(playerNum <- 1 to playerCount) {
        var score = gameState.getPlayerByNumber(playerNum).totalScore;
        System.out.println(s"Player: $playerNum got final score: $score");
        var piratesInDen = gameState.getPlayerByNumber(playerNum).getCardsInState(DEN).size;
        System.out.println(s"Player: $playerNum had $piratesInDen surviving pirates");
      }

      // Once we ready to keep adding more cards, basically do this.
     // var temp = sliceStop + 6;
     // player.addCardsToHand(playDeck.slice(sliceStop, temp));
     // sliceStop = temp;
   // }
    // Order players by final score and we're done!
  }
}
  