package Pirates

import Driver.GameState
import Entities.Player
import Pirates.PirateState._;

/*
 * Rank 19 class
 */
class Bosun(owningPlayer:Int) extends Pirate(19, owningPlayer) {
  
   val rankOrdering:List[Int] = List(4,5,6,1,2,3);

   /*
    * Gain two for every character in the den lower than Bosun
    */
   def dayActivity(state: GameState) {
     var owner = this.getMyOwner(state);
     owner.getCardsInState(DEN).foreach(rank => {
       if (rank < 19) {
         owner.currentLoot += 2;
       }
     })
   }
   
   def nightActivity(state: GameState) {
     return;
   }

   def endOfVoyageActivity(state: GameState) {
     return;
   }
  
}