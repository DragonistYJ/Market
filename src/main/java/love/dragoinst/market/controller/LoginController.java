package love.dragoinst.market.controller;

import love.dragoinst.market.bean.Administrator;
import love.dragoinst.market.service.AdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
    @Autowired
    private AdministratorService administratorService;
    @Autowired
    private HttpSession session;

    @PostMapping("/login/login")
    @ResponseBody
    public String login(@RequestParam String account, @RequestParam String password) {
        Administrator admin = administratorService.getAdmin(account);
        if (admin == null) return "fail";
        if (!admin.getPassword().equals(password)) return "fail";
        session.setAttribute("account", account);
        session.setAttribute("password", password);
        return "success";
    }

    @GetMapping("/login")
    public String loginModel() {
        return "login";
    }

    @GetMapping("/")
    public String defaultModel() {
        return "login";
    }
}
