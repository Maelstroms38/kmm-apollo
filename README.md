# Multiplatform Mobile Apps

## Introduction

Welcome to Multiplatform Mobile Apps!

Kotlin Multiplatform allows developers to save time by writing the business logic for their iOS and Android apps just once, in pure Kotlin. Kotlin compiles an embedded framework that is shared between iOS and Android apps. Developers can now write and reuse code that is shared across platforms.

In this chapter, we dive into the reasons you may want to consider using Kotlin Multiplatform. 

Let's start with an overview of the technologies applied throughout this course.

## Course Overview

![Course Overview](https://i.imgur.com/o27vuXF.png)

In this course we create a GraphQL server and shared repository in pure Kotlin. The course code covers the GraphQL server and client-side repository.

The final chapter provides an overview of Jetpack Compose and SwiftUI, along with the complete example app to reference for the final project. 

### 1. Ktor GraphQL Server

- [Ktor](https://ktor.io/)
	- Build a GraphQL API server with Ktor, an asynchronous Kotlin framework for creating microservices
- [KGraphQL](https://kgraphql.io/)
	- KGraphQL is a Kotlin implementation of GraphQL. It provides a rich DSL to set up the GraphQL schema
- [KMongo](https://litote.org/kmongo/)
	- KMongo is a Kotlin toolkit for Mongo
- [Heroku](https://www.heroku.com/)
	- Build data-driven apps with fully managed data services

### 2. Kotlin Multiplatform Mobile

- [Shared Repository](https://kotlinlang.org/docs/mobile/discover-kmm-project.html)
	- Build a Kotlin module that contains common logic for both Android and iOS applications
- [Apollo Client](https://www.apollographql.com/docs/android/essentials/get-started-multiplatform/)
	- Kotlin multiplatform allows you to use the same queries and generated models on both Android and iOS.
- [SQLDelight](https://cashapp.github.io/sqldelight/)
	- SQLDelight generates typesafe kotlin APIs from your SQL statements

After creating the server and shared repository you have a chance to create the Android and iOS apps using the following UI frameworks:

#### Android App

- [Android](https://www.android.com/)
	- Build the client-side Android application for our project with Kotlin
- [Jetpack Compose](https://developer.android.com/jetpack/compose)
	- Jetpack Compose is Android's modern toolkit for building native UI
- [Gradle Dependency Manager](https://docs.gradle.org/current/userguide/dependency_management.html)
	- Gradle handles declaring, resolving and using dependencies required by the project in an automated fashion.

#### iOS App

- [iOS](https://developer.apple.com/swift/)
	- Build the client-side iOS application for our project with Swift
- [SwiftUI](https://developer.apple.com/xcode/swiftui/)
	- SwiftUI is an innovative, exceptionally simple way to build user interfaces across all Apple platforms with the power of Swift
- [Swift Package Manager](https://swift.org/package-manager/)
	- The Swift Package Manager is a tool for managing the distribution of Swift code.

## What is KMM?

Kotlin Multiplatform, or KMM allows developers to create cross-platform mobile applications using shared business logic. Under the hood, Kotlin compiles a shared framework that is embedded with native iOS and Android applications. 

![KMM](https://i.imgur.com/2CtG9QQ.jpg)

KMM is designed to handle core business logic in Kotlin such as client-side networking, caching and persistence.

In short, KMM will handle any logic that does not involve displaying data. The benefit of this approach is that you can reuse code, save time, and still leverage the latest iOS and Android technologies.

### Why not {this} framework?

Some of you may be wondering if there are advantages to using KMM against other cross-platform frameworks, such as React Native or Flutter.

Cross-platform frameworks have gained popularity in recent years, so it is important to keep in mind that there are benefits to using each one. Cross-platform development can save you time and money but can cost you both in the long term. Let's take a look at two popular frameworks and their tradeoffs:

#### React Native :atom_symbol: 

Pros :heavy_check_mark: 

- Compiles iOS and Android apps using JavaScript or TypeScript.
- With Expo, you need not use an IDE like Xcode or Android Studio.

Cons :x: 

- The framework lags behind iOS and Android releases.
- Debugging can be a nightmare expecially on Android.

#### Flutter :butterfly: 

Pros :heavy_check_mark:

- Compiles iOS and Android apps using Dart.
- Has a full set of widgets in Google’s Material Design and in Apple’s style with the Cupertino pack.

Cons :x:

- Offers no support for Android TV and Apple TV.
- Still lags when compared to native development.

Cross-platform frameworks offer code reusability, but suffer from platform reliability issues. These frameworks cannot utilize the latest development tools such as Jetpack Compose or SwiftUI. Currently, these frameworks offer a *temporary solution* to writing cross-platform apps.

For a more reliable solution, let's discuss Kotlin Multiplatform.

## Why KMM?

![KMM Scorecard](https://i.imgur.com/1mhebUf.png)

> [Source: touchlab.co](https://touchlab.co/kotlin-multiplatform-cant-do-it-all/) 

What tradeoffs does KMM offer?

Pros :heavy_check_mark:

- Native performance and flexibility, along with code reusability. 
- Platform-specific code that *just works*.
- No restrictions on your app's UI, so you can build with SwiftUI, Jetpack Compose, etc. 

Cons :x:

- If you have little to no experience with Kotlin or Swift, there is a learning curve (see below).
- Multiplatform projects are currently in [alpha](https://kotlinlang.org/docs/reference/evolution/components-stability.html). Language features and tooling may change in future Kotlin versions.

## Kotlin and Swift Review

Before diving into the course material, we recommend looking at the following documentation for Kotlin and Swift syntax.

### Kotlin Appendix

- [Scope Functions](https://kotlinlang.org/docs/reference/scope-functions.html)
- [Visibility Modifiers](https://kotlinlang.org/docs/reference/visibility-modifiers.html)
- [Data Classes](https://kotlinlang.org/docs/reference/data-classes.html)
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines/basics.html)

### Swift Appendix

- [Optional Chaining](https://docs.swift.org/swift-book/LanguageGuide/OptionalChaining.html)
- [Access Control](https://docs.swift.org/swift-book/LanguageGuide/AccessControl.html)
- [Structures and Classes](https://docs.swift.org/swift-book/LanguageGuide/ClassesAndStructures.html)
- [Automatic Reference Counting](https://docs.swift.org/swift-book/LanguageGuide/AutomaticReferenceCounting.html)

## Installation

Visit [https://kotlinlang.org/lp/mobile/ecosystem/](https://kotlinlang.org/lp/mobile/ecosystem/) for an overview of the course tooling. 

We will be using **two different IDEs**: 

1. IntelliJ IDEA 
2. Android Studio Preview (Arctic Fox).

### iOS Hardware Requirements

In order to develop iOS applications, you will need a computer running Mac OS and Xcode 12+. Xcode is *not required* for this course. The code we write is pure Kotlin and you have the option to write Swift for the final project.

### Software Requirements 

In order to get started, download the following IDEs:

- [IntelliJ IDEA](https://www.jetbrains.com/idea/)
  - For IntelliJ, you will also need to install the [Ktor plugin](https://ktor.io/docs/intellij-idea.html#installing-the-plugin)
- [Android Studio Preview](https://developer.android.com/studio/preview) (Canary Build - Arctic Fox)
  - For Android Studio, you will also need to install the [Kotlin plugin](https://plugins.jetbrains.com/plugin/6954-kotlin)
  - For Android Studio, you will also need to install the [KMM plugin](https://plugins.jetbrains.com/plugin/14936-kotlin-multiplatform-mobile?_ga=2.263680905.421262346.1609783109-1907221068.1608841954)
- [Xcode 12+](https://developer.apple.com/xcode/) (Mac OS only)

Ready? Let's get started.

## References

- [Kotlin Multiplatform](https://kotlinlang.org/docs/reference/mpp-intro.html)
- [Multiplatform programming](https://kotlinlang.org/docs/reference/multiplatform.html)
- [Start with KMM](https://kotlinlang.org/docs/mobile/getting-started.html)
- [Setup KMM](https://kotlinlang.org/docs/mobile/setup.html)