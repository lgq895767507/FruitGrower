package com.lgq.fruitgrower.view.act;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by lgq on 16-2-16.
 */
public class
        FragmentController {
    private int containerId;
    private String tag;
    private FragmentManager fm;
    private ArrayList<Fragment> fragments;

    private static FragmentController controller;

    public static FragmentController getInstance(FragmentActivity activity, int containerId, String tag) {
        if (controller == null) {
            controller = new FragmentController(activity, containerId, tag);
        }
        return controller;
    }

    public static FragmentController getInstance(FragmentActivity activity, int containerId) {
        if (controller == null) {
            controller = new FragmentController(activity, containerId);
        }
        return controller;
    }

    public static void onDestroy() {
        controller = null;
    }

    private FragmentController(FragmentActivity activity, int containerId,String tag) {
        this.containerId = containerId;
        this.tag = tag;
        fm = activity.getSupportFragmentManager();
        initFragment();
    }

    private FragmentController(FragmentActivity activity, int containerId) {
        this.containerId = containerId;
        fm = activity.getSupportFragmentManager();
        initFragment();
    }

    private void initFragment() {
        fragments = new ArrayList<Fragment>();

        fragments.add(new MainFragment());
        fragments.add(new ClassifyFragment());
        fragments.add(new MessageFragment());
        fragments.add(new OwnerFragment());


        FragmentTransaction ft = fm.beginTransaction();
        for(Fragment fragment : fragments) {
            ft.add(containerId, fragment,tag);
        }
        ft.commit();
    }

    public void showFragment(int position) {
        hideFragments();
        Fragment fragment = fragments.get(position);
        FragmentTransaction ft = fm.beginTransaction();
     //   ft.addToBackStack(null);
    //    Log.i("lgq1","addTobackStack");

        ft.show(fragment);
        ft.commit();
    }

    public void hideFragments() {
        FragmentTransaction ft = fm.beginTransaction();
        for(Fragment fragment : fragments) {
            if(fragment != null) {
                ft.hide(fragment);
            }
        }
        ft.commit();
    }

    public Fragment getFragment(int position) {
        return fragments.get(position);
    }


}
