package Driver
import Entities.Player
import Entities.RandomPlayer
import Entities.DNNPlayer
import Pirates.PirateState._
import scala.util.Random
  object HelloWorld {
  val map = scala.collection.mutable.HashMap.empty[Int,Int];
  var gameState:GameState = new GameState;
  val GAME_COUNT = 2;
  def main(args: Array[String]): Unit = {
    var gameState:GameState = new GameState;
    //gameState.openStateRecording("BigOutput.csv");

    for (i <- 1 to GAME_COUNT) {
      runGame(i);
    }
    map.foreach(p => {
      System.out.println(p._1 + " won: " + (p._2/ GAME_COUNT.toFloat));
    });
    
  }
  
  def runGame(iteration:Int) {
    val startTime = System.currentTimeMillis();
    val playerCount: Int = 6;
    val roundCount:Int = 3;
    val turnCount:Int = 6;
    var playDeck:List[Int] = (1 to 30).toList
    var sliceStop = 9;
    playDeck = Random.shuffle(playDeck);  
    gameState.resetState();
    gameState.openStateRecording("RandomTest-"+iteration);
    
    innitPlayers(gameState, playDeck);
    
    // Always assumes 6 players
    gameState.innitVoyageTreasure();
    //System.out.println(gameState.getPlayerByNumber(1).getStateString());
    // Three rounds of six turns I believe
    for (round <- 0 to 2) {
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
      
     // System.out.println("Winnder:"+gameState.players.sortWith((x,y) => x.totalScore > y.totalScore)(0).myNumber);
      val winner = gameState.players.sortWith((x,y) => x.totalScore > y.totalScore)(0).myNumber;
      //gameState.players.foreach(p => println(p.totalScore));
      map.put(winner, map.getOrElse(winner, 0) + 1);
      System.out.println("Winner was: " + winner);
      gameState.closeFile(winner);
    
    //System.out.println("Elampsed time in ms: " + (System.currentTimeMillis() - startTime))
  }
  
  def innitPlayers(gameState:GameState, playDeck:List[Int]) {
    var cards = playDeck.slice(0, 9);
    addToPlayState(gameState, new DNNPlayer(1, true), cards);
    addToPlayState(gameState, new RandomPlayer(2, true), cards);
    addToPlayState(gameState, new RandomPlayer(3, true), cards);
    addToPlayState(gameState, new RandomPlayer(4, true), cards);
    addToPlayState(gameState, new RandomPlayer(5, true), cards);
    addToPlayState(gameState, new RandomPlayer(6, true), cards);
  }
  
  def addToPlayState(gameState:GameState, player:Player, cards:List[Int]) {
    player.innitDeck();
    player.addCardsToHand(cards);
    gameState.addPlayer(player);
  }
  
}
     /* for(playerNum <- 2 to playerCount) {
      player = new RandomPlayer(playerNum, true);
      player.innitDeck();
      // Each player stars with the same 9 cards
      player.addCardsToHand(playDeck.slice(0, sliceStop));
      gameState.addPlayer(player);
    }*/
    