package life.inha.icemarket.service;

import life.inha.icemarket.domain.User;

public interface EmailService {
//    String sendSimpleMessage(String to)throws Exception;

    String sendSimpleMessage(String to, Boolean isSignup) throws Exception;

    String CreateEmailKey(User user) throws Exception;

    String loadEmailKey(String email) throws Exception;
}
