package Pirates

import Driver.GameState
import Entities.Player
import Pirates.PirateState._;
import Treasure.TreasureType._;

/*
 * Rank 11 class
 * 
 */
class VoodooWitch(owningPlayer:Int) extends Pirate(11, owningPlayer) {
  
   val rankOrdering:List[Int] = List(4,5,6,1,2,3);

   /*
    * Gain 2 for each discarded character (only things discarded this voyage)
    */
   def dayActivity(state: GameState) {
     var owner = this.getMyOwner(state);
     owner.currentLoot += owner.getCardsInState(DISCARD).size;
   }
   
   def nightActivity(state: GameState) {
     return;
   }

   def endOfVoyageActivity(state: GameState) {
     return;
   }
  
}