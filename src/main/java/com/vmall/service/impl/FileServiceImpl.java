package com.vmall.service.impl;

import com.google.common.collect.Lists;
import com.vmall.service.IFileService;
import com.vmall.util.FTPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by GV
 * DATE:2017/6/22
 * TIME:下午8:43
 */
@Service
public class FileServiceImpl implements IFileService {
    private Logger logger= LoggerFactory.getLogger(FileServiceImpl.class);

    @Override
    public String upload(MultipartFile file, String path) {
//        重置文件名
        String fileName=file.getOriginalFilename();
        String fileExtensionName=fileName.substring(fileName.lastIndexOf("."));
        String newName= UUID.randomUUID().toString()+fileExtensionName;
//        判断路径下文件夹是否存在
        File fileDir =new File(path);
        if (!fileDir.exists()){
            //设置可执行
            fileDir.setExecutable(true);
            //创建文件
            fileDir.mkdir();
        }
//        创建在服务器路径下 upload文件
        File targetFile=new File(path,newName);
        try {
            //spring mvc  MultipartFile 转移到指定文件上
            file.transferTo(targetFile);

            //发送ftp服务器
            FTPUtil.uploadFile(Lists.newArrayList(targetFile));
            //上传成功则删除文件
            targetFile.delete();
        } catch (IOException e) {
            logger.error("上传文件失败",e);
        }
        return targetFile.getName();
    }


}
