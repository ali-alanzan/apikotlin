package com.example.myapplication

import android.R.attr.data
import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.edmodo.cropper.CropImageView
import java.io.*
import java.util.*


class SelectFragment : Fragment() {
    public lateinit var photoName :EditText
    public lateinit var imageView:CropImageView
    public lateinit var imageUri:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_select, container, false)
         photoName = view.findViewById<EditText>(R.id.text1)
        imageView = view.findViewById<CropImageView>(R.id.imageview)
        var selectImage = view.findViewById<Button>(R.id.button4)
            selectImage.setOnClickListener ( View.OnClickListener{
            var i = Intent()
            i.type = "*/*"
            i.action = Intent.ACTION_GET_CONTENT

            startForResult.launch(i)
            })

        return view
    }
    var startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {

            if (it.resultCode == Activity.RESULT_OK) {
                val selectedImage: Uri? = it.data?.data


                imageUri = it.data?.data.toString()
               // val mainActivity=MainActivity().formData(imageUri);

               // System.out.println(imageUri)
               // val file= MediaStore.Files(it.data.data)

               // System.out.println(imageUri)
                val image: Bitmap = getBitmap(requireContext(), null, it.data?.data.toString(), ::UriToBitmap)
                val mainActivity= bitmapToFile(image,"test")?.let { it1 -> MainActivity().formData(it1, selectedImage) };

                imageView.setImageBitmap(image)

               // val mainActivity=MainActivity().formData(file);


            }
        }


    fun bitmapToFile(bitmap: Bitmap, fileNameToSave: String): File? { // File name like "image.png"
        //create a file to write bitmap data
        var file: File? = null
        return try {
            file = File(Environment.getExternalStorageDirectory().toString() + File.separator + fileNameToSave)
            file.createNewFile()

            //Convert bitmap to byte array
            val bos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos) // YOU can also save it in JPEG
            val bitmapdata = bos.toByteArray()

            //write the bytes in file
            val fos = FileOutputStream(file)
            fos.write(bitmapdata)
            fos.flush()
            fos.close()
            file
        } catch (e: Exception) {
            e.printStackTrace()
            file // it will return null
        }
    }


}


