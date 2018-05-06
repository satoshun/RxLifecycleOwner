package com.github.satoshun.io.reactivex.lifecycleowner

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import android.arch.lifecycle.ViewModel
import android.support.annotation.CallSuper
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

internal class LifecycleBoundObserver(
    private val targetEvent: Lifecycle.Event
) : LifecycleObserver {

  lateinit var disposable: Disposable

  @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
  fun onStateChange(owner: LifecycleOwner, event: Lifecycle.Event) {
    if (targetEvent == event) {
      disposable.dispose()
      owner.lifecycle.removeObserver(this)
    }
  }
}

interface RxViewModel {
  fun addDisposable(disposable: Disposable)
  fun onCleared()
}

/**
 * sample implementation of [RxViewModel].
 *
 * disposables will released when Activity finished(not configuration changed)
 */
open class CompositeDisposableViewModel : ViewModel(), RxViewModel {

  private val disposables: CompositeDisposable = CompositeDisposable()

  @CallSuper
  override fun addDisposable(disposable: Disposable) {
    disposables.add(disposable)
  }

  @CallSuper
  override fun onCleared() {
    disposables.dispose()
  }
}
