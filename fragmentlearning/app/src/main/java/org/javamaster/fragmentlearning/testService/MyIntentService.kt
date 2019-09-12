package org.javamaster.fragmentlearning.testService

import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.util.Log

private const val ACTION_FOO = "org.javamaster.fragmentlearning.testService.action.FOO"
private const val ACTION_BAZ = "org.javamaster.fragmentlearning.testService.action.BAZ"

private const val EXTRA_PARAM1 = "org.javamaster.fragmentlearning.testService.extra.PARAM1"
private const val EXTRA_PARAM2 = "org.javamaster.fragmentlearning.testService.extra.PARAM2"

class MyIntentService : IntentService("MyIntentService") {

    override fun onHandleIntent(intent: Intent?) {
        Log.i(this::class.qualifiedName, "intent service start")
        when (intent?.action) {
            ACTION_FOO -> {
                val param1 = intent.getStringExtra(EXTRA_PARAM1)
                val param2 = intent.getStringExtra(EXTRA_PARAM2)
                handleActionFoo(param1, param2)
            }
            ACTION_BAZ -> {
                val param1 = intent.getStringExtra(EXTRA_PARAM1)
                val param2 = intent.getStringExtra(EXTRA_PARAM2)
                handleActionBaz(param1, param2)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(this::class.qualifiedName, "intent service stop")
    }


    private fun handleActionFoo(param1: String, param2: String) {
    }

    private fun handleActionBaz(param1: String, param2: String) {
    }

    companion object {

        @JvmStatic
        fun startActionFoo(context: Context, param1: String, param2: String) {
            val intent = Intent(context, MyIntentService::class.java).apply {
                action = ACTION_FOO
                putExtra(EXTRA_PARAM1, param1)
                putExtra(EXTRA_PARAM2, param2)
            }
            context.startService(intent)
        }

        @JvmStatic
        fun startActionBaz(context: Context, param1: String, param2: String) {
            val intent = Intent(context, MyIntentService::class.java).apply {
                action = ACTION_BAZ
                putExtra(EXTRA_PARAM1, param1)
                putExtra(EXTRA_PARAM2, param2)
            }
            context.startService(intent)
        }
    }
}
