# RxLifecycleOwner

[![CircleCI](https://circleci.com/gh/satoshun/RxLifecycleOwner/tree/master.svg?style=svg)](https://circleci.com/gh/satoshun/RxLifecycleOwner/tree/master)

RxLifecycleOwner respects android architecture components.

Android architecture components has LifeCycleOwner and ViewModel, RxLifecycleOwner control resource with it.


## Usage

with LifecycleOwner(common Activity or Fragment)

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


with RxViewModel(common ViewModel)
 
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
