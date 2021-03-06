package Entities
import Pirates.Pirate;
import Pirates.PirateState._;
import Driver.GameState;
import Treasure.Treasure;
import scala.util.Random

class RandomPlayer(playerNumber:Int, isActivePlayer:Boolean) extends Player (playerNumber, isActivePlayer) {
  
  val r = scala.util.Random;
  
  def playCard(state:GameState):Pirate = {
    //state.totalDecisions += 1;
    val piratesInHand = this.getCardsInState(HAND);

    //System.out.println("Number of options:" + piratesInHand.size);
    var randomPirate = piratesInHand(r.nextInt(piratesInHand.size));
    
    var choice:Pirate = this.getPirateFromDeck(randomPirate);
    //choice.state = IN_PLAY;
   // state.recordDecision(playerNumber + "," + state.recordGameStateWithDecision() + "," + DecisionType.PIRATE.id + "," + choice.majorRank);
    return choice;
  }
  
  def makeDecision(state:GameState, possibleChoices:List[Int], decisionPrompt:String, decisionType:Int):Int = {
    //state.totalDecisions += 1;
    var choice = possibleChoices(r.nextInt(possibleChoices.size));
   // state.recordDecision(playerNumber + "," + state.recordGameStateWithDecision() + "," + decisionType + "," + choice);
    choice;
  }
  
  def chooseTreasure(state:GameState, possibleChoices:Array[Treasure], decisionPrompt:String):Int = {
    var choices:List[Int] = Random.shuffle(((0 to (possibleChoices.size - 1)).toList));
    for (i <- 0 to (possibleChoices.size - 1)) {
      var choice = choices(i);
      if (possibleChoices(choice) != null) {
        state.recordDecision(playerNumber + "," + state.recordGameStateWithDecision() + "," + DecisionType.TREASURE.id + "," + choice);
        return choice;
      }
    }
    System.out.println("DISASTER!!!!!")
   // state.recordDecision(playerNumber + "," + state.recordGameStateWithDecision() + "," + DecisionType.TREASURE.id + "," + -1);
    return -1;
  }
  
}