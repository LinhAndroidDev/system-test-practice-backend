package net.javaguides.springboot.service;

import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.MimeMessage;
import net.javaguides.springboot.dto.EmailRequest;
import net.javaguides.springboot.utils.Constants;
import net.javaguides.springboot.utils.GmailOAuth2Authenticator;
import net.javaguides.springboot.utils.OAuth2Authenticator;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Properties;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private GmailOAuth2Authenticator auth;

    @Autowired
    private TemplateEngine templateEngine;

    public void sendHtmlMail(EmailRequest req) throws Exception {
        // Get access token
        String accessToken = auth.getAccessToken();

        JavaMailSenderImpl sender = (JavaMailSenderImpl) mailSender;

        // Tạo context cho Thymeleaf
        Context context = getContext(req);

        // Render HTML từ template
        String html = templateEngine.process("email_template.html", context);

        // Tạo Properties object mới với đầy đủ cấu hình
        Properties props = new Properties();
        
        // Copy properties từ sender (nếu có)
        props.putAll(sender.getJavaMailProperties());

        // Đảm bảo host và port được set đúng
        String host = sender.getHost() != null ? sender.getHost() : "smtp.gmail.com";
        int port = sender.getPort() > 0 ? sender.getPort() : 587;
        
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", String.valueOf(port));
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.starttls.required", "true");
        
        // Timeout settings
        props.put("mail.smtp.connectiontimeout", "10000");
        props.put("mail.smtp.timeout", "10000");
        props.put("mail.smtp.writetimeout", "10000");
        
        // Cấu hình OAuth2
        props.put("mail.smtp.auth.mechanisms", "XOAUTH2");
        props.put("mail.smtp.sasl.enable", "true");
        props.put("mail.smtp.sasl.mechanisms", "XOAUTH2");
        props.put("mail.smtp.auth.login.disable", "true");
        props.put("mail.smtp.auth.plain.disable", "true");
        
        // Debug (có thể bật để xem log)
        // props.put("mail.debug", "true");

        // Tạo Session với Properties đã được cấu hình đúng
        Session session = Session.getInstance(
                props,
                new OAuth2Authenticator(Constants.GMAIL_SENDER, accessToken)
        );

        MimeMessage message = new MimeMessage(session);
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom(Constants.GMAIL_SENDER);
        helper.setTo(req.getTo());
        helper.setSubject(req.getSubject());
        helper.setText(html, true);

        // Thực hiện gửi với error handling tốt hơn
        // Với OAuth2, sử dụng Transport.send() trực tiếp vì authentication đã được xử lý trong Session
        Transport.send(message);
    }

    @NotNull
    private static Context getContext(EmailRequest req) {
        Context context = new Context();
        context.setVariable("title", req.getSubject());
        context.setVariable("username", req.getReceiverName());
        context.setVariable("message", "Cập nhật Quick Practice để trải nghiệm những tính năng mới nhất!.<br>Vui lòng truy cập vào CH Play để cập nhật ứng dụng và nhận những phần quà hấp dẫn.");
        // Link mặc định đến CH Play, có thể override từ EmailRequest nếu cần
        String link = req.getLink() != null && !req.getLink().isEmpty()
            ? req.getLink()
            : "https://play.google.com/store/apps";
        context.setVariable("link", link);
        return context;
    }
}
