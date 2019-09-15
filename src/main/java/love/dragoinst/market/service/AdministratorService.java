package love.dragoinst.market.service;

import love.dragoinst.market.Mapper.AdministratorMapper;
import love.dragoinst.market.bean.Administrator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdministratorService {
    @Autowired
    private AdministratorMapper administratorMapper;

    public Administrator getAdmin(String account) {
        return administratorMapper.getAdmin(account);
    }
}
