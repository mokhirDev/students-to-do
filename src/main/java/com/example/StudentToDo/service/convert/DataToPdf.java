package com.example.StudentToDo.service.convert;

import com.example.StudentToDo.aggregation.entity.Student;
import com.example.StudentToDo.aggregation.entity.StudentImage;
import com.example.StudentToDo.repository.ImageRepository;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class DataToPdf {
    @Autowired
    private ImageRepository imageRepository;

    public byte[] insertTextToPdf(Student student) throws IOException {
        String imageName = student.getImageName();
        StudentImage studentImage = imageRepository.findByOriginalName(imageName).orElseThrow();
        String filePath = studentImage.getFilePath();
        Path path = Paths.get(filePath);
        byte[] imgBytes = Files.readAllBytes(path);
//        byte[] decode = Base64.getDecoder().decode(imgBytes);
        byte[] pdfBytes = new byte[1];
        PDPageContentStream contentStream = null;
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
                contentStream = new PDPageContentStream(document, page);
                contentStream.setNonStrokingColor(255, 183, 0);

                contentStream.addRect(0, 800, 600, 50); // x, y, ширина, высота
                contentStream.fill();
                contentStream.stroke();

                PDImageXObject userImg = PDImageXObject.createFromFile(filePath, document);
                contentStream.drawImage(userImg, 20, 680, 100, 100);
                PDImageXObject logo = PDImageXObject.createFromFile("C:\\Users\\moxir\\IdeaProjects\\StudentsProjects\\src\\main\\java\\com\\just\\go\\service\\convert\\davr_bank_logo.png", document);
                contentStream.drawImage(logo, 470, 810, 100, 20);
                contentStream.setNonStrokingColor(0,0,0);
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 25);
                contentStream.newLineAtOffset(140, 760);
                contentStream.showText("%s %s %s".formatted(student.getFirstName(), student.getMiddleName(), student.getLastName()));
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(140, 740);
                contentStream.showText("Erkak, 24 yoshda, tug'ilgan: 02-11-1999");
                contentStream.endText();

                contentStream.beginText();
                contentStream.newLineAtOffset(140, 725);
                contentStream.showText("+998 (90) 3571847");
                contentStream.endText();

                contentStream.beginText();
                contentStream.setNonStrokingColor(136, 138, 141);
                contentStream.setFont(PDType1Font.COURIER_OBLIQUE, 12);
                contentStream.newLineAtOffset(260, 725);
                contentStream.showText("-bog'lanish uchun raqam");
                contentStream.endText();

                contentStream.beginText();
                contentStream.setNonStrokingColor(0,0,0);
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(140, 710);
                contentStream.showText("maxkamov021199@gmail.com");
                contentStream.endText();


                contentStream.beginText();
                contentStream.newLineAtOffset(140, 670);
                contentStream.showText("Yashash manzil: Toshkent shahar");
                contentStream.endText();

                contentStream.beginText();
                contentStream.newLineAtOffset(140, 655);
                contentStream.showText("Fuqaroligi: O'zbekiston, ishlash uchun ruhsat berilgan");
                contentStream.endText();

                contentStream.beginText();
                contentStream.newLineAtOffset(140, 640);
                contentStream.showText("Ko'chishga tayyor emas, komandirovkalarga tayyor emas");
                contentStream.endText();


                contentStream.setNonStrokingColor(136, 138, 141);
                contentStream.setFont(PDType1Font.COURIER_BOLD, 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(20, 600);
                contentStream.showText("Kutilayotgan ish o'rni va ish haqqi");
                contentStream.endText();

                contentStream.addRect(20, 597, 550, 1); // x, y, ширина, высота
                contentStream.fill();
                contentStream.stroke();
                contentStream.close();
                document.save(byteArrayOutputStream);
                pdfBytes = byteArrayOutputStream.toByteArray();
            }
            document.save("Resume.pdf");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pdfBytes;
    }

    public void insertImageToPdf() throws IOException, URISyntaxException {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        Path path = Paths.get(ClassLoader.getSystemResource("Java_logo.png").toURI());
        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        PDImageXObject image
                = PDImageXObject.createFromFile(path.toAbsolutePath().toString(), document);
        contentStream.drawImage(image, 0, 0);
        contentStream.close();

        document.save("pdfBoxImage.pdf");
        document.close();

    }

    public void fileEncryption() {

    }
}
