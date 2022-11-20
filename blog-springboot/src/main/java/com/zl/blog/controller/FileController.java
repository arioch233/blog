package com.zl.blog.controller;

import com.zl.blog.common.Result;
import com.zl.blog.pojo.vo.ConditionVO;
import com.zl.blog.service.IFileService;
import com.zl.blog.utils.FileUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 文件上传模块
 *
 * @author 冷血毒舌
 * @create 2022/11/12 23:54
 */
@Api(tags = "文件上传模块")
@RestController
@RequestMapping(value = "/file")
public class FileController {


    @Autowired
    private IFileService fileService;

    /**
     * 本地路径
     */
    @Value("${upload.local.path}")
    private String localPath;

    /**
     * 获取当前文件列表
     *
     * @param conditionVO
     * @return
     */
    @ApiOperation(value = "获取当前文件列表")
    @GetMapping("/all")
    public Result getFileList(ConditionVO conditionVO) {
        return Result.success(fileService.getFileList(conditionVO));
    }

    /**
     * 上传图片
     *
     * @param file
     * @return Result String 返回图片地址
     */
    @ApiOperation(value = "上传图片")
    @ApiImplicitParam(name = "file", value = "文件", required = true, dataType = "MultipartFile")
    @PostMapping("/image/upload")
    public Result uploadImageFile(@RequestParam MultipartFile file) throws IOException {
        return Result.success(fileService.saveImageFile(file), null);
    }

    /**
     * 上传头像
     *
     * @param file
     * @return Result String 返回图片地址
     */
    @ApiOperation(value = "上传头像")
    @ApiImplicitParam(name = "file", value = "文件", required = true, dataType = "MultipartFile")
    @PostMapping("/avatar/upload")
    public Result uploadAvatarFile(@RequestParam MultipartFile file) throws IOException {
        return Result.success(fileService.uploadAvatarFile(file), null);
    }

    /**
     * 上传文章封面
     *
     * @param file
     * @return Result String 返回图片地址
     */
    @ApiOperation(value = "上传文章封面")
    @ApiImplicitParam(name = "file", value = "文件", required = true, dataType = "MultipartFile")
    @PostMapping("/article/upload")
    public Result uploadArticleImageFile(@RequestParam MultipartFile file) throws IOException {
        return Result.success(fileService.uploadArticleImageFile(file), null);
    }

    /**
     * 上传配置图片
     *
     * @param file
     * @return Result String 返回图片地址
     */
    @ApiOperation(value = "上传配置图片")
    @ApiImplicitParam(name = "file", value = "文件", required = true, dataType = "MultipartFile")
    @PostMapping("/config/upload")
    public Result uploadConfigImageFile(@RequestParam MultipartFile file) throws IOException {
        return Result.success(fileService.uploadConfigImageFile(file),null);
    }

    /**
     * 文件下载
     *
     * @param fileName 图片唯一标识
     * @param response
     * @return
     */
    @ApiOperation(value = "文件下载")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "filePath", value = "文件路径", required = true, dataType = "String"),
            @ApiImplicitParam(name = "fileName", value = "文件名", required = true, dataType = "String")
    })
    @GetMapping("/{filePath}/{fileName}")
    public void downloadImage(@PathVariable String filePath, @PathVariable String fileName, HttpServletResponse response) throws IOException {
        String path = localPath + filePath + "/";
        FileUtils.downloadFile(fileName, path, response);
    }

    /**
     * 文件删除
     *
     * @param imageIds
     * @return
     */
    @ApiOperation(value = "删除文件")
    @DeleteMapping("/delete")
    public Result deleteFile(@RequestBody List<Integer> imageIds) {
        fileService.deleteFile(imageIds);
        return Result.success();
    }

}
