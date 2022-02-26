package otaku.shelterforcowards.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import otaku.shelterforcowards.domain.Member;
import otaku.shelterforcowards.service.MemberService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class MemberController {
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {

        this.memberService = memberService;
        System.out.println("MemberService = " + memberService.getClass());
    }

    @GetMapping("/members/new")
    public String createForm() {
        return "members/createMemberForm";
    }

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();

        // 모델에 담기
        model.addAttribute("members", members);

        return "members/memberList";
    }

    @PostMapping("/members/new")
    public String create(MemberForm form) {
        Member member = new Member();
        member.setName(form.getName());
        member.setPassword(form.getPassword());

        memberService.join(member);

        return "redirect:/";
    }

    // 1. 아이디 검사
    // 2. 패스워드 검사
    // 3. 전부 맞으면 그 멤버를 가져옴


/*
    @GetMapping("/login")
    public String loginCheck(Model model) {
        return "/members/login";
    }*/

    @GetMapping("/myPage")
    public String showInfo(Member member, HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();

        Member loginMember = memberService.findByName(session.getAttribute("name").toString()).get();

        System.out.println("로그인 회원 정보 " + loginMember.getName());
        model.addAttribute("name", loginMember.getName());
        model.addAttribute("level", loginMember.getLevel());
        model.addAttribute("id", loginMember.getId());
        return "/members/myPage";
    }

    @GetMapping("/members/levelUp")
    public String levelUp(Member member, HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();

        Member loginMember = memberService.findByName(session.getAttribute("name").toString()).get();

        loginMember.setLevel(1);

        memberService.update(loginMember);

        model.addAttribute("name", loginMember.getName());
        model.addAttribute("level", loginMember.getLevel());
        model.addAttribute("id", loginMember.getId());

        return "/members/myPage";
    }


    @PostMapping("/login")
    public String loginId(MemberForm form, HttpServletRequest request) {

        Member loginMember = new Member();
        loginMember.setName(form.getName());
        loginMember.setPassword(form.getPassword());

        if(memberService.login(loginMember)) {
            HttpSession session = request.getSession();
            session.setAttribute("name", loginMember.getName());
            return "redirect:/myPage";
        }
        return "redirect:/";
    }

}
