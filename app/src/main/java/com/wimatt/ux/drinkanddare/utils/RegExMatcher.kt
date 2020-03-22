package com.wimatt.ux.drinkanddare.utils

import java.util.regex.Pattern

class RegExMatcher {
    companion object {
        private val sPattern = Pattern.compile("(?<=\\#)[A-Za-z1-9]+")
        fun getTags(text: String): List<String>  {
            var m1 = sPattern.matcher(text)
            val list = mutableListOf<String>()
            while (m1.find()) {
                list.add(m1.group())
            }
            return list
        }
    }
}