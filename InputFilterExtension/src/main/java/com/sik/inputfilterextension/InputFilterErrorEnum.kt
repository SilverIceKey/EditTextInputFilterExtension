package com.sik.inputfilterextension

/**
 * 过滤器的错误枚举
 */
enum class InputFilterErrorEnum(private val errorMsg: String) {
    INPUT_NOT_NUMBER("输入的不是数字"),
    INPUT_OUT_OF_RANGE("输入的数字超出范围")
}