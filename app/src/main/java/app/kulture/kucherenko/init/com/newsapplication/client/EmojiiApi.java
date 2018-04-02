package app.kulture.kucherenko.init.com.newsapplication.client;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.ihsanbal.logging.Level;
import com.ihsanbal.logging.LoggingInterceptor;

import app.kulture.kucherenko.init.com.newsapplication.BuildConfig;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.internal.platform.Platform;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by alex on 3/27/18.
 */

public class EmojiiApi {

    private static IEmojiiAPI emojiiInterface;
    private static EmojiiApi emojiiApi;
    private static final String host = "http://dev.soft-vice.com/emoji/";

    public synchronized static EmojiiApi getInstance() {
        if (emojiiApi == null) emojiiApi = new EmojiiApi();
        return emojiiApi;
    }

    private EmojiiApi() {
        createRetrofit();
    }

    private void createRetrofit() {
        //OkHttpClient3
        final OkHttpClient client = new OkHttpClient
                .Builder()
                .addInterceptor(logInterceptorBuilder())
                .build();

        Gson gson = new GsonBuilder().setLenient().create();
        //Retrofit2
        Retrofit retrofitAdapter = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(host)
                .client(client)
                .build();
        emojiiInterface = retrofitAdapter.create(IEmojiiAPI.class);
    }

    private LoggingInterceptor logInterceptorBuilder() {
        return new LoggingInterceptor.Builder()
                .loggable(BuildConfig.DEBUG)
                .setLevel(Level.BASIC)
                .log(Platform.INFO)
                .request("RequestLog")
                .response("ResponseLog")
                .tag("info")
                .addHeader("version", BuildConfig.VERSION_NAME)
                .logger((level, tag, msg) -> Log.w(tag, msg))
                .build();
    }

    public Flowable<JsonArray> getCategory() {
        return emojiiInterface.getCategory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Flowable<JsonArray> getSubCategory() {
        return emojiiInterface.getSubCategory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    //Todo not post request!!!
    public Flowable<ResponseBody> getZip() {
        return emojiiInterface.getZip(host + "admin/get-images.php")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
