package com.example.StudentToDo.service;

import com.example.StudentToDo.aggregation.entity.StudentImage;
import com.example.StudentToDo.aggregation.entity.Student;
import com.example.StudentToDo.exceptions.DatabaseException;
import com.example.StudentToDo.exceptions.NotFoundException;
import com.example.StudentToDo.aggregation.dto.student.RequestStudent;
import com.example.StudentToDo.aggregation.dto.student.StdImgResponse;
import com.example.StudentToDo.repository.ImageRepository;
import com.example.StudentToDo.service.interfaces.ImageServiceInterface;
import com.example.StudentToDo.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

import static com.example.StudentToDo.utils.ApiUrls.*;

@Service
@Log4j2
@RequiredArgsConstructor
public class ImageService implements ImageServiceInterface {
    private final StudentRepository studentRepository;
    private final ImageRepository imageRepository;


    @Override
    public String downloadImage(Long id) throws IOException {
        try {
            Optional<Student> student = studentRepository.findById(id);
            if (student.isEmpty()) {
                log.warn(id + ": doesn't exist");
                throw new NotFoundException(id + ": doesn't exist");
            }
            Student result = student.get();
            log.info(result + ": is present");
            String imageName = result.getImageName();
            StudentImage studentImage = imageRepository.findByOriginalName(imageName).orElseThrow();
            String filePath = studentImage.getFilePath();
            Path path = Paths.get(filePath);
            log.info("image downloaded from path: " + path);
            byte[] imgBytes = Files.readAllBytes(path);
            return "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(imgBytes);
        } catch (NotFoundException ex) {
            throw new NotFoundException(ex.getMessage());
        } catch (Exception ex) {
            throw new DatabaseException(ex.getMessage());
        }
    }


    @Override
    public StdImgResponse uploadImage(RequestStudent requestStudent) throws Exception {
        try {
            StudentImage savedImg = null;
            Student student = requestStudent.getStudent();
            String base64File = requestStudent.getBase64File();
            String[] parts = base64File.split(",");
            String[] dataType = parts[0].split(";");
            String[] imageFormat = dataType[0].split("/");
            String originalNameImg = UUID.randomUUID() + "." + imageFormat[1];
            log.info("Image generated with the name: " + originalNameImg);
            log.info("Image format: " + imageFormat[1]);
            String pathToImg = FOLDER_PATH + originalNameImg;
            log.info("Image uploaded to path: " + pathToImg);
            Long id = student.getId();
            if (studentRepository.existsById(id)) {
                log.info(id + ": is present");
                Student foundStd = studentRepository.findById(id).orElseThrow();
                removeImgIfExist(foundStd);
                try (OutputStream outputStream = new FileOutputStream(pathToImg)) {
                    byte[] imageBytes = Base64.getDecoder().decode(parts[1]);
                    outputStream.write(imageBytes);
                }
                savedImg = imageRepository.save(
                        StudentImage.builder()
                                .originalName(originalNameImg)
                                .type(imageFormat[1])
                                .filePath(pathToImg)
                                .build()
                );
                log.info("Student image saved: " +savedImg);
                foundStd.setImageName(savedImg.getOriginalName());
                studentRepository.save(foundStd);
                return new ModelMapper().map(savedImg, StdImgResponse.class);
            }
            log.warn(id + ": doesn't exist");
            throw new NotFoundException(id + ": bunday id student mavjud emas!");
        } catch (NotFoundException ex) {
            throw new NotFoundException(ex.getMessage());
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new DatabaseException(ex.getMessage());
        }
    }

    public void removeImgIfExist(Student student) {
        try {
            String imageName = student.getImageName();
            if (imageName == null) {
                log.warn("Image didn't found");
                throw new NotFoundException(" Image didn't found");
            }
            log.info(imageName+" is exist");
            File file = new File(FOLDER_PATH + imageName);
            log.info(file+" is created");
            if (file.delete()) {
                log.warn(file+": deleted");
                StudentImage studentImage = imageRepository.findByOriginalName(imageName).orElseThrow();
                imageRepository.delete(studentImage);
                log.warn(studentImage+": is deleted");
            }
        } catch (NotFoundException ex) {
            throw new NotFoundException(ex.getMessage());
        } catch (Exception ex) {
            throw new DatabaseException(ex.getMessage());
        }
    }
}
