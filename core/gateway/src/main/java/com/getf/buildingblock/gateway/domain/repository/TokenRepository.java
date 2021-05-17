package com.getf.buildingblock.gateway.domain.repository;

import com.getf.buildingblock.gateway.domain.model.UserDo;


public interface TokenRepository {
    public String createToken(Long userId);
}
