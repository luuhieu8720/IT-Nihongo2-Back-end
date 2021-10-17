package bkdn.pbl6.main.services;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import bkdn.pbl6.main.models.Account;

@Service
public class MailServiceImpl implements MailService {

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private TemplateEngine templateEngine;

	@Override
	public boolean sendHello(String email) {
		Context context = new Context();
		context.setVariable("email", email);
		String html = templateEngine.process("mail/hello.html", context);
		MimeMessage mailMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mailMessage);
		try {
			helper.setSubject("Hello java mail sender");
			helper.setTo(email);
			helper.setText(html, true);
			javaMailSender.send(mailMessage);
		} catch (MessagingException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean sendSignupMail(Account account) {
		Context context = new Context();
		context.setVariable("account", account);
		String html = templateEngine.process("mail/signup.html", context);
		MimeMessage mailMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mailMessage);
		try {
			helper.setSubject("Welcome");
			helper.setFrom("noreply@itnihongo.com");
			helper.setTo(account.getEmail());
			helper.setText(html, true);
			javaMailSender.send(mailMessage);
		} catch (MessagingException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	@Override
	public boolean sendNewPasswordMail(Account account) {
		Context context = new Context();
		context.setVariable("account", account);
		String html = templateEngine.process("mail/newpassword.html", context);
		MimeMessage mailMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mailMessage);
		try {
			helper.setSubject("Reset Password");
			helper.setFrom("noreply@itnihongo.com");
			helper.setTo(account.getEmail());
			helper.setText(html, true);
			javaMailSender.send(mailMessage);
		} catch (MessagingException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
