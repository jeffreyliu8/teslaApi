package com.askjeffreyliu.teslaapi;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class LoginAccessTokenViewModel extends ViewModel {

    private LiveData<String> accessTokenLiveData;

    private static final String GRANT_TYPE = "password";
    private static final String OWNERAPI_CLIENT_ID = "81527cff06843c8634fdc09e8ac0abefb46ac849f38fe1e431c2ef2106796384";
    private static final String OWNERAPI_CLIENT_SECRET = "c7257eb71a564034f9419ee651c7d0e5f7aa6bfbd18bafb5c5c033b093bb2fa3";

    private ProjectEndpoint projectRepository;

    public LoginAccessTokenViewModel() {
        projectRepository = new ProjectEndpoint();
        accessTokenLiveData = new MutableLiveData<>();
    }

    public void getAccessTokenLiveData(String email, String password) {
        accessTokenLiveData = projectRepository.getToken(GRANT_TYPE, OWNERAPI_CLIENT_ID, OWNERAPI_CLIENT_SECRET, email, password);
    }

    public LiveData<String> getLiveData() {
        return accessTokenLiveData;
    }
}