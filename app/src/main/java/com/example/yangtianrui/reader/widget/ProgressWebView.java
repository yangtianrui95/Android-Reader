package com.example.yangtianrui.reader.widget;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

/**
 * Created by yangtianrui on 16-6-1.
 * 带进度条的浏览器
 */
public class ProgressWebView extends WebView {
    private Handler mHandler;
    private WebView mWebView;
    private WebViewProgressBar mPbBar;
    // 隐藏ProgressBar
    private Runnable mHidePbBar = new Runnable() {
        @Override
        public void run() {
            mPbBar.setVisibility(View.GONE);
        }
    };


    public ProgressWebView(Context context) {
        this(context, null);
    }

    public ProgressWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPbBar = new WebViewProgressBar(getContext());
        mPbBar.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                , ViewGroup.LayoutParams.WRAP_CONTENT));
        mPbBar.setVisibility(View.GONE);
        addView(mPbBar);
        mHandler = new Handler();
        mWebView = this;
        setWebChromeClient(new MyWebChromeClient());
        setWebViewClient(new MyWebClient());
    }


    private class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            // 加载完成
            if (newProgress == 100) {
                mPbBar.setProgress(100);
                mHandler.postDelayed(mHidePbBar, 500);
            } else if (mPbBar.getVisibility() == View.GONE) {
                mPbBar.setVisibility(View.VISIBLE);
            }
            mPbBar.setProgress(newProgress);
            super.onProgressChanged(view, newProgress);
        }
    }

    private class MyWebClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            mWebView.loadUrl(url);
            return true;
        }
    }


}
