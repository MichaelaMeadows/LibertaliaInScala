package Pirates

import Driver.GameState
import Entities.Player
import Pirates.PirateState._;

/*
 * Rank 14 class
 */
class Brute(owningPlayer:Int) extends Pirate(3, owningPlayer) {
  
  val rankOrdering:List[Int] = List(6,1,2,3,4,5);
  
  /*
   * Kill the strongest pirate in play.
   */
   def dayActivity(state: GameState) {
     state.cardsInPlay.reverse(0).state = DISCARD;
     state.cardsInPlay = state.cardsInPlay.filter(p => p.state == IN_PLAY);
   }
   
   def nightActivity(state: GameState) {
     return;
   }
   def endOfVoyageActivity(state: GameState) {
     return;
   }
  
}