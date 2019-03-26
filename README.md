# 3rd Parties
* [Dagger](https://google.github.io/dagger/)
* [RxJava](https://github.com/ReactiveX/RxJava)
* [Klaxon](https://github.com/cbeust/klaxon)
* [Picasso](https://square.github.io/picasso/)
* [Retrofit](https://square.github.io/retrofit/)
* [Mockito](https://site.mockito.org/)

# Dependencies
* For a full list of dependencies, execute ```./gradlew :app:dependencies``` from the root directory

# Tools
* [Git](http://git-scm.com/downloads)
* [Android SDK](http://developer.android.com/sdk/installing/index.html)
* [Android Studio](http://developer.android.com/sdk/installing/index.html?pkg=studio)

# Install Git
* Visit [this page](http://git-scm.com/download/mac) and download git
* Double-click the .DMG file to mount it.  A new Finder window showing its contents should appear.
* If the window also contains a shortcut icon to `Applications`, drag and drop the app onto the shortcut.
* If not, double-click the mounted volume on your desktop and drag the app icon from there to the `Applications` icon in the Finder sidebar.

# Install SDK
* Download the Android SDK from [here](http://developer.android.com/sdk/installing/index.html?pkg=tools)
* ```cd ~``` and unpack the SDK like so: ```unzip android-sdk_r21.1-macosx.zip```
* Create a symbolic link like this: ```ln -s android-sdk-macosx android```

# Set Environment
* Add ```export ANDROID_HOME=/Users/your_uname/android``` to ```~/.bash_profile```
* Add ```export PATH=$PATH:$ANDROID_HOME/tools``` to ```~/.bash_profile```
* Execute ```source ~/.bash_profile``` to reload environment variables

# Update SDK
* If you already have the SDK installed, you may want to update it like so: ```~/android/tools/android update sdk --no-ui```

# Clone the Repo
* ```cd directory_where_you_keep_code``` and ```git clone https://github.com/realtybaron/swa_challenge.git```

# Run Unit Tests
* ```cd swa_challenge```
* ```./gradlew connectedCheck```

# Build and Install the APK on a device
* ```cd swa_challenge```
* ```./gradlew clean installProductionDebug```

# Build for Distribution to the PlayStore
* ```cd swa_challenge```
* ```./gradlew clean assembleRelease```
* If successful, APK can then be found in ```./app/build/outputs/apk/production/release/app-production-release.apk```