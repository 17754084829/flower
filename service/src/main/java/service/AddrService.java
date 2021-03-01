package service;

import dao.model.Addr;

import java.util.List;

public interface AddrService {
    List<Addr> getAddrs(String user_id);
    int addAddr(Addr addr);
}
