package rockpaperscissors.domain

import org.specs2.Specification
import org.specs2.specification.core.SpecStructure
import rockpaperscissors.domain.GameSymbol._

import scala.util.Random

class GameServiceSpec extends Specification {
  override def is: SpecStructure = s2"""
    computer moves are rather random       $computerRandomMoves
    game results follow the game rules     $gameRulesAreFollowed
    """
  val gameService = new GameService

  def computerRandomMoves = {
    val computerMoves = (1 to 100).map(_ => gameService.play(Paper).computerMove)
    computerMoves must contain(Rock, Paper, Scissors)
  }

  def gameRulesAreFollowed = {
    val results = (1 to 100).map(_ => gameService.play(anyGameSymbol))
    val game = new Game()
    results.map { r => game.play(r.playerMove, r.computerMove) must_== r.result }
  }

  private def anyGameSymbol = GameSymbol(Random.nextInt(3))
}
