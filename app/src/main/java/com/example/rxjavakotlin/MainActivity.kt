package com.example.rxjavakotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableOnSubscribe
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    // クラス内に含まれるSingletonのこと
    companion object {
        const val TAG = "MainActivity"
    }

    val mListNum = mutableListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)
    val mList: MutableList<Int> = mutableListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    val arrayNum = arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)
    val arrayNum1 = arrayOf(10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 110, 120)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rangeOperator().subscribe(
            {
                Log.d(TAG, "onNext : $it")
            },
            {
                Log.d(TAG, "${it}")
            },
            {
                Log.d(TAG, "onComplete")
            }
        )
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
                Log.d(TAG, "onSubscribe")
            }

            override fun onNext(t: List<Int>?) {
                Log.d(TAG, "$t")
            }

            override fun onError(e: Throwable?) {
                Log.d(TAG, "${e.toString()}")
            }

            override fun onComplete() {
                Log.d(TAG, "onComplete")
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
                Log.d(TAG, "onSubscribe")
            }

            override fun onNext(t: Array<Int>?) {
                Log.d(TAG, "onNext : ${Arrays.toString(t)}")
            }

            override fun onError(e: Throwable?) {
                Log.d(TAG, "${e.toString()}")
            }

            override fun onComplete() {
                Log.d(TAG, "onComplete")
            }
        }
        observable.subscribe(observer)
    }

    /** from Iterable
     *  要素を取り出して、1つずつ出力する。
     *
     * D/MainActivity: onSubscribe
     * D/MainActivity: onNext : 1
     * D/MainActivity: onNext : 2
     * D/MainActivity: onNext : 3
     * D/MainActivity: onNext : 4
     * D/MainActivity: onNext : 5
     * D/MainActivity: onNext : 6
     * D/MainActivity: onNext : 7
     * D/MainActivity: onNext : 8
     * D/MainActivity: onNext : 9
     * D/MainActivity: onNext : 10
     * D/MainActivity: onComplete
     *
     *  */

    fun fromIterableOperator(){
        val observable = Observable.fromIterable(mList)
        val observer = object : Observer<Int> {
            override fun onSubscribe(d: Disposable?) {
                Log.d(TAG, "onSubscribe")
            }

            override fun onNext(t: Int?) {
                Log.d(TAG, "onNext : $t")
            }

            override fun onError(e: Throwable?) {
                Log.d(TAG, "${e.toString()}")
            }

            override fun onComplete() {
                Log.d(TAG, "onComplete")
            }
        }
        observable.subscribe(observer)
    }

    /** Range
     *  指定した範囲の整数を出力するObservableを生成する。
     *
     * D/MainActivity: onNext : 1
     * D/MainActivity: onNext : 2
     * D/MainActivity: onNext : 3
     * D/MainActivity: onNext : 4
     * D/MainActivity: onNext : 5
     * D/MainActivity: onNext : 6
     * D/MainActivity: onNext : 7
     * D/MainActivity: onNext : 8
     * D/MainActivity: onNext : 9
     * D/MainActivity: onNext : 10
     * D/MainActivity: onComplete
     *
     *  */

    fun rangeOperator() : Observable<Int> {
        return Observable.range(1, 10)
    }

    /** Repeat
     *  指定回数繰り返す Observable を生成する。
     *　
     *  D/MainActivity: onNext : 1
     *  D/MainActivity: onNext : 2
     *  D/MainActivity: onNext : 3
     *  D/MainActivity: onNext : 4
     *  D/MainActivity: onNext : 5
     *  D/MainActivity: onNext : 6
     *  D/MainActivity: onNext : 7
     *  D/MainActivity: onNext : 8
     *  D/MainActivity: onNext : 9
     *  D/MainActivity: onNext : 10
     *  D/MainActivity: onNext : 11
     *  D/MainActivity: onNext : 12
     *  D/MainActivity: onNext : 13
     *  D/MainActivity: onNext : 14
     *  D/MainActivity: onNext : 15
     *  D/MainActivity: onNext : 16
     *  D/MainActivity: onNext : 17
     *  D/MainActivity: onNext : 18
     *  D/MainActivity: onNext : 19
     *  D/MainActivity: onNext : 20
     *  D/MainActivity: onNext : 1
     *  D/MainActivity: onNext : 2
     *  D/MainActivity: onNext : 3
     *  D/MainActivity: onNext : 4
     *  D/MainActivity: onNext : 5
     *  D/MainActivity: onNext : 6
     *  D/MainActivity: onNext : 7
     *  D/MainActivity: onNext : 8
     *  D/MainActivity: onNext : 9
     *  D/MainActivity: onNext : 10
     *  D/MainActivity: onNext : 11
     *  D/MainActivity: onNext : 12
     *  D/MainActivity: onNext : 13
     *  D/MainActivity: onNext : 14
     *  D/MainActivity: onNext : 15
     *  D/MainActivity: onNext : 16
     *  D/MainActivity: onNext : 17
     *  D/MainActivity: onNext : 18
     *  D/MainActivity: onNext : 19
     *  D/MainActivity: onNext : 20
     *  D/MainActivity: onComplete
     *
     *  */

    fun repeatOperator() : Observable<Int> {
        return Observable.range(1, 20).repeat(2)
    }


    /** Interval
     *  間隔をあけて出力する。
     *  */

    fun intervalOperator() : Observable<Long> {
        return Observable.interval(1, TimeUnit.SECONDS)
    }

    // Operators（10までカウントしたらonCompleteする）
    fun intervalOperatorSecond() : Observable<Long> {
        return Observable.interval(1, TimeUnit.SECONDS).takeWhile { value ->
            value <= 10
        }
    }

    /** Timer
     *  指定した時間分の遅延後に値が出力する Observableを作成する。
     *  */

    fun timeOperator() : Observable<Long> {
        return Observable.timer(5, TimeUnit.SECONDS, )
    }

    private fun getLocation(){
        Log.d(TAG, "latitude: 102.0303 Longitude: 1.2355")
    }

    /** create
     *  スクラッチでObservableを作るオペレーター
     *  */

    fun createOperator() : Observable<Int> {
        return Observable.create(ObservableOnSubscribe<Int> {
            try {
                for( i in mListNum){
                    it.onNext(i * 5)
                }
                it.onComplete()
            }
            catch (e: Exception) {
                it.onError(e)
            }
        })
    }
}