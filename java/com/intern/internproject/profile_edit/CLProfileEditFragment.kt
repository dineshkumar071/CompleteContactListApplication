package com.intern.internproject.profile_edit

import android.app.Activity
import android.content.ComponentName
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Parcelable
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.CLLoginResponseUser
import com.intern.internproject.R
import com.intern.internproject.base.CLBaseFragment
import com.intern.internproject.common.CLAlert
import com.intern.internproject.description.CLDescriptionActivity
import com.intern.internproject.sign_up.CLProfileEditViewModel
import kotlinx.android.synthetic.main.cl_fragment_edit_sigup.*
import kotlinx.android.synthetic.main.cl_fragment_signup.*
import kotlinx.android.synthetic.main.cl_toolbar_edit.*
import java.io.*


class CLProfileEditFragment : CLBaseFragment() {
    lateinit var editProfileViewModel: CLProfileEditViewModel
    private var newLogin: Boolean = false
    lateinit var clAlert: CLAlert
    private var mainPath: String? = null
    private var imagePath: String? = null
    var users = ArrayList<CLLoginResponseUser?>()
    private lateinit var bmp: Bitmap

    companion object {
        //image pick code
        const val IMG_PICK_CODE: Int = 1000
        //permission code
        const val PERMISSION_CODE: Int = 1001

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.cl_fragment_edit_sigup, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        editProfileViewModel = ViewModelProvider(this).get(CLProfileEditViewModel::class.java)
        editProfileViewModel.retrieveFromDatabase()
        observerViewModal()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_save_tool1.setOnClickListener {
            et_firstname.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(edit: Editable?) {
                    editProfileViewModel.firstName = edit.toString()
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }
            })
            et_lastname.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(edit: Editable?) {
                    editProfileViewModel.lastName = edit.toString()
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }
            })
            et_companyname.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(edit: Editable?) {
                    editProfileViewModel.companyName = edit.toString()
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }
            })
            et_phone_number.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(edit: Editable?) {
                    editProfileViewModel.phoneNumber = edit.toString()
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }
            })

            et_street1.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(edit: Editable?) {
                    editProfileViewModel.street1 = edit.toString()
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }
            })
            et_street2.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(edit: Editable?) {
                    editProfileViewModel.street2 = edit.toString()
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }
            })
            et_city.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(edit: Editable?) {
                    editProfileViewModel.city = edit.toString()
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }
            })
            et_state.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(edit: Editable?) {
                    editProfileViewModel.state = edit.toString()
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }
            })
            et_postcode.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(edit: Editable?) {
                    editProfileViewModel.postCode = edit.toString()
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }
            })
            editProfileViewModel.validation()


        }
        iv_edit.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (mContext.checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED && mContext.checkSelfPermission(
                        android.Manifest.permission.CAMERA
                    ) == PackageManager.PERMISSION_DENIED
                ) {
                    //Permission denied
                    val permissions = arrayOf(
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        android.Manifest.permission.CAMERA
                    )
                    //show popup to request runtime permission
                    requestPermissions(permissions, PERMISSION_CODE)
                } else {
                    //permission already granted
                    createMainFolder()
                    pickImageGallery()
                }
            } else {
                //system os less than marshmallow
                createMainFolder()
                pickImageGallery()
            }
        }
    }

    private fun observerViewModal() {

        editProfileViewModel.error.observe(viewLifecycleOwner, Observer { error1 ->
            val positiveClickListener = DialogInterface.OnClickListener { dialog, _ ->
                dialog?.dismiss()
            }
            clAlert = CLAlert.newInstance("Alert", error1, positiveClickListener)
            clAlert.show(
                (mContext as FragmentActivity).supportFragmentManager,
                "fragment_confirm_dialog"
            )
        })
        editProfileViewModel.loginLiveData.observe(viewLifecycleOwner, Observer { login ->
            newLogin = login
        })
        editProfileViewModel.success.observe(viewLifecycleOwner, Observer { _ ->
            Toast.makeText(context, "details saved", Toast.LENGTH_SHORT).show()
            val intent = Intent(
                context,
                CLDescriptionActivity::class.java
            )
            intent.putExtra("EMAIL", editProfileViewModel.eMail)
            startActivity(intent)
            (mContext as Activity).finish()
        })
        editProfileViewModel.dbList.observe(viewLifecycleOwner, Observer { valve ->
            for (i in valve) {

                et_fstname.setText(i.firstName)
                et_lstname.setText(i.lastName)
                et_companyname1.setText(getString(R.string.name_company))
                et_phone_number1.setText(i.phoneNumber)
                /*et_street11.setText(i.street1)
                et_street21.setText(i.street2)
                et_city1.setText(i.city)
                et_state1.setText(i.state)
                et_postcode1.setText(i.postcode)
                passWord=i.passWord
                eMail=i.Email
                confirmPassword=i.confirmPassword*/
            }
        })
        editProfileViewModel.responseSuccess.observe(viewLifecycleOwner, Observer { success ->
            Toast.makeText(context, success, Toast.LENGTH_SHORT).show()
        })
        editProfileViewModel.responseFail.observe(viewLifecycleOwner, Observer { fail ->
            Toast.makeText(context, fail, Toast.LENGTH_SHORT).show()
        })
    }


    private fun pickImageGallery() {
        // Intent the gallery
        val cameraIntents: MutableList<Intent> = ArrayList()
        //for camera
        val captureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        //for gallery
        val packageManager = (context as FragmentActivity).packageManager
        //resource
        val listCam = packageManager.queryIntentActivities(captureIntent, 0)
        for (res in listCam) {
            val packageName = res.activityInfo.packageName
            val intent = Intent(captureIntent)
            intent.component = ComponentName(packageName, res.activityInfo.name)
            intent.setPackage(packageName)
            cameraIntents.add(intent)
        }
        val galleryIntent = Intent()
        galleryIntent.type = "image/*"
        galleryIntent.action = Intent.ACTION_GET_CONTENT
        val chooserIntent =
            Intent.createChooser(galleryIntent, resources.getString(R.string.upload))
        chooserIntent.putExtra(
            Intent.EXTRA_INITIAL_INTENTS,
            cameraIntents.toTypedArray<Parcelable>()
        )
        startActivityForResult(chooserIntent, IMG_PICK_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //permission from the popup granted
                    createMainFolder()
                    pickImageGallery()

                } else {
                    // permission from the popup denied
                    Toast.makeText(context, "PERMISSION DENIED", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun createMainFolder() {
        val file = File(Environment.getExternalStorageDirectory(), "CONTACT_LIST")
        if (!file.exists()) {
            file.mkdirs()
            mainPath = file.absolutePath
        } else {
            mainPath = File(Environment.getExternalStorageDirectory(), "CONTACT_LIST").absolutePath
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMG_PICK_CODE && resultCode == Activity.RESULT_OK) {

            val image = data?.extras?.get("data")
            if (image != null) {
                //imageView.setImageURI(data?.data)
                bmp = image as Bitmap
                iv_edit.setImageBitmap(bmp)
            } else {
                bmp = MediaStore.Images.Media.getBitmap(mContext.contentResolver, data?.data)
                iv_edit.setImageBitmap(bmp)
            }
            val fileName: String? = editProfileViewModel.eMail
            val file = File(mainPath, "$fileName.jpg")
            try {
                val stream: OutputStream?
                stream = FileOutputStream(file)
                bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream)

            } catch (e: IOException) // Catch the exception
            {
                e.printStackTrace()
            }
            val savedImageURI: Uri = Uri.parse(file.absolutePath)
            imagePath = savedImageURI.toString()
            /* objectpojo = CLUSerEntity(
                 fstName,
                 lstName,
                 companyName,
                 eMail,
                 phoneNumber,
                 passWord,
                 confirmPassword,
                 street1,
                 street2,
                 city,
                 state,
                 postCode,
                 true,
                 file.absolutePath
             )*/
            iv_edit.setImageURI(savedImageURI)
        }
    }
}