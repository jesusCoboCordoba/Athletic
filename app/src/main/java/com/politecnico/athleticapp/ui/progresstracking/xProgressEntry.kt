package com.politecnico.athleticapp.ui.progresstracking

import android.os.Parcel
import android.os.Parcelable

data class ProgressEntry(
    var id: String = "",
    val weight: String = "",
    val measurement1: String = "",
    val measurement2: String = "",
    val timestamp: Long = 0L,
    var imageUrl: String? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readLong(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(weight)
        parcel.writeString(measurement1)
        parcel.writeString(measurement2)
        parcel.writeLong(timestamp)
        parcel.writeString(imageUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ProgressEntry> {
        override fun createFromParcel(parcel: Parcel): ProgressEntry {
            return ProgressEntry(
                parcel.readString().toString(),
                parcel.readString().toString(),
                parcel.readString().toString(),
                parcel.readString().toString(),
                parcel.readLong(),
                parcel.readString()
            )
        }

        override fun newArray(size: Int): Array<ProgressEntry?> {
            return arrayOfNulls(size)
        }
    }
}