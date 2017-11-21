package com.meridian.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author 刘洋
 * @date 2016年10月19日
 * @version 1.0
 * @parameter
 */
public class FileOperationUtil {

    //求文件的md5值
    public static String fileMd5(String filePath){
        String value = null;
        File file = new File(filePath);
        try {
            FileInputStream in = new FileInputStream(file);
            try {  
                MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());  
                MessageDigest md5 = MessageDigest.getInstance("MD5");  
                md5.update(byteBuffer);  
                BigInteger bi = new BigInteger(1, md5.digest());  
                value = bi.toString(16);  
            } catch (Exception e) {  
                e.printStackTrace();  
            } finally {  
                    if(null != in) {  
                        try {  
                        in.close();  
                    } catch (IOException e) {  
                        e.printStackTrace();  
                    }  
                }  
            }  
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }       

        return value;
        
    }

    /**
     * Java文件操作 获取文件扩展名
     * @author 刘洋
     * @date 2016年9月7日 上午11:34:23 
     * @version 1.0 
     * @param fileName
     * @return
     */
    public static String getExtensionName(String fileName) {
        fileName = new File(fileName).getName();
        if ((fileName != null) && (fileName.length() > 0)) {
            int dot = fileName.lastIndexOf('.');
            if ((dot > -1) && (dot < (fileName.length() - 1))) {
                return fileName.substring(dot + 1);
            }
        }
        return fileName;
    }

    /**
     * Java文件操作 获取不带扩展名的文件名
     * @author 刘洋
     * @date 2016年9月7日 上午11:34:12 
     * @version 1.0 
     * @param filename
     * @return
     */
    public static String getFileNameNoEx(String filename) {
        filename = new File(filename).getName();
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }
    
    /**
     * Java文件操作 获取完整文件名
     * @author 刘洋
     * @date 2016年9月7日 上午11:35:10 
     * @version 1.0 
     * @param filename
     * @return
     */
    public static String getFileName(String filename) {
        return new File(filename).getName();
    }
    
    /** 
     * 删除目录（文件夹）以及目录下的文件 
     * @param   sPath被删除目录的文件路径 
     * @return  目录删除成功返回true，否则返回false 
     */  
    public static boolean deleteDirectory(String sPath) {
        // 如果sPath不以文件分隔符结尾，自动添加文件分隔符
        if (!sPath.endsWith(File.separator)) {
            sPath = sPath + File.separator;
        }
        File dirFile = new File(sPath);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        boolean flag = true;
        // 删除文件夹下的所有文件(包括子目录)
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            // 删除子文件
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag)
                    break;
            } // 删除子目录
            else {
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag)
                    break;
            }
        }
        if (!flag)
            return false;
        // 删除当前目录
        if (dirFile.delete()) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * 删除单个文件
     * @param sPath被删除文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }
    
    /**
     * 创建文件夹
     * @author 刘洋
     * @date 2016年10月19日
     * @version 1.0
     * @param folderPath
     */
    public static void createFolder(String folderPath) {
        File newTargetFile = new File(folderPath);
        if (!newTargetFile.exists()) {
            newTargetFile.mkdirs();
        }
    }

    /**
     * 递归获取路径下全部文件列表
     * @author Reticence (liuyang_blue@qq.com)
     * @param filePath
     * @return
     */
    public static List<File> fileRecurses(File filePath) {
        List<File> fileList = new ArrayList<File>();
        File[] files = filePath.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                fileList.addAll(fileRecurses(file));
            } else {
                fileList.add(file);
            }
        }
        return fileList;
    }

    /**
     * 递归获取路径下全部文件列表
     * @author Reticence (liuyang_blue@qq.com)
     * @param filePath
     * @return
     */
    public static List<File> fileRecurses(String filePath) {
        return fileRecurses(new File(filePath));
    }
}
