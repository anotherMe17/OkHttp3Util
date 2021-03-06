package io.github.anotherme17.okhttp3util.util;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.stream.Stream;

/**
 * @author zhangwt
 * @date 2017/5/16 下午5:40.
 */
public class FileUtil {

    //创建文件
    public static boolean createFile(String destFileName) {
        File file = new File(destFileName);
        if (file.exists()) {
            System.out.println("创建单个文件" + destFileName + "失败，目标文件已存在！");
            return false;
        }
        if (destFileName.endsWith(File.separator)) {
            System.out.println("创建单个文件" + destFileName + "失败，目标文件不能为目录！");
            return false;
        }
        //判断目标文件所在的目录是否存在
        if (!file.getParentFile().exists()) {
            //如果目标文件所在的目录不存在，则创建父目录
            System.out.println("目标文件所在目录不存在，准备创建它！");
            if (!file.getParentFile().mkdirs()) {
                System.out.println("创建目标文件所在目录失败！");
                return false;
            }
        }
        //创建目标文件
        try {
            if (file.createNewFile()) {
                System.out.println("创建单个文件" + destFileName + "成功！");
                return true;
            } else {
                System.out.println("创建单个文件" + destFileName + "失败！");
                return false;
            }
        } catch (IOException e) {
            System.out.println("创建单个文件" + destFileName + "失败！" + e.getMessage());
            return false;
        }
    }

    //创建文件夹
    public static boolean createDir(String destDirName) {
        File dir = new File(destDirName);
        if (dir.exists()) {
            System.out.println("创建目录" + destDirName + "失败，目标目录已经存在");
            return false;
        }
        if (!destDirName.endsWith(File.separator)) {
            destDirName = destDirName + File.separator;
        }
        //创建目录
        if (dir.mkdirs()) {
            System.out.println("创建目录" + destDirName + "成功！");
            return true;
        } else {
            System.out.println("创建目录" + destDirName + "失败！");
            return false;
        }
    }

    public static void writeFile(String destFileName, String str) throws IOException {
        createFile(destFileName);

        FileWriter fileWriter = new FileWriter(destFileName);

        fileWriter.write(str);

        fileWriter.flush();
        fileWriter.close();
    }

    public static String readFile(String destFileName) throws IOException {
        StringBuilder content = new StringBuilder();
        FileReader fileReader = new FileReader(destFileName);
        BufferedReader br = new BufferedReader(fileReader);
        String s;
        while ((s = br.readLine()) != null) {
            content.append(s);
        }
        fileReader.close();

        return content.toString();
    }

    /**
     * 将文件保存在目标路径下
     *
     * @param dest    目标路径
     * @param content 源文件
     * @return true or false
     */
    public static boolean downloadFile(String dest, String content) {
        FileUtil.createFile(dest);
        try (FileOutputStream fout = new FileOutputStream(dest)) {
            FileChannel fc = fout.getChannel();
            byte[] bytes = content.getBytes();
            ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
            Stream.of(bytes).forEach(buffer::put);
            buffer.flip();
            fc.write(buffer);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    /**
     * 删除文件或者文件夹
     *
     * @param dest 文件或文件夹
     */
    public static void deleteFile(String dest) throws FileNotFoundException {
        File file = new File(dest);
        if (file.exists()) {
            if (file.isDirectory()) {//如果是文件夹,递归删除文件夹内的文件
                File[] files = file.listFiles();
                for (File f : files) {
                    deleteFile(f.getPath());
                }
                file.delete();//最后删除目录
            } else {
                //如果是文件,则直接删除
                file.delete();
            }
        } else {
            throw new FileNotFoundException("不存在该目录或文件");
        }
    }


    public static String getResourceFile(String filePath) {

        String content = null;
        boolean bFindBill = false;

        if (filePath != null && !"".equals(filePath)) {
            content = getFileContent(filePath);
            bFindBill = true;
        }

        if (!bFindBill) {
        }
        return content;
    }

    private static String getFileContent(String path) {
        String content = getFileContent(path, "utf-8");
        if (content != null && content.contains("�")) {
            content = getFileContent(path, "gbk");
        }
        return content;
    }

    public static String getFileContent(String path, String encoding) {
        FileInputStream stream = null;
        String content = null;
        try {
            stream = new FileInputStream(new File(path));
            content = IOUtils.toString(stream, encoding);

        } catch (Exception e) {
        } finally {
            IOUtils.closeQuietly(stream);
        }
        return content;
    }
}
