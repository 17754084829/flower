package service.impl;

import dao.mapper.AddrDao;
import dao.model.Addr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.AddrService;

import java.util.List;
@Service
public class AddrServiceImpl implements AddrService {
    @Autowired
    private AddrDao dao;
    @Override
    public List<Addr> getAddrs(String user_id) {

        return dao.getAddrs(user_id);
    }

    @Override
    public int addAddr(Addr addr) {
        List<Addr> list=getAddrs(addr.getUser_id());
        for(Addr addr1:list)
            if(addr1.getAddr().equals(addr.getAddr()))
                return -1;
        return dao.addAddr(addr);
    }
}
