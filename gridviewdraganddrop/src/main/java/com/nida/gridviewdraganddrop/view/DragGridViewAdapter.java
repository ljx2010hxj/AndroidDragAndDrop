package com.nida.gridviewdraganddrop.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nida.gridviewdraganddrop.R;

import java.util.List;

/**
 * Create Time:2019/6/10 10:24
 * Author:Kerwin Li
 * Description:
 **/
public class DragGridViewAdapter extends BaseAdapter {
    private List<DragItem> mItemsList;
    private Context context;
    private int mHidePos = -1;

    public DragGridViewAdapter(Context context) {
        this.context = context;
    }

    public DragGridViewAdapter(Context context, List<DragItem> list) {
        this.context = context;
        this.mItemsList = list;
    }

    public void setItemsList(List<DragItem> itemsList) {
        this.mItemsList = itemsList;
    }

    public List<DragItem> getItemsList() {
        return this.mItemsList;
    }

    public void setHidePos(int pos) {
        this.mHidePos = pos;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mItemsList != null && mItemsList.size() > 0 ? mItemsList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return mItemsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.grid_view_item_layout, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.img.setImageResource(mItemsList.get(position).imgResId);
        holder.title.setText(mItemsList.get(position).titleResId);
        if (position == mHidePos) {
            convertView.setVisibility(View.INVISIBLE);
        } else {
            convertView.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

    static class ViewHolder {
        LinearLayout itemLayout;
        ImageView img;
        TextView title;

        public ViewHolder(View view) {
            itemLayout = (LinearLayout) view;
            img = (ImageView) itemLayout.findViewById(R.id.grid_view_item_img);
            title = (TextView) itemLayout.findViewById(R.id.grid_view_item_title);
        }
    }

    public void swap(int from, int to) {
        mItemsList.add(to, mItemsList.remove(from));
        notifyDataSetChanged();
    }

    public void removeByPos(int pos) {
        mItemsList.remove(pos);
        notifyDataSetChanged();
    }

}
