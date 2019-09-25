package com.example.bootstrap.mapper;

import com.example.bootstrap.pojo.FilePojo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FileMapper {
    @Insert("insert into t_bootstrap_file_upload" +
            "(original_filename,file_upload_UUID,upload_user,upload_time,suffix,gmt_created) " +
            "values(#{originalFilename},#{fileUploadUUID},#{uploadUser},#{uploadTime},#{suffix},#{GMT_created})")
    void insertNewFile(FilePojo fileUpload);


    @Select("select id,original_filename,upload_time from t_bootstrap_file_upload where upload_user = #{username}")
    List<FilePojo> selectFileByUsername(String username);

    @Select("select concat(file_upload_UUID,suffix) from t_bootstrap_file_upload where id = #{fileId}")
    String getFileNameById(Integer fileId);
}
