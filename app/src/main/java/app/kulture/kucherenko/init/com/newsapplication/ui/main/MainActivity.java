package app.kulture.kucherenko.init.com.newsapplication.ui.main;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import app.kulture.kucherenko.init.com.newsapplication.R;
import app.kulture.kucherenko.init.com.newsapplication.adapters.NewsAdapter;
import app.kulture.kucherenko.init.com.newsapplication.databinding.ActivityMainBinding;
import app.kulture.kucherenko.init.com.newsapplication.models.retrofit.Article;
import app.kulture.kucherenko.init.com.newsapplication.models.retrofit.News;
import app.kulture.kucherenko.init.com.newsapplication.ui.fragments.MoreInfoFragment;
import app.kulture.kucherenko.init.com.newsapplication.utils.Constants;
import app.kulture.kucherenko.init.com.newsapplication.utils.SpaceItemDecoration;

/**
 * @author alex
 *         Created on 4/11/17.
 *         View of MVP implementation
 */
public class MainActivity extends AppCompatActivity implements MainContract.View {

    private ActivityMainBinding binding;
    private MainPresenter presenter;
    private NewsAdapter adapter;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        presenter = new MainPresenter(this);
        presenter.getNews(Constants.SORTING_TOP);
        binding.content.recycler.setLayoutManager(new LinearLayoutManager(this));
        binding.content.recycler.addItemDecoration(new SpaceItemDecoration(3));
        binding.content.recycler.setAdapter(adapter = new NewsAdapter(this));
    }

    @Override
    public void setNews(News news) {
        adapter.setList(news.getArticles());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void more(Article article) {
        MoreInfoFragment.newInstance(article).show(getSupportFragmentManager(), "MORE");
    }

    @Override
    public Context getContext() {
        return this;
    }
}
