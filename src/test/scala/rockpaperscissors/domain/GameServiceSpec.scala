package rockpaperscissors.domain

import org.specs2.Specification
import org.specs2.mock.Mockito
import org.specs2.specification.core.SpecStructure
import rockpaperscissors.domain.GameSymbol._

import rockpaperscissors.domain.GameFixtures._

class GameServiceSpec extends Specification with Mockito {
  override def is: SpecStructure =
    s2"""
    computer moves are rather random        $computerRandomMoves
    game results follow the game rules      $gameRulesAreFollowed
    game session is saved into a repository $gameSessionSaved
    """

  val gameService = new GameService(mock[GameHistoryRepository])

  private def computerRandomMoves = {
    val computerMoves = (1 to 100).map(_ => gameService.play(Paper).computerMove)
    computerMoves must contain(Rock, Paper, Scissors)
  }

  private def gameRulesAreFollowed = {
    val results = (1 to 100).map(_ => gameService.play(anyGameSymbol))
    val game = new Game()
    results.map { r => game.play(r.playerMove, r.computerMove) must_== r.result }
  }

  private def gameSessionSaved = {
    val gameHistoryRepository = new InMemoryGameHistoryRepository
    val gameService = new GameService(gameHistoryRepository)

    gameService.history() must_== Seq.empty

    val playerMove1 = GameFixtures.anyGameSymbol
    val playerMove2 = GameFixtures.anyGameSymbol

    gameService.play(playerMove1)
    val results1 = gameService.history()
    results1.size must_== 1
    results1.head.playerMove must_== playerMove1

    gameService.play(playerMove1)
    val results2 = gameService.history()
    results2.size must_== 2
    results2(0).playerMove must_== playerMove2
    results2(1).playerMove must_== playerMove1
  }

}
