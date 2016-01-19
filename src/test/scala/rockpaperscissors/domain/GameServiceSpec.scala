package rockpaperscissors.domain

import org.specs2.Specification
import org.specs2.mock.Mockito
import org.specs2.specification.core.SpecStructure
import rockpaperscissors.domain.GameSymbol._

import rockpaperscissors.domain.GameFixtures._

class GameServiceSpec extends Specification with Mockito {
  override def is: SpecStructure =
    s2"""
    computer moves are rather random       $computerRandomMoves
    game results follow the game rules     $gameRulesAreFollowed
    history is retrieved from repository   $gameHistoryFromRepository
    """

  val gameService = new GameService(mock[GameHistoryRepository])

  def computerRandomMoves = {
    val computerMoves = (1 to 100).map(_ => gameService.play(Paper).computerMove)
    computerMoves must contain(Rock, Paper, Scissors)
  }

  def gameRulesAreFollowed = {
    val results = (1 to 100).map(_ => gameService.play(anyGameSymbol))
    val game = new Game()
    results.map { r => game.play(r.playerMove, r.computerMove) must_== r.result }
  }

  def gameHistoryFromRepository = {
    val expectedReturn = Seq(GameFixtures.anyResult, GameFixtures.anyResult)

    val gameHistoryRepository = mock[GameHistoryRepository]
    val gameService = new GameService(gameHistoryRepository)
    gameHistoryRepository.list() returns expectedReturn

    gameService.history() must_== expectedReturn
  }
}
