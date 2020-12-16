package top.zhzhao.myclock.util;

public class UnicodeUtils {

    //Unicode转中文方法
    public static String unicodeToCn(String unicode) {
        /* 以 \ u 分割，因为java注释也能识别unicode，因此中间加了一个空格*/
        String[] strs = unicode.split("\\\\u");
        StringBuilder returnStr = new StringBuilder();
        // 由于unicode字符串以 \ u 开头，因此分割出的第一个字符是""。
        for (int i = 1; i < strs.length; i++) {
            returnStr.append((char) Integer.valueOf(strs[i], 16).intValue());
        }
        return returnStr.toString();
    }

    //中文转Unicode
    public static String cnToUnicode(String cn) {
        char[] chars = cn.toCharArray();
        StringBuilder returnStr = new StringBuilder();
        for (char aChar : chars) {
            returnStr.append("\\u").append(Integer.toString(aChar, 16));
        }
        return returnStr.toString();
    }
}
