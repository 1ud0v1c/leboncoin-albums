<h1 align="center">Leboncoin - Albums</h1>

<p align="center">
  <a href="https://android-arsenal.com/api?level=19"><img alt="API" src="https://img.shields.io/badge/API-19%2B-brightgreen.svg?style=flat"/></a>
</p>

Parsing of a Json flux to display a list of albums using clean architecture &amp; MVVM


## Architecture

For this test, I tried to use a [clean architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html) following [this example](https://fernandocejas.com/2018/05/07/architecting-android-reloaded/).
The main objective is to be able to have a great separation of concerns and thus improve the testability of the code. To do so, I have divided this project in 3 layers :

- **Domain Layer**: The domain package, which is responsible for handling pure Business Logic and defining Entities representing our business models.
- **Data Layer**: The Data modules will implement the interfaces defined in the Domain layer.
- **Presentation Layer**: The presentation module will contain our activities/fragments. For the presentation layer I used an [MVVM architecture](https://developer.android.com/jetpack/guide).


## External dependencies

- [Retrofit](https://github.com/square/retrofit): A type-safe HTTP client for Android. I used a moshi converter. I tend to use [moshi](https://github.com/square/moshi) over gson or jackson
since I saw [that talk](https://www.youtube.com/watch?time_continue=2526&v=1PwdqkKDCSo&feature=emb_logo). Moshi seems to better handle accent and error than Gson and is much smaller than Jackson.
- For my database, I chose [Room](https://developer.android.com/topic/libraries/architecture/room) for the efficiency to handle entities and database access.
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html): Light-weight thread implementation. I like the readability and the simplicity of coroutine.
- To load image, I picked [Coil](https://github.com/coil-kt/coil), which released the 1.0 recently. I liked it, because the library is smaller than Glide & Picasso & the library is backed by Kotlin Coroutines.
- [Koin](https://github.com/InsertKoinIO/koin): I used Koin for dependency injection.
