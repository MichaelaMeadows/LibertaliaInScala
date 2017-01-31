package Pirates

import Driver.GameState
import Entities.Player
import Pirates.PirateState._;

/*
 * Rank 4 class
 */
class Recruiter(minorRank:Int, owningPlayer:Int) extends Pirate(4, minorRank, owningPlayer) {
  
  /*
   * Take a card from your den and place it into your hand
   */
   def dayActivity(state: GameState, owningPlayer: Player) {
     val piratesInDen = owningPlayer.getCardsInState(DEN);
     // You MUST place a card into your hand if able.
     if (piratesInDen.size > 0) {
         var decision:Int = owningPlayer.makeDecision(state, piratesInDen, "What pirate do you wish to return to your hand?");
         owningPlayer.personalDeck(decision).state = HAND;
     }
   }
   
   def nightActivity(state: GameState, owningPlayer: Player) {
     return;
   }
   def endOfVoyageActivity(state: GameState, owningPlayer: Player) {
     return;
   }
  
}