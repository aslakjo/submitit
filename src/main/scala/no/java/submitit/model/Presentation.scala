package no.java.submitit.model

import Language._
import Level._

import no.java.submitit.model._
import scala.xml.NodeSeq

class Presentation {
  
  var title: String = ""
  var speakers: List[Speaker] = Nil
  var abstr: String = ""
  var outline: String = ""
  var description: String = ""
  var language: Language.Value = Norwegian
  var level: Level.Value = _
  var duration: Int = _
  var equipment: String = ""
  var requiredExperience: String = ""
  var expectedAudience: String = ""
 
  def init() {
    addSpeaker
  }
  
  def addSpeaker() {
    speakers = new Speaker :: speakers
  }
  
  def removeSpeaker(s: Speaker) {
    speakers = removeSpeaker(s, speakers)
  }
  
  private def removeSpeaker(s: Speaker, speakers: List[Speaker]): List[Speaker] = {
    speakers match {
      case speaker :: xs => if (s == speaker) xs else speaker :: removeSpeaker(s, xs)
      case Nil => Nil
    }
  }
   
}

object Presentation {

  def apply(title: String, 
            speakers: List[Speaker], 
            abstr: String, 
            outline: String, 
            description: String, 
            language: Language.Value,
            level: Level.Value,
            duration: Int,
            equipment: String,
            requiredExperience: String,
            expectedAudience: String): Presentation = {
			    val p = new Presentation
			    p.title = title
			    p.speakers = speakers
			    p.abstr = abstr
			    p.outline = outline
			    p.description = description
			    p.language = language
			    p.level = level
			    p.duration = duration
			    p.equipment = equipment
			    p.requiredExperience = requiredExperience
			    p.expectedAudience = expectedAudience
			    p
            }
}
                   
