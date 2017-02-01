package Driver

import Entities.Player;
import scala.collection.mutable.ListBuffer;
import Treasure.Treasure;
import Pirates.Pirate;

class GameState {
  
  // Ordering is important for cards that interact with players adjacent to you.
  private var players:List[Player] = List();
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
    cardsInPlay.foreach(f => f.dayActivity(this))
  }
  
  def endOfVoyage() {
    /*
     * Final activities of pirates in DEN
     * Update total score
     * Reset starting score
     */
    players.foreach(player => {
      player.endOfVoyage(this);
      player.totalScore += player.currentLoot;
      player.currentLoot = 10;
    })
  }

  
  
  def getCardsInOrder():List[Pirate] = {
    val cards = new ListBuffer[Pirate];
    this.players.foreach((p:Player) => cards+=p.playCard(this));
    return cards.toList.sorted;
  }
  
  def addPlayer(player:Player) = {
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