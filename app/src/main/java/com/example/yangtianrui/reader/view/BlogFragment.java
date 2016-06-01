package com.example.yangtianrui.reader.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yangtianrui.reader.R;
import com.example.yangtianrui.reader.config.Constants;
import com.example.yangtianrui.reader.widget.ProgressWebView;

/**
 * Created by yangtianrui on 16-6-1.
 * <p/>
 * 显示blog页面
 */
public class BlogFragment extends Fragment {
    private ProgressWebView mWebView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_blog, container, false);
        mWebView = (ProgressWebView) root.findViewById(R.id.id_pwv_web_view);
        mWebView.loadUrl(Constants.BLOG_URL);
        mWebView.getSettings().setJavaScriptEnabled(true);
        return root;
    }
}
