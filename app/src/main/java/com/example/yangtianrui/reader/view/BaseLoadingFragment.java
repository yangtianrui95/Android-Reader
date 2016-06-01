package com.example.yangtianrui.reader.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.yangtianrui.reader.R;

/**
 * Created by yangtianrui on 16-5-31.
 */
public abstract class BaseLoadingFragment extends Fragment {
    protected RelativeLayout mRlRoot;
    private ProgressBar mPbLoading;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRlRoot = (RelativeLayout) inflater.inflate(R.layout.content_main, container, false);
        mPbLoading = new ProgressBar(getActivity());
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(200, 200);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        mPbLoading.setLayoutParams(params);
        mRlRoot.addView(mPbLoading);
        return mRlRoot;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // 判断是否加载完成
        if (loadOK()) {
            switchContent();
        } else {
            switchToError();
        }
        super.onCreate(savedInstanceState);
    }

    private void switchContent() {
        mPbLoading.setVisibility(View.GONE);
        View content = createContentView();
        mRlRoot.addView(content);
    }

    private void switchToError() {
        mPbLoading.setVisibility(View.GONE);
        View error = createErrorView();
        mRlRoot.addView(error);
    }

    // 初始化view组件
    protected abstract View createContentView();

    // 是否正常载入
    protected abstract boolean loadOK();

    // 设置出错时View
    protected abstract View createErrorView();
}
