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
    Page<FileBackDTO> getFileList(ConditionVO conditionVO);

    String saveImageFile(MultipartFile file) throws IOException;

    void deleteFile(List<Integer> imageIds);
}
