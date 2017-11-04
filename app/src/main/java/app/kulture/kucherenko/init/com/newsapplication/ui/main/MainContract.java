package app.kulture.kucherenko.init.com.newsapplication.ui.main;

import android.content.Context;

import app.kulture.kucherenko.init.com.newsapplication.models.retrofit.Article;
import app.kulture.kucherenko.init.com.newsapplication.models.retrofit.News;

/**
 * @author alex
 * Created on 4/11/17.
 * Model of MVP implementation
 */
public interface MainContract {

    interface View {
        void setNews(News news);

        void more(Article article);

        Context getContext();
    }

    interface EventListener {
        void getNews(String sort);
        void errorOccured(Throwable error);
    }
}
