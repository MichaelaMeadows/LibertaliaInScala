package Pirates

import Driver.GameState
import Entities.Player
import Pirates.PirateState._;
import Treasure.TreasureType._;

/*
 * Rank 23 class
 */
class Treasurer(owningPlayer:Int) extends Pirate(23, owningPlayer) {
  
   val rankOrdering:List[Int] = List(2,3,4,5,6,1);

   def dayActivity(state: GameState) {
   }
   
   def nightActivity(state: GameState) {
     return;
   }

   /*
    * Gain 1 for each "good" treasure type
    */
   def endOfVoyageActivity(state: GameState) {
     var goodTreasureCount = 0;
     this.getMyOwner(state).treasure.foreach(t => {
       if (t.getType() == BARREL
        || t.getType() == CHEST
        || t.getType() == JEWELS) {
         goodTreasureCount += 1;
       }
     });
     this.getMyOwner(state).currentLoot += goodTreasureCount;
   }
  
}