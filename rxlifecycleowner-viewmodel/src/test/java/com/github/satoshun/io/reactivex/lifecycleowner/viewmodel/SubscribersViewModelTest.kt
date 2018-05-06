package com.github.satoshun.io.reactivex.lifecycleowner.viewmodel

import com.google.common.truth.Truth
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import java.util.concurrent.TimeUnit

private val onNextError: (Any) -> Unit = { throw IllegalArgumentException() }
private val onCompleteError: () -> Unit = { throw IllegalArgumentException() }

class SubscribersViewModelTest {

  private lateinit var viewModel: CompositeDisposableViewModel

  @Before
  fun setUp() {
    viewModel = CompositeDisposableViewModel()
  }

  @Test
  fun subscribeOfFlowable__cancelWhenOncleared() {
    val disposable = Flowable.just("fake")
        .delay(1, TimeUnit.DAYS)
        .subscribeOf(viewModel, onNext = onNextError)
    Truth.assertThat(disposable.isDisposed).isFalse()

    viewModel.onCleared()

    Truth.assertThat(disposable.isDisposed).isTrue()
  }

  @Test
  fun subscribeOfObservable__disposeWhenOncleared() {
    val disposable = Observable.just("fake")
        .delay(1, TimeUnit.DAYS)
        .subscribeOf(viewModel, onNext = onNextError)
    Truth.assertThat(disposable.isDisposed).isFalse()

    viewModel.onCleared()

    Truth.assertThat(disposable.isDisposed).isTrue()
  }

  @Test
  fun subscribeOfSingle__disposeWhenOncleared() {
    val disposable = Single.just("fake")
        .delay(1, TimeUnit.DAYS)
        .subscribeOf(viewModel)
    Truth.assertThat(disposable.isDisposed).isFalse()

    viewModel.onCleared()

    Truth.assertThat(disposable.isDisposed).isTrue()
  }

  @Test
  fun subscribeOfMaybe__disposeWhenOncleared() {
    val disposable = Maybe.just("fake")
        .delay(1, TimeUnit.DAYS)
        .subscribeOf(viewModel, onSuccess = onNextError)
    Truth.assertThat(disposable.isDisposed).isFalse()

    viewModel.onCleared()

    Truth.assertThat(disposable.isDisposed).isTrue()
  }

  @Test
  fun subscribeOfCompletable__disposeWhenOncleared() {
    val disposable = Completable.complete()
        .delay(1, TimeUnit.DAYS)
        .subscribeOf(viewModel, onComplete = onCompleteError)
    Truth.assertThat(disposable.isDisposed).isFalse()

    viewModel.onCleared()

    Truth.assertThat(disposable.isDisposed).isTrue()
  }
}
