# yarc (yet another reddit client)

yarc (yet another reddit client) is a simple Android app created to showcase some Android technologies. 
The app contains two screens: the first screen contains a list of posts from the [android community](https://www.reddit.com/r/androiddev/) subreddit
and the second contains the selected post details.


# Language and libraries used
- Kotlin
- Android Support
- Android Material
- OkHttp
- Retrofit
- Koin
- Kotlin Serialization
- Coroutines
- Flow
- Paging 3
- View Binding
- Glide
- Navigation
- Groupie
- Markon
- Mockk
- Truth
- JUnit5
- MockWebServer
- Espresso


# Architecture

The application follows [Google's recommended architecture](https://developer.android.com/jetpack/guide). The main reason to follow this model instead of [Uncle Bob's
Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html) is to avoid some unnecessary complexity from adding a domain layer, 
which doesn't make sense in this application context, for two reasons:

1. The domain layer will only act as a proxy, not containing meaningful code;
2. Less code, less bugs, less effort to fix the bugs that will emerge.


# Requirements

- JDK 11
- Android SDK
- Android 9 or greater


# Setup

Execute the following commands in project root:

`./gradlew installDebug` - Install the debug apk on the current connected device

`./gradlew test` - Run all unit tests

`./gradlew connectedAndroidTest` - Run all instrumented tests


# Showcase

## Light theme
https://user-images.githubusercontent.com/11521746/117085938-90374780-ad21-11eb-8101-a78d7cb1746a.mp4

## Dark theme
https://user-images.githubusercontent.com/11521746/117085970-a5ac7180-ad21-11eb-8f67-73b0ab2ccac6.mp4
