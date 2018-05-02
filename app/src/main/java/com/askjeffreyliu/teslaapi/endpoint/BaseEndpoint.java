package com.askjeffreyliu.teslaapi.endpoint;


public abstract class BaseEndpoint {

    protected static final String BASE_URL = "https://owner-api.teslamotors.com/";

    protected final String accessToken;

    public BaseEndpoint() {
        accessToken = null;
    }

    protected BaseEndpoint(final String accessToken) {
        this.accessToken = accessToken;
    }
}