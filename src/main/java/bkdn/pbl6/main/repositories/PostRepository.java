package bkdn.pbl6.main.repositories;

import java.util.ArrayList;

import org.springframework.data.mongodb.repository.MongoRepository;

import bkdn.pbl6.main.entities.PostEntity;

public interface PostRepository extends MongoRepository<PostEntity, String> {

	ArrayList<PostEntity> findAll();

}
