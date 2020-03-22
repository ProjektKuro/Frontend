package com.wimatt.ux.drinkanddare.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng

class StateViewModel : ViewModel() {
    var currentDice: MutableLiveData<Int> = MutableLiveData()
    var currentPlayerIndex = 0
    var maxRounds = 0
    var currentRound = 1
    var gameRunning = false
    public var query: MutableLiveData<String> = MutableLiveData()
    public var choosenLocation: MutableLiveData<ChoosenLocation> = MutableLiveData()

    fun resetData() {
        currentPlayerIndex = 0
        maxRounds = 5
        currentRound = 1
        gameRunning = false

    }

    fun setSearchQuery(queryStr: String) {
        query.value = queryStr
    }

    fun setChoosenLocation(choosenLocation: ChoosenLocation) {
        this.choosenLocation.value = choosenLocation
    }

    init {
        query.value = ""
        currentDice.value = 1
        resetData()
        choosenLocation.value = ChoosenLocation(LatLng(48.39841, 9.99155), "Ulm", "")
    }

    companion object {
        data class ChoosenLocation(
                val latLong: LatLng, val cityName: String?, val streetName: String?
        )
    }

}