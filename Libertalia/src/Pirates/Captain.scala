package Pirates

import Driver.GameState
import Entities.Player
import Pirates.PirateState._;
import Treasure.TreasureType._;

/*
 * Rank 29 class
 */
class Captain(owningPlayer:Int) extends Pirate(29, owningPlayer) {
  
   val rankOrdering:List[Int] = List(1,2,3,4,5,6);

   /*
    * Gain 3
    */
   def dayActivity(state: GameState) {
     this.getMyOwner(state).currentLoot += 3;
   }
   
   def nightActivity(state: GameState) {
     return;
   }
   
  /*
   * Lose 3 for each cursed relic
   */
   def endOfVoyageActivity(state: GameState) {
     var badTreasureCount = 0;
     this.getMyOwner(state).treasure.foreach(t => {
       if (t.getType() == CURSED) {
         badTreasureCount += 1;
       }
     });
     this.getMyOwner(state).currentLoot -= (badTreasureCount * 3);
   }
  
}