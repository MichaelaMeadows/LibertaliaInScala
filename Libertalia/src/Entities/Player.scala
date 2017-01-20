package Entities
import Pirates.Pirate;

class Player(playerNumber:Int) {
  
  var hand:List[Pirate] = List();
  var den:List[Pirate] = List();
  var discard:List[Pirate] = List();
  var loot:List[Treasure] = List();
  var totalScore:Int;
  var currentLoot:Int;
  
  def addCardToHand(pirate:Pirate) {
    hand = hand:+ pirate;
  }
  
}