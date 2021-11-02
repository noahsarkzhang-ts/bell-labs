package org.noahsrak.nacos.replacement;

import org.apache.commons.lang3.StringUtils;
import org.noahsrak.nacos.transformation.DynamiConfigItem;

import java.util.List;

/**
 * @author: zhangxt
 * @version:
 * @date: 2021/10/21
 */
public class ConfigReplacement {

    public static String replace(String content, List<DynamiConfigItem> items) {

         String target = content;

         for (DynamiConfigItem item : items) {
             String escapeWord = escapeExprSpecialWord(item.getSource());
             target = target.replaceAll(escapeWord,item.getTarget());
         }

        return target;
    }

    /**
     * 转义正则特殊字符 （$()*+.[]?\^{},|）
     *
     * @param keyword
     * @return
     */
    private static String escapeExprSpecialWord(String keyword) {
        if (StringUtils.isNotBlank(keyword)) {
            String[] fbsArr = { "\\", "$", "(", ")", "*", "+", ".", "[", "]", "?", "^", "{", "}", "|" };
            for (String key : fbsArr) {
                if (keyword.contains(key)) {
                    keyword = keyword.replace(key, "\\" + key);
                }
            }
        }
        return keyword;
    }

    public static void main(String[] args) {

        String command = "hostname : {{ hostname }} ";

        System.out.println("regex = " + "\\{\\{ hostname \\}\\}");
        
        String newCommand = command.replaceAll("\\{\\{ hostname \\}\\}","noahsark");

        System.out.println("newCommand = " + newCommand);

    }
}
