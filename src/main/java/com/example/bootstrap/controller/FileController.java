package com.example.bootstrap.controller;


import com.example.bootstrap.pojo.FilePojo;
import com.example.bootstrap.pojo.User;
import com.example.bootstrap.service.FileService;

import com.example.bootstrap.utils.IPv4Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/file")
public class FileController {
    @Autowired
    private FileService fileService;

    @GetMapping("/upload")
    public String getfileUploadPage(){
        return "fileUpload";
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
        //System.out.println(filePojos);
        return "index";
    }
    @GetMapping("/download/{fileId}")
    @ResponseBody
    public void fileDownload(@PathVariable("fileId") Integer fileId, HttpServletResponse response,HttpServletRequest request){
        //设置头
        response.setContentType("application/octet-stream");
        response.setHeader("content-type", "application/octet-stream");
        fileService.downloadFile(fileId,response,request);
    }

    @GetMapping("/share/{fileId}")
    public String fileShare(@PathVariable("fileId") Integer fileId, Model model){
        //分享链接
        Map<String,String> shareMap =  fileService.generateFileShareMap(fileId);
        String fileUUID = "";
        String sharePassword = "";
        for(Map.Entry<String,String> entry:shareMap.entrySet()){
            //本机测试环境
            // fileUUID = "http://127.0.0.1:8081/share/"+entry.getKey();
            //局域网测试环境
           String ipv4 = IPv4Tool.getWLANIPv4Address();
           fileUUID = ipv4+":8081/share/"+entry.getKey();
            sharePassword = entry.getValue();
            shareMap.remove(entry.getKey());
        }
        model.addAttribute("shareUrl",fileUUID);
        model.addAttribute("sharePassword",sharePassword);
        return "share/link";
    }
}
