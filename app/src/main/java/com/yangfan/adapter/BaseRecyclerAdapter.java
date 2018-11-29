package com.yangfan.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import com.github.captain_miao.uniqueadapter.library.BaseUniqueAdapter;
import com.github.captain_miao.uniqueadapter.library.ItemModel;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by yangfan
 * nrainyseason@163.com
 */
public abstract class BaseRecyclerAdapter<T extends ItemModel> extends BaseUniqueAdapter {
    private static final String TAG = "BaseRvAdapter";

    public static final int NO_POSITION = Integer.MIN_VALUE;
    public static final long NO_ID = Integer.MIN_VALUE;
    public static final int INVALID_TYPE = Integer.MIN_VALUE;
    protected List<T> mItemList = new ArrayList<>();


    @Override
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
            super.onBindViewHolder(holder, position);
    }


    @Override
    public final int getItemViewType(int position) {
        // content view type
        return super.getItemViewType(position);
    }


    public List<T> getList() {
        return mItemList;
    }


    public void addAll(List<T> list, boolean notifyDataChange) {
        if (list == null || list.isEmpty()) {
            return;
        }
        mItemList.addAll(list);
        if (notifyDataChange) {
            try {
                notifyItemRangeInserted(getBasicItemCount(), list.size());
            } catch (Exception e) {
                Log.w(TAG, "notifyItemRangeInserted failure");
                e.printStackTrace();
                notifyDataSetChanged();
            }
            notifyDataSetChanged();
        }
    }

    public void addAll(List<T> list) {
        addAll(list, true);
    }

    public void add(T t, boolean notifyDataChange) {
        if (t == null) {
            return;
        }
        mItemList.add(t);
        if (notifyDataChange) {
            try {
                notifyItemInserted(getBasicItemCount());
            } catch (Exception e) {
                Log.w(TAG, "notifyItemInserted failure");
                e.printStackTrace();
                notifyDataSetChanged();
            }
        }
    }

    public void add(T t) {
        add(t, true);
    }

    public void appendToList(List<T> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        addAll(list);
        try {
            notifyItemRangeInserted(getBasicItemCount(), list.size());
        } catch (Exception e) {
            Log.w(TAG, "notifyItemRangeInserted failure");
            e.printStackTrace();
            notifyDataSetChanged();
        }
    }

    public void append(T item) {
        append(item, true);
    }

    public void append(T item, boolean notifyDataChange) {
        if (item == null) {
            return;
        }
        add(item, notifyDataChange);
    }

    public void appendToTop(T item) {
        appendToTop(item, true);
    }

    public void appendToTop(T item, boolean notifyDataChange) {
        if (item == null) {
            return;
        }
        mItemList.add(0, item);
        if (notifyDataChange) {
            try {
                notifyItemInserted(0);
            } catch (Exception e) {
                Log.w(TAG, "notifyItemInserted failure");
                e.printStackTrace();
                notifyDataSetChanged();
            }
        }
    }

    public void appendToTopList(List<T> list) {
        if (list == null) {
            return;
        }
        mItemList.addAll(0, list);
        try {
            notifyItemRangeInserted(0, list.size());
        } catch (Exception e) {
            Log.w(TAG, "notifyItemRangeInserted failure");
            e.printStackTrace();
            notifyDataSetChanged();
        }
    }


    public T remove(int dataListIndex, boolean notifyDataChange) {
        if (dataListIndex >= 0 && dataListIndex < mItemList.size()) {
            T t = mItemList.remove(dataListIndex);
            if (notifyDataChange) {
                try {
                    notifyItemRemoved(dataListIndex);
                } catch (Exception e) {
                    Log.w(TAG, "notifyItemRemoved failure");
                    e.printStackTrace();
                    notifyDataSetChanged();
                }
            }
            return t;
        } else {
            return null;
        }
    }

    public T remove(int dataListIndex) {
        return remove(dataListIndex, true);
    }

    public boolean remove(T data, boolean notifyDataChange) {
        int position = mItemList.indexOf(data);
        remove(position, notifyDataChange);
        return position >= 0;
    }

    public boolean remove(T data) {
        return remove(data, true);
    }


    public void update(int dataListIndex, boolean notifyDataChange) {
        if (dataListIndex >= 0 && dataListIndex < mItemList.size()) {
            if (notifyDataChange) {
                try {
                    notifyItemChanged( dataListIndex);
                } catch (Exception e) {
                    Log.w(TAG, "notifyItemChanged failure");
                    e.printStackTrace();
                    notifyDataSetChanged();
                }
            }
        }
    }

    public boolean update(T data, boolean notifyDataChange) {
        int position = mItemList.indexOf(data);
        update(position, notifyDataChange);
        return position >= 0;
    }

    public boolean update(T data) {
        return update(data, true);
    }

    public void clear(boolean notifyDataChange) {
        mItemList.clear();
        if (notifyDataChange) {
            notifyDataSetChanged();
        }
    }

    public void clear() {
        clear(true);
    }

    protected int getBasicItemCount() {
        return mItemList.size();
    }

    @Override
    public int getItemCount() {
        return getBasicItemCount();
    }

    public T getItem(int position) {
        int dataOfIndex = position;
        if (dataOfIndex >= 0 && dataOfIndex < getBasicItemCount()) {
            return mItemList.get(dataOfIndex);
        } else {
            return null;
        }
    }
}
