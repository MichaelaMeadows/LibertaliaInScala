package Treasure

  object TreasureType extends Enumeration {
      type Type = Value
      // Nothing should ever use empty. It's just a place holder for states.
      val EMPTY, BARREL, JEWELS, CHEST, MAP, CURSED, OFFICER, SWORD = Value
    }