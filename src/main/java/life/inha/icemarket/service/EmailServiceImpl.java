package life.inha.icemarket.service;


import life.inha.icemarket.domain.User;
import life.inha.icemarket.respository.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Random;

@RequiredArgsConstructor
@Getter
@Service
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;
    private final UserRepository userRepository;

    private String emailKey;

    public MimeMessage createMessage(String to, Boolean isSignup) throws Exception {
        emailKey = loadEmailKey(to);
        System.out.println("Send to : " + to);
        System.out.println("Code : " + emailKey);
        MimeMessage message = javaMailSender.createMimeMessage();

        message.addRecipients(Message.RecipientType.TO, to);
        message.setSubject("ICE LIFE 회원 인증코드");
        String msgg = "";
        if (isSignup) {
            msgg += "<div style='margin:100px;'>";
            msgg += "<h1> 안녕하세요 인하대학교 정보통신공학과 ICE-LIFE 입니다. </h1>";
            msgg += "<br>";
            msgg += "<p>아래 코드를 회원가입 창으로 돌아가 입력해주세요<p>";
            msgg += "<br>";
            msgg += "<p>감사합니다!<p>";
            msgg += "<br>";
            msgg += "<div align='center' style='border:1px solid black; font-family:verdana';>";
            msgg += "<h3 style='color:blue;'>회원가입 인증 코드입니다.</h3>";
            msgg += "<div style='font-size:130%'>";
            msgg += "CODE : <strong>";
            msgg += emailKey + "</strong><div><br/> ";
            msgg += "</div>";
        } else {
            msgg += "<div style='margin:100px;'>";
            msgg += "<h1> 안녕하세요 인하대학교 정보통신공학과 ICE-LIFE 입니다. </h1>";
            msgg += "<br>";
            msgg += "<p>아래 코드를 비밀번호 초기화 창으로 돌아가 입력해주세요<p>";
            msgg += "<br>";
            msgg += "<p>감사합니다!<p>";
            msgg += "<br>";
            msgg += "<div align='center' style='border:1px solid black; font-family:verdana';>";
            msgg += "<h3 style='color:blue;'>비밀번호 초기화 인증 코드입니다.</h3>";
            msgg += "<div style='font-size:130%'>";
            msgg += "CODE : <strong>";
            msgg += emailKey + "</strong><div><br/> ";
            msgg += "</div>";
        }

        message.setText(msgg, "utf-8", "html");//내용

        message.setFrom(new InternetAddress("imdongle123.gmail.com", "ICE-LIFE"));//보내는 사람

        return message;
    }

    @Override
    public String sendSimpleMessage(String to, Boolean isSignup) throws Exception {
        MimeMessage message = createMessage(to, isSignup);
        try {
            javaMailSender.send(message);
        } catch (MailException es) {
            es.printStackTrace();
            throw new IllegalArgumentException();
        }
        return emailKey;
    }

    public String CreateEmailKey(User user) throws Exception {
        int certCharLength = 8;
        char[] characterTable = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
                'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
                'Y', 'Z', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0'};
        Random random = new Random(System.currentTimeMillis());
        int tablelength = characterTable.length;
        StringBuffer buf = new StringBuffer();

        for (int i = 0; i < certCharLength; i++) {
            buf.append(characterTable[random.nextInt(tablelength)]);
        }
        String key = buf.toString();
        user.setEmailconfirmkey(key);
        userRepository.save(user);
        return key;
    }

    public String loadEmailKey(String email) throws Exception {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("No user @ EmailServiceImpl"));
        return user.getEmailconfirmkey();
    }
}
