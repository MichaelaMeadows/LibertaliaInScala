package Pirates

import Driver.GameState
import Entities.Player
import Pirates.PirateState._;

/*
 * Rank 24 class
 */
class Gambler(owningPlayer:Int) extends Pirate(24, owningPlayer) {
  
   val rankOrdering:List[Int] = List(3,4,5,6,1,2);
  /*
   * Lose 1 doubloon for each piece of booty
   */
   def dayActivity(state: GameState) {
     state.players(owningPlayer).currentLoot -= state.players(owningPlayer).treasure.size;
   }

   def nightActivity(state: GameState) {
     return;
   }

   /*
    * Gain 8 doubloons at the end of the trip
    */
   def endOfVoyageActivity(state: GameState) {
     state.players(owningPlayer).currentLoot += 8;
   }
  
}