package com.turlington

import java.io.UnsupportedEncodingException
import java.util.*
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

internal object EmailUtil {

    /**
     * Outgoing Mail (SMTP) Server
     * requires TLS or SSL: smtp.gmail.com (use authentication)
     * Use Authentication: Yes
     * Port for TLS/STARTTLS: 587
     */
    fun sendEmail(fromEmail: String, password: String, toEmail: String, jobListing: JobListing) {
        val props = Properties()
        props.put("mail.smtp.host", "smtp.gmail.com") //SMTP Host
        props.put("mail.smtp.port", "587") //TLS Port
        props.put("mail.smtp.auth", "true") //enable authentication
        props.put("mail.smtp.starttls.enable", "true") //enable STARTTLS

        //create Authenticator object to pass in Session.getInstance argument
        val auth = object : Authenticator() {
            //override the getPasswordAuthentication method
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication(fromEmail, password)
            }
        }
        val session = Session.getInstance(props, auth)

        EmailUtil.sendEmail(session, fromEmail, toEmail, jobListing.emailSubject!!, jobListing.emailText)
    }

    /**
     * Utility method to send simple HTML email.
     */
    private fun sendEmail(session: Session, fromEmail: String, toEmail: String, subject: String, body: String) {
        try {
            val msg = MimeMessage(session)
            //set message headers
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8")
            msg.addHeader("format", "flowed")
            msg.addHeader("Content-Transfer-Encoding", "8bit")

            msg.setFrom(InternetAddress(fromEmail, fromEmail))
            msg.replyTo = InternetAddress.parse(fromEmail, false)
            msg.setSubject(subject, "UTF-8")
            msg.setText(body, "UTF-8")
            msg.sentDate = Date()

            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false))
            Transport.send(msg)
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        } catch (e: MessagingException) {
            e.printStackTrace()
        }
    }
}
