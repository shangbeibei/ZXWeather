package com.shangbeibei.zxweather.common.ioverride;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2016/1/21.
 */
public abstract class IFragmentPagerAdapter extends PagerAdapter {
    private final FragmentManager mfragmentManager;

    public IFragmentPagerAdapter(FragmentManager fm) {
        this.mfragmentManager = fm;
    }

    public abstract Fragment getItem(int position);

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return false;
    }

    @Override
    public void startUpdate(ViewGroup container) {
        super.startUpdate(container);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = getItem(position);
        if (!fragment.isAdded()) {
            FragmentTransaction ft = mfragmentManager.beginTransaction();
            ft.add(fragment, "WeatherFragment" + String.valueOf(position));
            ft.commit();
            mfragmentManager.executePendingTransactions();
        }
        if (fragment.getView().getParent() == null) {
            container.addView(fragment.getView());
        }
        return fragment.getView();

    }



    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        super.destroyItem(container, position, object);
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    private static String makeFragmentName(int viewId, int index) {
        return "android:switcher:" + viewId + ":" + index;
    }
}

