package com.doudou.jcip;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;

abstract public class A {
    /**
     *入口函数，自动生成
     * @author 豆豆
     * @date 2019/5/17 10:44a
    */
    public static void main(String[] args) throws IOException {
        Path sourceFile = Paths.get("xxx");
        Path newFile = Paths.get("xxx");
        PosixFileAttributes attrs =
                Files.readAttributes(sourceFile, PosixFileAttributes.class);
        FileAttribute<Set<PosixFilePermission>> attr =
                PosixFilePermissions.asFileAttribute(attrs.permissions());


        System.out.println("1212121");
    }
}
