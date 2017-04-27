package Entities
import Pirates.Pirate;
import Pirates.PirateState._;
import Driver.GameState;
import Treasure.Treasure;

class KmiPlayer(playerNumber:Int, isActivePlayer:Boolean) extends Player (playerNumber, isActivePlayer) {
  
  val r = scala.util.Random;
  
  // You can look at random player to get some idea of how to give valid responses.
  // I Kept the random logic for card selection.
  
  def playCard(state:GameState):Pirate = {
    val piratesInHand = this.getCardsInState(HAND);
    var randomPirate = piratesInHand(r.nextInt(piratesInHand.size));
    var choice:Pirate = this.getPirateFromDeck(randomPirate);
    choice.state = IN_PLAY;
    state.recordDecision(playerNumber + "," + state.recordGameStateWithDecision() + "," + DecisionType.PIRATE.id + "," + choice.majorRank);
    return choice;
  }
  
  def makeDecision(state:GameState, possibleChoices:List[Int], decisionPrompt:String, decisionType:Int):Int = {
    return possibleChoices(0);
  }
  
  def chooseTreasure(state:GameState, possibleChoices:Array[Treasure], decisionPrompt:String):Int = {
        //state.totalDecisions += 1;
    for (i <- 0 to (possibleChoices.size - 1)) {
      if (possibleChoices(i) != null) {
        state.recordDecision(playerNumber + "," + state.recordGameStateWithDecision() + "," + DecisionType.TREASURE.id + "," + i);
        return i;
      }
    }
    // This code should be unreachable given how game state operates.
    System.out.println("DISASTER!!!!!")
   // state.recordDecision(playerNumber + "," + state.recordGameStateWithDecision() + "," + DecisionType.TREASURE.id + "," + -1);
    return -1;
  }
  
}