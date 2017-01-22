package Pirates

import Driver.GameState
import Entities.Player

class Parrot(minorRank:Int, owningPlayer:Int) extends Pirate(1, minorRank, owningPlayer) {
    /*
   * Swap with another pirate in hand
   */
   def dayActivity(state: GameState, owningPlayer: Player) {
     
   }
   def nightActivity(state: GameState, owningPlayer: Player) {
     return;
   }
   def endOfVoyageActivity(state: GameState, owningPlayer: Player) {
     return;
   }
}