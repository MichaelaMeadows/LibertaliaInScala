package Pirates

import Driver.GameState
import Pirates.PirateState._;
import Entities.Player

class Mutineer(owningPlayer:Int) extends Pirate(13, owningPlayer) {
  
   val rankOrdering:List[Int] = List(5,6,1,2,3,4);

   def dayActivity(state: GameState) {
   }
   
   // Dicards the lowest ranked pirate in the den other than this. Gain  money.
   def nightActivity(state: GameState) {
     var owner = this.getMyOwner(state);
     var possibleKills = owner.getCardsInState(DEN).filter(p => p != 13).sorted;
     if (possibleKills.size > 0) {
       owner.getPirateFromDeck(possibleKills(0)).state = DISCARD;
       owner.currentLoot += 2;
     }
   }
   def endOfVoyageActivity(state: GameState) {
     return;
   }
}