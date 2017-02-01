package Driver

import Entities.Player;
import scala.collection.mutable.ListBuffer;
import Treasure.Treasure;
import Pirates.Pirate;
import Pirates.PirateState._;
import Treasure.TreasureType._;
import scala.collection.mutable.ListBuffer;
import scala.util.Random;

class GameState {
  
  // Ordering is important for cards that interact with players adjacent to you.
  private var players:List[Player] = List();
  var activePlayers:Int = 0;
  var treasure:Array[List[Treasure]] = Array.ofDim[List[Treasure]](6);
  // Once populated, this is assumed to be ordered... should use a structure to enforce that, haha.
  // TODO Players shouldn't be able to read this... I'll just leave it be for now though.
  var cardsInPlay:List[Pirate] = List();

  def nextTurn() {
    /*
     * Solicit cards from players
     * Order cards and execute daytime
     * Execute night time
     * Return
     */
    cardsInPlay = getCardsInOrder();
    cardsInPlay.foreach(f => f.dayActivity(this));
    // After play, pirates go to the DEN... unless they died for some reason.
    cardsInPlay.foreach(f => f.state = DEN);
    // This looks a little crazy
    players.foreach(player => { 
      player.getCardsInState(DEN).foreach(pirateRank => {
        player.getPirateFromDeck(pirateRank).nightActivity(this);
        })
      });

  }
  
  def endOfVoyage() {
    /*
     * Final activities of pirates in DEN
     * Update total score
     * Reset starting score
     */
    players.foreach(player => {
      player.endOfVoyage(this);
      player.updateCurretScoreWithTreasure();
      player.totalScore += player.currentLoot;
      player.currentLoot = 10;
      player.treasure = List();
    })
  }

  def innitVoyageTreasure() = {
    treasure = Array.ofDim[List[Treasure]](6);
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
    var treasureSelection = Random.shuffle(treasureList.toList);
    val treasureIterator = Iterator(treasureSelection);
    for (turn <- 0 to 5) {
      for (playerNum <- 1 to activePlayers)
      treasure(turn).+:(treasureIterator.next());
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