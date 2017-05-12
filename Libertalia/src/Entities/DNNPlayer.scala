package Entities
import Pirates.Pirate;
import Pirates.PirateState._;
import Driver.GameState;
import Treasure.Treasure;

class DNNPlayer(playerNumber:Int, isActivePlayer:Boolean) extends Player (playerNumber, isActivePlayer) {
  
  val r = scala.util.Random;
  
  def playCard(state:GameState):Pirate = {
    val piratesInHand = this.getCardsInState(HAND);
    var currentStateMatrix:Array[Array[Float]] = Array.ofDim(piratesInHand.size, state.FEATURE_COUNT);
    var currentStateVector:Array[Float] = generateStateVector(state, DecisionType.PIRATE.id);
    
    for (x <- 0 to (piratesInHand.size -1)) {
      currentStateMatrix(x) = currentStateVector.clone();
      currentStateMatrix(x)(state.FEATURE_COUNT - 1 ) = piratesInHand(x);
    }

    var results = state.tfAdapter.getExpectedMoveValues(currentStateMatrix);
    
    var bestIndex = 0;
    var index = 0;
    var bestScore:Float = 0;
    results.foreach(r => {
      if (r(1) > bestScore) {
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
    state.recordDecision(playerNumber + "," + state.recordGameStateWithDecision() + "," + DecisionType.PIRATE.id + "," + pirate.majorRank);
    return pirate;
  }
  
//  def playCard(state:GameState):Pirate = {
//    //state.totalDecisions += 1;
//    val piratesInHand = this.getCardsInState(HAND);
//
//    //System.out.println("Number of options:" + piratesInHand.size);
//    var randomPirate = piratesInHand(r.nextInt(piratesInHand.size));
//    
//    var choice:Pirate = this.getPirateFromDeck(randomPirate);
//    //choice.state = IN_PLAY;
//   // state.recordDecision(playerNumber + "," + state.recordGameStateWithDecision() + "," + DecisionType.PIRATE.id + "," + choice.majorRank);
//    return choice;
//  }
  
  def makeDecision(state:GameState, possibleChoices:List[Int], decisionPrompt:String, decisionType:Int):Int = {
    //state.totalDecisions += 1;
    var choice = possibleChoices(r.nextInt(possibleChoices.size));
    state.recordDecision(playerNumber + "," + state.recordGameStateWithDecision() + "," + decisionType + "," + choice);
    choice;
  }
  
  def chooseTreasure(state:GameState, possibleChoices:Array[Treasure], decisionPrompt:String):Int = {
    
    var validIndex:List[Int] = List();
    for (v <- 0 to (possibleChoices.size - 1)) {
      if (possibleChoices(v) != null) {
        validIndex = validIndex.+:(v);
      }
    }
    
    var currentStateMatrix:Array[Array[Float]] = Array.ofDim(validIndex.size, state.FEATURE_COUNT);
    var currentStateVector:Array[Float] = generateStateVector(state, DecisionType.PIRATE.id);
    
    for (x <- 0 to (validIndex.size -1)) {
      currentStateMatrix(x) = currentStateVector.clone();
      currentStateMatrix(x)(state.FEATURE_COUNT - 1 ) = validIndex(x);
    }

    var results = state.tfAdapter.getExpectedMoveValues(currentStateMatrix);
    var bestIndex = 0;
    var index = 0;
    var bestScore:Float = 0;
    results.foreach(r => {
      if (r(1) > bestScore) {
        bestScore = r(1);
        bestIndex = index;
      }
      index += 1;
    });
    validIndex(bestIndex);
  }
  
  // This makes me feel bad becasue of how inefficient it is....  TODO TODO TODO
  def generateStateVector(state:GameState, decisionId:Int):Array[Float] = {
    var vector:Array[Float] = Array.ofDim(state.FEATURE_COUNT);
    var index = 0;
    var stateString = (playerNumber + "," + state.recordGameStateWithDecision() + "," + decisionId).split(",");
    stateString.foreach(s => {
      vector(index) = s.toFloat;
      index += 1;
    });
    
    return vector;
  }
  
}