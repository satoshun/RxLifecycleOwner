package com.github.satoshun.io.reactivex.lifecycleowner.viewmodel

import android.arch.lifecycle.ViewModel
import android.support.annotation.CallSuper
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * It's a interface for [subscribeOf] methods. It's supposed to be used with [ViewModel] like a
 * [CompositeDisposableViewModel]
 */
interface RxViewModel {
  fun addDisposable(disposable: Disposable)
  fun onCleared()
}

/**
 * sample implementation of [RxViewModel]. [Disposable]s will be release when ViewModel finished.
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
