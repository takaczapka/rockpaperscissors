package rockpaperscissors.domain

import org.specs2.Specification
import org.specs2.specification.core.SpecStructure
import rockpaperscissors.domain.GameFixtures._

trait GameHistoryRepositorySpec {
  this: Specification =>

  def createRepository: GameHistoryRepository

  override def is: SpecStructure =
    s2"""
        there's no game results in a new repository                  $noResultsInEmpty
        earlier saved item is on the list retrieved                  $savedItemOnTheList
        items are in the retrieved list according to save sequence   $listFollowSaveSequence
      """

  private def noResultsInEmpty = createRepository.list() must_== Seq.empty[Result]

  private def savedItemOnTheList = {
    val repository = createRepository
    val item = anyResult
    repository.save(item)
    repository.list() === Seq(item)
  }

  private def listFollowSaveSequence = {
    val repository = createRepository
    val items = (1 to 1) map (_ => anyResult)
    items foreach repository.save
    repository.list() === items.reverse
  }
}

class InMemoryGameHistoryRepositorySpec extends Specification with GameHistoryRepositorySpec {

  def createRepository: GameHistoryRepository = new InMemoryGameHistoryRepository
}