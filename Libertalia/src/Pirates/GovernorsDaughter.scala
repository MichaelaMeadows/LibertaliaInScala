package Pirates

import Driver.GameState
import Entities.Player
import Pirates.PirateState._;

/*
 * Rank 25 class
 */
class GovernorsDaughter(owningPlayer:Int) extends Pirate(25, owningPlayer) {
  
   val rankOrdering:List[Int] = List(3,4,5,6,1,2);

   def dayActivity(state: GameState) {
     return;
   }
   
   def nightActivity(state: GameState) {
     return;
   }
   
   /*
    * If no one else has a Governor's Daughter, +6 doubloons
    * Otherwise, -3 doubloons
    */
   def endOfVoyageActivity(state: GameState) {
     for (num <- 1 to 6) {
       if (num != owningPlayer && state.getPlayerByNumber(num).getPirateFromDeck(25).state == DEN) {
         getMyOwner(state).currentLoot -= 3;
         return;
       }
     }
     getMyOwner(state).currentLoot += 6;
   }
  
}