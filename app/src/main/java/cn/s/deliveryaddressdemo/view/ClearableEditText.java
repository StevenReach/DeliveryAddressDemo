package cn.s.deliveryaddressdemo.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import cn.s.deliveryaddressdemo.R;


/**
 * 可一键清空的EditText
 */

public class ClearableEditText extends AppCompatEditText implements View.OnFocusChangeListener, TextWatcher {
    private Drawable mClearDrawable;
    private boolean hasFocus;


    public ClearableEditText(Context context) {
        super(context);
    }

    public ClearableEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ClearableEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        // getCompoundDrawables() Returns drawables for the left(0), top(1), right(2) and bottom(3)
        mClearDrawable = getCompoundDrawables()[2]; // 获取drawableRight
        if (mClearDrawable == null) {
            // 如果为空，即没有设置drawableRight，则使用R.drawable.close这张图片
            mClearDrawable = getResources().getDrawable(R.drawable.ic_clear);
        }

        mClearDrawable.setBounds(0, 0, mClearDrawable.getIntrinsicWidth(), mClearDrawable.getIntrinsicHeight());

        setOnFocusChangeListener(this);
        addTextChangedListener(this);
        // 默认隐藏图标
        setDrawableVisible(false);
    }

    /**
     * 我们无法直接给EditText设置点击事件，只能通过按下的位置来模拟clear点击事件
     * 当我们按下的位置在图标包括图标到控件右边的间距范围内均算有效
     */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (getCompoundDrawables()[2] != null) {
                // 起始位置
                int start = getWidth() - getTotalPaddingRight() + getPaddingRight();
                // 结束位置
                int end = getWidth();
                boolean available = (event.getX() > start) && (event.getX() < end);
                if (available) {
                    this.setText("");
                }
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        this.hasFocus = hasFocus;
        // 当输入文字时,显示图标
        if (hasFocus && getText().length() > 0) {
            setDrawableVisible(true);
        } else {
            setDrawableVisible(false);
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int count, int after) {
        if (hasFocus) {
            setDrawableVisible(s.length() > 0);
        }
    }
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    protected void setDrawableVisible(boolean visible) {
        Drawable right = visible ? mClearDrawable : null;
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
    }

}
