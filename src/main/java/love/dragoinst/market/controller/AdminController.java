package love.dragoinst.market.controller;

import com.google.gson.Gson;
import love.dragoinst.market.bean.Administrator;
import love.dragoinst.market.service.AdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class AdminController {
    @Autowired
    private HttpSession session;
    @Autowired
    private AdministratorService administratorService;

    private Gson gson = new Gson();

    @PostMapping("/adminInfo")
    @ResponseBody
    public String getAdminInfo() {
        String account = (String) session.getAttribute("account");
        String password = (String) session.getAttribute("password");
        if (account == null) return "fail";
        Administrator admin = administratorService.getAdmin(account);
        if (admin == null) return "fail";
        if (!admin.getPassword().equals(password)) return "fail";

        return gson.toJson(admin);
    }
}
