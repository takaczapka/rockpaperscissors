package rockpaperscissors.web

import org.scalatra.test.specs2._
import org.specs2.Specification
import org.specs2.specification.core.SpecStructure
import rockpaperscissors.domain._

import scala.xml.Node

class GameServletSpec extends ScalatraSpec {

  def is =
    s2"""
      GET / on GameServlet should
          return redirection status 302          $root302
          return content type of "text/html"     $rootContentType
      GET /game should
          return status 200                      $gameGet200
          return content type of "text/html"     $gameGetContentType
          return content which contains
              words "Rock", "Paper",  "Scissors" $gameGetContent
      POST /game with choice=0 to 2 should
          return status 200                      $gamePost200
          return content type of "text/html"     $gamePostContentType
          return content which contains
              words "Rock", "Paper",  "Scissors" $gamePostContent
      POST /game with choice=0 should
          return "rock" selected                 $gamePostChoice0
      POST /game with choice=1 should
          return "paper" selected                $gamePostChoice1
      POST /game with choice=2 should
          return "scissors" selected             $gamePostChoice2
      POST /game with choice > 2 should
          return a correct page (something is selected) $gamePostChoiceMoreThan2
      POST /game updated game history table      $gamePostUpdatesHistoryTable
      """

  val gameService = new GameService(new InMemoryGameHistoryRepository)
  addServlet(new GameServlet(gameService), "/*")

  private def root302 = get("/") {
    status must_== 302
  }

  private def rootContentType = get("/") {
    response.getContentType() startsWith "text/html;"
  }

  private def gameGet200 = get("/game") {
    status must_== 200
  }

  private def gameGetContentType = get("/game") {
    response.getContentType() startsWith "text/html;"
  }

  private def gameGetContent = get("/game") {
    rockPaperScissorsContentMatcher(body)
  }

  private val rockPaperScissorsContentMatcher = (_content: String) => {
    val content = _content.toLowerCase
    (content must contain("rock")) and
      (content must contain("scissors")) and
      (content must contain("paper"))
  }

  private def gamePost200 = post("/game", "choice" -> "2") {
    status must_== 200
  }

  private def gamePostContentType = post("/game", "choice" -> "2") {
    response.getContentType() must startWith("text/html;")
  }

  private def gamePostContent = post("/game", "choice" -> "2") {
    rockPaperScissorsContentMatcher(body)
  }

  private def gamePostChoice0 = post("/game", "choice" -> "0") {
    body.toLowerCase must containRegex("you:\\s*rock")
  }

  private def gamePostChoice1 = post("/game", "choice" -> "1") {
    body.toLowerCase must containRegex("you:\\s*paper")
  }

  private def gamePostChoice2 = post("/game", "choice" -> "2") {
    body.toLowerCase must containRegex("you:\\s*scissors")
  }

  private def gamePostChoiceMoreThan2 = post("/game", "choice" -> "5") {
    body.toLowerCase must containRegex("you:\\s*[scissors|rock|papper]")
  }

  private def gamePostUpdatesHistoryTable = {
    post("/game", "choice" -> "0") {
      historyTableItems(body)(0).head must_== "Rock"
    }
    post("/game", "choice" -> "1") {
      historyTableItems(body)(0).head must_== "Paper"
    }
    post("/game", "choice" -> "0") {
      historyTableItems(body)(0).head must_== "Rock"
      historyTableItems(body)(1).head must_== "Paper"
    }
  }

  private def historyTableItems(content: String): Seq[Seq[String]] = {
    val html = scala.xml.XML.loadString(content)

    for {
      table <- html \\ "table"
      if (table \ "@id").text == "history"
      tdTextSeq <- table \\ "tbody" \\ "tr" map { n: Node => (n \\ "td").map(_.text) }
    } yield tdTextSeq
  }

  val containRegex = (regex: String) => beMatching("(?s).*" + regex + ".*")
}


class HtmlTableSpec extends Specification {
  override def is: SpecStructure = s2"""whatever with table $tableEq"""

  val html = <html><table id="history">
    <tr><td>1</td><td>2</td><td>3</td></tr>
    <tr><td>4</td><td>5</td><td>6</td></tr>
    <tr><td>7</td><td>8</td><td>9</td></tr>
  </table></html>

  def attributeValueEquals(value: String)(node: Node) = {
    node.attributes.exists(_.value.text == value)
  }

  def tableEq = {
    val r = for {
      table <- html \\ "table"
      if (table \ "@id").text == "history"
      tdTextSeq <- table \\ "tr" map { n: Node => (n \\ "td").map(_.text) }
    } yield tdTextSeq

    println(r)
    ok
  }
}