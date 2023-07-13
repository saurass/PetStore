package com.petpic.petpicserv.validator;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ImageValidator {

    public boolean validate(MultipartFile multipartFile) {
        boolean result = true;

        String contentType = multipartFile.getContentType();
        if (!isSupportedContentType(contentType)) {
            result = false;
        }

        return result;
    }

    private boolean isSupportedContentType(String contentType) {
        System.out.println(contentType);
        return contentType != null && (contentType.equals("image/jpeg")
                || contentType.equals("image/png"));
    }
}
