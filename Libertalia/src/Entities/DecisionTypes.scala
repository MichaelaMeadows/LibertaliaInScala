package Entities

    object DecisionType extends Enumeration {
      type Type = Value
      val PIRATE, TREASURE, PLAYER_TO_ATTACK, PIRATE_TO_ATTACK, BOOTY_TO_SELL, 
      TREASURE_TO_KEEP, RETURN_TO_HAND, SURGEON_POWER, SHOULD_SELL_MAP = Value
    }