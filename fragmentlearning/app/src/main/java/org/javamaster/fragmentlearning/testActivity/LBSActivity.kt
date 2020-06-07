package org.javamaster.fragmentlearning.testActivity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import com.baidu.location.BDLocation
import com.baidu.location.BDLocationListener
import com.baidu.location.LocationClient
import com.baidu.location.LocationClientOption
import kotlinx.android.synthetic.main.activity_lbs.*
import org.javamaster.fragmentlearning.ui.activities.BaseAppActivity
import org.javamaster.fragmentlearning.utils.LocationUtils


class LBSActivity : BaseAppActivity() {
    private lateinit var locationClient: LocationClient
    var refreshTimes: Int = 1
    var refreshTimes1: Int = 1
    private var locationListener: android.location.LocationListener = object : android.location.LocationListener {
        override fun onLocationChanged(location: Location?) {
            if (location != null) {
                textView16.text = StringBuilder()
                    .append("来自locationManager:").append("\n")
                    .append("经度:${location.longitude}").append("\n")
                    .append("纬度:${location.latitude}").append("\n")
                    .append("刷新次数:${refreshTimes++}").toString()
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
        LocationUtils.refreshCurrentLocation(this@LBSActivity, locationListener)
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
            val intent = Intent(context, LBSActivity::class.java)
            context.startActivity(intent)
        }
    }

    inner class LocationListener : BDLocationListener {
        override fun onReceiveLocation(bdLocation: BDLocation) {
            runOnUiThread {
                val stringBuilder = StringBuilder()
                stringBuilder.append("来自百度地图:").append("\n")
                stringBuilder.append("经度:").append(bdLocation.longitude).append("\n")
                stringBuilder.append("纬度:").append(bdLocation.latitude).append("\n")
                stringBuilder.append("定位方式:")
                when (bdLocation.locType) {
                    BDLocation.TypeGpsLocation -> stringBuilder.append("GPS").append("\n")
                    BDLocation.TypeNetWorkLocation -> stringBuilder.append("NETWORK").append("\n")
                    else -> stringBuilder.append("未知(${bdLocation.locType})").append("\n")
                }
                stringBuilder.append("具体地址:").append("\n")
                stringBuilder.append(" ").append(bdLocation.country)
                stringBuilder.append(" ").append(bdLocation.province)
                stringBuilder.append(" ").append(bdLocation.city)
                stringBuilder.append(" ").append(bdLocation.district)
                stringBuilder.append(" ").append(bdLocation.street).append("\n")
                stringBuilder.append("刷新次数:${refreshTimes1++}")
                textView15.text = stringBuilder.toString()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        locationClient.stop()
        val locationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationManager.removeUpdates(locationListener)
    }
}
