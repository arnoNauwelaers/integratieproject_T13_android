package be.kdg.t13.politiekebarometer.utils;

import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import be.kdg.t13.politiekebarometer.MainActivity;
import be.kdg.t13.politiekebarometer.model.Notification;
import be.kdg.t13.politiekebarometer.service.PolitiekeBarometerService;
import be.kdg.t13.politiekebarometer.service.TokenRequest;
import be.kdg.t13.politiekebarometer.view.dashboard.DashboardFragment;
import be.kdg.t13.politiekebarometer.view.login.LoginFragment;
import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by bague on 27/04/2018.
 *
 *  --BELANGRIJK--
 *  VOORLOPIG WORDEN ALLE CERTIFICATEN TOEGESTAAN
 *  ER WORDT OOK TELKENS EEN HOST HEADER MET WAARDE LOCALHOST MEEGEGEVEN
 *  DIT MOET VERANDEREN WANNEER DE WEB APPLICATIE LIVE GAAT
 *
 */

public class ApiManager {
    private static ApiManager instance = null;
    private static String APILINK = "https://10.0.2.2:44330/api/";
    private static String APITOKEN;

    private Retrofit retrofit;
    private PolitiekeBarometerService service;

    public static ApiManager getInstance() {
        if(instance == null) {
            instance = new ApiManager();
            OkHttpClient okHttpClient = getUnsafeOkHttpClient();
            Gson gson = new GsonBuilder().setLenient().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
            instance.retrofit = new Retrofit.Builder().baseUrl(APILINK).client(okHttpClient).addConverterFactory(GsonConverterFactory.create(gson)).build();
            instance.service = instance.retrofit.create(PolitiekeBarometerService.class);
        }
        return instance;
    }

    public String getToken() {
        return APITOKEN;
    }
    public void resetToken() {
        APITOKEN = null;
    }

    public void testApi(final MainActivity c) {
        Call<String> call = service.debugApi();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Toast.makeText(c, response.body(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(c, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void requestToken(String username, String password, final MainActivity a, final LoginFragment frag) {
        Call<String> call = service.requestToken(new TokenRequest(username, password));
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                APITOKEN = response.body();
                LoginFragment.finishLogin(a, frag);
                a.changeFragmentFromOuterClass(DashboardFragment.newInstance(), "Dashboard");
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                APITOKEN = "test";
            } {
                LoginFragment.finishLogin(a, frag);
            }
        });
    }

    /*public int login(String username, String password) {
        final int[] userId = new int[1];
        Call<Integer> call = service.login(username, password);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                userId[0] = response.body();
            }
            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                userId[0] = -1;
            }
        });
        return userId[0];
    }*/

    public List<Notification> getNotifications() {
        final List<Notification> notifications = new ArrayList<>();
        /*Call<Notification[]> call = service.getNotifications();
        call.enqueue(new Callback<Notification[]>() {
            @Override
            public void onResponse(Call<Notification[]> call, Response<Notification[]> response) {
                notifications.add(new Notification(1, response.body().toString()));
            }
            @Override
            public void onFailure(Call<Notification[]> call, Throwable t) {
                notifications.add(new Notification(1, "Error"));
            }
        });*/
        notifications.add(new Notification(1,"test"));
        notifications.add(new Notification(1,"bart de wever"));
        notifications.add(new Notification(1,"theo"));
        return notifications;
    }

    /*public List<Item> getSearchResults() {
        final List<Item> items = new ArrayList<>();
        Call<Item[]> call = service.getSearchResults();
        call.enqueue(new Callback<Item[]>() {
            @Override
            public void onResponse(Call<Item[]> call, Response<Item[]> response) {
                String[] itemsStrings = response.body().toString().split(",");
                for(String itemString : itemsStrings) {
                    //NEW ITEM ATTRIBUTEN TOEVOEGEN
                    items.add(new Item());
                }
            }
            @Override
            public void onFailure(Call<Item[]> call, Throwable t) {
                //error
            }
        });
        items.add(new Item());
        items.add(new Item());
        items.add(new Item());
        return items;
    }*/


    //https://stackoverflow.com/questions/25509296/trusting-all-certificates-with-okhttp
    //ONVELLIG (tijdelijk)
    private static OkHttpClient getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager)trustAllCerts[0]);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            OkHttpClient okHttpClient = builder.addInterceptor(new Interceptor() {
                @Override
                public okhttp3.Response intercept(Chain chain) throws IOException {
                    Request originalRequest = chain.request();
                    Request.Builder builder = originalRequest.newBuilder().header("Authorization", Credentials.basic("aUsername", "aPassword")).header("Host", "localhost");
                    Request newRequest = builder.build();
                    return chain.proceed(newRequest);
                }
            }).build();
            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
