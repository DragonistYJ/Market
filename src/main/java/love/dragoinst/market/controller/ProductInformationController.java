package love.dragoinst.market.controller;

import com.google.gson.Gson;
import love.dragoinst.market.bean.Administrator;
import love.dragoinst.market.service.AdministratorService;
import love.dragoinst.market.service.ImagesService;
import love.dragoinst.market.service.ProductService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ProductInformationController {
    @Autowired
    private ProductService productService;
    @Autowired
    private HttpSession session;
    @Autowired
    private AdministratorService administratorService;
    @Autowired
    private ImagesService imagesService;

    private Gson gson = new Gson();

    @PostMapping("/productInfo/getInfo")
    @ResponseBody
    public String getInformation(@RequestParam Integer id) {
        return gson.toJson(productService.getProductById(id));
    }

    @GetMapping("/productInformation")
    public String productInformationModel(Model model) {
        String account = (String) session.getAttribute("account");
        String password = (String) session.getAttribute("password");
        if (account == null) return "redirect:login";
        Administrator admin = administratorService.getAdmin(account);
        if (admin == null) return "redirect:login";
        if (!admin.getPassword().equals(password)) return "redirect:login";

        return "productInformation";
    }

    @PostMapping("/product/update")
    @ResponseBody
    public String updateProduct(@RequestParam int id, @RequestParam String name, @RequestParam int inventory, @RequestParam double price,
                                @RequestParam String description, @RequestParam String size, @RequestParam String brand, @RequestParam String category, @RequestParam String crowed,
                                @RequestParam String img1, @RequestParam String img2, @RequestParam String img3, @RequestParam String img4) {

        int tmp = productService.updateProductById(id, name, inventory, price, description, size, brand, category, crowed);
        if (tmp <= 0) return "fail";
        if (!img1.equals("")) imagesService.updateImg4(id, img1);
        if (!img2.equals("")) imagesService.updateImg3(id, img2);
        if (!img3.equals("")) imagesService.updateImg2(id, img3);
        if (!img4.equals("")) imagesService.updateImg1(id, img4);

        return "success";
    }

    @PostMapping("/product/add")
    @ResponseBody
    public String addProduct(@RequestParam String name, @RequestParam int inventory, @RequestParam double price,
                             @RequestParam String description, @RequestParam String size, @RequestParam String brand, @RequestParam String category, @RequestParam String crowed,
                             @RequestParam String img1, @RequestParam String img2, @RequestParam String img3, @RequestParam String img4) {
        int tmp = productService.insertProduct(name, inventory, price, description, size, brand, category, crowed);
        if (tmp <= 0) return "fail";
        int id = productService.getMaxId();
        imagesService.insertImages(id, img1, img2, img3, img4);
        return "success";
    }

    @PostMapping("/product/delete")
    @ResponseBody
    public String deleteProduct(@RequestParam int id) {
        int tmp = productService.deleteProductById(id);
        if (tmp <= 0) return "fail";
        imagesService.deleteImages(id);
        return "success";
    }
}
