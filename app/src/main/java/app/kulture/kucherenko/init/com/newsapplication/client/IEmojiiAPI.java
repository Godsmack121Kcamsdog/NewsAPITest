package app.kulture.kucherenko.init.com.newsapplication.client;

import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Created by alex on 3/27/18.
 */

public interface IEmojiiAPI {

    @POST("admin/get-category.php")
    Flowable<JsonArray> getCategory();

    @POST("admin/get-sub-category.php")
    Flowable<JsonArray> getSubCategory();

    @GET
    Flowable<ResponseBody> getZip(@Url String url);

}
