package com.example.bootstrap.pojo;

import lombok.Data;

import java.sql.Date;

@Data
public class FilePojo {
    private Integer id;
    private String originalFilename;
    private String fileUploadUUID;
    private String uploadUser;
    private Date uploadTime;
    private String suffix;
    private String GMT_created;
    private String GMT_modified;
}
