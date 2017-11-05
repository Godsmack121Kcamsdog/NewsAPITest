package app.kulture.kucherenko.init.com.newsapplication.adapters;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;

import java.util.List;

import app.kulture.kucherenko.init.com.newsapplication.R;
import app.kulture.kucherenko.init.com.newsapplication.databinding.NewsItemBinding;
import app.kulture.kucherenko.init.com.newsapplication.models.retrofit.Article;
import app.kulture.kucherenko.init.com.newsapplication.ui.main.MainContract;

import static app.kulture.kucherenko.init.com.newsapplication.utils.DateTimeUtils.DD_MMMM_YYYY;
import static app.kulture.kucherenko.init.com.newsapplication.utils.DateTimeUtils.HH;
import static app.kulture.kucherenko.init.com.newsapplication.utils.DateTimeUtils.HH_mm_ss;
import static app.kulture.kucherenko.init.com.newsapplication.utils.DateTimeUtils.YYYY_MM_DD_HH_mm_SS;
import static app.kulture.kucherenko.init.com.newsapplication.utils.DateTimeUtils.formatOrderDateTimeForItem;

/**
 * Created by alex on 04/11/17.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsHolder> {

    private List<Article> list;
    private MainContract.View view;
    private LayoutInflater inflater;

    public NewsAdapter(List<Article> list, MainContract.View view) {
        this.list = list;
        this.view = view;
        inflater = LayoutInflater.from(view.getContext());
    }

    public NewsAdapter(MainContract.View view) {
        this.view = view;
        inflater = LayoutInflater.from(view.getContext());
    }

    public List<Article> getList() {
        return list;
    }

    public void setList(List<Article> list) {
        this.list = list;
    }

    @Override
    public NewsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NewsHolder(inflater.inflate(R.layout.news_item, parent, false));
    }

    @Override
    public void onBindViewHolder(NewsHolder holder, int position) {
        holder.binding.info.header.setText(list.get(position).getTitle());
        holder.binding.info.news.setText(list.get(position).getDescription());
        //author name
        if (list.get(position).getAuthor() != null) {
            String author = "Author: " + list.get(position).getAuthor();
            holder.binding.info.author.setText(author);
        }
        if (list.get(position).getPublishedAt() != null) {
            //prepare date for formatting
            String date = list.get(position).getPublishedAt().replace("T", " ").replace("Z", "");
            //get time in correct format from date
            String time = formatOrderDateTimeForItem(date.split(" ")[1], HH, HH_mm_ss);
            //format date
            date = formatOrderDateTimeForItem(date, DD_MMMM_YYYY, YYYY_MM_DD_HH_mm_SS);
            holder.binding.info.date.setText(String.valueOf("Date: " + date));
            //PM or AM label
            if (Integer.parseInt(time) > 12)
                holder.binding.time.value.setText(String.valueOf((Integer.parseInt(time) - 12) + " PM"));
            else {
                if (time.equals("00")) holder.binding.time.value.setText(String.valueOf("12 PM"));
                else
                    holder.binding.time.value.setText(String.valueOf(time + " AM"));
            }
        }
        holder.setArticle(list.get(position));
        if (list.get(position).getUrlToImage() != null)
            Glide.with(view.getContext())
                    .load(list.get(position).getUrlToImage())
                    .thumbnail(0.5f)
                    .crossFade()
                    .priority(Priority.IMMEDIATE)
                    .fitCenter()
                    .into(holder.binding.image);
        else
            holder.binding.image.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class NewsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        NewsItemBinding binding;
        Article article;

        NewsHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
            binding.image.setOnClickListener(this);
            binding.info.news.setOnClickListener(this);
        }

        Article getArticle() {
            return article;
        }

        void setArticle(Article article) {
            this.article = article;
        }

        @Override
        public void onClick(View view) {
            NewsAdapter.this.view.more(article);
        }
    }
}
