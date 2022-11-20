package com.zl.blog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zl.blog.entity.IFile;
import com.zl.blog.pojo.dto.FileBackDTO;
import com.zl.blog.pojo.vo.ConditionVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 文件服务
 *
 * @author 冷血毒舌
 * @description 针对表【sys_file】的数据库操作Service
 * @createDate 2022-11-13 00:36:49
 */
public interface IFileService extends IService<IFile> {
    /**
     * 获取文件列表
     *
     * @param conditionVO
     * @return
     */
    Page<FileBackDTO> getFileList(ConditionVO conditionVO);

    /**
     * 上传图片
     *
     * @param file
     * @return
     * @throws IOException
     */
    String saveImageFile(MultipartFile file) throws IOException;

    /**
     * 根据id删除图片
     *
     * @param imageIds
     */
    void deleteFile(List<Integer> imageIds);

    /**
     * 上传头像
     *
     * @param file
     * @return
     */
    String uploadAvatarFile(MultipartFile file) throws IOException;

    /**
     * 上传文章图片
     *
     * @param file
     * @return
     */
    String uploadArticleImageFile(MultipartFile file) throws IOException;

    /**
     * 上传配置图片
     *
     * @param file
     * @return
     */
    String uploadConfigImageFile(MultipartFile file) throws IOException;
}
