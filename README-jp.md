# RxLifecycleOwner

[![CircleCI](https://circleci.com/gh/satoshun/RxLifecycleOwner/tree/master.svg?style=svg)](https://circleci.com/gh/satoshun/RxLifecycleOwner/tree/master)

RxLifecycleOwnerは[Android Architecture Components](https://developer.android.com/topic/libraries/architecture/index.html)をライフサイクルに持つストリームを作るためのライブラリです。


## Motivation

このライブラリは[RxLifeCycle](https://github.com/trello/RxLifecycle)に強くインスパイアされています。
RxLifeCycleはActivity or Fragmentなどのコンポーネントと非常に上手く動作し、メモリリークを防ぐことが出来ます。
しかし、RxLifeCycleではRxJavaの[TakeUntil](http://reactivex.io/documentation/operators/takeuntil.html)オペレータに従っているため、
cancel/disposeとは違う挙動になります。特にSingleではonError(CancellationException)がコールされるため、意識をしないと想定外の動作を引き起こす場合があります。

そこで、RxLifeCycleのように使え、AndroidのActivityとFramentのライフサイクルに従い、cancel/disposeのように動作することを目指しRxLifecycleOwnerは誕生しました。
RxLifecycleOwnerでは、Architecture Componentsが提供するLifecycleOwnerとViewModelのライフサイクルにRxのストリームのライフサイクル同期することが出来ます。
より具体的には、Activityが再生成される度(configuration change)にcreate/disposeをしたいのであれば、LifecycleOwnerに従うようにストリームを定義し、
Activityのconfiguration changeのタイミングでdisposeしたくないのであればViewModelに従うようにストリームを定義することが出来ます。


## Usage

ActivityまたはFragmentにライフサイクルを合わせたい場合(LifecycleOwnerを実装しているコンポーネント)。

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


ViewModelにライフサイクルを合わせたい場合

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

not published


## License

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
