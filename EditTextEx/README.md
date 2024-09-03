# EditTextEx

`EditTextEx` 是一个自定义的 Android 组件，继承自 `LinearLayout`，包含一个 `EditText`、一个显示/隐藏密码的图标和一个清除文本的图标。该组件旨在提供一个功能丰富且易于使用的文本输入框，适用于需要显示或隐藏密码输入的场景。

## 特性

- **显示/隐藏密码**：通过点击图标切换密码的可见性。
- **一键清除文本**：提供一个清除按钮，可以快速清除输入框中的文本。
- **自定义下划线**：支持自定义输入框的下划线颜色和宽度。
- **灵活的自定义**：提供方法可以灵活配置 `EditText`、密码显示/隐藏图标和清除文本图标。

## 使用方法

### 1. 在布局文件中使用

在布局文件（如 `activity_main.xml`）中使用 `EditTextEx`：

```xml
<com.sik.edittextex.EditTextEx
    android:id="@+id/editTextEx"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:underline_color="@android:color/holo_blue_light"
    app:underline_stroke_width="2dp"
    app:password_icon_show="true"
    app:clear_text_icon_show="true"
    app:show_password_icon="@drawable/icon_visibility"
    app:hide_password_icon="@drawable/icon_visibility_off"
    app:clear_text_icon="@drawable/icon_close" />
```

### 2. 在代码中使用

在代码中初始化和配置 `EditTextEx`：

```
kotlin复制代码val editTextEx = findViewById<EditTextEx>(R.id.editTextEx)

// 设置初始文本
editTextEx.text = "Hello, World!"

// 配置 EditText 的属性
editTextEx.configEditText {
    setHint("请输入密码")
    setTextColor(Color.BLACK)
}

// 配置密码显示/隐藏图标
editTextEx.configPasswordIcon {
    setImageDrawable(ContextCompat.getDrawable(context, R.drawable.icon_visibility))
}

// 配置清除文本图标
editTextEx.configClearTextIcon {
    setImageDrawable(ContextCompat.getDrawable(context, R.drawable.icon_close))
}
```

## 自定义属性

`EditTextEx` 提供了一些自定义属性，可以在 XML 中直接配置：

- `app:underline_color`：设置下划线的颜色。
- `app:underline_stroke_width`：设置下划线的宽度。
- `app:password_icon_show`：是否显示密码显示/隐藏图标。
- `app:clear_text_icon_show`：是否显示清除文本图标。
- `app:show_password_icon`：设置显示密码图标的 Drawable 资源。
- `app:hide_password_icon`：设置隐藏密码图标的 Drawable 资源。
- `app:clear_text_icon`：设置清除文本图标的 Drawable 资源。
