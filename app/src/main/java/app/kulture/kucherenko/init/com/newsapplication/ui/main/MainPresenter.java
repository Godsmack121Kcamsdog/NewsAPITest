package app.kulture.kucherenko.init.com.newsapplication.ui.main;

import android.util.Log;
import android.widget.Toast;

import app.kulture.kucherenko.init.com.newsapplication.client.EmojiiApi;
import app.kulture.kucherenko.init.com.newsapplication.client.NewsApi;
import app.kulture.kucherenko.init.com.newsapplication.utils.Constants;

/**
 * @author alex
 *         Created on 4/11/17.
 *         Presenter of MVP implementation
 */
public class MainPresenter implements MainContract.EventListener {

    private MainContract.View view;

    MainPresenter(MainContract.View view) {
        this.view = view;
    }

    @Override
    public void getNews(String sort) {
        if (!(sort.equals(Constants.SORTING_LATEST) || sort.equals(Constants.SORTING_POPULAR) || sort.equals(Constants.SORTING_TOP))) {
            Toast.makeText(view.getContext(), sort + " is not sorting type", Toast.LENGTH_SHORT).show();
            return;
        }

        NewsApi.getInstance().getBBSNews(sort)
                .doOnError(this::errorOccurred)
                .doOnNext(view::setNews)
                .subscribe();
    }

    @Override
    public void errorOccurred(Throwable error) {
        Log.e(Constants.ERROR_TAG, error.getMessage());
        Toast.makeText(view.getContext(), "Error occurred", Toast.LENGTH_SHORT).show();
        //This is my dev branch
    }
}
