package org.javamaster.fragmentlearning.testCompleteDown

interface DownloadListener {

    fun onProgress(progress: Int)

    fun onSuccess()

    fun onFailed()

    fun onPaused()

    fun onCanceled()

}
