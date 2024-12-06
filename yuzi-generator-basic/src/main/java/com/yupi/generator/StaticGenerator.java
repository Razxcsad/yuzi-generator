package com.yupi.generator;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.util.ArrayUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * @author lenovo
 */
public class StaticGenerator {

    public static void main(String[] args) {
        // 获取项目根路径
        String projectPath = System.getProperty("user.dir");
        File parentFile = new File(projectPath).getParentFile();

        // 输入路径
        String inputPath = new File(parentFile, "yuzi-generator-demo-projects/acm-template").getAbsolutePath();

        // 执行文件复制
        copyFileByHutool(inputPath, projectPath);
    }

    public static void copyFileByHutool(String inputPath, String outputPath) {
        File src = new File(inputPath);
        File dest = new File(outputPath);

        // 创建目标目录
        final File subTarget = FileUtil.mkdir(FileUtil.file(dest, src.getName()));
        internalCopyDirContent(src, subTarget);
    }

    private static void internalCopyDirContent(File src, File dest) {
        // 如果目标目录不存在，创建目录
        if (!dest.exists()) {
            dest.mkdirs();
        }

        // 获取源路径目录下的所有文件
        final String[] files = src.list();
        if (ArrayUtil.isNotEmpty(files)) {
            for (String file : files) {
                File srcFile = new File(src, file);
                File destFile = new File(dest, file);

                // 如果是目录，递归调用复制
                if (srcFile.isDirectory()) {
                    internalCopyDirContent(srcFile, destFile);
                } else {
                    try {
                        // 使用 REPLACE_EXISTING 选项以防止文件已存在的错误
                        Files.copy(srcFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES);
                    } catch (IOException e) {
                        throw new IORuntimeException(e);
                    }
                }
            }
        }
    }
}
