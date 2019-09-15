package love.dragoinst.market.controller;

import com.google.gson.Gson;
import love.dragoinst.market.bean.Administrator;
import love.dragoinst.market.bean.Buyer;
import love.dragoinst.market.service.AdministratorService;
import love.dragoinst.market.service.BuyerService;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

@Controller
public class CustomerController {
    @Autowired
    private BuyerService buyerService;
    @Autowired
    private HttpSession session;
    @Autowired
    private AdministratorService administratorService;

    private Gson gson = new Gson();

    @PostMapping("/customer/delete")
    @ResponseBody
    public String deleteBuyer(@RequestParam int id) {
        int tmp = buyerService.deleteBuyer(id);
        if (tmp > 0) return "success";
        else return "fail";
    }

    @PostMapping("/customer/name")
    @ResponseBody
    public String getBuyersByName(@RequestParam String name) {
        List<Buyer> buyers = buyerService.getBuyersByName(name);
        return gson.toJson(buyers);
    }

    @PostMapping("/customer/all")
    @ResponseBody
    public String getCustomers(Boolean male, Boolean female, Boolean isVIP, Boolean notVIP, Boolean L0, Boolean L1, Boolean L2, Boolean L3, Boolean L4, Boolean L5, Integer fromAge, Integer toAge) {
        List<Buyer> buyers = buyerService.getAllBuyers();
        Iterator<Buyer> iterator = buyers.iterator();
        int year = Calendar.getInstance().get(Calendar.YEAR);
        while (iterator.hasNext()) {
            Buyer buyer = iterator.next();
            int age = buyer.getBirthday() == null ? 20 : year - Integer.parseInt(buyer.getBirthday().toString().split("-")[0]);
            if (male != null && !male && buyer.getGender() == 0) iterator.remove();
            else if (female != null && !female && buyer.getGender() == 1) iterator.remove();
            else if (isVIP != null && !isVIP && buyer.getVip() == 1) iterator.remove();
            else if (notVIP != null && !notVIP && buyer.getVip() == 0) iterator.remove();
            else if (L0 != null && !L0 && buyer.getLevel() == 0) iterator.remove();
            else if (L1 != null && !L1 && buyer.getLevel() == 1) iterator.remove();
            else if (L2 != null && !L2 && buyer.getLevel() == 2) iterator.remove();
            else if (L3 != null && !L3 && buyer.getLevel() == 3) iterator.remove();
            else if (L4 != null && !L4 && buyer.getLevel() == 4) iterator.remove();
            else if (L5 != null && !L5 && buyer.getLevel() == 5) iterator.remove();
            else if (fromAge != null && age < fromAge) iterator.remove();
            else if (toAge != null && age > toAge) iterator.remove();
        }
        return gson.toJson(buyers);
    }

    @GetMapping("/customer")
    public String customerModel(Model model) {
        String account = (String) session.getAttribute("account");
        String password = (String) session.getAttribute("password");
        if (account == null) return "redirect:login";
        Administrator admin = administratorService.getAdmin(account);
        if (admin == null) return "redirect:login";
        if (!admin.getPassword().equals(password)) return "redirect:login";

        return "customer";
    }
}
