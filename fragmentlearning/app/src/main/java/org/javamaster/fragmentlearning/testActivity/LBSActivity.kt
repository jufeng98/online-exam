package org.javamaster.fragmentlearning.testActivity

import android.Manifest.permission_group.LOCATION
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.baidu.location.BDLocation
import com.baidu.location.BDLocationListener
import com.baidu.location.LocationClient
import com.baidu.location.LocationClientOption
import kotlinx.android.synthetic.main.activity_lbs.*
import org.javamaster.fragmentlearning.ui.activities.BaseAppActivity


class LBSActivity : BaseAppActivity() {
    private lateinit var locationClient: LocationClient
    var locationListener: android.location.LocationListener = object : android.location.LocationListener {
        override fun onLocationChanged(location: Location?) {
            if (location != null) {
                textView16.text = "" + location.latitude + " " + location.longitude
            }
        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        }

        override fun onProviderEnabled(provider: String?) {
        }

        override fun onProviderDisabled(provider: String?) {
        }

    }

    override fun initContentView(): Int? {
        return org.javamaster.fragmentlearning.R.layout.activity_lbs
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        locationClient = LocationClient(applicationContext)
        locationClient.registerLocationListener(LocationListener())
        locationClient.locOption = initOption()
        locationClient.start()
        LocationUtil.getCurrentLocation(this@LBSActivity, locationListener)
    }


    companion object {
        fun initOption(): LocationClientOption {
            val option = LocationClientOption()
            option.locationMode = LocationClientOption.LocationMode.Hight_Accuracy
//可选，设置定位模式，默认高精度
//LocationMode.Hight_Accuracy：高精度；
//LocationMode. Battery_Saving：低功耗；
//LocationMode. Device_Sensors：仅使用设备；
            option.setCoorType("bd09ll")
//可选，设置返回经纬度坐标类型，默认GCJ02
//GCJ02：国测局坐标；
//BD09ll：百度经纬度坐标；
//BD09：百度墨卡托坐标；
//海外地区定位，无需设置坐标类型，统一返回WGS84类型坐标
            option.setScanSpan(5000)
//可选，设置发起定位请求的间隔，int类型，单位ms
//如果设置为0，则代表单次定位，即仅定位一次，默认为0
//如果设置非0，需设置1000ms以上才有效
            option.isOpenGps = true
//可选，设置是否使用gps，默认false
//使用高精度和仅用设备两种定位模式的，参数必须设置为true
            option.isLocationNotify = true
//可选，设置是否当GPS有效时按照1S/1次频率输出GPS结果，默认false
            option.setIgnoreKillProcess(false)
//可选，定位SDK内部是一个service，并放到了独立进程。
//设置是否在stop的时候杀死这个进程，默认（建议）不杀死，即setIgnoreKillProcess(true)
            option.SetIgnoreCacheException(false)
//可选，设置是否收集Crash信息，默认收集，即参数为false
            option.setWifiCacheTimeOut(5 * 60 * 1000)
//可选，V7.2版本新增能力
//如果设置了该接口，首次启动定位时，会先判断当前Wi-Fi是否超出有效期，若超出有效期，会先重新扫描Wi-Fi，然后定位
            option.setEnableSimulateGps(true)
//可选，设置是否需要过滤GPS仿真结果，默认需要，即参数为false
            option.setIsNeedAddress(true)
            return option
        }

        fun actonStart(context: Activity) {
            var intent = Intent(context, LBSActivity::class.java)
            context.startActivity(intent)
        }
    }

    inner class LocationListener : BDLocationListener {
        override fun onReceiveLocation(p0: BDLocation?) {
            runOnUiThread {
                var stringBuilder = StringBuilder()
                stringBuilder.append("经度:").append(p0?.latitude)
                stringBuilder.append(" 纬度:").append(p0?.longitude)
                stringBuilder.append(" 定位方式:")
                if (p0!!.locType == BDLocation.TypeGpsLocation) {
                    stringBuilder.append(" GPS")
                } else if (p0.locType == BDLocation.TypeNetWorkLocation) {
                    stringBuilder.append("NETWORK")
                }
                stringBuilder.append(" ").append(p0?.country)
                stringBuilder.append(" ").append(p0?.province)
                stringBuilder.append(" ").append(p0?.city)
                stringBuilder.append(" ").append(p0?.district)
                stringBuilder.append(" ").append(p0?.street)
                textView15.text = stringBuilder.toString()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        locationClient.stop()
        val locationManager = this!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationManager.removeUpdates(locationListener)
    }
}

object LocationUtil {
    private const val TAG = "LocationUtil"
    fun getCurrentLocation(context: Context, locationListener: LocationListener) {
        //如果系统版本号在23及其以上则检查权限
        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(
                context!!,
                LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(context, "no permission", Toast.LENGTH_SHORT).show()
            return
        }
        //获取LocationManager对象
        val locationManager = context!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        //配置Criteria耗电高
        val cri = Criteria()
        cri.powerRequirement = Criteria.POWER_HIGH
        // 获取可用的provider,第二个参数标识 provider是否可用.
        val bestProvider = locationManager.getBestProvider(cri, true)

        if (!TextUtils.isEmpty(bestProvider)) {
            Log.e(TAG, "bestProvider = " + bestProvider + "可用")
            locationManager.requestLocationUpdates(bestProvider, 5000L, 10f, locationListener)
        } else if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            Log.e(TAG, LocationManager.NETWORK_PROVIDER + "可用")
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000L, 10f, locationListener)
        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Log.e(TAG, LocationManager.GPS_PROVIDER + "可用")
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000L, 10f, locationListener)
        } else {
            //定位不可用，提示打开GPS
            Log.e(TAG, "定位不可用，提示打开GPS")
        }
    }
}
