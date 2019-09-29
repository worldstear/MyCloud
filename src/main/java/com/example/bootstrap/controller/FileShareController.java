package com.example.bootstrap.controller;

import com.example.bootstrap.pojo.FilePojo;
import com.example.bootstrap.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/share")
public class FileShareController {

    @Autowired
    private FileService fileService;
    @GetMapping("/{fileUUID}")
    public String getShare(@PathVariable("fileUUID")String fileUUID){
        FilePojo filePojo = fileService.selectFileByUUID(fileUUID);
        return "/share/download";
    }
}
