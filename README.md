# react-native-android-dropdown

An inline and stylable dropdown picker for android devices.

# Getting Started

### Installation
Using npm

``` $ npm install react-native-android-dropdown --save```

Using yarn

``` $ yarn add react-native-android-dropdown```

### Linking
There are two options for linking:

##### 1. Automatic

``` react-native link react-native-android-dropdown ```

##### 2. Manual

If the automatic linking fails for some reason, you can do the linking manually as follows:
 * add the following to <code>yourAppName/android/settings.gradle</code> file:
 
 ```
  include ':react-native-android-dropdown'
  project(':react-native-android-dropdown').projectDir = new File(rootProject.projectDir, '../node_modules/react-native-android-dropdown/android')
 ```

 * add the following inside the dependencies closure of  <code>yourAppName/android/app/build.gradle</code> file:
 ```
  implementation project(':react-native-android-dropdown')
```

* add the following to your <code>MainApplication.java</code> file:
 ```
  import com.androidDropdown.DropdownPackage;
 ```
 and also,
 ```
  @Override
  protected List<ReactPackage> getPackages() {
    return Arrays.<ReactPackage>asList(
      new MainReactPackage(),
      ....
      new DropdownPackage()                    <== Add this
    );
  }
 ```

# Usage

### Import

```import Dropdown from 'react-native-android-dropdown';```

### Example

```

import React, { Component } from 'react';
import { View, Text, StyleSheet } from 'react-native';
import Dropdown from 'react-native-android-dropdown';

export default class DropdownDemo extends Component {

  state = {
    selected: "Two"
  }

  _getSelected = (event) => {
      this.setState({ selected: event.nativeEvent.selected });
  }

  render() {
    const items = ["One", "Two", "Three"];
    const arrowDown = {size: 20, right: 20, color: "gray"};
    return (
      <View style={styles.container}>
        <Dropdown 
          placeholder="Select items" 
          items={items} 
          value="Two" 
          width={200} 
          height={40} 
          parentStyle={styles.parent} 
          itemStyle={styles.item}
          caret={arrowDown} 
          onChange={this._getSelected} 
        />
        <Text>The selected value is: {this.state.selected}</Text>
      </View>
    );
  }
}

const styles = StyleSheet.create({
    "container": {
        "flex": 1,
        "flexDirection": "column",
        "justifyContent": "center",
        "alignItems": "center"
    },
    "parent": {
        "backgroundColor": "#f1f1f1",
        "color": "#555555",
        "padding": 15,  
        "borderRadius": 10,  
        "borderWidth": 1,
        "borderColor": "gray",
        "fontSize": 18
    },
    "item": { 
        "backgroundColor": "#444444",
        "color": "white",
        "padding": 15,  
        "borderColor": "#f1f1f1",
        "fontSize": 18
    },
});

```

### Required props
 - <code>width</code>, <code>height</code>, <code>items</code>, <code>onChange</code>

### Optional props
 - <code>placeholder</code>, <code>value</code>, <code>parentStyle</code>, <code>itemStyle</code> and <code>caret</code>
 - the <code>parentStyle</code> prop has <code>backgroundColor</code>, <code>color</code>, <code>padding</code>, <code>borderRadius</code>, <code>borderWidth</code>, <code>borderColor</code> and <code>fontSize</code> properties 
 - the <code>itemStyle</code> prop has <code>backgroundColor</code>, <code>color</code>, <code>padding</code>, <code>borderWidth</code>, <code>borderColor</code> and <code>fontSize</code> properties
 - the <code>caret</code> prop has <code>size</code>, <code>color</code> and <code>right</code> as parameters
 - <code>padding</code> property can have a number value or an array of four elements ([left, top, right, bottom]). 
   In the latter case, the style should be added inline with the element.
   ```
   // dropdown item padding  along with other styles
   itemStyle={{...styles.item,  padding: [15, 5, 5, 15] }}
   ```
 - <code>borderRadius</code> property can have a number value or an array of four elements 
   ([top-left, top-right, bottom-right, bottom-left]). In the latter case, 
   the style should be added inline with the element.
   ```
   // container border radius (top=10, bottom=0) along with other styles
   itemStyle={{...styles.item,  borderRadius: [10, 10, 0, 0] }}
   ```
 - <code>borderWidth</code> property can have a number value or an array of four elements 
   ([left, top, right, bottom]). In the latter case, 
   the style should be added inline with the element. If partial border is to be applied,
   the latter case should be applied with a negative value for the unwanted borders.
   ```
   // border bottom along with other styles
   itemStyle={{...styles.item,  borderWidth: [-2, -2, -2, 1] }}
   ```

## Issues or suggestions?
If you have any issues or want to suggest something , your can write it [here](https://github.com/Asaye/react-native-android-dropdown/issues).
