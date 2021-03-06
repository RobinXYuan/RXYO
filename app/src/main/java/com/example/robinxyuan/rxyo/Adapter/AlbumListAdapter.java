package com.example.robinxyuan.rxyo.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.robinxyuan.rxyo.Image.AlbumBean;
import com.example.robinxyuan.rxyo.Image.ImageLoader;
import com.example.robinxyuan.rxyo.Image.SelectPhotoActivity;
import com.example.robinxyuan.rxyo.R;

import java.util.List;

/**
 * Created by robinxyuan on 2017/11/30.
 */

public class AlbumListAdapter extends BaseAdapter {

    private List<AlbumBean> list;
    private LayoutInflater mInflater;
    private ImageLoader mLoader;

    public AlbumListAdapter(SelectPhotoActivity selectPhotoActivity, List<AlbumBean> list) {
        this.list = list;
        mInflater = LayoutInflater.from(selectPhotoActivity);
        mLoader = new ImageLoader(selectPhotoActivity);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView==null){
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.layout_item_album,null);
            viewHolder.mImageView = (ImageView) convertView.findViewById(R.id.iv_directory_pic);
            viewHolder.mTextViewTitle = (TextView) convertView.findViewById(R.id.tv_directory_name);
            viewHolder.mTextViewCounts = (TextView) convertView.findViewById(R.id.tv_directory_nums);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        mLoader.setAsyncBitmapFromSD(list.get(position).topImagePath,viewHolder.mImageView,300,false,true,true);
        viewHolder.mTextViewTitle.setText(list.get(position).folderName);
        viewHolder.mTextViewCounts.setText(list.get(position).imageCounts+"");
        return convertView;
    }

    public static class ViewHolder {
        public ImageView mImageView;
        public TextView mTextViewTitle;
        public TextView mTextViewCounts;
    }

}
