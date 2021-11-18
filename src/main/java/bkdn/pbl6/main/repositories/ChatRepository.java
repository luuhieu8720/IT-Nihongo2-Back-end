package bkdn.pbl6.main.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import bkdn.pbl6.main.entities.ChatEntity;

public interface ChatRepository extends MongoRepository<ChatEntity, String> {

}
