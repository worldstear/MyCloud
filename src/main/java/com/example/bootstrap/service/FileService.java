package com.example.bootstrap.service;


import com.example.bootstrap.pojo.FilePojo;
import com.example.bootstrap.pojo.FileShare;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;


public interface FileService {


    boolean uploadFile(MultipartFile file, HttpServletRequest request);

    List<FilePojo> selectFileByUsername(String username);

    void downloadFile(Integer fileId, HttpServletResponse response,HttpServletRequest request);

    Map<String, String> generateFileShareMap(Integer fileId);

    FilePojo selectFileByUUID(String fileUUID);

    Boolean checkSharePassword(FileShare shareFile);

    String selectUsernameByFileId(Integer fileId);
}
