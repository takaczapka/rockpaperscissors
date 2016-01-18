package rockpaperscissors.domain

trait GameHistoryRepository {

  def save(result: Result)

  def list(): Seq[Result]
}

class InMemoryGameHistoryRepository extends GameHistoryRepository {

  private val items = collection.mutable.ListBuffer[Result]()

  override def save(result: Result) = items.prepend(result)

  override def list(): Seq[Result] = items
}
