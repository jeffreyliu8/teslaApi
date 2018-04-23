package com.askjeffreyliu.teslaapi;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;




public class ProjectListViewModel extends ViewModel {

    private final LiveData<String> accessTokenLiveData;
    private static final String EMAIL = "test@gmail.com";
    private static final String PASSWORD = "test";

    private static final String OWNERAPI_CLIENT_ID = "81527cff06843c8634fdc09e8ac0abefb46ac849f38fe1e431c2ef2106796384";
    private static final String OWNERAPI_CLIENT_SECRET = "c7257eb71a564034f9419ee651c7d0e5f7aa6bfbd18bafb5c5c033b093bb2fa3";


    public ProjectListViewModel() {
        ProjectEndpoint projectRepository = new ProjectEndpoint();
        accessTokenLiveData = projectRepository.getToken("password", OWNERAPI_CLIENT_ID, OWNERAPI_CLIENT_SECRET, EMAIL, PASSWORD);
    }

    public LiveData<String> getLiveData() {
        return accessTokenLiveData;
    }
}