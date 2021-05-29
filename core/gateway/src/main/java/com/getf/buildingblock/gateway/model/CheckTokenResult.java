package com.getf.buildingblock.gateway.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.util.List;

@Data
public class CheckTokenResult {
    private boolean active;
    private Long exp;
    @JsonProperty("user_name")
    private String userName;
    private String jti;
    @JsonProperty("client_id")
    private String clientId;
    private List<String> scope;
    private String error;
    @JsonProperty("error_description")
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

/*{"active":true,"exp":1622279503,"user_name":"admin","jti":"7cc58c5b-168b-4868-8233-61e50524ce3f","client_id":"app","scope":["all"]}*/
