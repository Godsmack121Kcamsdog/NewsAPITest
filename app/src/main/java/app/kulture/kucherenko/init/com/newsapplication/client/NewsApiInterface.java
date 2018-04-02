package app.kulture.kucherenko.init.com.newsapplication.client;

import app.kulture.kucherenko.init.com.newsapplication.models.retrofit.News;
import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * @author alex
 * Created on 4/11/17.
 */
interface NewsApiInterface {

    /**
     * @param url used cause of dynamic url adds
     * @return news from source
     * */
    @GET
    Flowable<News> getNews(@Url String url);
}
