# react-native-android-dropdown

An inline and stylable dropdown picker for android devices.

# Getting Started

### Installation
$ npm install react-native-android-dropdown --save

# Usage

### Import

```import Dropdown from 'react-native-android-dropdown';```

### Example

```

import React, { Component } from 'react';
import { View, Text, StyleSheet, requireNativeComponent } from 'react-native';
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
 - width, height, items, onChange

### Optional props
 - 'placeholder', 'value', 'parentStyle', 'itemStyle' and 'caret'
 - the 'parentStyle' prop has 'backgroundColor', 'color', 'padding', 'borderRadius', 'borderWidth', 'borderColor',
   and 'fontSize' properties 
 - the 'itemStyle' prop has 'backgroundColor', 'color', 'padding', 'borderWidth', 'borderColor',
   and 'fontSize' properties
 - the 'caret' prop has 'size', 'color' and 'right' as parameters
 - 'padding' property can have a number value or an array of four elements ([left, top, right, bottom]). 
   In the latter case, the style should be added inline with the element.
   ```
   // dropdown item padding  along with other styles
   itemStyle={{...styles.item,  padding: [15, 5, 5, 15] }}
   ```
 - 'borderRadius' property can have a number value or an array of four elements 
   ([top-left, top-right, bottom-right, bottom-left]). In the latter case, 
   the style should be added inline with the element.
   ```
   // container border radius (top=10, bottom=0) along with other styles
   itemStyle={{...styles.item,  borderRadius: [10, 10, 0, 0] }}
   ```
 - 'borderRadius' property can have a number value or an array of four elements 
   ([left, top, right, bottom]). In the latter case, 
   the style should be added inline with the element. If partial border is to be applied,
   the latter case should be applied with a negative value for the unwanted borders.
   ```
   // border bottom along with other styles
   itemStyle={{...styles.item,  borderWidth: [-2, -2, -2, 1] }}
   ```

## Issues or suggestions?
If you have any issues or want to suggest something , your can write it [here](https://github.com/Asaye/react-native-android-dropdown/issues).
