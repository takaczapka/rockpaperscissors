package rockpaperscissors.domain

import rockpaperscissors.domain.GameResult.GameResult
import rockpaperscissors.domain.GameSymbol.GameSymbol

import scala.util.Random

case class Result(playerMove: GameSymbol, computerMove: GameSymbol, result: GameResult)

class GameService {

  val game = new Game()

  def play(playerMove: GameSymbol): Result = {

    val computerMove = GameSymbol(Random.nextInt(3))
    val gameResult = game.play(playerMove, computerMove)
    Result(playerMove, computerMove, gameResult)
  }
}
