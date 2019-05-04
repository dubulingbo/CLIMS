package cn.clims.service.mail;

public interface MailService {
	public void sendMsg(String mailTo,String msg)throws Exception;
}
