package no.java.submitit.app.pages

import org.apache.wicket.markup.html.panel.Panel
import org.apache.wicket.extensions.validation.validator.RfcCompliantEmailAddressValidator
import org.apache.wicket.ajax.markup.html.form._
import org.apache.wicket.markup.html.form._
import org.apache.wicket.markup.html.list._
import org.apache.wicket.model.PropertyModel
import model._
import Implicits._

class SpeakerPanel(speakers: List[Speaker], f: Form) extends Panel("speakers") {

  add(new SubmitLink("newSpeaker", f) {
    override def onSubmit {
      State.get.currentPresentation.addSpeaker()
      setResponsePage(classOf[SubmitPage])
    }
  })
  
  add(new ListView("speakerList",  speakers.reverse) {
    override def populateItem(item: ListItem) {
      val speaker = item.getModelObject.asInstanceOf[Speaker]
      item.add(new TextField("speakerName", new PropertyModel(speaker, "name")))
      val email = new TextField("email", new PropertyModel(speaker, "email"))
      email.add(RfcCompliantEmailAddressValidator.getInstance())
      item.add(email)
      item.add(new TextArea("bio", new PropertyModel(speaker, "bio")))
      
      item.add(new SubmitLink("remove", f) {
        override def onSubmit {
          State.get.currentPresentation.removeSpeaker(speaker)
          setResponsePage(classOf[SubmitPage])
        }	
      })	
    }
  })
}