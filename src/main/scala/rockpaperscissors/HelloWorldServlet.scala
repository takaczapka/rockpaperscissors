package rockpaperscissors

import org.scalatra._
import scalate.ScalateSupport

import scala.util.Random
import scala.xml.{Text, NodeSeq}

class HelloWorldServlet extends RockPaperScissorsStack {

  def html(message: NodeSeq = NodeSeq.Empty) =
    <html lang="en">
      <head>
        <title>Bootstrap Example</title>
        <meta charset="utf-8"></meta>
        <meta name="viewport" content="width=device-width, initial-scale=1">
          <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css"/>
          <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
          <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
        </meta>
      </head>
      <body>

        <div class="container">
          <h2>Choose</h2>
          <form action="/game" method="post">
            <div class="btn-group">
              <button type="submit" name="choice" value="0" class="btn btn-primary">Rock</button>
              <button type="submit" name="choice" value="1" class="btn btn-primary">Paper</button>
              <button type="submit" name="choice" value="2" class="btn btn-primary">Scissors</button>
            </div>
          </form>{message}
        </div>
      </body>
    </html>


  get("/game") {
    html()
  }

  post("/game") {
    val computer = Random.nextInt(3)
    val me = params("choice").toInt
    val res = if (computer == me) {
      "Draw"
    } else {
      if (computer + 1 == me) {
        "You won"
      } else {
        "Computer won"
      }
    }

    html(<h3>
      {res}
    </h3> <h4>
      You:
      {gameSymbols(me)}
      vs Computer:
      {gameSymbols(computer)}
    </h4>)
  }

  object GameSymbol extends Enumeration {
    type GameSymbol = Value

    val Rock = Value
    val Paper = Value
    val Scissors = Value
  }


  import GameSymbol._

  val gameSymbols = Seq(Rock, Paper, Scissors)

  /*
  get("/") {
    <html>
      <body>
        <h1>Hello, world!</h1>
        Say <a href="hello-scalate">hello to Scalate</a>.
      </body>
    </html>
  } */
}
