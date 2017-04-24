package Pirates

import Driver.GameState
import Pirates.PirateState._;
import Treasure.TreasureType._;
import Treasure.Treasure;
import Entities.Player

class SpanishSpy(owningPlayer:Int) extends Pirate(17, owningPlayer) {
  
   val rankOrdering:List[Int] = List(3,4,5,6,1,2);

   // Discard all Spanish Officers. Draw booty for each one discarded.
   // I cheat slightly here. I'm not adding in the discarded officers and randomizing again.
   def dayActivity(state: GameState) {
     val owner = this.getMyOwner(state);
     var sizeBeforeFilter = owner.treasure.size;
     owner.treasure = owner.treasure.filterNot(tr => tr.getType() == OFFICER);
     
     //this.getMyOwner(state).treasure.foreach(t => System.out.print(t.getType()));
    // System.out.println("Discarding officers and drawing:" + (sizeBeforeFilter - owner.treasure.size));
     for (i <- 1 to (sizeBeforeFilter - owner.treasure.size)) {
     //  System.out.println("Drawing stuff");
       owner.treasure = owner.treasure.+:(state.fullTreasureList(state.nextTreasurePiece));
       state.nextTreasurePiece += 1;
     }
     //this.getMyOwner(state).treasure.foreach(t => System.out.print(t.getType()));
     
   }
   def nightActivity(state: GameState) { }
   def endOfVoyageActivity(state: GameState) { }
}