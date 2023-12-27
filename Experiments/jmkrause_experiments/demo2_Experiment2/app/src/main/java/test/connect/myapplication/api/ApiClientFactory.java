package test.connect.myapplication.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClientFactory {

    static Retrofit apiClientSeed = null;

    static Retrofit GetApiClientSeed() {

        if (apiClientSeed == null) {
            apiClientSeed = new Retrofit.Builder()
                    .baseUrl("https://42961dcd-7614-43d3-a695-3b78518cb5e1.mock.pstmn.io")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return apiClientSeed;
    }


//    public static PostApi GetPostApi(){
//        return GetApiClientSeed().create(PostApi.class);
//    }

    public static PersonApi GetPersonApi() {
        return GetApiClientSeed().create(PersonApi.class);
    }

}
