# Funsol FCM

[![](https://jitpack.io/v/Funsol-Projects/Funsol-FCM.svg)](https://jitpack.io/#Funsol-Projects/Funsol-FCM)

PandaFCM is a Firebase Cloud Messaging Android app that demonstrates registering your Android app
for notifications and handling the receipt of a message. InstanceID allows easy registration while
FirebaseMessagingService and FirebaseInstanceIDService enable token refreshes and message handling
on the client.

> Read more about [Firebase Cloud Messaging](https://firebase.google.com/docs/cloud-messaging).

## Getting Started

### Step 1

[Add firebase to your Android App](https://firebase.google.com/docs/android/setup).

#### Note: After complition of step one, your project should have a google-services.json file added to the root of your project along with the classpath, plugin and dependecies.

#### Classpath

```
    classpath 'com.google.gms:google-services:latest-version'
```

#### Plugin

```
    id 'com.google.gms.google-services'
```

#### Dependencies

no dependencies required

### Step 2

Add maven repository in project level build.gradle or in latest project setting.gradle file

```
    repositories {
        google()
        mavenCentral()
        maven { url "https://jitpack.io" }
    }
```  

### Step 3

Add PandaFCM dependencies in App level build.gradle.

```
    dependencies {
        implementation 'com.github.Funsol-Projects:Funsol-FCM:1.0'
    }
```  

### Step 4

Finally intialize Firebase and setup FCM in application class or in your "MainActivity"

```
    PandaFCM.setupFCM(this, "YourTopicName")
```

### Remove

If you want to stop receiving notification from the subscribed topic simply call.

```
    PandaFCM.removeFCM("YourTopicName")
```

## License

MIT License

Copyright (c) [2021]

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
associated documentation files (the "Software"), to deal in the Software without restriction,
including without limitation the rights to use, copy, modify, merge, publish, distribute,
sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial
portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES
OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

