package com.example.myapplication

import android.content.ComponentName
import android.content.Context
import android.content.ServiceConnection
import android.net.Uri
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream


class MainActivity : AppCompatActivity(), UploadRequestBody.UploadCallBack {
    private lateinit var mService: HelloService
    private var mBound: Boolean = false
    private val connection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            val binder = service as HelloService.HelloServiceBinder
            mService = binder.getService()
            mBound = true
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            mBound = false
        }
    }
    private lateinit var fragmentManager: FragmentManager
    private var photoInfo = ArrayList<PhotoInfo>()
    lateinit var imagelink:String;
    lateinit var imagesearch:String;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        photoInfo = PhotoInfoTester.createRandomphotos(10)
        Log.i(Globals.TAG, "Activity 1 onCreate")
        Toast.makeText(this, "Activity onCreate", Toast.LENGTH_SHORT).show()


    }

    fun switchFragment(v: View) {
        Toast.makeText(
            this,
            "Activity switchFragment. Tag" + v.getTag().toString(),
            Toast.LENGTH_SHORT
        ).show()

        fragmentManager = supportFragmentManager

        if (Integer.parseInt(v.getTag().toString()) == 1) {
            fragmentManager
                .beginTransaction()
                .replace(
                    R.id.myframe,
                    SelectFragment(),
                    "Fragment1"
                )
                .commit()
         //   formData()

        }
        if (Integer.parseInt(v.getTag().toString()) == 2) {
            fragmentManager
                .beginTransaction()
                .replace(
                    R.id.myframe,
                    resoultFragment(photoInfo),
                    "Fragment2"
                )
                .commit()
//            getMethod()
        }
        if (Integer.parseInt(v.getTag().toString()) == 3) {
            fragmentManager
                .beginTransaction()
                .replace(
                    R.id.myframe,
                    SavedFragment(),
                    "Fragment3"
                )
                .commit()
        }

    }

    fun submit(view: View) {
           var nameViewText = (fragmentManager.findFragmentByTag("Fragment1") as SelectFragment).photoName.text.toString()
           var imageUri = (fragmentManager.findFragmentByTag("Fragment1") as SelectFragment).imageUri
           var imageRect = (fragmentManager.findFragmentByTag("Fragment1") as SelectFragment).imageView.actualCropRect
            val newPhoto : PhotoInfo = PhotoInfo(nameViewText,imageUri,imageRect.left.toInt(),imageRect.top.toInt()
                      ,imageRect.width().toInt(),imageRect.height().toInt())
                photoInfo.add(newPhoto)
        Toast.makeText(this, "Added New Student", Toast.LENGTH_SHORT).show()
    }


    private fun uploadImage (file:File, selectedImage: Uri?) {
        if( selectedImage == null ) {
            Toast.makeText(this, "Select an image first", Toast.LENGTH_SHORT).show()
            return
        }

//        val parcelFileDescriptor =
//            contentResolver.openFileDescriptor(selectedImage!!, "r", null)) ?: return
//        val inputStream = FileInputStream(parcelFileDescriptor!!.fileDescriptor)
//        val file = File(cacheDir, contentResolver.getFileName(selectedImage!!))
//        val outputStream = FileOutputStream(file)
//        inputStream.copyTo(outputStream)
//

        val body = UploadRequestBody(file,  this)

        MyAPI().uploadImage(
            MultipartBody.Part.createFormData("image", file.name, body),
        ).enqueue(object: Callback<String>{
            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e("RETROFIT_ERROR", t.message.toString())
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                imagesearch= response.body().toString()
                Log.e("RETROFIT_SUCCESS", imagesearch)

            }
        })


    }


    /////////////////////////////the full mode//////////////////////

    fun formData(file:File, selectedImage: Uri?) {
        uploadImage(file, selectedImage)
        return
        Thread(Runnable {
            // Create Retrofit
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api-edu.gtl.ai")
                .build()

            // Create Service
            val service = retrofit.create(APIService::class.java)

            // List of all MIME Types you can upload: https://www.freeformatter.com/mime-types-list.html

            // Get file from assets folder
            //  val file =getFileFromAssets(this, "car.png")



            var requestFile = file.asRequestBody("image/png".toMediaTypeOrNull())

            var body = MultipartBody.Part.createFormData("image", file.name, requestFile)



            CoroutineScope(Dispatchers.IO).launch {

                // Do the POST request and get response


                val response = service.uploadEmployeeData(body)





                //some method here

                // val response = service.uploadEmployeeData(body)

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {

                        imagelink = response.body()?.string().toString();


                        // Convert raw JSON to pretty JSON using GSON library

                        /* val gson = GsonBuilder().setPrettyPrinting().create()
                           val prettyJson = gson.toJson(
                               JsonParser.parseString(
                                   response.body()
                                       ?.string() // About this thread blocking annotation : https://github.com/square/retrofit/issues/3255
                               )
                           )
       */
                        Log.d("Pretty Printed JSON :", imagelink)


                    } else {

                        Log.e("RETROFIT_ERROR", response.code().toString())

                    }
                }



            }

        }).start()



    }

    override fun onProgressUpdate(percentage: Int) {

    }

    fun getFileFromAssets(context: Context, fileName: String): File =
        File(context.cacheDir, fileName)
            .also {
                if (!it.exists()) {
                    it.outputStream().use { cache ->
                        context.assets.open(fileName).use { inputStream ->
                            inputStream.copyTo(cache)
                        }
                    }
                }
            }

    fun getMethod() {
        Thread(Runnable {

        // Create Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("http://api-edu.gtl.ai")
            .build()

        // Create Service
        val service = retrofit.create(APIService::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            /*
             * For @Query: You need to replace the following line with val response = service.getEmployees(2)
             * For @Path: You need to replace the following line with val response = service.getEmployee(53)
             */

            // Do the GET request and get response
            val response = service.getFromBing(imagelink)
            //val response = service.getFromGoogle(imagelink)
           // val response = service.getFromTineye(imagelink)

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {

                    // Convert raw JSON to pretty JSON using GSON library
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(
                        JsonParser.parseString(
                            response.body()
                                ?.string() // About this thread blocking annotation : https://github.com/square/retrofit/issues/3255
                        )
                    )

                    Log.d("Pretty Printed JSON :", prettyJson)

                } else {

                    Log.e("RETROFIT_ERROR", response.code().toString())

                }
            }
        }
    }).start()




    }



    override fun onStart() {
        super.onStart()
        Log.i(Globals.TAG, "Activity 1 onStart")
        Toast.makeText(this, "Activity onStart", Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        Log.i(Globals.TAG, "Activity 1 onResume")
        Toast.makeText(this, "Activity onResume", Toast.LENGTH_SHORT).show()
    }

    override fun onPause() {
        super.onPause()
        Log.i(Globals.TAG, "Activity 1 onPause")
        Toast.makeText(this, "Activity onPause", Toast.LENGTH_SHORT).show()
    }

    override fun onStop() {
        super.onStop()
        Log.i(Globals.TAG, "Activity 1 onStop")
        Toast.makeText(this, "Activity onStop", Toast.LENGTH_SHORT).show()
    }

    override fun onRestart() {
        super.onRestart()
        Log.i(Globals.TAG, "Activity 1 onRestart")
        Toast.makeText(this, "Activity onRestart", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(Globals.TAG, "Activity 1 onDestroy")
        Toast.makeText(this, "Activity onDestroy", Toast.LENGTH_SHORT).show()
    }








}



