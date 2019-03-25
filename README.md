# KGeoGson

An implementation of GeoJSON / RFC7946 for Kotlin, using GSON.

## Download 

 [ ![Download](https://api.bintray.com/packages/chris-hatton/maven/geojson-kotlin/images/download.svg) ](https://bintray.com/chris-hatton/maven/KGeoGson/_latestVersion)

```groovy
repositories {
    jcenter()
}

dependencies {
    implementation 'org.chrishatton.lib:geojson-kotlin:0.0.1'
}

```

## Usage

 - Create a `Point`
 
 ```kotlin
val point = Point(latitude = 1.0, longitude = 2.0)
```

 - Incorporate point into a `Feature`
 
 ```kotlin
val feature = Feature(geometry = point)
```
 
 - Then incorporate feature into a `FeatureCollection`
 
 ```kotlin
val featureCollection = FeatureCollection(1, listOf(feature))
```
 
 - For each *GeoJson* object, you can call `toJson()`
 
 ```kotlin
val geoJsonText = featureCollection.toJson()
```

 - Parsing :
 
 ```kotlin
 val geoJsonObjectOut: FeatureCollection = FeatureCollection.fromJson(geoJsonText)
```
 
### GsonBuilder

To serialize and parse GeoJson you need to register special adapter to GSon

```kotlin
val gson = GsonBuilder()
            // registering KGeoGson adapter here
            .registerGeoJsonTypeAdapters()
            .create()
```


## License

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

