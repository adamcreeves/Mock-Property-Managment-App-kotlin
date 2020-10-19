package com.example.mockpropertymanagmentapp.data.repositories

import android.Manifest
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat.startActivityForResult
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener

class PhotoRepository {
    fun requestGalleryPermissions(myContext: Context) {
        Dexter.withContext(myContext)
            .withPermissions(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    if (report!!.areAllPermissionsGranted()) {
//                        openTheGallery()
                        Toast.makeText(
                            myContext,
                            "Gallery Access Granted",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    if (report.isAnyPermissionPermanentlyDenied) {
                        Toast.makeText(
                            myContext,
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
//    fun openTheGallery() {
//        var intent = Intent()
//        intent.action = Intent.ACTION_PICK
//        intent.type = "image/*"
//        startActivityForResult(intent, 202)
//    }
}