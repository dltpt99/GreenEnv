package nt.greenenv.greenenv.controller;

import nt.greenenv.greenenv.domain.member.Member;
import nt.greenenv.greenenv.domain.member.MemberNewForm;
import nt.greenenv.greenenv.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class MemberController {

    private MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/member/new")
    public String TestSignupPage() {
        return "/members/TEST_signForm";
    }

    @PostMapping("/member/new")
    public String signup(MemberNewForm form) {
        try {
            memberService.signup(form);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

        return "redirect:/";
    }

    @GetMapping("/member/login")
    public String loginPage(HttpServletRequest request, Model model) {
        if (request.getAttribute("error)") != null) {
            model.addAttribute("error", request.getAttribute("error").toString());
        }
        return "/members/TEST_loginForm";
    }

    @PostMapping("/member/login")
    public String login() {
        return "/members/TEST_loginForm.html";
    }

    @GetMapping("/member/list")
    @ResponseBody
    public List<Member> showMemberList() {
        return memberService.getAllMember();
    }
}
