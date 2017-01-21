package Pirates

import Driver.GameState
import Entities.Player

/*
 * Rank 3 class
 */
class Begger(minorRank:Int) extends Pirate(3, minorRank) {
  
  /*
   * Steal money from the most powerful pirate played
   */
   def dayActivity(state: GameState, owningPlayer: Player) {
     return;
   }
   def nightActivity(state: GameState, owningPlayer: Player) {
     return;
   }
   def endOfVoyageActivity(state: GameState, owningPlayer: Player) {
     return;
   }
  
}