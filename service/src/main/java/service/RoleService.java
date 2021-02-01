package service;

import dao.model.Role;

public interface RoleService {
    String getRoleEntryById(String id) throws Exception;
    int addRole(Role role) throws Exception;
}
