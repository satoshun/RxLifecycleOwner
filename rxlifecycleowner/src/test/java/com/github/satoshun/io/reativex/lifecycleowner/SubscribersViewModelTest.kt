package com.github.satoshun.io.reativex.lifecycleowner

import com.google.common.truth.Truth
import io.reactivex.*
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
  fun subscribeByFlowable__cancelWhenOncleared() {
    val disposable = Flowable.just("fake")
        .delay(1, TimeUnit.DAYS)
        .subscribeBy(viewModel, onNext = onNextError)
    Truth.assertThat(disposable.isDisposed).isFalse()

    viewModel.onCleared()

    Truth.assertThat(disposable.isDisposed).isTrue()
  }

  @Test
  fun subscribeByObservable__disposeWhenOncleared() {
    val disposable = Observable.just("fake")
        .delay(1, TimeUnit.DAYS)
        .subscribeBy(viewModel, onNext = onNextError)
    Truth.assertThat(disposable.isDisposed).isFalse()

    viewModel.onCleared()

    Truth.assertThat(disposable.isDisposed).isTrue()
  }

  @Test
  fun subscribeBySingle__disposeWhenOncleared() {
    val disposable = Single.just("fake")
        .delay(1, TimeUnit.DAYS)
        .subscribeBy(viewModel)
    Truth.assertThat(disposable.isDisposed).isFalse()

    viewModel.onCleared()

    Truth.assertThat(disposable.isDisposed).isTrue()
  }

  @Test
  fun subscribeByMaybe__disposeWhenOncleared() {
    val disposable = Maybe.just("fake")
        .delay(1, TimeUnit.DAYS)
        .subscribeBy(viewModel, onSuccess = onNextError)
    Truth.assertThat(disposable.isDisposed).isFalse()

    viewModel.onCleared()

    Truth.assertThat(disposable.isDisposed).isTrue()
  }

  @Test
  fun subscribeByCompletable__disposeWhenOncleared() {
    val disposable = Completable.complete()
        .delay(1, TimeUnit.DAYS)
        .subscribeBy(viewModel, onComplete = onCompleteError)
    Truth.assertThat(disposable.isDisposed).isFalse()

    viewModel.onCleared()

    Truth.assertThat(disposable.isDisposed).isTrue()
  }
}
