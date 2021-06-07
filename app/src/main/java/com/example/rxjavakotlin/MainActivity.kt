package com.example.rxjavakotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.reactivex.rxjava3.core.*
import io.reactivex.rxjava3.core.Observable
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
    val mUserList = arrayListOf<User>(
        User(1, "demo1", 22),
        User(2, "demo2", 18),
        User(3, "demo3", 15),
        User(4, "demo4", 16),
        User(5, "demo5", 22),
        User(6, "demo6", 26)
    )

    val mUserEmptyList = emptyList<User>()

    val mUserProfileList = arrayListOf<UserProfile>(
        UserProfile(1, "demo1", 22, "https://text.com/1"),
        UserProfile(2, "demo2", 18, "https://text.com/2"),
        UserProfile(3, "demo3", 16, "https://text.com/3"),
        UserProfile(4, "demo4", 16, "https://text.com/4"),
        UserProfile(5, "demo5", 22, "https://text.com/5"),
        UserProfile(6, "demo6", 26, "https://text.com/6"),
        UserProfile(7, "demo5", 24, "https://text.com/7"),
        UserProfile(8, "demo6", 24, "https://text.com/8")
    )

    val mBlogList = mutableListOf<Blog> (
        Blog(1, 1, "title1", "content1"),
        Blog(2, 1, "title2", "content2"),
        Blog(3, 2, "title1", "content1"),
        Blog(4, 2, "title2", "content2"),
        Blog(5, 2, "title3", "content3"),
        Blog(6, 3, "title1", "content1"),
        Blog(7, 13, "title1", "content1")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        zipOperator()
//            .subscribe(
//                {
//                    Log.d(TAG, "OnNext $it")
//                },
//                {
//                    Log.d(TAG, "onError ${it}")
//                },
//                {
//                    Log.d(TAG, "onComplete")
//                }
//            )

        createCompletable().subscribe((observerCompletableObservable()))
    }

    /** 4, 5.Just
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

    /** 6.fromArray
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

    /** 7.from Iterable
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

    /** 8.Range
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

    /** 9.Repeat
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


    /** 10.Interval
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

    /** 11.Timer
     *  指定した時間分の遅延後に値が出力する Observableを作成する。
     *  */

    fun timeOperator() : Observable<Long> {
        return Observable.timer(5, TimeUnit.SECONDS, )
    }

    private fun getLocation(){
        Log.d(TAG, "latitude: 102.0303 Longitude: 1.2355")
    }

    /** 12.create
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

    /** 13.filter
     *  条件に一致したアイテムのみ抽出する。
     *  */

    fun filterOperator() : Observable<User> {
        return Observable.fromIterable(mUserList)
    }

    /** 14.Last
     *  最後のアイテムのみ取得する。
     *  */


    fun lastOperator() : Observable<User> {
        return Observable.fromIterable(mUserList)
    }

    /** 15.Distinct
     *  重複しているアイテムはどちらか片方しか表示されない。
     *  */

    fun distinctOperator() : Observable<User> {
        return Observable.fromIterable(mUserList)
    }

    /** 16.Skip
     *  指定した数だけonNextを行わない。
     *  */

    fun skipOperator() : Observable<User> {
        return Observable.fromIterable(mUserList)
    }

    /** 17.Buffer
     *  アイテムを一度に出力するのではなくて、分けて出力する。
     *  */

    fun bufferOperator() : Observable<User> {
        return Observable.fromIterable(mUserList)
    }

    /** 18.Map
     *  各アイテムに対して関数を反映した上で出力する
     *  */

    fun mapOperator() : Observable<User> {
        return Observable.fromIterable(mUserList)
    }

    /** 19.FlatMap
     *  Observableによって出力されたアイテムをObservableに変換して、1つのObservableにした上で出力する
     *  ※2重の配列を出力する、関数を間にかませる
     *  */

    fun flatMapOperator() : Observable<User> {
        return Observable.fromIterable(mUserList)
    }

    fun getUserProfile(id: Long) : Observable<UserProfile> {
        return Observable.fromIterable(mUserProfileList)
            .filter {
                it.id == id
            }
    }

    /** 20.GroupBy Operator
     *  指定した条件に合致するアイテムをObservableから取り出して、それぞれ異なるObservableとして出力する
     *  */

    fun groupByOperator() : Observable<User> {
        return Observable.fromIterable(mUserList)
    }

    /** 21.Merge Operator
     *  複数の出力を結合することで、1つにまとめることができる
     *  */

    fun getUser() : Observable<User> {
        return Observable.fromIterable(mUserList)
    }


    fun getProfile() : Observable<UserProfile> {
        return Observable.fromIterable(mUserProfileList)
    }


    fun mergeOperator(): Observable<Any> {
        return Observable.merge(getUser(), getProfile())
    }

    /** 22.Concat Operator
     *  複数のObservableを合成。
     *  mergeとconcatの違いはmergeはObservableを合成する際に全てのストリームの順を考慮した合成のに対し、
     *  concatは引数で渡されたObservableの順で合成すること。
     *  */

    fun getNum1To100() : Observable<Int> {
        return Observable.range(1, 100)
    }


    fun getNum101To150() : Observable<Int> {
        return Observable.range(101, 50)
    }


    fun concatOperator(): Observable<Int>{
    return Observable.concat(getNum101To150(), getNum1To100())
//        return getNum1To100().concatWith(getNum101To150())
    }

    /** 23.StartWith Operator
     *  アイテムの出力を行う前に、指定された一連のアイテムを出力する
     *  getNum1To100()から先に実行して、getNum101To150()を実行する
     *  */

    fun startWithOperator(): Observable<Int> {
        return getNum101To150().startWith(getNum1To100())
    }


    /** 24.Zip Operator
     *  複数のObservableを組み合わせて（合体する）、出力する
     *  例では、1つめのObservableを組み合わせて、1Aと出力している。
     *  以降同様に組み合わせて出力して、5に関しては対応するアルファベットが存在しないため、出力されない。
     *  */

    // Sample1

    fun zipOperator() : Observable<Any> {
        val num = Observable.just(1, 2, 3, 4, 5)
        val char = Observable.just("A", "B", "C", "D")
        return Observable.zip(num, char, { t1, t2 ->
            "$t1$t2"
        })
    }

    // Sample2

    fun zipOperatorSecond() : Observable<List<BlogDetail>> {
        return Observable.zip(getUsers(), getBlogs(), {t1, t2 ->
            blogDetail(t1, t2)
        })
    }

    fun getBlogs() : Observable<List<Blog>> {
        return Observable.just(mBlogList)
    }


    fun getUsers() : Observable<List<User>> {
        return Observable.just(mUserList)
    }

    fun blogDetail(t1: List<User>, t2: List<Blog>) : List<BlogDetail> {
        val listBlogDetail : MutableList<BlogDetail> = emptyList<BlogDetail>().toMutableList()
        t1.forEach { user ->
            t2.forEach {
                    blog ->
                if(blog.userId == user.id) {
                    listBlogDetail.add(
                        BlogDetail(
                            blog.id, blog.userId, blog.title, blog.content, user
                        )
                    )
                }
            }
        }
        return listBlogDetail
    }

    /** 25.Observable　and Observer
     *  */

    fun createObservable() : Observable<Int> {
        return Observable.create { emitter ->
            try {
                if(!emitter.isDisposed){
                    for(i in 0..100) {
                        emitter.onNext(i)
                    }
                    emitter.onComplete()
                }


            }
            catch (e:Exception) {
                emitter.onError(e)


            }
        }
    }

    fun observer() : Observer<Int> {
        return object : Observer<Int> {
            override fun onSubscribe(d: Disposable?) {
                Log.d(TAG, "onSubscribe")
            }

            override fun onNext(t: Int?) {
                Log.d(TAG, "onNext $t")
            }

            override fun onError(e: Throwable?) {
                Log.d(TAG, "onError")
            }

            override fun onComplete() {
                Log.d(TAG, "onComplete")
            }
        }
    }


    /** 27.Single and Single Observer
     * データを1件だけ通知するか、もしくはエラーを通知するクラス
     * 1回だけしか呼び出されないため、サンプルコードの場合は0のみ出力される
     *  */

    fun createSingleObservable() : Single<Int> {
        return Single.create { emitter ->
            try {
                if(!emitter.isDisposed) {
                    for (i in 0..100) {
                        emitter.onSuccess(i)
                    }
                }
            }
            catch (e:Exception) {
                emitter.onError(e)
            }
        }
    }

    fun singleObserver() : SingleObserver<Int> {
        return object : SingleObserver<Int> {
            override fun onSubscribe(d: Disposable?) {
                Log.d(TAG, "onSubscribe")
            }


            override fun onSuccess(t: Int?) {
                Log.d(TAG, "onSuccess : $t")
            }


            override fun onError(e: Throwable?) {
                Log.d(TAG, "onError")
            }
        }
    }

    /** 28.Maybe and MaybeObserver
     * データを1件だけ通知するか、1件も通知せずに完了を通知するか、もしくはエラーを通知するクラス
     *  */


    fun createMaybeObservable(): Maybe<List<User>> {
        return Maybe.just(mUserList)
    }

    fun observeMaybeObservable() : MaybeObserver<List<User>> {
        return object : MaybeObserver<List<User>> {
            override fun onSubscribe(d: Disposable?) {
                Log.d(TAG, "onSubscribe")
            }
            override fun onSuccess(t: List<User>?) {
                t?.forEach {
                    Log.d(TAG, "onSuccess : $it")
                }
            }
            override fun onError(e: Throwable?) {
                Log.d(TAG, "onError")
            }

            override fun onComplete() {
                Log.d(TAG, "onComplete")
            }
        }
    }

    /** 29.Completable and CompletableObserver
     * 1件も通知せずに完了を通知するか、もしくはエラーを通知するクラス
     *  */

    fun createCompletable() : Completable {
        return Completable.create {  emitter ->
            try {
                if(!emitter.isDisposed) {
                    getLocation()
                    emitter.onComplete()
                }
            } catch (e:Exception) {
                emitter.onError(e)
            }


        }
    }
    
    fun observerCompletableObservable() : CompletableObserver {
        return object: CompletableObserver {
            override fun onSubscribe(d: Disposable?) {
                Log.d(TAG, "onSubscribe")
            }


            override fun onComplete() {
                Log.d(TAG, "onComplete")
            }


            override fun onError(e: Throwable?) {

                // thorw Exception(“Exceptoion”)
                // 上記を記述することでエラーの状態することができる
                Log.d(TAG, "onError")
            }


        }
    }


    fun createFlowableObservable() : Flowable<Int> {
        return Flowable.range(1, 100)
    }


    fun createFlowableObservableT2() : Observable<Int> {
        return Observable.range(1, 100)
    }
}