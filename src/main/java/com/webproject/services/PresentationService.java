package com.webproject.services;

import com.webproject.form.PresentationForm;
import com.webproject.models.Presentation;
import com.webproject.models.Slide;
import com.webproject.models.User;
import com.webproject.repositories.PresentationRepository;
import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipInputStream;

import com.webproject.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.zeroturnaround.zip.ZipUtil;

@Service
public class PresentationService {

    private static final int RESULTS_PER_PAGE = 10;

    @Autowired
    PresentationRepository presentationRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SlideService slideService;

    public Page<Presentation> findAll(int pageIndex, String tag) {
        if (tag != null) {
            return findAllForTag(tag, pageIndex);
        } else {
            return findAll(pageIndex);
        }
    }

    public Optional<Presentation> getPresentationById(Long id) {
        return presentationRepository.findById(id);
    }

    public Page<Presentation> findAll(int pageIndex) {
        return presentationRepository.findAll(PageRequest.of(pageIndex, RESULTS_PER_PAGE));
    }

    public Page<Presentation> findAllForTag(String tag, int pageIndex) {
        return presentationRepository.findByTagsContaining(tag, PageRequest.of(pageIndex, RESULTS_PER_PAGE));
    }

    public Page<Presentation> findAllForUser(String userId, int pageIndex) {
        //all for user id
        return presentationRepository.findAll(PageRequest.of(pageIndex, RESULTS_PER_PAGE));
    }

    public Set<String> getAllDistinctTags() {
        Set<String> tags = new HashSet<>();
        List<Presentation> presentations = presentationRepository.findAll();
        presentations.stream().forEach(p -> tags.addAll(Arrays.asList(p.getTags().split(","))));
        return tags;
    }

    public void exportAll() {
        ZipUtil
            .pack(new File("src/main/resources/files"), new File("src/main/resources/static/presentations.zip"));
    }

    public File decompressZipToDestination(MultipartFile zipFile) throws IOException, ZipException {
        File destDir = createTempDirectory();
        byte[] buffer = new byte[1024];
        ZipInputStream zis = new ZipInputStream(zipFile.getInputStream());
        ZipEntry zipEntry = zis.getNextEntry();
        while (zipEntry != null) {
            File newFile = newFile(destDir, zipEntry);
            FileOutputStream fos = new FileOutputStream(newFile);
            int len;
            while ((len = zis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
            fos.close();
            zipEntry = zis.getNextEntry();
        }
        zis.closeEntry();
        zis.close();
        return destDir;
    }

    private static File createTempDirectory() throws IOException {
        return Files.createTempDirectory("temp_folder").toFile();
    }

    private static File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
        File destFile = new File(destinationDir, zipEntry.getName());

        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();

        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        }

        return destFile;
    }

    public List<Presentation> createPresentations(File destination, PresentationForm input,
                                                  Long userId) throws StringIndexOutOfBoundsException, NumberFormatException, IOException {
        File[] files = destination.listFiles();
        List<Presentation> presentations = new ArrayList<>();
        for (File file : files) {
            if (file.isFile()) {
                presentations.add(createPresentation(input, new FileInputStream(file), userRepository.getOne(userId)));
            }
        }
        return presentations;
    }

    private Presentation createPresentation(PresentationForm form, InputStream fis, User user) throws IOException {
        Presentation presentation = Presentation.builder()
                                                .name(form.getName())
                                                .tags(form.getTags())
                                                .user(user).build();
        presentationRepository.save(presentation);
        File presentationDir = new File("src/main/resources/presentations", presentation.getId().toString());
        if (!presentationDir.exists()) {
            Files.createDirectory(presentationDir.toPath());
        }
        File slidesDir = new File(presentationDir, "slides");
        if (!slidesDir.exists()) {
            Files.createDirectory(slidesDir.toPath());
        }
        List<Slide> slides = slideService.createSlides(fis, slidesDir, presentation);
        presentation.setSlides(slides);
        return presentation;
    }

    public void uploadPresentation(PresentationForm form, Long userId) throws IOException {
        createPresentation(form, form.getZipFile().getInputStream(), userRepository.getOne(userId));
    }

}
