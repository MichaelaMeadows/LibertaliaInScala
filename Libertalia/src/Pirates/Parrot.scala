package Pirates

import Driver.GameState
import Entities.Player

class Parrot(owningPlayer:Int) extends Pirate(1, owningPlayer) {
  
   val rankOrdering:List[Int] = List(1,2,3,4,5,6);

   /*
   * Swap with another pirate in hand
   * Logic lives in the game state class and is executed before daytime technically starts.
   */
   def dayActivity(state: GameState) {
   }
   def nightActivity(state: GameState) {
     return;
   }
   def endOfVoyageActivity(state: GameState) {
     return;
   }
}