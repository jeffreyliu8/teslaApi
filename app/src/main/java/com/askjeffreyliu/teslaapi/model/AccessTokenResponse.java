package com.askjeffreyliu.teslaapi.model;

public class AccessTokenResponse {
    private String access_token;
    private String token_type;
    private long expires_in;
    private long created_at;
    private String refresh_token;

    public String getAccess_token() {
        return access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public long getExpires_in() {
        return expires_in;
    }

    public long getCreated_at() {
        return created_at;
    }

    public String getRefresh_token() {
        return refresh_token;
    }
}
