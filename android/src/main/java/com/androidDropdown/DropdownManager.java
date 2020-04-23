package com.androidDropdown;

import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.Dynamic;
import com.facebook.react.common.MapBuilder;
import android.widget.ArrayAdapter;
import java.util.Map;


public class DropdownManager extends SimpleViewManager<Dropdown> {
    
    private ThemedReactContext mContext = null;
    @Override
    public String getName() {
        return "Dropdown";
    }

    @Override
    protected Dropdown createViewInstance(ThemedReactContext reactContext) {    
        return new Dropdown(reactContext);
    }

    @ReactProp(name = "placeholder")
	public void setPlaceholder(Dropdown view, String placeholder) {
        view.setPlaceholder(placeholder);
	}

    @ReactProp(name = "items")
    public void setItems(Dropdown view, ReadableArray items) {       
        ArrayAdapter adapter = (ArrayAdapter) view.getAdapter();     
        int start = adapter.getCount();   
        for (int i = 0; i < items.size(); i++) {
            adapter.insert(items.getString(i), i + start);
        }
    }

    @ReactProp(name = "value")
    public void setValue(Dropdown view, String value) {
       view.setValue(value);
    }

    @ReactProp(name = "parentStyle")
    public void setParentStyles(Dropdown view, ReadableMap parentStyle) {
       view.setParentStyles(parentStyle);
    }

    @ReactProp(name = "itemStyle")
    public void setItemStyles(Dropdown view, ReadableMap itemStyle) {
        view.setItemStyles(itemStyle);
    }

    @ReactProp(name = "caret")
    public void setCaret(Dropdown view, ReadableMap caret) {
        view.setCaret(caret);
    }

	public Map getExportedCustomBubblingEventTypeConstants() {
        return MapBuilder.builder()
            .put(
                "selection",
                MapBuilder.of(
                    "phasedRegistrationNames",
                    MapBuilder.of("bubbled", "onChange")))
                    .build();
    }
}