package com.example.yangtianrui.reader.view;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.yangtianrui.reader.R;
import com.example.yangtianrui.reader.bean.Book;

import java.util.ArrayList;
import java.util.List;

public class BookDetailActivity extends AppCompatActivity {

    private CollapsingToolbarLayout mCtlLayout;
    private ViewPager mVpContent;
    private TabLayout mTvTabs;
    private Toolbar mToolbar;
    private ImageView mIvImg;

    private String[] mTitles = new String[]{
            "内容摘要", "作者简介", "目录"
    };
    private List<Fragment> mFragments = new ArrayList<>();
    private Book mBook;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        initData();
        initView();
    }

    private void initData() {
        mBook = (Book) getIntent().getSerializableExtra(BooksFragment.SEND_BOOK);
        mFragments.add(BookDetailFragment.getInstance(mBook.getSummary()));
        mFragments.add(BookDetailFragment.getInstance(mBook.getAuthor_intro()));
        mFragments.add(BookDetailFragment.getInstance(mBook.getCatalog()));
    }

    private void initView() {
        mIvImg = (ImageView) findViewById(R.id.id_iv_detail_img);
        mCtlLayout = (CollapsingToolbarLayout) findViewById(R.id.id_ct_detail_coll);
        mVpContent = (ViewPager) findViewById(R.id.id_vp_detail_pager);
        mTvTabs = (TabLayout) findViewById(R.id.id_tl_detail_tab);
        mToolbar = (Toolbar) findViewById(R.id.id_toolbar_detail);
        mCtlLayout.setTitle(mBook.getTitle());
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        // 使用Glide加载图片
        Glide.with(this)
                .load(mBook.getImages().getLarge())
                .fitCenter()
                .into(mIvImg);
        mVpContent.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mTitles.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mTitles[position];
            }
        });
        mTvTabs.setupWithViewPager(mVpContent);
    }
}
