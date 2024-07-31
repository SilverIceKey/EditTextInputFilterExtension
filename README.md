# EditTextInputFilterExtension

[![](https://jitpack.io/v/SilverIceKey/EditTextInputFilterExtension.svg)](https://jitpack.io/#SilverIceKey/EditTextInputFilterExtension)

`EditTextInputFilterExtension` 是一个为Android `EditText` 控件提供扩展输入过滤功能的库。这个库可以帮助开发者快速实现各种输入限制，如限制输入长度、格式、禁止输入特定字符等。

## 特性

- **限制输入字符长度**
- **过滤特殊字符**
- **支持正则表达式定义的输入模式**
- **可定制的过滤策略，如白名单或黑名单模式**
- **简单集成，易于使用**
- **增加自定义EditText带密码显示隐藏和一键清除按钮以及下划线**

## 安装

在你的 `build.gradle` 文件中添加以下依赖项：

```groovy
dependencies {
    implementation("com.github.silvericekey:EditTextInputFilterExtension:TAG")
}
```

## 使用方法

在你的 `Activity` 或 `Fragment` 中配置 `EditText` 使用 `EditTextInputFilterExtension`：

```kotlin
import com.example.edittextinputfilterextension.EditTextInputFilter

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editText = findViewById<EditText>(R.id.editText)
        val filters = arrayOf(LengthLimitInputFilter(limitLength = 10, hintAction = {}))
        editText.filters = filters
    }
}
```

这个示例设置了 `EditText` 长度限制为10位，并且当输入超过10位时触发指定的方法。

## 目前存在的过滤器

| 过滤器名称             | 过滤器作用           |
| ---------------------- | -------------------- |
| LengthLimitInputFilter | 长度限制输入过滤器。 |



## 贡献

欢迎贡献代码！请阅读 [CONTRIBUTING.md](./CONTRIBUTING.md) 了解详细的贡献流程。

## 许可证

`EditTextInputFilterExtension` 遵循 MIT 许可证。有关详细信息，请参阅 [LICENSE](./LICENSE)。
