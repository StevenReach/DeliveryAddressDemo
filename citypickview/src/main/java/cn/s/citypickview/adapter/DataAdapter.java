package cn.s.citypickview.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import cn.s.citypickview.R;


public class DataAdapter extends BaseAdapter {
    private String[] mDatas;
    private Context context;
    public DataAdapter(Context context, String[] mDatas) {
        this.context=context;
        this.mDatas = mDatas;
    }

    @Override
    public int getCount() {
        return mDatas==null ? 0 :mDatas.length;
    }

    @Override
    public Object getItem(int position) {
        return mDatas[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView= View.inflate(context, R.layout.data_textview,null);
        TextView textView = convertView.findViewById(R.id.data_text);
        textView.setText(mDatas[position]);
        return textView;
    }
    public void setList(String[] datas) {
        if (datas != null && datas.length>0) {
            mDatas=datas;
        }
        notifyDataSetChanged();
    }
}
