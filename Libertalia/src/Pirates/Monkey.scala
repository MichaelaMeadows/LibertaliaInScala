package Pirates

import Driver.GameState
import Pirates.PirateState._;
import Treasure.TreasureType._;
import Treasure.Treasure;
import Entities.Player

class Monkey(owningPlayer:Int) extends Pirate(2, owningPlayer) {
  
   val rankOrdering:List[Int] = List(2,3,4,5,6,1);

   // Give all cursed relics to player on the left.
   def dayActivity(state: GameState) {
     val leftPlayer = state.getLeftPlayer(this.getMyOwner(state).myNumber);
     val cursesToAdd = this.getMyOwner(state).treasure.filter(t => t.getType() == CURSED).size;
     
     var i = 0;
     for (i <- 1 to cursesToAdd) {
      // System.out.println("I have curses: " + cursesToAdd);
      // System.out.println("Before");
     //  leftPlayer.treasure.foreach(t => System.out.print(t.getType()))
       leftPlayer.treasure = leftPlayer.treasure.+:(new Treasure(CURSED));
      // System.out.println("After");
      // leftPlayer.treasure.foreach(t => System.out.print(t.getType()))
     }
     this.getMyOwner(state).treasure = this.getMyOwner(state).treasure.filter(t => t.getType() != CURSED);
     //System.out.println("My New Treasure");
     //this.getMyOwner(state).treasure.foreach(t => System.out.print(t.getType()))
     
   }
   
   def nightActivity(state: GameState) { }
   def endOfVoyageActivity(state: GameState) { }
}