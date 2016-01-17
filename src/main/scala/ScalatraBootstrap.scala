import org.scalatra._
import javax.servlet.ServletContext

import rockpaperscissors.domain.GameService
import rockpaperscissors.web.GameServlet

class ScalatraBootstrap extends LifeCycle {

  override def init(context: ServletContext) {

    val gameService = new GameService

    context.mount(new GameServlet(gameService), "/*")
  }
}
