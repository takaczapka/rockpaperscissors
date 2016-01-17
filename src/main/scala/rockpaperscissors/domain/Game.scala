package rockpaperscissors.domain

object GameSymbol extends Enumeration {
  type GameSymbol = Value

  val Rock = Value(0)
  val Paper = Value(1)
  val Scissors = Value(2)
}

object GameResult extends Enumeration {
  type GameResult = Value

  val Won = Value
  val Lost = Value
  val Draw = Value
}

class Game {
  import GameResult._
  import GameSymbol._

  def play(player1Move: GameSymbol, player2Move: GameSymbol): GameResult = {
    if (player1Move == player2Move) Draw
    else {
      if ((player1Move.id + 1) % 3 == player2Move.id) Lost
      else Won
    }
  }
}