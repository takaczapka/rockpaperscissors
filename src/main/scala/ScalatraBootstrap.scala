import rockpaperscissors.{RealMagicService, HelloWorldServlet}
import org.scalatra._
import javax.servlet.ServletContext

class ScalatraBootstrap extends LifeCycle {

  override def init(context: ServletContext) {

    val magicService = new RealMagicService

    context.mount(new HelloWorldServlet(magicService), "/*")
  }
}
