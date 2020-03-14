package org.javamaster.fragmentlearning.testActivity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_baidu_map.*
import com.amap.api.maps.AMapUtils
import com.amap.api.maps.model.LatLng

class GaodeMapActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        val latLng1 = LatLng(0.0, 0.0)
        var latLng2 = LatLng(0.0, 0.0)
        val distance = AMapUtils.calculateLineDistance(latLng1, latLng2)

    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        map_view.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()

    }

    companion object {
        fun actonStart(context: Activity) {
            val intent = Intent(context, GaodeMapActivity::class.java)
            context.startActivity(intent)
        }
    }
}
