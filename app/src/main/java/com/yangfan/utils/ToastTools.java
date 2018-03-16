package com.yangfan.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by yangfan
 * nrainyseason@163.com
 */

public class ToastTools {
    static final String TAG = "ToastTools";
    private Toast toast = null;
    private static ToastTools sToastTools = new ToastTools();

    public ToastTools() {
    }

    public static synchronized void showToast(Context context, String content) {
        cancelToast();
        if(sToastTools.toast == null) {
            sToastTools.toast = Toast.makeText(context, content, 0);
            sToastTools.toast.setGravity(17, 0, 0);
            sToastTools.toast.show();
        }

    }

    public static synchronized void cancelToast() {
        if(null != sToastTools.toast) {
            sToastTools.toast.cancel();
        }

        sToastTools.toast = null;
    }
}
