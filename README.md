
# react-native-app-upgrade

## Getting started

`$ npm install react-native-app-upgrade --save`

### Mostly automatic installation

`$ react-native link react-native-app-upgrade`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-app-upgrade` and add `RNAppUpgrade.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNAppUpgrade.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.darklycoder.rn.upgrade.RNAppUpgradePackage;` to the imports at the top of the file
  - Add `new RNAppUpgradePackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-app-upgrade'
  	project(':react-native-app-upgrade').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-app-upgrade/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-app-upgrade')
  	```


## Usage
```javascript
import RNAppUpgrade from 'react-native-app-upgrade';

// TODO: What to do with the module?
RNAppUpgrade;
```
  