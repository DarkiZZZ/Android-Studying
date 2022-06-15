package com.example.alertdialogtest.entities

class AvailableVolumeValues(val values: List<Int>,
                            val currentIndex: Int) {
    companion object{

        fun createVolumeValues(currentValue: Int): AvailableVolumeValues{
            val values: IntProgression = (0..100 step 10)
            val currentIndex: Int = values.indexOf(currentValue)
            return if (currentIndex == -1){
                val list: List<Int> = values + currentValue
                AvailableVolumeValues(list, list.lastIndex)
            }else{
                AvailableVolumeValues(values.toList(), currentIndex)
            }
        }
    }

}