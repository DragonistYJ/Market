package love.dragoinst.market.service;

import love.dragoinst.market.Mapper.ImagesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImagesService {
    @Autowired
    private ImagesMapper imagesMapper;

    public int deleteImages(int id) {
        return imagesMapper.deleteImages(id);
    }

    public int updateImg1(int id, String img) {
        return imagesMapper.updateImg1(id, img);
    }

    public int updateImg2(int id, String img) {
        return imagesMapper.updateImg2(id, img);
    }

    public int updateImg3(int id, String img) {
        return imagesMapper.updateImg3(id, img);
    }

    public int updateImg4(int id, String img) {
        return imagesMapper.updateImg4(id, img);
    }

    public int insertImages(int id, String img1, String img2, String img3, String img4) {
        return imagesMapper.insertImages(id, img1, img2, img3, img4);
    }
}
