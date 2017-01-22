package Pirates

import Driver.GameState
import Entities.Player

/*
 * Rank 3 class
 */
class Begger(minorRank:Int, owningPlayer:Int) extends Pirate(3, minorRank, owningPlayer) {
  
  /*
   * Steal money from the most powerful pirate played
   */
   def dayActivity(state: GameState, owningPlayer: Player) {
     // Steals the smaller value between 3 and the money the enemy has.
     var targetPlayer:Player = state.players(state.cardsInPlay(0).owningPlayer);
     val moneyStolen:Int = Math.min(targetPlayer.currentLoot, 3)
     targetPlayer.currentLoot -= moneyStolen;
     owningPlayer.currentLoot += moneyStolen;
   }
   
   def nightActivity(state: GameState, owningPlayer: Player) {
     return;
   }
   def endOfVoyageActivity(state: GameState, owningPlayer: Player) {
     return;
   }
  
}