package org.alex.iconinputlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.style.AbsoluteSizeSpan;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.alex.util.LogUtil;

import org.alex.alexlibrary.R;

import java.lang.reflect.Field;
import java.util.Random;

/**
 * 作者：Alex
 * 时间：2016年09月03日    21:29
 * 简述：
 */
@SuppressWarnings("all")
public class IconInputLayout extends RelativeLayout {
    /**
     * 右边的 小按钮
     */
    private ImageView rightImageView;
    /**
     * 左边的 小按钮
     */
    private ImageView leftImageView;
    /**
     * 左边的 小按钮
     */
    private TextView leftTextView;
    /**
     * 输入框
     */
    private EditText editText;
    /**
     * 右边 按钮的 左边距
     */
    private int rightLeftPadding;
    /**
     * 右边 按钮的 右边距
     */
    private int rightRightPadding;
    /**
     * 左边 按钮的 左边距
     */
    private int leftLeftPadding;
    /**
     * 左边 按钮的 右边距
     */
    private int leftRightPadding;
    /**
     * 右边 图标 控件的 id
     */
    private int rightId;
    /**
     * 左边 图标 控件的 id
     */
    private int leftId;
    /**
     * 右按钮 的 功能 枚举
     * 1 密码 可见 性
     * 2 清空 输入框
     */
    private int rightFunction;
    private static final InputFilter[] NO_FILTERS = new InputFilter[0];
    private int inputType;
    private int rightResId;
    private int rightResId2;
    /**
     * 密码 可见 性
     */
    private boolean pwdVisibility;
    private static final int nextInt = 100000;

    public IconInputLayout(Context context) {
        super(context);
        initView(null);
    }

    public IconInputLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(attrs);
    }

    private void initView(AttributeSet attrs) {
        MyOnClickListener onClickListener = new MyOnClickListener();
        MyTextWatcher textWatcher = new MyTextWatcher();
        Context context = getContext();
        pwdVisibility = false;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.IconInputLayout);
        rightLeftPadding = typedArray.getDimensionPixelSize(R.styleable.IconInputLayout_right_leftPadding, 0);
        rightRightPadding = typedArray.getDimensionPixelSize(R.styleable.IconInputLayout_right_rightPadding, 0);
        leftLeftPadding = typedArray.getDimensionPixelSize(R.styleable.IconInputLayout_left_leftPadding, 0);
        leftRightPadding = typedArray.getDimensionPixelSize(R.styleable.IconInputLayout_left_rightPadding, 0);
        rightFunction = typedArray.getInteger(R.styleable.IconInputLayout_right_function, 0);
        int leftResId = typedArray.getResourceId(R.styleable.IconInputLayout_leftDrawableResId, -1);
        rightResId = typedArray.getResourceId(R.styleable.IconInputLayout_rightDrawableResId, -1);
        rightResId2 = typedArray.getResourceId(R.styleable.IconInputLayout_rightDrawableResId2, -1);
        String leftText = typedArray.getString(R.styleable.IconInputLayout_leftText);
        String hint = typedArray.getString(R.styleable.IconInputLayout_hint);
        String text = typedArray.getString(R.styleable.IconInputLayout_text);
        String digits = typedArray.getString(R.styleable.IconInputLayout_digits);
        float textSizeHint = typedArray.getDimension(R.styleable.IconInputLayout_textSizeHint, 0F);
        int textColorHint = typedArray.getColor(R.styleable.IconInputLayout_textColorHint, Color.parseColor("#555555"));
        int leftTextColor = typedArray.getColor(R.styleable.IconInputLayout_leftTextColor, Color.parseColor("#000000"));
        float leftTextSize = typedArray.getDimension(R.styleable.IconInputLayout_leftTextSize, 0F);
        int textColor = typedArray.getColor(R.styleable.IconInputLayout_textColor, Color.parseColor("#000000"));
        int textCursorDrawableResId = typedArray.getResourceId(R.styleable.IconInputLayout_textCursorDrawable, -1);
        float textSize = typedArray.getDimension(R.styleable.IconInputLayout_textSize, 0F);
        inputType = typedArray.getInt(R.styleable.IconInputLayout_inputType, EditorInfo.TYPE_NULL);
        int maxLength = typedArray.getInt(R.styleable.IconInputLayout_maxLength, -1);
        textSize = (textSize <= 0) ? sp2px(14) : textSize;
        leftTextSize = (leftTextSize <= 0) ? sp2px(14) : leftTextSize;
        textSizeHint = (textSizeHint <= 0) ? sp2px(14) : textSizeHint;
        if (!TextUtils.isEmpty(leftText)) {
            leftId = new Random().nextInt(nextInt);
            leftTextView = new TextView(getContext());
            leftTextView.setText(leftText);
            leftTextView.setPadding(leftLeftPadding, 0, leftRightPadding, 0);
            leftTextView.setId(leftId);
            leftTextView.setGravity(Gravity.CENTER_VERTICAL);
            leftTextView.setTextColor(leftTextColor);
            leftTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, leftTextSize);
            LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT | RelativeLayout.CENTER_VERTICAL);
            addView(leftTextView, params);
        }
        if (leftResId != -1) {
            leftId = new Random().nextInt(nextInt);
            leftImageView = new ImageView(getContext());
            leftImageView.setImageResource(leftResId);
            leftImageView.setPadding(leftLeftPadding, 0, leftRightPadding, 0);
            leftImageView.setId(leftId);
            leftImageView.setOnClickListener(onClickListener);
            LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            addView(leftImageView, params);
        }
        if (rightResId != -1) {
            rightId = new Random().nextInt(nextInt);
            rightImageView = new ImageView(getContext());
            rightImageView.setImageResource(rightResId);
            rightImageView.setPadding(rightLeftPadding, 0, rightRightPadding, 0);
            rightImageView.setId(rightId);
            rightImageView.setOnClickListener(onClickListener);
            LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            addView(rightImageView, params);
        }
        editText = new EditText(getContext());
        editText.setSingleLine(true);
        editText.setInputType(inputType);
        editText.setBackgroundResource(android.R.color.transparent);
        editText.setTextColor(textColor);
        editText.setGravity(Gravity.CENTER_VERTICAL);
        editText.setHintTextColor(textColorHint);
        editText.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        editText.addTextChangedListener(textWatcher);
        if (rightFunction == 1) {
            setPwdVisibility(pwdVisibility);
        }
        setCursorDrawableColor(textCursorDrawableResId);
        if (maxLength >= 0) {
            editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        } else {
            editText.setFilters(NO_FILTERS);
        }
        if (!TextUtils.isEmpty(hint)) {
            SpannableString spannableString = new SpannableString(hint);
            AbsoluteSizeSpan absoluteSizeSpan = new AbsoluteSizeSpan((int) textSizeHint);
            spannableString.setSpan(absoluteSizeSpan, 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            editText.setHint(new SpannedString(spannableString));
        }
        if (!TextUtils.isEmpty(text)) {
            editText.setText(text);
            editText.setSelection(text.length());
        }
        if (!TextUtils.isEmpty(digits)) {
            editText.setKeyListener(DigitsKeyListener.getInstance(digits));
        }
        LayoutParams editTextParam = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        if (!TextUtils.isEmpty(leftText)) {
            editTextParam.addRule(RelativeLayout.RIGHT_OF, leftTextView.getId());
        }
        if (leftResId != -1) {
            editTextParam.addRule(RelativeLayout.RIGHT_OF, leftImageView.getId());
        }
        if (rightResId != -1) {
            editTextParam.addRule(RelativeLayout.LEFT_OF, rightImageView.getId());
        }
        addView(editText, editTextParam);
        /*回收资源*/
        typedArray.recycle();
    }

    /**
     * 设置 输入框游标的  资源 id
     */
    public void setCursorDrawableColor(@IdRes int textCursorDrawableResId) {
        if (textCursorDrawableResId <= 0) {
            return;
        }
        try {
            // https://github.com/android/platform_frameworks_base/blob/kitkat-release/core/java/android/widget/TextView.java#L562-564
            Field f = TextView.class.getDeclaredField("mCursorDrawableRes");
            f.setAccessible(true);
            f.set(editText, textCursorDrawableResId);
        } catch (Throwable ignored) {
            LogUtil.e(ignored);
        }
    }

    public EditText getEditText() {
        return editText;
    }

    /**
     * 获取输入框 内容
     */
    public String getText() {
        if ((editText == null) || (editText.getText() == null)) {
            return "";
        }
        return editText.getText().toString();
    }

    /**
     * 设置输入框内容
     */
    public void setText(@NonNull String text) {
        if ((editText != null) && (text != null)) {
            editText.setText(text);
            editText.setSelection(text.length());
        }
    }

    /**
     * 当前 密码的 可见性
     *
     * @param pwdVisibility true 可见， false 不可见；默认不可见
     */
    public void setPwdVisibility(boolean pwdVisibility) {
        this.pwdVisibility = pwdVisibility;
        if (pwdVisibility) {
            if (rightImageView != null) {
                rightImageView.setImageResource(rightResId);
            }
            editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            if (rightImageView != null) {
                rightImageView.setImageResource(rightResId2);
            }
            editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        editText.setSelection(getText().length());
    }

    private final class MyOnClickListener implements OnClickListener {
        /**
         * 右按钮 的 功能 枚举
         * 1 当前 密码 可见性
         * 2 清空 输入框
         */
        @Override
        public void onClick(View view) {
            if ((leftImageView != null) && (leftImageView.getId() == view.getId())) {

            } else if ((rightImageView != null) && (rightImageView.getId() == view.getId())) {
                if (rightFunction == 2) {
                    editText.setText("");
                }
                if (rightFunction == 1) {
                    pwdVisibility = !pwdVisibility;
                    setPwdVisibility(pwdVisibility);
                }
            }
        }
    }


    private final class MyTextWatcher implements TextWatcher {
        /**
         * 文字变化前
         */
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        /**
         * 文字变化时
         */
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        /**
         * 文字变化后
         */
        @Override
        public void afterTextChanged(Editable s) {
            if (TextUtils.isEmpty(s) && (rightImageView != null)) {
                rightImageView.setVisibility(View.INVISIBLE);
                rightImageView.setClickable(false);
            } else if (!TextUtils.isEmpty(s) && (rightImageView != null)) {
                rightImageView.setVisibility(View.VISIBLE);
                rightImageView.setClickable(true);
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isInEditMode()) {
            return;
        }
    }

    /**
     * 数据转换: dp---->px
     */
    private float dp2Px(float dp) {
        return dp * getContext().getResources().getDisplayMetrics().density;
    }

    /**
     * sp转px
     */
    private int sp2px(float sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getContext().getResources().getDisplayMetrics());
    }
}
