package com.yangfan.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources.NotFoundException;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
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
    private static long lastClickTimeWithCustom;

    public CommonUtils() {
    }

    public static String encodeBase64File(String path) {
        File file = new File(path);
        byte[] buffer = new byte[0];

        try {
            FileInputStream inputFile = new FileInputStream(file);
            buffer = new byte[(int)file.length()];
            inputFile.read(buffer);
            inputFile.close();
        } catch (IOException var4) {
            return null;
        }

        return Base64.encodeToString(buffer, 0);
    }

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if(0L < timeD && timeD < 500L) {
            return true;
        } else {
            lastClickTime = time;
            return false;
        }
    }

    public static boolean isFastDoubleClick(double second) {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTimeWithCustom;
        if(0L < timeD && (double)timeD < second * 1000.0D) {
            return true;
        } else {
            lastClickTimeWithCustom = time;
            return false;
        }
    }

    public static String date2String(Date time, String format) {
        try {
            SimpleDateFormat dateformat = new SimpleDateFormat(format);
            return dateformat.format(time);
        } catch (Exception var3) {
            return "";
        }
    }

    public static String getDateFormat(String srcDate, String srcDatePattern, String pattern) {
        if(TextUtils.isEmpty(srcDate)) {
            return "";
        } else {
            SimpleDateFormat format = new SimpleDateFormat(pattern);

            try {
                return format.format((new SimpleDateFormat(srcDatePattern)).parse(srcDate));
            } catch (ParseException var5) {
                var5.printStackTrace();
                return "";
            }
        }
    }

    public static boolean isNetworkConnected(Context context) {
        if(context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if(mNetworkInfo != null) {
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

    public static String getVersionName(Context context) {
        PackageManager packageManager = context.getPackageManager();

        try {
            PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packInfo.versionName;
        } catch (NameNotFoundException var4) {
            var4.printStackTrace();
            return null;
        }
    }

    public static int getVersionCode(Context context) {
        PackageManager packageManager = context.getPackageManager();

        try {
            PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packInfo.versionCode;
        } catch (NameNotFoundException var4) {
            var4.printStackTrace();
            return 0;
        }
    }

    public static void setAlterdialogWindowAlpha(Window window) {
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.dimAmount = 0.8F;
        window.setAttributes(lp);
        window.addFlags(2);
        window.clearFlags(131072);
    }

    public static String getChannelName(Context ctx) {
        if(ctx == null) {
            return null;
        } else {
            String channelName = null;

            try {
                PackageManager packageManager = ctx.getPackageManager();
                if(packageManager != null) {
                    ApplicationInfo applicationInfo = packageManager.getApplicationInfo(ctx.getPackageName(), PackageManager.GET_META_DATA);
                    if(applicationInfo != null && applicationInfo.metaData != null) {
                        channelName = applicationInfo.metaData.get("UMENG_CHANNEL") + "";
                    }
                }
            } catch (NameNotFoundException var4) {
                var4.printStackTrace();
            }

            return channelName;
        }
    }

    public static Bitmap readBitMap(Context context, int resId) {
        try {
            Options opt = new Options();
            opt.inPreferredConfig = Config.ARGB_8888;
            opt.inPurgeable = true;
            opt.inInputShareable = true;
            InputStream is = context.getResources().openRawResource(resId);
            return BitmapFactory.decodeStream(is, (Rect)null, opt);
        } catch (NotFoundException var4) {
            return null;
        }
    }

    public static int getStatusHeight(Context context) {
        int statusHeight = -1;

        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        return statusHeight;
    }

    public static int getScreenWidth(Context mContext) {
        DisplayMetrics metric = new DisplayMetrics();
        ((Activity)mContext).getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric.widthPixels;
    }

    public static int getScreenHeight(Context mContext) {
        DisplayMetrics metric = new DisplayMetrics();
        ((Activity)mContext).getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric.heightPixels;
    }

    public static int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dpValue * scale + 0.5F);
    }

    public static int px2sp(float pxValue, float fontScale) {
        return (int)(pxValue / fontScale + 0.5F);
    }

    public static int px2dip(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int)(pxValue / scale + 0.5F);
    }

    public static void openActicity(Context context, Class<?> class1, Bundle pBundle) {
        openActicity(context, class1, pBundle, 0);
    }

    public static void openActicity(Context context, Class<?> class1, Bundle pBundle, int flags) {
        Intent intent = new Intent(context, class1);
        if(pBundle != null) {
            intent.putExtras(pBundle);
        }

        if(flags > 0) {
            intent.setFlags(flags);
        }

        context.startActivity(intent);
    }

    public static void openActicity(Activity activity, Class<?> class1, Bundle pBundle, boolean closeActivity) {
        Intent intent = new Intent(activity, class1);
        if(pBundle != null) {
            intent.putExtras(pBundle);
        }

        activity.startActivity(intent);
        if(closeActivity) {
            activity.finish();
        }

    }

    public static String getImagePathFromUri(Uri fileUrl, Activity a) {
        return GetPathFromUri4kitkat.getPath(a, fileUrl);
    }

    public static double round2(double value) {
        return round(value, 2, 4);
    }

    public static double round(double value, int scale, int roundingMode) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(scale, roundingMode);
        return bd.doubleValue();
    }

    public static boolean isWebchatAvaliable(Activity activity) {
        try {
            activity.getPackageManager().getPackageInfo("com.tencent.mm", PackageManager.GET_ACTIVITIES);
            return true;
        } catch (Exception var2) {
            return false;
        }
    }

    public static void showOrHide(EditText etPassword) {
        String v = etPassword.getText().toString();
        etPassword.setText("");
        if(etPassword.getInputType() != 129) {
            etPassword.setInputType(129);
        } else {
            etPassword.setInputType(145);
        }

        etPassword.setText(v);
        etPassword.setSelection(v.length());
    }

    public static String getPullToRefreshTime(Context context) {
        String label = DateUtils.formatDateTime(context, System.currentTimeMillis(), 524305);
        return label;
    }

    public static Date localToGMT(Date date) {
        if(date == null) {
            return null;
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            return new Date(sdf.format(date));
        }
    }

    public static String Local2UTC() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("gmt"));
        return sdf.format(new Date());
    }

    public static String utc2Local(String utcTime) {
        SimpleDateFormat utcFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        utcFormater.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date gpsUTCDate = null;

        try {
            gpsUTCDate = utcFormater.parse(utcTime);
        } catch (ParseException var4) {
            var4.printStackTrace();
        }

        SimpleDateFormat localFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        localFormater.setTimeZone(TimeZone.getDefault());
        return localFormater.format(Long.valueOf(gpsUTCDate.getTime()));
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

    public static void setBackgroundAlpha(Activity activity, float bgAlpha) {
        if(activity != null) {
            WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
            lp.alpha = bgAlpha;
            activity.getWindow().setAttributes(lp);
        }
    }

    public static void onDestroyWebView(WebView mWebView) {
        if(mWebView != null) {
            ViewParent parent = mWebView.getParent();
            if(parent != null) {
                ((ViewGroup)parent).removeView(mWebView);
            }

            mWebView.stopLoading();
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
