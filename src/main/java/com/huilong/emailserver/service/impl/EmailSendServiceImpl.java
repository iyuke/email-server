package com.huilong.emailserver.service.impl;


import com.huilong.emailserver.model.MailEntity;
import com.huilong.emailserver.service.EmailSendService;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by 18358 on 2016/11/23.
 */
@Service
public class EmailSendServiceImpl implements EmailSendService {
    protected final Logger logger = LoggerFactory.getLogger(EmailSendServiceImpl.class);
    @Override
    public boolean send(MailEntity mail) {
        // 发送email
        HtmlEmail email = new HtmlEmail();
        try {
            // 这里是SMTP发送服务器的名字：163的如下："smtp.163.com"
            email.setHostName(mail.getHost());
            // 字符编码集的设置
            email.setCharset(MailEntity.ENCODEING);
            // 收件人的邮箱
            email.addTo(mail.getReceiver());
            // 发送人的邮箱
            email.setFrom(mail.getSender(), mail.getName());
            // 如果需要认证信息的话，设置认证：用户名-密码。分别为发件人在邮件服务器上的注册名称和密码
            email.setAuthentication(mail.getUsername(), mail.getPassword());
            // 要发送的邮件主题
            email.setSubject(mail.getSubject());
            // 要发送的信息，由于使用了HtmlEmail，可以在邮件内容中使用HTML标签
            email.setMsg(mail.getMessage());
            // 发送
            email.send();
            if (logger.isDebugEnabled()) {
                logger.debug(mail.getSender() + "发送邮件到" + mail.getReceiver());
            }
            return true;
        } catch (EmailException e) {
            e.printStackTrace();
            logger.info(mail.getSender() + "发送邮件到" + mail.getReceiver() + "失败");
            return false;
        }
    }
}
