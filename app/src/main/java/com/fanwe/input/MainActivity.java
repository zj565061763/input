package com.fanwe.input;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.fanwe.lib.input.FClearEditText;
import com.fanwe.lib.input.FPasswordEditText;

public class MainActivity extends AppCompatActivity
{
    private FClearEditText et_clear;
    private FPasswordEditText et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_clear = findViewById(R.id.et_clear);
        et_password = findViewById(R.id.et_password);

        /**
         * 设置清空内容图片
         * 默认图片为R.drawable.lib_input_selector_edit_clear，支持在主项目中定义该图片来覆盖库中的图片
         */
        et_clear.setDrawableClear(getResources().getDrawable(R.drawable.lib_input_selector_edit_clear));

        /**
         * 设置密码可见状态的图片
         * 默认图片为R.drawable.lib_input_ic_edit_password_visible，支持在主项目中定义该图片来覆盖库中的图片
         */
        et_password.setDrawablePasswordVisible(getResources().getDrawable(R.drawable.lib_input_ic_edit_password_visible));
        /**
         * 设置密码不可见状态的图片
         * 默认图片为R.drawable.lib_input_ic_edit_password_invisible，支持在主项目中定义该图片来覆盖库中的图片
         */
        et_password.setDrawablePasswordInvisible(getResources().getDrawable(R.drawable.lib_input_ic_edit_password_invisible));
    }
}
