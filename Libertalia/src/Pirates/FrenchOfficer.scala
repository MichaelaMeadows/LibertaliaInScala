package Pirates

import Driver.GameState
import Entities.Player
import Pirates.PirateState._;

/*
 * Rank 10 class
 */
class FrenchOfficer(owningPlayer:Int) extends Pirate(10, owningPlayer) {
  
   val rankOrdering:List[Int] = List(3,4,5,6,1,2);

   /*
    * If you have less than 9, gain 5
    */
   def dayActivity(state: GameState) {
     if (this.getMyOwner(state).currentLoot < 9) {
       this.getMyOwner(state).currentLoot += 5;
     }
   }
   
   def nightActivity(state: GameState) {
     return;
   }

   def endOfVoyageActivity(state: GameState) {
     return;
   }
  
}