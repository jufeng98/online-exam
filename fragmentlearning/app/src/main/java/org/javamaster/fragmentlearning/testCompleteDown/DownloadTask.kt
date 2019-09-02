package org.javamaster.fragmentlearning.testCompleteDown

import android.os.AsyncTask
import android.os.Environment
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.io.RandomAccessFile
import java.util.concurrent.TimeUnit

/**
 * @author yudong
 * @date 2019/8/31
 */
class DownloadTask(private val listener: DownloadListener, var cancel: Boolean, var pause: Boolean) :
    AsyncTask<String, Int, Int>() {

    companion object {
        const val FAIL = 0
        const val SUCCESS = 1
        const val CANCEL = 2
        const val PAUSE = 3
    }

    override fun doInBackground(vararg params: String?): Int {
        var downloadUrl = params[0]!!
        var path = Environment.getExternalStorageDirectory()
        var index = downloadUrl.lastIndexOf("/")
        var fileName = downloadUrl.substring(index + 1)
        var downloadLength = 0L
        var file = File(path, fileName)
        if (file.exists()) {
            downloadLength = file.length()
        }
        var contentLength = getContentLength(downloadUrl)
        if (contentLength == 0L) {
            return FAIL
        } else if (contentLength == downloadLength) {
            return SUCCESS
        }
        var randomFile = RandomAccessFile(file, "rw")
        if (downloadLength > 0) {
            randomFile.seek(downloadLength)
        }
        var client = OkHttpClient()
        var request = Request.Builder().header("RANGE", "bytes=$downloadLength-").url(downloadUrl).build()
        var response = client.newCall(request).execute()
        var inputStream = response.body!!.byteStream()
        var bytes = ByteArray(10240)
        try {
            var total = 0
            var len = inputStream.read(bytes)
            while (len != -1) {
                TimeUnit.MILLISECONDS.sleep(100)
                when {
                    cancel -> return CANCEL
                    pause -> return PAUSE
                    else -> {
                        total += len
                        randomFile.write(bytes, 0, len)
                        len = inputStream.read(bytes)
                        var progress = ((total + downloadLength) * 100 / contentLength).toInt()
                        publishProgress(progress)
                    }
                }
            }
        } finally {
            response.body!!.close()
            inputStream.close()
            randomFile.close()
            if (cancel) {
                file.delete()
            }
        }
        return SUCCESS
    }

    override fun onProgressUpdate(vararg values: Int?) {
        var progress = values[0]!!
        listener.onProgress(progress)
    }

    override fun onPostExecute(result: Int?) {
        var type = result!!
        when (type) {
            FAIL -> listener.onFailed()
            SUCCESS -> listener.onSuccess()
            CANCEL -> listener.onCanceled()
            PAUSE -> listener.onPaused()
        }
    }

    private fun getContentLength(url: String): Long {
        var client = OkHttpClient()
        var request = Request.Builder().url(url).build()
        var response = client.newCall(request).execute()
        if (response.body != null) {
            var length = response.body!!.contentLength()
            response.body!!.close()
            return length
        }
        return 0

    }
}