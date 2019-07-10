package com.yhxc.utils.mail;

import com.yhxc.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.persistence.criteria.CriteriaBuilder;
import java.io.File;
import java.util.Date;
import java.util.Properties;

import static com.yhxc.utils.GetProperties.getPprVue;

/**
 * 邮件发送器
 * @author 赵贺飞
 */
public class SimpleMailSender {
    private static Properties pros = getPprVue("setting/email.properties");

    public static String sendEmail(String toEmail, String title, String content, String attachmentPath){
        String info = "";
        try {
            boolean isSuccess = SimpleMailSender.send(toEmail, title, content, attachmentPath);//调用发送邮件函数
            if (isSuccess) {
                info = "发送成功！";
            } else {
                info = "发送失败！";
            }
        } catch (Exception e) {
            e.printStackTrace();
            info = "发送失败！";
        }
        return info;
    }


    /**
     * @param toEmail
     * @param title
     * @param content
     * @param attachmentPath
     * @throws Exception
     */
    public static boolean send(String toEmail, String title, String content, String attachmentPath) throws Exception {
        boolean isSuccess = true;
        String smtp = pros.getProperty("smtp");
        String portStr = pros.getProperty("port");
        Integer port = null;
        if(StringUtil.isNotEmpty(portStr)){
            port = Integer.parseInt(portStr);
        }
        String fromEmail = pros.getProperty("fromEmail");
        String password = pros.getProperty("password");
                JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(smtp);
        mailSender.setPort(port);
        mailSender.setUsername(fromEmail);
        mailSender.setPassword(password);

        Properties prop = new Properties();
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.timeout", "25000");
        mailSender.setJavaMailProperties(prop);

        MimeMessage m = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(m, true, "GBK");
            helper.setTo(toEmail);
            helper.setFrom(fromEmail);
            helper.setSubject(title);
            helper.setText(content);
            if (StringUtils.isNotBlank(attachmentPath)) {
                File file = new File(attachmentPath);
                FileDataSource source = new FileDataSource(file);
                helper.addAttachment(attachmentPath, source); //添加附件
            }
            /*发送邮件*/
            mailSender.send(m);
        } catch (Exception e) {
            e.printStackTrace();
            isSuccess = false;
        }
        return isSuccess;
    }
}   
