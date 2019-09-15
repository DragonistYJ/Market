package love.dragoinst.market.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import love.dragoinst.market.bean.Administrator;
import love.dragoinst.market.bean.BriefOrder;
import love.dragoinst.market.service.AdministratorService;
import love.dragoinst.market.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class OrderInformationController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private HttpSession session;
    @Autowired
    private AdministratorService administratorService;

    private Gson gson = new GsonBuilder().setDateFormat("YYYY-MM-dd HH:mm:ss").create();

    @PostMapping("/orderInformation/update")
    @ResponseBody
    public String updateState(@RequestParam int id, @RequestParam String state) {
        if (!state.equals("success") && !state.equals("fail") && !state.equals("transiting")) return "fail";
        int tmp = orderService.updateStateById(id, state);
        if (tmp >= 0) return "success";
        else return "fail";
    }

    @PostMapping("/orderInformation/brief")
    @ResponseBody
    public String getBriefOrder(@RequestParam int id) {
        BriefOrder briefOrder = orderService.getBriefOrderById(id);
        briefOrder.setAllPrice(briefOrder.getQuantity() * briefOrder.getPrice());
        return gson.toJson(briefOrder);
    }

    @GetMapping("/orderInformation")
    public String orderInformation(Model model) {
        String account = (String) session.getAttribute("account");
        String password = (String) session.getAttribute("password");
        if (account == null) return "redirect:login";
        Administrator admin = administratorService.getAdmin(account);
        if (admin == null) return "redirect:login";
        if (!admin.getPassword().equals(password)) return "redirect:login";

        return "orderInformation";
    }
}
