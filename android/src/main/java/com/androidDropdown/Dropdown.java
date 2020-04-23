package com.androidDropdown;

import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.bridge.Dynamic;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.uimanager.events.RCTEventEmitter;

import android.view.View;
import android.view.Gravity;
import android.view.ViewGroup;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;

import android.widget.Spinner;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.CheckedTextView;
import android.widget.AdapterView.OnItemSelectedListener;

import java.util.List;
import java.util.ArrayList;

public class Dropdown extends Spinner {  
    
    private ReadableMap mStyles, mItemStyles, mCaret; 
    private boolean mHasPlaceHolder = false;
    private int mSelectedIndex = -1;

    private OnItemSelectedListener mListener = new OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {          
            String text = (String) parent.getItemAtPosition(position);
            if (mStyles != null && mStyles.hasKey("color")) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor(mStyles.getString("color")));
            }
            if (mStyles != null && mStyles.hasKey("fontSize")) {
              ((TextView) parent.getChildAt(0)).setTextSize(mStyles.getInt("fontSize"));
            }             
             
            if (mSelectedIndex >= 0) {
                if (mHasPlaceHolder) mSelectedIndex++;
                position = mSelectedIndex;
                mSelectedIndex = -1;
            }  
            onReceiveNativeEvent(text); 
            setSelection(position);              
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    };
  
    public Dropdown(Context context) {
        super(context);             
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);  
        drawable.setColor(Color.WHITE);
        drawable.setStroke(1, Color.parseColor("#cccccc"));
        setBackground(drawable); 
        addAdapter(context);  
        setPadding(0, 0, 0, 0);
        setOnItemSelectedListener(mListener);
    }    

    @Override 
    public void setSelection(int position) {
        boolean sameSelected = position == getSelectedItemPosition();   
        super.setSelection(position);    
        if (!sameSelected) {
            setAdapter(getAdapter());
            ((ArrayAdapter) getAdapter()).notifyDataSetChanged();
            setSelection(position, false);
            getOnItemSelectedListener().onItemSelected(this, getSelectedView(), position, getSelectedItemId());
        }    
    }   

    public void setPlaceholder(String placeholder) {
        ArrayAdapter adapter = (ArrayAdapter) getAdapter();        
        adapter.insert(placeholder, 0);
        mHasPlaceHolder = true;
    }

    public void setValue(String value) { 
        if (value == null) return;   
        ArrayAdapter adapter = (ArrayAdapter) getAdapter();
        int size = adapter.getCount();
        for (int i = 0; i < size; i++) {
            if (adapter.getItem(i).toString().equalsIgnoreCase(value)) {
                mSelectedIndex = i;
                return;
            }
        }        
    }

    public void setParentStyles(ReadableMap styles) {
      this.mStyles = styles;  
    }

    public void setItemStyles(ReadableMap styles) {
      this.mItemStyles = styles;
    }

    void setCaret(ReadableMap caret) {
      this.mCaret = caret;
    }

    private void addAdapter(Context context) {
        List<String> list = new ArrayList<String>();        
        setAdapter(
            new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, list){
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);   
                    if (mStyles != null && mStyles.hasKey("backgroundColor")) {
                       ((GradientDrawable) getBackground()).setColor(Color.parseColor(mStyles.getString("backgroundColor")));  
                    } 
                    addStyles((CheckedTextView) view, mStyles);   
                    return view;
                }
                @Override
                public View getDropDownView(int position, View convertView, ViewGroup parent) {
                    View view = super.getDropDownView(position, convertView, parent);
                     
                    GradientDrawable drawable = new GradientDrawable();
                    drawable.setShape(GradientDrawable.RECTANGLE);
                    if (mItemStyles != null && mItemStyles.hasKey("backgroundColor")) {
                       drawable.setColor(Color.parseColor(mItemStyles.getString("backgroundColor"))); 
                    }   
                    view.setBackground(drawable);                    
                    if (mStyles != null && mStyles.hasKey("height")) {
                      ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) view.getLayoutParams();
                      if (params != null) {
                          params.height = mItemStyles.getInt("height");
                          view.setLayoutParams(params);
                      }
                    }
                    
                    addStyles((CheckedTextView) view, mItemStyles); 
                    return view;
                }
            }
        );
    }    

    void addStyles(CheckedTextView view, ReadableMap style) {
        if (style == null) return;

        String key = null;
        ReadableMapKeySetIterator iterator = style.keySetIterator();       
        GradientDrawable drawable = ((GradientDrawable) view.getBackground());

        if (drawable == null) {
            drawable = ((GradientDrawable) getBackground());
        }
        
        while (iterator.hasNextKey()) {
            key = iterator.nextKey();
            switch(key) {
                case "padding": {
                  Dynamic value = style.getDynamic(key);
                  int left = 0, top = 0, right = 0, bottom = 0;
                  if (value.getType() == ReadableType.Number) {
                    left = top = right = bottom = value.asInt();
                  } else if (value.getType() == ReadableType.Array) { 
                    ReadableArray array = value.asArray();                   
                    left = array.getInt(0);
                    top = array.getInt(1);
                    right = array.getInt(2);
                    bottom = array.getInt(3);                    
                  }   
                  view.setPadding(left, top, right, bottom);
                  break;
                } 
                case "borderWidth": {
                    Dynamic value = style.getDynamic(key);
                    String color = style.getString("borderColor");  

                    int max = 0, left = -2, top = -2, right = -2, bottom = -2;
                    if (value.getType() == ReadableType.Number) {
                        max = left = top = right = bottom = value.asInt();
                    } else if (value.getType() == ReadableType.Array) { 
                        ReadableArray array = value.asArray();                   
                        left = array.getInt(0);
                        top = array.getInt(1);
                        right = array.getInt(2);
                        bottom = array.getInt(3);   
                        max = Math.max(Math.max(left, top), Math.max(right, bottom));                 
                    }  
                    
                    if (color == null) {
                        color = "#aaaaaa";
                    }  

                    drawable.setStroke(max, Color.parseColor(color));
                    if (style == mItemStyles) {
                        Drawable[] layers = {drawable};
                        LayerDrawable layerDrawable = new LayerDrawable(layers);
                        layerDrawable.setLayerInset(0, left, top, right, bottom);
                        view.setBackground(layerDrawable); 
                    }
                    break;
                }
                case "fontSize": {
                  view.setTextSize(style.getInt(key));
                  break;
                }
                case "color": {
                  view.setTextColor(Color.parseColor(style.getString(key)));
                  break;
                }
                case "borderRadius": {
                  Dynamic value = style.getDynamic(key);
                  
                  if (value.getType() == ReadableType.Number) {
                    float r = (float) value.asDouble();
                    ((GradientDrawable) getBackground()).setCornerRadius(r);
                  } else if (value.getType() == ReadableType.Array) {
                    float r0, r1, r2, r3;
                    ReadableArray array = value.asArray();
                    r0 = (float) array.getDouble(0);
                    r1 = (float) array.getDouble(1);
                    r2 = (float) array.getDouble(2);
                    r3 = (float) array.getDouble(3);
                    drawable.setCornerRadii(new float[] { r0, r0, r1, r1, r2, r2, r3, r3 });
                  }                 
                  break;
                }
            }
        }
    }    

    @Override
    protected void onDraw(Canvas canvas) {

        int w = canvas.getWidth(), h = canvas.getHeight();

        if (this.mCaret != null && w > 0 && h > 0) { 
            Paint trianglePaint = new Paint();
            Path path = new Path();
           
            float size = (float) this.mCaret.getDouble("size");
            float right = (float) this.mCaret.getDouble("right");
            String color = this.mCaret.getString("color");  
            if (size == 0) size = 20;
            if (color == null) color = "black"; 
            
            trianglePaint.setStyle(Style.FILL);
            trianglePaint.setColor(Color.parseColor(color));

            path.moveTo(w - size - right, h/2 - size/2);
            path.lineTo(w - right, h/2 - size/2);
            path.lineTo(w - size/2 - right, h/2 + size/2);
       
            canvas.save();  
            canvas.drawPath(path, trianglePaint);                                                    
            canvas.restore();
        }
    }

    public void onReceiveNativeEvent(String item) {
        WritableMap event = Arguments.createMap();
        event.putString("selected", item);
        ReactContext reactContext = (ReactContext)getContext();
        reactContext.getJSModule(RCTEventEmitter.class).receiveEvent(getId(), "selection", event);
    }
}