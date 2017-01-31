package Pirates

import Driver.GameState
import Entities.Player

class Parrot(owningPlayer:Int) extends Pirate(1, owningPlayer) {
  
   val rankOrdering:List[Int] = List(1,2,3,4,5,6);

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