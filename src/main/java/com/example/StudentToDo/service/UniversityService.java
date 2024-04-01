package com.example.StudentToDo.service;

import com.example.StudentToDo.aggregation.entity.University;
import com.example.StudentToDo.exceptions.DatabaseException;
import com.example.StudentToDo.exceptions.NotFoundException;
import com.example.StudentToDo.aggregation.mapper.interfaces.UniversityMapperInterface;
import com.example.StudentToDo.aggregation.dto.student.ResponseMessage;
import com.example.StudentToDo.aggregation.dto.university.RequestUniversity;
import com.example.StudentToDo.aggregation.dto.university.ResponseUniversity;
import com.example.StudentToDo.repository.UniversityRepository;
import com.example.StudentToDo.service.interfaces.UniversityServiceInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;


@Service
@Log4j2
@RequiredArgsConstructor
public class UniversityService implements UniversityServiceInterface {
    private final UniversityRepository universityRepository;
    private final UniversityMapperInterface mapper;
    @Override
    public ResponseUniversity registerUniversity(RequestUniversity university) {
        University newUniver = mapper.toEntity(university);
        universityRepository.save(newUniver);
        return mapper.toDto(newUniver);
    }

    @Override
    public ResponseUniversity findUniversityById(String id) {
      try{
          Optional<University> byId = universityRepository.findById(Long.valueOf(id));
          if (byId.isEmpty()){
              log.warn(byId.get() + ": doesn't exist");
              throw new NotFoundException(byId.get().getId()+": bunday id mavjud emas!");
          }
          log.info(byId.get()+": is present");
          University university = byId.get();
          return mapper.toDto(university);
      }catch (NotFoundException ex){
          throw new NotFoundException(ex.getMessage());
      }catch (Exception ex){
          throw new DatabaseException(ex.getMessage());
      }
    }


    @Override
    public ResponseUniversity modify(RequestUniversity updateUniver, String stdId) {
        try{
            Optional<University> byId = universityRepository.findById(Long.valueOf(stdId));
            if (byId.isEmpty()){
                log.warn(byId.get() + ": doesn't exist");
                throw new NotFoundException(byId.get().getId()+": Bunday id mavjud emas!");
            }
            log.info(byId.get()+": is present");
            University university = byId.get();
            university.setName(updateUniver.getName());
            University saved = universityRepository.save(university);
            log.info(byId.get()+" is updated to: "+saved);
            return mapper.toDto(saved);
        }catch (NotFoundException ex){
            throw new NotFoundException(ex.getMessage());
        }catch (Exception ex){
            throw new DatabaseException(ex.getMessage());
        }
    }

    @Override
    public ResponseUniversity findUniversityByName(RequestUniversity university) {
        University byName = universityRepository.findByName(university.getName());
        log.info("University found by name(%s): ".formatted(university.getName())+" : "+university);
        return mapper.toDto(byName);
    }


    @Override
    public ResponseMessage remove(String univerId) {
        try{
            Long id = Long.valueOf(univerId);
            Optional<University> byId = universityRepository.findById(id);
            if (byId.isEmpty()) {
                log.warn(id + ": doesn't exist");
                throw new NotFoundException(byId.get().getId()+": Bunday id mavjud emas!");
            }
            universityRepository.deleteById(id);
            log.info(byId.get()+": is present");
            return ResponseMessage
                    .builder()
                    .message("Deleted successfully, student with id: %d".formatted(id))
                    .time(LocalDateTime.now())
                    .build();
        }catch (NotFoundException ex){
            throw new NotFoundException(ex.getMessage());
        }catch (Exception ex){
            throw new DatabaseException(ex.getMessage());
        }
    }

    @Override
    public Page<ResponseUniversity> findAllUniversity(Pageable pageable) {
        Page<University> universities = universityRepository.findAll(pageable);
        log.info("All universities found: "+universities);
        return universities.map(mapper::toDto);
    }

}
