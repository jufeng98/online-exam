package org.javamaster.fragmentlearning.testActivity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.baidu.location.LocationClient
import com.baidu.mapapi.SDKInitializer
import com.baidu.mapapi.map.MapStatusUpdateFactory
import com.baidu.mapapi.map.MyLocationData
import com.baidu.mapapi.model.LatLng
import kotlinx.android.synthetic.main.activity_baidu_map.*
import org.javamaster.fragmentlearning.R

class BaiduMapActivity : AppCompatActivity() {

    private lateinit var client: LocationClient
    private var firstLocate = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        client = LocationClient(applicationContext)
        SDKInitializer.initialize(applicationContext)
        setContentView(R.layout.activity_baidu_map)
        client.locOption = LBSActivity.initOption()
        client.registerLocationListener {
            if (firstLocate) {
                var ll = LatLng(it.latitude, it.longitude)
                var update = MapStatusUpdateFactory.newLatLng(ll)
                map_view.map.animateMapStatus(update)
                map_view.map.isMyLocationEnabled = true
                firstLocate = false
            }
            var myLocationData = MyLocationData.Builder().latitude(it.latitude).longitude(it.longitude).build()
            map_view.map.setMyLocationData(myLocationData)
        }
        client.start()
        var update = MapStatusUpdateFactory.zoomTo(12.5F)
        map_view.map.animateMapStatus(update)

    }

    override fun onResume() {
        super.onResume()
        map_view.onResume()
    }

    override fun onPause() {
        super.onPause()
        map_view.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        client.stop()
        map_view.map.isMyLocationEnabled = false
        map_view.onDestroy()

    }

    companion object {
        fun actonStart(context: Activity) {
            var intent = Intent(context, BaiduMapActivity::class.java)
            context.startActivity(intent)
        }
    }
}
