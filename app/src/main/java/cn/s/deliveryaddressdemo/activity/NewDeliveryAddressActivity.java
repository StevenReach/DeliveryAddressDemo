package cn.s.deliveryaddressdemo.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import cn.s.citypickview.BaseActivity;
import cn.s.citypickview.entity.PickerData;
import cn.s.citypickview.listener.OnPickerClickListener;
import cn.s.citypickview.view.PickerView;
import cn.s.deliveryaddressdemo.R;
import cn.s.deliveryaddressdemo.db.DatabaseOperation;
import cn.s.deliveryaddressdemo.view.ClearableEditText;

public class NewDeliveryAddressActivity extends BaseActivity implements View.OnClickListener {

    private PickerView pickerView;

    SharedPreferences mySharedPreferences;//声明信息保存类

    private SQLiteDatabase db;//数据库操作类
    private DatabaseOperation dop;

    private ClearableEditText etConsignee;
    private ClearableEditText etPhone;
    private ClearableEditText etDetailed;
    private Switch dSwitch;
    private TextView tvLocation;
    private TextView tvTitle;
    private TextView tvDelete;

    int item_Id;
    Intent intent;
    String editModel = null;
    String consignee;
    String phone;
    String province;
    String city;
    String district;
    String detailed;
    String first;
    String second;
    String third;
    String checked = "编辑";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_delivery_address);

        initView();
    }

    private void initView() {


        tvLocation = findViewById(R.id.tv_location);
        final LinearLayout llShow = findViewById(R.id.ll_location);

        dSwitch = findViewById(R.id.sw_default);
        dSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    checked = "默认";
                }else {
                    checked ="编辑" ;
                }
            }
        });

        LinearLayout llBack = findViewById(R.id.ll_back);
        llBack.setOnClickListener(this);
        LinearLayout llSave = findViewById(R.id.ll_save);
        llSave.setOnClickListener(this);
        LinearLayout llDelete = findViewById(R.id.ll_delete_delivery_address);
        llDelete.setOnClickListener(this);

        tvTitle = findViewById(R.id.tv_add_delivery_address);
        tvDelete = findViewById(R.id.tv_save_delivery_address);
        etConsignee = findViewById(R.id.et_consignee);
        etPhone = findViewById(R.id.et_phone_number);
        etDetailed = findViewById(R.id.et_detailed_delivery_address);

        dop = new DatabaseOperation(this, db);
        intent = getIntent();
        editModel = intent.getStringExtra("editModel");
        item_Id = intent.getIntExtra("addressId", 0);
        // 加载数据
        loadData();

        initProvinceDatas();
        //选择器数据实体类封装
        PickerData data=new PickerData();
        //设置数据，有多少层级自己确定
        data.setFirstDatas(mProvinceDatas);
        data.setSecondDatas(mCitisDatasMap);
        data.setThirdDatas(mDistrictDatasMap);

        if(editModel.equals("update")){
            data.setInitSelectText(first,second,third);

        }
//        data.setInitSelectText("河北省","石家庄市","平山县");
        //初始化选择器
        pickerView=new PickerView(this,data);

        llShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = ( InputMethodManager ) v.getContext( ).getSystemService( Context.INPUT_METHOD_SERVICE );
                if ( Objects.requireNonNull(imm).isActive( ) ) {
                    imm.hideSoftInputFromWindow( v.getApplicationWindowToken( ) , 0 );
                }
                //显示选择器
                pickerView.show(llShow);
            }
        });
        //选择器完成三级选择后点击回调
        pickerView.setOnPickerClickListener(new OnPickerClickListener() {
            //选择列表时触发的事件
            @Override
            public void OnPickerClick(PickerData pickerData) {
                //想获取单个选择项 PickerData内也有方法（弹出框手动关闭）
                tvLocation.setText(pickerData.getSelectText());
                province = pickerData.getFirstText();
                city = pickerData.getSecondText();
                district = pickerData.getThirdText();
                pickerView.dismiss();//关闭选择器
            }
            //点击确定按钮触发的事件（自动关闭）
            @Override
            public void OnPickerConfirmClick(PickerData pickerData) {
                tvLocation.setText(pickerData.getSelectText());
                province = pickerData.getFirstText();
                city = pickerData.getSecondText();
                district = pickerData.getThirdText();
            }
        });

    }

    //加载数据
    public void loadData(){
        if (editModel.equals("newAdd")){
            etConsignee.setText("");
            etPhone.setText("");
            etDetailed.setText("");
        }else if (editModel.equals("update")){

            tvDelete.setText("删除该收货地址");

            tvTitle.setText("修改收货地址");
            dop.create_db();
            Cursor cursor = dop.query_db(item_Id);
            cursor.moveToFirst();

            String check = cursor.getString(cursor.getColumnIndex("checked"));
            consignee = cursor.getString(cursor.getColumnIndex("consignee"));
            phone = cursor.getString(cursor.getColumnIndex("phone"));
            province = cursor.getString(cursor.getColumnIndex("province"));
            city = cursor.getString(cursor.getColumnIndex("city"));
            district = cursor.getString(cursor.getColumnIndex("district"));
            detailed = cursor.getString(cursor.getColumnIndex("detailed"));

            int startIndex = 0;

            etConsignee.append(consignee.substring(startIndex, consignee.length()));
            etPhone.append(phone.substring(startIndex, phone.length()));
            tvLocation.append(province.substring(startIndex, province.length()) + " " +
                    city.substring(startIndex, city.length()) + " " + district.substring(startIndex, district.length()));
            etDetailed.append(detailed.substring(startIndex, detailed.length()));

            if (check.equals("默认")){
                dSwitch.setChecked(true);
            }else {
                dSwitch.setChecked(false);
            }

            //已经选择的省市区
            first = province.substring(startIndex, province.length());
            second = city.substring(startIndex, city.length());
            third = district.substring(startIndex, district.length());
            dop.close_db();
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.ll_back:
                NewDeliveryAddressActivity.this.finish();
                break;
            case R.id.ll_save:
                consignee = etConsignee.getText().toString();
                phone = etPhone.getText().toString();
                detailed = etDetailed.getText().toString();


                if (consignee.length() < 2){
                    Toast.makeText(this,"收货人姓名请不少于2个字！",Toast.LENGTH_SHORT).show();
                }else if (phone.length() != 11){
                    Toast.makeText(this,"请填写正确的手机号码！",Toast.LENGTH_SHORT).show();
                } else if (district == null){
                    Toast.makeText(this,"请完成所在地区的选择！",Toast.LENGTH_SHORT).show();
                }else if (detailed.length()<5){
                    Toast.makeText(this,"详细地址请不要少于5个字",Toast.LENGTH_SHORT).show();
                } else {
                    dop.create_db();
                    if (editModel.equals("newAdd")){
                        getSwitchChecked(checked);
                        dop.insert_db(checked, consignee, phone, province, city, district, detailed);
                    }else if (editModel.equals("update")){
                        getSwitchChecked(checked);
                        dop.update_db(checked, consignee, phone, province, city, district, detailed, item_Id);
                    }
                    dop.close_db();
                    NewDeliveryAddressActivity.this.finish();
                }
                break;
            case R.id.ll_delete_delivery_address:
                consignee = etConsignee.getText().toString();
                phone = etPhone.getText().toString();
                detailed = etDetailed.getText().toString();


                if (consignee.length() < 2){
                    Toast.makeText(this,"收货人姓名请不少于2个字！",Toast.LENGTH_SHORT).show();
                }else if (phone.length() != 11){
                    Toast.makeText(this,"请填写正确的手机号码！",Toast.LENGTH_SHORT).show();
                } else if (district == null){
                    Toast.makeText(this,"请完成所在地区的选择！",Toast.LENGTH_SHORT).show();
                }else if (detailed.length()<5){
                    Toast.makeText(this,"详细地址请不要少于5个字",Toast.LENGTH_SHORT).show();
                }
                else {
                    dop.create_db();
                    if (editModel.equals("newAdd")){
                        getSwitchChecked(checked);
                        dop.insert_db(checked, consignee, phone, province, city, district, detailed);
                    }else if (editModel.equals("update")){
                        dop.delete_db(item_Id);
                    }
                    dop.close_db();
                    NewDeliveryAddressActivity.this.finish();
                }
                break;
        }

    }


    //将默认地址保存到SharePreferences
    public void getSwitchChecked(String checked){

        if (checked.equals("默认")){
            mySharedPreferences = getSharedPreferences("defaultaddress", NewDeliveryAddressActivity.MODE_PRIVATE);
            SharedPreferences.Editor editor = mySharedPreferences.edit();
            editor.clear().apply();
            editor.putString("consignee",consignee);
            editor.putString("phone",phone);
            editor.putString("province",province);
            editor.putString("city",city);
            editor.putString("district",district);
            editor.putString("detailed",detailed);
            editor.apply();
        }

    }


}
