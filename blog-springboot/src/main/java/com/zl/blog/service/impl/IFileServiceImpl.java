package com.zl.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zl.blog.common.enums.FilePathEnum;
import com.zl.blog.entity.IFile;
import com.zl.blog.exception.ServiceException;
import com.zl.blog.mapper.IFileMapper;
import com.zl.blog.pojo.dto.FileBackDTO;
import com.zl.blog.pojo.vo.ConditionVO;
import com.zl.blog.service.IFileService;
import com.zl.blog.strategy.context.UploadStrategyContext;
import com.zl.blog.strategy.impl.LocalUploadStrategyImpl;
import com.zl.blog.utils.BeanCopyUtils;
import com.zl.blog.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.zl.blog.common.CommonConst.IMAGE_TYPE_LIST;
import static com.zl.blog.common.CommonConst.TRUE;
import static com.zl.blog.common.enums.FilePathEnum.*;

/**
 * 文件服务实现
 *
 * @author 冷血毒舌
 * @description 针对表【sys_file】的数据库操作Service实现
 * @createDate 2022-11-13 00:36:49
 */
@Service
public class IFileServiceImpl extends ServiceImpl<IFileMapper, IFile>
        implements IFileService {

    /**
     * 本地路径
     */
    @Value("${upload.local.path}")
    private String localPath;

    @Resource
    private IFileMapper fileMapper;

    @Autowired
    private UploadStrategyContext uploadStrategyContext;

    @Autowired
    private LocalUploadStrategyImpl localUploadStrategy;

    @Override
    public Page<FileBackDTO> getFileList(ConditionVO conditionVO) {
        QueryWrapper<IFile> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("id");
        if (conditionVO.getKeywords() != null) {
            queryWrapper.like("filename", conditionVO.getKeywords());
        }
        Page<IFile> page = page(new Page<>(conditionVO.getCurrent(), conditionVO.getSize()), queryWrapper);
        Page<FileBackDTO> pageDTO = new Page<>();
        BeanCopyUtils.copyIPage(page, pageDTO, FileBackDTO.class);
        return pageDTO;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String saveImageFile(MultipartFile file) throws IOException {
        return getUrl(IMAGES, file);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String uploadAvatarFile(MultipartFile file) throws IOException {
        return getUrl(AVATAR, file);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String uploadArticleImageFile(MultipartFile file) throws IOException {
        return getUrl(ARTICLE, file);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String uploadConfigImageFile(MultipartFile file) throws IOException {
        return getUrl(CONFIG, file);
    }

    /**
     * 保存图片并返回url
     *
     * @param filePath
     * @param file
     * @return
     * @throws IOException
     */
    private String getUrl(FilePathEnum filePath, MultipartFile file) throws IOException {
        // 获取文件md5值
        String md5 = FileUtils.getMd5(file.getInputStream());
        // 获取文件扩展名
        String extName = FileUtils.getExtName(file.getOriginalFilename());
        // 判断文件类型是否为图片
        if (!Arrays.asList(IMAGE_TYPE_LIST).contains(extName)) {
            throw new ServiceException("文件格式错误，请上传相应的图片格式（jpg,jpeg,png，gif）当前格式为：" + extName);
        }
        // 重新生成文件名
        String fileName = md5 + extName;
        // 获取文件的大小
        long fileSize = file.getSize() / 1024 + (file.getSize() % 1024 != 0 ? 1 : 0);
        // 图片URL
        String url = "";
        // 判断文件是否已存在
        if (!localUploadStrategy.exists(filePath.getPath() + fileName)) {
            // 不存在则继续上传
            url = uploadStrategyContext.executeUploadStrategy(fileName, file.getInputStream(), filePath.getPath());
            fileMapper.insert(IFile.builder()
                    .filename(fileName)
                    .type(extName.replace(".", ""))
                    .size(fileSize)
                    .md5(md5)
                    .path(filePath.toString())
                    .enable(TRUE)
                    .url(url)
                    .build());
        } else {
            url = localUploadStrategy.getFileAccessUrl(filePath.getPath() + fileName);
            fileMapper.insert(IFile.builder()
                    .filename(fileName)
                    .type(extName.replace(".", ""))
                    .size(fileSize)
                    .md5(md5)
                    .enable(TRUE)
                    .url(url)
                    .build());
        }
        return url;
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteFile(List<Integer> imageIds) {
        List<IFile> list = listByIds(imageIds);
        for (IFile file : list) {
            if (Objects.nonNull(file.getPath())) {
                FileUtils.deleteFile(file.getFilename(),
                        localPath + FilePathEnum.valueOf(file.getPath()).getPath());
            }
        }
        this.removeBatchByIds(imageIds);
    }
}




