package no.java.submitit.app.pages

import org.apache.wicket.markup.html.WebPage
import org.apache.wicket.markup.html.link._
import no.java.submitit.common._
import no.java.submitit.model._
import no.java.submitit.app.State
import org.apache.wicket.markup.html.basic._
import org.apache.wicket.markup.html.link._
import org.apache.wicket.protocol.http.servlet.ServletWebRequest
import _root_.java.util.{Date,Properties}
import javax.servlet.http.HttpServletRequest
import javax.mail.{Message,Session,Transport}
import javax.mail.internet.MimeMessage

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class ConfirmPage extends LayoutPage {
  
  val state = State.get
  state.fromServer = true

  val backendClient = state.backendClient
  
  val pres = state.currentPresentation
  val logger = LoggerFactory.getLogger(classOf[ConfirmPage])
  
  val presentation = pres.toString
  logger.info(presentation)
  
  add(new MultiLineLabel("pres", presentation))
  
  val uniqueId = backendClient.savePresentation(pres)
  
  val request = getRequest.asInstanceOf[ServletWebRequest].getHttpServletRequest
  val url = "http://" + request.getServerName + ":" + 
    request.getServerPort + request.getContextPath + 
    "/lookupPresentation?id=" + uniqueId
  
  add(new ExternalLink("confirmUrl", url, url))
  
  add(new NewPresentationLink("newPresentation"))

  if(SubmititApp.boolSetting("sendEmail")) sendConfirmationMail(pres, url)
  
  def sendConfirmationMail(pres: Presentation, url: String) {
    val props = new Properties
    props.put("mail.smtp.host", "smtp")
    props.put("mail.from", "program@java.no")
    val session = Session.getInstance(props, null)
    
    val msg = new MimeMessage(session)
    msg.setFrom
    pres.speakers.foreach(speaker => 
      msg.addRecipients(Message.RecipientType.TO, speaker.email)
    )
    msg.setSubject("Confirmation of JavaZone 2009 submission \"" + pres.title + "\"")
    msg.setSentDate(new Date)
    msg.setText("Thank you for submitting your presentation titled \"" + pres.title + "\"!\n\n" +
                  "You can access the submitted presentation at " + url + "\n\n\n" +
                  "JavaZone Programme Committee\n\n\n\n" +
    "The following has been registered:\n" + pres)
    
    Transport.send(msg)
  }
  
}
