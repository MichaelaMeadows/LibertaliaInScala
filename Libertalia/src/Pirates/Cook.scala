package Pirates

import Driver.GameState
import Entities.Player
import Pirates.PirateState._;
import Treasure.TreasureType._;

/*
 * Rank 18 class
 * 
 * The cooks special power takes place during treasure selection. The cook MUST slect 2 items, if able to.
 */
class Cook(owningPlayer:Int) extends Pirate(18, owningPlayer) {
  
   val rankOrdering:List[Int] = List(4,5,6,1,2,3);

   def dayActivity(state: GameState) {
   }
   
   def nightActivity(state: GameState) {
     return;
   }

   def endOfVoyageActivity(state: GameState) {
     return;
   }
  
}