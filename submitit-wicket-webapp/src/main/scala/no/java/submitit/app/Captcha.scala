package no.java.submitit.app

import org.apache.wicket.extensions.markup.html.captcha.CaptchaImageResource
import org.apache.wicket.markup.html.image.Image

class Captcha {

  def randomString() = {
    def randomInt(min: Int, max: Int):Int = (Math.random * (max - min)).asInstanceOf[Int] + min
    
    val captchaLength = SubmititApp.intSetting("captchaLengthInt") - 1 
    val res = for(i <- 0 to captchaLength) yield randomInt('a', 'z').asInstanceOf[Byte]
    new String(res.toArray)
  }
  
  val imagePass = randomString()
  
  val image = new Image("captchaImage", new CaptchaImageResource(imagePass))
  
  var password: String = _
  
}
