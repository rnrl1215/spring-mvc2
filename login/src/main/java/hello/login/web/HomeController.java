package hello.login.web;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import hello.login.web.argumentresolver.Login;
import hello.login.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberRepository memberRepository;
    private final SessionManager sessionManager;

   // @GetMapping("/")
    public String home() {
        return "home";
    }




    @GetMapping("/")
    // SessionAttribute 해당 name 을 가진 세션을 찾아온다. 생성을 하진 않는다.
    public String loginHomeV3ArgumentResolver(@Login Member member, Model model) {

        if (member == null) {
            return "home";
        }

        model.addAttribute("member", member);

        return "loginHome";
    }


    //@GetMapping("/")
    // SessionAttribute 해당 name 을 가진 세션을 찾아온다. 생성을 하진 않는다.
    public String loginHomeV3Spring(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)Member member, Model model) {

        if (member == null) {
            return "home";
        }

        model.addAttribute("member", member);

        return "loginHome";
    }


    //@GetMapping("/")
    public String loginHomeV3(HttpServletRequest request, Model model) {

        //Member member = (Member) sessionManager.getSession(request);

        HttpSession session = request.getSession(false);

        if (session == null) {
            return "home";
        }


        Member member = (Member)session.getAttribute(SessionConst.LOGIN_MEMBER);

        if (member == null) {
            return "home";
        }


        model.addAttribute("member", member);

        return "loginHome";
    }


    //@GetMapping("/")
    public String loginHomeV2(HttpServletRequest request, Model model) {

        Member member = (Member) sessionManager.getSession(request);


        if (member == null) {
            return "home";
        }

        model.addAttribute("member", member);

        return "loginHome";
    }

    //@GetMapping("/")
    public String loginHome(@CookieValue(name = "memberId", required = false) Long memberId, Model model) {
        if (memberId == null) {
            return "home";
        }

        Member loginMember = memberRepository.findById(memberId);
        if (loginMember == null) {
            return "home";
        }

        model.addAttribute("member", loginMember);

        return "loginHome";
    }
}