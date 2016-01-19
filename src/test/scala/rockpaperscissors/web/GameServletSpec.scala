package rockpaperscissors.web

import org.scalatra.test.specs2._
import rockpaperscissors.domain.{InMemoryGameHistoryRepository, GameService}

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
      """

  addServlet(new GameServlet(new GameService(new InMemoryGameHistoryRepository)), "/*")

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

  val containRegex = (regex: String) => beMatching("(?s).*" + regex + ".*")
}
