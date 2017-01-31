package Entities
import Pirates.Pirate;
import Pirates.PirateState._;
import Driver.GameState;

class RandomPlayer(playerNumber:Int, isActivePlayer:Boolean) extends Player (playerNumber, isActivePlayer) {
  
  val r = scala.util.Random;
  
  def playCard(state:GameState):Pirate = {
    val piratesInHand = this.getCardsInState(HAND);
    
    var choice:Pirate = this.getPirateFromDeck(piratesInHand(r.nextInt(piratesInHand.size)));
    choice.state = IN_PLAY;
    return choice;
  }
  
  def makeDecision(state:GameState, possibleChoices:List[Int], decisionPrompt:String):Int = {
    return 1;
  }
  
}