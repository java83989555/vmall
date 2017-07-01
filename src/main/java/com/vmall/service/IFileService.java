package com.vmall.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by GV
 * DATE:2017/6/22
 * TIME:下午8:43
 */
public interface IFileService {
    String upload(MultipartFile file,String path);
}
