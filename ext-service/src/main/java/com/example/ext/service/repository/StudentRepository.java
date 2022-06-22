package com.example.ext.service.repository;

import com.example.ext.service.model.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Long> {

    @Query(value = "SELECT st FROM StudentEntity st WHERE st.room.id = :roomId")
    List<StudentEntity> findAllByRoomId(@Param("roomId") Long roomId);
}
