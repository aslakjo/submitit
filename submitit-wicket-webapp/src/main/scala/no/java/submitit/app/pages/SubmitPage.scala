package no.java.submitit.app.pages

import org.apache.wicket.markup.html.panel.FeedbackPanel
import org.apache.wicket.model._
import no.java.submitit.model._
import org.apache.wicket.PageParameters
import org.apache.wicket.markup.ComponentTag
import org.apache.wicket.markup.html.form._
import org.apache.wicket.markup.html.panel.FeedbackPanel
import org.apache.wicket.model.PropertyModel
import org.apache.wicket.util.value.ValueMap
import org.apache.wicket.markup.html.link._
import no.java.submitit.app._
import common.Implicits._


class SubmitPage extends LayoutPage {
  
  val state = State.get
  state setCaptchaIfNotSet
  val captcha = State.get.captcha

  /** Random captcha password to match against. */
  val (pres, isNew) = 
    if (state.currentPresentation == null) {
      val p = new Presentation
      p.init
      state.currentPresentation = p
      (state.currentPresentation, true)
    } else {
      (state.currentPresentation, false)
    } 
  
    
  def password = getRequest.getParameter("password");
  
  add(new Form("inputForm") with EasyForm {
    
    val verified = state.verifiedWithCaptha
    
    add(new FeedbackPanel("feedback"))
    addPropTF("title", pres, "title")
    addPropTA("theabstract", pres, "abstr")
    addPropTF("duration", pres, "duration")
    addPropTA("equipment",pres, "equipment")
    addPropTA("requiredExperience", pres, "requiredExperience")
    addPropTA("expectedAudience", pres, "expectedAudience")
    addPropRC("level", pres, "level", Level.elements.toList)
    addPropRC("language", pres, "language", Language.elements.toList)
    
    if (!verified) add(captcha.image)
    
    add(new TextField("password", new PropertyModel(captcha, "password")){
      override def isVisible = !verified
    })
    
    private class ReviewLink(id: String, form: Form) extends SubmitLink(id, form){
      override def onSubmit() { 
          handleSubmit
      }
    }
    
    add(new ReviewLink("reviewButtonTop", this))
    add(new ReviewLink("reviewButtonBottom", this))
    
    add(new SubmitLink("captchaButton", this){
      override def onSubmit()  {
        setupCatcha
      }
    })
    
    add(new SpeakerPanel(pres))
    
    def handleSubmit() {
      // Some form validation
      if (!state.verifiedWithCaptha && captcha.imagePass != password) error("Wrong captcha password")
      
      required(pres.speakers, "You must specify at least one speaker")
      required(pres.title, "You must specify a title")
      required(pres.abstr, "You must specify an abstract")
      
      pres.speakers.foreach(sp => {
        required(sp.name, "You must specify an a name")
        required(sp.email, "You must specify an email")
        required(sp.bio, "You must specify an bio")
      })
      
      if(!hasErrorMessage) {
        state.verifiedWithCaptha = true
        setResponsePage(classOf[ReviewPage])
      }
    }
    
  })
  

  def setupCatcha {
    state.resetCaptcha
    setResponsePage(classOf[SubmitPage])
  }
    
}
