package com.bearkinf.appinitprojectlatest

import android.content.DialogInterface.OnClickListener
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bearkinf.appinitprojectlatest.net.action.GithubAction

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        GithubAction.getUserRepos("bearkinf") { success, msg ->
//            if (success) {
//
//                Log.e("bear", "success : $msg")
//            } else {
//                Log.w("bear", "fail : $msg")
//                errorDialog(msg)
//            }
//        }

        GithubAction.getUserRepos2("bearfdaszcxkinf", {
            Log.e("bear", "success : $it")
        }, {
            Log.w("bear", "fail : $it")
            errorDialog(it)
        });

//        TestApiService.service.listRepos("bearkinf")
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({
//                Log.e("bear", "it : ${it}")
//            }, {
//                it.printStackTrace()
//                Log.e("bear", "error : ${it.message}")
//
//                errorDialog(it.message)
//            })


//        TestApiService.service2(TestApiService::class.java).listRepos("bearkinf")
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({
//                Log.w("bear", "it : ${it}")
//            }, {
//                it.printStackTrace()
//            })
//            .let {
//                disposable.add(it)
//            }
//
//        TestApiService.service33(TestApiService::class.java).listRepos("bear")
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({
//                Log.d("bear", "it : ${it}")
//            }, {
//                it.printStackTrace()
//            })
//            .let {
//                disposable.add(it)
//            }

    }


    override fun onDestroy() {
        super.onDestroy()

        GithubAction.clearDisposable
    }


    private fun errorDialog(msg: String?) {

        AlertDialog.Builder(this@MainActivity)

            .setTitle("error")
            .setMessage(msg)
            .setCancelable(false)
            .setNegativeButton("확인", OnClickListener { dialog, which ->
                dialog.dismiss()
            })
            .create().show()


    }


}