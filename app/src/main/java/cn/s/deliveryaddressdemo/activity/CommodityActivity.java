package cn.s.deliveryaddressdemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import cn.s.deliveryaddressdemo.R;

public class CommodityActivity extends AppCompatActivity {

    public static CommodityActivity instance = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commodity);
        instance = this;

        Button buy = findViewById(R.id.btn_buy);
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CommodityActivity.this,PaymentActivity.class);
                startActivity(intent);
            }
        });

    }

}
