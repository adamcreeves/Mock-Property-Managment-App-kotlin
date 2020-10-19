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
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.mockpropertymanagmentapp.R
import com.example.mockpropertymanagmentapp.data.listeners.NewPropertyListener
import com.example.mockpropertymanagmentapp.data.models.*
import com.example.mockpropertymanagmentapp.data.network.MyApi
import com.example.mockpropertymanagmentapp.helpers.SessionManager
import com.example.mockpropertymanagmentapp.helpers.toastShort
import com.example.mockpropertymanagmentapp.data.listeners.PropertiesListener
import com.example.mockpropertymanagmentapp.data.viewmodels.PropertiesViewModel
import com.example.mockpropertymanagmentapp.databinding.ActivityAddNewPropertyBinding
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
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.property_bottom_sheet.view.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File

class AddNewPropertyActivity : AppCompatActivity(), NewPropertyListener {
    lateinit var sessionManager: SessionManager
    lateinit var bottomSheetDialog: BottomSheetDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_add_new_property)
        val binding: ActivityAddNewPropertyBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_add_new_property)
        val addNewPropertyViewModel =
            ViewModelProviders.of(this).get(PropertiesViewModel::class.java)
        binding.addNewPropertyViewModel = addNewPropertyViewModel
        binding.addNewPropertyViewModel!!.newPropertyListener = this
        sessionManager = SessionManager(this)
        init()
    }

    private fun init() {
        toolbar()
        bottomSheetDialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.property_bottom_sheet, null)
        bottomSheetDialog.setContentView(view)
        button_add_new_property_add_photo.setOnClickListener {
            bottomSheetDialog.show()
        }
        view.button_to_camera.setOnClickListener {
            requestCameraPermission()
        }
        view.button_to_gallery.setOnClickListener {
            requestGalleryPermissions()
        }
//        button_add_new_property_save.setOnClickListener {
//            var address = edit_text_address.text.toString()
//            var city = edit_text_city.text.toString()
//            var state = edit_text_state.text.toString()
//            var country = edit_text_country.text.toString()
//            var purchasePrice = edit_text_price.text.toString()
//            var imageUrl = sessionManager.getImageUrl()
//            MyApi().addNewProperty(
//                Prop(
//                    address = address,
//                    city = city,
//                    state = state,
//                    country = country,
//                    purchasePrice = purchasePrice,
//                    image = imageUrl
//                )
//            )
//                .enqueue(object : Callback<AddPropertyResponse> {
//                    override fun onResponse(
//                        call: Call<AddPropertyResponse>,
//                        response: Response<AddPropertyResponse>
//                    ) {
//                        if (response.isSuccessful) {
//                            Toast.makeText(
//                                applicationContext,
//                                "Property Added Successfully",
//                                Toast.LENGTH_SHORT
//                            ).show()
//                            finish()
//                        }
//                    }
//
//                    override fun onFailure(call: Call<AddPropertyResponse>, t: Throwable) {
//                        Toast.makeText(applicationContext, "Property Not Added", Toast.LENGTH_SHORT)
//                            .show()
//                    }
//
//                })
//        }
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
        if (requestCode == 203) {
            var bmp = data?.extras!!.get("data") as Bitmap
            var uri = getImageUri(this, bmp)
            var imagePath = getRealPathFromURI(uri)
            postNewImage(imagePath!!)
            bottomSheetDialog.dismiss()
        }
        if (requestCode == 202) {
            var imagePath = getRealPathFromURI(data?.data)
            postNewImage(imagePath!!)
            bottomSheetDialog.dismiss()
        }
    }

    private fun postNewImage(path: String) {
        var file = File(path)
        var requestFile = RequestBody.create(MediaType.parse("image/jpeg"), file)
        var body = MultipartBody.Part.createFormData("image", file.name, requestFile)
        MyApi().postNewImage(body)
            .enqueue(object : Callback<UploadPictureResponse> {
                override fun onResponse(
                    call: Call<UploadPictureResponse>,
                    response: Response<UploadPictureResponse>
                ) {
                    if (response.isSuccessful) {
                        Log.d("bbb", response.body()!!.data.location)
                        sessionManager.saveImageUrl(response.body()!!.data.location)
                    }
                }

                override fun onFailure(call: Call<UploadPictureResponse>, t: Throwable) {
                }
            })
    }

    private fun requestCameraPermission() {
        Dexter.withContext(this)
            .withPermission(Manifest.permission.CAMERA)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                    Toast.makeText(applicationContext, "Camera Access Granted", Toast.LENGTH_SHORT)
                        .show()
                    openTheCamera()
                }

                override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                    Toast.makeText(applicationContext, "Camera Access Denied", Toast.LENGTH_SHORT)
                        .show()
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

    private fun toolbar() {
        var toolbar = toolbar
        toolbar.title = "Add New Property"
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                startActivity(Intent(this, PropertyActivity::class.java))
            }
        }
        return true
    }

    override fun onHasStarted() {
        this.toastShort("New property is being submitted")
    }

    override fun onSuccessfulAdd(response: LiveData<AddPropertyResponse>) {
        response.observe(this, Observer {
            this.toastShort("New property has been successfully added")
            startActivity(Intent(this, PropertyActivity::class.java))
                finish()
        })
    }

    override fun onFailedAdd(message: String) {
        this.toastShort(message)
    }
}