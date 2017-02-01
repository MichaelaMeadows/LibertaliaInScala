package Pirates

import Driver.GameState
import Entities.Player
import PirateState._

/*
 * This defines all of the things that a good pirate must be able to do.
 */
// It feels super stupid to have owningPlayer as a value here :(
abstract class Pirate(val majorRank:Int, val owningPlayer:Int) extends Ordered[Pirate] {
  
  val rankOrdering:List[Int];
  
  // Pirates must implement their own mapping from player -> minorRank
  def getMinorRank():Int = {
    return rankOrdering(owningPlayer - 1);
  }
  
  def getMyOwner(gameState:GameState):Player = {
    return gameState.getPlayerByNumber(owningPlayer);
  }
  
  // Pirate is out of play by default
  var state = OUT_OF_PLAY;
  /*
   * 0 Not in play
   * 1 In hand
   * 2 In Den
   * 3 Discarded
   * 4 Out of play (discarded and then removed from play at end of voyage)
   */
  
  def dayActivity(state: GameState)
  def nightActivity(state: GameState)
  def endOfVoyageActivity(state: GameState)
  
  // 0 Isn't possible... unless we have a bug
  def compare(that: Pirate):Int = {
    if (that.majorRank > this.majorRank) {
     return 1;
    } else if (that.majorRank < this.majorRank) {
      return -1;
    } else if (that.getMinorRank() > this.getMinorRank()) {
      return 1;
    }
    return -1;
  }
}
