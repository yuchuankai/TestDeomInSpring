package 正则表达式;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @CreateTime: 2025年 03月 06日 15:08
 * @Description:
 * @Author: MR.YU
 */
public class main {


    public static void main(String[] args) {
//        String value = "test(公开).docx";
//        System.out.println(Pattern.quote(value));
        String regex = processConditionSuffix("doc", 2);
        System.out.println(regex);
        Pattern pattern = Pattern.compile(regex);
        String str1 = "test(公开).doc";
        String str2 = "test(公开).docx";
        System.out.println(pattern.matcher(str1).find());
        System.out.println(pattern.matcher(str2).find());
    }



    private static String processConditionSuffix(String value, int comparator) {
        String regex = "";
        switch (comparator) {
            case 1:
                regex = "^.*\\Q" + value + "\\E$";
                break;
            case 2:
                regex = "^(?!.*\\Q" + value + "\\E$).*$";
                break;
            case 3:
                regex = ".*\\Q" + value + "\\E.*$";
                break;
            case 4:
                regex = "^(?!.*\\Q" + value + "\\E).*$";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + comparator);
        }
        return regex;
    }

    private static boolean isMatch(boolean regexRes, boolean matchUpload) {
        if (matchUpload) {
            return regexRes;
        } else {
            return !regexRes;
        }
    }
}
