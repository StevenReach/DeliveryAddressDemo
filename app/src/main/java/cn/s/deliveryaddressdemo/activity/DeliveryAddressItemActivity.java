package cn.s.deliveryaddressdemo.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.s.deliveryaddressdemo.R;
import cn.s.deliveryaddressdemo.adapter.AdapterAddress;
import cn.s.deliveryaddressdemo.adapter.AdapterAddressPayment;
import cn.s.deliveryaddressdemo.bean.DeliveryAddressBean;
import cn.s.deliveryaddressdemo.constant.Constant;
import cn.s.deliveryaddressdemo.db.DatabaseOperation;

public class DeliveryAddressItemActivity extends AppCompatActivity {

    private SQLiteDatabase db;//数据库对象
    private DatabaseOperation dop;//自定义数据库类

    private TextView tv_address_id;

    private ListView lvAddress;

    public AdapterAddress adapterAddress;
    public AdapterAddressPayment adapterAddressPayment;

    Intent intent;
    String select;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_address_item);

        dop = new DatabaseOperation(this, db);
        lvAddress = findViewById(R.id.lv_address);

        intent = getIntent();
        select = intent.getStringExtra("select");

        LinearLayout llAdd = findViewById(R.id.ll_add);
        llAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DeliveryAddressItemActivity.this,NewDeliveryAddressActivity.class);
                intent.putExtra("editModel", "newAdd");
                startActivity(intent);
            }
        });
        LinearLayout ll_back = findViewById(R.id.ll_back);
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();

        if (select.equals("select")){
            // 显示列表
            showAddressItem();
            // 为列表添加监听器
            lvAddress.setOnItemClickListener(new SelectItemClickEvent());
        }else if (select.equals("add")){
            // 显示列表
            showAddressList();
            // 为列表添加监听器
            lvAddress.setOnItemClickListener(new ItemClickEvent());
            // 为列表添加长按事件
            lvAddress.setOnItemLongClickListener(new ItemLongClickEvent());
        }
    }

    private void showAddressItem() {

        // 创建或打开数据库 获取数据
        dop.create_db();
        //获取数据库内容
        Cursor cursor = dop.query_db();
        List<DeliveryAddressBean> list = new ArrayList<>();//信息集合
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {// 光标移动成功
                // 把数据取出
                DeliveryAddressBean bean = new DeliveryAddressBean();//创建数据库实体类
                //保存信息id到实体类
                bean.set_id("" + cursor.getInt(cursor.getColumnIndex("_id")));
                bean.setChecked(cursor.getString(cursor.
                        getColumnIndex("checked")));
                bean.setConsignee(cursor.getString(cursor
                        .getColumnIndex("consignee")));//保存内容到实体类
                bean.setPhone(cursor.getString(cursor
                        .getColumnIndex("phone")));
                bean.setProvince(cursor.getString(cursor
                        .getColumnIndex("province")));
                bean.setCity(cursor.getString(cursor
                        .getColumnIndex("city")));
                bean.setDistrict(cursor.getString(cursor
                        .getColumnIndex("district")));
                bean.setDetailed(cursor.getString(cursor
                        .getColumnIndex("detailed")));

                list.add(bean);//把保存信息实体类保存到信息集合里
            }
            //倒序显示数据
            Collections.reverse(list);
            adapterAddressPayment = new AdapterAddressPayment(list, this);//装载信息
            lvAddress.setAdapter(adapterAddressPayment);//列表设置笔记信息适配器
        }
        dop.close_db();//关闭数据库
        if (list.size()==0){
            lvAddress.setVisibility(View.GONE);
        }else {
            lvAddress.setVisibility(View.VISIBLE);
        }

    }

    // 显示列表
    private void showAddressList() {
        // 创建或打开数据库 获取数据
        dop.create_db();
        //获取数据库内容
        Cursor cursor = dop.query_db();
        List<DeliveryAddressBean> list = new ArrayList<>();//信息集合
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {// 光标移动成功
                // 把数据取出
                DeliveryAddressBean bean = new DeliveryAddressBean();//创建数据库实体类
                //保存信息id到实体类
                bean.set_id("" + cursor.getInt(cursor.getColumnIndex("_id")));
                bean.setChecked(cursor.getString(cursor.
                        getColumnIndex("checked")));
                bean.setConsignee(cursor.getString(cursor
                        .getColumnIndex("consignee")));//保存内容到实体类
                bean.setPhone(cursor.getString(cursor
                        .getColumnIndex("phone")));
                bean.setProvince(cursor.getString(cursor
                        .getColumnIndex("province")));
                bean.setCity(cursor.getString(cursor
                        .getColumnIndex("city")));
                bean.setDistrict(cursor.getString(cursor
                        .getColumnIndex("district")));
                bean.setDetailed(cursor.getString(cursor
                        .getColumnIndex("detailed")));

                list.add(bean);//把保存信息实体类保存到信息集合里
            }
            //倒序显示数据
            Collections.reverse(list);
            adapterAddress = new AdapterAddress(list, this);//装载信息
            lvAddress.setAdapter(adapterAddress);//列表设置笔记信息适配器
        }
        dop.close_db();//关闭数据库
        if (list.size()==0){
            lvAddress.setVisibility(View.GONE);
        }else {
            lvAddress.setVisibility(View.VISIBLE);
        }
    }

    // 列表长按监听器
    class ItemLongClickEvent implements AdapterView.OnItemLongClickListener {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view,
                                       int position, long id) {
            //初始化id保存控件
            tv_address_id = view.findViewById(R.id.tv_address_id);

            //获取控件上id信息转换成int类型
            int item_id = Integer.parseInt(tv_address_id.getText().toString());
            //弹出选择操作框方法
            simpleList(item_id);
            return true;
        }
    }

    // 简单列表对话框，用于选择操作
    public void simpleList(final int item_id) {
        //实例化AlertDialog
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this,
                R.style.custom_dialog);
        //设置弹窗标题
        alertDialogBuilder.setTitle("选择操作");
        //设置弹窗选项内容
        alertDialogBuilder.setItems(R.array.itemOperation,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            // 编辑
                            case 0:

                                Intent intent = new Intent(DeliveryAddressItemActivity.this,
                                        NewDeliveryAddressActivity.class);//跳转到添加地址页
                                intent.putExtra("editModel", "update");//传递编辑信息
                                intent.putExtra("addressId", item_id);//传递id信息
                                startActivity(intent);//开始跳转

                                break;
                            // 删除
                            case 1:

                                dop.create_db();// 打开数据库
                                dop.delete_db(item_id);//删除数据
                                dop.close_db();// 关闭数据库
                                // 刷新列表显示
                                lvAddress.invalidate();
                                showAddressList();
                                break;
                        }
                    }
                });
        alertDialogBuilder.create();//创造弹窗
        alertDialogBuilder.show();//显示弹窗
    }

    // 列表单击监听器
    class ItemClickEvent implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            tv_address_id = view.findViewById(R.id.tv_address_id);
            int item_id = Integer.parseInt(tv_address_id.getText().toString());


            Intent intent = new Intent(DeliveryAddressItemActivity.this, NewDeliveryAddressActivity.class);
            intent.putExtra("editModel", "update");
            intent.putExtra("addressId", item_id);
            startActivity(intent);

        }
    }


    class SelectItemClickEvent implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id){
            TextView tvConsignee = view.findViewById(R.id.tv_adapter_consignee_payment);
            TextView tvPhone = view.findViewById(R.id.tv_adapter_phone_number_payment);
            TextView tvLocation = view.findViewById(R.id.tv_adapter_location_payment);
            TextView tvDetailed = view.findViewById(R.id.tv_adapter_delivery_address_payment);


            Constant.selectConsignee = tvConsignee.getText().toString();
            Constant.selectPhone = tvPhone.getText().toString();
            Constant.selectAddress = tvLocation.getText().toString() + tvDetailed.getText().toString();

            DeliveryAddressItemActivity.this.finish();
            PaymentActivity.instance.initRefresh();
        }

    }
}
