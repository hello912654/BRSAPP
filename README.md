# baggage processing

## layout

- Data
  - dao
  - model
  - repository
- di(dependency injection)
- ui
  - screen
  - theme
  - viewmodel

## Top level build.gradle

```java

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
//    id("com.google.devtools.ksp") version "1.9.0-1.0.13" apply false
    id("com.google.dagger.hilt.android") version "2.48" apply false

}

buildscript {
    dependencies {
        classpath(libs.hilt.android.gradle.plugin)
    }
}
```

#### use dagger & hilt

## App level build.gradle

#### kapt & hilt

```java
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)

    //ksp
    //id("com.google.devtools.ksp")
    //kapt
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}
```

#### room and hilt impletation

```java

    //room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    //ksp(libs.androidx.room.compiler)
    kapt(libs.androidx.room.compiler)

    //hilt
    //implementation(libs.hilt.android)
    implementation(libs.hilt.android)
    //ksp(libs.hilt.android.compiler)
    kapt(libs.hilt.android.compiler.v248)

    //hilt navigation
    implementation(libs.androidx.hilt.navigation.compose)
```

## Application to use hilt injection

#### create HHTApplication

```java
@HiltAndroidApp
class HHTApplication : Application()
```

## AndroidManifest.xml

#### add application name

```kotlin
<application
    android:name=".HHTApplication"
    ...
    ...
    ...
</application>
```

## MainActivity 使用 hilt injection

### 必須先建立上述的 hiltAndroidApp

#### 使用 @AndroidEntryPoint

```java
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    ....
}
```

## View Model & UI

#### UI 可以使用來自 view model 的資料

---

#### BaggageViewModel

```java
//使用hiltviewmodel
@HiltViewModel
class BaggageViewModel @Inject constructor(
    private val repository: BaggageRepository
) : ViewModel() {
    ...
}
```

#### BaggageInputScreen

```java
// 使用hiltviewmodel()
@Composable
fun BaggageInputScreen(
    modifier: Modifier = Modifier,
    viewModel: BaggageViewModel = hiltViewModel()
) {
    // get all Bsm list by viewModel.bsmList.collectAsState()
    val bsmList by viewModel.bsmList.collectAsState()
    ...
}
```
