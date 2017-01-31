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
    for (i <- 0 to 29) {
      personalDeck(i) = new Begger(playerNumber);
    }
  }
  
  // This controls access to the personal deck, and shift to account for 0 indexing
  def getPirateFromDeck(majorRank:Int):Pirate = {
    return personalDeck(majorRank - 1);
  }
  
  def addCardsToHand(cardsToAdd:List[Int]) {
    //cardsToAdd.foreach(card => addCardToHand(personalDeck(card)));
    // Hard coding garbage for now to test
    addCardToHand(personalDeck(3));
  }
  
  /*
   * There are some number of methods required to interact with players
   */
  def addCardToHand(pirate:Pirate) {
    this.personalDeck(pirate.majorRank).state = HAND;
  }
  
  def getCardsInState(state:Value):List[Int] = {
    val buf = new ListBuffer[Int];
    personalDeck.foreach((p:Pirate) => {
      if(p.state == state) {
            buf+=p.majorRank
        }
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
  def makeDecision(state:GameState, possibleChoices:List[Int], decisionPrompt:String):Int
  
}