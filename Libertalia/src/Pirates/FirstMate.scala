package Pirates

import Driver.GameState
import Entities.Player
import Pirates.PirateState._;

/*
 * Rank 28 class
 */
class FirstMate(owningPlayer:Int) extends Pirate(28, owningPlayer) {
  
   val rankOrdering:List[Int] = List(6,1,2,3,4,5);
  

   def dayActivity(state: GameState) {
     return;
   }
   
   def nightActivity(state: GameState) {
     return;
   }
   
   /*
    * Gain 1 for every pirate in your den
    */
   def endOfVoyageActivity(state: GameState) {
     getMyOwner(state).currentLoot += getMyOwner(state).getCardsInState(DEN).size;
   }
  
}