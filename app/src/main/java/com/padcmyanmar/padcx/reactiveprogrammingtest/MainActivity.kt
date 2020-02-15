package com.padcmyanmar.padcx.reactiveprogrammingtest

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.padcmyanmar.padcx.reactiveprogrammingtest.dummy.DUMMY_FOOD_LIST
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.toObservable

class MainActivity : AppCompatActivity() {

    private val compositeDisposable : CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

         Log.d("Hello","hello")

        Observable.just(getNumbersFromOnetoTen())

        Observable.fromIterable(getNumbersFromOnetoTen())

        Observable.fromCallable {
            getNumbersFromOnetoTen()
        }

        //getNumbersFromOnetoTen().toObservable() [same as Observable.fromIterable]

        //filter
        getNumbersFromOnetoTen().toObservable()
            .filter { it % 2 == 0 }
            .subscribe {
                Log.d("Even Numbers",it.toString())
            }
            .addTo(compositeDisposable)

        //first element
        getNumbersFromOnetoTen().toObservable()
            .firstElement()
            .subscribe {
                Log.d("First Number",it.toString())
            }
            .addTo(compositeDisposable)

        //last element
        getNumbersFromOnetoTen().toObservable()
            .lastElement()
            .subscribe {
                Log.d("Last Number",it.toString())
            }
            .addTo(compositeDisposable)

        //skip
        getNumbersFromOnetoTen().toObservable()
            .skip(3)
            .subscribe {
                Log.d("Skipped 3 Numbers",it.toString())
            }
            .addTo(compositeDisposable)

        //take
        getNumbersFromOnetoTen().toObservable()
            .take(3)
            .subscribe {
                Log.d("Took 3 Numbers",it.toString())
            }
            .addTo(compositeDisposable)

        //ignore elements
        getNumbersFromOnetoTen().toObservable()
            .ignoreElements()
            .subscribe{
                Log.d("Ignore","Ignore  all elements")
            }
            .addTo(compositeDisposable)

    //Transforming Operators
        //map can use convert desire type
        getNumbersFromOnetoTen().toObservable()
            .map { it * 10 }
            .subscribe {
                Log.d("Multiply Numbers",it.toString())
            }
            .addTo(compositeDisposable)

        getNumbersFromOnetoTen().toObservable()
            .map { it.toString() }
            .subscribe {
                Log.d("String",it)
            }
            .addTo(compositeDisposable)


        //buffer can use combine
        getNumbersFromOnetoTen().toObservable()
            .buffer(2)
            .subscribe {
                Log.d("Buffer",it.toString())
            }
            .addTo(compositeDisposable)

        //scan
        getNumbersFromOnetoTen().toObservable()
            .scan{ a,b -> a+b }
            .subscribe {
                Log.d("Scan",it.toString())
            }
            .addTo(compositeDisposable)

    //Conditional Operators
        //all return boolean value
        getNumbersFromOnetoTen().toObservable()
            .all { it %2 ==0  }
            .subscribe {  result ->
                Log.d("All",result.toString())
            }
            .addTo(compositeDisposable)

        //skipWhile skip elements when condition is true
        getNumbersFromOnetoTen().toObservable()
            .skipWhile { it < 2 }
            .subscribe {
                Log.d("SkipWhile",it.toString())
            }
            .addTo(compositeDisposable)

        //takeWhile take elements when condition is true
        getNumbersFromOnetoTen().toObservable()
            .takeWhile { it < 2 }
            .subscribe {
                Log.d("TakeWhile",it.toString())
            }
            .addTo(compositeDisposable)

        // reduce only last value return
        getNumbersFromOnetoTen().toObservable()
            .reduce { a,b -> a+b }
            .subscribe{
                Log.d("Reduce",it.toString())
            }
            .addTo(compositeDisposable)

// Exercises
        // get Odd no: & take first
        getNumbersFromOnetoTen().toObservable()
            .filter { it%3 == 0 }
            .firstElement()
            .subscribe {
                Log.d("First Odd no: is ",it.toString())
            }
            .addTo(compositeDisposable)

        // cre: 1 to 100 List, take last 50 elements,
        // only odd numbers , 1 item is multiply by 20
        // and cast Hello in Front

        getNumbersFromOnetoHundred().toObservable()
            .skip(50)
            .filter { it % 2 != 0 }
            .map { "Hello ${it*20}" }
            .subscribe {
                Log.d("Test exercise",it.toString())
            }
            .addTo(compositeDisposable)

        // Get total price of all American Food
        DUMMY_FOOD_LIST.toObservable()
            .filter { it.category == "American" }
            .map { it.price }
            .reduce { a,b ->  a+b }
            .subscribe {
                Log.d("Result 1",it.toString())
            }
            .addTo(compositeDisposable)

        // Skip until the first Italian Food &
        // print out all food name with price below 4000
        DUMMY_FOOD_LIST.toObservable()
            .skipWhile { it.category != "Italian" }
            .filter { it.price < 4000 }
            .subscribe {
                Log.d("Result 2",it.name)
            }
            .addTo(compositeDisposable)


        // Find food which starts with "S" &
        // has a price less than 8000.
        // Produce a map with "name" as first value & "price" as second value
        DUMMY_FOOD_LIST.toObservable()
            .filter {
                it.name.startsWith("S") &&
                it.price < 8000
            }
            .map { Pair(it.name,it.price) }
            .subscribe {
                Log.d("Result 3",it.toString())
            }
            .addTo(compositeDisposable)
    }

    private fun getNumbersFromOnetoTen() : List<Int> {
        return (1..10).toList()
    }

    private fun getNumbersFromOnetoHundred() : List<Int> {
        return (1..100).toList()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}
