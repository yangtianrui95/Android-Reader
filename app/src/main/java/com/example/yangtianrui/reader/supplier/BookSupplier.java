package com.example.yangtianrui.reader.supplier;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.yangtianrui.reader.bean.Book;
import com.example.yangtianrui.reader.config.Constants;
import com.google.android.agera.Result;
import com.google.android.agera.Supplier;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by yangtianrui on 16-5-31.
 * 查询数据,并且提供数据给观察者
 */
public class BookSupplier implements Supplier<Result<List<Book>>> {

    private String key;
    private OkHttpClient client = new OkHttpClient();


    public BookSupplier(String key) {
        this.key = key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    /**
     * 通过网络请求与解析json对象,获取书籍列表
     */
    private List<Book> getBooks() {
        HttpUrl httpUrl = HttpUrl.parse(Constants.API_URL)
                .newBuilder()
                .addQueryParameter("q", key)
                .addQueryParameter("start", "0")
                .addQueryParameter("count", "20")
                .build();
        Request request = new Request.Builder().url(httpUrl).build();
        try {
            // 以阻塞的方式发送请求
            Response response = client.newCall(request).execute();
            JSONObject jsonObject = new JSONObject(response.body().string());
            JSONArray jsonArray = jsonObject.optJSONArray("books");
            Gson gson = new Gson();
            List<Book> books = gson.fromJson(jsonArray.toString(), new TypeToken<List<Book>>() {
            }.getType());
//            Log.v("LOG",books+"");
            return books;
        } catch (Exception e) {
            Log.v("LOG", "error in getBooks"+e.getMessage());
            return null;
        }
    }


    @NonNull
    @Override
    public Result<List<Book>> get() {
        List<Book> books = getBooks();
        if (books == null) {
            return Result.failure();
        } else {
            return Result.success(books);
        }
    }
}
