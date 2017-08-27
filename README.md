# RxLifecycleOwner

[![CircleCI](https://circleci.com/gh/satoshun/RxLifecycleOwner/tree/master.svg?style=svg)](https://circleci.com/gh/satoshun/RxLifecycleOwner/tree/master)

RxLifecycleOwner respects [Android Architecture Components](https://developer.android.com/topic/libraries/architecture/index.html). RxJava Stream can follow to architecture Component lifecycle(like a Activity, Fragment or ViewModel).


## Motivation

RxLifecycleOwner inspired a [RxLifeCycle](https://github.com/trello/RxLifecycle).
RxLifeCycle works to lifecycle of Activity and Fragment and prevents memory leak.
But, RxLifeCycle respects [TakeUntil](http://reactivex.io/documentation/operators/takeuntil.html) operator. So it will behave defferently from cancel/dispose. Especially, Single + TakeUntil will call onError(CancellationException). Sometimes it's a strange behavior.

So RxLifecycleOwner will aim that

- can be used like a RxLifeCycle
- respects a lifecycle of Activity and Fragment
- performs like a cancel/dispose

RxLifecycleOwner achieved it that follow a Architecture Components!

- follow a LifecycleOwner if want to call create/dispose when onCreated(each configuration change) Activity or Fragment.
- follow a ViewModel if want to call create/dispose when onCleared(completely finished) Activity or Fragment.


## Usage

follow a Activity or Fragment(call cancel/dispose when configuration changed)

```kotlin
Observable.just("1", "2")
    .delay(5, TimeUnit.SECONDS)
    .doOnDispose {
      Log.d("call doOnDispose", "done")
    }
    .subscribeOf(this, { // `this` implements LifecycleOwner
      Log.d("call onNext", it)
    })
```


follow a ViewModel(no call cancel/dispose when configuration changed)

```kotlin
Observable.just("1", "2")
    .delay(5, TimeUnit.SECONDS)
    .doOnDispose {
      Log.d("call doOnDispose", "done")
    }
    .subscribeOf(this, { // `this` extends RxViewModel(like a ViewModel)
      Log.d("call onNext", it)
    })
```
