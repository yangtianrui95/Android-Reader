package com.example.yangtianrui.reader.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yangtianrui.reader.R;
import com.example.yangtianrui.reader.bean.Book;
import com.example.yangtianrui.reader.util.FormatUtil;

import java.util.List;

/**
 * Created by yangtianrui on 16-5-31.
 * 书籍信息的adapter类
 */
public class BookAdapter extends BaseAdapter {

    private List<Book> mBooks;
    private Context mContext;

    public BookAdapter(Context context, List<Book> books) {
        this.mContext = context;
        this.mBooks = books;
    }


    @Override
    public int getCount() {
        return mBooks.size();
    }

    @Override
    public Object getItem(int position) {
        return mBooks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.item_each_book, null, false);
            holder.mTvTitle = (TextView) convertView.findViewById(R.id.id_tv_book_title);
            holder.mTvSubTitle = (TextView) convertView.findViewById(R.id.id_tv_book_subtitle);
            holder.mTvPages = (TextView) convertView.findViewById(R.id.id_tv_book_pages);
            holder.mTvPubDate = (TextView) convertView.findViewById(R.id.id_tv_book_publish_date);
            holder.mTvPrice = (TextView) convertView.findViewById(R.id.id_tv_book_prize);
            holder.mIvImg = (ImageView) convertView.findViewById(R.id.id_iv_book_img);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mTvTitle.setText(FormatUtil.subText(mBooks.get(position).getTitle(), 20));
        holder.mTvSubTitle.setText("副标题 : " + FormatUtil.subText(mBooks.get(position).getAlt_title(), 10));
        holder.mTvPrice.setText("价格 : " + mBooks.get(position).getPrice());
        holder.mTvPages.setText("页数 : " + mBooks.get(position).getPages());
        holder.mTvPubDate.setText("出版日期 : " + mBooks.get(position).getPubdate());
        // 使用Glide加载图片
        Glide.with(mContext)
                .load(mBooks.get(position).getImage())
                .fitCenter()
                .into(holder.mIvImg);
        return convertView;
    }

    private static class ViewHolder {

        private TextView mTvTitle;
        private TextView mTvSubTitle;
        private TextView mTvPubDate;
        private TextView mTvPages;
        private TextView mTvPrice;
        private ImageView mIvImg;

    }
}
