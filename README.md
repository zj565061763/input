## Gradle
[![](https://jitpack.io/v/zj565061763/input.svg)](https://jitpack.io/#zj565061763/input)

## demo
![](https://thumbsnap.com/i/2M1Yl8Vm.gif?0404)

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">

    <com.fanwe.lib.input.FClearEditText
        android:id="@+id/et_clear"
        android:layout_width="match_parent"
        android:layout_height="50dp" />

    <com.fanwe.lib.input.FPasswordEditText
        android:id="@+id/et_password"
        android:layout_width="match_parent"
        android:layout_height="50dp" />

</LinearLayout>
```

```java
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
         * 默认图片为R.drawable.lib_views_selector_edit_clear，支持在主项目中定义该图片来覆盖库中的图片
         */
        et_clear.setDrawableClear(getResources().getDrawable(R.drawable.lib_views_selector_edit_clear));

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
```