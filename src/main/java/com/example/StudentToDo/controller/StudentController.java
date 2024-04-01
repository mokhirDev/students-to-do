package com.example.StudentToDo.controller;

import com.example.StudentToDo.aggregation.dto.student.RequestStudent;
import com.example.StudentToDo.aggregation.dto.student.ResponseMessage;
import com.example.StudentToDo.aggregation.dto.student.ResponseStudent;
import com.example.StudentToDo.aggregation.dto.student.StdImgResponse;
import com.example.StudentToDo.service.interfaces.ImageServiceInterface;
import com.example.StudentToDo.service.interfaces.StudentServiceInterface;
import io.github.jhipster.web.util.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

import static com.example.StudentToDo.utils.ApiUrls.*;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping(STUDENT_MAIN_URL)
@RequiredArgsConstructor
public class StudentController {
    public final StudentServiceInterface studentServiceInterface;
    public final ImageServiceInterface imageServiceInterface;

    @GetMapping(ALL)
    ResponseEntity<List<ResponseStudent>> getAll(@RequestParam("page") int pageIndex,
                                                 @RequestParam("size") int pageSize,
                                                 @RequestParam MultiValueMap<String, String> queryParams,
                                                 UriComponentsBuilder uriBuilder) {
        Page<ResponseStudent> page = studentServiceInterface.findAllStudents(PageRequest.of(pageIndex, pageSize));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping(GET_EXCEL)
    ResponseEntity<byte[]> getAll() throws IOException {
        byte[] allStudentExcel = studentServiceInterface.getAllStudentExcel();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=studentInfo.xlsx");
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<>(allStudentExcel, headers, HttpStatus.OK);
    }

    @GetMapping(GET_USER_CV_PDF)
    ResponseEntity<byte[]> getStudentCV(@PathVariable String userId) throws IOException {
        byte[] studentPdf = studentServiceInterface.getStudentCVPdf(userId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=Resume.pdf");
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<>(studentPdf, headers, HttpStatus.CREATED);
    }

    @PostMapping(UPLOAD_USER_IMG)
    public ResponseEntity<StdImgResponse> uploadUserImg(
            @RequestBody RequestStudent student
    ) throws Exception {
        return new ResponseEntity<>(imageServiceInterface.uploadImage(student), HttpStatus.OK);
    }

    @GetMapping(value = DOWNLOAD_USER_IMG_AVATAR, consumes = MediaType.ALL_VALUE)
    public ResponseEntity<String> downloadUserImgAvatar(
            @RequestBody RequestStudent student) throws IOException {
        return new ResponseEntity<>(imageServiceInterface.downloadImage(student.getStudent().getId()), HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<ResponseStudent> registerStudent(
            @Valid
            @RequestBody RequestStudent student) {
//        log.trace("A TRACE Message");
//        log.debug("A DEBUG Message");
//        log.info("An INFO Message");
//        log.warn("A WARN Message");
//        log.error("An ERROR Message");
        return new ResponseEntity<>(studentServiceInterface.registerStudent(student), HttpStatus.CREATED);
    }

    @GetMapping(GET_BY_NAME)
    List<ResponseStudent> retrieveByName(@PathVariable String entityName) {
        return studentServiceInterface.findStudentByName(entityName);
    }

    @GetMapping(value = GET_BY_ID, produces = "application/json")
    ResponseEntity<ResponseStudent> findStudentById(@PathVariable String entityId) {
        return new ResponseEntity<>(studentServiceInterface.findStudentById(entityId), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<ResponseStudent> modifyStudent(
            @RequestBody RequestStudent stdForUpdate){
        return new ResponseEntity<>(studentServiceInterface.modify(stdForUpdate), HttpStatus.OK);
    }

    @DeleteMapping(DELETE)
    ResponseEntity<ResponseMessage> remove(@PathVariable String entityId) {
        return new ResponseEntity<>(studentServiceInterface.remove(entityId), HttpStatus.OK);
    }

    @PostMapping(SET_DEPARTMENT)
    ResponseEntity<ResponseStudent> setDepartment(@RequestBody RequestStudent student){
        return new ResponseEntity<>(studentServiceInterface.setDepartment(student), HttpStatus.OK);
    }
}
