package rockpaperscissors.domain

import org.specs2.Specification
import org.specs2.specification.core.SpecStructure
import rockpaperscissors.domain.GameResult._
import rockpaperscissors.domain.GameSymbol._


class GameSpec extends Specification {
  override def is: SpecStructure =
    s2"""
      paper wins over rock       $paperWinsRock
      rock loses over paper      $rockLosesPaper
      scissors wins over paper   $scissorsWinPaper
      paper loses over scissors  $paperLosesScissors
      rock wins over scissors    $rockWinsScissors
      scissors lose over rock    $scissorsLoseRock
      the same move gives draw   $draw
    """

  val game = new Game()

  private def paperWinsRock = game.play(Paper, Rock) must_== Won

  private def rockLosesPaper = game.play(Rock, Paper) must_== Lost

  private def scissorsWinPaper = game.play(Scissors, Paper) must_== Won

  private def paperLosesScissors = game.play(Paper, Scissors) must_== Lost

  private def rockWinsScissors = game.play(Rock, Scissors) must_== Won

  private def scissorsLoseRock = game.play(Scissors, Rock) must_== Lost

  private def draw = {
    (game.play(Scissors, Scissors) must_== Draw) and
      (game.play(Rock, Rock) must_== Draw) and
      (game.play(Paper, Paper) must_== Draw)
  }
}
