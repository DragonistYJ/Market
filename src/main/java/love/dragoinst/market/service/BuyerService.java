package love.dragoinst.market.service;

import love.dragoinst.market.Mapper.BuyerMapper;
import love.dragoinst.market.bean.Buyer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuyerService {
    @Autowired
    private BuyerMapper buyerMapper;

    public int countBuyer() {
        return buyerMapper.countBuyer();
    }

    public List<Buyer> getBuyersByName(String name) {
        return buyerMapper.getBuyersByName(name.toUpperCase());
    }

    public List<Buyer> getAllBuyers() {
        return buyerMapper.getAllBuyers();
    }

    public int deleteBuyer(int id) {
        return buyerMapper.deleteBuyer(id);
    }
}
