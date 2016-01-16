package rockpaperscissors

trait MagicService {

  def doTheMagic(): String
}

class RealMagicService extends MagicService {

  override def doTheMagic(): String = "magic!"
}
