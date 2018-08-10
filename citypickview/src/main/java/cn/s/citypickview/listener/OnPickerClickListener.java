package cn.s.citypickview.listener;


import cn.s.citypickview.entity.PickerData;

public interface OnPickerClickListener {
    public void OnPickerClick(PickerData pickerData);
    public void OnPickerConfirmClick(PickerData pickerData);
}
