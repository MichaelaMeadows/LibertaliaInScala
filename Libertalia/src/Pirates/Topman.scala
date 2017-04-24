package Pirates

import Driver.GameState
import Pirates.PirateState._;
import Treasure.TreasureType._;
import Treasure.Treasure;
import Entities.Player

class Topman(owningPlayer:Int) extends Pirate(16, owningPlayer) {
  
   val rankOrdering:List[Int] = List(2,3,4,5,6,1);

   def dayActivity(state: GameState) {}
   def nightActivity(state: GameState) { }
   
   // If you have the smallest den, you gain 5! Ties are okay.
   def endOfVoyageActivity(state: GameState) { 
     val owner = this.getMyOwner(state);
     var smallestDen = 10; 
     state.players.foreach(p => {
       smallestDen = Math.min(smallestDen, p.getCardsInState(DEN).size);
     })
     
     // Really, there's no way it can actually be smaller
    // System.out.println("Topman Considered!");
     //System.out.println("Smallest den should be " + smallestDen);
     if (owner.getCardsInState(DEN).size <= smallestDen) { 
      // System.out.println("Topman awarded!");
      // owner.getCardsInState(DEN).foreach(f => {System.out.print("Pirate:" + f)});
       owner.currentLoot += 5;
     }

   }
}