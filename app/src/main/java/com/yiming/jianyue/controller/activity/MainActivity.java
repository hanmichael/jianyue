package com.yiming.jianyue.controller.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.yiming.jianyue.R;
import com.yiming.jianyue.controller.activity.weixin.WeiXinActivity;
import com.yiming.jianyue.controller.activity.xiaohua.XiaoHuaActivity;
import com.yiming.jianyue.utils.common.ShareUtils;
import com.yiming.jianyue.view.adapter.fragment.AcMainFmAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.sharesdk.framework.ShareSDK;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private int position = 0;
    private Toolbar toolbar;

    private View headView;
    public AcMainFmAdapter adapter;
    @Bind(R.id.fragment_main_tab_layout)
    TabLayout mTabLayout;
    @Bind(R.id.fragment_main_viewpager)
    ViewPager mViewPager;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ShareSDK.initSDK(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            /**https://developer.android.com/intl/zh-cn/samples/ImmersiveMode/src/com.example.android.immersivemode/ImmersiveModeFragment.html
             * https://developer.android.com/training/system-ui/navigation.html*/
            Window window = getWindow();
            // Translucent status bar
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // Translucent navigation bar
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            int colorPrimaryDark = ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark);
            window.setStatusBarColor(colorPrimaryDark);
        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_camara);
        headView = navigationView.inflateHeaderView(R.layout.nav_header_main);
        TextView txt2 = (TextView) headView.findViewById(R.id.headerTxt);
        txt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WebviewActivity.class);
                intent.putExtra("url", "http://fanofdemo.github.io/");
                intent.putExtra("title", "yiming");
                startActivity(intent);
            }
        });
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        if (adapter == null)
            adapter = new AcMainFmAdapter(this.getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(4);
        mTabLayout.setupWithViewPager(mViewPager);

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_camara) {
            changeTitle("A站B站");
        } else if (id == R.id.nav_gallery) {
            WeiXinActivity.startActivity(this);
        } else if (id == R.id.nav_slideshow) {
            XiaoHuaActivity.startActivity(this);
        }
//        else if (id == R.id.nav_manage) {
//            changeTitle("天气");
//            WeatherFragment fragment = new WeatherFragment();
//            android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
//            fm.beginTransaction().replace(R.id.content_frame, fragment).commit();
//        } else
        if (id == R.id.nav_share) {
            String imageUrl = "http://img.hb.aicdn.com/ef6960ba7b45f903f15872e0446cc0059d857b6e9ed-zWbJuy_fw658";
            String url = "https://github.com/fanOfDemo/jianyue";
            ShareUtils.showShare(this, imageUrl, url, "简悦APP，聊以自慰");
        } else if (id == R.id.nav_send) {
        }
        item.setChecked(false);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void changeTitle(String title) {
        if (toolbar == null) return;
        toolbar.setTitle(title);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ShareSDK.stopSDK(this);

    }
}
