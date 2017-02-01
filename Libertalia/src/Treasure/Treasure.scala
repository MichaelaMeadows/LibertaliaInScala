package Treasure

class Treasure(treasureType:TreasureType.Value) {
  
  
  /*
   * AFAIK, Scala lacks an elegant way to encapsulate values in an enum. That makes me sad I guess.
   */
  def getValue():Int = {
    if (this.treasureType == TreasureType.BARREL) {
      return 1;
    } else if (this.treasureType == TreasureType.JEWELS) {
      return 3;
    } else if (this.treasureType == TreasureType.CHEST) {
      return 5;
    } else if (this.treasureType == TreasureType.CURSED) {
      System.out.println("LOSE MONEY");
      return -3;
    }
    /*
     * Other types have no value.
     */
    return 0;
  }
  
  def getType():TreasureType.Value = {
    return this.treasureType;
  }
  
}
