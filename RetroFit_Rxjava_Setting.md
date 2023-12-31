# Retrofit & Rxjava 설정
---

- RxJava 설정.
- Retrofit 설정.
- 비동기 서버 통신 하기위한 라이브러리 모듈

## dependencies Add

- 라이브러리 등록

``` java
dependencies {
    ...
    // Rxjava
    // https://github.com/ReactiveX/RxJava
    implementation "io.reactivex.rxjava3:rxjava:3.1.7"
    
    // RxAndroid
    // https://github.com/ReactiveX/RxAndroid
      implementation 'io.reactivex.rxjava3:rxandroid:3.0.2'
    // Because RxAndroid releases are few and far between, it is recommended you also
    // explicitly depend on RxJava's latest version for bug fixes and new features.
    // (see https://github.com/ReactiveX/RxJava/releases for latest 3.x.x version)
    // implementation 'io.reactivex.rxjava3:rxjava:3.1.5'
    
    // RxKotlin
    // https://github.com/ReactiveX/RxKotlin
    implementation("io.reactivex.rxjava3:rxkotlin:3.0.1")
    
    // RxKoltin 사용시 적용해야함.
    // repositories {
    //     maven { url 'https://jitpack.io' }
    // }
       
    // retrofit
    //https://github.com/square/retrofit
    implementation 'com.squareup.retrofit2:retrofit:2.9.0'
    // Rxjava adapter
    // https://github.com/square/retrofit/tree/master/retrofit-adapters/rxjava3
    implementation 'com.squareup.retrofit2:adapter-rxjava3:2.9.0'
    
    // data converter
    // https://github.com/square/retrofit/tree/master/retrofit-converters/gson
    implementation 'com.squareup.retrofit2:converter-gson:2.9.0'  // 데이터 형태를 json 에서 gson 으로
    // https://github.com/square/retrofit/tree/master/retrofit-converters/scalars
    implementation 'com.squareup.retrofit2:converter-scalars:2.9.0' // 데이터 형태르 String 으로
    
    // okhttp log interceptor body 메시지 확인.
    // https://github.com/square/okhttp/tree/master/okhttp-logging-interceptor
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")    
    
    
    
    // gson
    // https://github.com/google/gson
    implementation 'com.google.code.gson:gson:2.10.1'
   
}
```

## 클래스 생성 방법

### interface 방식

- 레트로핏에 필요한 서비스 interface 안에 구현한다.
- interface class 안에 동반객체 생성을 사용하여 레트로핏을 구현한다.
- 함수호출시 유용하다고 판단된다.
- 다형성 보다는 단독 구현 실행.

### object 방식

- Object class 싱글턴 형식을 구현할 수 있다.
- 레트로핏에 필요한 서비스 interface를 따로 구현해야한다.
- 추상 : api service interface 가 많으면 제네릭스를 사용하여 여러 인터페이스 구현이 가능하다.
- 다형성 사용을위한 구성이 가능한듯.


## lambda 식 콜백 처리.