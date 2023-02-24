package cmc.config;

import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

@Slf4j
@Configuration
@EnableEncryptableProperties
public class JasyptConfig {
    @Value("${jasypt.encryptor.algorithm}")
    private String algorithm;
    @Value("${jasypt.encryptor.pool-size}")
    private int poolSize;
    @Value("${jasypt.encryptor.string-output-type}")
    private String stringOutputType;
    @Value("${jasypt.encryptor.key-obtention-iterations}")
    private int keyObtentionIterations;

    @Bean
    public StringEncryptor jasyptStringEncryptor() {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        encryptor.setPoolSize(poolSize);
        encryptor.setAlgorithm(algorithm);
        encryptor.setPassword(getJasyptEncryptorPassword());
        encryptor.setStringOutputType(stringOutputType);
        encryptor.setKeyObtentionIterations(keyObtentionIterations);
        return encryptor;
    }

    private String getJasyptEncryptorPassword() {
        try {
            ClassPathResource resource = new ClassPathResource("jasypt-encryptor-password.txt");
            return Files.readAllLines(Paths.get(resource.getURI())).stream()
                    .collect(Collectors.joining(""));
        } catch (IOException e) {
            throw new RuntimeException("Not found Jasypt password file.");
        }
    }
}