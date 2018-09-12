package com.yangfan.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.ViewGroup.LayoutParams;

import com.yangfan.commonkitlibrary.R;
import com.yangfan.utils.CommonUtils;

/**
 * Created by yangfan
 * nrainyseason@163.com
 */

public class CustomDialog extends Dialog {

    public CustomDialog(Context context, int theme) {
        super(context, theme);
    }

    public CustomDialog(Context context) {
        super(context);
    }

    /**
     * Helper class for creating a custom dialog
     */
    public static class Builder {
        LinearLayout bgLayout;
        private TextView tv_ok, tv_cancle;
        private Context context;
        private String title;
        private String message;
        private String positiveButtonText;
        private String negativeButtonText;
        private View contentView;
        private double screenRatioX = 0.8;//dialog 宽度 占屏比  0-1

        private OnClickListener positiveButtonClickListener, negativeButtonClickListener;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setScreenRatioX(double screenRatioX) {
            this.screenRatioX = screenRatioX;
            return this;
        }

        /**
         * Set the Dialog message from String
         *
         * @param message
         * @return
         */
        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        /**
         * Set the Dialog message from resource
         *
         * @param message
         * @return
         */
        public Builder setMessage(int message) {
            this.message = (String) context.getText(message);
            return this;
        }

        /**
         * Set the Dialog title from resource
         *
         * @param title
         * @return
         */
        public Builder setTitle(int title) {
            this.title = (String) context.getText(title);
            return this;
        }

        /**
         * Set the Dialog title from String
         *
         * @param title
         * @return
         */
        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        /**
         * Set a custom content view for the Dialog.
         * If a message is set, the contentView is not
         * added to the Dialog...
         *
         * @param v
         * @return
         */
        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        /**
         * Set the positive button resource and it's listener
         *
         * @param positiveButtonText
         * @param listener
         * @return
         */
        public Builder setPositiveButton(int positiveButtonText, OnClickListener listener) {
            this.positiveButtonText = (String) context.getText(positiveButtonText);
            this.positiveButtonClickListener = listener;
            return this;
        }

        /**
         * Set the positive button text and it's listener
         *
         * @param positiveButtonText
         * @param listener
         * @return
         */
        public Builder setPositiveButton(String positiveButtonText, OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }

        /**
         * Set the negative button resource and it's listener
         *
         * @param negativeButtonText
         * @param listener
         * @return
         */
        public Builder setNegativeButton(int negativeButtonText, OnClickListener listener) {
            this.negativeButtonText = (String) context.getText(negativeButtonText);
            this.negativeButtonClickListener = listener;
            return this;
        }

        /**
         * Set the negative button text and it's listener
         *
         * @param negativeButtonText
         * @param listener
         * @return
         */
        public Builder setNegativeButton(String negativeButtonText, OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }

        /**
         * get the negative button
         *
         * @return
         */
        public TextView getNegativeButton() {
            if (tv_ok != null) {
                return tv_cancle;
            } else {
                throw new RuntimeException("getNegativeButton must be after create method");
            }
        }

        /**
         * get the positive button
         *
         * @return
         */
        public TextView getPositiveButton() {
            if (tv_ok != null) {
                return tv_ok;
            } else {
                throw new RuntimeException("getPositiveButton must be after create method");
            }
        }

        /**
         * set dialog background
         *
         * @param res
         */
        public void setDialogBackGround(int res) {
            if (bgLayout != null) {
                bgLayout.setBackgroundResource(res);
            } else {
                throw new RuntimeException("setDialogBackGround must be after create method");
            }
        }

        public LinearLayout getDialogBackLayout() {
            if (bgLayout != null) {
                return bgLayout;
            } else {
                throw new RuntimeException("getDialogBackLayout must be after create method");
            }
        }

        /**
         * Create the custom dialog
         */
        public CustomDialog create() {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            final CustomDialog dialog = new CustomDialog(context, R.style.progress_dialog);

            View layout = inflater.inflate(R.layout.pop_normal, null);
            dialog.addContentView(layout, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
            bgLayout = (LinearLayout) layout.findViewById(R.id.dialog_bg_layout);
            // set the dialog title
            if (title != null) {
                ((TextView) layout.findViewById(R.id.tv_title)).setText(title);
            } else {
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.tv_title).setVisibility(View.GONE);
            }

            // set the content message
            if (message != null) {
                ((TextView) layout.findViewById(R.id.tv_content)).setText(message);
            } else {
                layout.findViewById(R.id.tv_content).setVisibility(View.GONE);
            }
            if (contentView != null) {
                // if no message set
                // add the contentView to the dialog body
                ((LinearLayout) layout.findViewById(R.id.lay_content)).removeAllViewsInLayout();
//                ((LinearLayout) layout.findViewById(R.id.lay_content)).addView(contentView, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
                ((LinearLayout) layout.findViewById(R.id.lay_content)).addView(contentView);

            }

            // set the confirm button
            tv_ok = ((TextView) layout.findViewById(R.id.tv_ok));
            if (positiveButtonText != null) {
                tv_ok.setText(positiveButtonText);
                if (positiveButtonClickListener != null) {
                    tv_ok.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            positiveButtonClickListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                        }
                    });
                }
            } else {
                // if no confirm button just set the visibility to GONE
                tv_ok.setVisibility(View.GONE);
            }

            // set the cancel button
            tv_cancle = ((TextView) layout.findViewById(R.id.tv_cancel));
            if (negativeButtonText != null) {
                tv_cancle.setText(negativeButtonText);
                if (negativeButtonClickListener != null) {
                    tv_cancle.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            negativeButtonClickListener.onClick(dialog, DialogInterface.BUTTON_NEGATIVE);
                        }
                    });
                }
            } else {
                // if no confirm button just set the visibility to GONE
                tv_cancle.setVisibility(View.GONE);
            }

            if (positiveButtonText == null || negativeButtonText == null)
                layout.findViewById(R.id.v_line).setVisibility(View.GONE);
            if (positiveButtonText == null && negativeButtonText == null) {
                layout.findViewById(R.id.v_line2).setVisibility(View.GONE);
                layout.findViewById(R.id.lay_btn).setVisibility(View.GONE);
            }


            dialog.setContentView(layout);
            CustomDialog.width = (int) (CommonUtils.getScreenWidth(context) * screenRatioX);
            WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
            params.width = CustomDialog.width;
//            params.height = 1000;
            dialog.getWindow().setAttributes(params);

            return dialog;
        }

    }

    public static int width;
}
