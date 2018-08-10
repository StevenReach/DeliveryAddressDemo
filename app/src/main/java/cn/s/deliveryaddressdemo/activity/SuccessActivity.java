package cn.s.deliveryaddressdemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import cn.s.deliveryaddressdemo.R;
import cn.s.deliveryaddressdemo.constant.Constant;

public class SuccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_success);

        TextView tvResultCon = findViewById(R.id.tv_result_consignee);
        TextView tvResultPh = findViewById(R.id.tv_result_phone);
        TextView tvResultAd = findViewById(R.id.tv_result_address);

        tvResultCon.setText(Constant.aConsignee);
        tvResultPh.setText(Constant.aPhone);
        tvResultAd.setText(Constant.aAddress);

    }

}
