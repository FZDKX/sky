package com.fzdkx.controller.admin;

import com.fzdkx.result.Result;
import com.fzdkx.utils.AliOssUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.UUID;

/**
 * @author 发着呆看星
 * @create 2023/8/17 12:00
 */
@RestController
@RequestMapping("/admin/common")
@Api("公共接口")
@Slf4j
public class CommonController {
    @Resource
    private AliOssUtil aliOssUtil;

    @ApiOperation("文件上传")
    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) throws IOException {
        try {
            // 获取文件名
            String filename = file.getOriginalFilename();
            // 获取文件后缀
            String extension = filename.substring(filename.lastIndexOf("."));
            // 构造新文件名
            String objectName = UUID.randomUUID().toString() + extension;
            // 上传文件,获得文件的请求路径
            String filePath = aliOssUtil.upload(file.getBytes(), objectName);
            return Result.success(filePath);
        } catch (IOException e) {
            log.error("文件上传失败");
        }
        return Result.error("文件上传失败");
    }
}
