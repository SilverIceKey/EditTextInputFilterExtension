package com.sik.inputfilterextension

import android.text.InputFilter
import android.text.Spanned

/**
 * 长度限制输入过滤器。
 * @param limitLength 允许的最大字符数。
 * @param hintAction 当输入长度超过限制时调用的动作，例如显示提示。
 */
class LengthLimitInputFilter(val limitLength: Int, val hintAction: () -> Unit = {}) : InputFilter {
    override fun filter(
        source: CharSequence?, // 输入的文本
        start: Int,            // 输入的文本的开始位置
        end: Int,              // 输入的文本的结束位置
        dest: Spanned?,        // 当前显示的内容
        dstart: Int,           // 将要替换或添加的内容的起始位置
        dend: Int              // 将要替换或添加的内容的结束位置
    ): CharSequence {
        // 当前内容的长度 + 新输入的内容的长度 - 被替换的内容的长度
        val newLength = dest!!.length + (end - start) - (dend - dstart)

        if (newLength <= limitLength) { // 如果新内容长度不超过限制
            return source!! // 返回原始输入内容，无需过滤
        } else {
            // 如果输入超过了长度限制
            hintAction.invoke() // 执行定义的动作，如显示提示

            // 计算超出部分的索引
            val keep = limitLength - (dest.length - (dend - dstart))

            // 如果没有位置保留，直接返回空字符串，避免超出部分的输入
            if (keep <= 0) {
                return ""
            }

            // 如果开始和保留的索引在有效范围内，返回可保留的部分
            if (keep >= end - start) {
                return source!!.subSequence(start, end)
            }

            // 否则只返回直到限制允许的部分
            return source!!.subSequence(start, start + keep)
        }
    }
}