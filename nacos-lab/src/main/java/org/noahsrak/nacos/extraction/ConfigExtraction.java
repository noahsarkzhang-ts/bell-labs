package org.noahsrak.nacos.extraction;

import org.noahsrak.nacos.transformation.DynamiConfigItem;
import org.noahsrak.nacos.transformation.ItemType;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: zhangxt
 * @version:
 * @date: 2021/10/19
 */
public class ConfigExtraction {

    /**
     *  占位符：
     * 1. 取环境变量：{{ hostname }},
     * 2. url: {{ URL:{http://config.hst.com/test?hostname=#(hostname)} }}
     * 3. shell命令：{{ SHELL:{hostname -l} }}
     */
    private static final String ITEM_PATTERN = "\\{\\{\\s*(.*?)\\s*\\}\\}";

    /**
     *  占位符类型：ENV,URL,SHELL
     */
    private static final String TYPE_PATTERN = "(.*?)\\s*:\\s*\\{(.*?)\\}(.*?)";

    public static List<DynamiConfigItem> extract(String content) {
        List<DynamiConfigItem> items = new ArrayList<>();

        // 创建 Pattern 对象
        Pattern itemPattern = Pattern.compile(ITEM_PATTERN);
        Pattern typePattern = Pattern.compile(TYPE_PATTERN);

        // 现在创建 matcher 对象
        Matcher iteMatcher = itemPattern.matcher(content);
        while (iteMatcher.find()) {
            DynamiConfigItem item = new DynamiConfigItem();

            String source = iteMatcher.group(0);
            String command = iteMatcher.group(1);
            item.setSource(source);

            Matcher commandMatcher = typePattern.matcher(command);
            if (commandMatcher.find()) {
                String commandType = commandMatcher.group(1);
                if (ItemType.valueOf(commandType) != null) {
                    item.setType(ItemType.valueOf(commandType));
                    item.setCommand(commandMatcher.group(2));
                } else {
                    System.out.println("No supported Type:" + commandType);
                }

            } else {
                item.setType(ItemType.ENV);
                item.setCommand(command);
            }

            items.add(item);
        }

        return items;
    }

    public static void main(String[] args) {
        String content = "{{ hostname}} /r/n"
                + "{{ URL:{http://config.hst.com/test?hostname=#(hostname)} }} /r/n"
                + "{{ SHELL:{hostname -l} }}";

        List<DynamiConfigItem> items = extract(content);

        items.stream().forEach(item -> System.out.println(item));

    }

    private static void RegexTest() {
        // 按指定模式在字符串查找
        String line = "{{ hostname }} \r\n " +
                "{{ip}}";
        // + "{{ ip }}";
        //String pattern = "(\\D*)(\\d+)(.*)";
        String pattern = "\\{\\{\\s*((.*?))\\s*\\}\\}";


        // 创建 Pattern 对象
        Pattern r = Pattern.compile(pattern);

        // 现在创建 matcher 对象
        Matcher m = r.matcher(line);
        while (m.find()) {
            System.out.println("Found value: " + m.group(0));
            System.out.println("Found value: " + m.group(1));
        }
    }
}
