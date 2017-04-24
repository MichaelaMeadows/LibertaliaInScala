package Pirates

import Driver.GameState
import Pirates.PirateState._;
import Treasure.TreasureType._;
import Treasure.Treasure;
import Entities.Player

class GrannyWata(owningPlayer:Int) extends Pirate(27, owningPlayer) {
  
   val rankOrdering:List[Int] = List(5,6,1,2,3,4);

   def dayActivity(state: GameState) {
     //System.out.println("Played A Granny");
   }
   
   // Gain two doubloons
   // The game state makes sure this is the only Granny alive and in a den.
   def nightActivity(state: GameState) { 
     //System.out.println("Granny Power");
     this.getMyOwner(state).currentLoot += 2;
   }
   def endOfVoyageActivity(state: GameState) { }
}