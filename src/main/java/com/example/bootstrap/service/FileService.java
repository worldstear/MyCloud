package com.example.bootstrap.service;


import com.example.bootstrap.pojo.FilePojo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


public interface FileService {


    boolean uploadFile(MultipartFile file, HttpServletRequest request);

    List<FilePojo> selectFileByUsername(String username);

    void downloadFile(Integer fileId, HttpServletResponse response,HttpServletRequest request);
}
