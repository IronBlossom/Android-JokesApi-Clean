## JokesApi implementation in Android

**API:** https://v2.jokeapi.dev/

# About app

This app starts with a fullscreen mode (to show traditional splash screen) showing **number of times
the app is opened**. Taking care of device rotation, system call activity re-creation. After **3
seconds** the MainActivity switches the fragment to show the list of 10 random jokes, **starting
with Christmas jokes**. **Pull to refresh** the list, but this time api is **not blacklist**
potential offensive jokes. Tap jokes to see the *id, type, setup, punchline (**if its two part
jokes, otherwise only one**), and category* in a `BottomSheetDialog` popup. Room database was an
overkill for a simple application like this but used for *further scaling up i.e. offline-first,
favourite jokes etc.* Application is focused on clean, scalable and maintainable code.

## Environment, Frameworks and Libraries

- Android Studio Arctic Fox Patch 3, kotlin version **1.5.31**,
- Android Jetpack library apis **LiveData, LifecycleEventObserver, AndroidViewModel.**
- **RetroFit2** and **Moshi** for network calls.
- Kotlin **coroutines** for asynchronous operation in synchronous, declarative code.
- **MVVM** architecture is used
- `databinding` used for less boilerplate code.
