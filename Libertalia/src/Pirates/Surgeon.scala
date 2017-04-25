package Pirates

import Driver.GameState
import Pirates.PirateState._;
import Treasure.TreasureType._;
import Treasure.Treasure;
import Entities.Player
import Entities.DecisionType._;

class Surgeon(owningPlayer:Int) extends Pirate(22, owningPlayer) {
  
   val rankOrdering:List[Int] = List(1,2,3,4,5,6);

   // Bring a card from the discarded state back into your hand
   // This plays with the known information thing... but I'm going to ignore that for now.
   def dayActivity(state: GameState) {
     val owner = this.getMyOwner(state);
     var discardedPirates = owner.getCardsInState(DISCARD);
     if (discardedPirates.size > 0) { 
       //System.out.println("Choices " + discardedPirates);
     var pirateToResurrect = this.getMyOwner(state).makeDecision(state, discardedPirates, "Select a pirate to BRING BACK TO LIFE", SURGEON_POWER.id);
       //System.out.println("Resurrecting " + pirateToResurrect);
       owner.getPirateFromDeck(pirateToResurrect).state = HAND;
     }

   }
   def nightActivity(state: GameState) { }
   def endOfVoyageActivity(state: GameState) { }
}