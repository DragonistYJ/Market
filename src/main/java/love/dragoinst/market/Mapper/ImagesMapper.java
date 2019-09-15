package love.dragoinst.market.Mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;

public interface ImagesMapper {
    @Insert("insert into images(sp_id,img_1,img_2,img_3,img_4) values (#{id},#{img1},#{img2},#{img3},#{img4})")
    int insertImages(int id, String img1, String img2, String img3, String img4);

    @Update("update images set img_1 = #{img} where sp_id = #{id}")
    int updateImg1(int id, String img);

    @Update("update images set img_2 = #{img} where sp_id = #{id}")
    int updateImg2(int id, String img);

    @Update("update images set img_3 = #{img} where sp_id = #{id}")
    int updateImg3(int id, String img);

    @Update("update images set img_4 = #{img} where sp_id = #{id}")
    int updateImg4(int id, String img);

    @Delete("delete from images where sp_id = #{id}")
    int deleteImages(int id);
}
