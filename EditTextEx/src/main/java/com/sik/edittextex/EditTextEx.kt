package com.sik.edittextex

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.text.InputType
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.widget.addTextChangedListener

class EditTextEx(context: Context?, attrs: AttributeSet?) : LinearLayout(context, attrs) {
    /**
     * 默认图标大小
     */
    private val defaultIconSize = 72

    /**
     * 最小输入框宽度
     */
    private val minEditTextWidth = 200

    /**
     * 默认显示密码的图标
     */
    private var defaultShowPasswordIcon: Drawable? =
        context?.getDrawable(R.drawable.icon_visibility)

    /**
     * 默认隐藏密码图标
     */
    private var defaultHidePasswordIcon: Drawable? =
        context?.getDrawable(R.drawable.icon_visibility_off)

    /**
     * 默认清除图标
     */
    private var defaultClearTextIcon: Drawable? = context?.getDrawable(R.drawable.icon_close)

    /**
     * 显示隐藏密码图标是否显示
     */
    private var passwordIconShow: Boolean = true

    /**
     * 一键清除图标是否显示
     */
    private var clearTextIconShow: Boolean = true

    /**
     * 编辑框
     */
    private var editText: EditText

    /**
     * 默认inputType
     */
    private var inputType = InputType.TYPE_CLASS_TEXT

    /**
     * 显示隐藏密码图标
     */
    private var passwordIcon: ImageView

    /**
     * 清除文本图标
     */
    private var clearTextIcon: ImageView

    /**
     * 宽高
     */
    private var contentWidth = 0
    private var contentHeight = 0

    /**
     * 下划线宽度
     */
    private var underlineStrokeWidth = 2f

    /**
     * 上下文
     */
    private val mContext: Context? = context

    /**
     * 下划线画笔
     */
    private val underLinePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        strokeWidth = underlineStrokeWidth
        color = Color.BLACK
    }

    init {
        setAttrs(attrs)
        underLinePaint.strokeWidth = underlineStrokeWidth
        editText = createEditText()
        passwordIcon = createPasswordIcon()
        clearTextIcon = createClearTextIcon()
        gravity = Gravity.CENTER_VERTICAL
        setWillNotDraw(false)
        addViews()
        // 使用post确保视图完成布局后调整宽高
        post { adjustLayout() }
    }

    /**
     * 创建EditText控件
     */
    private fun createEditText(): EditText {
        return EditText(context).apply {
            layoutParams =
                LayoutParams(minEditTextWidth, ViewGroup.LayoutParams.WRAP_CONTENT).apply {
                    weight = 1f
                }
            setTextColor(Color.BLACK)
            background = null
        }
    }

    /**
     * 创建显示隐藏密码图标控件
     */
    private fun createPasswordIcon(): ImageView {
        return ImageView(context).apply {
            layoutParams = LayoutParams(defaultIconSize, defaultIconSize)
            setImageDrawable(defaultHidePasswordIcon)
        }
    }

    /**
     * 创建清除文本图标控件
     */
    private fun createClearTextIcon(): ImageView {
        return ImageView(context).apply {
            layoutParams = LayoutParams(defaultIconSize, defaultIconSize)
                .apply { leftMargin = defaultIconSize / 3 }
            setImageDrawable(defaultClearTextIcon)
            visibility = View.GONE
        }
    }

    /**
     * 添加子视图
     */
    private fun addViews() {
        addView(editText)
        if (passwordIconShow) addView(passwordIcon)
        if (clearTextIconShow) addView(clearTextIcon)
    }

    /**
     * 调整布局参数
     */
    private fun adjustLayout() {
        contentWidth = width
        contentHeight = height
        resizeEditText()
        resizePasswordIcon()
        resizeClearTextIcon()
    }

    /**
     * 解析自定义属性
     */
    private fun setAttrs(attrs: AttributeSet?) {
        val obtainAttributes = resources.obtainAttributes(attrs, R.styleable.EditTextEx)
        defaultShowPasswordIcon = obtainAttributes.getDrawable(
            R.styleable.EditTextEx_show_password_icon
        ) ?: defaultShowPasswordIcon
        defaultHidePasswordIcon = obtainAttributes.getDrawable(
            R.styleable.EditTextEx_hide_password_icon
        ) ?: defaultHidePasswordIcon
        defaultClearTextIcon =
            obtainAttributes.getDrawable(R.styleable.EditTextEx_clear_text_icon)
                ?: defaultClearTextIcon
        passwordIconShow =
            obtainAttributes.getBoolean(R.styleable.EditTextEx_password_icon_show, true)
        clearTextIconShow =
            obtainAttributes.getBoolean(R.styleable.EditTextEx_clear_text_icon_show, true)
        underlineStrokeWidth =
            obtainAttributes.getFloat(R.styleable.EditTextEx_underline_stroke_width, 2f)
        inputType = obtainAttributes.getInt(
            R.styleable.EditTextEx_android_inputType,
            InputType.TYPE_CLASS_TEXT
        )
        inputType = when {
            (inputType != InputType.TYPE_CLASS_PHONE && inputType and InputType.TYPE_CLASS_NUMBER == InputType.TYPE_CLASS_NUMBER) -> {
                InputType.TYPE_CLASS_NUMBER
            }

            (inputType and InputType.TYPE_CLASS_TEXT == InputType.TYPE_CLASS_TEXT) -> {
                InputType.TYPE_CLASS_TEXT
            }

            else -> {
                inputType
            }
        }
        obtainAttributes.recycle()
    }

    /**
     * 初始化输入框
     */
    private fun resizeEditText() {
        editText.layoutParams = (editText.layoutParams as LayoutParams).apply {
            width =
                if (contentWidth == 0) minEditTextWidth else contentWidth - paddingLeft - paddingRight -
                        (if (passwordIconShow) defaultIconSize else 0) -
                        (if (clearTextIconShow) defaultIconSize else 0)
            height = ViewGroup.LayoutParams.WRAP_CONTENT
        }
        editText.addTextChangedListener {
            if (editText.text.isNotEmpty() && clearTextIconShow) {
                clearTextIcon.visibility = View.VISIBLE
            } else {
                clearTextIcon.visibility = View.GONE
            }
        }
        editText.requestLayout()
    }

    /**
     * 初始化密码显示隐藏图标
     */
    private fun resizePasswordIcon() {
        if (passwordIconShow) {
            passwordIcon.layoutParams = (passwordIcon.layoutParams as LayoutParams).apply {
                width = defaultIconSize
                height = defaultIconSize
            }
            passwordIcon.setOnClickListener {
                val currentInputType = editText.inputType
                val currentSelection = editText.selectionStart
                val passwordInputType =
                    when {
                        inputType == InputType.TYPE_CLASS_TEXT -> {
                            inputType or InputType.TYPE_TEXT_VARIATION_PASSWORD
                        }

                        inputType == InputType.TYPE_CLASS_NUMBER -> {
                            inputType or InputType.TYPE_NUMBER_VARIATION_PASSWORD
                        }

                        else -> {
                            inputType or InputType.TYPE_TEXT_VARIATION_PASSWORD
                        }
                    }
                val visiblePasswordInputType =
                    when {
                        inputType == InputType.TYPE_CLASS_TEXT -> {
                            inputType or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                        }

                        inputType == InputType.TYPE_CLASS_NUMBER -> {
                            inputType or InputType.TYPE_NUMBER_VARIATION_NORMAL
                        }

                        else -> {
                            inputType or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                        }
                    }
                if (currentInputType == passwordInputType) {
                    editText.inputType = visiblePasswordInputType
                    passwordIcon.setImageDrawable(defaultShowPasswordIcon)
                } else {
                    editText.inputType = passwordInputType
                    passwordIcon.setImageDrawable(defaultHidePasswordIcon)
                }
                // Move the cursor to the end of the text
                editText.setSelection(currentSelection)
                editText.postInvalidate()
            }
            editText.inputType =
                when {
                    inputType == InputType.TYPE_CLASS_TEXT -> {
                        inputType or InputType.TYPE_TEXT_VARIATION_PASSWORD
                    }

                    inputType == InputType.TYPE_CLASS_NUMBER -> {
                        inputType or InputType.TYPE_NUMBER_VARIATION_PASSWORD
                    }

                    else -> {
                        inputType or InputType.TYPE_TEXT_VARIATION_PASSWORD
                    }
                }

            passwordIcon.requestLayout()
        }
    }

    /**
     * 初始化一键清除图标
     */
    private fun resizeClearTextIcon() {
        if (clearTextIconShow) {
            clearTextIcon.layoutParams = (clearTextIcon.layoutParams as LayoutParams).apply {
                width = defaultIconSize
                height = defaultIconSize
            }
            clearTextIcon.setOnClickListener {
                editText.setText("")
            }
            clearTextIcon.requestLayout()
        }
    }

    /**
     * 绘制底部线条
     */
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (background == null) {
            val startX = paddingLeft.toFloat()
            val endX = (width - paddingRight).toFloat()
            val yPosition = height - paddingBottom - underLinePaint.strokeWidth / 2f
            canvas.drawLine(startX, yPosition, endX, yPosition, underLinePaint)
        }
    }

    /**
     * EditText文本
     */
    var text: String
        get() {
            return editText.text?.toString() ?: ""
        }
        set(value) {
            editText.setText(value)
        }

    /**
     * 配置EditText
     */
    fun configEditText(config: EditText.() -> Unit) {
        config(editText)
    }

    /**
     * 配置密码显示隐藏图标
     */
    fun configPasswordIcon(config: ImageView.() -> Unit) {
        config(passwordIcon)
    }

    /**
     * 配置一键清除图标
     */
    fun configClearTextIcon(config: ImageView.() -> Unit) {
        config(clearTextIcon)
    }
}
