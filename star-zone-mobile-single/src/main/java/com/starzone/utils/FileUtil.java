package com.starzone.utils;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 文件操作工具类
 * @doc 说明 1.文件夹是否存在，不存在先创建 2.文件是否存在，不存在，则创建
 * @FileName FileUtil.java
 * @author qiu_hf
 * @version 1.0.0
 * @since 2019年9月28日
 * @history 1.0.0.0 2019年9月28日 下午1:32:15 created by【qiu_hf】
 */
public class FileUtil {
	
	public static final Logger log = LoggerFactory.getLogger(FileUtil.class);

	/**
	 * 判断文件是否存在
	 * @doc 说明
	 * @param file
	 * @author qiu_hf
	 * @throws IOException 
	 * @history 2019年9月28日 下午1:34:30 Create by 【qiu_hf】
	 */
    public static void judeFileExists(File file) throws IOException {

        if (file.exists()) {
            log.info("file exists, delete and create a new one.");
            file.delete();
            file.createNewFile();
        } else {
            log.info("file not exists, create it ...");
            try {
                file.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    /**
     * 判断文件夹是否存在
     * @doc 说明
     * @param file
     * @author qiu_hf
     * @history 2019年9月28日 下午1:34:41 Create by 【qiu_hf】
     */
    public static void judeDirExists(File file) {

        if (file.exists()) {
            if (file.isDirectory()) {
                log.info("dir exists");
            } else {
                log.info("the same name file exists, can not create dir");
            }
        } else {
            log.info("dir not exists, create it ...");
            file.mkdir();
        }

    }
}
