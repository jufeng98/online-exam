package org.javamaster.fragmentlearning.data.model

import android.os.Parcel
import android.os.Parcelable


/**
 * @author yudong
 * @date 2019/9/2
 */
data class Person(var name: String, var age: Int) : Parcelable {
    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(name) // 写出name
        dest.writeInt(age) // 写出age
    }


    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Person> = object : Parcelable.Creator<Person> {
            override fun createFromParcel(source: Parcel): Person {
                return Person(source.readString()!!, source.readInt())
            }

            override fun newArray(size: Int): Array<Person?> {
                return arrayOfNulls(size)
            }
        }
    }
}