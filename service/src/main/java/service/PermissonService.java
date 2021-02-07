package service;

import dao.model.Permission;

public interface PermissonService {
    String getPermissionListByUserID(String uid) throws Exception;
    int addPermission(Permission permission) throws Exception;
}
