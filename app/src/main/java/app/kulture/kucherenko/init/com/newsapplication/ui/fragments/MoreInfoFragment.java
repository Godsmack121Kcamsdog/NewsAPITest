package app.kulture.kucherenko.init.com.newsapplication.ui.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.kulture.kucherenko.init.com.newsapplication.R;
import app.kulture.kucherenko.init.com.newsapplication.databinding.NewsFragmentBinding;
import app.kulture.kucherenko.init.com.newsapplication.models.retrofit.Article;

public class MoreInfoFragment extends DialogFragment {

    private Article article;

    public static MoreInfoFragment newInstance(Article article) {
        MoreInfoFragment fragment = new MoreInfoFragment();
        fragment.article = article;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_NoTitleBar_Fullscreen);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_fragment, container, false);
        NewsFragmentBinding binding = DataBindingUtil.bind(view);
        if (article.getUrl() != null)
            binding.web.loadUrl(article.getUrl());
        else dismiss();
        return view;
    }

}
