package com.webproject.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class CloudinaryService {

    private Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
        "cloud_name", "dmxmc676d",
        "api_key", "649891352997634",
        "api_secret", "1UOOSWMU05PKBX2hAxDJEIy9i1c"));

    public String uploadToCloudinary(File qrCode) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(qrCode, ObjectUtils.emptyMap());
        return (String) uploadResult.get("secure_url");
    }
}
