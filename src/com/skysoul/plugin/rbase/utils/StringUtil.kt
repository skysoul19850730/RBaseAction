package com.skysoul.plugin.rbase.utils

object StringUtil {
    fun toClassName(s: String): String {
        var sb = ""
        var needUp = true//首字母大写
        s.forEach {
            if (it == '_') {
                needUp = true
            } else {
                if (needUp) {
                    sb += it.toUpperCase()
                } else {
                    sb += it
                }
                needUp = false
            }
        }
        return sb
    }

    fun toResName(s: String): String {
        var sb = ""
        s.forEach {
            if (it.isUpperCase() && sb.isNotEmpty()) {
                sb += "_"
            }
            sb += it.toLowerCase()
        }
        return sb
    }
}