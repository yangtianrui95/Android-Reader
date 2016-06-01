package com.example.yangtianrui.reader.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yangtianrui.reader.R;

/**
 * Created by yangtianrui on 16-6-1.
 */
public class BookDetailFragment extends Fragment {

    public static final String INFO_ARGS = "INFO";

    public BookDetailFragment() {
    }

    public static BookDetailFragment getInstance(String content) {
        Bundle bundle = new Bundle();
        bundle.putString(INFO_ARGS, content);
        BookDetailFragment fragment = new BookDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        String content = getArguments().getString(INFO_ARGS);
        View root = inflater.inflate(R.layout.fragment_detail_book, container, false);
        TextView tv= (TextView) root.findViewById(R.id.id_tv_detail);
        tv.setText(content);
        return root;
    }
}
