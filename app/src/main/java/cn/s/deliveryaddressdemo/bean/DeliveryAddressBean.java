package cn.s.deliveryaddressdemo.bean;

public class DeliveryAddressBean {

    private String _id;
    private String checked;
    private String consignee;
    private String phone;
    private String province;
    private String city;
    private String district;
    private String detailed;


    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {

        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDetailed() {
        return detailed;
    }

    public void setDetailed(String detailed) {
        this.detailed = detailed;
    }


    public String getPhone() {

        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getConsignee() {

        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String get_id() {

        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}
