package com.getf.buildingblock.infrastructure.login.user;

public class DebugLoginUser implements LoginUser {

    @Override
    public Long getUserId() {
        return 0L;
    }

    @Override
    public String getUserName() {
        return "DEBUG";
    }
}
