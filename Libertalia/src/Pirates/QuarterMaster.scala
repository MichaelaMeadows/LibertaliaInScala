package Pirates

import Driver.GameState
import Entities.Player
import Pirates.PirateState._;
import Treasure.TreasureType._;

/*
 * Rank 26 class
 */
class QuarterMaster(owningPlayer:Int) extends Pirate(26, owningPlayer) {
  
   val rankOrdering:List[Int] = List(4,5,6,1,2,3);

   /*
    * Gain 1 for each booty you have
    */
   def dayActivity(state: GameState) {
     this.getMyOwner(state).currentLoot += this.getMyOwner(state).treasure.size;
   }
   
   def nightActivity(state: GameState) {
     return;
   }
   
  /*
   * Lose 8
   */
   def endOfVoyageActivity(state: GameState) {
     this.getMyOwner(state).currentLoot -= 8;
   }
  
}