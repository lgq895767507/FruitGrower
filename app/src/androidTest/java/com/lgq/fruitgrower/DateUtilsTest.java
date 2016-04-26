package com.lgq.fruitgrower;
import android.text.format.DateFormat;
import com.lgq.fruitgrower.view.utils.DateUtils;

import junit.framework.Assert;
import junit.framework.TestCase;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by lgq on 16-4-26.
 */
public class DateUtilsTest extends TestCase {

    public static final long ONE_MINUTE_MILLIONS = 60 * 1000;
    public static final long ONE_HOUR_MILLIONS = 60 * ONE_MINUTE_MILLIONS;
    public static final long ONE_DAY_MILLIONS = 24 * ONE_HOUR_MILLIONS;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * case 1:传入一个合理有效的string类型的日期yyyy-MM-dd HH:mm:ss，且与当前时间相差10分钟以内
     * 期望：返回"刚刚"
     */

    public void testGetShortTime1() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date();
        long durTime = curDate.getTime() - 9 * ONE_MINUTE_MILLIONS;
        try {
            Assert.assertEquals("刚刚", DateUtils.getShortTime(sdf.format(durTime)));
        } catch (Exception e) {
            fail();
        }

    }

    /**
     * case 1:传入一个合理有效的string类型的日期yyyy-MM-dd HH:mm:ss，且与当前时间相差一个小时以内
     * 期望：返回"多少分钟前"
     */

    public void testGetShortTime2() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date();
        long durTime = curDate.getTime() - 48 * ONE_MINUTE_MILLIONS;
        try {
            Assert.assertEquals("48分钟前", DateUtils.getShortTime(sdf.format(durTime)));
        } catch (Exception e) {
            fail();
        }

    }

    /**
     * case 1:传入一个合理有效的string类型的日期yyyy-MM-dd HH:mm:ss，且与当前时间相差一天以上
     * 期望：返回“具体的那天的日期"
     */

    public void testGetShortTime3() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date();
        long durTime = curDate.getTime() - 3 * ONE_DAY_MILLIONS;
        Date date = sdf.parse(sdf.format(durTime));
        String str = DateFormat.format("MM-dd", date).toString();
        try {
            Assert.assertEquals(str, DateUtils.getShortTime(sdf.format(durTime)));
        } catch (Exception e) {
            fail();
        }
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
