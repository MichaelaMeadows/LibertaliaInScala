package Entities
import Pirates.Pirate;
import Driver.GameState;
import Treasure.Treasure;

abstract class Player(playerNumber:Int) {
  
  var hand:List[Pirate] = List();
  var den:List[Pirate] = List();
  var discard:List[Pirate] = List();
  var loot:List[Treasure] = List();
  var totalScore:Int = 0;
  var currentLoot:Int = 0;
  
  /*
   * There are some number of methods required to interact with players
   */
  def addCardToHand(pirate:Pirate) {
    this.hand = hand:+ pirate;
  }

  def setCurrentLoot(loot:Int) {
    this.currentLoot = loot;
  }

  def setTotalScore(loot:Int) {
    this.totalScore = loot;
  }

  def endOfVoyage(state:GameState) {
    this.den.foreach((p:Pirate) => p.endOfVoyageActivity(state, this))
  }

  def nightActivity(state:GameState) {
    this.den.foreach((p:Pirate) => p.nightActivity(state, this))
  }

  // Various AI strategies need to extend this class and fill out what this method should do I guess.
  // Humans should just ping for input from the player.
  def playCard():Pirate
  
  // Generic method to solicit a choice from a player / AI
  // Takes a prompt and expects an int response
  def makeDecision(decisionPrompt:String):Int
  
}