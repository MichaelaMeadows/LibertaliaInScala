package Entities
import Pirates.Pirate;
import Pirates.PirateState._;
import Driver.GameState;
import Treasure.Treasure;

class RandomPlayer(playerNumber:Int, isActivePlayer:Boolean) extends Player (playerNumber, isActivePlayer) {
  
  val r = scala.util.Random;
  
  def playCard(state:GameState):Pirate = {
    val piratesInHand = this.getCardsInState(HAND);

    //System.out.println("Number of options:" + piratesInHand.size);
    var randomPirate = piratesInHand(r.nextInt(piratesInHand.size));
    
    var choice:Pirate = this.getPirateFromDeck(randomPirate);
    choice.state = IN_PLAY;
    return choice;
  }
  
  def makeDecision(state:GameState, possibleChoices:List[Int], decisionPrompt:String):Int = {
    return possibleChoices(r.nextInt(possibleChoices.size));
  }
  
  def chooseTreasure(state:GameState, possibleChoices:Array[Treasure], decisionPrompt:String):Int = {
    for (i <- 0 to (possibleChoices.size - 1)) {
      if (possibleChoices(i) != null) {
        return i;
      }
    }
    return -1;
  }
  
}