package bkdn.pbl6.main.repositories;

import java.util.ArrayList;

import org.springframework.data.mongodb.repository.MongoRepository;

import bkdn.pbl6.main.entities.ReportEntity;

public interface ReportRepository extends MongoRepository<ReportEntity, String> {

	ArrayList<ReportEntity> findByIdUser(String idUser);

	ArrayList<ReportEntity> findByIdTutor(String idTutor);

	ArrayList<ReportEntity> findAll();

}
