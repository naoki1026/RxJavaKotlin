package com.example.rxjavakotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import java.util.*

class MainActivity : AppCompatActivity() {

    // クラス内に含まれるSingletonのこと
    companion object {
        const val TAG = "MainActivity"
    }

    val mList: MutableList<Int> = mutableListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    val arrayNum = arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)
    val arrayNum1 = arrayOf(10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 110, 120)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fromArrayOperator()
    }

    /** Just
     *  直接引数に渡したオブジェクトで Observable を生成する。
     *  また、複数渡した場合はその分 onNext が呼ばれ、複数渡す場合は型が統一されてなくてもエラーにならない。
     *
     *  D/MainActivity: onSubscribe
     *  D/MainActivity: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
     *  D/MainActivity: onComplete
     */

    fun justoperators() {
        val observable = Observable.just(mList)
        val observer = object : Observer<List<Int>> {

            override fun onSubscribe(d: Disposable?) {
                Log.d(MainActivity.TAG, "onSubscribe")
            }

            override fun onNext(t: List<Int>?) {
                Log.d(MainActivity.TAG, "$t")
            }

            override fun onError(e: Throwable?) {
                Log.d(MainActivity.TAG, "${e.toString()}")
            }

            override fun onComplete() {
                Log.d(MainActivity.TAG, "onComplete")
            }
        }
        observable.subscribe(observer)
    }

    /** fromArray
     *  様々なオブジェクトをObservableに変換する。
     *
     *  D/MainActivity: onSubscribe
     *  D/MainActivity: onNext : [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12]
     *  D/MainActivity: onNext : [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 110, 120]
     *  D/MainActivity: onComplete
     *  */

    fun fromArrayOperator() {

        val observable = Observable.fromArray(arrayNum, arrayNum1)
        val observer = object : Observer<Array<Int>> {

            override fun onSubscribe(d: Disposable?) {
                Log.d(MainActivity.TAG, "onSubscribe")
            }

            override fun onNext(t: Array<Int>?) {
                Log.d(MainActivity.TAG, "onNext : ${Arrays.toString(t)}")
            }

            override fun onError(e: Throwable?) {
                Log.d(MainActivity.TAG, "${e.toString()}")
            }

            override fun onComplete() {
                Log.d(MainActivity.TAG, "onComplete")
            }
        }
        observable.subscribe(observer)
    }
}