package Pirates

import Driver.GameState
import Entities.Player
import Pirates.PirateState._;

/*
 * Rank 24 class
 */
class Gambler(minorRank:Int, owningPlayer:Int) extends Pirate(24, minorRank, owningPlayer) {
  
  /*
   * Lose 1 doubloon for each piece of booty
   */
   def dayActivity(state: GameState, owningPlayer: Player) {
     owningPlayer.currentLoot -= owningPlayer.treasure.size;
   }

   def nightActivity(state: GameState, owningPlayer: Player) {
     return;
   }
   
   /*
    * Gain 8 doubloons at the end of the trip
    */
   def endOfVoyageActivity(state: GameState, owningPlayer: Player) {
     owningPlayer.currentLoot += 8;
   }
  
}