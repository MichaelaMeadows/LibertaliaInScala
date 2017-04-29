package Entities
import Pirates.Pirate;
import Pirates.PirateState._;
import Driver.GameState;
import Treasure.Treasure;

class DNNPlayer(playerNumber:Int, isActivePlayer:Boolean) extends Player (playerNumber, isActivePlayer) {
  
  val r = scala.util.Random;
  
  def playCard(state:GameState):Pirate = {
    val piratesInHand = this.getCardsInState(HAND);
    var currentStateMatrix:Array[Array[Float]] = Array.ofDim(piratesInHand.size, 299);
    var currentStateVector:Array[Float] = generateStateVector(state, DecisionType.PIRATE.id);
    
    for (x <- 0 to (piratesInHand.size -1)) {
      currentStateMatrix(x) = currentStateVector.clone();
      currentStateMatrix(x)(298) = piratesInHand(x);
    }

    var results = state.tfAdapter.getExpectedMoveValues(currentStateMatrix);
    
    var bestIndex = 0;
    var index = 0;
    var bestScore:Float = 0;
    results.foreach(r => {
      if (r(0) > bestScore) {
        bestScore = r(1);
        bestIndex = index;
      }
      index += 1;
    });

    var pirate = this.getPirateFromDeck(piratesInHand(bestIndex));
    
    if (pirate.state != HAND) {
      piratesInHand.foreach(println);
      println("Pirates done")
      println(bestIndex);
      println("Index")
      println(pirate.majorRank);
      println("Returned Rank")
      throw new Exception("FAIL");
    }
    return pirate;
  }
  
  def makeDecision(state:GameState, possibleChoices:List[Int], decisionPrompt:String, decisionType:Int):Int = {
    //state.totalDecisions += 1;
    var choice = possibleChoices(r.nextInt(possibleChoices.size));
    state.recordDecision(playerNumber + "," + state.recordGameStateWithDecision() + "," + decisionType + "," + choice);
    choice;
  }
  
  def chooseTreasure(state:GameState, possibleChoices:Array[Treasure], decisionPrompt:String):Int = {
      for (i <- 0 to (possibleChoices.size - 1)) {
      if (possibleChoices(i) != null) {
        state.recordDecision(playerNumber + "," + state.recordGameStateWithDecision() + "," + DecisionType.TREASURE.id + "," + i);
        return i;
      }
    }
    System.out.println("DISASTER!!!!!")
   // state.recordDecision(playerNumber + "," + state.recordGameStateWithDecision() + "," + DecisionType.TREASURE.id + "," + -1);
    return -1;
  }
  
  // This makes me feel bad becasue of how inefficient it is....  TODO TODO TODO
  def generateStateVector(state:GameState, decisionId:Int):Array[Float] = {
    var vector:Array[Float] = Array.ofDim(299);
    var index = 0;
    var stateString = (playerNumber + "," + state.recordGameStateWithDecision() + "," + decisionId).split(",");
    stateString.foreach(s => {
      vector(index) = s.toFloat;
      index += 1;
    });
    
    return vector;
  }
  
}