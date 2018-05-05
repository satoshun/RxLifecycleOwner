object Vers {
  val agp = "3.1.2"

  val compile_sdk = 27
  val min_sdk = 14
  val target_sdk = 27

  val kotlin = "1.2.41"
  val couroutine = "0.22.5"
  val support_lib = "27.1.1"
}

object Libs {
  val android_plugin = "com.android.tools.build:gradle:${Vers.agp}"
  val kotlin_plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Vers.kotlin}"
  val dokka_plugin = "org.jetbrains.dokka:dokka-gradle-plugin:0.9.16"

  val kotlin = "org.jetbrains.kotlin:kotlin-stdlib:${Vers.kotlin}"

  val rx_java = "io.reactivex.rxjava2:rxjava:2.1.10"
  val rx_android = "io.reactivex.rxjava2:rxandroid:2.0.2"

  val arch_runtime = "android.arch.lifecycle:runtime:1.1.0"
  val arch_extensions = "android.arch.lifecycle:extensions:1.1.0"
  val arch_compiler = "android.arch.lifecycle:compiler:1.1.0"
  val appcompat_v7 = "com.android.support:appcompat-v7:${Vers.support_lib}"
  val support_annotations = "com.android.support:support-annotations:${Vers.support_lib}"
  val constraint_layout = "com.android.support.constraint:constraint-layout:1.1.0"

  val junit = "junit:junit:4.12"
  val support_test = "com.android.support.test:runner:1.0.1"
  val espresso = "com.android.support.test.espresso:espresso-core:3.0.1"

  val appcompat = "com.android.support:appcompat-v7:${Vers.support_lib}"
  val support_v4 = "com.android.support:support-v4:${Vers.support_lib}"

  val truth = "com.google.truth:truth:0.39"
  val mockito_kotlin = "com.nhaarman:mockito-kotlin-kt1.1:1.5.0"
  val multidex = "com.android.support:multidex:1.0.3"
}
