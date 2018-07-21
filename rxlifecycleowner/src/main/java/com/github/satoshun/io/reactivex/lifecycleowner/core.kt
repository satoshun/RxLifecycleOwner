package com.github.satoshun.io.reactivex.lifecycleowner

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import io.reactivex.disposables.Disposable

@Suppress("NOTHING_TO_INLINE")
internal inline fun lifecycleBoundObserver(
  lifecycle: Lifecycle,
  targetEvent: Lifecycle.Event,
  init: (LifecycleBoundObserver) -> Disposable
): Disposable {
  val observer = LifecycleBoundObserver(targetEvent)
  val disposable = init(observer)
  observer.disposable = disposable
  lifecycle.addObserver(observer)
  return disposable
}

internal class LifecycleBoundObserver(
  private val targetEvent: Lifecycle.Event
) : LifecycleObserver {

  lateinit var disposable: Disposable

  @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
  fun onStateChange(owner: LifecycleOwner, event: Lifecycle.Event) {
    if (targetEvent == event) {
      disposable.dispose()
      owner.lifecycle.removeObserver(this)
      return
    }
    if (event == Lifecycle.Event.ON_DESTROY) {
      owner.lifecycle.removeObserver(this)
    }
  }
}
