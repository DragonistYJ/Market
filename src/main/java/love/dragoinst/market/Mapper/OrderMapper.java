package love.dragoinst.market.Mapper;

import love.dragoinst.market.bean.BriefOrder;
import love.dragoinst.market.bean.Order;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface OrderMapper {
    @Results({
            @Result(property = "productName", column = "pName"),
            @Result(property = "spId", column = "sp_id"),
            @Result(property = "size", column = "size"),
            @Result(property = "price", column = "price"),
            @Result(property = "brand", column = "brand"),
            @Result(property = "origin", column = "origin"),
            @Result(property = "destination", column = "destination"),
            @Result(property = "company", column = "devCom"),
            @Result(property = "expressNumber", column = "expressNum"),
            @Result(property = "buyerName", column = "bName"),
            @Result(property = "birthday", column = "birthday"),
            @Result(property = "location", column = "location"),
            @Result(property = "phone", column = "phone"),
            @Result(property = "gender", column = "gender"),
            @Result(property = "createTime", column = "creTime"),
            @Result(property = "endTime", column = "preTime"),
            @Result(property = "quantity", column = "quantity"),
            @Result(property = "state", column = "state"),
            @Result(property = "buyerImg", column = "head_url"),
            @Result(property = "productImg", column = "img_1")
    })
    @Select("select img_1,head_url,product.sp_id,size,quantity,state,product.name as pName,price,brand,origin,destination,devCom,expressNum,buyer.name as bName,birthday,location,phone,gender,creTime,preTime from orders,product,buyer,images where orders.b_id = buyer.b_id and orders.sp_id = product.sp_id and images.sp_id = orders.sp_id and o_id = #{id}")
    BriefOrder getBriefOrderById(int id);

    @Results({
            @Result(property = "oId", column = "o_id"),
            @Result(property = "bId", column = "b_id"),
            @Result(property = "buyerName", column = "bName"),
            @Result(property = "spId", column = "sp_id"),
            @Result(property = "productName", column = "pName"),
            @Result(property = "quantity", column = "quantity"),
            @Result(property = "createTime", column = "creTime"),
            @Result(property = "state", column = "state"),
            @Result(property = "price", column = "price"),
            @Result(property = "img", column = "img_1")
    })
    @Select("select img_1,o_id, orders.sp_id, orders.b_id, buyer.name as bName, product.name as pName, quantity, creTime, state, price from orders,product,buyer,images where orders.b_id = buyer.b_id and orders.sp_id = product.sp_id and images.sp_id = orders.sp_id and upper(product.name) like " + "'%" + "${product}" + "%'")
    List<Order> getOrdersByProduct(String product);

    @Results({
            @Result(property = "oId", column = "o_id"),
            @Result(property = "bId", column = "b_id"),
            @Result(property = "buyerName", column = "bName"),
            @Result(property = "spId", column = "sp_id"),
            @Result(property = "productName", column = "pName"),
            @Result(property = "quantity", column = "quantity"),
            @Result(property = "createTime", column = "creTime"),
            @Result(property = "state", column = "state"),
            @Result(property = "price", column = "price"),
            @Result(property = "img", column = "img_1")
    })
    @Select("select img_1,o_id, orders.sp_id, orders.b_id, buyer.name as bName, product.name as pName, quantity, creTime, state, price from orders,product,buyer,images where orders.b_id = buyer.b_id and orders.sp_id = product.sp_id and images.sp_id = orders.sp_id and upper(buyer.name) like " + "'%" + "${buyer}" + "%'")
    List<Order> getOrdersByBuyer(String buyer);

    @Results({
            @Result(property = "oId", column = "o_id"),
            @Result(property = "bId", column = "b_id"),
            @Result(property = "buyerName", column = "bName"),
            @Result(property = "spId", column = "sp_id"),
            @Result(property = "productName", column = "pName"),
            @Result(property = "quantity", column = "quantity"),
            @Result(property = "createTime", column = "creTime"),
            @Result(property = "state", column = "state"),
            @Result(property = "price", column = "price"),
            @Result(property = "img", column = "img_1")
    })
    @Select("select img_1, o_id, orders.sp_id, orders.b_id, buyer.name as bName, product.name as pName, quantity, creTime, state, price from orders,product,buyer,images where orders.b_id = buyer.b_id and orders.sp_id = product.sp_id and images.sp_id = orders.sp_id")
    List<Order> getAllOrders();

    @Update("update orders set state = #{state} where o_id = #{id}")
    int updateStateById(int id, String state);

    @Select("select count(*) from orders where state = 'fail' and creTime >= #{fromDate}")
    int countFailOrderFromDate(String date);

    @Select("select count(*) from orders where state = 'fail' and creTime <= #{toDate}")
    int countFailOrderToDate(String date);

    @Select("select count(*) from orders where state = 'fail' and creTime >= #{fromDate} and creTime <= #{toDate}")
    int countFailOrderBetweenDate(String fromDate, String toDate);

    @Select("select count(*) from orders where state = 'fail'")
    int countFailOrder();

    //从date开始到现在的所有成功订单
    @Select("select count(*) from orders where state = 'success' and creTime >= #{fromDate}")
    int countSuccessOrderFromDate(String date);

    //从建立系统开始到date的所有成功订单
    @Select("select count(*) from orders where state = 'success' and creTime <= #{toDate}")
    int countSuccessOrderToDate(String date);

    @Select("select count(*) from orders where state = 'success' and creTime >= #{fromDate} and creTime <= #{toDate}")
    int countSuccessOrderBetweenDate(String fromDate, String toDate);

    //全部成功订单
    @Select("select count(*) from orders where state = 'success'")
    int countSuccessOrder();

    @Select("select count(*) from orders where state = 'transiting'")
    int countTransitingOrder();
}
