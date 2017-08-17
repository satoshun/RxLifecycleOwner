package com.github.satoshun.io.reactivex.lifecycleowner

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LifecycleRegistry
import com.google.common.truth.Truth
import io.reactivex.*
import org.junit.Before
import org.junit.Test
import java.util.concurrent.TimeUnit

class SubscribersLifecycleOwnerTest {

  private lateinit var owner: SimpleLifecycleOwner

  @Before
  fun setUp() {
    owner = SimpleLifecycleOwner()
  }

  @Test
  fun subscribeOfFlowable__disposeWhenDestroyed() {
    val disposable = Flowable.just("fake")
        .delay(1, TimeUnit.DAYS)
        .subscribeOf(owner)
    Truth.assertThat(disposable.isDisposed).isFalse()

    owner.registry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)

    Truth.assertThat(disposable.isDisposed).isTrue()
  }

  @Test
  fun subscribeOfFlowable__nodisposeWhenResumed() {
    val disposable = Flowable.just("fake")
        .delay(1, TimeUnit.DAYS)
        .subscribeOf(owner)
    Truth.assertThat(disposable.isDisposed).isFalse()

    owner.registry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)

    Truth.assertThat(disposable.isDisposed).isFalse()
  }

  @Test
  fun subscribeOfObservable__disposeWhenDestroyed() {
    val disposable = Observable.just("fake")
        .delay(1, TimeUnit.DAYS)
        .subscribeOf(owner)
    Truth.assertThat(disposable.isDisposed).isFalse()

    owner.registry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)

    Truth.assertThat(disposable.isDisposed).isTrue()
  }

  @Test
  fun subscribeOfObservable__nodisposeWhenResumed() {
    val disposable = Observable.just("fake")
        .delay(1, TimeUnit.DAYS)
        .subscribeOf(owner)
    Truth.assertThat(disposable.isDisposed).isFalse()

    owner.registry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)

    Truth.assertThat(disposable.isDisposed).isFalse()
  }

  @Test
  fun subscribeOfSingle__disposeWhenDestroyed() {
    val disposable = Single.just("fake")
        .delay(1, TimeUnit.DAYS)
        .subscribeOf(owner)
    Truth.assertThat(disposable.isDisposed).isFalse()

    owner.registry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)

    Truth.assertThat(disposable.isDisposed).isTrue()
  }

  @Test
  fun subscribeOfSingle__nodisposeWhenResumed() {
    val disposable = Single.just("fake")
        .delay(1, TimeUnit.DAYS)
        .subscribeOf(owner)
    Truth.assertThat(disposable.isDisposed).isFalse()

    owner.registry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)

    Truth.assertThat(disposable.isDisposed).isFalse()
  }

  @Test
  fun subscribeOfMaybe__disposeWhenDestroyed() {
    val disposable = Maybe.just("fake")
        .delay(1, TimeUnit.DAYS)
        .subscribeOf(owner)
    Truth.assertThat(disposable.isDisposed).isFalse()

    owner.registry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)

    Truth.assertThat(disposable.isDisposed).isTrue()
  }

  @Test
  fun subscribeOfMaybe__nodisposeWhenResumed() {
    val disposable = Maybe.just("fake")
        .delay(1, TimeUnit.DAYS)
        .subscribeOf(owner)
    Truth.assertThat(disposable.isDisposed).isFalse()

    owner.registry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)

    Truth.assertThat(disposable.isDisposed).isFalse()
  }

  @Test
  fun subscribeOfCompletable__disposeWhenDestroyed() {
    val disposable = Completable.complete()
        .delay(1, TimeUnit.DAYS)
        .subscribeOf(owner)
    Truth.assertThat(disposable.isDisposed).isFalse()

    owner.registry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)

    Truth.assertThat(disposable.isDisposed).isTrue()
  }

  @Test
  fun subscribeOfCompletable__nodisposeWhenResumed() {
    val disposable = Completable.complete()
        .delay(1, TimeUnit.DAYS)
        .subscribeOf(owner)
    Truth.assertThat(disposable.isDisposed).isFalse()

    owner.registry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)

    Truth.assertThat(disposable.isDisposed).isFalse()
  }
}


private class SimpleLifecycleOwner : LifecycleOwner {

  val registry = LifecycleRegistry(this)

  init {
    registry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
  }

  override fun getLifecycle(): Lifecycle {
    return registry
  }
}
