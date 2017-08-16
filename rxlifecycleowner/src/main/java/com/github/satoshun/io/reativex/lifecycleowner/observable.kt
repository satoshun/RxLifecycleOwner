package com.github.satoshun.io.reativex.lifecycleowner

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import io.reactivex.Observable
import io.reactivex.disposables.Disposable

// todo only support onNext
fun <T : Any> Observable<T>.subscribeBy(owner: LifecycleOwner, onNext: (T) -> Unit): Disposable {
  val disposable = subscribe(onNext)
  owner.lifecycle.addObserver(LifecycleBoundObserver(disposable))
  return disposable
}

// todo: now only support ON_DESTROY EVENT
private class LifecycleBoundObserver(
    private val disposable: Disposable
) : LifecycleObserver {

  @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
  fun onStateChange() {
    disposable.takeIf { !it.isDisposed }?.dispose()
  }
}
