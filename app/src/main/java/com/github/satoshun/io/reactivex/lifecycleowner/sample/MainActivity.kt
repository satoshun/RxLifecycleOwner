package com.github.satoshun.io.reactivex.lifecycleowner.sample

import android.arch.lifecycle.LifecycleActivity
import android.os.Bundle
import android.util.Log
import com.github.satoshun.io.reativex.lifecycleowner.subscribeBy
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class MainActivity : LifecycleActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.main_act)

    Observable.just("1", "2")
        .delay(5, TimeUnit.SECONDS)
        .doOnDispose {
          Log.d("call doOnDispose", "done")
        }
        .subscribeBy(this, {
          Log.d("call onNext", it)
        })
  }
}
