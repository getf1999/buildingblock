package com.getf.buildingblock.gateway.model;

import lombok.Data;

@Data
public class CheckTokenResult {
    private boolean active;
    private Long exp;
    private String userName;
    private String jti;
    private String clientId;
    private String scope;
    private String error;
    private String errorDescription;
    /*
      "error": "invalid_token",
    "error_description": "Cannot convert access token to JSON"

    {
    "active": true,
    "exp": 1621939447,
    "user_name": "admin",
    "jti": "bfa7d732-6f52-4134-86e0-a2925e9d80ad",
    "client_id": "app",
    "scope": [
        "all"
    ]
}*/
}
