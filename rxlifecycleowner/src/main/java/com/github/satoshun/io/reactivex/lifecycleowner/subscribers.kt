package com.github.satoshun.io.reactivex.lifecycleowner

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.support.annotation.MainThread
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.exceptions.OnErrorNotImplementedException

private val onNextEmpty: (Any) -> Unit = {}
private val onErrorEmpty: (Throwable) -> Unit = { throw OnErrorNotImplementedException(it) }
private val onCompleteEmpty: () -> Unit = {}

/**
 * Overloaded subscribe function and works with LifecycleOwner. default [Lifecycle.Event] is
 * [Lifecycle.Event.ON_DESTROY].
 */
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

/**
 * Overloaded subscribe function and works with Lifecycle. default [Lifecycle.Event] is
 * [Lifecycle.Event.ON_DESTROY].
 */
@MainThread
fun <T : Any> Flowable<T>.subscribeOf(
  lifecycle: Lifecycle,
  event: Lifecycle.Event = Lifecycle.Event.ON_DESTROY,
  onNext: (T) -> Unit = onNextEmpty,
  onError: (Throwable) -> Unit = onErrorEmpty,
  onComplete: () -> Unit = onCompleteEmpty
): Disposable =
  lifecycleBoundObserver(lifecycle, event) { observer ->
    this
        .doOnTerminate { lifecycle.removeObserver(observer) }
        .subscribe(onNext, onError, onComplete)
  }

/**
 * Overloaded subscribe function and works with LifecycleOwner. default [Lifecycle.Event] is
 * [Lifecycle.Event.ON_DESTROY].
 */
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

/**
 * Overloaded subscribe function and works with Lifecycle. default [Lifecycle.Event] is
 * [Lifecycle.Event.ON_DESTROY].
 */
@MainThread
fun <T : Any> Observable<T>.subscribeOf(
  lifecycle: Lifecycle,
  event: Lifecycle.Event = Lifecycle.Event.ON_DESTROY,
  onNext: (T) -> Unit = onNextEmpty,
  onError: (Throwable) -> Unit = onErrorEmpty,
  onComplete: () -> Unit = onCompleteEmpty
): Disposable =
  lifecycleBoundObserver(lifecycle, event) { observer ->
    this
        .doOnTerminate { lifecycle.removeObserver(observer) }
        .subscribe(onNext, onError, onComplete)
  }

/**
 * Overloaded subscribe function and works with LifecycleOwner. default [Lifecycle.Event] is
 * [Lifecycle.Event.ON_DESTROY].
 */
@MainThread
fun <T : Any> Single<T>.subscribeOf(
  owner: LifecycleOwner,
  event: Lifecycle.Event = Lifecycle.Event.ON_DESTROY,
  onSuccess: (T) -> Unit = onNextEmpty,
  onError: (Throwable) -> Unit = onErrorEmpty
): Disposable {
  return subscribeOf(owner.lifecycle, event, onSuccess, onError)
}

/**
 * Overloaded subscribe function and works with Lifecycle. default [Lifecycle.Event] is
 * [Lifecycle.Event.ON_DESTROY].
 */
@MainThread
fun <T : Any> Single<T>.subscribeOf(
  lifecycle: Lifecycle,
  event: Lifecycle.Event = Lifecycle.Event.ON_DESTROY,
  onSuccess: (T) -> Unit = onNextEmpty,
  onError: (Throwable) -> Unit = onErrorEmpty
): Disposable =
  lifecycleBoundObserver(lifecycle, event) { observer ->
    this
        .doOnDispose { lifecycle.removeObserver(observer) }
        .subscribe(onSuccess, onError)
  }

/**
 * Overloaded subscribe function and works with LifecycleOwner. default [Lifecycle.Event] is
 * [Lifecycle.Event.ON_DESTROY].
 */
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

/**
 * Overloaded subscribe function and works with Lifecycle. default [Lifecycle.Event] is
 * [Lifecycle.Event.ON_DESTROY].
 */
@MainThread
fun <T : Any> Maybe<T>.subscribeOf(
  lifecycle: Lifecycle,
  event: Lifecycle.Event = Lifecycle.Event.ON_DESTROY,
  onSuccess: (T) -> Unit = onNextEmpty,
  onError: (Throwable) -> Unit = onErrorEmpty,
  onComplete: () -> Unit = onCompleteEmpty
): Disposable =
  lifecycleBoundObserver(lifecycle, event) { observer ->
    this
        .doOnDispose { lifecycle.removeObserver(observer) }
        .subscribe(onSuccess, onError, onComplete)
  }

/**
 * Overloaded subscribe function and works with LifecycleOwner. default [Lifecycle.Event] is
 * [Lifecycle.Event.ON_DESTROY].
 */
@MainThread
fun Completable.subscribeOf(
  owner: LifecycleOwner,
  event: Lifecycle.Event = Lifecycle.Event.ON_DESTROY,
  onComplete: () -> Unit = onCompleteEmpty,
  onError: (Throwable) -> Unit = onErrorEmpty
): Disposable {
  return subscribeOf(owner.lifecycle, event, onComplete, onError)
}

/**
 * Overloaded subscribe function and works with Lifecycle. default [Lifecycle.Event] is
 * [Lifecycle.Event.ON_DESTROY].
 */
@MainThread
fun Completable.subscribeOf(
  lifecycle: Lifecycle,
  event: Lifecycle.Event = Lifecycle.Event.ON_DESTROY,
  onComplete: () -> Unit = onCompleteEmpty,
  onError: (Throwable) -> Unit = onErrorEmpty
): Disposable =
  lifecycleBoundObserver(lifecycle, event) { observer ->
    this
        .doOnTerminate { lifecycle.removeObserver(observer) }
        .subscribe(onComplete, onError)
  }
