package rockpaperscissors.domain

import scala.util.Random

object GameFixtures {

  def anyGameSymbol = GameSymbol(Random.nextInt(3))

  def anyGameResult = {
    val vals = GameResult.values.toIndexedSeq
    vals(Random.nextInt(vals.size))
  }

  def anyResult = Result(anyGameSymbol, anyGameSymbol, anyGameResult)
}
