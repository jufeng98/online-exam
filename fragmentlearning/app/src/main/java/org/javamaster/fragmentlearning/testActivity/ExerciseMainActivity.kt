package org.javamaster.fragmentlearning.testActivity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_exercise_main.*
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.testAdapter.ExerciseArrayAdapter
import org.javamaster.fragmentlearning.testCompleteDown.DownloadActivity
import org.javamaster.fragmentlearning.ui.activities.BaseAppActivity

/**
 * @author yudong
 * @date 2019/8/28
 */
class ExerciseMainActivity : BaseAppActivity() {
    var list: MutableList<Pair<String, Class<out Any>>> = mutableListOf()
    override fun initContentView(): Int? {
        return R.layout.activity_exercise_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = "Api Demos"
        initList()
        list_view.adapter = ExerciseArrayAdapter(this, R.layout.activity_exercise_list_view, list)
        list_view.setOnItemClickListener { _, view, _, _ ->
            var viewHolder = view.tag as ExerciseArrayAdapter.ViewHolder
            var intent = Intent(this, viewHolder.clz)
            this@ExerciseMainActivity.startActivity(intent)
        }
    }

    private fun initList() {
        list.add(Pair("提示框", AlertDialogActivity::class.java))
        list.add(Pair("动画", AnimationActivity::class.java))
        list.add(Pair("更多动画", AnimationMoreActivity::class.java))
        list.add(Pair("聊天页面", ChatActivity::class.java))
        list.add(Pair("Content Resolver", ContentResolverActivity::class.java))
        list.add(Pair("fragment", FragmentActivity::class.java))
        list.add(Pair("HttpConnection的使用", HttpConnectionActivity::class.java))
        list.add(Pair("ListView的使用", ListViewActivity::class.java))
        list.add(Pair("ListView的深入使用", ListViewCustomerActivity::class.java))
        list.add(Pair("RecyclerView的使用", RecyclerViewActivity::class.java))
        list.add(Pair("SQLite数据库的使用", SQLiteActivity::class.java))
        list.add(Pair("LitePal的使用", LitePalActivity::class.java))
        list.add(Pair("下载功能的实现", DownloadActivity::class.java))
        list.add(Pair("WebView的使用", WebViewActivity::class.java))
        list.add(Pair("选取或拍摄相片", TakeAndPickPhotoActivity::class.java))
        list.add(Pair("Service的使用", ServiceActivity::class.java))
        list.add(Pair("本地广播", LocalBroadcastActivity::class.java))
        list.add(Pair("定时任务", ScheduledActivity::class.java))
        list.add(Pair("打电话", MakeCallActivity::class.java))
        list.add(Pair("存储路径", StoragePathActivity::class.java))
        list.add(Pair("音频播放器", MediaPlayerActivity::class.java))
        list.add(Pair("视频播放器", VideoPlayerActivity::class.java))
        list.add(Pair("tool_bar菜单", MenuActivity::class.java))
        list.add(Pair("MaterialDesign", MaterialDesignActivity::class.java))
        list.add(Pair("Message和Handler以及AsyncTask", MessageHandlerActivity::class.java))
        list.add(Pair("发送广播", MyBroadcastActivity::class.java))
        list.add(Pair("动态注册广播接收器", NetWorkChangeReceiverActivity::class.java))
        list.add(Pair("横竖屏切换不同布局", NewsActivity::class.java))
        list.add(Pair("保存和载入数据", SaveAndLoadActivity::class.java))
        list.add(Pair("使用Fragment保存和载入数据", SendActivity::class.java))
        list.add(Pair("发短信", SmsActivity::class.java))
        list.add(Pair("状态栏通知", NotificationActivity::class.java))
        list.add(Pair("位置服务(LBS)", LBSActivity::class.java))
        list.add(Pair("百度地图", BaiduMapActivity::class.java))
    }

    companion object {
        fun actionStart(context: Activity) {
            var intent = Intent(context, ExerciseMainActivity::class.java)
            context.startActivity(intent)
        }
    }
}
