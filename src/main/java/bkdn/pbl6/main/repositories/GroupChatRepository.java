package bkdn.pbl6.main.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import bkdn.pbl6.main.entities.GroupChatEntity;

public interface GroupChatRepository extends MongoRepository<GroupChatEntity, String> {

}
