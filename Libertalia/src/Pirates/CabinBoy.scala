package Pirates

import Driver.GameState
import Entities.Player
import Pirates.PirateState._;
import Treasure.TreasureType._;

/*
 * Rank 5 class
 * 
 * The cabinboys special power takes place during treasure selection. The boy gets no treasure.
 */
class CabinBoy(owningPlayer:Int) extends Pirate(5, owningPlayer) {
  
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