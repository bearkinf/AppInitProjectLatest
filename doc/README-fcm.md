# 안드로이드 최신 버전에 따른 빌드 확인 .

## 테스트 사양

~~~
Android Studio Flamingo | 2022.2.1 Patch 1
Build #AI-222.4459.24.2221.9971841, built on April 20, 2023
Runtime version: 17.0.6+7-b469.82 x86_64
VM: OpenJDK 64-Bit Server VM by JetBrains s.r.o.
macOS 12.6.5
GC: G1 Young Generation, G1 Old Generation
Memory: 4096M
Cores: 8
Metal Rendering is ON
Registry:
external.system.auto.import.disabled=true
ide.text.editor.with.preview.show.floating.toolbar=false
gradle.version.catalogs.dynamic.support=true

Non-Bundled Plugins:
PlantUML integration (6.1.0-IJ2022.2)
~~~

## ************ 최초 프로젝트 생성. ************

### root/settings.gradle

~~~
pluginManagement {
repositories {
google()
mavenCentral()
gradlePluginPortal()
}
}
dependencyResolutionManagement {
repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
repositories {
google()
mavenCentral()
}
}
rootProject.name = "My Application-Build-test"
include ':app'

~~~

### root/build.gradle

~~~
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
id 'com.android.application' version '8.0.1' apply false
id 'com.android.library' version '8.0.1' apply false
id 'org.jetbrains.kotlin.android' version '1.8.20' apply false
}
~~~

### root/app/build.gradle

~~~
plugins {
id 'com.android.application'
id 'org.jetbrains.kotlin.android'
}

android {
namespace 'com.bearkinf.myapplication_build_test'
compileSdk 33

    defaultConfig {
        applicationId "com.bearkinf.myapplication_build_test"
        minSdk 23
        targetSdk 33
        versionCode 1
        versionName "1.0"

        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
    }
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = '1.8'
    }

}

dependencies {

    implementation 'androidx.core:core-ktx:1.8.0'
    implementation 'androidx.appcompat:appcompat:1.6.1'
    implementation 'com.google.android.material:material:1.5.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
    testImplementation 'junit:junit:4.13.2'
    androidTestImplementation 'androidx.test.ext:junit:1.1.5'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.5.1'

}
~~~

## ************ 최초 프로젝트 생성. ************

### 기본 빌드 환경
~~~
project structure
sdk Location - gralde = gradle jdk jbr-17
~~~
빌드 함........... 성공

### test 11 build
~~~
project structure
sdk Location - gralde = gradle jdk 11
빌드 함........... 성공
~~~

### test 1.8
~~~
project structure
sdk Location - gralde = gradle jdk 1.8
빌드 함........... 실패

~~~

### 호환성 문제 발생 .
~~~
> Could not resolve com.android.tools.build:gradle:8.0.1.
> Required by:
> project : > com.android.application:com.android.application.gradle.plugin:8.0.1
> project : > com.android.library:com.android.library.gradle.plugin:8.0.1
> No matching variant of com.android.tools.build:gradle:8.0.1 was found. The consumer was configured
> to find a library for use during runtime, compatible with Java 8, packaged as a jar, and its
> dependencies declared externally, as well as attribute 'org.gradle.plugin.api-version' with
> value '
> 8.0' but:

- Variant 'apiElements' capability com.android.tools.build:gradle:8.0.1 declares a library, packaged
  as a jar, and its dependencies declared externally:
- Incompatible because this component declares a component for use during compile-time, compatible
  with Java 11 and the consumer needed a component for use during runtime, compatible with Java 8
  
~~~


## 앱 파일 빌드시 파일 이름생성
~~~
applicationVariants.all { variant ->

        variant.outputs.all {
            def date = new Date()

// def formatteTimedDate = date.format('yyyyMMddHHmmss')
def formatteDaydDate = date.format('yyyyMMdd')
outputFileName = "${parent.project.getName()
}-v${variant.versionName}-${formatteDaydDate}-${variant.buildType.name}.apk"
}
}
~~~

### 앱 빌드 및 런 후 파일생성.
~~~
root/app/build/intermediates/apk/debug/생성.apk
이렇게 생성된 apk 는 디바이스에 넣어서 설치를 하지 못한다.

앱을 Rebuild project만 했을때
root/app/build/outputs/apk/debug/생성.apk
여기서 생성된 apk 는 모두 설치 된다.

릴리즈 앱 빌드 또는 런 할떄 signing key 가 없을때

buildTypes {

        // 빌드 환경을 분리 해야한다.
        release {
	//  이부분 추가. 
	 signingConfig signingConfigs.debug
	}	

}

이렇게 하면 빌드가 된다.
~~~


### 빌드 타입으로 인한 apk 생성 도움말.
~~~
https://stackoverflow.com/questions/24785270/how-to-change-app-name-per-gradle-build-type/24786371#24786371

https://kjwon15.net/wordpress/articles/computer/android/499
~~~
~~~
빌드 타입별로 이름을 다른게 한다거나 아이콘을 변경하고싶을떄 리소스파일을 수정하면된다.

root/app/src/debug/res/
root/app/src/release/res/
필요한 데이터 또는 이미지를 넣어서 변경된것을 확인.
~~~

### 빌드 타입별로 app 을 설치하고 싶을때.
~~~
buildTypes {

        // 빌드 환경을 분리 해야한다.
        debug {
	//  이부분 추가. 
	applicationIdSuffix ".debug"
	}	

}
이렇게 하면 기존 패키지 명 뒤에 .debug가 붙어서 설치된다.
~~~


## ************ 수정된 프로젝트 생성. ************
~~~

root/settings.gradle
pluginManagement {
repositories {
google()
mavenCentral()
gradlePluginPortal()
}
}
dependencyResolutionManagement {
repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
repositories {
google()
mavenCentral()
}
}
rootProject.name = "Build-test"
include ':app'

root/build.gradle
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
id 'com.android.application' version '8.0.1' apply false
id 'com.android.library' version '8.0.1' apply false
id 'org.jetbrains.kotlin.android' version '1.8.20' apply false
}

root/app/build.gradle
plugins {
id 'com.android.application'
id 'org.jetbrains.kotlin.android'
}

android {
namespace 'com.bearkinf.myapplication_build_test'
compileSdk 33

    defaultConfig {
        applicationId "com.bearkinf.myapplication_build_test"
        minSdk 23
        targetSdk 33
        versionCode 1
        versionName "1.0"

        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {

        // 빌드 환경을 분리 해야한다.
        release {
            debuggable false
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
            multiDexEnabled true
            // 이부분은 나중에 추가 수정.
            signingConfig signingConfigs.debug
        }

        debug {
            multiDexEnabled true
            debuggable true
            signingConfig signingConfigs.debug
            // 이 부분을 추가하면 release와 별개로 debug 앱이 설치된다.
            applicationIdSuffix ".debug"
        }
    }
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = '1.8'
    }

    //안드로이드 파일 이름 정하기.
    applicationVariants.all { variant ->

        variant.outputs.all {
            def date = new Date()
//            def formattedTimeDate = date.format('yyyyMMddHHmmss')
            def formattedDayDate = date.format('yyyyMMdd')
            outputFileName = "${parent.project.getName()}-v${variant.versionName}-${formattedDayDate}-${variant.buildType.name}.apk"
        }
    }



dependencies {

    implementation 'androidx.core:core-ktx:1.8.0'
    implementation 'androidx.appcompat:appcompat:1.6.1'
    implementation 'com.google.android.material:material:1.5.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
    testImplementation 'junit:junit:4.13.2'
    androidTestImplementation 'androidx.test.ext:junit:1.1.5'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.5.1'

} 
~~~


## 빌드 환경 별 속성 적용하기.

### 빌드 타입별 앱 이름 변경.
~~~
빌드 타입뼐 앱 이름 변경.   
android:label="${applicationLabel}"   
아래 둘중 하나.   
manifestPlaceholders.put("applicationLabel", "@string/app_name")   
manifestPlaceholders = [appLabel: "releaseApp"]   
~~~

### 빌드 타입별 서버 주소 변경.
~~~
buildConfigField "String", "BASE_URL", "\"https://www.daum.net\""
~~~



~~~
    buildTypes {

        // 빌드 환경을 분리 해야한다.
        release {
            debuggable false
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
            multiDexEnabled true
            // 이부분은 나중에 추가 수정.
            signingConfig signingConfigs.debug
            buildConfigField "String", "BASE_URL", "\"https://www.daum.net\""
            //빌드 타입뼐 앱 이름 변경.
            // android:label="${applicationLabel}"
//            manifestPlaceholders.put("applicationLabel", "@string/app_name")
            manifestPlaceholders = [appLabel: "releaseApp"]
        }

        debug {
            multiDexEnabled true
            debuggable true
            signingConfig signingConfigs.debug
            buildConfigField "String", "BASE_URL", '"https://www.naver.com"'

            // 이 부분을 추가하면 release와 별개로 debug 앱이 설치된다.
            // 패키지 명 맨뒤에 .debug 가 붙는다.
            applicationIdSuffix ".debug"
            //빌드 타입뼐 앱 이름 변경.
            // android:label="${applicationLabel}"
//            manifestPlaceholders.put("applicationLabel", "@string/app_name_debug")
            manifestPlaceholders = [appLabel: "DevelApp"]
        }
    }
~~~

## 수정된 빌드 그래들 

~~~
plugins {
    id 'com.android.application'
    id 'org.jetbrains.kotlin.android'
}

android {
    namespace 'com.bearkinf.myapplication_build_test'
    compileSdk 33

    defaultConfig {
        applicationId "com.bearkinf.myapplication_build_test"
        minSdk 23
        targetSdk 33
        versionCode 1
        versionName "1.0"

        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
        multiDexEnabled true
    }

    buildTypes {

        // 빌드 환경을 분리 해야한다.
        release {
            debuggable false
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
            multiDexEnabled true
            // 이부분은 나중에 추가 수정.
            signingConfig signingConfigs.debug
            buildConfigField "String", "BASE_URL", "\"https://www.daum.net\""
            //빌드 타입뼐 앱 이름 변경.
            // android:label="${applicationLabel}"
//            manifestPlaceholders.put("applicationLabel", "@string/app_name")
            manifestPlaceholders = [appLabel: "releaseApp"]
        }

        debug {
            multiDexEnabled true
            debuggable true
            signingConfig signingConfigs.debug
            buildConfigField "String", "BASE_URL", '"https://www.naver.com"'

            // 이 부분을 추가하면 release와 별개로 debug 앱이 설치된다.
            // 패키지 명 맨뒤에 .debug 가 붙는다.
            applicationIdSuffix ".debug"
            //빌드 타입뼐 앱 이름 변경.
            // android:label="${applicationLabel}"
//            manifestPlaceholders.put("applicationLabel", "@string/app_name_debug")
            manifestPlaceholders = [appLabel: "DevelApp"]
        }
    }
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = '1.8'
    }


    //안드로이드 파일 이름 정하기.
    applicationVariants.all { variant ->

        variant.outputs.all {
            def date = new Date()
//            def formattedTimeDate = date.format('yyyyMMddHHmmss')
            def formattedDayDate = date.format('yyyyMMdd')
            outputFileName = "${parent.project.getName()}-v${variant.versionName}-${formattedDayDate}-${variant.buildType.name}.apk"
        }
    }


    buildFeatures {
        // 파일 생성.
        buildConfig true

        //화면 연결을 위한 뷰바인딩 등록.
        viewBinding true
    }


}

dependencies {

    implementation 'androidx.core:core-ktx:1.8.0'
    implementation 'androidx.appcompat:appcompat:1.6.1'
    implementation 'com.google.android.material:material:1.5.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
    testImplementation 'junit:junit:4.13.2'
    androidTestImplementation 'androidx.test.ext:junit:1.1.5'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.5.1'
}
~~~


## google FCM ADD and Test
위에서 구성된 내용을 기본으로 하고 Firebase test 시작.


### Firebase console 프로젝트 생성.
위 프로젝트 패키지를 기본으로 프로젝트를 생성.
디버그용 및 릴리즈용 프로젝트 확인 및 생성.

google-services.json 다운로드 후

root/app/ vhfej 안에 복사



### 플러그인 및 구성환경 등록.
- 플러그인 구성시 패키지 명들을 다시 확인해봐야한다.
~~~
root/build.gradle
    
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id 'com.android.application' version '8.0.1' apply false
    id 'com.android.library' version '8.0.1' apply false
    id 'org.jetbrains.kotlin.android' version '1.8.20' apply false

    //Firebase Cloud Message 등록 하기.

    //아후 멀리 돌아간다..https://jgeun97.tistory.com/202
    id 'com.google.gms.google-services' version '4.3.15' apply false

    //이름 구성이 다 다르다...
    //https://stackoverflow.com/questions/72542162/unable-to-add-firebase-crashlytics-for-gradle-version-7-2
    // Add the dependency for the Crashlytics Gradle plugin
    id'com.google.firebase.crashlytics'version '2.9.5' apply false
}
~~~

~~~
root/app/build.gradle

plugins {
    id 'com.android.application'
    id 'org.jetbrains.kotlin.android'
    // Add the Google services Gradle plugin
    id 'com.google.gms.google-services'
    // Add the Crashlytics Gradle plugin
    id 'com.google.firebase.crashlytics'
}

dependencies {

    // Import the Firebase BoM
    implementation platform('com.google.firebase:firebase-bom:32.0.0')
    // TODO: Add the dependencies for Firebase products you want to use
    // When using the BoM, don't specify versions in Firebase dependencies
    implementation 'com.google.firebase:firebase-analytics-ktx'
    //fcm message
    implementation 'com.google.firebase:firebase-messaging-ktx'
    // crashlytics
    implementation 'com.google.firebase:firebase-crashlytics-ktx'
    // Add the dependencies for any other desired Firebase products
    // https://firebase.google.com/docs/android/setup#available-libraries

}
~~~

### fcmservice 만들기
FirebaseMessagingService() 상속받는 클래스 생성
생성된 클래스를 AndroidManifest.xml 에 서비스 등록.
~~~
AndroidManifest.xml
 <!-- 메시지를 받기 위한 서비스 등록-->
        <service
            android:name=".fcm.BuildTestFcmService"
            android:exported="false">

            <intent-filter>
                <action android:name="com.google.firebase.MESSAGING_EVENT" />
            </intent-filter>


        </service>

~~~

클래스 작성.
노티피케이션 관련한 추가 등록 사항.
https://firebase.google.com/docs/cloud-messaging/android/client?hl=ko
~~~
// 안드로이드 13버전(skd 33 에서의 퍼미션 등록. Manifest.permission.POST_NOTIFICATIONS)
Manifest.permission.POST_NOTIFICATIONS
~~~

~~~
액티비티 쪽.
노피케이션 매니져 등록

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            val channelId = getString(R.string.default_notification_channel_id)
            val channelName = getString(R.string.default_notification_channel_name)
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(
                NotificationChannel(
                    channelId,
                    channelName,
                    NotificationManager.IMPORTANCE_LOW,
                ),
            )
        }

~~~



~~~
        <service
            android:name=".MyFirebaseMessagingService"
            android:enabled="true"
            android:exported="true"
            android:stopWithTask="false">
            <intent-filter>
                <action android:name="com.google.firebase.MESSAGING_EVENT" />
            </intent-filter>
        </service>
        <!--
            TODO: enabled: 시스템에서 활동을 인스턴스화할 수 있는지 여부
            true: 인스턴스화 가능(기본값)
            flase: 인스턴스화 불가능

            TODO: exported: 다른 애플리케이션의 구성요소에서 활동을 시작할 수 있는지를 설정
            'true': 모든 앱에서 활동에 액세스할 수 있으며 정확한 클래스 이름으로 활동을 시작할 수 있습니다.
            'false': 활동은 같은 애플리케이션의 구성요소나 사용자 ID가 같은 애플리케이션, 권한이 있는 시스템 구성요소에서만 시작될 수 있음. 이는 인텐트 필터가 없는 경우의 기본값

            TODO: stopWithTask: 휴대폰의 태스크에서 모두닫기 및 스와이프했을때 현상
            true: 휴대폰의 태스크에서 모두닫기 및 스와이프 했을 때 액티비는 종료되고 서비스도 종료
            false: 휴대폰의 태스크에서 모두닫기 및 스와이프 했을 때 액티비티는 종료되고 서비스는 onTackRemoved() 호출이 되며 곧바로 서비스가 재시작되어 onCreate()를 타게 된다.
        -->

~~~

안드로이드 12(sdk 32) 버전 menifast
~~~
plugins {
    id 'com.android.application'
    id 'org.jetbrains.kotlin.android'

    //https://3edc.tistory.com/65
    // kotlin-kapt(kotlin Annotation Processiong tool) 는?
    //    id 'kotlin-kapt'
    //    https://developer.android.com/studio/build/migrate-to-ksp?hl=ko
    // kapt 에서 ksp 로 이동.
    // ksp (kotlin Symbol Processing)
    // https://www.charlezz.com/?p=45255

    // Add the Google services Gradle plugin
    id 'com.google.gms.google-services'

    // Add the Crashlytics Gradle plugin
    id 'com.google.firebase.crashlytics'
}

android {
    namespace 'com.bearkinf.myapplication_build_test'
    compileSdk 32

    defaultConfig {
        applicationId "com.bearkinf.myapplication_build_test"
        minSdk 23
        targetSdk 32
        versionCode 1
        versionName "1.0"

        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
        multiDexEnabled true
    }

    // 빌드 구성 시 만들수있는 apk 종류
    //    https://thdev.tech/android/2022/02/26/Android-Flavor/

    buildTypes {

        // 빌드 환경을 분리 해야한다.
        release {
            debuggable false
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
            multiDexEnabled true
            // 이부분은 나중에 추가 수정.
            signingConfig signingConfigs.debug
            buildConfigField "String", "BASE_URL", "\"https://www.daum.net\""
            //빌드 타입뼐 앱 이름 변경.
            // android:label="${applicationLabel}"
            manifestPlaceholders.put("appLabel", "@string/app_name")
//            manifestPlaceholders = [appLabel: "releaseApp"]
        }

        debug {
            multiDexEnabled true
            debuggable true
            signingConfig signingConfigs.debug
            buildConfigField "String", "BASE_URL", '"https://www.naver.com"'

            // 이 부분을 추가하면 release와 별개로 debug 앱이 설치된다.
            // 패키지 명 맨뒤에 .debug 가 붙는다.
            // google-services.json 파일에 해당 패키지 있어야 한다.
            applicationIdSuffix ".debug"
            //빌드 타입뼐 앱 이름 변경.
            // android:label="${applicationLabel}"
            manifestPlaceholders.put("appLabel", "@string/app_name_debug")
//            manifestPlaceholders = [appLabel: "DevelApp"]
        }
    }
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = '1.8'
    }


    //안드로이드 파일 이름 정하기.
    applicationVariants.all { variant ->

        variant.outputs.all {
            def date = new Date()
//            def formattedTimeDate = date.format('yyyyMMddHHmmss')
            def formattedDayDate = date.format('yyyyMMdd')
            outputFileName = "${parent.project.getName()}-v${variant.versionName}-${formattedDayDate}-${variant.buildType.name}.apk"
        }
    }


    buildFeatures {
        // 파일 생성.
        buildConfig true

        //화면 연결을 위한 뷰바인딩 등록.
        viewBinding true
    }


}

dependencies {

    implementation 'androidx.core:core-ktx:1.8.0'
    implementation 'androidx.appcompat:appcompat:1.5.1'
    implementation 'com.google.android.material:material:1.5.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
    testImplementation 'junit:junit:4.13.2'
    androidTestImplementation 'androidx.test.ext:junit:1.1.5'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.5.1'


    // Import the Firebase BoM
    implementation platform('com.google.firebase:firebase-bom:32.0.0')
    // TODO: Add the dependencies for Firebase products you want to use
    // When using the BoM, don't specify versions in Firebase dependencies
    implementation 'com.google.firebase:firebase-analytics-ktx'
    //fcm message
    implementation 'com.google.firebase:firebase-messaging-ktx'
    // crashlytics
    implementation 'com.google.firebase:firebase-crashlytics-ktx'
    // Add the dependencies for any other desired Firebase products
    // https://firebase.google.com/docs/android/setup#available-libraries


//    implementation 'androidx.work:work-runtime:2.8.1'

    //Gson
    implementation 'com.google.code.gson:gson:2.10.1'

}
~~~