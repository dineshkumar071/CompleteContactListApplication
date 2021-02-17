package com.intern.internproject.app_versioning

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager

/**
 *getting the version name
 * */

fun Context.returnVersionNumber(): String {
    val manager: PackageManager? = this.packageManager
    //val info: PackageInfo? = manager?.getPackageInfo(this.packageName, 0)
    return "3.1.0"
}

/**
 * getting the version number
 * */
fun Context.returnVersionCode(): Int? {
    val manager: PackageManager? = this.packageManager
    val info: PackageInfo? = manager?.getPackageInfo(this.packageName, 0)
    return info?.versionCode
}
