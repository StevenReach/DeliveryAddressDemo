package cn.s.deliveryaddressdemo.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

public class DatabaseOperation {
    private SQLiteDatabase db;
    private Context context;

    public DatabaseOperation(Context context, SQLiteDatabase db) {
        this.db = db;
        this.context = context;
    }

    // 数据库的打开或创建
    public void create_db() {
        // 创建或打开数据库address.db3
        db = SQLiteDatabase.openOrCreateDatabase(context.getFilesDir()
                .toString() + "delivery.db3", null);
        if (db == null) {//判断数据库是否创建成功
            Toast.makeText(context, "数据库创建不成功", Toast.LENGTH_LONG).show();
        }
        // 创建表,名称为diary,主键为_id
        db.execSQL("create table if not exists delivery_address(_id integer primary key autoincrement,"
                + "checked text,"//收货人名称
                + "consignee text,"//收货人名称
                + "phone varchar(20),"//收货人电话
                + "province text,"//所在地区
                + "city text,"
                + "district text,"
                + "detailed text" + ")");//详细地址
    }
    //插入信息到数据库
    public void insert_db(String checked, String consignee, String phone, String province, String city, String district, String detailed) {
        if (checked.equals("默认")) {//判断是否有内容
            db.execSQL("update delivery_address set checked ='" + "编辑" + "'");
            db.execSQL("insert into delivery_address(checked,consignee, phone, province, city, district, detailed) values('"
                    + checked
                    + "','"
                    + consignee//收货人名称
                    + "','"
                    + phone//电话
                    + "','"
                    + province//省
                    + "','"
                    + city//市
                    + "','"
                    + district//行政区
                    + "','"
                    +detailed//详细地址
                    + "');");
        } else {
            db.execSQL("insert into delivery_address(checked,consignee, phone, province, city, district, detailed) values('"
                    + checked
                    + "','"
                    + consignee//收货人名称
                    + "','"
                    + phone//电话
                    + "','"
                    + province//省
                    + "','"
                    + city//市
                    + "','"
                    + district//行政区
                    + "','"
                    +detailed//详细地址
                    + "');");
        }
    }
    //根据id更新数据库内容信息
    public void update_db(String checked, String consignee, String phone, String province, String city, String district, String detailed,
                          int item_ID) {
        if (checked.equals("默认")) {
            db.execSQL("update delivery_address set checked ='" + "编辑" + "'");
            db.execSQL("update delivery_address set checked = '" + checked + "',consignee = '" + consignee + "',phone = '" + phone + "'," +
                    "province='" + province + "',city = '" + city + "',district ='" + district + "'," +
                    "detailed = '" + detailed + "'where _id = '" + item_ID + "'");
            Toast.makeText(context, "修改成功", Toast.LENGTH_SHORT).show();
        } else {
            db.execSQL("update delivery_address set checked = '" + checked + "',consignee = '" + consignee + "',phone = '" + phone + "'," +
                    "province='" + province + "',city = '" + city + "',district ='" + district + "'," +
                    "detailed = '" + detailed + "'where _id = '" + item_ID + "'");
            Toast.makeText(context, "修改成功", Toast.LENGTH_SHORT).show();
        }
    }
    //查询所有内容
    public Cursor query_db() {
        Cursor cursor = db.rawQuery("select * from delivery_address", null);
        return cursor;
    }
    //根据数据id查询数据内容
    public Cursor query_db(int item_ID) {
        Cursor cursor = db.rawQuery("select * from delivery_address where _id='" + item_ID
                + "';", null);
        return cursor;

    }


    // 删除某一条数据
    public void delete_db(int item_ID) {
        db.execSQL("delete from delivery_address where _id='" + item_ID + "'");
    }

    // 关闭数据库
    public void close_db() {
        db.close();
    }
}
