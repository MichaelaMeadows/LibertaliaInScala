package Pirates

import Driver.GameState
import Entities.Player
import Pirates.PirateState._;

/*
 * Rank 30 class
 */
class SpanishGovernor(owningPlayer:Int) extends Pirate(30, owningPlayer) {
  
   val rankOrdering:List[Int] = List(2,3,4,5,6,1);
  
  /*
   * Discard your entire den
   */
   def dayActivity(state: GameState) {
     var player = state.getPlayerByNumber(owningPlayer);
     player.getCardsInState(DEN).foreach(rank => player.getPirateFromDeck(rank).state = DISCARD);
   }
   
   def nightActivity(state: GameState) {
     return;
   }
   def endOfVoyageActivity(state: GameState) {
     return;
   }
  
}