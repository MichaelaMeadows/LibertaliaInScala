package Pirates

import Driver.GameState
import Entities.Player
import Pirates.PirateState._;
import Treasure.TreasureType._;

/*
 * Rank 20 class
 */
class Armorer(owningPlayer:Int) extends Pirate(20, owningPlayer) {
  
   val rankOrdering:List[Int] = List(5,6,1,2,3,4);

   def dayActivity(state: GameState) {
   }
   
   /*
    * Gain 1 doubloon for each saber
    */
   def nightActivity(state: GameState) {
     System.out.println("ARMORER WAS ACTIVATED");
     var sabers = 0;
     this.getMyOwner(state).treasure.foreach(t => {
       if (t.getType() == SWORD) {
         sabers += 1;
       }
     });
     this.getMyOwner(state).currentLoot += sabers;
   }

   def endOfVoyageActivity(state: GameState) {
   }
  
}