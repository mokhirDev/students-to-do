package com.example.StudentToDo.controller;

import com.example.StudentToDo.aggregation.dto.student.ResponseMessage;
import com.example.StudentToDo.aggregation.dto.university.RequestUniversity;
import com.example.StudentToDo.aggregation.dto.university.ResponseUniversity;
import com.example.StudentToDo.service.interfaces.UniversityServiceInterface;
import io.github.jhipster.web.util.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


import java.util.List;

import static com.example.StudentToDo.utils.ApiUrls.*;

@RestController
@RequestMapping(UNIVERSITY_MAIN_URL)
@RequiredArgsConstructor
public class UniversityController {
    public final UniversityServiceInterface universityServiceInterface;

    @GetMapping(ALL)
    ResponseEntity<List<ResponseUniversity>> getAll(@RequestParam("page") int pageIndex,
                                                    @RequestParam("size") int pageSize,
                                                    @RequestParam MultiValueMap<String, String> queryParams,
                                                    UriComponentsBuilder uriBuilder) {
        Page<ResponseUniversity> page = universityServiceInterface.findAllUniversity(PageRequest.of(pageIndex, pageSize));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @PostMapping
    public ResponseEntity<ResponseUniversity> registerUniversity(@RequestBody RequestUniversity university) {
        return new ResponseEntity<>(universityServiceInterface.registerUniversity(university), HttpStatus.OK);
    }



    @GetMapping(value = GET_BY_ID, produces = "application/json")
    ResponseEntity<ResponseUniversity> findUniversityById(@PathVariable String entityId) {
        return new ResponseEntity<>(universityServiceInterface.findUniversityById(entityId), HttpStatus.OK);
    }

    @PutMapping(UPDATE)
    public ResponseEntity<ResponseUniversity> modifyUniver(
            @PathVariable String entityId,
            @RequestBody RequestUniversity univerForUpdate){
        return new ResponseEntity<>(universityServiceInterface.modify(univerForUpdate, entityId), HttpStatus.OK);
    }

    @DeleteMapping(DELETE)
    ResponseEntity<ResponseMessage> remove(@PathVariable String entityId) {
        return new ResponseEntity<>(universityServiceInterface.remove(entityId), HttpStatus.OK);
    }

    @GetMapping(FIND_BY_NAME)
    ResponseEntity<ResponseUniversity> findUniversityByName(@RequestBody RequestUniversity university) {
        return new ResponseEntity<>(universityServiceInterface.findUniversityByName(university), HttpStatus.OK);
    }
}
