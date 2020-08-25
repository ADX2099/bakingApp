package com.adx2099.bakingapp.connection;

import static com.adx2099.bakingapp.helper.BakingConstants.BASE_URL;

public class ApiUtils {
    public ApiUtils(){

    }

    public static APIService getApiService(){
        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }

    public static APIService getPlainAAPIService(){
        return RetrofitClient.getClientPlain(BASE_URL).create(APIService.class);

    }


}
