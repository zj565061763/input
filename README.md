## Gradle
[![](https://jitpack.io/v/zj565061763/input.svg)](https://jitpack.io/#zj565061763/input)

## demo
![](https://thumbsnap.com/i/2M1Yl8Vm.gif?0404)

```xml
<com.fanwe.lib.input.FEditTextContainer
    android:layout_width="match_parent"
    android:layout_height="50dp">

    <com.fanwe.lib.input.FEditText
        android:layout_width="match_parent"
        android:layout_height="match_parent" />

    <com.fanwe.lib.input.stateview.EditTextClearImageView
        android:layout_width="16dp"
        android:layout_height="16dp"
        android:layout_gravity="right|center_vertical"
        android:layout_marginRight="10dp" />

</com.fanwe.lib.input.FEditTextContainer>
```

```xml
<com.fanwe.lib.input.FEditTextContainer
    android:layout_width="match_parent"
    android:layout_height="50dp">

    <com.fanwe.lib.input.FEditText
        android:layout_width="match_parent"
        android:layout_height="match_parent" />

    <com.fanwe.lib.input.stateview.EditTextPasswordImageView
        android:layout_width="18dp"
        android:layout_height="18dp"
        android:layout_gravity="right|center_vertical"
        android:layout_marginRight="10dp" />

</com.fanwe.lib.input.FEditTextContainer>
```

```xml
<com.fanwe.lib.input.FEditTextContainer
    android:layout_width="match_parent"
    android:layout_height="50dp">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:gravity="center_vertical"
        android:orientation="horizontal"
        android:paddingLeft="10dp"
        android:paddingRight="10dp">

        <com.fanwe.lib.input.FEditText
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_marginRight="10dp"
            android:layout_weight="1"
            android:background="@null" />

        <com.fanwe.lib.input.stateview.EditTextClearImageView
            android:layout_width="16dp"
            android:layout_height="16dp"
            android:layout_marginRight="10dp" />

        <com.fanwe.lib.input.stateview.EditTextPasswordImageView
            android:layout_width="18dp"
            android:layout_height="18dp" />

    </LinearLayout>

    <com.fanwe.lib.input.stateview.EditTextFocusImageView
        android:layout_width="match_parent"
        android:layout_height="1dp"
        android:layout_gravity="bottom"
        android:background="@drawable/selector_edit_underline" />

</com.fanwe.lib.input.FEditTextContainer>
```