package service.impl;

import dao.mapper.RoleDao;
import dao.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.RoleService;
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDao roleDao;
    @Override
    public String getRoleEntryById(String id) throws Exception{
        return roleDao.getRoleEntryById(id);
    }

    @Override
    public int addRole(Role role) throws Exception {
        return roleDao.addRole(role);
    }

    @Override
    public int update(Role role) {
        return roleDao.update(role);
    }
}
