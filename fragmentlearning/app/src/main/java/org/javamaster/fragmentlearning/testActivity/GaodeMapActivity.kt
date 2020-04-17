package org.javamaster.fragmentlearning.testActivity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_baidu_map.*
import com.amap.api.maps.AMapUtils
import com.amap.api.maps.CoordinateConverter
import com.amap.api.maps.model.LatLng

class GaodeMapActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        val latLng1 = LatLng(0.0, 0.0)
        var latLng2 = LatLng(0.0, 0.0)
        val distance = AMapUtils.calculateLineDistance(latLng1, latLng2)
        Log.i(this::class.qualifiedName, distance.toString())
        val converter = CoordinateConverter(applicationContext)
        // CoordType.GPS 待转换坐标类型
        converter.from(CoordinateConverter.CoordType.BAIDU)
        // sourceLatLng待转换坐标点 LatLng类型
        val sourceLatLng = LatLng(113.25, 21.23)
        converter.coord(sourceLatLng)
        // 执行转换操作
        val desLatLng = converter.convert()
        Log.i(this::class.qualifiedName, desLatLng.toString())
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
