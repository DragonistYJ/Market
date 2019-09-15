package love.dragoinst.market.controller;

import love.dragoinst.market.bean.Administrator;
import love.dragoinst.market.service.AdministratorService;
import love.dragoinst.market.service.BuyerService;
import love.dragoinst.market.service.OrderService;
import love.dragoinst.market.service.ProductService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private BuyerService buyerService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;
    @Autowired
    private AdministratorService administratorService;
    @Autowired
    private HttpSession session;

    @PostMapping("index/countByCategory")
    @ResponseBody
    public String countProducts(@RequestParam boolean top, @RequestParam boolean bottom, @RequestParam boolean shoe, @RequestParam boolean acc) {


        JSONObject object = new JSONObject();
        if (top) object.put("top", productService.countInventoryByCategory("top"));
        if (bottom) object.put("bottom", productService.countInventoryByCategory("bottom"));
        if (shoe) object.put("shoe", productService.countInventoryByCategory("shoe"));
        if (acc) object.put("acc", productService.countInventoryByCategory("acc"));
        return object.toString();
    }

    @PostMapping("index/orderNumbers")
    @ResponseBody
    public String getFailOrderNumber(String toDate, Integer length) {
        if (toDate == null || length == null) return new JSONObject().toString();

        List<String> dates = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, Integer.parseInt(toDate.split("-")[0]));
        calendar.set(Calendar.MONTH, Integer.parseInt(toDate.split("-")[1]) - 1);
        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(toDate.split("-")[2]));
        for (int i = 0; i < length; i++) {
            dates.add((calendar.get(Calendar.MONTH) + 1) + "." + calendar.get(Calendar.DAY_OF_MONTH));
            calendar.add(Calendar.DAY_OF_MONTH, -1);
        }
        Collections.reverse(dates);

        List<Integer> fails = orderService.getFailOrderNumbersByDate(toDate, length);
        List<Integer> success = orderService.getSuccessOrderNumbersByDate(toDate, length);
        JSONObject object = new JSONObject();
        object.put("fail", fails);
        object.put("success", success);
        object.put("dates", dates);
        return object.toString();
    }

    @GetMapping("index/countProductNoInventory")
    @ResponseBody
    public int countProductNoInventory() {
        return productService.countProductNoInventory();
    }

    @ResponseBody
    @PostMapping("index/countSuccessOrder")
    public int countSuccessOrder(String fromDate, String toDate) {
        if (fromDate == null && toDate == null) {
            return orderService.countSuccessOrder();
        } else if (fromDate == null) {
            return orderService.countSuccessOrderToDate(toDate);
        } else if (toDate == null) {
            return orderService.countSuccessOrderFromDate(fromDate);
        } else {
            return orderService.countSuccessOrderBetweenDate(fromDate, toDate);
        }
    }

    @ResponseBody
    @PostMapping("index/countFailOrder")
    public int countFailOrder(String fromDate, String toDate) {
        if (fromDate == null && toDate == null) {
            return orderService.countFailOrder();
        } else if (fromDate == null) {
            return orderService.countFailOrderToDate(toDate);
        } else if (toDate == null) {
            return orderService.countFailOrderFromDate(fromDate);
        } else {
            return orderService.countFailOrderBetweenDate(fromDate, toDate);
        }
    }

    @ResponseBody
    @GetMapping("index/countAllProduct")
    public int countAllProduct() {
        return productService.countAllProduct();
    }

    @ResponseBody
    @GetMapping("index/countTransitingOrder")
    public int countTransitingOrder() {
        return orderService.countTransitingOrder();
    }

    @ResponseBody
    @GetMapping("index/countBuyer")
    public int countBuyer() {
        return buyerService.countBuyer();
    }

    @GetMapping("index")
    public String indexModel(Model model) {
        String account = (String) session.getAttribute("account");
        String password = (String) session.getAttribute("password");
        if (account == null) return "redirect:login";
        Administrator admin = administratorService.getAdmin(account);
        if (admin == null) return "redirect:login";
        if (!admin.getPassword().equals(password)) return "redirect:login";

        return "index";
    }
}
