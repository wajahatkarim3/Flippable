<div align="center"><img src="https://user-images.githubusercontent.com/8867121/154110583-1e5364de-3106-47c7-9b94-bf8b1e9d6ff3.gif"/></div>
<h1 align="center">💳 Flippable</h1>
<h4 align="center">A Jetpack Compose utility library to create flipping Composable views with 2 sides.</h4>
<div align="center"><a href="https://twitter.com/intent/tweet?url=https%3A%2F%2Fgithub.com%2Fwajahatkarim3%2FEasyFlipViewPager&text=Create%20amazing%20book%20or%20card%20flipping%20animations%20for%20your%20ViewPager%20in%20Android%20with%20these%202-lines%20of%20code%20through%20EasyFlipViewPager&hashtags=android%2C%20kotlin%2C%20java%2C%20opensource%2C%20programming">
        <img src="https://img.shields.io/twitter/url/http/shields.io.svg?style=social"/>
    </a> <a href="https://twitter.com/WajahatKarim">
        <img src="https://img.shields.io/twitter/follow/WajahatKarim?style=social"/>
    </a>
</div> 
<br/>


<div align="center">
  <img src="https://img.shields.io/maven-central/v/com.wajahatkarim/flippable" />
    <!-- PRs Welcome -->
    <a href="">
        <img src="https://img.shields.io/badge/PRs-welcome-brightgreen.svg"/>
    </a>
    <!-- Say Thanks! -->
    <a href="https://saythanks.io/to/wajahatkarim3">
        <img src="https://img.shields.io/badge/Say%20Thanks-!-1EAEDB.svg"/>
    </a>
    <a href="https://www.paypal.me/WajahatKarim/5">
        <img src="https://img.shields.io/badge/$-donate-ff69b4.svg?maxAge=2592000&amp;style=flat">
    </a>
</div>

<div align="center">
  <sub>Built with ❤︎ by
  <a href="https://twitter.com/WajahatKarim">Wajahat Karim</a> and
  <a href="https://github.com/wajahatkarim3/Flippable/graphs/contributors">
    contributors
  </a>
</div>
<br/>
<br/>

## Demo
https://user-images.githubusercontent.com/8867121/154115910-8e2d2661-97c0-4b5a-b27e-76857533afe6.mp4
              
<br/>
<br/>
        
## 💻 Installation
In `build.gradle` of `app` module, include this dependency
        
```groovy
implementation "com.wajahatkarim:flippable:x.y.z"
```
        
Please replace x, y and z with the latest version numbers ![](https://img.shields.io/maven-central/v/com.wajahatkarim/flippable).
        
Or you can find latest version and changelogs in the [releases](https://github.com/wajahatkarim3/Flippable/releases).

<br/>
        
## ❓ Usage

Add the [`Flippable`](https://github.com/wajahatkarim3/Flippable/blob/main/flippable/src/main/java/com/wajahatkarim/flippable/Flippable.kt) composable and define the front and back side composable methods inside. That's it.

```kotlin
Flippable(
    frontSide = {
        // Composable content for the front side
    },

    backSide = {
        // Composable content for the back side
    },

    flipController = rememberFlipController(),

    // Other optional parameters
)
```

The `FlippableController` allows you to programatically flip the view from any event or button click or any method call etc. There's a method `rememberFlipController()` to get an instance of `FlippableController`. If you want to use any key for the `remember`, you can do so by directly creating `FlippableController` yourself like the code below:

```kotlin
val flipController = remember { FlippableController() }

val flipController1 = remember(key = "2") { FlippableController() }
```

<br/>
    
## 🎨 Customization Parameters
If you'd like to discover what `Flippable` offers, here is an exhaustive description of customizable parameters.
    
```kotlin
    
val controller = rememberFlipController()
    
Flippable(
    frontSide = {
        // Composable content for the front side
    },
    
    backSide = {
        // Composable content for the back side
    },
    
    // To manually controll the flipping, you would need an instance of FlippableController. 
    // You can access it using rememberFlipController() method.
    // This provides methods like controller.flip(), controller.flipToFront(), controller.flipToBack() etc.
    flipController = controller,
    
    // The obvious one - if you have done Jetpack Compose before.
    modifier = Modifier,
    
    // The duration it takes for the flip transition in Milliseconds. Default is 400
    flipDurationMs = 400,
    
    // If true, this will flip the view when touched. 
    // If you want to programatically flip the view without touching, use FlippableController.
    flipOnTouch = flipOnTouchEnabled,
    
    // If false, flipping will be disabled completely. 
    // The flipping will not be triggered with either touch or with controller methods.
    flipEnabled = flipEnabled,
    
    // The Flippable is contained in a Box, so this tells
    // the alignment to organize both Front and Back side composable.
    contentAlignment = Alignment.TopCenter,
    
    //If true, the Flippable will automatically flip back after 
    //duration defined in autoFlipDurationMs. By default, this is false..
    autoFlip = false,
    
    //The duration in Milliseconds after which auto-flip back animation will start. Default is 1 second.
    autoFlipDurationMs = 1000,
    
    // The animation type of flipping effect. Currently there are 4 animations. 
    // Horizontal Clockwise and Anti-Clockwise, Vertical Clockwise and Anti-Clockwise
    // See animation types section below.
    flipAnimationType = FlipAnimationType.HORIZONTAL_CLOCKWISE,
    
    // The [GraphicsLayerScope.cameraDistance] for the flip animation. 
    // Sets the distance along the Z axis (orthogonal to the X/Y plane
    // on which layers are drawn) from the camera to this layer.
    cameraDistance = 30.0F,
    
    // The listener which is triggered when flipping animation is finished.
    onFlippedListener = { currentSide ->
        // This is called when any flip animation is finished. 
        // This gives the current side which is visible now in Flippable.
    }
)
```

I encourage you to play around with the [sample app](https://github.com/wajahatkarim3/Flippable/blob/main/app/src/main/java/com/wajahatkarim/flippable_demo/MainActivity.kt) to get a better look and feel. It showcases advanced usage with customized parameters.
        
<br/>
        
## 📄 API Documentation
Visit the [API documentation](https://wajahatkarim3.github.io/Flippable) of this library to get more information in detail.

<br/>
        
## ⚙️ Animation Types

### Horizontal Clockwise
![Kapture 2022-02-15 at 23 20 11](https://user-images.githubusercontent.com/8867121/154124561-2f6d6d2d-1f7a-4d85-92cd-c91f54b6f245.gif)

### Horizontal Anti-Clockwise
![Kapture 2022-02-15 at 23 24 05](https://user-images.githubusercontent.com/8867121/154125061-f40fed57-d1d3-42ee-94cf-f0597ce12fee.gif)

### Vertical Clockwise
![Kapture 2022-02-15 at 23 26 00](https://user-images.githubusercontent.com/8867121/154125376-496d2577-1c65-49bf-a1e1-0ba0aefcd0b0.gif)


### Vertical Anti-Clockwise
![Kapture 2022-02-15 at 23 26 33](https://user-images.githubusercontent.com/8867121/154125464-f89f3196-466a-4be9-9874-cf78ba4729ac.gif)
        
<br/>
        
## 👨 Developed By

<a href="https://twitter.com/WajahatKarim" target="_blank">
  <img src="https://avatars1.githubusercontent.com/u/8867121?s=460&v=4" width="70" align="left">
</a>

**Wajahat Karim**

[![Twitter](https://img.shields.io/badge/-twitter-grey?logo=twitter)](https://twitter.com/WajahatKarim)
[![Web](https://img.shields.io/badge/-web-grey?logo=appveyor)](https://wajahatkarim.com/)
[![Medium](https://img.shields.io/badge/-medium-grey?logo=medium)](https://medium.com/@wajahatkarim3)
[![Linkedin](https://img.shields.io/badge/-linkedin-grey?logo=linkedin)](https://www.linkedin.com/in/wajahatkarim/)

<br/>

## 👍 How to Contribute
1. Fork it
2. Create your feature branch (git checkout -b my-new-feature)
3. Commit your changes (git commit -am 'Add some feature')
4. Push to the branch (git push origin my-new-feature)
5. Create new Pull Request

<br/>
        
## 📃 License

    Copyright 2022 Wajahat Karim

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
