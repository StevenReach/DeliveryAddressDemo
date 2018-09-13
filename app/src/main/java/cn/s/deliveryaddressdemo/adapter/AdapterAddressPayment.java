package cn.s.deliveryaddressdemo.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import cn.s.deliveryaddressdemo.R;
import cn.s.deliveryaddressdemo.activity.NewDeliveryAddressActivity;
import cn.s.deliveryaddressdemo.bean.DeliveryAddressBean;

public class AdapterAddressPayment extends BaseAdapter implements View.OnClickListener{

    private List<DeliveryAddressBean> list;
    private Context context;

    public AdapterAddressPayment(List<DeliveryAddressBean> list, Context context) {
        super();
        this.list = list;
        this.context = context;

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
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_address_payment, null);
            holder.tv_address_id_payment = convertView
                    .findViewById(R.id.tv_address_id_payment);
            holder.tv_adapter_consignee_payment = convertView
                    .findViewById(R.id.tv_adapter_consignee_payment);
            holder.tv_adapter_phone_number_payment = convertView
                    .findViewById(R.id.tv_adapter_phone_number_payment);
            holder.tv_adapter_location_payment = convertView
                    .findViewById(R.id.tv_adapter_location_payment);
            holder.tv_adapter_delivery_address_payment = convertView
                    .findViewById(R.id.tv_adapter_delivery_address_payment);
            holder.btn_edit = convertView.findViewById(R.id.btn_edit);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_address_id_payment.setText(list.get(position).get_id());
        holder.tv_adapter_consignee_payment.setText(list.get(position).getConsignee());
        holder.tv_adapter_phone_number_payment.setText(list.get(position).getPhone());
        holder.tv_adapter_location_payment.setText(list.get(position).getProvince() + list.get(position).getCity() + list.get(position).getDistrict());
        holder.tv_adapter_delivery_address_payment.setText(list.get(position).getDetailed());

        holder.btn_edit.setOnClickListener(this);
        holder.btn_edit.setTag(Integer.parseInt(list.get(position).get_id()));

        return convertView;

    }

    @Override
    public void onClick(View v) {

        int tag = (int) v.getTag();
        switch (v.getId()){
            case R.id.btn_edit:
                Intent intent = new Intent();
                intent.setClass(context, NewDeliveryAddressActivity.class);
                intent.putExtra("editModel", "update");
                intent.putExtra("addressId",tag);
                context.startActivity(intent);
                break;
        }
    }


    public static class ViewHolder {
        TextView tv_address_id_payment,tv_adapter_consignee_payment,tv_adapter_phone_number_payment,
                tv_adapter_location_payment,tv_adapter_delivery_address_payment;
        Button btn_edit;
    }
}
