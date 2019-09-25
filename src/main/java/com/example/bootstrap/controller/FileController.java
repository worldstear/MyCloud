package com.example.bootstrap.controller;


import com.example.bootstrap.pojo.FilePojo;
import com.example.bootstrap.pojo.User;
import com.example.bootstrap.service.FileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/file")
public class FileController {
    @Autowired
    private FileService fileService;

    @GetMapping("/upload")
    public String getfileUploadPage(){
        return "/fileUpload";
    }

    @PostMapping("/upload")
    @ResponseBody
    public String fileUpload(@RequestParam("file")MultipartFile file,HttpServletRequest request) {
       boolean isUpload = fileService.uploadFile(file,request);
        return isUpload?"上传成功":"上传失败";
    }

    @GetMapping("/list")
    public String fileList(HttpServletRequest request){
        User loginUser = (User)request.getSession().getAttribute("loginUser");
        String username = loginUser.getUsername();
        //System.out.println("username = " + username);
        List<FilePojo> filePojos = fileService.selectFileByUsername(username);
        request.setAttribute("filePojos",filePojos);
        System.out.println(filePojos);
        return "/index";
    }
    @GetMapping("/download/{fileId}")
    @ResponseBody
    public void fileDownload(@PathVariable("fileId") Integer fileId, HttpServletResponse response){
        response.setContentType("application/octet-stream");
        response.setHeader("content-type", "application/octet-stream");
        fileService.downloadFile(fileId,response);
    }
}