package Pirates

import Driver.GameState
import Entities.Player
import PirateState._

/*
 * This defines all of the things that a good pirate must be able to do.
 */
// It feels super stupid to have owningPlayer as a value here :(
abstract class Pirate(val majorRank:Int, val minorRank:Int, val owningPlayer:Int) extends Ordered[Pirate] {
  
  // Pirate is out of play by default
  var state = OUT_OF_PLAY;
  /*
   * Yah yah... it should be an enum.
   * 0 Not in play
   * 1 In hand
   * 2 In Den
   * 3 Discarded
   * 4 Out of play (discarded and then removed from play at end of voyage)
   */
  
  def dayActivity(state: GameState, owningPlayer: Player)
  def nightActivity(state: GameState, owningPlayer: Player)
  def endOfVoyageActivity(state: GameState, owningPlayer: Player)
  
  def getMajorRank():Int = {
    return majorRank;
  } 
  
  def getMinorRank():Int = {
    return minorRank;
  } 
  
  // 0 Isn't possible... unless we have a bug
  def compare(that: Pirate):Int = {
    if (that.majorRank > this.majorRank) {
     return 1;
    } else if (that.majorRank < this.majorRank) {
      return -1;
    } else if (that.minorRank > this.minorRank) {
      return 1;
    }
    return -1;
  }
}
