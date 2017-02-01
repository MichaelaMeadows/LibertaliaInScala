package Pirates

import Driver.GameState
import Entities.Player
import Pirates.PirateState._;
import Treasure.TreasureType._;
import Treasure.Treasure;

/*
 * Rank 8 class
 */
class Waitress(owningPlayer:Int) extends Pirate(8, owningPlayer) {
  
   val rankOrdering:List[Int] = List(1,2,3,4,5,6);

   def dayActivity(state: GameState) {
   }
   
   /*
    * You MAY sell a map to gain 3.
    */
   def nightActivity(state: GameState) {
    var mapCount = 0;
     this.getMyOwner(state).treasure.foreach(t => {
       if (t.getType() == MAP) {
         mapCount += 1;
       }
     });

     if (mapCount > 0) {
       var decision = this.getMyOwner(state).makeDecision(state, List(0, 1), "Would you like to sell a map to earn 3? 0=No 1=Yes")
       if (decision == 1) {
         System.out.println("I chose to sell a map. I previously has maps of quantity: " + mapCount);
         this.getMyOwner(state).treasure = this.getMyOwner(state).treasure.filter(t => t.getType() != MAP);
         mapCount -= 1;
         while (mapCount > 0) {
           this.getMyOwner(state).treasure = this.getMyOwner(state).treasure:+ new Treasure(MAP);
           mapCount -= 1;
         }
         this.getMyOwner(state).currentLoot += 3;
         this.getMyOwner(state).treasure.foreach(t => System.out.println("" + t.getType()));
         return;
       }
       System.out.println("I chose NOT to sell a map");
     }
     
     
   }

   def endOfVoyageActivity(state: GameState) {
   }
  
}