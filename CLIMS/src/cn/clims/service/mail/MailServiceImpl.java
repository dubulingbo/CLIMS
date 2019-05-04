package cn.clims.service.mail;

import javax.annotation.Resource;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;


@Service
public class MailServiceImpl implements MailService {
	@Resource
	private SimpleMailMessage mailMessage;
	@Resource
	private MailSender mailSender;
	
	@Override
	public void sendMsg(String mailTo, String msg)throws Exception {
		mailMessage.setTo(mailTo);
		mailMessage.setText(msg);
		mailSender.send(mailMessage);
	}

}
