package com.sik.inputfilterextension

import android.text.InputFilter
import android.text.Spanned

/**
 * 数字范围过滤器
 */
class NumberRangeInputFilter(
    private val startNumber: Number,
    private val endNumber: Number,
    private val hintAction: (InputFilterErrorEnum) -> Unit = {}
) : InputFilter {
    override fun filter(
        source: CharSequence?,
        start: Int,
        end: Int,
        dest: Spanned?,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        try {
            // 合成新的字符串
            val newString = StringBuilder(dest).apply {
                replace(dstart, dend, source?.subSequence(start, end).toString())
            }.toString()

            // 检查是否为空
            if (newString.isEmpty()) {
                return null
            }

            // 尝试解析数字
            val inputNumber = newString.toDouble()

            // 判断数字是否在范围内
            if (inputNumber in startNumber.toDouble()..endNumber.toDouble()) {
                return null // 返回 null 表示不需要过滤，接受输入
            }
        } catch (e: NumberFormatException) {
            // 输入不是有效的数字
            hintAction(InputFilterErrorEnum.INPUT_NOT_NUMBER)
        }

        // 提示错误操作
        hintAction(InputFilterErrorEnum.INPUT_OUT_OF_RANGE)

        // 返回空字符串表示过滤掉输入
        return ""
    }
}