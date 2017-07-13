package com.zone.lib.utils.data.file2io2data;

import java.util.HashMap;

/**
 * Created by fuzhipeng on 16/9/27.
 */

public class HashMapZ extends HashMap{
    @Override
    public Object get(Object key) {
        return super.get(key);
    }

    @Override
    public Object put(Object key, Object value) {
        return super.put(key, value);
    }

   public boolean getBoolean(String key, boolean defValue){
       return super.get(key)!=null? (boolean) super.get(key) :defValue;
   }
   public int getInt(String key, int defValue){
       return super.get(key)!=null? (int) super.get(key) :defValue;
   }
   public long getLong(String key, long defValue){
       return super.get(key)!=null? (long) super.get(key) :defValue;
   }
   public float getFloat(String key, float defValue){
       return super.get(key)!=null? (float) super.get(key) :defValue;
   }
   public String getString(String key, String defValue){
       return super.get(key)!=null? (String) super.get(key) :defValue;
   }

   public HashMapZ putBoolean(String key, boolean value){
       super.put(key, value);
       return this;
   }
   public HashMapZ putInt(String key, int value){
       super.put(key, value);
       return this;
   }
   public HashMapZ putLong(String key, long value){
       super.put(key, value);
       return this;
   }
   public HashMapZ putFloat(String key, float value){
       super.put(key, value);
       return this;
   }
   public HashMapZ putString(String key, String value){
        super.put(key, value);
       return this;
   }
}
