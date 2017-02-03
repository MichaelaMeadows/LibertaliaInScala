package Pirates

import Driver.GameState
import Entities.Player
import Pirates.PirateState._;
import scala.collection.mutable.ListBuffer;

/*
 * Rank 15 class
 */
class Gunner(owningPlayer:Int) extends Pirate(15, owningPlayer) {
  
  val rankOrdering:List[Int] = List(1,2,3,4,5,6);
  
  /*
   * Kill a pirate in ANY den.
   */
   def dayActivity(state: GameState) {
     var attackablePlayers = new ListBuffer[Int];
     for (i <- 1 to 6) {
       if (state.getPlayerByNumber(i).activePlayer && state.getPlayerByNumber(i).getCardsInState(DEN).size > 0) {
         attackablePlayers+=i;
       }
     }
    if (attackablePlayers.size > 0) {
      var playerChoice = this.getMyOwner(state).makeDecision(state, attackablePlayers.toList, "Chose a player to attack")
      var pirateToAttack = this.getMyOwner(state).makeDecision(state, state.getPlayerByNumber(playerChoice).getCardsInState(DEN), "Select a pirate to kill");
      state.getPlayerByNumber(playerChoice).getPirateFromDeck(pirateToAttack).state = DISCARD;
    }
   }
   
   def nightActivity(state: GameState) {
     return;
   }
   def endOfVoyageActivity(state: GameState) {
     return;
   }
  
}