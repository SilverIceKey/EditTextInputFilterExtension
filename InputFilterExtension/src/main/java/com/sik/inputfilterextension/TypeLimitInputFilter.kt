package com.sik.inputfilterextension

import android.text.InputFilter
import android.text.Spanned

/**
 * 输入内容类型限制过滤器。
 * @param allowedTypes 允许的字符类型。
 * @param hintAction 当输入不符合限制时调用的动作，例如显示提示。
 */
class TypeLimitInputFilter(
    private val allowedTypes: Set<InputType>,
    private val hintAction: () -> Unit = {}
) : InputFilter {

    enum class InputType {
        UPPERCASE, LOWERCASE, DIGIT, SPECIAL
    }

    override fun filter(
        source: CharSequence?, // 输入的文本
        start: Int,            // 输入的文本的开始位置
        end: Int,              // 输入的文本的结束位置
        dest: Spanned?,        // 当前显示的内容
        dstart: Int,           // 将要替换或添加的内容的起始位置
        dend: Int              // 将要替换或添加的内容的结束位置
    ): CharSequence {
        // 检查输入内容类型
        for (i in start until end) {
            val char = source!![i]
            if (!isAllowed(char)) {
                hintAction.invoke()
                return ""
            }
        }

        // 如果输入内容符合要求，返回原始输入内容
        return source!!
    }

    // 判断字符是否允许
    private fun isAllowed(char: Char): Boolean {
        return when {
            allowedTypes.contains(InputType.UPPERCASE) && char.isUpperCase() -> true
            allowedTypes.contains(InputType.LOWERCASE) && char.isLowerCase() -> true
            allowedTypes.contains(InputType.DIGIT) && char.isDigit() -> true
            allowedTypes.contains(InputType.SPECIAL) && !char.isLetterOrDigit() -> true
            else -> false
        }
    }
}
