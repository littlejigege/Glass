package com.qgstudio.glass.common.model.data

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "records")
data class Record(var latitude: Double,
                  var longitude: Double,
                  var info: String = "",
                  @PrimaryKey @NotNull var warningTime: Long) {

    val timeString:String
    get() {
        return SimpleDateFormat("HH : ss").format(Date(warningTime))
    }
}