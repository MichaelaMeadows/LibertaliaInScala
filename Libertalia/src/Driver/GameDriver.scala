package Driver
import Entities.Player
import Entities.RandomPlayer
import Entities.DNNPlayer
import Pirates.PirateState._
import scala.util.Random
import java.io._;

  object HelloWorld {
  val map = scala.collection.mutable.HashMap.empty[Int,Int];
  var gameState:GameState = new GameState;
  val TRAIN_COUNT = 300;
  val TEST_COUNT = 100;
  val TRAIN_NAME = "TrainData-";
  val TRAIN_FINAL = "TrainingData";
  val TEST_NAME = "TestData-"
  val TEST_FINAL = "TestingData";
  
  var globalDecisionCount = 0;
  def main(args: Array[String]): Unit = {
    var gameState:GameState = new GameState;
    //gameState.openStateRecording("BigOutput.csv");

    globalDecisionCount = 0;
    for (i <- 1 to TRAIN_COUNT) {
      runGame(i, TRAIN_NAME);
    }
    map.foreach(p => {
      System.out.println(p._1 + " won: " + (p._2/ TRAIN_COUNT.toFloat));
    });
    concatFiles(TRAIN_FINAL, TRAIN_NAME, TRAIN_COUNT, globalDecisionCount)
    
    map.clear();
    globalDecisionCount = 0;
    for (i <- 1 to TEST_COUNT) {
      runGame(i, TEST_NAME);
    }
    map.foreach(p => {
      System.out.println(p._1 + " won: " + (p._2/ TEST_COUNT.toFloat));
    });
    concatFiles(TEST_FINAL, TEST_NAME, TEST_COUNT, globalDecisionCount)

  }
  
  def runGame(iteration:Int, outNameBase:String) {
    val startTime = System.currentTimeMillis();
    val playerCount: Int = 6;
    val roundCount:Int = 3;
    val turnCount:Int = 6;
    var playDeck:List[Int] = (1 to 30).toList
    var sliceStop = 9;
    playDeck = Random.shuffle(playDeck);  
    gameState.resetState();
    gameState.openStateRecording(outNameBase+iteration);
    
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
      //System.out.println("Winner was: " + winner);
      gameState.closeFile(winner);
      globalDecisionCount += gameState.totalDecisions;
    //System.out.println("Elampsed time in ms: " + (System.currentTimeMillis() - startTime))
      
  }
  
  def innitPlayers(gameState:GameState, playDeck:List[Int]) {
    var cards = playDeck.slice(0, 9);
    addToPlayState(gameState, new RandomPlayer(1, true), cards);
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
  
  def concatFiles(outFile:String, baseName:String, numFiles:Int, globalDecisionCount:Int) {
    var out:OutputStream = new FileOutputStream(outFile);
    var buf:Array[Byte] = Array.ofDim(5000);
    out.write(((globalDecisionCount + 1)+","+ gameState.FEATURE_COUNT + "\n").getBytes());
    for (x <- 1 to numFiles) {
        var in:InputStream = new FileInputStream(baseName+x);
        var b:Int = 0;
        b = in.read(buf);
        while (b >= 0) {
            out.write(buf, 0, b);
            out.flush();
            b = in.read(buf)
        }
        in.close();
        var f:File = new File(baseName+x);
        f.delete();
    }
    out.close();
  }

}
     /* for(playerNum <- 2 to playerCount) {
      player = new RandomPlayer(playerNum, true);
      player.innitDeck();
      // Each player stars with the same 9 cards
      player.addCardsToHand(playDeck.slice(0, sliceStop));
      gameState.addPlayer(player);
    }*/
    