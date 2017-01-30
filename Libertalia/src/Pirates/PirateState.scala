package Pirates

  object PirateState extends Enumeration {
      type State = Value
      val NOT_IN_PLAY, HAND, IN_PLAY, DEN, DISCARD, OUT_OF_PLAY = Value
    }