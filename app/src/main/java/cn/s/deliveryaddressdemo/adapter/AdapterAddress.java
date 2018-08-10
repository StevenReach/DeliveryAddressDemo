package cn.s.deliveryaddressdemo.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import cn.s.deliveryaddressdemo.R;
import cn.s.deliveryaddressdemo.bean.DeliveryAddressBean;

public class AdapterAddress extends BaseAdapter {
    private List<DeliveryAddressBean> list;
    private Context context;
    private LayoutInflater inflater;

    public AdapterAddress(List<DeliveryAddressBean> list, Context context) {
        super();
        this.list = list;
        this.context = context;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @SuppressLint({"SetTextI18n", "InflateParams"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder;
        if (convertView == null) {
            holder = new Holder();
            convertView = inflater.inflate(R.layout.adapter_address, null);
            holder.tv_address_id = convertView
                    .findViewById(R.id.tv_address_id);
            holder.tv_address_checked = convertView
                    .findViewById(R.id.tv_address_checked);
            holder.tv_adapter_consignee = convertView
                    .findViewById(R.id.tv_adapter_consignee);
            holder.tv_adapter_phone_number = convertView
                    .findViewById(R.id.tv_adapter_phone_number);
            holder.tv_adapter_location = convertView
                    .findViewById(R.id.tv_adapter_location);
            holder.tv_adapter_delivery_address = convertView
                    .findViewById(R.id.tv_adapter_delivery_address);
            holder.tv_default = convertView.findViewById(R.id.tv_default);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        holder.tv_address_id.setText(list.get(position).get_id());
        holder.tv_address_checked.setText(list.get(position).getChecked());
        holder.tv_adapter_consignee.setText(list.get(position).getConsignee());
        holder.tv_adapter_phone_number.setText(list.get(position).getPhone());
        holder.tv_adapter_location.setText(list.get(position).getProvince() + list.get(position).getCity() + list.get(position).getDistrict());
        holder.tv_adapter_delivery_address.setText(list.get(position).getDetailed());
        holder.tv_default.setText(list.get(position).getChecked());

        return convertView;

    }

    class Holder {
        public TextView tv_address_id,tv_address_checked,tv_adapter_consignee,tv_adapter_phone_number,
                tv_adapter_location,tv_adapter_delivery_address,tv_default;
        public RelativeLayout rl_adapter_address;
    }
}
