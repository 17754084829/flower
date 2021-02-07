package service.impl;

import common.util.DataVerify;
import dao.mapper.PermissionDao;
import dao.model.Permission;
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

    @Override
    public int addPermission(Permission permission) throws Exception{
        DataVerify.ObjectisNotEmpty(permission);
        return permissionDao.addPermission(permission);
    }
}
