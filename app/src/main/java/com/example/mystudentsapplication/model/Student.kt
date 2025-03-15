package com.example.mystudentsapplication.model

import androidx.room.Entity
import androidx.room.PrimaryKey

import android.os.Parcel
import android.os.Parcelable


@Entity
data class Student(
    val fullname: String?,
    @PrimaryKey val id: String,
    val phone: String?,
    val address: String?,
    var isChecked: Boolean
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString() ?: "",
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(fullname)
        parcel.writeString(id)
        parcel.writeString(phone)
        parcel.writeString(address)
        parcel.writeByte(if (isChecked) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Student> {
        override fun createFromParcel(parcel: Parcel): Student {
            return Student(parcel)
        }

        override fun newArray(size: Int): Array<Student?> {
            return arrayOfNulls(size)
        }
    }
}