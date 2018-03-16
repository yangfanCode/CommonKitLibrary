package com.yangfan.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.graphics.drawable.LayerDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Scroller;

import com.bumptech.glide.Glide;
import com.yangfan.commonkitlibrary.R.styleable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangfan
 * nrainyseason@163.com
 */

public class CustomBannerView extends RelativeLayout {
    private CustomViewPager pager;
    private LinearLayout indicatorContainer;
    private Drawable unSelectedDrawable;
    private Drawable selectedDrawable;
    private Drawable maskDrawable;
    private int WHAT_AUTO_PLAY = 5000;
    private boolean isAutoPlay = true;
    private boolean isIndicatorVisible = true;
    private int itemCount;
    private int maskStartColor = 0;
    private int maskEndColor = 0;
    private int selectedIndicatorColor = -65536;
    private int unSelectedIndicatorColor = -2004318072;
    private Shape indicatorShape;
    private int selectedIndicatorHeight;
    private int selectedIndicatorWidth;
    private int unSelectedIndicatorHeight;
    private int unSelectedIndicatorWidth;
    private Position indicatorPosition;
    private int autoPlayDuration;
    private int scrollDuration;
    private int indicatorSpace;
    private int indicatorMargin;
    private int cornerRadii;
    private int placeholder;
    private int errorHolder;
    private ScaleType scaleType;
    private boolean isLoop;
    private OnBannerItemClickListener onBannerItemClickListener;
    private BannerOnPageChangeListener bannerOnPageChangeListener;
    private Handler handler;


    public CustomBannerView(Context context) {
        super(context);
        this.indicatorShape = Shape.oval;
        this.selectedIndicatorHeight = 6;
        this.selectedIndicatorWidth = 6;
        this.unSelectedIndicatorHeight = 6;
        this.unSelectedIndicatorWidth = 6;
        this.indicatorPosition = Position.centerBottom;
        this.autoPlayDuration = 2500;
        this.scrollDuration = 900;
        this.indicatorSpace = 3;
        this.indicatorMargin = 10;
        this.cornerRadii = 0;
        this.scaleType = ScaleType.CENTER_CROP;
        this.isLoop = true;
        this.handler = new Handler(new Handler.Callback() {
            public boolean handleMessage(Message msg) {
                if(msg.what == CustomBannerView.this.WHAT_AUTO_PLAY && CustomBannerView.this.pager != null) {
                    CustomBannerView.this.pager.setCurrentItem(CustomBannerView.this.pager.getCurrentItem() + 1, true);
                    CustomBannerView.this.handler.sendEmptyMessageDelayed(CustomBannerView.this.WHAT_AUTO_PLAY, (long)CustomBannerView.this.autoPlayDuration);
                }

                return false;
            }
        });
        this.init((AttributeSet)null, 0);
    }

    public CustomBannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.indicatorShape = Shape.oval;
        this.selectedIndicatorHeight = 6;
        this.selectedIndicatorWidth = 6;
        this.unSelectedIndicatorHeight = 6;
        this.unSelectedIndicatorWidth = 6;
        this.indicatorPosition = Position.centerBottom;
        this.autoPlayDuration = 2500;
        this.scrollDuration = 900;
        this.indicatorSpace = 3;
        this.indicatorMargin = 10;
        this.cornerRadii = 0;
        this.scaleType = ScaleType.CENTER_CROP;
        this.isLoop = true;
        this.handler = new Handler(new Handler.Callback() {
            public boolean handleMessage(Message msg) {
                if(msg.what == CustomBannerView.this.WHAT_AUTO_PLAY && CustomBannerView.this.pager != null) {
                    CustomBannerView.this.pager.setCurrentItem(CustomBannerView.this.pager.getCurrentItem() + 1, true);
                    CustomBannerView.this.handler.sendEmptyMessageDelayed(CustomBannerView.this.WHAT_AUTO_PLAY, (long)CustomBannerView.this.autoPlayDuration);
                }

                return false;
            }
        });
        this.init(attrs, 0);
    }

    public CustomBannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.indicatorShape = Shape.oval;
        this.selectedIndicatorHeight = 6;
        this.selectedIndicatorWidth = 6;
        this.unSelectedIndicatorHeight = 6;
        this.unSelectedIndicatorWidth = 6;
        this.indicatorPosition = Position.centerBottom;
        this.autoPlayDuration = 2500;
        this.scrollDuration = 900;
        this.indicatorSpace = 3;
        this.indicatorMargin = 10;
        this.cornerRadii = 0;
        this.scaleType = ScaleType.CENTER_CROP;
        this.isLoop = true;
        this.handler = new Handler(new Handler.Callback() {
            public boolean handleMessage(Message msg) {
                if(msg.what == CustomBannerView.this.WHAT_AUTO_PLAY && CustomBannerView.this.pager != null) {
                    CustomBannerView.this.pager.setCurrentItem(CustomBannerView.this.pager.getCurrentItem() + 1, true);
                    CustomBannerView.this.handler.sendEmptyMessageDelayed(CustomBannerView.this.WHAT_AUTO_PLAY, (long)CustomBannerView.this.autoPlayDuration);
                }

                return false;
            }
        });
        this.init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyle) {
        TypedArray array = this.getContext().obtainStyledAttributes(attrs, styleable.BannerLayoutStyle, defStyle, 0);
        this.selectedIndicatorColor = array.getColor(styleable.BannerLayoutStyle_selectedIndicatorColor, this.selectedIndicatorColor);
        this.unSelectedIndicatorColor = array.getColor(styleable.BannerLayoutStyle_unSelectedIndicatorColor, this.unSelectedIndicatorColor);
        this.maskStartColor = array.getColor(styleable.BannerLayoutStyle_maskStartColor, this.maskStartColor);
        this.maskEndColor = array.getColor(styleable.BannerLayoutStyle_maskEndColor, this.maskEndColor);
        int shape = array.getInt(styleable.BannerLayoutStyle_indicatorShape, Shape.oval.ordinal());
        Shape[] var5 = Shape.values();
        int var6 = var5.length;

        int var7;
        for(var7 = 0; var7 < var6; ++var7) {
            Shape shape1 = var5[var7];
            if(shape1.ordinal() == shape) {
                this.indicatorShape = shape1;
                break;
            }
        }

        this.selectedIndicatorHeight = (int)array.getDimension(styleable.BannerLayoutStyle_selectedIndicatorHeight, (float)this.selectedIndicatorHeight);
        this.selectedIndicatorWidth = (int)array.getDimension(styleable.BannerLayoutStyle_selectedIndicatorWidth, (float)this.selectedIndicatorWidth);
        this.unSelectedIndicatorHeight = (int)array.getDimension(styleable.BannerLayoutStyle_unSelectedIndicatorHeight, (float)this.unSelectedIndicatorHeight);
        this.unSelectedIndicatorWidth = (int)array.getDimension(styleable.BannerLayoutStyle_unSelectedIndicatorWidth, (float)this.unSelectedIndicatorWidth);
        int position = array.getInt(styleable.BannerLayoutStyle_indicatorPosition, Position.centerBottom.ordinal());
        Position[] var12 = Position.values();
        var7 = var12.length;

        for(int var15 = 0; var15 < var7; ++var15) {
            Position position1 = var12[var15];
            if(position == position1.ordinal()) {
                this.indicatorPosition = position1;
            }
        }

        this.indicatorSpace = (int)array.getDimension(styleable.BannerLayoutStyle_indicatorSpace, (float)this.indicatorSpace);
        this.indicatorMargin = (int)array.getDimension(styleable.BannerLayoutStyle_indicatorMargin, (float)this.indicatorMargin);
        this.autoPlayDuration = array.getInt(styleable.BannerLayoutStyle_autoPlayDuration, this.autoPlayDuration);
        this.scrollDuration = array.getInt(styleable.BannerLayoutStyle_scrollDuration, this.scrollDuration);
        this.isAutoPlay = array.getBoolean(styleable.BannerLayoutStyle_isAutoPlay, this.isAutoPlay);
        this.placeholder = array.getResourceId(styleable.BannerLayoutStyle_placeholder, this.placeholder);
        this.errorHolder = array.getResourceId(styleable.BannerLayoutStyle_errorHolder, this.errorHolder);
        this.isIndicatorVisible = array.getBoolean(styleable.BannerLayoutStyle_isIndicatorVisible, this.isIndicatorVisible);
        this.cornerRadii = (int)array.getDimension(styleable.BannerLayoutStyle_cornerRadii, 0.0F);
        array.recycle();
        GradientDrawable unSelectedGradientDrawable = new GradientDrawable();
        GradientDrawable selectedGradientDrawable = new GradientDrawable();
        switch(indicatorShape.ordinal()) {
            case 1:
                unSelectedGradientDrawable.setShape(GradientDrawable.RECTANGLE);
                selectedGradientDrawable.setShape(GradientDrawable.RECTANGLE);
                this.setCornerRadii(unSelectedGradientDrawable, (float)this.cornerRadii, (float)this.cornerRadii, (float)this.cornerRadii, (float)this.cornerRadii);
                this.setCornerRadii(selectedGradientDrawable, (float)this.cornerRadii, (float)this.cornerRadii, (float)this.cornerRadii, (float)this.cornerRadii);
                break;
            case 2:
                unSelectedGradientDrawable.setShape(GradientDrawable.OVAL);
                selectedGradientDrawable.setShape(GradientDrawable.OVAL);
        }

        unSelectedGradientDrawable.setColor(this.unSelectedIndicatorColor);
        unSelectedGradientDrawable.setSize(this.unSelectedIndicatorWidth, this.unSelectedIndicatorHeight);
        LayerDrawable unSelectedLayerDrawable = new LayerDrawable(new Drawable[]{unSelectedGradientDrawable});
        this.unSelectedDrawable = unSelectedLayerDrawable;
        selectedGradientDrawable.setColor(this.selectedIndicatorColor);
        selectedGradientDrawable.setSize(this.selectedIndicatorWidth, this.selectedIndicatorHeight);
        LayerDrawable selectedLayerDrawable = new LayerDrawable(new Drawable[]{selectedGradientDrawable});
        this.selectedDrawable = selectedLayerDrawable;
        GradientDrawable maskGradientDrawable = new GradientDrawable(Orientation.BOTTOM_TOP, new int[]{this.maskStartColor, this.maskEndColor});
        this.maskDrawable = new LayerDrawable(new Drawable[]{maskGradientDrawable});
    }

    private void setCornerRadii(GradientDrawable drawable, float r0, float r1, float r2, float r3) {
        drawable.setCornerRadii(new float[]{r0, r0, r1, r1, r2, r2, r3, r3});
    }

    public void setViewRes(List<Integer> viewRes) {
        if(viewRes != null && viewRes.size() != 0) {
            List<View> views = new ArrayList();
            this.itemCount = viewRes.size();
            if(this.itemCount < 1) {
                throw new IllegalStateException("item count not equal zero");
            } else {
                if(this.itemCount < 2) {
                    views.add(this.getImageView((Integer)((Integer)viewRes.get(0)), 0));
                    views.add(this.getImageView((Integer)((Integer)viewRes.get(0)), 0));
                    views.add(this.getImageView((Integer)((Integer)viewRes.get(0)), 0));
                } else if(this.itemCount < 3) {
                    views.add(this.getImageView((Integer)((Integer)viewRes.get(0)), 0));
                    views.add(this.getImageView((Integer)((Integer)viewRes.get(1)), 1));
                    views.add(this.getImageView((Integer)((Integer)viewRes.get(0)), 0));
                    views.add(this.getImageView((Integer)((Integer)viewRes.get(1)), 1));
                } else {
                    for(int i = 0; i < viewRes.size(); ++i) {
                        views.add(this.getImageView((Integer)viewRes.get(i), i));
                    }
                }

                this.setViews2(views);
            }
        }
    }

    @NonNull
    private ImageView getImageView(Integer res, final int position) {
        ImageView imageView = new ImageView(this.getContext());
        imageView.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if(CustomBannerView.this.onBannerItemClickListener != null) {
                    CustomBannerView.this.onBannerItemClickListener.onItemClick(position);
                }

            }
        });
        imageView.setScaleType(ScaleType.CENTER_CROP);
        Glide.with(this.getContext()).load(res).centerCrop().into(imageView);
        return imageView;
    }

    public void setViewUrls(List<String> urls) {
        if(urls != null && urls.size() != 0) {
            List<View> views = new ArrayList();
            this.itemCount = urls.size();
            if(this.itemCount < 1) {
                throw new IllegalStateException("item count not equal zero");
            } else {
                if(this.itemCount < 2) {
                    views.add(this.getImageView((String)((String)urls.get(0)), 0));
                    views.add(this.getImageView((String)((String)urls.get(0)), 0));
                    views.add(this.getImageView((String)((String)urls.get(0)), 0));
                } else if(this.itemCount < 3) {
                    views.add(this.getImageView((String)((String)urls.get(0)), 0));
                    views.add(this.getImageView((String)((String)urls.get(1)), 1));
                    views.add(this.getImageView((String)((String)urls.get(0)), 0));
                    views.add(this.getImageView((String)((String)urls.get(1)), 1));
                } else {
                    for(int i = 0; i < urls.size(); ++i) {
                        views.add(this.getImageView((String)urls.get(i), i));
                    }
                }

                this.setViews2(views);
            }
        }
    }

    @NonNull
    private ImageView getImageView(String url, final int position) {
        ImageView imageView = new ImageView(this.getContext());
        imageView.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if(CustomBannerView.this.onBannerItemClickListener != null) {
                    CustomBannerView.this.onBannerItemClickListener.onItemClick(position);
                }

            }
        });
        imageView.setScaleType(this.scaleType);
        if(this.placeholder != 0 && this.errorHolder == 0) {
            Glide.with(this.getContext()).load(url).placeholder(this.placeholder).crossFade().into(imageView);
        } else if(this.placeholder == 0 && this.errorHolder != 0) {
            Glide.with(this.getContext()).load(url).error(this.errorHolder).crossFade().into(imageView);
        } else if(this.placeholder != 0 && this.errorHolder != 0) {
            Glide.with(this.getContext()).load(url).placeholder(this.placeholder).error(this.errorHolder).crossFade().into(imageView);
        } else {
            Glide.with(this.getContext()).load(url).crossFade().into(imageView);
        }

        return imageView;
    }

    public void setScaleType(ScaleType scaleType) {
        this.scaleType = scaleType;
    }

    public void setViews(List<View> list) {
        if(list != null && list.size() >= 1) {
            this.itemCount = list.size();
            List<View> views = new ArrayList();
            if(this.itemCount < 2) {
                views.add(list.get(0));
                views.add(list.get(0));
                views.add(list.get(0));
            } else if(this.itemCount < 3) {
                views.add(list.get(0));
                views.add(list.get(0));
                views.add(list.get(1));
                views.add(list.get(1));
            } else {
                for(int i = 0; i < list.size(); ++i) {
                    views.add(list.get(i));
                }
            }

            this.initView(views);
        }
    }

    private void setViews2(List<View> views) {
        if(views != null && views.size() >= 1) {
            this.initView(views);
        }
    }

    private void initView(List<View> views) {
        this.removeAllViewsInLayout();
        this.pager = new CustomViewPager(this.getContext());
        this.addView(this.pager);
        ImageView mask = new ImageView(this.getContext());
        mask.setImageDrawable(this.maskDrawable);
        LayoutParams maskParams = new LayoutParams(-1, -1);
        this.addView(mask, maskParams);
        this.setSliderTransformDuration(this.scrollDuration);
        this.indicatorContainer = new LinearLayout(this.getContext());
        this.indicatorContainer.setGravity(16);
        LayoutParams params = new LayoutParams(-2, -2);
        switch(indicatorPosition.ordinal()) {
            case 1:
                params.addRule(14);
                params.addRule(12);
                break;
            case 2:
                params.addRule(14);
                params.addRule(10);
                break;
            case 3:
                params.addRule(9);
                params.addRule(12);
                break;
            case 4:
                params.addRule(9);
                params.addRule(10);
                break;
            case 5:
                params.addRule(11);
                params.addRule(12);
                break;
            case 6:
                params.addRule(11);
                params.addRule(10);
        }

        params.setMargins(this.indicatorMargin, this.indicatorMargin, this.indicatorMargin, this.indicatorMargin);
        this.addView(this.indicatorContainer, params);

        for(int i = 0; i < this.itemCount; ++i) {
            ImageView indicator = new ImageView(this.getContext());
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-2, -2);
            indicator.setLayoutParams(layoutParams);
            if(i != this.itemCount - 1) {
                indicator.setPadding(0, 0, this.indicatorSpace, 0);
            }

            indicator.setImageDrawable(this.unSelectedDrawable);
            this.indicatorContainer.addView(indicator);
        }

        this.indicatorContainer.setVisibility(this.isIndicatorVisible && this.itemCount > 1?VISIBLE:INVISIBLE);
        LoopPagerAdapter pagerAdapter = new LoopPagerAdapter(views);
        this.pager.setAdapter(pagerAdapter);
        int targetItemPosition = 0;
        if(this.isLoop) {
            targetItemPosition = 1073741823 - 1073741823 % this.itemCount;
        }

        this.pager.setCurrentItem(targetItemPosition);
        this.switchIndicator(targetItemPosition % this.itemCount);
        this.pager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            public void onPageSelected(int position) {
                CustomBannerView.this.switchIndicator(position % CustomBannerView.this.itemCount);
                if(CustomBannerView.this.bannerOnPageChangeListener != null) {
                    CustomBannerView.this.bannerOnPageChangeListener.onPageSelected(position % CustomBannerView.this.itemCount);
                }

            }
        });
        this.startAutoPlay();
    }

    public void setSliderTransformDuration(int duration) {
        try {
            Field mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            FixedSpeedScroller scroller = new FixedSpeedScroller(this.pager.getContext(), (Interpolator)null, duration);
            mScroller.set(this.pager, scroller);
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }

    public void startAutoPlay() {
        this.stopAutoPlay();
        if(this.isAutoPlay) {
            this.handler.sendEmptyMessageDelayed(this.WHAT_AUTO_PLAY, (long)this.autoPlayDuration);
        }

    }

    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        if(visibility == 0) {
            this.startAutoPlay();
        } else {
            this.stopAutoPlay();
        }

    }

    public void setSlideable(boolean slideable) {
        this.pager.setCanScroll(slideable);
        if(!slideable) {
            this.stopAutoPlay();
            this.isAutoPlay = false;
        }

    }

    public void setAutoPlay(boolean autoPlay) {
        this.isAutoPlay = autoPlay;
    }

    public void stopAutoPlay() {
        if(this.isAutoPlay) {
            this.handler.removeMessages(this.WHAT_AUTO_PLAY);
        }

    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch(ev.getAction()) {
            case 0:
                this.stopAutoPlay();
                break;
            case 1:
            case 3:
                this.startAutoPlay();
            case 2:
        }

        return super.dispatchTouchEvent(ev);
    }

    private void switchIndicator(int currentPosition) {
        for(int i = 0; i < this.indicatorContainer.getChildCount(); ++i) {
            ((ImageView)this.indicatorContainer.getChildAt(i)).setImageDrawable(i == currentPosition?this.selectedDrawable:this.unSelectedDrawable);
        }

    }

    public void setOnBannerItemClickListener(OnBannerItemClickListener onBannerItemClickListener) {
        this.onBannerItemClickListener = onBannerItemClickListener;
    }

    public void setBannerOnPageChangeListener(BannerOnPageChangeListener bannerOnPageChangeListener) {
        this.bannerOnPageChangeListener = bannerOnPageChangeListener;
    }

    public void setLoop(boolean loop) {
        this.isLoop = loop;
    }

    public interface BannerOnPageChangeListener {
        void onPageSelected(int var1);
    }

    public class FixedSpeedScroller extends Scroller {
        private int mDuration;

        public FixedSpeedScroller(Context context) {
            super(context);
            this.mDuration = 1000;
        }

        public FixedSpeedScroller(Context context, Interpolator interpolator) {
            super(context, interpolator);
            this.mDuration = 1000;
        }

        public FixedSpeedScroller(Context context, Interpolator interpolator, int duration) {
            this(context, interpolator);
            this.mDuration = duration;
        }

        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, this.mDuration);
        }

        public void startScroll(int startX, int startY, int dx, int dy) {
            super.startScroll(startX, startY, dx, dy, this.mDuration);
        }
    }

    public class LoopPagerAdapter extends PagerAdapter {
        private List<View> views;

        public LoopPagerAdapter(List<View> view) {
            this.views = view;
        }

        public int getCount() {
            return CustomBannerView.this.isLoop?2147483647:CustomBannerView.this.itemCount;
        }

        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        public Object instantiateItem(ViewGroup container, int position) {
            if(this.views.size() > 0) {
                View view = (View)this.views.get(position % this.views.size());
                if(container.equals(view.getParent())) {
                    container.removeView(view);
                }

                container.addView(view);
                return view;
            } else {
                return null;
            }
        }

        public void destroyItem(ViewGroup container, int position, Object object) {
        }
    }

    public interface OnBannerItemClickListener {
        void onItemClick(int var1);
    }

    private static enum Position {
        centerBottom,
        rightBottom,
        leftBottom,
        centerTop,
        rightTop,
        leftTop;

        private Position() {
        }
    }

    private static enum Shape {
        rect,
        oval;

        private Shape() {
        }
    }
}
