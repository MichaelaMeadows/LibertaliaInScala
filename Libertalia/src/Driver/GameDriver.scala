package Driver
import Entities.RandomPlayer
import Pirates.PirateState._
import scala.util.Random
  object HelloWorld {
  val map = scala.collection.mutable.HashMap.empty[Int,Int];
  
  def main(args: Array[String]): Unit = {
    for (i <- 1 to 1) {
      runGame();
    }
    map.foreach(p => {
      System.out.println(p._1 + " won: " + (p._2/ 20000f));
   });
    
  }
  
  def runGame() {
    val startTime = System.currentTimeMillis();
    val playerCount: Int = 6;// Hard coding this for now args(0).toInt;
    val roundCount:Int = 3;
    val turnCount:Int = 6;
    var playDeck:List[Int] = (1 to 30).toList
    var sliceStop = 9;
    playDeck = Random.shuffle(playDeck);  
    
    var gameState:GameState = new GameState;
    gameState.openStateRecording("RandomTest-" + startTime + "-" + Random.nextInt(50));
    for(playerNum <- 1 to playerCount) {
      var player = new RandomPlayer(playerNum, true);
      player.innitDeck();
      // Each player stars with the same 9 cards
      player.addCardsToHand(playDeck.slice(0, sliceStop));
      gameState.addPlayer(player);
    }
    gameState.innitVoyageTreasure();
    //System.out.println(gameState.getPlayerByNumber(1).getStateString());
    // Three rounds of six turns I believe
    for (round <- 0 to 2) {
     // gameState.innitVoyageTreasure();
   //   System.out.println("Round: " + round);
      for (turn <- 0 to turnCount - 1) {
        //System.out.println("Turn: " + turn);
        gameState.nextTurn();
      }
      //System.out.println(gameState.getPlayerByNumber(1).getStateString());
      gameState.endOfVoyage();
      gameState.innitVoyageTreasure();
      gameState.players.foreach(p => p.addCardsToHand(playDeck.slice(sliceStop, sliceStop + 6)));
      sliceStop += 6;
     // gameState.innitVoyageTreasure();
     // gameState.players.ad
    }
     // for(playerNum <- 1 to playerCount) {
        //var score = gameState.getPlayerByNumber(playerNum).totalScore;
        //System.out.println(s"Player: $playerNum got final score: $score");
        //System.out.println(gameState.getPlayerByNumber(playerNum).getStateString());
        //var piratesInDen = gameState.getPlayerByNumber(playerNum).getCardsInState(DEN).size;
        //System.out.println(s"Player: $playerNum had $piratesInDen surviving pirates");
     // }
      gameState.closeFile();
      
     // System.out.println("Winnder:"+gameState.players.sortWith((x,y) => x.totalScore > y.totalScore)(0).myNumber);
      val winner = gameState.players.sortWith((x,y) => x.totalScore > y.totalScore)(0).myNumber;
      map.put(winner, map.getOrElse(winner, 0) + 1);
      
      // Once we ready to keep adding more cards, basically do this.
     // var temp = sliceStop + 6;
     // player.addCardsToHand(playDeck.slice(sliceStop, temp));
     // sliceStop = temp;
//    }
    // Order players by final score and we're done!
    
    System.out.println("Elampsed time in ms: " + (System.currentTimeMillis() - startTime))
  }
  
}
  