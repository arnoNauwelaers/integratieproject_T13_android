package be.kdg.t13.politiekebarometer.utils;

import android.content.SharedPreferences;
import android.widget.Toast;

import com.anychart.anychart.AnyChart;
import com.anychart.anychart.Chart;
import com.anychart.anychart.DataEntry;
import com.anychart.anychart.ValueDataEntry;
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
import be.kdg.t13.politiekebarometer.model.User;
import be.kdg.t13.politiekebarometer.service.PolitiekeBarometerService;
import be.kdg.t13.politiekebarometer.service.charts.ChartItemData;
import be.kdg.t13.politiekebarometer.service.charts.Item;
import be.kdg.t13.politiekebarometer.service.charts.SimpleChart;
import be.kdg.t13.politiekebarometer.service.TokenRequest;
import be.kdg.t13.politiekebarometer.view.dashboard.DashboardFragment;
import be.kdg.t13.politiekebarometer.view.login.LoginFragment;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
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

    public static String getToken() {
        return APITOKEN;
    }

    public static void setToken(String token, MainActivity a) {
        APITOKEN = token;
        SharedPreferences settings = a.getSharedPreferences("POLITIEKE_BAROMETER", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("ACCESS_TOKEN", ApiManager.getInstance().getToken());
        editor.apply();
    }

    public static void resetToken(MainActivity a) {
        APITOKEN = "000";
        SharedPreferences settings = a.getSharedPreferences("POLITIEKE_BAROMETER", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("ACCESS_TOKEN", ApiManager.getInstance().getToken());
        editor.apply();
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
                if(APITOKEN==null||APITOKEN.isEmpty()||APITOKEN.equals("000")) {
                    LoginFragment.finishLogin(a, frag, true);
                }else{
                    LoginFragment.finishLogin(a, frag, false);
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                APITOKEN = "000";
                LoginFragment.finishLogin(a, frag, true);
            }
        });
    }

    public void getHomeCharts() {
        Call<List<SimpleChart>> call = service.getHomeChartData();
        call.enqueue(new Callback<List<SimpleChart>>() {
            @Override
            public void onResponse(Call<List<SimpleChart>> call, Response<List<SimpleChart>> response) {
                List<Chart> charts = new ArrayList<>();
                for(SimpleChart chart : response.body()) {
                    List<DataEntry> data = new ArrayList<>();
                    for(ChartItemData chartData : chart.data) {
                        for(Item item : chartData.data) {
                            data.add(new ValueDataEntry(item.name, item.amount));
                        }
                    }
                    charts.add(AnyChart.column().setData(data));
                }
                ChartManager.setHomeCharts(charts);
            }
            @Override
            public void onFailure(Call<List<SimpleChart>> call, Throwable t) {

            }
        });

    }

    public void getDashboardCharts() {
        Call<ResponseBody> call = service.getDashboardCharts();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                List<Chart> charts = new ArrayList<>();
                List<DataEntry> data = new ArrayList<>();
                data.add(new ValueDataEntry("John", 10000));
                data.add(new ValueDataEntry("Jake", 12000));
                data.add(new ValueDataEntry("Peter", 18000));
                charts.add(AnyChart.pie().setData(data));
                List<DataEntry> data2 = new ArrayList<>();
                data2.add(new ValueDataEntry("John", 10000));
                data2.add(new ValueDataEntry("Jake", 12000));
                data2.add(new ValueDataEntry("Peter", 18000));
                charts.add(AnyChart.pie().setData(data2));
                List<DataEntry> data3 = new ArrayList<>();
                data3.add(new ValueDataEntry("John", 10000));
                data3.add(new ValueDataEntry("Jake", 12000));
                data3.add(new ValueDataEntry("Peter", 18000));
                charts.add(AnyChart.pie().setData(data3));
                ChartManager.setDashboardCharts(charts);
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    /*public List<Notification> getNotifications() {
        final List<Notification> notifications = new ArrayList<>();
        Call<List<Notification>> call = service.getNotifications();
        call.enqueue(new Callback<List<Notification>>() {
            @Override
            public void onResponse(Call<List<Notification>> call, Response<List<Notification>> response) {
                notifications.add(new Notification(1, response.body().toString()));
            }
            @Override
            public void onFailure(Call<List<Notification>> call, Throwable t) {
                notifications.add(new Notification(1, "Error"));
            }
        });
        notifications.add(new Notification(1,"test"));
        notifications.add(new Notification(1,"bart de wever"));
        notifications.add(new Notification(1,"theo"));
        return notifications;
    }*/

    public void setUserInfo(final MainActivity a) {
        Call<User> call = service.getUserInfo();
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                UserManager.setUser(new User(response.body().getId(), response.body().getUsername()));
                a.changeFragmentFromOuterClass(DashboardFragment.newInstance(), "Dashboard");
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    public List<String> getSearchResults() {
        final List<String> items = new ArrayList<>();
        Call<List<String>> call = service.getSearchResults();
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                String[] itemsStrings = response.body().toString().split(",");
                for(String itemString : itemsStrings) {
                    //NEW ITEM ATTRIBUTEN TOEVOEGEN
                    items.add("test");
                }
            }
            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                //error
            }
        });
        items.add("test");
        items.add("test");
        items.add("test");
        return items;
    }


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
                    String token = ApiManager.getInstance().getToken();
                    if(token == null||token.isEmpty()) {
                        token = "000";
                    }
                    Request.Builder builder = originalRequest.newBuilder().header("Authorization", token).header("Host", "localhost");
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
