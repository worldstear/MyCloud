package com.example.bootstrap.controller;

import com.example.bootstrap.pojo.User;
import com.example.bootstrap.service.FileService;
import com.example.bootstrap.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/file")
public class FileUploadController {
    @Autowired
    private FileService fileService;
    @Autowired
    private LoginService loginService;

    @GetMapping("/upload")
    public String getfileUploadPage(Model model, HttpServletRequest request){
        //进行用户登录判断
        Cookie[] cookies = request.getCookies();
        if(cookies==null||cookies.length==0){
            return "/login";
        }

        String userToken = "";
        for (Cookie cookie: cookies) {
            if(cookie.getName().equals("user_token")){
                userToken = cookie.getValue();
            }
        }

        User loginUser = loginService.checkLogin(userToken);
        if(loginUser==null){
            return "rediect:/index";
        }
        model.addAttribute("loginUser",loginUser);
        return "/fileUpload";
    }

    @PostMapping("/upload")
    @ResponseBody
    public String fileUpload(@RequestParam("file")MultipartFile file) {
       boolean isUpload = fileService.uploadFile(file);
        return isUpload?"上传成功":"上传失败";
    }
}
