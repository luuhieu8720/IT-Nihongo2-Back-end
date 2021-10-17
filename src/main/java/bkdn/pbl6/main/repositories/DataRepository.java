package bkdn.pbl6.main.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import bkdn.pbl6.main.entities.DataEntity;

public interface DataRepository extends MongoRepository<DataEntity, String> {

}
