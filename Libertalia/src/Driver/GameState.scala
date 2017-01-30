package Driver

import Entities.Player;
import Treasure.Treasure;
import Pirates.Pirate;

class GameState {
  
  // Ordering is important for cards that interact with players adjacent to you.
  var players:List[Player] = List();
  var treasure:Array[List[Treasure]] = Array.ofDim[List[Treasure]](6);
  // Once populated, this is assumed to be ordered... should use a structure to enforce that, haha.
  var cardsInPlay:List[Pirate] = List();

  
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

}