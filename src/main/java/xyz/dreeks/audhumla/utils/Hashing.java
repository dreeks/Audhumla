package xyz.dreeks.audhumla.utils;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class Hashing {

    public static String hash(File file) throws Exception  {
        InputStream is = new FileInputStream(file);
        return DigestUtils.sha1Hex(is);
    }

}
