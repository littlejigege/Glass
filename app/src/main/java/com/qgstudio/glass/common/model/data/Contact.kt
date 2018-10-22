package com.qgstudio.glass.common.model.data

import android.arch.persistence.room.Entity


data class Contact(var name: String, var phone: String, val id: Int = -1) {
}