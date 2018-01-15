package com.zealot.mail.controller.api;

import org.simplejavamail.email.Email;
import org.simplejavamail.mailer.Mailer;
import org.simplejavamail.mailer.config.ServerConfig;
import org.simplejavamail.mailer.config.TransportStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zealot.constants.ResponseStatus;
import com.zealot.mail.controller.BaseController;
import com.zealot.mail.result.ResultResponse;

@Controller
@RequestMapping(value="/mail")
public class MailApi extends BaseController {
	
	private final static Logger logger = LoggerFactory.getLogger(MailApi.class);
	
	/**
	 * If you just want to see what email is being sent, just set this to true. It won't actually connect to an SMTP server then.
	 */
	private static final boolean LOGGING_MODE = false;
	
	
	private static final ServerConfig serverConfigSSL_qq = new ServerConfig("smtp.qq.com", 25, "404235882@qq.com", "lwwospwmiuvwbidf");
	
	/**
	*  - content: 邮件内容
	*  - subject: 邮件标题
	*  - tos: 使用逗号分隔的多个邮件地址
	*/
	@RequestMapping(value="/api")
	@ResponseBody
	public ResultResponse api(Model model, String tos, String subject, String content)
	{
		ResultResponse result = new ResultResponse();
		
		try {
			logger.debug("*****************************mailstart****************************");
			logger.debug("tos="+tos+" : subject="+subject);
			logger.debug("*****************************mailsend*****************************");
			final Email emailNormal = new Email();
			emailNormal.setFromAddress("404235882", "404235882@qq.com");
			// don't forget to add your own address here ->
			String [] receivers = tos.split(",");
			emailNormal.addToRecipients(receivers);
			emailNormal.setText("");
			emailNormal.setTextHTML(content);
			emailNormal.setSubject(subject);
			
			sendMail(emailNormal);
		} catch (Exception e) {
			result.setStatus(ResponseStatus.ERROR);
			result.setErrMsg(e.getMessage());
			logger.error("MailApi.api=========",e);
		}

		return result;
	}
	
	private static void sendMail(final Email email) {
		// ProxyConfig proxyconfig = new ProxyConfig("localhost", 1030);
		sendMail(serverConfigSSL_qq, TransportStrategy.SMTP_TLS, email);
//		sendMail(serverConfigTLS, TransportStrategy.SMTP_TLS, email);
//		sendMail(serverConfigSSL, TransportStrategy.SMTP_SSL, email);
	}

	private static void sendMail(ServerConfig serverConfigSMTP, TransportStrategy smtpTls, Email email) {
		Mailer mailer = new Mailer(serverConfigSMTP, smtpTls);
		mailer.setTransportModeLoggingOnly(LOGGING_MODE);
		mailer.sendMail(email);
	}
}
