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
    state.writeToFile(state.recordGameStateWithDecision() + "," + playerNumber + "," + DecisionType.PIRATE.id + "," + choice.majorRank);
    return choice;
  }
  
  def makeDecision(state:GameState, possibleChoices:List[Int], decisionPrompt:String, decisionType:Int):Int = {
    var choice = possibleChoices(r.nextInt(possibleChoices.size));
    state.writeToFile(state.recordGameStateWithDecision() + "," + playerNumber + "," + decisionType + "," + choice);
    choice;
  }
  
  def chooseTreasure(state:GameState, possibleChoices:Array[Treasure], decisionPrompt:String):Int = {
    for (i <- 0 to (possibleChoices.size - 1)) {
      if (possibleChoices(i) != null) {
        state.writeToFile(state.recordGameStateWithDecision() + "," + playerNumber + "," + DecisionType.TREASURE.id + "," + i);
        return i;
      }
    }
    state.writeToFile(state.recordGameStateWithDecision() + "," + playerNumber + "," + DecisionType.TREASURE.id + "," + -1);
    return -1;
  }
  
}