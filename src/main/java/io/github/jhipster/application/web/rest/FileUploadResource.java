package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.config.ApplicationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * FileUpload controller
 */
@RestController
@RequestMapping("/api/file-upload")
public class FileUploadResource {

    private final Logger log = LoggerFactory.getLogger(FileUploadResource.class);

    @Autowired
    private ApplicationProperties applicationProperties;

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<Map<String, Object>> upload(@RequestPart("file") MultipartFile file, @RequestPart("key") String key) throws IOException {


        log.warn("文件名是: {}", file.getOriginalFilename());
        log.warn("内容类型是: {}", file.getContentType());

        String sysName = System.getProperty("os.name");

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        long dateTime = System.currentTimeMillis();

        String formatDate = simpleDateFormat.format(new Date(dateTime));
        String fileOriginalName = file.getOriginalFilename();
        String extensionName = fileOriginalName.substring(fileOriginalName.lastIndexOf(".") + 1);
        String dirUrl = formatDate + "/" + extensionName + "/";
        String saveFileName =  fileOriginalName;
        log.warn("是否匹配? {}", extensionName.toLowerCase().matches("jpe?g|png|gif|bmp"));
        if (extensionName.toLowerCase().matches("jpe?g|png|gif|bmp")) {
            String uuidName = UUID.randomUUID().toString();
            dirUrl =  formatDate + "/" + "images/";
            saveFileName =  uuidName + '.' + extensionName;
        }

        log.warn("扩展名是: {}", extensionName);

        //linux环境
        String fileUploadUrl = this.applicationProperties.getFileUploadUrl() == null ? "" : this.applicationProperties.getFileUploadUrl();
        File upload = new File(fileUploadUrl);
        log.warn("读取配置文件的fileupload url 是: {}", upload);

        log.warn("操作系统是: {}", sysName);
        if (sysName.toLowerCase().contains("windows")){
            upload = new File("D:/nginxwww/static/upload/" + dirUrl);
        } else if (sysName.toLowerCase().contains("mac")) {
            upload = new File("/usr/local/var/www/static/upload/", dirUrl);
        } else {
            upload = new File(upload.getAbsolutePath(), dirUrl);
        }
        if (!upload.exists()) {
            upload.mkdirs();
        }
        File destFile = new File(upload.getAbsolutePath() + "/" + saveFileName);
        file.transferTo(destFile);

        HashMap<String, Object> map = new HashMap<>();
        map.put("fileName", fileOriginalName);
        map.put("extensionName", extensionName);
        map.put("fileUrl", "static/upload/" + dirUrl + saveFileName);
        map.put("key", key);

        return ResponseEntity.ok().body(map);
    }


}
