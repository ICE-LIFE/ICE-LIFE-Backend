package life.inha.icemarket.service;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@RequiredArgsConstructor
@Getter
@Service
public class EmailServiceImpl implements EmailService{
    private final JavaMailSender javaMailSender;

    private final String emailPW = createKey();

    public MimeMessage createMessage(String to) throws Exception{
        System.out.println("Send to : " + to);
        System.out.println("Code : " + emailPW);
        MimeMessage message = javaMailSender.createMimeMessage();

        message.addRecipients(Message.RecipientType.TO, to);
        message.setSubject("ICE LIFE 회원 인증코드");

        String msgg = "";
        msgg+= "<div style='margin:100px;'>";
        msgg+= "<h1> 안녕하세요 인하대학교 정보통신공학과 ICE-LIFE 입니다. </h1>";
        msgg+= "<br>";
        msgg+= "<p>아래 코드를 회원가입 창으로 돌아가 입력해주세요<p>";
        msgg+= "<br>";
        msgg+= "<p>감사합니다!<p>";
        msgg+= "<br>";
        msgg+= "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg+= "<h3 style='color:blue;'>회원가입 인증 코드입니다.</h3>";
        msgg+= "<div style='font-size:130%'>";
        msgg+= "CODE : <strong>";
        msgg+= emailPW+"</strong><div><br/> ";
        msgg+= "</div>";
        message.setText(msgg, "utf-8", "html");//내용

        message.setFrom(new InternetAddress("imdongle123.gmail.com","ICE-LIFE"));//보내는 사람

        return message;
    }
    public static String createKey() {
        return "1234";
    }

    @Override
    public String sendSimpleMessage(String to) throws Exception {
       MimeMessage message = createMessage(to);
       try{
           javaMailSender.send(message);
       }catch(MailException es){
           es.printStackTrace();
           throw new IllegalArgumentException();
       }
       return emailPW;
    }
}
