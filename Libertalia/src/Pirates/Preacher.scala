package Pirates

import Driver.GameState
import Pirates.PirateState._;
import Entities.Player
import Entities.DecisionType._;

class Preacher(owningPlayer:Int) extends Pirate(6, owningPlayer) {
  
   val rankOrdering:List[Int] = List(6,1,2,3,4,5);

   // Discard all booty except for 1
   def dayActivity(state: GameState) {
     val treasureSize = this.getMyOwner(state).treasure.size;
     if (treasureSize > 1) {
         var treasureChoice = this.getMyOwner(state).makeDecision(state, List.range(0, treasureSize), "Choose a treasure index to NOT discard", TREASURE_TO_KEEP.id);
         this.getMyOwner(state).treasure = List(this.getMyOwner(state).treasure(treasureChoice));
     }
   }
   
   def nightActivity(state: GameState) {
   }
   
   // Gain 5
   def endOfVoyageActivity(state: GameState) {
     this.getMyOwner(state).currentLoot += 5;
   }
}