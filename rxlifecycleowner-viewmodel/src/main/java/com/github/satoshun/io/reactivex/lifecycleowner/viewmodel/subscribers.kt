package com.github.satoshun.io.reactivex.lifecycleowner.viewmodel

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
 * Overloaded subscribe function and works with [RxViewModel]. [Disposable] will be released when
 * [RxViewModel.onCleared] is called.
 */
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

/**
 * Overloaded subscribe function and works with [RxViewModel]. [Disposable] will be released when
 * [RxViewModel.onCleared] is called.
 */
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

/**
 * Overloaded subscribe function and works with [RxViewModel]. [Disposable] will be released when
 * [RxViewModel.onCleared] is called.
 */
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

/**
 * Overloaded subscribe function and works with [RxViewModel]. [Disposable] will be released when
 * [RxViewModel.onCleared] is called.
 */
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

/**
 * Overloaded subscribe function and works with [RxViewModel]. [Disposable] will be released when
 * [RxViewModel.onCleared] is called.
 */
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
