// 임시 컨트롤러입니다. 사용자 정보를 출력하고 권한에 따라 인증된 사용자만 접속 가능한 홈페이지 입니다.
// jwt를 Bearer Token으로 전송하면 회원 정보가 출력됩니다.

package life.inha.icemarket.controller;

import life.inha.icemarket.domain.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WhoAmIController {
    @ResponseBody
    @GetMapping("/whoami")
    public String whoami(@AuthenticationPrincipal User user) {
        String info =
                "Email : " + user.getEmail() + "\nId : " + user.getId() + "\nRole : " + user.getAuthorities();
        return info;
    }

    @ResponseBody
    @GetMapping("/onlyuser")
    public String onlyuser(@AuthenticationPrincipal User user) {
        return "You (" + user.getEmail() + ") are user. Your user.getAuthorities() is : " + user.getAuthorities();
    }

    @ResponseBody
    @GetMapping("/onlyadmin")
    public String onlyadmin(@AuthenticationPrincipal User user) {
        return "You (" + user.getEmail() + ") are admin. Your user.getAuthorities() is : " + user.getAuthorities();
    }
}
