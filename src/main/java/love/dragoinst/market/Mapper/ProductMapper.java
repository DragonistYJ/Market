package love.dragoinst.market.Mapper;

import love.dragoinst.market.bean.Product;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ProductMapper {
    @Select("select count(*) from product where inventory = 0")
    int countProductNoInventory();

    @Select("select ifnull(sum(inventory),0) from product where category = #{category}")
    int countInventoryByCategory(String category);

    @Select("select sum(inventory) from product")
    int countAllProduct();

    @Select("select ifnull(sum(inventory),0) from product where category = #{category} and crowed = #{crowed}")
    int countInventoryByCategoryAndCrowed(String category, String crowed);

    @Select("select ifnull(sum(inventory),0) from product where crowed = #{crowed}")
    int countInventoryByCrowed(String crowed);

    @Results({
            @Result(property = "spId", column = "sp_id"),
            @Result(property = "name", column = "name"),
            @Result(property = "picURL", column = "pic_url"),
            @Result(property = "inventory", column = "inventory"),
            @Result(property = "price", column = "price"),
            @Result(property = "description", column = "description"),
            @Result(property = "size", column = "size"),
            @Result(property = "brand", column = "brand"),
            @Result(property = "category", column = "category"),
            @Result(property = "crowed", column = "crowed")
    })
    @Select("select * from product where upper(name) like " + "'%" + "${name}" + "%'")
    List<Product> getProductsByName(String name);

    @Results({
            @Result(property = "spId", column = "sp_id"),
            @Result(property = "name", column = "name"),
            @Result(property = "picURL", column = "pic_url"),
            @Result(property = "inventory", column = "inventory"),
            @Result(property = "price", column = "price"),
            @Result(property = "description", column = "description"),
            @Result(property = "size", column = "size"),
            @Result(property = "brand", column = "brand"),
            @Result(property = "category", column = "category"),
            @Result(property = "crowed", column = "crowed")
    })
    @Select("select * from product")
    List<Product> getAllProducts();

    @Results({
            @Result(property = "spId", column = "spId"),
            @Result(property = "name", column = "name"),
            @Result(property = "picURL", column = "pic_url"),
            @Result(property = "inventory", column = "inventory"),
            @Result(property = "price", column = "price"),
            @Result(property = "description", column = "description"),
            @Result(property = "size", column = "size"),
            @Result(property = "brand", column = "brand"),
            @Result(property = "category", column = "category"),
            @Result(property = "crowed", column = "crowed"),
            @Result(property = "img1", column = "img_1"),
            @Result(property = "img2", column = "img_2"),
            @Result(property = "img3", column = "img_3"),
            @Result(property = "img4", column = "img_4"),
    })
    @Select("select product.sp_id as spId,name,pic_url,inventory,price,description,size,brand,category,crowed,img_1,img_2,img_3,img_4 from product,images where product.sp_id = images.sp_id and product.sp_id = #{id}")
    Product getProductById(int id);

    @Update("update product set name = #{name}, inventory = #{inventory}, price = #{price}, description = #{description}, size = #{size}, brand = #{brand}, category = #{category}, crowed = #{crowed} where sp_id = #{id}")
    int updateProductById(int id, String name, int inventory, double price, String description, String size, String brand, String category, String crowed);

    @Insert("insert product(name,inventory,price,description,size,brand,category,crowed) values (#{name},#{inventory},#{price},#{description},#{size},#{brand},#{category},#{crowed})")
    int insertProduct(String name, int inventory, double price, String description, String size, String brand, String category, String crowed);

    @Update("update product set pic_url = #{url} where sp_id = #{id}")
    int setProductPicURLById(int id, String url);

    @Select("select sp_id from product where pic_url is null")
    List<Integer> getProductIdNullPicURL();

    @Select("select max(sp_id) from product")
    int getMaxId();

    @Delete("delete from product where sp_id = #{id}")
    int deleteProductById(int id);
}
