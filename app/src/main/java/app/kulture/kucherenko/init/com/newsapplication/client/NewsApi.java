package app.kulture.kucherenko.init.com.newsapplication.client;

import android.util.Log;

import com.ihsanbal.logging.Level;
import com.ihsanbal.logging.LoggingInterceptor;

import app.kulture.kucherenko.init.com.newsapplication.App;
import app.kulture.kucherenko.init.com.newsapplication.BuildConfig;
import app.kulture.kucherenko.init.com.newsapplication.R;
import app.kulture.kucherenko.init.com.newsapplication.models.retrofit.News;
import app.kulture.kucherenko.init.com.newsapplication.utils.Constants;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.internal.platform.Platform;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author alex
 *         Created on 4/11/17.
 *         Threadsafe Singletone for making requests
 *         <p>
 *         P.S. If api provided more requests - class should be
 *         rewrited for extending and writing group of requests in other classes,
 *         but it`s not - so I placed request calling here
 */
public class NewsApi {

    private static NewsApiInterface newsInterface;
    private static NewsApi apiCompetitions;
    private static final String host = App.getInstance().getMResources().getString(R.string.host);
    private static final String articles = "articles?source=";
    private static final String sort_by = "&sortBy=";
    private static final String api_key = "&apiKey=" + App.getInstance().getMResources().getString(R.string.api_key);

    public synchronized static NewsApi getInstance() {
        if (apiCompetitions == null) apiCompetitions = new NewsApi();
        return apiCompetitions;
    }

    private NewsApi() {
        createRetrofit();
    }

    private void createRetrofit() {
        //OkHttpClient3
        final OkHttpClient client = new OkHttpClient
                .Builder()
                .addInterceptor(logInterceptorBuilder())
                .build();

        //Retrofit2
        Retrofit retrofitAdapter = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(host)
                .client(client)
                .build();
        newsInterface = retrofitAdapter.create(NewsApiInterface.class);
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

    public Flowable<News> getBBSNews(String sortingType) {
        String url = host
                .concat(articles).concat(Constants.DEFAULT_SOURCE)
                .concat(sort_by).concat(sortingType)
                .concat(api_key);
        return newsInterface.getNews(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
