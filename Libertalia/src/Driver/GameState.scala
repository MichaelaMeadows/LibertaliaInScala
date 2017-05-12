package Driver

import Entities.Player;
import scala.collection.mutable.ListBuffer;
import Treasure.Treasure;
import Pirates.Pirate;
import Pirates.CabinBoy;
import Pirates.Cook;
import Pirates.Parrot;
import Pirates.PirateState._;
import Entities.DecisionType._;
import Treasure.TreasureType._;
import scala.collection.mutable.ListBuffer;
import scala.util.Random;
import java.io._;

class GameState {
  // Feature count does not include the annotation
  var FEATURE_COUNT = 293;
  // Once populated, this is assumed to be ordered... should use a structure to enforce that, haha.
  // TODO Players shouldn't be able to read this... I'll just leave it be for now though.
  var cardsInPlay:List[Pirate] = List();
  var treasure:Array[Array[Treasure]] = Array.ofDim[Treasure](6, 6);
  var decisions:Array[String] = Array.ofDim(300);
  var fullTreasureList:List[Treasure] = List();
  var players:List[Player] = List();
  var activePlayers:Int = 0;
  var turnNumber = 0;
  var voyageNumber = 0;
  var totalDecisions = 0;
  var nextTreasurePiece = 0;
  // I do not want to reset the tfAdapter since that's expensive.
  var tfAdapter = new TFAdapter();
  

  var file:File = null;
  var bw:BufferedWriter = null;// = new File(canonicalFilename)
  //val bw = new BufferedWriter(new FileWriter(file))
  //bw.write(text)
 // bw.close()
   /*
   * Solicit cards from players
   * Order cards and execute daytime
   * Choose treasure
   * Execute night time
   * Return
   */
  
  def nextTurn() {
    cardsInPlay = getCardsInOrder();
    doParrotCheck();
    cardsInPlay.foreach(f => f.dayActivity(this));
    // We do a filter after every stage in case a pirate has died.
    cardsInPlay = cardsInPlay.filter(p => p.state == IN_PLAY);
    duskStage();
    // We do a filter after every stage in case a pirate has died.
    cardsInPlay = cardsInPlay.filter(p => p.state == IN_PLAY);
    nightStage();
    // Increment the turn counter
    turnNumber += 1;
  }
  
  def doParrotCheck() = {
    while (cardsInPlay(0).isInstanceOf[Parrot]) {
      //System.out.println("Doing the parrot thing");
      var player = cardsInPlay(0).getMyOwner(this);
      cardsInPlay(0).state = DISCARD;
      cardsInPlay = cardsInPlay.filter(p => p.state == IN_PLAY);
      //System.out.println("After parrot filter size:" + cardsInPlay.size);
      cardsInPlay = cardsInPlay:+player.playCard(this); 
      //System.out.println("After new cards played size:" + cardsInPlay.size);
    }
  }
  
  def nightStage() = {
    // After play, pirates go to the DEN... unless they died for some reason.
    cardsInPlay.foreach(f => f.state = DEN);
    // Perform Granny Wata Kill Check
    doGrannyWataCheck();
    
    // This looks a little crazy
    players.foreach(player => { 
      player.getCardsInState(DEN).reverse.foreach(pirateRank => {
         player.getPirateFromDeck(pirateRank).nightActivity(this);
        })
    });
  }
  
  def doGrannyWataCheck() = {
    var grannyCount = 0;
    players.foreach(player => {
      if (player.getPirateFromDeck(27).state == DEN) {
        grannyCount+=1;
      }
    });
    
    if (grannyCount > 1) {
      //System.out.println("Destroying the Grannys: " + grannyCount);
      players.foreach(player => {
      if (player.getPirateFromDeck(27).state == DEN) {
        player.getPirateFromDeck(27).state = DISCARD;
      }
    });
    }
  }
  
  def duskStage() = {
        cardsInPlay.reverse.foreach(p => {
          if (p.isInstanceOf[Cook]) {
            selectAndUpdateTreasure(p);
            selectAndUpdateTreasure(p);
          } else if (p.isInstanceOf[CabinBoy]) {
            // Do nothin
          } else {
            // All other pirates select as normal.
            selectAndUpdateTreasure(p);
          }
        })
  }
  
  // This can be executed 0-2 times depending on the pirate being played
  def selectAndUpdateTreasure(p:Pirate):Unit = {
    var relevantPlayer = p.getMyOwner(this);
    
    if (treasure(turnNumber).filter(t => t != null).size == 0) {
      //System.out.println("No valid treasures");
      return None;
    }
    
      // TODO Maybe don't even ask if there's no valid treasure?
      var treasureChoice = relevantPlayer.chooseTreasure(this, treasure(turnNumber), "");
      
      // Do a check to make sure it's valid?
    //  if (treasureChoice != -1) {
        var selectedTreasure = treasure(turnNumber)(treasureChoice);
        if (selectedTreasure.getType() == OFFICER) {
          // Kill the poor pirate
          p.state = DISCARD;
        }
        if (selectedTreasure.getType() == SWORD) {
          // Kill a pirate in adjacent den.
         // System.out.println("Checking adjaceny for: " + p.owningPlayer);
         // getAdjacentPlayers(p.owningPlayer - 1).foreach(f => System.out.println("AdjacentIs: " + f.myNumber));
          var adjacentPlayersWithKillablePirates:List[Int] = getAdjacentPlayers(p.owningPlayer - 1).filter(player => player.getCardsInState(DEN).size > 0)
          .map(possible => possible.myNumber);
          if (adjacentPlayersWithKillablePirates.size > 0) {
            var playerChoice = relevantPlayer.makeDecision(this, adjacentPlayersWithKillablePirates, "Select a player to attack", PLAYER_TO_ATTACK.id);
            var pirateToAttack = relevantPlayer.makeDecision(this, players(playerChoice - 1 ).getCardsInState(DEN), "Select a pirate to kill", PIRATE_TO_ATTACK.id);
            players(playerChoice - 1).getPirateFromDeck(pirateToAttack).state = DISCARD;
            var decider = relevantPlayer.myNumber;
            //System.out.println(s"Player: $decider attacked $playerChoice and killed $pirateToAttack");
          }
        }
          relevantPlayer.treasure = relevantPlayer.treasure:+selectedTreasure;
          treasure(turnNumber)(treasureChoice) = null;
     // }
  }
  
  def endOfVoyage() {
    /*
     * We must do the Topman check before the dens are cleared.
     */
    // 16 is the Topman rank... this does execute some wated loops.... :(
    players.foreach(player => {
      if (player.getPirateFromDeck(16).state == DEN) {
        player.getPirateFromDeck(16).endOfVoyageActivity(this);
      }
    });
    
    /*
     * Final activities of pirates in DEN
     * Update total score
     * Reset starting score
     */
    players.foreach(player => {
      player.endOfVoyage(this);
      var items = player.treasure.size;
      
      //System.out.println(s"Player got $items items");
      player.updateCurretScoreWithTreasure();
      player.totalScore += player.currentLoot;
      player.currentLoot = 10;
      player.treasure = List();
      player.resetDenAndDiscard();
    })
    turnNumber = 0;
    voyageNumber += 1;
  }
  
  def resetState() {
    cardsInPlay = List();
    treasure = Array.ofDim[Treasure](6, 6);
    decisions = Array.ofDim(300);
    fullTreasureList= List();
    players = List();
    activePlayers= 0;
    turnNumber = 0;
    voyageNumber = 0;
    totalDecisions = 0;
    nextTreasurePiece = 0;
  }
  

  def innitVoyageTreasure() = {
    treasure = Array.ofDim[Treasure](6, 6);
    nextTreasurePiece = 0;
    var treasureList = new ListBuffer[Treasure]();
    for (i <- 0 to 3) {
      treasureList += new Treasure(CHEST);
    }
    for (i <- 0 to 5) {
      treasureList += new Treasure(JEWELS);
    }
    for (i <- 0 to 9) {
      treasureList += new Treasure(BARREL);
    }
    for (i <- 0 to 5) {
      treasureList += new Treasure(OFFICER);
    }
    for (i <- 0 to 5) {
      treasureList += new Treasure(SWORD);
    }
    for (i <- 0 to 7) {
      treasureList += new Treasure(MAP);
    }
    for (i <- 0 to 9) {
      treasureList += new Treasure(CURSED);
    }
    fullTreasureList = Random.shuffle(treasureList.toList);
    //val treasureIterator = Iterator(treasureSelection);
    for (turn <- 0 to 5) {
      for (playerNum <- 0 to (activePlayers - 1)) {
        treasure(turn)(playerNum) = fullTreasureList(nextTreasurePiece);
        nextTreasurePiece += 1;
      }
    }

  }
  
  def getCardsInOrder():List[Pirate] = {
    val cards = new ListBuffer[Pirate];
    this.players.foreach((p:Player) => cards+=p.playCard(this));
    cards.foreach(c => c.state = IN_PLAY);
    return cards.toList.sorted;
  }
  
  def addPlayer(player:Player) = {
    activePlayers += 1;
    players = players:+ player;
  }
  
  def getPlayerByNumber(playerNumber:Int):Player = {
    return players(playerNumber - 1);
  }
  
  def getAdjacentPlayers(playerPosition:Int):List[Player] = {
    var leftPos:Int = playerPosition-1;
    if (leftPos < 0) {
      leftPos = players.size - 1;
    }
    val leftPlayer:Player = players(leftPos);
    val rightPlayer:Player = players(((playerPosition+1) % players.size));
    return List(leftPlayer, rightPlayer);
  }
  
  // Just used for the monkey
  def getLeftPlayer(playerPosition:Int):Player = {
    var leftPos:Int = playerPosition-1;
    if (leftPos < 0) {
      leftPos = players.size - 1;
    }
    return players(leftPos);
  }
  
  // TODO - Eventually this records the state before a decision, and what decision the "player" made. 
  // This will be used to generate training data
  def recordGameStateWithDecision():String = { 
    val buf = new StringBuilder;
    //buf ++= "Player, voyage, turn";
    buf ++= voyageNumber + "," + turnNumber;
    
    //buf ++= "36 treasures";
    treasure.foreach(day => {
      
      day.foreach(t => {
        if (t != null) {
           buf ++= ","+t.getType().id;
        } else {
          buf ++= ","+(0);
        }
      })
    });
    //buf ++= "180 pirates";
    players.foreach(p => buf++= p.getStateString());
    //buf ++= "type and choice";
    buf.toString();
  }
  
  def openStateRecording(fileName:String) {
    file = new File(fileName)
    bw = new BufferedWriter(new FileWriter(file))
  }
  
  def recordDecision(text:String) {
    decisions(totalDecisions) = text;
    totalDecisions += 1;
   // bw.write(text + "\n")
  }
  def closeFile(winner:Int) {
   // bw.write(totalDecisions + "\n")
   //////
    for (x <- 0 to (totalDecisions -1 )) {
      if (decisions(x).take(1).equals(""+winner)) {
        decisions(x) = decisions(x) + ",1";
      } else {
        decisions(x) = decisions(x) + ",0";
      }
      
    }
    
   //////
    
    for (i <- 0 to (totalDecisions -1 )) {
      bw.write(decisions(i)+"\n")
    }

    //decisions.foreach(d => {
      //if (d == null) {
       // return;
      //}
      //bw.write(d+"\n")
      //});
    bw.close()
/*    totalDecisions = 0;
    turnNumber = 0;
    voyageNumber = 0;
    totalDecisions = 0;
    nextTreasurePiece = 0;
    players = List();*/
  }

}