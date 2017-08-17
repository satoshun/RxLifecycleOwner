package com.github.satoshun.io.reativex.lifecycleowner

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
  fun subscribeByFlowable__disposeWhenDestroyed() {
    val disposable = Flowable.just("fake")
        .delay(1, TimeUnit.DAYS)
        .subscribeBy(owner)
    Truth.assertThat(disposable.isDisposed).isFalse()

    owner.registry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)

    Truth.assertThat(disposable.isDisposed).isTrue()
  }

  @Test
  fun subscribeByFlowable__nodisposeWhenResumed() {
    val disposable = Flowable.just("fake")
        .delay(1, TimeUnit.DAYS)
        .subscribeBy(owner)
    Truth.assertThat(disposable.isDisposed).isFalse()

    owner.registry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)

    Truth.assertThat(disposable.isDisposed).isFalse()
  }

  @Test
  fun subscribeByObservable__disposeWhenDestroyed() {
    val disposable = Observable.just("fake")
        .delay(1, TimeUnit.DAYS)
        .subscribeBy(owner)
    Truth.assertThat(disposable.isDisposed).isFalse()

    owner.registry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)

    Truth.assertThat(disposable.isDisposed).isTrue()
  }

  @Test
  fun subscribeByObservable__nodisposeWhenResumed() {
    val disposable = Observable.just("fake")
        .delay(1, TimeUnit.DAYS)
        .subscribeBy(owner)
    Truth.assertThat(disposable.isDisposed).isFalse()

    owner.registry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)

    Truth.assertThat(disposable.isDisposed).isFalse()
  }

  @Test
  fun subscribeBySingle__disposeWhenDestroyed() {
    val disposable = Single.just("fake")
        .delay(1, TimeUnit.DAYS)
        .subscribeBy(owner)
    Truth.assertThat(disposable.isDisposed).isFalse()

    owner.registry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)

    Truth.assertThat(disposable.isDisposed).isTrue()
  }

  @Test
  fun subscribeBySingle__nodisposeWhenResumed() {
    val disposable = Single.just("fake")
        .delay(1, TimeUnit.DAYS)
        .subscribeBy(owner)
    Truth.assertThat(disposable.isDisposed).isFalse()

    owner.registry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)

    Truth.assertThat(disposable.isDisposed).isFalse()
  }

  @Test
  fun subscribeByMaybe__disposeWhenDestroyed() {
    val disposable = Maybe.just("fake")
        .delay(1, TimeUnit.DAYS)
        .subscribeBy(owner)
    Truth.assertThat(disposable.isDisposed).isFalse()

    owner.registry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)

    Truth.assertThat(disposable.isDisposed).isTrue()
  }

  @Test
  fun subscribeByMaybe__nodisposeWhenResumed() {
    val disposable = Maybe.just("fake")
        .delay(1, TimeUnit.DAYS)
        .subscribeBy(owner)
    Truth.assertThat(disposable.isDisposed).isFalse()

    owner.registry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)

    Truth.assertThat(disposable.isDisposed).isFalse()
  }

  @Test
  fun subscribeByCompletable__disposeWhenDestroyed() {
    val disposable = Completable.complete()
        .delay(1, TimeUnit.DAYS)
        .subscribeBy(owner)
    Truth.assertThat(disposable.isDisposed).isFalse()

    owner.registry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)

    Truth.assertThat(disposable.isDisposed).isTrue()
  }

  @Test
  fun subscribeByCompletable__nodisposeWhenResumed() {
    val disposable = Completable.complete()
        .delay(1, TimeUnit.DAYS)
        .subscribeBy(owner)
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
