package service.impl;

import dao.mapper.PermissionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.PermissonService;
@Service
public class PermissonServiceImpl implements PermissonService {
    @Autowired
    private PermissionDao permissionDao;
    @Override
    public String getPermissionListByUserID(String uid) throws Exception {
        return permissionDao.getPermissionListByUserID(uid);
    }
}
