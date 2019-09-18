package com.example.bootstrap.service;


import org.springframework.web.multipart.MultipartFile;


public interface FileService {


    boolean uploadFile(MultipartFile file);

}
