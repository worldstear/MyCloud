package com.example.bootstrap.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class FileServiceImpl implements FileService{
    private Logger logger  = LoggerFactory.getLogger(FileServiceImpl.class);
    @Override
    public boolean uploadFile(MultipartFile file) {
        try {
            file.transferTo(new File("C:/Users/76720/Desktop/"+file.getOriginalFilename()));
            //将上传的文件记录到数据库中
        } catch (IOException e) {
            logger.error(e.getMessage());
            return false;
        }
        return true;
    }
}
