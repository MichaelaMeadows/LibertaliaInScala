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
     state.getPlayerByNumber(owningPlayer).currentLoot -= state.getPlayerByNumber(owningPlayer).treasure.size;
   }

   def nightActivity(state: GameState) {
     return;
   }

   /*
    * Gain 8 doubloons at the end of the trip
    */
   def endOfVoyageActivity(state: GameState) {
     state.getPlayerByNumber(owningPlayer).currentLoot += 8;
   }
  
}