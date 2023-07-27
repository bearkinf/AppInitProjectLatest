- [목차](#목차)
- [Android Project Build Setting Document](#android-project-build-setting-document)
  - [1. 개발 환경](#1-개발-환경)
    - [1.1 개발 환경 확인](#11-개발-환경-확인)
    - [1.2 프로젝트 생성.](#12-프로젝트-생성)
    - [1.3 IDE에 따른 gradle version](#13-ide에-따른-gradle-version)
  - [2. 최초 프로젝트 생성](#2-최초-프로젝트-생성)
    - [2.1 root/build.gradle](#21-rootbuildgradle)
    - [2.2 root/setting.gradle](#22-rootsettinggradle)
    - [2.3 root/app/build.gradle](#23-rootappbuildgradle)
    - [2.4 root/app/src/main](#24-rootappsrcmain)
  - [3. 개발환경 추가 설정](#3-개발환경-추가-설정)
    - [3.1 앱 buildConfig 파일 생성.](#31-앱-buildconfig-파일-생성)
    - [3.2 앱 빌드 설정.](#32-앱-빌드-설정)
    - [3.3 빌드 후 파일 생성위치](#33-빌드-후-파일-생성위치)
    - [3.4 앱 빌드시 파일 이름 변경.](#34-앱-빌드시-파일-이름-변경)
    - [3.5 앱 빌드시 어플리케이션 이름 변경](#35-앱-빌드시-어플리케이션-이름-변경)
    - [3.6 앱 빌드시 서버 테스트 연동](#36-앱-빌드시-서버-테스트-연동)
    - [3.7 앱 빌드시 패키지 추가 설정.](#37-앱-빌드시-패키지-추가-설정)
  - [4. FCM 설정 또는 build.gradle 환경 확인.](#4-fcm-설정-또는-buildgradle-환경-확인)
    - [4.1 최신환경에 따른 FCM빌드 환경](#41-최신환경에-따른-fcm빌드-환경)
    - [4.2 FCM Push Message 설정.](#42-fcm-push-message-설정)
    - [4.3 Firebase Crashlytics 설정.](#43-firebase-crashlytics-설정)

# 목차

# Android Project Build Setting Document

안드로이드 프로젝트 생성 참고 문서.

## 1. 개발 환경



### 1.1 개발 환경 확인

- 안드로이드 스튜디오

~~~
Android Studio Flamingo | 2022.2.1 Patch 1
Build #AI-222.4459.24.2221.9971841, built on April 20, 2023
Runtime version: 17.0.6+7-b469.82 x86_64
VM: OpenJDK 64-Bit Server VM by JetBrains s.r.o.
macOS 12.6.6
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

- kotlin or java
- 현재까지의 안드로이드 최신 버전 API 13 (sdk 33)
  </br>

### 1.2 프로젝트 생성.

- 작업할 환경을 선택.
- 화면 생성할 액티비티를 선택.
- 프로젝트 이름, 프로잭트 패키지, 저장위치, 최소 버전 , 작업할 언어 선택(kotlin or java)
  </br>

### 1.3 IDE에 따른 gradle version

- 안드로이드 스튜디오 버전에 따라 달라질 수 있다.
- APG 업그레이드를 유도하기도 한다.

~~~
Android Gradle Plugin Version 8.0.1
Gradle Version 8.0
Gradle JDK = jbr-17 
~~~


## 2. 최초 프로젝트 생성

### 2.1 root/build.gradle

~~~ java
root/build.gradle

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id 'com.android.application' version '8.0.1' apply false
    id 'com.android.library' version '8.0.1' apply false
    id 'org.jetbrains.kotlin.android' version '1.8.20' apply false
}
~~~


### 2.2 root/setting.gradle

~~~ java
root/setting.gradle

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
rootProject.name = "My Application-bearkinf"
include ':app'
~~~


### 2.3 root/app/build.gradle

~~~ java
root/app/build.gradle

plugins {
    id 'com.android.application'
    id 'org.jetbrains.kotlin.android'
}

android {
    namespace 'com.example.myapplication_bearkinf'
    compileSdk 33

    defaultConfig {
        applicationId "com.example.myapplication_bearkinf"
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


### 2.4 root/app/src/main

- 개발 작업을 하는 폴더 위치.
  
## 3. 개발환경 추가 설정


### 3.1 앱 buildConfig 파일 생성.

- 최신 안드로이드 스튜디오에서 프로젝트 생성후 BuildConfig 파일이 생성이 안될 수 있다.
- root/app/build.gradle 안에서 처리.

~~~ java
    buildFeatures {
        // 파일 생성.
        buildConfig true
    }
~~~

- root/gradle.properties 안에서 처리.

~~~ 
# android.defaults.buildfeatures.buildconfig=true # buildConfig 파일 생성 
~~~

### 3.2 앱 빌드 설정.

- Build Variants
  빌드 시 debug 또는 release 를 선택할 수 있다.
  release 빌드시 오류 대응.
  ~~~ java
  Error: The apk for your currently selected variant cannot be signed. Please specify a signing configuration for this variant (release).
  
  root/app/build.gradle

    buildTypes {
        
        release {
            debuggable false

            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
            //signinkey 없이 릴리즈 빌드 시 추가.
            signingConfig signingConfigs.debug
        }
        
        debug {
            debuggable true
            signingConfig signingConfigs.debug
        }
    }
  ~~~

### 3.3 빌드 후 파일 생성위치

- Run App 을 하여 앱을 빌드 한 경우

~~~ 
root/app/build/intermediates/apk/ 폴더에 debug or release 폴더안에 파일이 생성된다.
~~~

- 안드로이드 스튜디오 메뉴 -> Build -> Rebuild Project 한 경우

~~~
root/app/build/outputs/apk 폴더에 debug or release 폴더안에 파일이 생성된다.
~~~

- Run App을 하여 apk가 생성된 경우 apk 파일을 추출하여 디바이스에 직접 넣어서 실행하면 실행되지 않는다(앱 설치가 안된다).
- Rebuild Project 하여 생성된 apk 파일이 디바이스에 넣어서 실행하면 앱설치가 된다.
  </br>

### 3.4 앱 빌드시 파일 이름 변경.

- 기본적으로 앱을 빌드 또는 실행을 하면 app-debug.apk 또는 apk-release.apk가 생성된다.
  맵의 상태와 만든날짜를 확인하여 apk 파일을 생성할 수 있다.

~~~ java
root/app/build.gradle 

    //안드로이드 파일 이름 정하기.
    applicationVariants.all { variant ->

        variant.outputs.all {
            def date = new Date()
            // def formattedTimeDate = date.format('yyyyMMddHHmmss')
            def formattedDayDate = date.format('yyyyMMdd')
            outputFileName = "${parent.project.getName()}-v${variant.versionName}-${formattedDayDate}-${variant.buildType.name}.apk"
        }
    }
~~~

### 3.5 앱 빌드시 어플리케이션 이름 변경

- 앱 빌드시 기본적인 앱 이름이 생성된다.
  android:label="@string/app_name" 을 android:label="${appLabel}" 바꿔준다.

~~~ java
root/app/main/폴더 안 AndroidManifest.xml

    <application
        android:label="${appLabel}"
    >
    </application>

~~~

- root/app/build.gradle 안 디버그와 릴리즈에 맞게
  manifestPlaceholders.put("appLabel", "@string/app_name_release")
  manifestPlaceholders.put("appLabel", "@string/app_name_debug")
  추가를해주면 빌드상태에 맞게 앱 이름이 변경된다.

~~~ java
root/app/build.gradle

    buildTypes {
        
        release {
            debuggable false

            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
            //signinkey 없이 릴리즈 빌드 시 추가.
            signingConfig signingConfigs.debug

            manifestPlaceholders.put("appLabel", "@string/app_name_release")
        }
        
        debug {
            debuggable true
            signingConfig signingConfigs.debug

            manifestPlaceholders.put("appLabel", "@string/app_name_debug")
        }
    }
~~~


### 3.6 앱 빌드시 서버 테스트 연동

- 개발서버와 서비스 서버와의 분리할 수 있다.
  3.1 의 BuildConfig 생성이 된 다음 해당 객체애 접근이 가능하다.
  각각의 빌드 환경 별로 buildConfigField "String", "BASE_URL", '"내용추가"' 내용을 추가하면 된다.
  필요에 따라 port 를 변수로 추가하여 서버 주소를 분기 처리할 수 있다.

~~~ java
root/app/build.gradle

    buildTypes {
        
        release {
            debuggable false

            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
            //signinkey 없이 릴리즈 빌드 시 추가.
            signingConfig signingConfigs.debug

            manifestPlaceholders.put("appLabel", "@string/app_name_release")

            buildConfigField "String", "BASE_URL", '"https://www.naver.com"'

        }
        
        debug {
            debuggable true
            signingConfig signingConfigs.debug

            manifestPlaceholders.put("appLabel", "@string/app_name_debug")
            
            buildConfigField "String", "BASE_URL", '"https://www.daum.net"'
         
        }
    }
~~~

- 코드적용

~~~
// BuildConfig.DEBUG 가 true 면 www.daum.net 으로 
// BuildConfig.DEBUG 가 false 면 www.naver.com 으로
// 상황에 맞게 if 문으로 분기 해도 되고 아니면 그대로 적용하여도 된다.
val url = BuildConfig.BASE_URL
~~~


### 3.7 앱 빌드시 패키지 추가 설정.

- 앱 빌드시 동시에 디버깅 버전과 릴리즈 버전 따로 설치하고자 할때
  applicationIdSuffix ".debug" 추가를 해주면 패키지명 뒤에 추가로  ** .** .** .debug 로 설치가 된다.
  같은 앱을 디버그와 릴리즈 2개의 앱 설치가 가능하다.
- 파이버베이스 에  ** .** .** .debug 앱 추가를 하고 google-services.json 파일을 넣어주면
  각각의 앱에 푸시 테스트가 가능하다.

~~~ java
root/app/build.gradle

    buildTypes {
        
        release {
            debuggable false

            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
            //signinkey 없이 릴리즈 빌드 시 추가.
            signingConfig signingConfigs.debug

            manifestPlaceholders.put("appLabel", "@string/app_name_release")

            buildConfigField "String", "BASE_URL", '"https://www.naver.com"'
        }
        
        debug {
            debuggable true
            signingConfig signingConfigs.debug

            manifestPlaceholders.put("appLabel", "@string/app_name_debug")
            
            buildConfigField "String", "BASE_URL", '"https://www.daum.net"'

            applicationIdSuffix ".debug"
        }
    }
~~~

## 4. FCM 설정 또는 build.gradle 환경 확인.

- https://firebase.google.com/docs/android/setup?hl=ko&authuser=0
- 최신 그래들 환경에서는 조금 다름.
- 현재 문서의 기준은 최신 그래들을 반영하여 적용시킴.
- 파이어베이스 콘솔에서 앱 추가 후 google-services.json 파일을 root/app/ 폴더안에 추가 할것.
  </br>

### 4.1 최신환경에 따른 FCM빌드 환경

- FCM 기본 구성.
- com.google.firebase:firebase-analytics-ktx
  https://firebase.google.com/docs/analytics/get-started?platform=android&authuser=0&hl=ko
  앱 사용량과 행동 데이터를 수집한다.

- 2.1 root/build.gradle

~~~ java
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id 'com.android.application' version '8.0.1' apply false
    id 'com.android.library' version '8.0.1' apply false
    id 'org.jetbrains.kotlin.android' version '1.8.20' apply false
}
~~~

- 추가된 root/build.gradle
- https://jgeun97.tistory.com/202 내용 참고

~~~ java
root/build.gradle

buildscript {
    dependencies {
        // Add the dependency for the Google services Gradle plugin
        classpath 'com.google.gms:google-services:4.3.15'
    }
}

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id 'com.android.application' version '8.0.1' apply false
    id 'com.android.library' version '8.0.1' apply false
    id 'org.jetbrains.kotlin.android' version '1.8.20' apply false
}

또는
//buildscript 사용안함.
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id 'com.android.application' version '8.0.1' apply false
    id 'com.android.library' version '8.0.1' apply false
    id 'org.jetbrains.kotlin.android' version '1.8.20' apply false
    
    // Add the dependency for the Google services Gradle plugin
    // classpath 'com.google.gms:google-services:4.3.15'
    // 위 내용중 : 을 . 로 변경 버전을 따로 분리
    id 'com.google.gms.google-services' version '4.3.15' apply false

}
~~~

- root/app/build.gradle 플러그인 추가

~~~ java
root/app/build.gradle

plugins {
    id 'com.android.application'
    id 'org.jetbrains.kotlin.android'
        // Add the Google services Gradle plugin
    id 'com.google.gms.google-services'
}


dependencies {
    // Import the Firebase BoM
    implementation platform('com.google.firebase:firebase-bom:32.0.0')
    // TODO: Add the dependencies for Firebase products you want to use
    // When using the BoM, don't specify versions in Firebase dependencies
    implementation 'com.google.firebase:firebase-analytics-ktx'

}
~~~


### 4.2 FCM Push Message 설정.

- com.google.firebase:firebase-messaging-ktx
  https://firebase.google.com/docs/cloud-messaging/android/client?authuser=0&hl=ko
  사용자에게 푸시 메시지를 받을 수 있다.
- root/app/build.gradle 플러그인 추가

~~~ java
root/app/build.gradle

plugins {
    id 'com.android.application'
    id 'org.jetbrains.kotlin.android'
        // Add the Google services Gradle plugin
    id 'com.google.gms.google-services'
}


dependencies {
    // Import the Firebase BoM
    implementation platform('com.google.firebase:firebase-bom:32.0.0')
    // TODO: Add the dependencies for Firebase products you want to use
    // When using the BoM, don't specify versions in Firebase dependencies
    implementation 'com.google.firebase:firebase-analytics-ktx'

    implementation 'com.google.firebase:firebase-messaging-ktx'

}
~~~

- FirebaseMessagingService를 확장하는 서비스를 추가
  이 서비스는 백그라운드에서 앱의 알림을 수신하는 것 외에 다른 방식으로 메시지를 처리하려는 경우에 필요합니다. 포그라운드 앱의 알림 수신, 데이터 페이로드 수신, 업스트림
  메시지 전송 등을 수행하려면 이 서비스를 확장해야 합니다.
- 앱 매니페스트 수정.

~~~ java
<service
    android:name=".java.MyFirebaseMessagingService"
    android:exported="false">
    <intent-filter>
        <action android:name="com.google.firebase.MESSAGING_EVENT" />
    </intent-filter>
</service>
~~~

- Android 13 이상 런타임 알림 권한 요청.
  Manifest.permission.POST_NOTIFICATIONS 권한 승인을 꼭 해줘야 한다.
  </br>

### 4.3 Firebase Crashlytics 설정.

- com.google.firebase:firebase-crashlytics-ktx
  https://firebase.google.com/docs/crashlytics/get-started?platform=android&authuser=0&hl=ko
  비정상 종료 를 확인 할 수 있다.
- 추가된 root/build.gradle
- https://jgeun97.tistory.com/202 내용 참고

~~~ java
root/build.gradle

buildscript {
    dependencies {
        // Add the dependency for the Google services Gradle plugin
        classpath 'com.google.gms:google-services:4.3.15'

        // Add the dependency for the Crashlytics Gradle plugin
        classpath 'com.google.firebase:firebase-crashlytics-gradle:2.9.5'
    }
}

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id 'com.android.application' version '8.0.1' apply false
    id 'com.android.library' version '8.0.1' apply false
    id 'org.jetbrains.kotlin.android' version '1.8.20' apply false
}

또는
//buildscript 사용안함.

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id 'com.android.application' version '8.0.1' apply false
    id 'com.android.library' version '8.0.1' apply false
    id 'org.jetbrains.kotlin.android' version '1.8.20' apply false
    
    // Add the dependency for the Google services Gradle plugin
    // classpath 'com.google.gms:google-services:4.3.15'
    // 위 내용중 : 을 . 로 변경 버전을 따로 분리
    id 'com.google.gms.google-services' version '4.3.15' apply false

    // https://stackoverflow.com/questions/72542162/unable-to-add-firebase-crashlytics-for-gradle-version-7-2
    // Add the dependency for the Crashlytics Gradle plugin
    id'com.google.firebase.crashlytics'version '2.9.5' apply false

}
~~~

- root/app/build.gradle 플러그인 추가

~~~ java
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

    implementation 'com.google.firebase:firebase-messaging-ktx'

    implementation 'com.google.firebase:firebase-crashlytics-ktx'

}
~~~