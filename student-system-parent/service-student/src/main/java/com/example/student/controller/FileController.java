package com.example.student.controller;

import com.example.common.Result;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.UUID;

@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private MinioClient minioClient;

    @Value("${minio.bucketName}")
    private String bucketName;

    @Value("${minio.endpoint}")
    private String endpoint;

    @PostMapping("/upload")
    public Result upload(@RequestParam("file") MultipartFile file) {
        try {
            // 1. 生成唯一文件名，防止重复覆盖
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

            // 2. 上传文件到 MinIO
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );

            // 3. 拼接文件的访问地址并返回给前端
            String fileUrl = endpoint + "/" + bucketName + "/" + fileName;
            return Result.ok(fileUrl);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("文件上传失败");
        }
    }
}