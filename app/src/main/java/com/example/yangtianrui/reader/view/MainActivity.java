package com.example.yangtianrui.reader.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.yangtianrui.reader.R;
import com.example.yangtianrui.reader.config.Constants;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar mToolbar;
    private DrawerLayout mDlLayout;
    private NavigationView mNvMenu;
    private ActionBarDrawerToggle mToggle;

    private long mExitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        switchToBook();
    }

    /**
     * 初始化组件及Toolbar
     */
    private void initView() {
        mDlLayout = (DrawerLayout) findViewById(R.id.id_dl_main_layout);
        mNvMenu = (NavigationView) findViewById(R.id.id_nv_menu);
        mToolbar = (Toolbar) findViewById(R.id.id_toolbar);
        setSupportActionBar(mToolbar);
        // 设置关联图标
        mToggle = new ActionBarDrawerToggle(
                this, mDlLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDlLayout.setDrawerListener(mToggle);
        mToggle.syncState();
        // 设置菜单栏点击事件
        mNvMenu.setNavigationItemSelectedListener(this);
    }

    // 单击后退键时
    // 单击就两下才可退出
    @Override
    public void onBackPressed() {
        if (mDlLayout.isDrawerOpen(GravityCompat.START)) {
            mDlLayout.closeDrawer(GravityCompat.START);
        } else {
            doExitAPP();
        }
    }

    /**
     * 点击两下Back键即可退出
     */
    private void doExitAPP() {
        if (System.currentTimeMillis() - mExitTime > 2000) {
            Snackbar.make(mDlLayout, "再按一下即可退出", Snackbar.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id) {
            case R.id.id_nav_book:
                switchToBook();
                break;
            case R.id.id_nav_blog:
                switchToBlog();
                break;
            case R.id.id_nav_explain:
                switchToExplain();
                break;
            case R.id.id_nav_github:
                toGithub();
                break;
        }
        mDlLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * 打开系统浏览器跳转到github
     */
    private void toGithub() {
        Uri uri = Uri.parse(Constants.GITHUB_URL);
        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
        startActivity(intent);
    }

    /**
     * 切换到图书
     */
    private void switchToBook() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.id_fl_content, new BooksFragment()).commit();
        mToolbar.setTitle("书籍信息");
    }

    private void switchToExplain() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.id_fl_content, new ExplainFragment()).commit();
        mToolbar.setTitle("使用说明");
    }

    private void switchToBlog() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.id_fl_content, new BlogFragment()).commit();
        mToolbar.setTitle("作者博客");
    }

}
