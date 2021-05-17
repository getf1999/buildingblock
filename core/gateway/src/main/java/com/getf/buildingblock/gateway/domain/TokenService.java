package com.getf.buildingblock.gateway.domain;

import com.getf.buildingblock.gateway.domain.model.UserDo;
import com.getf.buildingblock.gateway.domain.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenService {
    @Autowired
    TokenRepository repository;

    public String createToken(Long userId) {
        return repository.createToken(userId);
    }
}
