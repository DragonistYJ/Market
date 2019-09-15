package love.dragoinst.market.Mapper;

import love.dragoinst.market.bean.Administrator;
import org.apache.ibatis.annotations.Select;

public interface AdministratorMapper {
    @Select("select * from administrator where account = #{account}")
    Administrator getAdmin(String account);
}
