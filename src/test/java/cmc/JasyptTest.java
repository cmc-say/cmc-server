package cmc;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;

import java.nio.file.Files;
import java.nio.file.Paths;

// jasypt 암호화를 위한 테스트 코드
@Disabled
@Profile("test")
@SpringBootTest
public class JasyptTest extends TestSupport{

    @Test
    void jasypt() {
        String S3_BUCKET_NAME = "";
        String IMAGE_IAM_ACCESS_KEY = "";
        String IMAGE_IAM_SECRET_KEY = "";

        String JWT_SECRET = "";
        
        String LOCAL_DB_USERNAME = "";
        String LOCAL_DB_PASSWORD = "";

        String REMOTE_DB_URL = "";
        String REMOTE_DB_USERNAME = "";
        String REMOTE_DB_PASSWORD = "";

        System.out.println(jasyptEncoding(S3_BUCKET_NAME));
        System.out.println(jasyptEncoding(IMAGE_IAM_ACCESS_KEY));
        System.out.println(jasyptEncoding(IMAGE_IAM_SECRET_KEY));

        System.out.println(jasyptEncoding(JWT_SECRET));

        System.out.println(jasyptEncoding(LOCAL_DB_USERNAME));
        System.out.println(jasyptEncoding(LOCAL_DB_PASSWORD));

        System.out.println(jasyptEncoding(REMOTE_DB_URL));
        System.out.println(jasyptEncoding(REMOTE_DB_USERNAME));
        System.out.println(jasyptEncoding(REMOTE_DB_PASSWORD));

        System.out.println("-----------------------");

        System.out.println(jasyptDecoding(jasyptEncoding(S3_BUCKET_NAME)));
        System.out.println(jasyptDecoding(jasyptEncoding(IMAGE_IAM_ACCESS_KEY)));
        System.out.println(jasyptDecoding(jasyptEncoding(IMAGE_IAM_SECRET_KEY)));

        System.out.println(jasyptDecoding(jasyptEncoding(JWT_SECRET)));

        System.out.println(jasyptDecoding(jasyptEncoding(LOCAL_DB_USERNAME)));
        System.out.println(jasyptDecoding(jasyptEncoding(LOCAL_DB_PASSWORD)));

        System.out.println(jasyptDecoding(jasyptEncoding(REMOTE_DB_URL)));
        System.out.println(jasyptDecoding(jasyptEncoding(REMOTE_DB_USERNAME)));
        System.out.println(jasyptDecoding(jasyptEncoding(REMOTE_DB_PASSWORD)));

    }

    public String jasyptEncoding(String value) {

        String key = "";
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        encryptor.setPoolSize(1);
        encryptor.setAlgorithm("PBEWithMD5AndDES");
        encryptor.setPassword(key);
        encryptor.setStringOutputType("base64");
        encryptor.setKeyObtentionIterations(100000);
        return encryptor.encrypt(value);
    }

    public String jasyptDecoding(String value) {

        String key = "";
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        encryptor.setPoolSize(1);
        encryptor.setAlgorithm("PBEWithMD5AndDES");
        encryptor.setPassword(key);
        encryptor.setStringOutputType("base64");
        encryptor.setKeyObtentionIterations(100000);
        return encryptor.decrypt(value);
    }
}
