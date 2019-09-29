package com.example.bootstrap.mapper;

import com.example.bootstrap.pojo.FilePojo;
import com.example.bootstrap.pojo.FileShare;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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

    @Select("select file_upload_UUID from t_bootstrap_file_upload where id = #{fileId}")
    String getFileUUIDById(Integer fileId);

    @Insert("insert into t_bootstrap_file_share (file_id, file_UUID, share_password, gmt_created) " +
            "values(#{fileId},#{fileUUID},#{sharePassword},#{GMT_created})")
    int insertFileShare(FileShare fileShare);

    @Select("select count(1) from t_bootstrap_file_share where file_id = #{fileId}")
    int selectFileShareByFileId(Integer fileId);

    @Update("update t_bootstrap_file_share set share_password = #{sharePassword},gmt_modified = #{GMT_modified} where file_id = #{fileId}")
    int updateSharePassword(FileShare fileShare);

    @Select("")
    FilePojo selectFileByUUID(String fileUUID);
}
