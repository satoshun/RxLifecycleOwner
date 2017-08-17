package com.github.satoshun.io.reativex.lifecycleowner

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.support.annotation.MainThread
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.exceptions.OnErrorNotImplementedException

private val onNextEmpty: (Any) -> Unit = {}
private val onErrorEmpty: (Throwable) -> Unit = { throw OnErrorNotImplementedException(it) }
private val onCompleteEmpty: () -> Unit = {}

@MainThread
fun <T : Any> Observable<T>.subscribeBy(
    owner: LifecycleOwner,
    event: Lifecycle.Event = Lifecycle.Event.ON_DESTROY,
    onNext: (T) -> Unit = onNextEmpty,
    onError: (Throwable) -> Unit = onErrorEmpty,
    onComplete: () -> Unit = onCompleteEmpty
): Disposable {
  val disposable = subscribe(onNext, onError, onComplete)
  owner.lifecycle.addObserver(LifecycleBoundObserver(disposable, event))
  return disposable
}

@MainThread
fun <T : Any> Observable<T>.subscribeBy(
    viewModel: RxViewModel,
    onNext: (T) -> Unit = onNextEmpty,
    onError: (Throwable) -> Unit = onErrorEmpty,
    onComplete: () -> Unit = onCompleteEmpty
): Disposable {
  val disposable = subscribe(onNext, onError, onComplete)
  viewModel.addDisposable(disposable)
  return disposable
}
