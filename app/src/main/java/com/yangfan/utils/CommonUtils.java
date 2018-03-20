package com.yangfan.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.webkit.WebView;
import android.widget.EditText;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;

/**
 * Created by yangfan
 * nrainyseason@163.com
 */

public class CommonUtils {
    private static long lastClickTime;
    public static final String DEFAULT_FORMAT = "yyyy-MM-dd";

    /**
     * encodeBase64File:(将文件转成base64 字符串). <br/>
     *
     * @param path 文件路径
     * @return
     * @throws Exception
     * @author guhaizhou@126.com
     * @since JDK 1.6
     */
    public static String encodeBase64File(String path) {
        File file = new File(path);
        byte[] buffer = new byte[0];
        try {
            FileInputStream inputFile = new FileInputStream(file);
            buffer = new byte[(int) file.length()];
            inputFile.read(buffer);
            inputFile.close();
        } catch (IOException e) {
            return null;
        }
        return Base64.encodeToString(buffer, Base64.DEFAULT);
    }

    /**
     * 防止频繁点击
     *
     * @returna
     */
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    private static long lastClickTimeWithCustom;

    /**
     * 防止频繁点击
     *
     * @returna
     */
    public static boolean isFastDoubleClick(double second) {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTimeWithCustom;
        if (0 < timeD && timeD < second * 1000) {
            return true;
        }
        lastClickTimeWithCustom = time;
        return false;
    }

    /**
     * 如果希望格式化时间为12小时制的，则使用hh:mm:ss
     * 而如果希望格式化时间为24小时制的，则使用HH:mm:ss
     * String  format   ;"yyyy-MM-dd HH:mm:ss"
     */
    public static String date2String(Date time, String format) {
        try {
            SimpleDateFormat dateformat = new SimpleDateFormat(format);
            return dateformat.format(time);
        } catch (Exception e) {
        }
        return "";
    }

    /**
     * 如果希望格式化时间为12小时制的，则使用hh:mm:ss
     * 而如果希望格式化时间为24小时制的，则使用HH:mm:ss
     * String  pattern   ;"yyyy-MM-dd HH:mm:ss"
     * srcDate 格式要与 srcDatePattern 一致
     */
    public static String getDateFormat(String srcDate, String srcDatePattern, String pattern) {
        if (TextUtils.isEmpty(srcDate))
            return "";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            return format.format(new SimpleDateFormat(srcDatePattern).parse(srcDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 判断网络是否可用
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    public static String getUUID() {
        UUID uuid = UUID.randomUUID();
        String uniqueId = uuid.toString();
        return uniqueId;
    }

    /**
     * 获取版本名称
     */
    public static String getVersionName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packInfo;
        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取版本号
     */
    public static int getVersionCode(Context context) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packInfo;
        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 设置当弹出Aletrdialog是window的背景色深度
     */
    public static void setAlterdialogWindowAlpha(Window window) {
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.dimAmount = 0.8f;
        window.setAttributes(lp);
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
    }

    /**
     * 获取渠道名
     *
     * @param ctx 此处习惯性的设置为activity，实际上context就可以
     * @return 如果没有获取成功，那么返回值为空
     */
    public static String getChannelName(Context ctx) {
        if (ctx == null) {
            return null;
        }
        String channelName = null;
        try {
            PackageManager packageManager = ctx.getPackageManager();
            if (packageManager != null) {
                //注意此处为ApplicationInfo 而不是 ActivityInfo,因为友盟设置的meta-data是在application标签中，而不是某activity标签中，所以用ApplicationInfo
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(ctx.getPackageName(), PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        //此处这样写的目的是为了在debug模式下也能获取到渠道号，如果用getString的话只能在Release下获取到。
                        channelName = applicationInfo.metaData.get("UMENG_CHANNEL") + "";
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return channelName;

    }

    public static Bitmap readBitMap(Context context, int resId) {
        try {
            BitmapFactory.Options opt = new BitmapFactory.Options();
            opt.inPreferredConfig = Bitmap.Config.ARGB_8888;
            opt.inPurgeable = true;
            opt.inInputShareable = true;
//            opt.inSampleSize = 4;
            // 获取资源图片
            InputStream is = context.getResources().openRawResource(resId);
            return BitmapFactory.decodeStream(is, null, opt);
        } catch (Resources.NotFoundException e) {
            return null;
        }
    }

    /**
     * 获得状态栏的高度
     *
     * @param context
     * @return
     */
    public static int getStatusHeight(Context context) {

        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    /**
     * 获取屏幕的宽度
     *
     * @param mContext
     * @return
     */
    public static int getScreenWidth(Context mContext) {
        DisplayMetrics metric = new DisplayMetrics();
        ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric.widthPixels;
    }

    /**
     * 获取屏幕的高度
     *
     * @param mContext
     * @return
     */
    public static int getScreenHeight(Context mContext) {
        DisplayMetrics metric = new DisplayMetrics();
        ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric.heightPixels;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     * @param fontScale （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int px2sp(float pxValue, float fontScale) {
        return (int) (pxValue / fontScale + 0.5f);
    }


    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static void openActicity(Context context, Class<?> class1,
                                    Bundle pBundle) {
        openActicity(context, class1, pBundle, 0);

    }

    /**
     * 跳转到新的activity
     *
     * @param context context
     * @param class1  目标Activity
     * @param pBundle
     * @param flags   启动模式
     */
    public static void openActicity(Context context, Class<?> class1,
                                    Bundle pBundle, int flags) {
        Intent intent = new Intent(context, class1);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        if (flags > 0) {
            intent.setFlags(flags);
        }
        context.startActivity(intent);

    }

    /**
     * 跳转到新的activity
     *
     * @param activity      现在的Activity
     * @param class1        目标Activity
     * @param pBundle
     * @param closeActivity 是否关闭当前Activity
     */
    public static void openActicity(Activity activity, Class<?> class1,
                                    Bundle pBundle, boolean closeActivity) {
        Intent intent = new Intent(activity, class1);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        activity.startActivity(intent);
        // activity.overridePendingTransition(R.anim.slide_in_right,
        // R.anim.slide_out_left);
        if (closeActivity) {
            activity.finish();
        }

    }

    /**
     * url转换成path
     */
    public static String getImagePathFromUri(Uri fileUrl, Activity a) {

        return GetPathFromUri4kitkat.getPath(a, fileUrl);

    }

    /**
     * 对double数据进行取精度, 保留2位小数, 精度取值方式 BigDecimal.ROUND_HALF_UP
     *
     * @param value double数据
     * @return 精度计算后的数据
     */
    public static double round2(double value) {
        return round(value, 2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 对double数据进行取精度
     *
     * @param value        double数据
     * @param scale        精度位数(保留的小数位数)
     * @param roundingMode 精度取值方式
     * @return 精度计算后的数据
     */
    public static double round(double value, int scale, int roundingMode) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(scale, roundingMode);
        return bd.doubleValue();
    }

//    roundingMode的取值有一下几个：
//
//    ROUND_CEILING
//            大于等于该数的那个最近值
//    ROUND_DOWN
//    正数是小于等于该数的那个最近数，负数是大于等于该数的那个最近数
//            ROUND_FLOOR
//    小于等于该数的那个值
//            ROUND_HALF_DOWN
//    五舍六入
//            ROUND_HALF_EVEN
//    向（距离）最近的一边舍入，除非两边（的距离）是相等,如果是这样，如果保留位数是奇数，使用ROUND_HALF_UP ，如果是偶数，使用ROUND_HALF_DOWN
//            ROUND_HALF_UP
//    四舍五入
//            ROUND_UNNECESSARY
//    计算结果是精确的，不需要舍入模式
//            ROUND_UP
//    和ROUND_DOWN相反

    public static boolean isWebchatAvaliable(Activity activity) {
        //检测手机上是否安装了微信
        try {
            activity.getPackageManager().getPackageInfo("com.tencent.mm", PackageManager.GET_ACTIVITIES);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    /**
     * 密码显示或隐藏 （切换）
     */
    public static void showOrHide(EditText etPassword) {
        //记住光标开始的位置
        String v = etPassword.getText().toString();
        etPassword.setText("");
        if (etPassword.getInputType() != (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {//隐藏密码
            etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        } else {//显示密码
            etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        }
        etPassword.setText(v);
        etPassword.setSelection(v.length());

    }


    public static String getPullToRefreshTime(Context context) {
        String label = DateUtils.formatDateTime(context,
                System.currentTimeMillis(),
                DateUtils.FORMAT_SHOW_TIME
                        | DateUtils.FORMAT_SHOW_DATE
                        | DateUtils.FORMAT_ABBREV_ALL);
        return label;
    }

    public static Date localToGMT(Date date) {
        if (date == null) return null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        return new Date(sdf.format(date));
    }

    /**
     * 当地时间 ---> UTC时间
     *
     * @return
     */
    public static String Local2UTC() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("gmt"));
        return sdf.format(new Date());
    }

    /**
     * UTC时间 ---> 当地时间
     *
     * @param utcTime UTC时间
     * @return
     */
    public static String utc2Local(String utcTime) {
        SimpleDateFormat utcFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//UTC时间格式
        utcFormater.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date gpsUTCDate = null;
        try {
            gpsUTCDate = utcFormater.parse(utcTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat localFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//当地时间格式
        localFormater.setTimeZone(TimeZone.getDefault());
        return localFormater.format(gpsUTCDate.getTime());
    }

    public static int getTextWidth(String text, Paint paint) {
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        return bounds.left + bounds.width();
    }

    public static int getTextHeight(String text, Paint paint) {
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        return bounds.bottom + bounds.height();
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha 屏幕透明度0.0-1.0 1表示完全不透明
     */
    public static void setBackgroundAlpha(Activity activity, float bgAlpha) {
        if (activity == null) return;
        WindowManager.LayoutParams lp = activity.getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        activity.getWindow().setAttributes(lp);
    }

    // 销毁WebView
    public static void onDestroyWebView(WebView mWebView) {
        if (mWebView != null) {
            // 如果先调用destroy()方法，则会命中if (isDestroyed()) return;这一行代码，需要先onDetachedFromWindow()，再
            // destory()
            ViewParent parent = mWebView.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(mWebView);
            }
            mWebView.stopLoading();
            // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
            mWebView.getSettings().setJavaScriptEnabled(false);
            mWebView.clearHistory();
            mWebView.clearView();
            mWebView.removeAllViews();
            mWebView.destroy();
        }
    }

    /**
     * 动画移动view并摆放至相应的位置
     *
     * @param view               控件
     * @param xFromDeltaDistance x起始位置的偏移量
     * @param xToDeltaDistance   x终止位置的偏移量
     * @param yFromDeltaDistance y起始位置的偏移量
     * @param yToDeltaDistance   y终止位置的偏移量
     * @param duration           动画的播放时间
     * @param delay              延迟播放时间
     * @param isBack             是否需要返回到开始位置
     */
    public static void moveViewWithAnimation(final View view, final float xFromDeltaDistance, final float xToDeltaDistance, final float yFromDeltaDistance, final float yToDeltaDistance, int duration, int delay, final boolean isBack) {
        //创建位移动画
        TranslateAnimation ani = new TranslateAnimation(xFromDeltaDistance, xToDeltaDistance, yFromDeltaDistance, yToDeltaDistance);
        ani.setInterpolator(new AccelerateInterpolator());//设置加速器
        ani.setDuration(duration);//设置动画时间
        ani.setStartOffset(delay);//设置动画延迟时间
        //监听动画播放状态
        ani.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                int deltaX = (int) (xToDeltaDistance - xFromDeltaDistance);
                int deltaY = (int) (yToDeltaDistance - yFromDeltaDistance);
                int layoutX = view.getLeft();
                int layoutY = view.getTop();
                int tempWidth = view.getWidth();
                int tempHeight = view.getHeight();
                view.clearAnimation();
                if (!isBack) {
                    layoutX += deltaX;
                    layoutY += deltaY;
                    view.layout(layoutX, layoutY, layoutX + tempWidth, layoutY + tempHeight);
                } else {
                    view.layout(layoutX, layoutY, layoutX + tempWidth, layoutY + tempHeight);
                }
            }
        });
        view.startAnimation(ani);
    }
}
