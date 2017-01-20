package Pirates

import Driver.GameState
import Entities.Player

/*
 * This defines all of the things that a good pirate must be able to do.
 */

abstract class Pirate(val majorRank:Int, val minorRank:Int) {
  
  def dayActivity(state: GameState, owningPlayer: Player)
  def nightActivity(state: GameState, owningPlayer: Player)
  def endOfVoyageActivity(state: GameState, owningPlayer: Player)
  
  def getMajorRank():Int = {
    return majorRank;
  } 
  
  def getMinorRank():Int = {
    return minorRank;
  } 
}