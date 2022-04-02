package org.noahsrak.shell.translation;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

/**
 * 翻译命令类
 *
 * @author zhangxt
 * @date 2022/02/09 18:45
 **/
@ShellComponent
public class TranslationCommands {


    @ShellMethod("Translate text from one language to another.")
    public String translate(String text) {
        // invoke service
        return "Hello Translation";
    }

    @ShellMethod("Say hello")
    public void hello(String name) {
        System.out.println("hello, " + name + "!");
    }
}
