package com.zone.lib.utils.data.check;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * String Utils
 * 
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2011-7-22
 */
public class StringCheck {

    private StringCheck() {
        throw new AssertionError();
    }

    /**
     * is null or its length is 0 or it is made by space
     * 
     * <pre>
     * isBlank(null) = true;
     * isBlank(&quot;&quot;) = true;
     * isBlank(&quot;  &quot;) = true;
     * isBlank(&quot;a&quot;) = false;
     * isBlank(&quot;a &quot;) = false;
     * isBlank(&quot; a&quot;) = false;
     * isBlank(&quot;a b&quot;) = false;
     * </pre>
     * 
     * @param str
     * @return if string is null or its size is 0 or it is made by space, return true, else return false.
     */
    public static boolean isBlank(String str) {
        return (str == null || str.trim().length() == 0);
    }

    public static boolean isEmptyTrim(String str) {
        return str == null ||( str.length() == 0 || str.trim().length() == 0);
    }
    /**
     * compare two string
     * 
     * @param base
     * @param dst
     * @return
     */
    public static boolean isEquals(String base, String dst) {
        return  base != null && base.equals(dst);
    }

    /**
     * encoded in utf-8
     * 
     * <pre>
     * utf8Encode(null)        =   null
     * utf8Encode("")          =   "";
     * utf8Encode("aa")        =   "aa";
     * utf8Encode("啊啊啊啊")   = "%E5%95%8A%E5%95%8A%E5%95%8A%E5%95%8A";
     * </pre>
     * 
     * @param str
     * @return
     * @throws UnsupportedEncodingException if an error occurs
     */
    public static String utf8Encode(String str) {
        return utf8Encode(str,null);
    }

    /**
     * encoded in utf-8, if exception, return defultReturn
     * 
     * @param str
     * @param defultReturn
     * @return
     */
    public static String utf8Encode(String str, String defultReturn) {
        if (!isEmptyTrim(str) && str.getBytes().length != str.length())
            try {
                return URLEncoder.encode(str, "UTF-8");
            } catch (UnsupportedEncodingException e){
                if (defultReturn!=null)
                    return defultReturn;
                else
                    throw new RuntimeException("UnsupportedEncodingException occurred. ", e);
            }
        return str;
    }


}
