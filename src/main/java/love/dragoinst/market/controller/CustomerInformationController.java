package love.dragoinst.market.controller;

import love.dragoinst.market.bean.Administrator;
import love.dragoinst.market.service.AdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class CustomerInformationController {
    @Autowired
    private HttpSession session;
    @Autowired
    private AdministratorService administratorService;

    @GetMapping("/customerInformation")
    public String customerInformationModel(Model model) {
        String account = (String) session.getAttribute("account");
        String password = (String) session.getAttribute("password");
        if (account == null) return "redirect:login";
        Administrator admin = administratorService.getAdmin(account);
        if (admin == null) return "redirect:login";
        if (!admin.getPassword().equals(password)) return "redirect:login";

        return "customerInformation";
    }
}
