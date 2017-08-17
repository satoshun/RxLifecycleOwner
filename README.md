# RxLifecycleOwner

[![CircleCI](https://circleci.com/gh/satoshun/RxLifecycleOwner/tree/master.svg?style=svg)](https://circleci.com/gh/satoshun/RxLifecycleOwner/tree/master)

RxLifecycleOwner respects android architecture lifecycle components.


## usage

```kotlin
Observable.just("1", "2")
    .delay(5, TimeUnit.SECONDS)
    .doOnDispose {
      Log.d("call doOnDispose", "done")
    }
    .subscribeOf(this, {
      Log.d("call onNext", it)
    })
```


## todo

- provides fluent api for lifecycle-aware components.
- and more
