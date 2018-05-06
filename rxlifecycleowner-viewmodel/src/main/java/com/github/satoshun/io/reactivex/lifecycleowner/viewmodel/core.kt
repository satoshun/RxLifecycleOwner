package com.github.satoshun.io.reactivex.lifecycleowner.viewmodel

import android.arch.lifecycle.ViewModel
import android.support.annotation.CallSuper
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

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
