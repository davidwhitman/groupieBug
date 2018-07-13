package com.example.dwhitman.playground

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.util.Log
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker

/**
 * TODO Add class description.
 */
class ActivityThing : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val workManager = WorkManager.getInstance()
        val workName = "work name!"

        workManager?.cancelUniqueWork(workName)
        workManager?.pruneWork()
        workManager?.getStatusesForUniqueWork(workName)
                ?.observe(this@ActivityThing, Observer { results ->
                    Log.d("WorkManagerTesting", results?.joinToString(separator = "\n"))
                })

        for (i in 1..200) {
            workManager
                    ?.enqueue(OneTimeWorkRequestBuilder<SuccessWorker>().build())
        }
    }

    class SuccessWorker : Worker() {
        override fun doWork(): Result {
            Log.v("WorkManagerTesting", "Thread: ${Thread.currentThread().id}")
            this.runAttemptCount
            return Result.SUCCESS
        }
    }

    class FailureWorker : Worker() {
        override fun doWork(): Result {
            Thread.sleep(500)
            return Result.FAILURE
        }
    }
}