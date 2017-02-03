package Driver

import Entities.Player;
import scala.collection.mutable.ListBuffer;
import Treasure.Treasure;
import Pirates.Pirate;
import Pirates.CabinBoy;
import Pirates.Cook;
import Pirates.Parrot;
import Pirates.PirateState._;
import Treasure.TreasureType._;
import scala.collection.mutable.ListBuffer;
import scala.util.Random;

class GameState {
  
  // Ordering is important for cards that interact with players adjacent to you.
  private var players:List[Player] = List();
  var activePlayers:Int = 0;
  // 6 turns of up to 6 players
  var treasure:Array[Array[Treasure]] = Array.ofDim[Treasure](6, 6);
  var fullTreasureList:List[Treasure] = List();
  var nextTreasurePiece = 0;
  // Once populated, this is assumed to be ordered... should use a structure to enforce that, haha.
  // TODO Players shouldn't be able to read this... I'll just leave it be for now though.
  var cardsInPlay:List[Pirate] = List();
  var turnNumber = 0;
  
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
      System.out.println("Doing the parrot thing");
      var player = cardsInPlay(0).getMyOwner(this);
      cardsInPlay(0).state = DISCARD;
      cardsInPlay = cardsInPlay.filter(p => p.state == IN_PLAY);
      System.out.println("After parrot filter size:" + cardsInPlay.size);
      cardsInPlay = cardsInPlay:+player.playCard(this); 
      System.out.println("After new cards played size:" + cardsInPlay.size);
    }
  }
  
  def nightStage() = {
    // After play, pirates go to the DEN... unless they died for some reason.
    cardsInPlay.foreach(f => f.state = DEN);
    // This looks a little crazy
    players.foreach(player => { 
      player.getCardsInState(DEN).foreach(pirateRank => {
         player.getPirateFromDeck(pirateRank).nightActivity(this);
        })
    });
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
  def selectAndUpdateTreasure(p:Pirate) = {
    var relevantPlayer = p.getMyOwner(this);
      var treasureChoice = relevantPlayer.chooseTreasure(this, treasure(turnNumber), "");
      // Do a check to make sure it's valid?
      if (treasureChoice != -1) {
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
            var playerChoice = relevantPlayer.makeDecision(this, adjacentPlayersWithKillablePirates, "Select a player to attack");
            var pirateToAttack = relevantPlayer.makeDecision(this, players(playerChoice - 1 ).getCardsInState(DEN), "Select a pirate to kill");
            players(playerChoice - 1).getPirateFromDeck(pirateToAttack).state = DISCARD;
            var decider = relevantPlayer.myNumber;
            //System.out.println(s"Player: $decider attacked $playerChoice and killed $pirateToAttack");
          }
        }
          relevantPlayer.treasure = relevantPlayer.treasure:+selectedTreasure;
          treasure(turnNumber)(treasureChoice) = null;
      }
  }
  
  def endOfVoyage() {
    /*
     * Final activities of pirates in DEN
     * Update total score
     * Reset starting score
     */
    players.foreach(player => {
      player.endOfVoyage(this);
      var items = player.treasure.size;
      
      System.out.println(s"Player got $items items");
      player.updateCurretScoreWithTreasure();
      player.totalScore += player.currentLoot;
      player.currentLoot = 10;
      player.treasure = List();
      player.resetDenAndDiscard();
    })
    turnNumber = 0;
  }

  def innitVoyageTreasure() = {
    treasure = Array.ofDim[Treasure](6, 6);
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
  
  // TODO - Eventually this records the state before a decision, and what decision the "player" made. 
  // This will be used to generate training data
  def recordGameStateWithDecision() { } // Not sure what to have it take yet

}