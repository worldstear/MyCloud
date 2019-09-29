package com.example.bootstrap.pojo;

import lombok.Data;

@Data
public class FileShare {
    private Integer id;
    private Integer fileId;
    private String fileUUID;
    private Integer shareTime;
    private String sharePassword;
    private String GMT_created;
    private String GMT_modified;
}
