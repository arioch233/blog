package com.zl.blog.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.zl.blog.exception.ServiceException;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.*;

import static com.zl.blog.common.CommonConst.IMAGE_TYPE_LIST;
import static com.zl.blog.common.enums.StatusCodeEnum.SYSTEM_ERROR;

/**
 * 文件工具类
 *
 * @author 冷血毒舌
 * @create 2022/11/12 23:32
 */
@Log4j2
public class FileUtils {
    /**
     * 生成图片文件基本信息
     *
     * @param file
     * @return Map<String, Object>
     * @throws IOException
     */
    public static Map<String, Object> generateImageFileBaseInfo(MultipartFile file) throws IOException {
        Map<String, Object> baseInfo = new HashMap<>();
        // 获取文件的md5
        String md5 = SecureUtil.md5(file.getInputStream());
        baseInfo.put("md5", md5);
        // 获取文件的大小
        long size = file.getSize();
        baseInfo.put("size", size / 1024 + (size % 1024 != 0 ? 1 : 0));
        // 获取文件类型
        String originalFilename = file.getOriginalFilename();
        String type = FileUtil.extName(originalFilename);
        baseInfo.put("type", type);
        if (!Arrays.asList(IMAGE_TYPE_LIST).contains(type)) { // 判断文件类型是否为图片
            throw new ServiceException("文件格式错误，请上传相应的图片格式（jpg,jpeg,png，gif）当前格式为：" + type);
        }
        // 定义一个文件唯一的标识码
        String uuid = IdUtil.fastSimpleUUID();
        baseInfo.put("uuid", uuid);
        return baseInfo;
    }

    /**
     * 对文件进行统一命名
     * uuid + YYYY-MM-DD-HH-MM-SS + .[type]
     *
     * @param uuid 文件唯一标识
     * @return
     */
    public static String generateFileName(String uuid, String type) {
        // 时间处理
        //当前时间
        Date date = DateUtil.date();
        String today = DateUtil.format(date, "yyyy-MM-dd-HH-mm-ss");
        return uuid + "-" + today + StrUtil.DOT + type;
    }

    /**
     * 文件下载
     *
     * @param fileName
     * @param imageUploadPath
     * @param response
     * @throws IOException
     */
    public static void downloadFile(String fileName, String imageUploadPath, HttpServletResponse response) throws IOException {
        // 根据文件的唯一标识码获取文件
        File uploadFile = new File(imageUploadPath + fileName);
        // 设置输出流的格式
        ServletOutputStream outputStream = response.getOutputStream();
        response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
        response.setContentType("application/octet-stream");
        // 读取文件的字节流
        outputStream.write(FileUtil.readBytes(uploadFile));
        outputStream.flush();
        outputStream.close();
    }

    /**
     * 删除磁盘上的文件
     *
     * @param fileName       文件名
     * @param fileUploadPath 文件路径
     */
    public static void deleteFile(String fileName, String fileUploadPath) {
        try {
            File file = new File(fileUploadPath + fileName);
            FileUtil.del(file);
        } catch (IORuntimeException e) {
            throw new ServiceException(SYSTEM_ERROR.getCode(), "内部错误删除文件失败");
        }
    }


    /**
     * 获取文件md5值
     *
     * @param inputStream 文件输入流
     * @return {@link String} 文件md5值
     */
    public static String getMd5(InputStream inputStream) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("md5");
            byte[] buffer = new byte[8192];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                md5.update(buffer, 0, length);
            }
            return new String(HexUtil.encodeHex(md5.digest()));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 得到文件扩展名
     *
     * @param fileName 文件名称
     * @return {@link String} 文件后缀
     */
    public static String getExtName(String fileName) {
        if (StringUtils.isBlank(fileName)) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 转换file
     *
     * @param multipartFile 多部分文件
     * @return {@link File} file
     */
    public static File multipartFileToFile(MultipartFile multipartFile) {
        File file = null;
        try {
            String originalFilename = multipartFile.getOriginalFilename();
            String[] filename = Objects.requireNonNull(originalFilename).split("\\.");
            file = File.createTempFile(filename[0], filename[1]);
            multipartFile.transferTo(file);
            file.deleteOnExit();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }


    /**
     * 自动调节精度(经验数值)
     *
     * @param size 源图片大小
     * @return 图片压缩质量比
     */
    private static double getAccuracy(long size) {
        double accuracy;
        if (size < 900) {
            accuracy = 0.85;
        } else if (size < 2048) {
            accuracy = 0.6;
        } else if (size < 3072) {
            accuracy = 0.44;
        } else {
            accuracy = 0.4;
        }
        return accuracy;
    }

}