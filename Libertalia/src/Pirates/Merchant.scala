package Pirates

import Driver.GameState
import Pirates.PirateState._;
import Treasure.TreasureType._;
import Treasure.Treasure;
import Entities.Player

class Merchant(owningPlayer:Int) extends Pirate(21, owningPlayer) {
  
   val rankOrdering:List[Int] = List(6,1,2,3,4,5);


   /*
    * Sell booty to gain money
    * 2 -> 3
    * 3 -> 5
    * 
    * I'm going to fail to correctly handle the cases where someone has more than 4 of an identical treasure... it should be super rare... I hope!
    */
   def dayActivity(state: GameState) {
     val map = scala.collection.mutable.HashMap.empty[Treasure.TreasureType.Value,Int]
     
    // System.out.println("Evaluating ability to sell treasure");
    // this.getMyOwner(state).treasure.foreach(t => System.out.print(t.getType()));
     var owner = this.getMyOwner(state);
     owner.treasure.foreach(t => {
       map.put(t.getType(), map.getOrElse(t.getType(), 0) + 1);
     });
     
     var treasureChoices:List[Treasure.TreasureType.Value] = List();
     map.foreach(f => {
       //System.out.println("Type " + f._1 + "Count " + f._2);
       if (f._2 >= 2) {
         treasureChoices = treasureChoices.+:(f._1);
       }
     });
     
     if (treasureChoices.size > 0) {
       //System.out.println("I can sell treasure");
       var treasureToSell = this.getMyOwner(state).makeDecision(state, List.range(0, treasureChoices.size), "Which treasure do you want to sell?");
         val preFilterSize = owner.treasure.size;
         // Here is the buggy logic. It should filter a max of 3 elements.
         owner.treasure = owner.treasure.filter(t => t.getType() != treasureChoices(treasureToSell));
        // System.out.println("After the sell");
        // this.getMyOwner(state).treasure.foreach(t => System.out.print(t.getType()));
         
         if ((preFilterSize - owner.treasure.size) == 2) {
           //System.out.println("I sold 2");
           owner.currentLoot += 3;
         } else {
           //System.out.println("I sold 3");
           owner.currentLoot += 5;
         }
     }
   }

   
   def nightActivity(state: GameState) { }
   def endOfVoyageActivity(state: GameState) { }
}