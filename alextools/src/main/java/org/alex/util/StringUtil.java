package org.alex.util;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 作者：Alex
 * 时间：2016年09月03日    10:14
 * 简述：
 */

@SuppressWarnings("all")
public class StringUtil
{
    private static final char CHAR_CHINESE_SPACE = '\u3000';//中文（全角）空格
    /**判断String是null 或 ""*/
    public static boolean isEmpty(String text) {
        return (text == null || text.length() == 0);
    }
    /**变成首字母大写
     * <br/>capitalizeFirstLetter(null)     =   null;
     * <br/>capitalizeFirstLetter("")       =   "";
     * <br/>capitalizeFirstLetter("2ab")    =   "2ab"
     * <br/>capitalizeFirstLetter("a")      =   "A"
     * <br/>capitalizeFirstLetter("ab")     =   "Ab"
     * <br/>capitalizeFirstLetter("Abc")    =   "Abc"
     */
    public static String becomeCapitalized(String text)
    {
        if (isEmpty(text)){
            return text;
        }
        char c = text.charAt(0);
        return (!Character.isLetter(c) || Character.isUpperCase(c)) ? text : new StringBuilder(text.length()).append(Character.toUpperCase(c)).append(text.substring(1)).toString();
    }
    /**全角符号--->半角符号
     * <br/>fullWidthToHalfWidth(null) = null;
     * <br/>fullWidthToHalfWidth("") = "";
     * <br/>fullWidthToHalfWidth(new String(new char[] {12288})) = " ";
     * <br/>fullWidthToHalfWidth("！＂＃＄％＆) = "!\"#$%&";
     */
    public static String fullWidthToHalfWidth(String text) {
        if (isEmpty(text)) {
            return text;
        }
        char[] source = text.toCharArray();
        for (int i = 0; i < source.length; i++) {
            if (source[i] == 12288) {
                source[i] = ' ';
            } else if (source[i] >= 65281 && source[i] <= 65374) {
                source[i] = (char)(source[i] - 65248);
            } else {
                source[i] = source[i];
            }
        }
        return new String(source);
    }

    /**半角符号---->全角符号
     * <br/>halfWidthToFullWidth(null) = null;
     * <br/>halfWidthToFullWidth("") = "";
     * <br/>halfWidthToFullWidth(" ") = new String(new char[] {12288});
     * <br/>halfWidthToFullWidth("!\"#$%&) = "！＂＃＄％＆";
     */
    public static String halfWidthToFullWidth(String text) {
        if (isEmpty(text)) {
            return text;
        }
        char[] source = text.toCharArray();
        for (int i = 0; i < source.length; i++) {
            if (source[i] == ' ') {
                source[i] = (char)12288;
            } else if (source[i] >= 33 && source[i] <= 126) {
                source[i] = (char)(source[i] + 65248);
            } else {
                source[i] = source[i];
            }
        }
        return new String(source);
    }
    /**判断是纯数字 [0-9]
     * <br/>不匹配, 返回false
     */
    public static boolean isAllNumber(String text)
    {
        if (text != null && !text.trim().equalsIgnoreCase("")) {
            Pattern pattern = Pattern.compile("^[0-9]*$");
            Matcher matcher = pattern.matcher(text);
            if (matcher.matches()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    /**判断是纯数字 [0-9]    正数、负数、和小数
     * <br/>不匹配, 返回false
     */
    public static boolean isAllNumberWithDecimal(String text)
    {
        if (text != null && !text.trim().equalsIgnoreCase("")) {
            Pattern pattern = Pattern.compile("^(\\-|\\+)?\\d+(\\.\\d+)?$");
            Matcher matcher = pattern.matcher(text);
            if (matcher.matches()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    /**判断是纯数字 [0-9]    带1-2位小数的正数或负数
     * <br/>不匹配, 返回false
     */
    public static boolean isAllNumricWithDecimalAtBetween(String text, int min, int max)
    {
        if (text != null && !text.trim().equalsIgnoreCase("")) {
            Pattern pattern = Pattern.compile("^(\\-)?\\d+(\\.\\d{"+min+","+max+"})?$");
            Matcher matcher = pattern.matcher(text);
            if (matcher.matches()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    /**判断是纯数字 [0-9]  正好 length 个
     * <br/>不匹配, 返回false
     */
    public static boolean isAllNumberWithLength(String text, int length)
    {
        if (text != null && !text.trim().equalsIgnoreCase("")) {
            Pattern pattern = Pattern.compile("^\\d{"+length+"}$");
            Matcher matcher = pattern.matcher(text);
            if (matcher.matches()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    /**判断是纯数字 [0-9]  正好 length 个
     * <br/>不匹配, 返回false
     */
    public static boolean isAllNumberWithLengthAtLeast(String text, int length)
    {
        if (text != null && !text.trim().equalsIgnoreCase("")) {
            Pattern pattern = Pattern.compile("^\\d{"+length+",}$");
            Matcher matcher = pattern.matcher(text);
            if (matcher.matches()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    /**判断是纯数字 [0-9]  正好 length 个
     * <br/>不匹配, 返回false
     */
    public static boolean isAllNumberWithLengthAtBetween(String text, int min, int max)
    {
        if (text != null && !text.trim().equalsIgnoreCase("")) {
            Pattern pattern = Pattern.compile("^\\d{"+min+","+max+"}$");
            Matcher matcher = pattern.matcher(text);
            if (matcher.matches()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**判断是纯英文字母, [a-z,A-Z]
     * <br/>不匹配, 返回false
     */
    public static boolean isAllLetters(String text)
    {
        if (text != null && !text.trim().equalsIgnoreCase("")) {
            Pattern pattern = Pattern.compile("[A-Za-z]+");
            Matcher matcher = pattern.matcher(text);
            if (matcher.matches()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    /**判断是纯汉字
     * <br/>不匹配, 返回false
     */
    public static boolean isAllChinese(String text)
    {
        if (text != null && !text.trim().equalsIgnoreCase(""))
        {
            Pattern pattern = Pattern.compile("[\u4E00-\u9FA5\uF900-\uFA2D]+");
            Matcher matcher = pattern.matcher(text);
            if (matcher.matches()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    public static boolean isOnlyEnglishAndNum(String text){
        if (text != null && !text.trim().equalsIgnoreCase(""))
        {
            Pattern pattern = Pattern.compile("^[A-Za-z0-9]+");
            Matcher matcher = pattern.matcher(text);
            if (matcher.matches()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    /**仅仅 包含 汉字 和 数字
     * <br/>不匹配, 返回false
     */
    public static boolean isOnlyChineseAndNum(String text)
    {
        if (text != null && !text.trim().equalsIgnoreCase(""))
        {
            Pattern pattern = Pattern.compile("[\u4E00-\u9FA5\uF900-\uFA2D0-9]+");
            Matcher matcher = pattern.matcher(text);
            if (matcher.matches()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    /**判断含有汉字
     * <br/>不匹配, 返回false
     */
    public static boolean isHaveChinese(String text)
    {
        if (text != null && !text.trim().equalsIgnoreCase(""))
        {
            for (int i = 0; i < text.length(); i++) {
                char ss = text.charAt(i);
                boolean s = String.valueOf(ss).matches("[\u4E00-\u9FA5\uF900-\uFA2D]");
                if (s) {
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }
    }
    /**判断字符个数, 是否满足长度范围[min, max]; 所有的文字,都算作一个字符
     *  <br/>不匹配, 返回false
     */
    public static boolean isNumOverLengthOK(String text, int min,int max) {
        return isAllNumber(text) & isPwdLengthOK(text, min, max);
    }
    /**判断字符个数, 是否满足长度范围[min, max]; 所有的文字,都算作一个字符
     *  <br/>不匹配, 返回false
     */
    public static boolean isPwdLengthOK(String text, int min,int max)
    {
        if (text != null && !text.trim().equalsIgnoreCase("")) {
            String Regular = "^[0-9a-zA-Z]\\w{"+(min-1)+","+(max-1)+"}$";
            Pattern pattern = Pattern.compile(Regular, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(text);
            if (matcher.matches()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    /**判断字符个数, 是否满足长度范围[min, max]; 所有的文字,都算作一个字符
     *  <br/>不匹配, 返回false
     */
    public static boolean isLengthOK(String text, int min,int max)
    {
        if (text != null && !text.trim().equalsIgnoreCase("")) {
            String Regular = "^[\\u4E00-\\u9FA5\\uF900-\\uFA2D\\w]{"+min+"," + max + "}$";
            Pattern pattern = Pattern.compile(Regular, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(text);
            if (matcher.matches()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    /**长度为[min, max]的所有字符
     *  <br/>不匹配, 返回false
     */
    public static boolean isLengthOKWithAllWords(String text, int min,int max)
    {
        if (text != null && !text.trim().equalsIgnoreCase("")) {
            String Regular = "^.{"+min+","+max+"}$";
            Pattern pattern = Pattern.compile(Regular, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(text);
            if (matcher.matches()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    /**长度为[min, max]的   由数字、26个英文字母或者下划线组成的字符串
     *  <br/>不匹配, 返回false
     */
    public static boolean isLengthOKWithAllPwdWords(String text, int min,int max)
    {
        if (text != null && !text.trim().equalsIgnoreCase("")) {
            String Regular = "^\\w{"+min+","+max+"}$";
            Pattern pattern = Pattern.compile(Regular, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(text);
            if (matcher.matches()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    /**验证邮箱合法性
     * <br/>不匹配, 返回false
     */
    public static boolean isEmail(String text)
    {
        if (text != null && !text.trim().equalsIgnoreCase("")) {
            String Regular = "^([a-z0-9A-Z]+[-|\\_.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern pattern = Pattern.compile(Regular, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(text);
            if (matcher.matches()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    /**验证QQ号码合法性, [min, max]
     */
    public static boolean isQQ(String text, int min, int max)
    {
        if (text != null && !text.trim().equalsIgnoreCase("")) {
            Pattern pattern = Pattern.compile("[1-9][0-9]{"+(min-1)+","+(max-1)+"}");
            Matcher matcher = pattern.matcher(text);
            if (matcher.matches()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    public static boolean isPhoneNum(String text)
    {
        boolean flag = false;
        try
        {
            Pattern p = Pattern.compile("(1)[0-9]{10}");
            Matcher m = p.matcher(text);
            flag = m.matches();
        }catch(Exception e){
            flag = false;
        }
        return flag;
    }
    /**
     * @param text
     * @param startLength 从第0个，开始 startLength 个保留
     * @param endLength 从最后一个开始 往前endLength 个保留
     * @param star 替换符
     * */
    public static String replace(String text, int startLength, int endLength, String star){
        if((text == null) || (startLength <0) || (endLength > text.length())){
            return text;
        }
        String start3 = text.substring(0, startLength);
        String end2 = text.substring(text.length()-endLength, text.length());
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i<(text.length() - startLength - endLength); i++){
            builder.append(star);
        }
        return start3+builder.toString()+end2;
    }
    /**在str中找到所有满足regex的匹配子串, 组成集合
     * <br/>常用正则匹配见 {@link #commonRegex()} 方法
     */
    public static ArrayList<String> getRegexMatcherResults(String text, String regex) {
        try {
            if (text != null && !text.trim().equalsIgnoreCase("")) {
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(text);
                ArrayList<String> RegexMatcherResults = new ArrayList<String>();
                while (matcher.find()) {
                    RegexMatcherResults.add(matcher.group(1));
                }
                return RegexMatcherResults;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    /**通过正则表达式提取字串
     * @param text 传入的字符串
     * @return 要获取的字符串
     */
    public static String getRegexString(String text) {
        String strReturn = "";
        int a = text.indexOf("{");
        int b = text.lastIndexOf("}");
        strReturn = text.substring(a, b + 1);
        return strReturn;
    }
    public static boolean isRightPwd(String text)
    {
        boolean flag = false;
        try
        {
            Pattern p = Pattern.compile("^[0-9a-zA-Z_]{6,18}");
            Matcher m = p.matcher(text);
            flag = m.matches();
        }catch(Exception e){
            flag = false;
        }
        return flag;
    }
    /**
     * 描述：是字母和数字.
     * @param text 指定的字符串
     * @return 是否只是字母和数字:是为true，否则false
     */
    public static Boolean isNumberLetter(String text) {
        Boolean isNoLetter = false;
        String expr = "^[A-Za-z0-9]+$";
        if (text.matches(expr)) {
            isNoLetter = true;
        }
        return isNoLetter;
    }
    /**
     * 从字符串s中截取某一段字符串
     * @param text
     * @param startToken 开始标记
     * @param endToken 结束标记
     * @return
     */
    public static String mid(String text, String startToken, String endToken) {
        return mid(text, startToken, endToken, 0);
    }
    public static String mid(String text, String startToken, String endToken, int fromStart) {
        if (startToken==null || endToken==null)
            return null;
        int start = text.indexOf(startToken, fromStart);
        if (start==(-1))
            return null;
        int end = text.indexOf(endToken, start + startToken.length());
        if (end==(-1))
            return null;
        String sub = text.substring(start + startToken.length(), end);
        return sub.trim();
    }
    /**
     * 简化字符串，删除空格键、tab键、换行键
     * @param text
     * @return
     */
    public static String compact(String text) {
        char[] cs = new char[text.length()];
        int len = 0;
        for(int n=0; n<cs.length; n++) {
            char c = text.charAt(n);
            if(c==' ' || c=='\t' || c=='\r' || c=='\n' || c==CHAR_CHINESE_SPACE)
                continue;
            cs[len] = c;
            len++;
        }
        return new String(cs, 0, len);
    }
    /**编码*/
    public static String encode(String text) {
        try
        {
            return URLEncoder.encode(text, "UTF-8");
        } catch (Exception e){
            Log.e("Url","有异常："+e);
        }
        return text;
    }
    /**
     * 求两个字符串数组的并集，利用set的元素唯一性
     * @param arr1
     * @param arr2
     * @return
     */
    public static String[] union(String[] arr1, String[] arr2) {
        Set<String> set = new HashSet<String>();
        for (String str : arr1) {
            set.add(str);
        }
        for (String str : arr2) {
            set.add(str);
        }
        String[] result = {};
        return set.toArray(result);
    }

    /**
     * 求两个字符串数组的交集
     * @param arr1
     * @param arr2
     * @return
     */
    public static String[] intersect(String[] arr1, String[] arr2) {
        Map<String, Boolean> map = new HashMap<String, Boolean>();
        LinkedList<String> list = new LinkedList<String>();
        for (String str : arr1) {
            if (!map.containsKey(str)) {
                map.put(str, Boolean.FALSE);
            }
        }
        for (String str : arr2) {
            if (map.containsKey(str)) {
                map.put(str, Boolean.TRUE);
            }
        }
        for (Map.Entry<String, Boolean> e : map.entrySet()) {
            if (e.getValue().equals(Boolean.TRUE)) {
                list.add(e.getKey());
            }
        }
        String[] result = {};
        return list.toArray(result);
    }
    /**
     * 求两个字符串数组的差集
     * @param arr1
     * @param arr2
     * @return
     */
    public static String[] minus(String[] arr1, String[] arr2) {
        LinkedList<String> list = new LinkedList<String>();
        LinkedList<String> history = new LinkedList<String>();
        String[] longerArr = arr1;
        String[] shorterArr = arr2;
        //找出较长的数组来减较短的数组
        if (arr1.length > arr2.length) {
            longerArr = arr2;
            shorterArr = arr1;
        }
        for (String str : longerArr) {
            if (!list.contains(str)) {
                list.add(str);
            }
        }
        for (String str : shorterArr) {
            if (list.contains(str)) {
                history.add(str);
                list.remove(str);
            } else {
                if (!history.contains(str)) {
                    list.add(str);
                }
            }
        }

        String[] result = {};
        return list.toArray(result);
    }
    /**
     * 字符串数组反转
     * @param textAry
     * @return
     */
    public static String[] reverse(String[] textAry) {
        for (int i = 0; i < textAry.length; i++) {
            String top = textAry[0];
            for (int j = 1; j < textAry.length - i; j++) {
                textAry[j - 1] = textAry[j];
            }
            textAry[textAry.length - i - 1] = top;
        }
        return textAry;
    }
    /**产生 length 个随机数 并组成一个 String*/
    public static String getRandomStringWithSpan(int length){
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++){
            Random random = new Random();
            builder.append(random.nextInt(10)+" ");
        }
        return builder.toString().substring(0, builder.length()-1);
    }
    public static double string2Double(String text, double error){
        if(TextUtils.isEmpty(text)){
            Log.e(StringUtil.class.getSimpleName(), "text == null");
            return error;
        }
        try
        {
            return Double.parseDouble(text);
        } catch (Exception e){
            Log.e(StringUtil.class.getSimpleName(), "有异常："+e);
            return error;
        }
    }
    /**截取 111.00 中的整数部分 111*/
    public static String getIntPart(String text){
        String tmp = "0";
        if(TextUtils.isEmpty(text)){
            return tmp;
        }
        if(!text.contains(".")){
            return text;
        }
        try
        {
            tmp = text.split("\\.")[0];
        } catch (Exception e){
            return tmp;
        }
        return tmp;
    }
    public static int string2Int(String text, int error){
        try
        {
            return Integer.parseInt(text);
        } catch (Exception e){
            return error;
        }
    }
    public static float string2Float(String text, float error){
        try
        {
            return Float.parseFloat(text);
        } catch (Exception e){
            return error;
        }
    }
    /**
     * 将字符串转成MD5值
     *
     * @param string
     * @return
     */
    public static String string2MD5(String string) {
        byte[] hash;

        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10)
                hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }

        return hex.toString();
    }

    /**
     * 从 最后一个 split开始，到text结束的字串（不包含split）
     */
    public static String subString4End(@NonNull String text, String split) {
        if (TextUtils.isEmpty(text)) {
            return "";
        }
        int lastIndexOf = text.lastIndexOf(split);
        if (lastIndexOf < 0) {
            return "";
        }
        return text.substring(lastIndexOf + 1, text.length());
    }
    /**
     * 从 第一个，到split结束的字串（不包含split）
     */
    public static String subString4Start(@NonNull String text, String split) {
        if (TextUtils.isEmpty(text)) {
            return "";
        }
        int lastIndexOf = text.lastIndexOf(split);
        if (lastIndexOf < 0) {
            return "";
        }
        return text.substring(0, lastIndexOf);
    }
    /**常用正则表达式
     * <pre>
     * 常用正则表达式代码提供 验证数字: ^[0-9]*$ <br>
     * 验证n位的数字: ^\d{n}$ <br>
     * 验证至少n位数字: ^\d{n,}$ <br>
     * 验证m-n位的数字: ^\d{m,n}$ <br>
     * 验证零和非零开头的数字: ^(0|[1-9][0-9]*)$ <br>
     * 验证有两位小数的正实数: ^[0-9]+(.[0-9]{2})?$ <br>
     * 验证有1-3位小数的正实数: ^[0-9]+(.[0-9]{1,3})?$ <br>
     * 验证非零的正整数: ^\+?[1-9][0-9]*$ <br>
     * 验证非零的负整数: ^\-[1-9][0-9]*$ <br>
     * 验证非负整数（正整数 + 0） ^\d+$ <br>
     * 验证非正整数（负整数 + 0） ^((-\d+)|(0+))$ <br>
     * 验证长度为3的字符: ^.{3}$ <br>
     * 验证由26个英文字母组成的字符串: ^[A-Za-z]+$ <br>
     * 验证由26个大写英文字母组成的字符串: ^[A-Z]+$ <br>
     * 验证由26个小写英文字母组成的字符串: ^[a-z]+$ <br>
     * 验证由数字和26个英文字母组成的字符串: ^[A-Za-z0-9]+$ <br>
     * 验证由数字、26个英文字母或者下划线组成的字符串: ^\w+$ <br>
     * 验证用户密码:^[a-zA-Z]\w{5,17}$ 正确格式为: 以字母开头，长度在6-18之间，只能包含字母、数字和下划线。 <br>
     * 验证是否含有 ^%&',;=?$\" 等字符: [^%&',;=?$\x22]+ <br>
     * 验证汉字: ^[\u4E00-\u9FA5\uF900-\uFA2D]+$ <br>
     * 验证Email地址: ^\w+[-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$ <br>
     * 验证InternetURL: ^http://([\w-]+\.)+[\w-]+(/[\w-./?%&=]*)?$
     * ；^[a-zA-z]+://(w+(-w+)*)(.(w+(-w+)*))*(?S*)?$ <br>
     * 验证手机号码: ^(\(\d{3,4}\)|\d{3,4}-)?\d{7,8}$: --正确格式为: XXXX-XXXXXXX，XXXX-
     * XXXXXXXX，xx-XXXXXXXX，XXXXXXX，XXXXXXXX。 <br>
     * 验证身份证号（15位或18位数字）: ^\d{15}|\d{}18$ <br>
     * 验证一年的12个月: ^(0?[1-9]|1[0-2])$ 正确格式为: “01”-“09”和“1”“12” <br>
     * 验证一个月的31天: ^((0?[1-9])|((1|2)[0-9])|30|31)$ 正确格式为: 01、09和1、31。 <br>
     * 整数: ^-?\d+$ <br>
     * 非负浮点数（正浮点数 + 0）: ^\d+(\.\d+)?$ <br>
     * 正浮点数
     * ^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9
     * ][0-9]*))$ <br>
     * 非正浮点数（负浮点数 + 0） ^((-\d+(\.\d+)?)|(0+(\.0+)?))$ <br>
     * 负浮点数
     * ^(-(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1
     * -9][0-9]*)))$ <br>
     * 浮点数 ^(-?\d+)(\.\d+)?$ <br>
     * </pre>
     */
    public void commonRegex(){

    }
}

