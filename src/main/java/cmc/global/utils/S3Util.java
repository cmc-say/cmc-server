package cmc.global.utils;

import cmc.global.error.ErrorResponse;
import cmc.global.error.exception.BusinessException;
import cmc.global.error.exception.ErrorCode;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class S3Util {
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    // MultipartFile을 전달받아 File로 전환한 후 S3에 업로드
    public String upload(MultipartFile multipartFile, String dirName) {
        try {
            File uploadFile = convert(multipartFile).get();
            return upload(uploadFile, dirName);
        } catch (MaxUploadSizeExceededException e) {
            throw new BusinessException(ErrorCode.FILE_SIZE_EXCEED);
        } catch (Exception e) {
            log.info("FILE UPLOAD ERROR {}" , e);
            throw new BusinessException(ErrorCode.FILE_UPLOAD_ERROR);
        }
    }

    public void delete(String filename) {
        amazonS3Client.deleteObject(new DeleteObjectRequest(bucket, filename));
    }

    private String upload(File uploadFile, String dirName) {
        String fileName = dirName + "/" + uploadFile.getName();
        String uploadImageUrl = putS3(uploadFile, fileName);
        removeNewFile(uploadFile);  // 로컬에 생성된 File 삭제 (MultipartFile -> File 전환 하며 로컬에 파일 생성됨)
        return uploadImageUrl;      // 업로드된 파일의 S3 URL 주소 반환
    }

    private String putS3(File uploadFile, String fileName) {

        amazonS3Client.putObject(
                    new PutObjectRequest(bucket, fileName, uploadFile)
                            .withCannedAcl(CannedAccessControlList.PublicRead));	// PublicRead 권한으로 업로드 됨

        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    private void removeNewFile(File targetFile) {
        if(targetFile.delete()) {
            log.info("[S3 이미지 업로드] 파일이 삭제되었습니다.");
        }else {
            log.info("[S3 이미지 업로드] 파일이 삭제되지 못했습니다.");
        }
    }

    private Optional<File> convert(MultipartFile file) throws IOException {
        File convertFile = new File(createFileName(file.getOriginalFilename()));

        if(convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }

    private String createFileName(String filename) {
        String FILE_EXTENSION_SEPARATOR = ".";
        int fileExtensionIndex = filename.lastIndexOf(FILE_EXTENSION_SEPARATOR);
        String fileExtension = filename.substring(fileExtensionIndex);
        String fileOriginalName = filename.substring(0, fileExtensionIndex);
        String now = String.valueOf(System.currentTimeMillis());

        return fileOriginalName + "_" + now + fileExtension;
    }

}
