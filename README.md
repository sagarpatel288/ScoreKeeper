# ScoreKeeper ![CI status](https://img.shields.io/badge/build-passing-brightgreen.svg)

ScoreKeeper is the application as a part of Google Udacity India Scholar Android Basic Nanodegree Project.

## Gif
![scorekeeper.gif](/gifs/scorekeeper.gif?raw=true "scorekeeper.gif")

## Screenshots
<p>
  <img src="/screenshots/001.png" width="300"/>
</p>
<p>
  <img src="/screenshots/002.png" width="300"/>
</p>
<p>
  <img src="/screenshots/003.png" width="300"/>
</p>
<p>
  <img src="/screenshots/004.png" width="300"/>
</p>
<p>
  <img src="/screenshots/005.png" width="300"/>
</p>
<p>
  <img src="/screenshots/006.png" width="300"/>
</p>

## About
### Sdk and tools
    * compileSdkVersion = 28
    * minSdkVersion = 16
    * targetSdkVersion = 28

### Native Android Dependencies
    * supportLibraryVersion = '27.1.1'

### Butter knife for view binding
    * butterKnifeVersion = '8.8.1'

### Test dependencies
    * junitVersion = '4.12'
    * espressoVersion = '3.0.2'
    

## Rubric and features

* Tried to follow best practices
* Style for common set of attributes
* Resource files according to type of resources. E.g. dimens for dimensions
* Proper unit according to type of resource. E.g. sp for TextView 
* Java documentation throughout the application
* Proper usage of Android Studio's _Reformat_ and _Rearrange_ option
* There are two teams separated by a column (View divider)
* Each team has a unique team name, current score and set of custom buttons to add 3, 2 and 1 points
* Result button to see the result
* Reset button to reset the game at any stage
* [Dialog Fragment](https://developer.android.com/reference/android/app/DialogFragment) to show _result_ and _reset alert_
* Custom button with gradient background, rounded corners and ripple effects for Api 21 and above
* Screen configuration (Orientation) change support
* Optimized singleton for Typeface


## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## License
[MIT](https://choosealicense.com/licenses/mit/)
