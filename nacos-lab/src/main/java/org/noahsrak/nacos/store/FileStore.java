package org.noahsrak.nacos.store;

import org.apache.commons.io.FileUtils;
import org.noahsrak.nacos.config.AgentConfig;

import java.io.File;

/**
 * @author: zhangxt
 * @version:
 * @date: 2021/10/22
 */
public class FileStore {

    private AgentConfig agentConfig;

    public FileStore() {
    }

    public FileStore(AgentConfig agentConfig) {
        this.agentConfig = agentConfig;
    }

    public void store(String content) {

        String fileName = this.agentConfig.getConfig().getFile();
        System.out.println("File = " + fileName);
        System.out.println("content:\n" + content);

        try {
            File file = new File(fileName);

            /**
             * 判断hello.properties文件是否存在，如果不存在，创建该文件
             */
            if (!file.exists()) {
                file.createNewFile();
            }

            /**
             * 写入字符串到指定的文件中
             */
            FileUtils.writeStringToFile(file, content, "UTF-8");

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
