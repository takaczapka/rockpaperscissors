package rockpaperscissors.web

import rockpaperscissors.RockPaperScissorsStack
import rockpaperscissors.domain.{GameService, GameSymbol}

import scala.xml.NodeSeq

class GameServlet(gameService: GameService) extends RockPaperScissorsStack {

  get("/game") {
    contentType="text/html"
    html()
  }

  post("/game") {
    val playerChoice = params("choice").toInt % 3

    val result = gameService.play(GameSymbol(playerChoice))

    val res = result.result.toString

    contentType="text/html"

    html(<h3>
      {res}
    </h3> <h4>
      You:
      {result.playerMove.toString}
      vs Computer:
      {result.computerMove.toString}
    </h4>
    <br/>
      <h3>Game History</h3>
      <table id="history" class="table table-striped .table-bordered .table-hover .table-condensed">
        <thead>
        <tr><th>Player</th><th>Computer</th><th>Result</th></tr>
        </thead>
        <tbody>
        {for (i <- gameService.history()) yield {
          <tr><td>{i.playerMove}</td><td>{i.computerMove}</td><td>{i.result}</td></tr>
        }}
        </tbody>
      </table>)
  }

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



  get("/") {
    redirect("/game")
  }
}
