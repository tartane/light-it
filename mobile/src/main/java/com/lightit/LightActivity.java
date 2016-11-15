package com.lightit;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.WindowManager;

import com.viewpagerindicator.CirclePageIndicator;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LightActivity extends FragmentActivity {

    @Bind(R.id.pager)
    ViewPager mPager;

    @Bind(R.id.circleIndicator)
    CirclePageIndicator mCircleIndicator;

    private ScreenSlidePagerAdapter mPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_light);
        ButterKnife.bind(this);

        ActionBar actionBar = getActionBar();

        if(actionBar != null) {
            actionBar.hide();
        }

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_FULL;
        getWindow().setAttributes(params);

        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mCircleIndicator.setViewPager(mPager);
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch(position) {
                case 0:
                    mCircleIndicator.setFillColor(ContextCompat.getColor(LightActivity.this, android.R.color.black));
                    mCircleIndicator.setStrokeColor(ContextCompat.getColor(LightActivity.this, android.R.color.black));
                    return LightFragment.newInstance(false);
                case 1:
                    LightFragment frag = LightFragment.newInstance(true);
                    frag.SetOnLightChangeListener(new LightFragment.OnLightChangeListener() {
                        @Override
                        public void onLightChange(boolean lightActived) {
                            if(mPager.getCurrentItem() == 1) {
                                if (lightActived) {
                                    mCircleIndicator.setFillColor(ContextCompat.getColor(LightActivity.this, android.R.color.black));
                                    mCircleIndicator.setStrokeColor(ContextCompat.getColor(LightActivity.this, android.R.color.black));
                                }
                                else {
                                    mCircleIndicator.setFillColor(ContextCompat.getColor(LightActivity.this, android.R.color.white));
                                    mCircleIndicator.setStrokeColor(ContextCompat.getColor(LightActivity.this, android.R.color.white));
                                }
                            } else {
                                mCircleIndicator.setFillColor(ContextCompat.getColor(LightActivity.this, android.R.color.black));
                                mCircleIndicator.setStrokeColor(ContextCompat.getColor(LightActivity.this, android.R.color.black));
                            }
                        }
                    });
                    return frag;
                default:
                    return LightFragment.newInstance(false);
            }

        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
