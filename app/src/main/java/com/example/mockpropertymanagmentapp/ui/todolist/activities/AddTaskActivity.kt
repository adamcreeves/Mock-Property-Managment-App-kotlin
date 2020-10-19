package com.example.mockpropertymanagmentapp.ui.todolist.activities

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
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.mockpropertymanagmentapp.R
import com.example.mockpropertymanagmentapp.data.listeners.ToDoListener
import com.example.mockpropertymanagmentapp.data.models.Task
import com.example.mockpropertymanagmentapp.data.models.UploadPictureResponse
import com.example.mockpropertymanagmentapp.data.network.MyApi
import com.example.mockpropertymanagmentapp.data.viewmodels.ToDoViewModel
import com.example.mockpropertymanagmentapp.databinding.ActivityAddTaskBinding
import com.example.mockpropertymanagmentapp.helpers.SessionManager
import com.example.mockpropertymanagmentapp.helpers.toastShort
import com.example.mockpropertymanagmentapp.ui.home.HomeActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.database.FirebaseDatabase
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.activity_add_task.*
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


class AddTaskActivity : AppCompatActivity(), ToDoListener {
    lateinit var sessionManager: SessionManager
    lateinit var bottomSheetDialog: BottomSheetDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityAddTaskBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_task)
        val viewModel = ViewModelProviders.of(this).get(ToDoViewModel::class.java)
        binding.viewModel = viewModel
        binding.viewModel!!.todoListener = this
        sessionManager = SessionManager(this)
        init()
    }

    private fun init() {
        toolbar()
        bottomSheetDialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.property_bottom_sheet, null)
        bottomSheetDialog.setContentView(view)
        button_add_new_task_add_photo.setOnClickListener {
            bottomSheetDialog.show()
        }
        view.button_to_camera.setOnClickListener {
            requestCameraPermission()
        }
        view.button_to_gallery.setOnClickListener {
            requestGalleryPermissions()
        }
    }

    private fun toolbar() {
        var toolbar = toolbar
        toolbar.title = "Add New Task"
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return true
    }


    override fun onStarted() {
        progress_bar_add_task.visibility = VISIBLE
    }

    override fun onSuccessfulAdd(message: String) {
        this.toastShort(message)
//        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

    override fun onFailedAdd(message: String) {
        this.toastShort(message)
        progress_bar_add_task.visibility = INVISIBLE
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
            postNewImage(imagePath!!)
            bottomSheetDialog.dismiss()
        }
        if(requestCode == 202) {
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
            .enqueue(object: Callback<UploadPictureResponse> {
                override fun onResponse(
                    call: Call<UploadPictureResponse>,
                    response: Response<UploadPictureResponse>
                ) {
                    if(response.isSuccessful){
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