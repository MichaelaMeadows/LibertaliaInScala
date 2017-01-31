package Pirates

import Driver.GameState
import Entities.Player
import Pirates.PirateState._;

/*
 * Rank 4 class
 */
class Barkeep(minorRank:Int, owningPlayer:Int) extends Pirate(4, minorRank, owningPlayer) {

   def dayActivity(state: GameState, owningPlayer: Player) {
     return
   }
   
   /*
    * Earn a doubloon every night
    */
   def nightActivity(state: GameState, owningPlayer: Player) {
     owningPlayer.currentLoot += 1
   }
   
   def endOfVoyageActivity(state: GameState, owningPlayer: Player) {
     return;
   }
  
}