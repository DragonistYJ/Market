package love.dragoinst.market.Mapper;

import love.dragoinst.market.bean.Buyer;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface BuyerMapper {
    @Delete("delete from buyer where b_id =#{id}")
    int deleteBuyer(int id);

    @Select("select count(*) from buyer")
    int countBuyer();

    @Results({
            @Result(property = "bId", column = "b_id"),
            @Result(property = "name", column = "name"),
            @Result(property = "password", column = "password"),
            @Result(property = "level", column = "level"),
            @Result(property = "gender", column = "gender"),
            @Result(property = "location", column = "location"),
            @Result(property = "headURL", column = "head_url"),
            @Result(property = "idNumber", column = "id_number"),
            @Result(property = "vip", column = "vip"),
            @Result(property = "birthday", column = "birthday"),
            @Result(property = "phone", column = "phone"),
            @Result(property = "description", column = "description"),
    })
    @Select("select * from buyer where upper(name) like " + "'%" + "${name}" + "%'")
    List<Buyer> getBuyersByName(String name);

    @Results({
            @Result(property = "bId", column = "b_id"),
            @Result(property = "name", column = "name"),
            @Result(property = "password", column = "password"),
            @Result(property = "level", column = "level"),
            @Result(property = "gender", column = "gender"),
            @Result(property = "location", column = "location"),
            @Result(property = "headURL", column = "head_url"),
            @Result(property = "idNumber", column = "id_number"),
            @Result(property = "vip", column = "vip"),
            @Result(property = "birthday", column = "birthday"),
            @Result(property = "phone", column = "phone"),
            @Result(property = "description", column = "description"),
    })
    @Select("select * from buyer")
    List<Buyer> getAllBuyers();
}
