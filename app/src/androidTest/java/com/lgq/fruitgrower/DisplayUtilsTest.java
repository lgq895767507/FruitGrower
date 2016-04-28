package com.lgq.fruitgrower;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.lgq.fruitgrower.view.MainActivity;
import com.lgq.fruitgrower.view.application.AppAct;
import com.lgq.fruitgrower.view.utils.DisplayUtils;

import junit.framework.TestCase;

/**
 * Created by lgq on 16-4-26.
 */
public class DisplayUtilsTest extends TestCase {

    private String TAG = "lgq";

    private Context context;
    @Override
    protected void setUp() throws Exception {
        super.setUp();

    }

    public void testDisPx2dp() throws Exception{
        MainActivity application = new MainActivity();
        context = application.getApplicationContext();
        Log.i(TAG, ""+DisplayUtils.px2dp(context, 10));
    }


    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
