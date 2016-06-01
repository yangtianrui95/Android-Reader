package com.example.yangtianrui.reader.view;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.yangtianrui.reader.R;
import com.example.yangtianrui.reader.adapter.BookAdapter;
import com.example.yangtianrui.reader.bean.Book;
import com.example.yangtianrui.reader.biz.BookBIZ;
import com.example.yangtianrui.reader.supplier.BookSupplier;
import com.google.android.agera.BaseObservable;
import com.google.android.agera.Repositories;
import com.google.android.agera.Repository;
import com.google.android.agera.Result;
import com.google.android.agera.Updatable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by yangtianrui on 16-5-31.
 * <p/>
 * 按照列表项,显示书籍信息
 */
public class BooksFragment extends Fragment implements Updatable, AdapterView.OnItemClickListener {
    private FloatingActionButton mFabSearch;
    private ListView mLvBooks;
    private SwipeRefreshLayout mSrlRefresh;

    private Repository<Result<List<Book>>> mRepository;
    private ExecutorService mExecutor;// 线程池
    private SearchObserver mObserver;
    private BookSupplier mSupplier;

    private BookAdapter mAdapter;
    private List<Book> mBooks = new ArrayList<>();

    private BookBIZ mBookBIZ = new BookBIZ();

    public static final String SEND_BOOK = "BOOK";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_books, null, false);
        mFabSearch = (FloatingActionButton) view.findViewById(R.id.id_fab_search);
        mLvBooks = (ListView) view.findViewById(R.id.id_lv_books);
        mSrlRefresh = (SwipeRefreshLayout) view.findViewById(R.id.id_srl_refresh);
        mAdapter = new BookAdapter(getActivity(), mBooks);
        mLvBooks.setAdapter(mAdapter);
        mLvBooks.setOnItemClickListener(this);
        setupRefresh();
        setupSearch();
        setupRepository();
        return view;
    }

    /**
     * 设置刷新操作
     */
    private void setupRefresh() {
        mSrlRefresh.setColorSchemeColors(R.color.colorPrimary);
        mSrlRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mObserver.doSearch(mBookBIZ.switchKeyWorld());
            }
        });
    }


    private void setupRepository() {
        mExecutor = Executors.newSingleThreadExecutor();// 单线程线程池
        mObserver = new SearchObserver();
        mSupplier = new BookSupplier(mBookBIZ.switchKeyWorld()); // 首先进行查询的书籍

        mRepository = Repositories
                .repositoryWithInitialValue(Result.<List<Book>>absent())
                .observe(mObserver)
                .onUpdatesPerLoop()
                .goTo(mExecutor)
                .thenGetFrom(mSupplier)
                .compile();

    }

    @Override
    public void onResume() {
        super.onResume();
        mRepository.addUpdatable(this);
        Log.v("LOG", "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        mRepository.removeUpdatable(this);
    }

    /**
     * 给fab初始化查询操作
     */
    private void setupSearch() {
        mFabSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater inflater = LayoutInflater.from(getActivity());
                View dialogView = inflater.inflate(R.layout.dialog_search_book, null, false);
                final EditText etKey = (EditText) dialogView.findViewById(R.id.id_et_dialog_key);
                final Button btnSearch = (Button) dialogView.findViewById(R.id.id_btn_dialog_search);
                // 创建一个对话框
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setView(dialogView);
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();
                btnSearch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!TextUtils.isEmpty(etKey.getText())) {
//                            Log.v("LOG", "onclick");
                            doInSearch(etKey.getText().toString());
                        }
                        // 关闭对话框
                        alertDialog.dismiss();
                    }
                });
            }
        });
    }

    /**
     * 　进行查询操作
     */
    private void doInSearch(String key) {
//        Log.v("LOG", "Fragment doInSearch");
        mObserver.doSearch(key);
    }


    /**
     * 使用观察者更新数据
     */
    @Override
    public void update() {
//        Log.v("LOG", "Fragment update");
        if (mRepository.get().isPresent()) {
            if (mSrlRefresh.isRefreshing()) {
                mSrlRefresh.setRefreshing(false);
            }
            mBooks.clear();
            mBooks.addAll(mRepository.get().get());
//            Log.v("LOG", mBooks.toString());
            mAdapter.notifyDataSetChanged();
        }
    }

    // 启动到详情页
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), BookDetailActivity.class);
        intent.putExtra(SEND_BOOK, mBooks.get(position));
        startActivity(intent);
    }

    /**
     * 从数据池中获取数据
     */
    private class SearchObserver extends BaseObservable {
        // 进行查找操作
        public void doSearch(String key) {
//            Log.v("LOG", "Observer doSearch");
            mSupplier.setKey(key);
            dispatchUpdate();
        }
    }

}
