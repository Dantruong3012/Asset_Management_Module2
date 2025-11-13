package com.asset_management.dantruong.user_services;

import java.util.Map;

public interface IUserService {
    Map<String, Users> loadUser();
    boolean saveUser(Map<String, Users> users);
}
