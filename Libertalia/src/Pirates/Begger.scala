package Pirates

import Driver.GameState
import Entities.Player

/*
 * Rank 3 class
 */
class Begger(owningPlayer:Int) extends Pirate(3, owningPlayer) {
  
  val rankOrdering:List[Int] = List(3,4,5,6,1,2);
  
  /*
   * Steal money from the most powerful pirate played
   */
   def dayActivity(state: GameState) {
     // Steals the smaller value between 3 and the money the enemy has.
     var targetPlayer:Player = state.getPlayerByNumber(state.cardsInPlay(0).owningPlayer);
     val moneyStolen:Int = Math.min(targetPlayer.currentLoot, 3)
     targetPlayer.currentLoot -= moneyStolen;
     state.getPlayerByNumber(owningPlayer).currentLoot += moneyStolen; 
     System.out.println("Player: " + owningPlayer + "Has money: " + state.getPlayerByNumber(owningPlayer).currentLoot);
   }
   
   def nightActivity(state: GameState) {
     return;
   }
   def endOfVoyageActivity(state: GameState) {
     return;
   }
  
}