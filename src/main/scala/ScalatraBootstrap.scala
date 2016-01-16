import rockpaperscissors.{RealMagicService, GameServlet}
import org.scalatra._
import javax.servlet.ServletContext

class ScalatraBootstrap extends LifeCycle {

  override def init(context: ServletContext) {

    val magicService = new RealMagicService

    context.mount(new GameServlet(magicService), "/*")
  }
}
