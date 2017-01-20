package Pirates

import Driver.GameState
import Entities.Player

/*
 * This defines all of the things that a good pirate must be able to do.
 */

trait Pirate {
   def dayActivity(state: GameState, owningPlayer: Player)
   def nightActivity(state: GameState, owningPlayer: Player)
   def endOfVoyageActivity(state: GameState, owningPlayer: Player)
   def getRank():Int
}