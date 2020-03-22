package com.wimatt.ux.drinkanddare.retrofit.models

object Model {
    //TODO für was ?
    data class Result(val query: Query)
    data class Query(val searchinfo: SearchInfo)
    data class SearchInfo(val totalhits: Int)
}