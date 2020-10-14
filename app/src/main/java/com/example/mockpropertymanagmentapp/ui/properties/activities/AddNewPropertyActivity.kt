package com.example.mockpropertymanagmentapp.ui.properties.activities

import android.Manifest
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.mockpropertymanagmentapp.R
import com.example.mockpropertymanagmentapp.data.repositories.UserRepository
import com.example.mockpropertymanagmentapp.databinding.ActivityAddNewPropertyBinding
import com.example.mockpropertymanagmentapp.databinding.ActivityLoginBinding
import com.example.mockpropertymanagmentapp.helpers.toastShort
import com.example.mockpropertymanagmentapp.ui.home.HomeActivity
import com.example.mockpropertymanagmentapp.ui.properties.PropertiesListener
import com.example.mockpropertymanagmentapp.ui.properties.PropertiesViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.activity_add_new_property.*
import kotlinx.android.synthetic.main.activity_property.*
import kotlinx.android.synthetic.main.property_bottom_sheet.view.*
import java.io.ByteArrayOutputStream

class AddNewPropertyActivity : AppCompatActivity(), PropertiesListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityAddNewPropertyBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_new_property)
        val viewModel = ViewModelProviders.of(this).get(PropertiesViewModel::class.java)
        binding.viewModel = viewModel
        viewModel.propertiesListener = this
        init()
    }

    private fun init() {
        val bottomSheetDialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.property_bottom_sheet, null)
        bottomSheetDialog.setContentView(view)
        button_add_new_property_add_photo.setOnClickListener{
            bottomSheetDialog.show()
        }
        view.button_to_camera.setOnClickListener{
            requestCameraPermission()
        }
        view.button_to_gallery.setOnClickListener{
            requestGalleryPermissions()
        }
    }


    private fun requestGalleryPermissions() {
        Dexter.withContext(this)
            .withPermissions(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    if (report!!.areAllPermissionsGranted()) {
                        openTheGallery()
                        Toast.makeText(
                            applicationContext,
                            "Gallery Access Granted",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    if (report.isAnyPermissionPermanentlyDenied) {
                        Toast.makeText(
                            applicationContext,
                            "Gallery Permission Denied",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    token?.continuePermissionRequest()
                }

            }).onSameThread()
            .check()
    }

    private fun openTheGallery() {
        var intent = Intent()
        intent.action = Intent.ACTION_PICK
        intent.type = "image/*"
        startActivityForResult(intent, 202)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 203) {
            var bmp = data?.extras!!.get("data") as Bitmap
            var uri = getImageUri(this, bmp)
            var imagePath = getRealPathFromURI(uri)
            UserRepository().postNewImage(imagePath!!)
        }
    }

    private fun requestCameraPermission() {
        Dexter.withContext(this)
            .withPermission(Manifest.permission.CAMERA)
            .withListener(object: PermissionListener {
                override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                    Toast.makeText(applicationContext, "Camera Access Granted", Toast.LENGTH_SHORT).show()
                    openTheCamera()
                }

                override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                    Toast.makeText(applicationContext, "Camera Access Denied", Toast.LENGTH_SHORT).show()
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: PermissionRequest?,
                    token: PermissionToken?
                ) {
                    token?.continuePermissionRequest()
                }

            }).check()
    }
    private fun openTheCamera() {
        var intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, 203)
    }

    override fun onStarted() {
        this.toastShort("Adding new property")
    }

    override fun onSuccessful(response: LiveData<String>) {
        response.observe(this, Observer {
            this.toastShort("Added new property successfully")
            Log.d("abc", response.value.toString())
            finish()
        })
    }

    override fun onFailure(message: String) {
        this.toastShort(message)
    }


    // get URI from bitmap
    fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path: String =
            MediaStore.Images.Media.insertImage(inContext.contentResolver, inImage, "Title", null)
        return Uri.parse(path)
    }

    // get actual path
    fun getRealPathFromURI(uri: Uri?): String? {
        val cursor: Cursor? = contentResolver.query(uri!!, null, null, null, null)
        cursor!!.moveToFirst()
        val idx: Int = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
        return cursor.getString(idx)
    }

}