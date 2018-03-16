package com.yangfan.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yangfan.commonkitlibrary.R;
import com.yangfan.commonkitlibrary.R.id;
import com.yangfan.commonkitlibrary.R.style;
import com.yangfan.utils.CommonUtils;

/**
 * Created by yangfan
 * nrainyseason@163.com
 */

public class CustomDialog  extends Dialog {
    public static int width;

    public CustomDialog(Context context, int theme) {
        super(context, theme);
    }

    public CustomDialog(Context context) {
        super(context);
    }

    public static class Builder {
        private Context context;
        private String title;
        private String message;
        private String positiveButtonText;
        private String negativeButtonText;
        private View contentView;
        private double screenRatioX = 0.8D;
        private OnClickListener positiveButtonClickListener;
        private OnClickListener negativeButtonClickListener;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setScreenRatioX(double screenRatioX) {
            this.screenRatioX = screenRatioX;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setMessage(int message) {
            this.message = (String)this.context.getText(message);
            return this;
        }

        public Builder setTitle(int title) {
            this.title = (String)this.context.getText(title);
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        public Builder setPositiveButton(int positiveButtonText, OnClickListener listener) {
            this.positiveButtonText = (String)this.context.getText(positiveButtonText);
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setPositiveButton(String positiveButtonText, OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(int negativeButtonText, OnClickListener listener) {
            this.negativeButtonText = (String)this.context.getText(negativeButtonText);
            this.negativeButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(String negativeButtonText, OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }

        public CustomDialog create() {
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final CustomDialog dialog = new CustomDialog(this.context, style.progress_dialog);
            View layout = inflater.inflate(R.layout.pop_normal, (ViewGroup)null);
            dialog.addContentView(layout, new ViewGroup.LayoutParams(-1, -2));
            if(this.title != null) {
                ((TextView)layout.findViewById(id.tv_title)).setText(this.title);
            } else {
                layout.findViewById(id.tv_title).setVisibility(View.GONE);
            }

            if(this.message != null) {
                ((TextView)layout.findViewById(id.tv_content)).setText(this.message);
            } else {
                layout.findViewById(id.tv_content).setVisibility(View.GONE);
            }

            if(this.contentView != null) {
                ((LinearLayout)layout.findViewById(id.lay_content)).removeAllViewsInLayout();
                ((LinearLayout)layout.findViewById(id.lay_content)).addView(this.contentView);
            }

            if(this.positiveButtonText != null) {
                ((TextView)layout.findViewById(id.tv_ok)).setText(this.positiveButtonText);
                if(this.positiveButtonClickListener != null) {
                    layout.findViewById(id.tv_ok).setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            Builder.this.positiveButtonClickListener.onClick(dialog, -1);
                        }
                    });
                }
            } else {
                layout.findViewById(id.tv_ok).setVisibility(View.GONE);
            }

            if(this.negativeButtonText != null) {
                ((TextView)layout.findViewById(id.tv_cancel)).setText(this.negativeButtonText);
                if(this.negativeButtonClickListener != null) {
                    layout.findViewById(id.tv_cancel).setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            Builder.this.negativeButtonClickListener.onClick(dialog, -2);
                        }
                    });
                }
            } else {
                layout.findViewById(id.tv_cancel).setVisibility(View.GONE);
            }

            if(this.positiveButtonText == null || this.negativeButtonText == null) {
                layout.findViewById(id.v_line).setVisibility(View.GONE);
            }

            if(this.positiveButtonText == null && this.negativeButtonText == null) {
                layout.findViewById(id.v_line2).setVisibility(View.GONE);
                layout.findViewById(id.lay_btn).setVisibility(View.GONE);
            }

            dialog.setContentView(layout);
            CustomDialog.width = (int)((double) CommonUtils.getScreenWidth(this.context) * this.screenRatioX);
            android.view.WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
            params.width = CustomDialog.width;
            dialog.getWindow().setAttributes(params);
            return dialog;
        }
    }
}
