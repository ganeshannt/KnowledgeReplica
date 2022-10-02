package com.knowledgereplica.util;

import com.knowledgereplica.constant.AppConstant;
import com.knowledgereplica.model.UserEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * @author Ganeshan Nagarajan
 * @since 02/10/22
 */

public class AppUtils {
    public static void saveFile(String fileName,
                                MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(AppConstant.UPLOAD_DIR);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Could not save image file: " + fileName, ioe);
        }
    }

    public static void deleteFile(String fileName) throws IOException {
        String filePath = AppConstant.UPLOAD_DIR + "/" + fileName;
        Path uploadedPath = Paths.get(filePath);
        File file = uploadedPath.toFile();
        if (!file.exists()) {
            throw new FileNotFoundException();
        }
        if (!file.delete()) {
            throw new IOException("Could not delete image file: " + fileName);
        }
    }

    public static boolean isValidUserRequest(UserEntity user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return StringUtils.equals(authentication.getName(), user.getEmail());
    }
}
