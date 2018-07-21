package com.github.satoshun.io.reactivex.lifecycleowner.sample

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.github.satoshun.io.reactivex.lifecycleowner.subscribeOf
import com.github.satoshun.io.reactivex.lifecycleowner.viewmodel.CompositeDisposableViewModel
import com.github.satoshun.io.reactivex.lifecycleowner.viewmodel.subscribeOf
import io.reactivex.Observable
import io.reactivex.subjects.ReplaySubject
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

  private val mainViewModel: MainViewModel by lazy {
    ViewModelProviders.of(this).get(MainViewModel::class.java)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.main_act)

    // follow Lifecycle event
    Observable.interval(3, TimeUnit.SECONDS)
        .doOnDispose { Log.d("call doOnDispose", "done with DESTROY EVENT") }
        .subscribeOf(this, onNext = {
          Log.d("call onNext", it.toString())
        })

    mainViewModel.strings
        .doOnDispose { Log.d("call doOnDispose", "done child with DESTROY EVENT") }
        .subscribeOf(this, onNext = {
          Log.d("call onNext", it)
        })

    mainViewModel.strings
        .doOnDispose { Log.d("call doOnDispose", "done child with DESTROY EVENT") }
        .subscribeOf(this.lifecycle, onNext = {
          Log.d("call onNext: Lifecycle", it)
        })
  }
}

class MainViewModel : CompositeDisposableViewModel() {

  private val repository = UserNameRepository()

  val strings = ReplaySubject.create<String>().toSerialized()

  init {
    refreshStreams()
  }

  fun refreshStreams() {
    // follow ViewModel onCleared lifecycle
    repository.getUserName()
        .doOnDispose { Log.d("call doOnDispose", "done source with ViewModel") }
        .subscribeOf(this,
            onNext = { strings.onNext(it) },
            onError = { strings.onError(it) }
        )
  }
}

private class UserNameRepository {
  fun getUserName(): Observable<String> {
    return Observable.just("tom", "ken").delay(5, TimeUnit.SECONDS)
  }
}
