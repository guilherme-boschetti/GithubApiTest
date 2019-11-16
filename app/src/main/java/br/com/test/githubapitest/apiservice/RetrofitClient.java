package br.com.test.githubapitest.apiservice;

import java.io.IOException;

import br.com.test.githubapitest.util.SharedPreferencesUtil;
import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL = "https://api.github.com";
    private static Retrofit retrofit = null;

    private static Retrofit getRetrofit(final String username, final String password) {
        if (retrofit == null) {

            // set header through OkHttpClient if API is authenticate API.
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

            httpClient.addInterceptor(new Interceptor() {

                @Override
                public okhttp3.Response intercept(Chain chain) throws IOException {

                    Request request = chain.request().newBuilder()
                            //.addHeader("Authorization", "Basic " + Base64.encodeToString((username+password).getBytes(), Base64.DEFAULT)) // This way is also possible
                            .addHeader("Authorization", Credentials.basic(username, password))
                            .addHeader("Accept", "application/vnd.github.v3+json")
                            .build();
                    return chain.proceed(request);
                }
            });

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
        }
        return retrofit;
    }

    public static <S> S createService(Class<S> serviceClass, String username, String password) {
        return getRetrofit(username, password).create(serviceClass);
    }
}
