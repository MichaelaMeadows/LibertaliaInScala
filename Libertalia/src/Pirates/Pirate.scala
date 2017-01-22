package Pirates

import Driver.GameState
import Entities.Player

/*
 * This defines all of the things that a good pirate must be able to do.
 */
// It feels super stupid to have owningPlayer as a value here :(
abstract class Pirate(val majorRank:Int, val minorRank:Int, val owningPlayer:Int) extends Ordered[Pirate] {
  
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
