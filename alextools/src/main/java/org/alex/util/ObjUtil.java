package org.alex.util;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 作者：Alex
 * 时间：2016年09月03日    10:09
 * 简述：
 */

@SuppressWarnings("all")
public class ObjUtil
{
    public static boolean isEmpty(Object obj){
        if(obj == null){
            return true;
        }
        if((obj instanceof String) && (((String) obj).length() == 0)){
            return true;
        }
        if(obj instanceof Map){
            return isMapEmpty((Map<?, ?>) obj);
        }
        if(obj instanceof List){
            return isListEmpty((List<?>) obj);
        }
        if (obj instanceof Object[]) {
            return (((Object[])obj).length == 0);
        }
        if(obj instanceof Bitmap){
            return isBitmapEmpty((Bitmap) obj);
        }
        if(obj instanceof Drawable){
            return isDrawableEmpty((Drawable) obj);
        }
        return false;
    }
    public static boolean isNotEmpty(Object obj){
        return !isEmpty(obj);
    }
    private static boolean isListEmpty(List<?> list){
        if((list == null) || list.isEmpty()){
            return true;
        }
        return false;
    }
    private static boolean isMapEmpty(Map<?,?> map){
        if((map == null) || map.isEmpty()){
            return true;
        }
        return false;
    }
    private static boolean isBitmapEmpty(Bitmap bitmap){
        if((bitmap == null) || (bitmap.getHeight()<=0) || (bitmap.getWidth()<=0)){
            return true;
        }
        return false;
    }
    private static boolean isDrawableEmpty(Drawable drawable){
        if((drawable == null) || (drawable.getIntrinsicHeight()<=0) || (drawable.getIntrinsicWidth()<=0)){
            return true;
        }
        return false;
    }
    private static boolean isStringEmpty(String text){
        if((text == null) || (text.length()>0)){
            return true;
        }
        return false;
    }
    public static String toString(List<?> list, String split){
        StringBuilder builder = new StringBuilder();
        builder.append("");
        for (int i = 0; (list!=null) && (i<list.size()); i++){
            if(i<(list.size()-1)){
                builder.append(list.get(i)+split);
            }else{
                builder.append(list.get(i)+"");
            }
        }
        return builder.toString();
    }
    public static String toString(List<?> list){
        return toString(list, ",");
    }
    public static String[] toStringArray(List<?> list){
        if((list==null) || (list.size()<=0)){
            return new String[0];
        }
        String array[] = new String[list.size()];
        for (int i = 0; (list!=null) && (i<list.size()); i++){
            array[i] = list.get(i)+"";
        }
        return array;
    }
    @SuppressWarnings("unchecked")
    public static <T> T[] toArray(List<T> list){
        if((list==null) || (list.size()<=0)){
            return (T[]) new Object[0];
        }
        Object array[] = new Object[list.size()];
        for (int i = 0; (list!=null) && (i<list.size()); i++){
            array[i] = list.get(i)+"";
        }
        return (T[]) array;
    }
    public static <T> List<T> toList(T t[]){
        if((t==null) || (t.length<=0)){
            return new ArrayList<>();
        }
        List<T> list = new ArrayList<T>();
        for (int i = 0; (t!=null) && (i<t.length); i++){
            list.add(t[i]);
        }
        return list;
    }

}

