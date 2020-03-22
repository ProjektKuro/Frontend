package com.example.breadcrumbsview

data class NavigationItem(
        val text: String,
        val id: Int
) {
    override fun equals(other: Any?): Boolean {
        if (other is NavigationItem) {
            return other.id == id
        }
        return false
    }
}