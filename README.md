# RxLifecycleOwner

[![CircleCI](https://circleci.com/gh/satoshun/RxLifecycleOwner/tree/master.svg?style=svg)](https://circleci.com/gh/satoshun/RxLifecycleOwner/tree/master) [![](https://jitpack.io/v/satoshun/RxLifecycleOwner.svg)](https://jitpack.io/#satoshun/RxLifecycleOwner)

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
    .subscribeOf(this, { // `this` implements LifecycleOwner like a Activity or Fragment
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


## Installation

```gradle
maven { url 'https://jitpack.io' }

implementation 'com.github.satoshun:RxLifecycleOwner:0.1.0'
```


## License

```
Copyright (C) 2017 SatoShun

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
