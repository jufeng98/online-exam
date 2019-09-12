package org.javamaster.fragmentlearning.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.util.Log
import androidx.core.content.ContextCompat
import org.apache.commons.lang3.StringUtils

/**
 * @author yudong
 * @date 2019/9/9
 */
object LocationUtils {
    private const val TAG = "LocationUtils"
    fun refreshCurrentLocation(context: Context, locationListener: LocationListener) {
        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(
                context,
                Manifest.permission_group.LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            throw SecurityException("no location permission")
        }
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val cri = Criteria()
        cri.powerRequirement = Criteria.POWER_HIGH
        val bestProvider = locationManager.getBestProvider(cri, true)
        when {
            StringUtils.isNotBlank(bestProvider) -> {
                Log.i(TAG, bestProvider + "可用")
                locationManager.requestLocationUpdates(bestProvider, 5000L, 10f, locationListener)
            }
            locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) -> {
                Log.i(TAG, LocationManager.NETWORK_PROVIDER + "可用")
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000L, 10f, locationListener)
            }
            locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) -> {
                Log.i(TAG, LocationManager.GPS_PROVIDER + "可用")
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000L, 10f, locationListener)
            }
            else -> throw IllegalStateException("gps disabled,please notify user to open it")
        }
    }
}