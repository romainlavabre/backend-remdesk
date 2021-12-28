package com.remdesk.api.configuration.environment;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
public interface Variable {
    String SERVER_PORT        = "server.port";
    String MAIL_DOMAIN        = "mail.mailgun.domain";
    String MAIL_FROM          = "mail.from";
    String MAIL_PRIVATE_KEY   = "mail.mailgun.private-key";
    String SMS_TWILIO_SID     = "sms.twilio.sid";
    String SMS_PRIVATE_KEY    = "sms.twilio.auth-token";
    String SMS_FROM           = "sms.from";
    String JWT_SECRET         = "jwt.secret";
    String JWT_LIFE_TIME      = "jwt.life-time";
    String BASE_TEMPLATE_PATH = "base.template.path";
    String PDF_TMP_DIRECTORY  = "pdf.tmp.directory";
    String HIDDEN_FILENAME    = "hidden.filename";
}
