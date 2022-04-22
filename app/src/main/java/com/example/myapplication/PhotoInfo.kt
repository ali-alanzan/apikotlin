package com.example.myapplication

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.VectorDrawable
import android.net.Uri
import android.provider.MediaStore
import androidx.core.content.ContextCompat
import java.io.Serializable
import kotlin.random.Random

data class PhotoInfo(var name: String,var url :String,var x: Int, var y: Int, var w: Int, var h: Int,var position: Int=-1):Serializable {}
    object PhotoInfoTester {

        fun createRandomphotos(amount: Int): ArrayList<PhotoInfo> {

            val generatedList: ArrayList<PhotoInfo> = ArrayList<PhotoInfo>()

            repeat(amount) {
                var randomName: String = Random.generateRandomString(5..10)
                randomName =
                    "" + randomName[0].toUpperCase() + randomName.subSequence(1, randomName.length)


                generatedList.add(
                    PhotoInfo(
                        randomName,
                        "",
                        0,0,0,0
                        )
                )
            }

            return generatedList
        }

    }

    fun Random.generateRandomString(intRange: IntRange): String {
        var randomString: String = ""
        repeat(intRange.random()) { randomString += ('a'..'z').random().toString() }
        return randomString

    }
    fun VectorDrawableToBitmap(context: Context, id: Int?, uri: String?) : Bitmap {
        val drawable = (ContextCompat.getDrawable(context!!, id!!) as VectorDrawable)
        val image = Bitmap.createBitmap(
        drawable.getIntrinsicWidth(),
        drawable.getIntrinsicHeight(),
        Bitmap.Config.ARGB_8888
    )
         val canvas = Canvas(image)
         drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight())
         drawable.draw(canvas)

    return image
        }

    fun UriToBitmap(context: Context, id: Int?, uri: String?): Bitmap {
            val image: Bitmap = MediaStore.Images.Media.getBitmap(context!!.contentResolver, Uri.parse(uri))
             return image
        }

    fun getBitmap(context: Context, id: Int?, uri: String?, decoder: (Context, Int?, String?) -> Bitmap): Bitmap {
            return decoder(context, id, uri)
        }

object Globals {
    val TAG = "AndroidLifeCycle"
}





