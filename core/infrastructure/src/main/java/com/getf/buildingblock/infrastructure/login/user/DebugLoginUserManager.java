package com.getf.buildingblock.infrastructure.login.user;


public class DebugLoginUserManager implements LoginUserManager {
    private static final LoginUser loginUser=new DebugLoginUser();

    @Override
    public LoginUser getUser() {
        return loginUser;
    }
}
