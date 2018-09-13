package cn.s.deliveryaddressdemo.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.reflect.Field;

import cn.s.deliveryaddressdemo.R;
import cn.s.deliveryaddressdemo.constant.Constant;

public class PaymentActivity extends AppCompatActivity {

    @SuppressLint("StaticFieldLeak")
    public static PaymentActivity instance = null;

    private TextView tvConsignee, tvPhone, tvDelivery;


    String con;
    String ph;
    String ad;
    int flag = 0;

    SharedPreferences mySharedPreferences;//声明信息保存类

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        instance = this;

        tvConsignee = findViewById(R.id.tv_payment_consignee);
        tvPhone = findViewById(R.id.tv_payment_phone);
        tvDelivery = findViewById(R.id.tv_payment_delivery);


        //取出默认地址
        mySharedPreferences = getSharedPreferences("defaultaddress", NewDeliveryAddressActivity.MODE_PRIVATE);
        tvConsignee.setText(mySharedPreferences.getString("consignee", ""));
        tvPhone.setText(mySharedPreferences.getString("phone", ""));
        tvDelivery.setText(mySharedPreferences.getString("province", "") +
                mySharedPreferences.getString("city", "") +
                mySharedPreferences.getString("district", "") +
                mySharedPreferences.getString("detailed", ""));

        final String dCity = mySharedPreferences.getString("city", "");

        //返回事件
        LinearLayout llBack = findViewById(R.id.ll_back);
        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PaymentActivity.this.finish();
            }
        });

        //收货地址单击事件
        RelativeLayout rlAddress = findViewById(R.id.rl_payment_address);
        rlAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaymentActivity.this,DeliveryAddressItemActivity.class);
                intent.putExtra("select","select");
                startActivity(intent);
                flag = 1;
            }
        });

        //提交订单事件
        Button submit = findViewById(R.id.btn_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断是否是按钮是否是第一次点击，对话框只提示一次
                switch (flag){
                    case 0:
                        if (dCity.equals(Constant.aCity)) {
                            Intent intent = new Intent(PaymentActivity.this, SuccessActivity.class);
                            startActivity(intent);
                            PaymentActivity.this.finish();
                            CommodityActivity.instance.finish();

                            con = tvConsignee.getText().toString();
                            ph = tvPhone.getText().toString();
                            ad = tvDelivery.getText().toString();

                            Constant.aConsignee = con;
                            Constant.aPhone = ph;
                            Constant.aAddress = ad;

                        } else {
                            simpleTips();
                        }

                        flag = 1;
                        break;

                    case 1:
                        Intent intent = new Intent(PaymentActivity.this, SuccessActivity.class);
                        startActivity(intent);
                        PaymentActivity.this.finish();
                        CommodityActivity.instance.finish();

                        con = tvConsignee.getText().toString();
                        ph = tvPhone.getText().toString();
                        ad = tvDelivery.getText().toString();

                        Constant.aConsignee = con;
                        Constant.aPhone = ph;
                        Constant.aAddress = ad;

                        break;
                }
            }
        });

    }

    // 简单对话框，用于选择操作
    public void simpleTips() {
        //实例化AlertDialog
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("提示：")
                .setMessage("检测到默认地址中的城市与您现在所在城市不同。\n\n是否需要更换收货地址？")

                .setPositiveButton("否", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent1 = new Intent(PaymentActivity.this, SuccessActivity.class);
                startActivity(intent1);
                PaymentActivity.this.finish();
                CommodityActivity.instance.finish();
                con = tvConsignee.getText().toString();
                ph = tvPhone.getText().toString();
                ad = tvDelivery.getText().toString();

                Constant.aConsignee = con;
                Constant.aPhone = ph;
                Constant.aAddress = ad;
            }
        })
                .setNegativeButton("去更换", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent2 = new Intent(PaymentActivity.this,DeliveryAddressItemActivity.class);
                        intent2.putExtra("select","select");
                        startActivity(intent2);

                    }
                })

                .create();//创造弹窗
        dialog.show();//显示弹窗

        //自定义对话框的一些属性
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(22);
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.RED);
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextSize(22);
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.RED);
        try {
            Field mAlert = AlertDialog.class.getDeclaredField("mAlert");
            mAlert.setAccessible(true);
            Object mAlertController = mAlert.get(dialog);
            //通过反射修改title字体大小和颜色
            Field mTitle = mAlertController.getClass().getDeclaredField("mTitleView");
            mTitle.setAccessible(true);
            TextView mTitleView = (TextView) mTitle.get(mAlertController);
            mTitleView.setTextSize(22);
            mTitleView.setTextColor(Color.BLACK);
            //通过反射修改message字体大小和颜色
            Field mMessage = mAlertController.getClass().getDeclaredField("mMessageView");
            mMessage.setAccessible(true);
            TextView mMessageView = (TextView) mMessage.get(mAlertController);
            mMessageView.setTextSize(20);
            mMessageView.setTextColor(Color.DKGRAY);
        } catch (IllegalAccessException | NoSuchFieldException e1) {
            e1.printStackTrace();
        }

    }

    //刷新UI
    public void initRefresh() {

        tvConsignee.setText(Constant.selectConsignee);
        tvPhone.setText(Constant.selectPhone);
        tvDelivery.setText(Constant.selectAddress);

    }
}
