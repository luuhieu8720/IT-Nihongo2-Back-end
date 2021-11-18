package bkdn.pbl6.main.repositories;

import java.util.ArrayList;

import org.springframework.data.mongodb.repository.MongoRepository;

import bkdn.pbl6.main.entities.GroupChatMemberEntity;

public interface GroupChatMemberRepository extends MongoRepository<GroupChatMemberEntity, String> {

	ArrayList<GroupChatMemberEntity> findByIdAccount(String idAccount);

	GroupChatMemberEntity findByIdAndIdAccountNotLike(String id, String idAccount);

	ArrayList<GroupChatMemberEntity> findByIdGroup(String idGroup);

}
