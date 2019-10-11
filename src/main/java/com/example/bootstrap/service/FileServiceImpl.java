package com.example.bootstrap.service;

import com.example.bootstrap.mapper.FileMapper;
import com.example.bootstrap.pojo.FilePojo;
import com.example.bootstrap.pojo.FileShare;
import com.example.bootstrap.pojo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.Date;
import java.util.*;

@Service
public class FileServiceImpl implements FileService{
    private Logger logger  = LoggerFactory.getLogger(FileServiceImpl.class);
    @Value("${file.upload.path}")
    private String fileupload;
    @Autowired
    private FileMapper fileMapper;
    @Autowired
    private LoginServiceImpl loginService;
    @Override
    public boolean uploadFile(MultipartFile file, HttpServletRequest request) {
        try {
            User user = (User)request.getSession().getAttribute("loginUser");
            String originalFilename = file.getOriginalFilename();
            String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            String fileNameUUID = UUID.randomUUID().toString();
            File uploadFile = new File(fileupload + user.getUsername() + "/" + fileNameUUID + suffix);
            if(!uploadFile.exists()){
                if(uploadFile.mkdirs()){
                    long createdTime = System.currentTimeMillis();
                    file.transferTo(uploadFile);
                    logger.info("上传花费时长:" + String.valueOf(System.currentTimeMillis()-createdTime));
                    //将上传的文件记录到数据库中
                    FilePojo fileUpload = new FilePojo();
                    fileUpload.setFileUploadUUID(fileNameUUID);
                    fileUpload.setOriginalFilename(originalFilename);
                    fileUpload.setSuffix(suffix);

                    fileUpload.setUploadUser(user.getUsername());
                    fileUpload.setUploadTime(new Date(createdTime));
                    fileUpload.setGMT_created(String.valueOf(createdTime));
                    fileMapper.insertNewFile(fileUpload);
                }else {//无法创建目录
                    return false;
                }
            }


        } catch (IOException e) {
            logger.error(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public List<FilePojo> selectFileByUsername(String username) {
        return fileMapper.selectFileByUsername(username);
    }

    /**
     * 实现文件下载
     * @param fileId 文件id
     * @param response response写出到外面
     */
    @Override
    public void downloadFile(Integer fileId, HttpServletResponse response,HttpServletRequest request) {
        User loginUser = (User)request.getSession().getAttribute("loginUser");
        //如果无法获取到用户信息则不可下载
        if(null == loginUser){return;}
        String fileName = fileMapper.getFileNameById(fileId);
        response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
        File file = new File(fileupload+loginUser.getUsername()+"/"+fileName);
        System.out.println(file.getAbsolutePath());
        FileInputStream fis = null;
        ServletOutputStream outputStream = null;
        try {
            fis = new FileInputStream(file);
            outputStream = response.getOutputStream();
            byte[] datas = new byte[1024];
            while (-1 != fis.read(datas)){
                outputStream.write(datas);
            }
            outputStream.flush();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }finally {
            if(null != fis){
                try {
                    fis.close();
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }
            }
            if(null != outputStream){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }
            }
        }
    }

    @Override
    public Map<String, String> generateFileShareMap(Integer fileId) {
        //先查询数据库，若存在该数据则设置密码为新密码
        String filePassworld = String.valueOf(new Random().nextInt(8999)+1000);
        String fileUUID = fileMapper.getFileUUIDById(fileId);
        HashMap<String, String> map = new HashMap<>();
        map.put(fileUUID,filePassworld);

        FileShare fileShare = new FileShare();
        fileShare.setFileId(fileId);
        fileShare.setSharePassword(filePassworld);
        fileShare.setFileUUID(fileUUID);

        int count = fileMapper.selectFileShareByFileId(fileId);
        if(count >= 1){//若存在数据则更新
            fileShare.setGMT_modified(String.valueOf(System.currentTimeMillis()));
            int resultCount = fileMapper.updateSharePassword(fileShare);
            if(resultCount > 0){
                return map;
            }else {
                return null;
            }
        }else{//若不存在数据则更新
            fileShare.setGMT_created(String.valueOf(System.currentTimeMillis()));
            int insertResult = fileMapper.insertFileShare(fileShare);
            if(insertResult>0){
                return map;
            }else {
                return null;
            }
        }
    }

    @Override
    public FilePojo selectFileByUUID(String fileUUID) {
        FilePojo filePojo = fileMapper.selectFileByUUID(fileUUID);
        return filePojo;
    }

    @Override
    public Boolean checkSharePassword(FileShare shareFile) {
        Boolean isShareCorrect = fileMapper.selectFileByFileIdAndSharePassword(shareFile);
        System.out.println("isShareCorrect = " + isShareCorrect);
        return isShareCorrect;
    }

    @Override
    public String selectUsernameByFileId(Integer fileId) {
        return fileMapper.selectUsernameByFileId(fileId);
    }
}
