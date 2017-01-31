package Pirates

import Driver.GameState
import Entities.Player
import Pirates.PirateState._;

/*
 * Rank 4 class
 */
class Barkeep(owningPlayer:Int) extends Pirate(4, owningPlayer) {
  
  val rankOrdering:List[Int] = List(4,5,6,1,2,3);

   def dayActivity(state: GameState) {
     return
   }
   
   /*
    * Earn a doubloon every night
    */
   def nightActivity(state: GameState) {
     state.getPlayerByNumber(owningPlayer).currentLoot += 1
   }
   
   def endOfVoyageActivity(state: GameState) {
     return;
   }
  
}