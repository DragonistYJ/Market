package love.dragoinst.market.controller;

import com.google.gson.Gson;
import love.dragoinst.market.Mapper.ProductMapper;
import love.dragoinst.market.bean.Administrator;
import love.dragoinst.market.bean.Product;
import love.dragoinst.market.service.AdministratorService;
import love.dragoinst.market.service.ProductService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private HttpSession session;
    @Autowired
    private AdministratorService administratorService;
    @Autowired
    private Gson gson = new Gson();

    @PostMapping("/product/name")
    @ResponseBody
    public String getProductsByName(@RequestParam String name) {
        List<Product> products = productService.getProductsByName(name);
        return gson.toJson(products);
    }

    @PostMapping("/product/productList")
    @ResponseBody
    public String getBriefProducts(Boolean top, Boolean bottom, Boolean shoe, Boolean acc, Boolean male, Boolean female, Boolean teenager, Boolean child, Boolean have, Boolean no, Double minPrice, Double maxPrice) {
        List<Product> products = productService.getAllProducts();
        Iterator<Product> iterator = products.iterator();
        while (iterator.hasNext()) {
            Product product = iterator.next();
            if (top != null && product.getCategory().equals("top") && !top) iterator.remove();
            else if (bottom != null && product.getCategory().equals("bottom") && !bottom) iterator.remove();
            else if (shoe != null && product.getCategory().equals("shoe") && !shoe) iterator.remove();
            else if (acc != null && product.getCategory().equals("acc") && !acc) iterator.remove();
            else if (male != null && product.getCrowed().equals("male") && !male) iterator.remove();
            else if (female != null && product.getCrowed().equals("female") && !female) iterator.remove();
            else if (teenager != null && product.getCrowed().equals("teenager") && !teenager) iterator.remove();
            else if (child != null && product.getCrowed().equals("child") && !child) iterator.remove();
            else if (no != null && product.getInventory() == 0 && !no) iterator.remove();
            else if (have != null && product.getInventory() > 0 && !have) iterator.remove();
            else if (minPrice != null && product.getPrice() < minPrice) iterator.remove();
            else if (maxPrice != null && product.getPrice() > maxPrice) iterator.remove();
        }

        JSONArray array = new JSONArray(products);
        return array.toString();
    }

    @PostMapping("/product/pieCategory")
    @ResponseBody
    public String pieCategory() {
        JSONObject object = new JSONObject();
        List<Integer> number = new ArrayList<>();
        number.add(productService.countInventoryByCategory("top"));
        number.add(productService.countInventoryByCategory("bottom"));
        number.add(productService.countInventoryByCategory("shoe"));
        number.add(productService.countInventoryByCategory("acc"));
        object.put("category", number);
        return object.toString();
    }

    @PostMapping("/product/pieCrowed")
    @ResponseBody
    public String pieCrowed() {
        JSONObject object = new JSONObject();
        List<Integer> number = new ArrayList<>();
        number.add(productService.countInventoryByCrowed("male"));
        number.add(productService.countInventoryByCrowed("female"));
        number.add(productService.countInventoryByCrowed("teenager"));
        number.add(productService.countInventoryByCrowed("child"));
        object.put("crowed", number);
        return object.toString();
    }

    @PostMapping("/product/barChart")
    @ResponseBody
    public String barChart() {
        JSONObject object = new JSONObject();
        List<Integer> number = new ArrayList<>();
        number.add(productService.countInventoryByCategoryAndCrowed("top", "male"));
        number.add(productService.countInventoryByCategoryAndCrowed("bottom", "male"));
        number.add(productService.countInventoryByCategoryAndCrowed("shoe", "male"));
        number.add(productService.countInventoryByCategoryAndCrowed("acc", "male"));
        object.put("male", number);
        number.clear();
        number.add(productService.countInventoryByCategoryAndCrowed("top", "female"));
        number.add(productService.countInventoryByCategoryAndCrowed("bottom", "female"));
        number.add(productService.countInventoryByCategoryAndCrowed("shoe", "female"));
        number.add(productService.countInventoryByCategoryAndCrowed("acc", "female"));
        object.put("female", number);
        number.clear();
        number.add(productService.countInventoryByCategoryAndCrowed("top", "teenager"));
        number.add(productService.countInventoryByCategoryAndCrowed("bottom", "teenager"));
        number.add(productService.countInventoryByCategoryAndCrowed("shoe", "teenager"));
        number.add(productService.countInventoryByCategoryAndCrowed("acc", "teenager"));
        object.put("teenager", number);
        number.clear();
        number.add(productService.countInventoryByCategoryAndCrowed("top", "child"));
        number.add(productService.countInventoryByCategoryAndCrowed("bottom", "child"));
        number.add(productService.countInventoryByCategoryAndCrowed("shoe", "child"));
        number.add(productService.countInventoryByCategoryAndCrowed("acc", "child"));
        object.put("child", number);
        return object.toString();
    }

    @GetMapping("/product")
    public String productModel(Model model) {
        String account = (String) session.getAttribute("account");
        String password = (String) session.getAttribute("password");
        if (account == null) return "redirect:login";
        Administrator admin = administratorService.getAdmin(account);
        if (admin == null) return "redirect:login";
        if (!admin.getPassword().equals(password)) return "redirect:login";

        return "product";
    }
}
