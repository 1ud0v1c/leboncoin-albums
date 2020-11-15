<h1 align="center">Leboncoin - Albums</h1>

<p align="center">
  <a href="https://android-arsenal.com/api?level=19"><img alt="API" src="https://img.shields.io/badge/API-19%2B-brightgreen.svg?style=flat"/></a>
</p>

Request, parsing & persistence of a [Json flux](https://static.leboncoin.fr/img/shared/technical-test.json) to display a list of albums using clean architecture &amp; MVVM pattern.

<div align="center">

![Application icon launcher](https://github.com/1ud0v1c/leboncoin-albums/blob/main/images/icon-launcher.png)

</div>

## Design

The main inspiration for my application comes from the design of [Shakib Ali](https://www.behance.net/Shakibali) intituled [Workout & Fitness App](https://www.behance.net/gallery/101104737/Workout-Fitness-App?tracking_source=search_projects_recommended%7Capplication%20mobile%20gallery).
I really liked the listing page so I tried to reproduce it.

 I also had inspiration from the two following designs :
- [Planet of events design mobile app](https://www.behance.net/gallery/95407439/Planet-of-events-design-mobile-app-UXUI?tracking_source=search_projects_recommended%7Capplication%20mobile%20gallery) from [Vadim Bondarenko](https://www.behance.net/Despro) and [Yakovlevv Design](https://www.behance.net/yakovlevv).
- [Culttrip](https://www.behance.net/gallery/88746691/Culttrip?tracking_source=search_projects_recommended%7Capplication%20mobile%20gallery) from [UGEM Design](https://www.behance.net/ugem)

To be able to provide a great user experience when loading or when an error occurred while fetching the data, I browsed some really great projects, for example :
- [Error Illustrations - Empty States Vol 02](https://www.behance.net/gallery/57693817/Error-Illustrations-Empty-States-Vol-02?tracking_source=search_projects_recommended%7CAndroid%20empty%20state) from [Nimasha Perera](https://www.behance.net/nimashasperera)
- [DAY 62-empty state](https://www.behance.net/gallery/53698651/DAY-62-empty-state) from [Shangnan Zhang](https://www.behance.net/Zhangshangnan)

To find the logo to display the current state of my adapter list, I used the website [Undraw](https://undraw.co/).

Last, but not least I have found my launcher icon on [pixabay](https://pixabay.com/illustrations/gallery-image-icon-album-3381283/).


## Implementation

For, my implementation I used one single Activity with multiple fragments. To go trough the fragment I used the [navigation library](https://developer.android.com/guide/navigation/navigation-getting-started) of
Jetpack. We have two fragments :
- ListAlbumFragment, which correspond to the current list of albums
- DetailAlbumFragment, which is the detail of a specific album that we can reach by clicking on an item of the previous Fragment

### ListAlbumFragment

For the ListAlbumFragment, I tried to provide a nice & efficient user experience, thanks to different states. As soon as, you launch the application a loading screen will appear to help the user to be patient, If you
launch the application without having internet, if you have a timeout or a server issue. You will end with a screen which gives you a way to relaunch the request.

<div align="center">

![Home loading screen](https://github.com/1ud0v1c/leboncoin-albums/blob/main/images/list_fragment_loading.png)
![Home error](https://github.com/1ud0v1c/leboncoin-albums/blob/main/images/list_fragment_error.png)

</div>

If you have internet and the server response well, you will have a fadeOut animation hiding the loading screen and thus reveal the list of images.

<div align="center">

![Home listing](https://github.com/1ud0v1c/leboncoin-albums/blob/main/images/list_fragment_success.png)

</div>

The loading of the items is progressive, I load the data by a batch of 15 items and as soon as you reach a certain amount of items the next batch is fetched. The progression and scrolling position are saved by using
the [SavedStateHandle class](https://developer.android.com/topic/libraries/architecture/viewmodel-savedstate).


### DetailAlbumFragment

As soon as you clicked on an item for the previous list you end in the DetailAlbumFragment, which display the detail about the Album.

<div align="center">

![Detail album fragment](https://github.com/1ud0v1c/leboncoin-albums/blob/main/images/detail_fragment.png)

</div>

In the same way of the ListAlbumFragment, we save the albumId with a SavedStateHandle to be able to recover inside the DetailAlbumFragment with the good item if the application has been killed in background.


## Architecture

<div align="center">

![Architecture](https://github.com/1ud0v1c/leboncoin-albums/blob/main/images/architecture.png)

</div>

For this test, I tried to use a [clean architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html) following [this example](https://fernandocejas.com/2018/05/07/architecting-android-reloaded/)
and [this article](https://medium.com/blablacar/the-more-devs-the-merrier-part-1-e3fd041c0a10). The main objective is to be able to have a great separation of concerns and thus improve the testability of the code.
To do so, I have divided this project in 3 layers :

- **Domain Layer**: The domain package, which is responsible for handling pure Business Logic and defining Entities representing our business models.
- **Data Layer**: The Data modules will implement the interfaces defined in the Domain layer.
- **Presentation Layer**: The presentation module which contains our activities/fragments. For the presentation layer I used an [MVVM architecture](https://developer.android.com/jetpack/guide).


## External dependencies

- [Retrofit](https://github.com/square/retrofit): A type-safe HTTP client for Android. I used a moshi converter. I tend to use [moshi](https://github.com/square/moshi) over gson or jackson
since I saw [that talk](https://www.youtube.com/watch?time_continue=2526&v=1PwdqkKDCSo&feature=emb_logo). Moshi seems to better handle accent and error than Gson and is much smaller than Jackson.
- For my database, I chose [Room](https://developer.android.com/topic/libraries/architecture/room) for the efficiency to handle entities and database access.
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html): Light-weight thread implementation. I like the readability and the simplicity of coroutine.
- To load images, I picked [Coil](https://github.com/coil-kt/coil), which released the 1.0 recently. I liked it, because the library is smaller than Glide & Picasso & the library is backed by Kotlin Coroutines.
- [Koin](https://github.com/InsertKoinIO/koin): I used Koin for dependency injection.
- I used [Barista](https://github.com/AdevintaSpain/Barista) to ease my UI test writing.


## What went wrong during the test

- I had issues with Android KitKat, the library OkHttp [dropped the support of Android 4.4](https://medium.com/square-corner-blog/okhttp-3-13-requires-android-5-818bb78d07ce) in the latest version. So to be able to
have Retrofit working I needed to downgrade & force the version of the OkHttp library.
- I encounter some issues while testing the ViewModels classes, I tried to make an implementation based on [Mockito](https://site.mockito.org/). The result was working, but the tests were flaky, they were failing half the
time. The solution was pretty simple, by adding the CoroutineDispatcher used by the ViewModel in the constructor, I was able to use my own Dispatcher for the test and thus succeed to execute more easily and remove
Mockito.
- I was using Koin 2.2.0 in the project. This version has [been released](https://medium.com/koin-developers/whats-next-with-koin-2-2-3-0-releases-6c5464ae5e3d) the 13 October, I tried to add a SavedStateHandle to my
ViewModel using the new syntax (they advise to use get() as usual), but I encountered [an exception](https://www.google.com/search?client=firefox-b-d&q=%22No+definition+found+for+class%3A%27androidx.lifecycle.SavedStateHandle%27.%22)
by doing so. So I downgraded to the [version to the 2.1.6](https://medium.com/koin-developers/unboxing-koin-2-1-7f1133ebb790) where the syntax is a bit different, but works pretty well !

## Interesting links

During the test, I read several interesting things like :

- The [next release](https://medium.com/androiddevelopers/restore-recyclerview-scroll-position-a8fbdc9a9334) of the RecyclerView library (1.2.0-alpha6 for now) will be able to restore the scrolling position directly,
no need to handle it by ourselves.
- An [article](https://dev.to/adevintaspain/making-android-ui-testing-enjoyable-3a1n) by the developer of Barista to explain his adventure with Android UI tests.
