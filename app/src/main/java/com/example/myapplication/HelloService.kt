package com.example.myapplication

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.*
import java.net.URL

class HelloService : Service() {
    private var startMode: Int = 0             // indicates how to behave if the service is killed
    private var binder: IBinder? = HelloServiceBinder()        // interface for clients that bind
    private var allowRebind: Boolean = false   // indicates whether onRebind should be used

    inner class HelloServiceBinder : Binder() {
        fun getService(): HelloService = this@HelloService
    }


   /* suspend fun downloadFile(): String = withContext(Dispatchers.IO) {
        URL("http://api-edu.gtl.ai/\n" +
                "api/v1/imagesearch\n" +
                "/upload").readText()
    }
    override fun onCreate() {

        GlobalScope.launch(Dispatchers.Main) { // launches coroutine in main thread
            var result = downloadFile()
            Log.i(Globals.TAG, result)
        }
    }*/

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Toast.makeText(this, "service on start", Toast.LENGTH_SHORT).show()

        Log.i(Globals.TAG, "service onStart")
        return startMode
    }

    override fun onBind(intent: Intent): IBinder? {
        Toast.makeText(this, "service onBind", Toast.LENGTH_SHORT).show()

        Log.i(Globals.TAG, "service onBind")
        return binder
    }

    override fun onUnbind(intent: Intent): Boolean {
        Toast.makeText(this, "service onUnbind", Toast.LENGTH_SHORT).show()

        Log.i(Globals.TAG, "service onUnbind")
        return allowRebind
    }

    override fun onRebind(intent: Intent) {

        Toast.makeText(this, "service onRebind", Toast.LENGTH_SHORT).show()

        Log.i(Globals.TAG, "service onRebind")
    }

    override fun onDestroy() {
        Toast.makeText(this, "service onDestroy", Toast.LENGTH_SHORT).show()

        Log.i(Globals.TAG, "service onDestroy")
    }
}