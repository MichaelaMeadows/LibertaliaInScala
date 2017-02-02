package Pirates

import Driver.GameState
import Entities.Player
import Pirates.PirateState._;
import Treasure.TreasureType._;

/*
 * Rank 12 class
 */
class FreedSlave(owningPlayer:Int) extends Pirate(12, owningPlayer) {
  
   val rankOrdering:List[Int] = List(5,6,1,2,3,4);

   def dayActivity(state: GameState) {
     return;
   }
   
   /*
    * Gain 1 for each pirate rank >= 13
    */
   def nightActivity(state: GameState) {
     var owner = this.getMyOwner(state);
     owner.currentLoot += owner.getCardsInState(DEN).filter(p => p > 12).size;
   }
   
  /*
   * Lose 8
   */
   def endOfVoyageActivity(state: GameState) {
     return;
   }
  
}