package Entities
import scala.collection.mutable.ListBuffer;
import Pirates.Pirate;
import Driver.GameState;
import Pirates._;
import Pirates.PirateState._;
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
  
  /*
   * Fill the personal deck with cards not in play, and with the correct minor version
   * Right now... just making the worst game ever
   */
  def innitDeck() {
    // If I haven't made it yet, it's a beggar. Worst game ever. Literally unplayable.
    for (i <- 0 to 29) {
      personalDeck(i) = new Begger(playerNumber);
    }
    // Commented out lines mean I haven't made that pirate yet!
    innitPirate(new Parrot(playerNumber));
    //innitPirate(new Monkey(playerNumber));
    innitPirate(new Begger(playerNumber));
    innitPirate(new Recruiter(playerNumber));
    //innitPirate(new CabinBoy(playerNumber));
    //innitPirate(new Preacher(playerNumber));
    innitPirate(new Barkeep(playerNumber)); 
   // innitPirate(new Waitress(playerNumber));
    innitPirate(new Carpenter(playerNumber));
    //innitPirate(new FrenchOfficer(playerNumber));
    //innitPirate(new VoodooWitch(playerNumber));
    //innitPirate(new FreedSlave(playerNumber));
    //innitPirate(new Mutineer(playerNumber));
    //innitPirate(new Brute(playerNumber));
    //innitPirate(new Gunner(playerNumber));
    //innitPirate(new Topman(playerNumber));
    //innitPirate(new SpanishSpy(playerNumber));
    //innitPirate(new Cook(playerNumber));
    //innitPirate(new Bosun(playerNumber));
    //innitPirate(new Armorer(playerNumber));
    //innitPirate(new Merchant(playerNumber));
    //innitPirate(new Surgeon(playerNumber));
    //innitPirate(new Treasurer(playerNumber));
    innitPirate(new Gambler(playerNumber));
    //innitPirate(new GovernorsDaughter(playerNumber));
    //innitPirate(new QuarterMaster(playerNumber));
    //innitPirate(new GrannyWata(playerNumber));
    //innitPirate(new FirstMate(playerNumber));
    //innitPirate(new Captain(playerNumber));
    //innitPirate(new SpanishGovernor(playerNumber));
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
    addCardToHand(personalDeck(3 - 1));
  }
  
  /*
   * There are some number of methods required to interact with players
   */
  def addCardToHand(pirate:Pirate) {
    this.personalDeck(pirate.majorRank - 1).state = HAND;
  }
  
  def getCardsInState(state:Value):List[Int] = {
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
    this.getCardsInState(DEN).foreach((p:Int) => this.personalDeck(p).endOfVoyageActivity(state))
  }

  def nightActivity(state:GameState) {
    this.getCardsInState(DEN).foreach((p:Int) => this.personalDeck(p).nightActivity(state))
  }

  // Various AI strategies need to extend this class and fill out what this method should do I guess.
  // Humans should just ping for input from the player.
  def playCard(state:GameState):Pirate
  
  // Generic method to solicit a choice from a player / AI
  // Takes a prompt and expects an int response
  // Might want to also take in DECISION_TYPE or something to add minimal intelligence
  def makeDecision(state:GameState, possibleChoices:List[Int], decisionPrompt:String):Int
  
}