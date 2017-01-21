package Entities
import Pirates.Pirate;

class RandomPlayer(playerNumber:Int) extends Player (playerNumber) {
  
  val r = scala.util.Random;
  
  def playCard():Pirate = {
    var choice:Pirate = this.hand(r.nextInt(hand.size));
    this.hand = hand.filter(_>choice);
  }
  
  def makeDecision(decisionPrompt:String):Int = {
    return 1;
  }
  
}