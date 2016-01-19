package rockpaperscissors.domain

import rockpaperscissors.domain.GameResult.GameResult
import rockpaperscissors.domain.GameSymbol.GameSymbol

import scala.util.Random

case class Result(playerMove: GameSymbol, computerMove: GameSymbol, result: GameResult)

class GameService(gameHistoryRepository: GameHistoryRepository) {

  val game = new Game()

  def play(playerMove: GameSymbol): Result = {
    val computerMove = GameSymbol(Random.nextInt(3))
    val gameResult = game.play(playerMove, computerMove)

    val result = Result(playerMove, computerMove, gameResult)
    gameHistoryRepository.save(result)

    result
  }

  def history() = gameHistoryRepository.list()
}
