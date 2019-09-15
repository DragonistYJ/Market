package love.dragoinst.market.service;

import love.dragoinst.market.Mapper.ProductMapper;
import love.dragoinst.market.bean.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductMapper productMapper;

    public int deleteProductById(int id) {
        return productMapper.deleteProductById(id);
    }

    public int getMaxId() {
        return productMapper.getMaxId();
    }

    public int countProductNoInventory() {
        return productMapper.countProductNoInventory();
    }

    public int countInventoryByCategory(String category) {
        return productMapper.countInventoryByCategory(category);
    }

    public int countAllProduct() {
        return productMapper.countAllProduct();
    }

    public int countInventoryByCategoryAndCrowed(String category, String crowed) {
        return productMapper.countInventoryByCategoryAndCrowed(category, crowed);
    }

    public int countInventoryByCrowed(String crowed) {
        return productMapper.countInventoryByCrowed(crowed);
    }

    public List<Product> getProductsByName(String name) {
        return productMapper.getProductsByName(name.toUpperCase());
    }

    public List<Product> getAllProducts() {
        return productMapper.getAllProducts();
    }

    public Product getProductById(int id) {
        return productMapper.getProductById(id);
    }

    public int updateProductById(int id, String name, int inventory, double price, String description, String size, String brand, String category, String crowed) {
        return productMapper.updateProductById(id, name, inventory, price, description, size, brand, category, crowed);
    }

    public int insertProduct(String name, int inventory, double price, String description, String size, String brand, String category, String crowed) {
        int tmp = productMapper.insertProduct(name, inventory, price, description, size, brand, category, crowed);
        if (tmp <= 0) return -1;
        List<Integer> ids = productMapper.getProductIdNullPicURL();
        for (Integer integer : ids) {
            productMapper.setProductPicURLById(integer, "img/product/product" + integer + "/product-" + integer);
        }
        return 1;
    }
}
