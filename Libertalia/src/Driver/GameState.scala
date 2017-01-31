package Driver

import Entities.Player;
import Treasure.Treasure;
import Pirates.Pirate;

class GameState {
  
  // Ordering is important for cards that interact with players adjacent to you.
  var players:List[Player] = List();
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
    
  }
  
  def endOfVoyage() {
    /*
     * Perform end of voyage activities.
     * Return after updating final scored.
     */
  }
  
  
  def getCardsInOrder:List[Pirate] = {
    var cards:List[Pirate] = List();
    this.players.foreach((p:Player) => cards:+p.playCard(this));
    return cards.sorted;
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