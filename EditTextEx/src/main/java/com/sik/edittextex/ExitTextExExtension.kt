package com.sik.edittextex

import androidx.core.widget.addTextChangedListener
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener

/**
 * 设置EditTextText
 */
@BindingAdapter("app:EditTextValue")
fun setEditTextValue(editTextEx: EditTextEx, value: String?) {
    if (editTextEx.text != value) {
        editTextEx.text = value ?: ""
    }
}

/**
 * 获取EditTextEx的文本
 */
@InverseBindingAdapter(attribute = "app:EditTextValue", event = "app:EditTextValue_AttrChanged")
fun getEditTextValue(editTextEx: EditTextEx): String {
    return editTextEx.text
}

/**
 * 监听EditTextEx的文本变化
 */
@BindingAdapter("app:EditTextValue_AttrChanged")
fun setEditTextListeners(editTextEx: EditTextEx, attrChange: InverseBindingListener) {
    editTextEx.configEditText {
        addTextChangedListener {
            attrChange.onChange()
        }
    }
}
