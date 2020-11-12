<h1 align="center">Leboncoin - Albums</h1>

<p align="center">
  <a href="https://android-arsenal.com/api?level=19"><img alt="API" src="https://img.shields.io/badge/API-19%2B-brightgreen.svg?style=flat"/></a>
</p>

Parsing of a Json flux to display a list of albums using clean architecture &amp; MVVM


## Design

The main inspiration for my application comes from the design of [Shakib Ali](https://www.behance.net/Shakibali) intituled [Workout & Fitness App](/101104737/Workout-Fitness-App?tracking_source=search_projects_recommended|application mobile gallery).
I really liked the listing page so I tried to reproduce it.
 
 I also had inspiration from the two following designs :
- [Planet of events design mobile app](https://www.behance.net/gallery/95407439/Planet-of-events-design-mobile-app-UXUI?tracking_source=search_projects_recommended%7Capplication%20mobile%20gallery) from [Vadim Bondarenko](https://www.behance.net/Despro) and [Yakovlevv Design](https://www.behance.net/yakovlevv).
- [Culttrip](https://www.behance.net/gallery/88746691/Culttrip?tracking_source=search_projects_recommended%7Capplication%20mobile%20gallery) from [UGEM Design](https://www.behance.net/ugem)

To be able to provide a great user experience when loading or when an error occurred while fetching the data, I browsed some really great projects, for example :
- [Error Illustrations - Empty States Vol 02](https://www.behance.net/gallery/57693817/Error-Illustrations-Empty-States-Vol-02?tracking_source=search_projects_recommended%7CAndroid%20empty%20state) from [Nimasha Perera](https://www.behance.net/nimashasperera)
- [DAY 62-empty state](https://www.behance.net/gallery/53698651/DAY-62-empty-state) from [Shangnan Zhang](https://www.behance.net/Zhangshangnan)

To find the logo to display the current state of my adapter list, I used the website [Undraw](https://undraw.co/).


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
