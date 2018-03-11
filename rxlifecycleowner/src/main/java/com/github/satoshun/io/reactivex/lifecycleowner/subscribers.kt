package com.github.satoshun.io.reactivex.lifecycleowner

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.support.annotation.MainThread
import io.reactivex.*
import io.reactivex.disposables.Disposable
import io.reactivex.exceptions.OnErrorNotImplementedException

private val onNextEmpty: (Any) -> Unit = {}
private val onErrorEmpty: (Throwable) -> Unit = { throw OnErrorNotImplementedException(it) }
private val onCompleteEmpty: () -> Unit = {}

@MainThread
fun <T : Any> Flowable<T>.subscribeOf(
    owner: LifecycleOwner,
    event: Lifecycle.Event = Lifecycle.Event.ON_DESTROY,
    onNext: (T) -> Unit = onNextEmpty,
    onError: (Throwable) -> Unit = onErrorEmpty,
    onComplete: () -> Unit = onCompleteEmpty
): Disposable {
  return subscribeOf(owner.lifecycle, event, onNext, onError, onComplete)
}

@MainThread
fun <T : Any> Flowable<T>.subscribeOf(
    lifecycle: Lifecycle,
    event: Lifecycle.Event = Lifecycle.Event.ON_DESTROY,
    onNext: (T) -> Unit = onNextEmpty,
    onError: (Throwable) -> Unit = onErrorEmpty,
    onComplete: () -> Unit = onCompleteEmpty
): Disposable {
  val observer = LifecycleBoundObserver(event)
  val disposable = this
      .doOnTerminate { lifecycle.removeObserver(observer) }
      .subscribe(onNext, onError, onComplete)
  observer.disposable = disposable
  lifecycle.addObserver(observer)
  return disposable
}


@MainThread
fun <T : Any> Observable<T>.subscribeOf(
    owner: LifecycleOwner,
    event: Lifecycle.Event = Lifecycle.Event.ON_DESTROY,
    onNext: (T) -> Unit = onNextEmpty,
    onError: (Throwable) -> Unit = onErrorEmpty,
    onComplete: () -> Unit = onCompleteEmpty
): Disposable {
  return subscribeOf(owner.lifecycle, event, onNext, onError, onComplete)
}

@MainThread
fun <T : Any> Observable<T>.subscribeOf(
    lifecycle: Lifecycle,
    event: Lifecycle.Event = Lifecycle.Event.ON_DESTROY,
    onNext: (T) -> Unit = onNextEmpty,
    onError: (Throwable) -> Unit = onErrorEmpty,
    onComplete: () -> Unit = onCompleteEmpty
): Disposable {
  val observer = LifecycleBoundObserver(event)
  val disposable = this
      .doOnTerminate { lifecycle.removeObserver(observer) }
      .subscribe(onNext, onError, onComplete)
  observer.disposable = disposable
  lifecycle.addObserver(observer)
  return disposable
}


@MainThread
fun <T : Any> Single<T>.subscribeOf(
    owner: LifecycleOwner,
    event: Lifecycle.Event = Lifecycle.Event.ON_DESTROY,
    onSuccess: (T) -> Unit = onNextEmpty,
    onError: (Throwable) -> Unit = onErrorEmpty
): Disposable {
  return subscribeOf(owner.lifecycle, event, onSuccess, onError)
}

@MainThread
fun <T : Any> Single<T>.subscribeOf(
    lifecycle: Lifecycle,
    event: Lifecycle.Event = Lifecycle.Event.ON_DESTROY,
    onSuccess: (T) -> Unit = onNextEmpty,
    onError: (Throwable) -> Unit = onErrorEmpty
): Disposable {
  val observer = LifecycleBoundObserver(event)
  val disposable = this
      .doOnDispose { lifecycle.removeObserver(observer) }
      .subscribe(onSuccess, onError)
  observer.disposable = disposable
  lifecycle.addObserver(observer)
  return disposable
}

@MainThread
fun <T : Any> Maybe<T>.subscribeOf(
    owner: LifecycleOwner,
    event: Lifecycle.Event = Lifecycle.Event.ON_DESTROY,
    onSuccess: (T) -> Unit = onNextEmpty,
    onError: (Throwable) -> Unit = onErrorEmpty,
    onComplete: () -> Unit = onCompleteEmpty
): Disposable {
  return subscribeOf(owner.lifecycle, event, onSuccess, onError, onComplete)
}

@MainThread
fun <T : Any> Maybe<T>.subscribeOf(
    lifecycle: Lifecycle,
    event: Lifecycle.Event = Lifecycle.Event.ON_DESTROY,
    onSuccess: (T) -> Unit = onNextEmpty,
    onError: (Throwable) -> Unit = onErrorEmpty,
    onComplete: () -> Unit = onCompleteEmpty
): Disposable {
  val observer = LifecycleBoundObserver(event)
  val disposable = this
      .doOnDispose { lifecycle.removeObserver(observer) }
      .subscribe(onSuccess, onError, onComplete)
  observer.disposable = disposable
  lifecycle.addObserver(observer)
  return disposable
}


@MainThread
fun Completable.subscribeOf(
    owner: LifecycleOwner,
    event: Lifecycle.Event = Lifecycle.Event.ON_DESTROY,
    onComplete: () -> Unit = onCompleteEmpty,
    onError: (Throwable) -> Unit = onErrorEmpty
): Disposable {
  return subscribeOf(owner.lifecycle, event, onComplete, onError)
}

@MainThread
fun Completable.subscribeOf(
    lifecycle: Lifecycle,
    event: Lifecycle.Event = Lifecycle.Event.ON_DESTROY,
    onComplete: () -> Unit = onCompleteEmpty,
    onError: (Throwable) -> Unit = onErrorEmpty
): Disposable {
  val observer = LifecycleBoundObserver(event)
  val disposable = this
      .doOnTerminate { lifecycle.removeObserver(observer) }
      .subscribe(onComplete, onError)
  observer.disposable = disposable
  lifecycle.addObserver(observer)
  return disposable
}


@MainThread
fun <T : Any> Flowable<T>.subscribeOf(
    viewModel: RxViewModel,
    onNext: (T) -> Unit = onNextEmpty,
    onError: (Throwable) -> Unit = onErrorEmpty,
    onComplete: () -> Unit = onCompleteEmpty
): Disposable {
  val disposable = subscribe(onNext, onError, onComplete)
  viewModel.addDisposable(disposable)
  return disposable
}

@MainThread
fun <T : Any> Observable<T>.subscribeOf(
    viewModel: RxViewModel,
    onNext: (T) -> Unit = onNextEmpty,
    onError: (Throwable) -> Unit = onErrorEmpty,
    onComplete: () -> Unit = onCompleteEmpty
): Disposable {
  val disposable = subscribe(onNext, onError, onComplete)
  viewModel.addDisposable(disposable)
  return disposable
}

@MainThread
fun <T : Any> Single<T>.subscribeOf(
    viewModel: RxViewModel,
    onSuccess: (T) -> Unit = onNextEmpty,
    onError: (Throwable) -> Unit = onErrorEmpty
): Disposable {
  val disposable = subscribe(onSuccess, onError)
  viewModel.addDisposable(disposable)
  return disposable
}

@MainThread
fun <T : Any> Maybe<T>.subscribeOf(
    viewModel: RxViewModel,
    onSuccess: (T) -> Unit = onNextEmpty,
    onError: (Throwable) -> Unit = onErrorEmpty,
    onComplete: () -> Unit = onCompleteEmpty
): Disposable {
  val disposable = subscribe(onSuccess, onError, onComplete)
  viewModel.addDisposable(disposable)
  return disposable
}

@MainThread
fun Completable.subscribeOf(
    viewModel: RxViewModel,
    onComplete: () -> Unit = onCompleteEmpty,
    onError: (Throwable) -> Unit = onErrorEmpty
): Disposable {
  val disposable = subscribe(onComplete, onError)
  viewModel.addDisposable(disposable)
  return disposable
}
