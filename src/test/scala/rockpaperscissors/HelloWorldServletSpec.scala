package rockpaperscissors

import org.scalatra.test.specs2._

// For more on Specs2, see http://etorreborre.github.com/specs2/guide/org.specs2.guide.QuickStart.html
class HelloWorldServletSpec extends ScalatraSpec {

  def is =
    "GET / on HelloWorldServlet" ^ "should return status 200" ! root200 ^ end

  def aa = "GET / on HelloWorldServlet" ^ "should return status 2001" ! contentType ^ end

  addServlet(new HelloWorldServlet(new MagicService {
    override def doTheMagic(): String = "boom"
  }), "/*")

  def root200 = get("/") {
    status must_== 200
  }

  def contentType = get("/") {
    response.getContentType() must_== "text/html"
  }
}


