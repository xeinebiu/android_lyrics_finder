## Lyrics Finder
Find any songs lyrics from your android application

![](./docs/preview.jpg)
---
### Installation
Step 1. 
Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://www.jitpack.io' }
		}
	}
Step 2. Add the dependency

	dependencies {
            implementation 'com.github.xeinebiu:android_lyrics_finder:1.2.0'
	}


---
### Example
```kotlin
    fun search(query: String) {
        val lyrics = LyricsFinder(...args).find(query)
        findViewById<AppCompatTextView>(R.id.lyricsView).text = lyrics
    }
```

### Change logs
    1.2.0
        - Update Gradle
        - Update Kotlin
        - Convert LyricsFinder from Object to Class
    1.0.0
        - Initial Release
