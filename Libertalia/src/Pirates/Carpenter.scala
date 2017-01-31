package Pirates
import Driver.GameState
import Entities.Player

// This is the rank 9 class
class Carpenter(owningPlayer:Int) extends Pirate(9, owningPlayer) {
  
  val rankOrdering:List[Int] = List(2,3,4,5,6,1);
  
   /*
   * Lose 50% of doubloons
   */
   def dayActivity(state: GameState) {
     // I think it's rounded up in the game? May need to change this for off by 1.
       state.players(owningPlayer).currentLoot = (state.players(owningPlayer).currentLoot / 2);
   }
   // Does nothing
   def nightActivity(state: GameState) {
     return;
   }
   // Gain 10 doubloons
   def endOfVoyageActivity(state: GameState) {
     state.players(owningPlayer).currentLoot = state.players(owningPlayer).currentLoot + 10;
   }
  
}