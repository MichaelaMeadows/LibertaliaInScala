package Entities
import scala.collection.mutable.ListBuffer;
import Pirates.Pirate;
import Driver.GameState;
import Pirates._;
import Pirates.PirateState._;
import Treasure.TreasureType._;
import Treasure.Treasure;

abstract class Player(playerNumber:Int, isActivePlayer:Boolean) {
  
  /*
   * I think these should just be generated on the fly. Might be slower... but not in a way that should matter;
  var hand:List[Pirate] = List();
  var den:List[Pirate] = List();
  var discard:List[Pirate] = List();
   */
  private var personalDeck = new Array[Pirate](30);
  var treasure:List[Treasure] = List();
  var totalScore:Int = 0;
  var currentLoot:Int = 10;
  var myNumber = playerNumber;
  var activePlayer = isActivePlayer;
  
  /*
   * Fill the personal deck with cards not in play, and with the correct minor version
   * Right now... just making the worst game ever
   */
  def innitDeck() {
    // If I haven't made it yet, it's a beggar. Worst game ever. Literally unplayable.
   // for (i <- 0 to 29) {
    //  personalDeck(i) = new Begger(playerNumber);
   // }
    // Commented out lines mean I haven't made that pirate yet!
    innitPirate(new Parrot(playerNumber));
    innitPirate(new Monkey(playerNumber));
    innitPirate(new Begger(playerNumber));
    innitPirate(new Recruiter(playerNumber));
    innitPirate(new CabinBoy(playerNumber));
    innitPirate(new Preacher(playerNumber));
    innitPirate(new Barkeep(playerNumber)); 
    innitPirate(new Waitress(playerNumber));
    innitPirate(new Carpenter(playerNumber));
    innitPirate(new FrenchOfficer(playerNumber));
    innitPirate(new VoodooWitch(playerNumber));
    innitPirate(new FreedSlave(playerNumber));
    innitPirate(new Mutineer(playerNumber));
    innitPirate(new Brute(playerNumber));
    innitPirate(new Gunner(playerNumber));
    innitPirate(new Topman(playerNumber));
    innitPirate(new SpanishSpy(playerNumber));
    innitPirate(new Cook(playerNumber));
    innitPirate(new Bosun(playerNumber));
    innitPirate(new Armorer(playerNumber));
    innitPirate(new Merchant(playerNumber));
    innitPirate(new Surgeon(playerNumber));
    innitPirate(new Treasurer(playerNumber));
    innitPirate(new Gambler(playerNumber));
    innitPirate(new GovernorsDaughter(playerNumber));
    innitPirate(new QuarterMaster(playerNumber));
    innitPirate(new GrannyWata(playerNumber));
    innitPirate(new FirstMate(playerNumber));
    innitPirate(new Captain(playerNumber));
    innitPirate(new SpanishGovernor(playerNumber));

    //System.out.println("Starting rank list");
    //personalDeck.foreach(f => System.out.println("Rank:" + f.majorRank));
    
  }
  
  def getStateString():String = {
    val buf = new StringBuilder;
   // buf ++= "total, currrent";
    
    // Intentionally not showing total score in the state.
    //buf.append(","+totalScore + "," + currentLoot);
    buf.append("," + currentLoot);
    //buf.append("Pirates");
   // buf.append("@Pirates@");
    personalDeck.foreach(p => buf.append("," + p.state.id));
    // Need to make treasure constant length... because of things like monkey and cook... the upper bound on length is actually quite large.
    //buf ++= "treasures";
   // buf.append("@Treasure@");
    treasure.foreach(t => buf.append("," + t.getType().id));
    // Now we pad the treasure list out to 10
    for (i <- treasure.length to 10) {
      buf.append("," + 0)
    }
    return buf.toString();
  }
  
  def innitPirate(pirate:Pirate) {
    personalDeck(pirate.majorRank - 1) = pirate;
  }
  
  // This controls access to the personal deck, and shift to account for 0 indexing
  def getPirateFromDeck(majorRank:Int):Pirate = {
    return personalDeck(majorRank - 1);
  }
  
  def addCardsToHand(cardsToAdd:List[Int]) {
    //cardsToAdd.foreach(card => addCardToHand(personalDeck(card)));
    // Hard coding garbage for now to test
    // 2 is actually the begger (3 - 1)
    //addCardToHand(personalDeck(3 - 1));
    
    cardsToAdd.foreach(c => addCardToHand(personalDeck(c - 1)));
    
    //ANYTHING GOES
    //for (i <- 0 to 29) {
     //addCardToHand(personalDeck(i));
    //}
  }
  
  /*
   * There are some number of methods required to interact with players
   */
  def addCardToHand(pirate:Pirate) {
    this.personalDeck(pirate.majorRank - 1).state = HAND;
  }
  
  def getCardsInState(state:PirateState.Value):List[Int] = {
    val buf = new ListBuffer[Int];
    var position = 0;
    personalDeck.foreach((p:Pirate) => {
      if(p.state == state) {
            buf+=p.majorRank
        }
      position +=1;
      }
    );
    return buf.toList;
  }

  def endOfVoyage(state:GameState) {
    this.getCardsInState(DEN).foreach((p:Int) => this.getPirateFromDeck(p).endOfVoyageActivity(state))
  }

  def nightActivity(state:GameState) {
    this.getCardsInState(DEN).foreach((p:Int) => this.getPirateFromDeck(p).nightActivity(state))
  }
  
  def resetDenAndDiscard() {
    this.personalDeck.foreach(pirate => {
      if (pirate.state == DEN
          || pirate.state == DISCARD) {
        pirate.state = OUT_OF_PLAY;
      }
    });
  }
  /*
   * Add up all the treasure values.
   */
  def updateCurretScoreWithTreasure() {
    var treasureValue:Int = 0;
    var mapsFound:Int = 0;
    treasure.foreach(treasure => {
      treasureValue += treasure.getValue();
      if (treasure.getType() == MAP) {
        mapsFound += 1;
      }
      })
      if (mapsFound >= 3) {
        //System.out.println("I GOT ENOUGH MAPS");
        treasureValue += 12;
      }
    this.currentLoot += treasureValue;
  }

  // Various AI strategies need to extend this class and fill out what this method should do I guess.
  // Humans should just ping for input from the player.
  def playCard(state:GameState):Pirate
  
  // Generic method to solicit a choice from a player / AI
  // Takes a prompt and expects an int response
  // Might want to also take in DECISION_TYPE or something to add minimal intelligence
  def makeDecision(state:GameState, possibleChoices:List[Int], decisionPrompt:String, decisionType:Int):Int
  
  // Return the index of the treasure you want
  def chooseTreasure(state:GameState, possibleChoices:Array[Treasure], decisionPrompt:String):Int
  
/*    object DecisionType extends Enumeration {
      type Type = Value
      val PIRATE, TREASURE, PLAYER_TO_ATTACK, PIRATE_TO_ATTACK = Value
    }*/
  
  
}