package love.dragoinst.market.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import love.dragoinst.market.bean.Administrator;
import love.dragoinst.market.bean.Order;
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
import java.util.Iterator;
import java.util.List;

@Controller
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private HttpSession session;
    @Autowired
    private AdministratorService administratorService;

    private Gson gson = new GsonBuilder().setDateFormat("YYYY-MM-dd HH:mm:ss").create();

    @PostMapping("/order/product")
    @ResponseBody
    public String getOrdersByProduct(@RequestParam String product) {
        List<Order> orders = orderService.getOrdersByProduct(product);
        return gson.toJson(orders);
    }

    @PostMapping("/order/buyer")
    @ResponseBody
    public String getOrdersByBuyer(@RequestParam String buyer) {
        List<Order> orders = orderService.getOrdersByBuyer(buyer);
        return gson.toJson(orders);
    }

    @PostMapping("/order/all")
    @ResponseBody
    public String getAllOrders(String fromDate, String toDate) {
        List<Order> orders = orderService.getAllOrders();
        Iterator<Order> iterator = orders.iterator();
        while (iterator.hasNext()) {
            Order order = iterator.next();
            if (fromDate != null && order.getCreateTime().compareTo(fromDate) < 0) iterator.remove();
            else if (toDate != null && order.getCreateTime().compareTo(toDate) > 0) iterator.remove();
        }
        for (Order order : orders) {
            order.setPrice(order.getQuantity() * order.getPrice());
        }

        return gson.toJson(orders);
    }

    @GetMapping("/order")
    public String orderModel(Model model) {
        String account = (String) session.getAttribute("account");
        String password = (String) session.getAttribute("password");
        if (account == null) return "redirect:login";
        Administrator admin = administratorService.getAdmin(account);
        if (admin == null) return "redirect:login";
        if (!admin.getPassword().equals(password)) return "redirect:login";

        return "order";
    }
}
