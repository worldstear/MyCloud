package com.example.bootstrap.controller;

import com.example.bootstrap.pojo.FilePojo;
import com.example.bootstrap.pojo.FileShare;
import com.example.bootstrap.pojo.User;
import com.example.bootstrap.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/share")
public class FileShareController {

    @Autowired
    private FileService fileService;
    @GetMapping("/{fileUUID}")
    public String getShare(@PathVariable("fileUUID")String fileUUID, Model model){
        FilePojo filePojo = fileService.selectFileByUUID(fileUUID);
        model.addAttribute("file",filePojo);
        return "share/download";
    }

    @PostMapping("/download")
    @ResponseBody
    public String getFileDownload(FileShare shareFile, HttpServletRequest request, HttpServletResponse response){
        System.out.println(shareFile);
        Boolean checkPassword = fileService.checkSharePassword(shareFile);
        if(checkPassword){
            String username = fileService.selectUsernameByFileId(shareFile.getFileId());
            User uploadUser = new User();
            uploadUser.setUsername(username);
            HttpSession session = request.getSession();
            session.setAttribute("loginUser",uploadUser);
            fileService.downloadFile(shareFile.getFileId(),response,request);
            session.setAttribute("loginUser",null);
            return "文件下载完成";
        }else {
            return "密码不正确";
        }

    }
}
