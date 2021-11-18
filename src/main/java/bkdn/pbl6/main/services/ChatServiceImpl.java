package bkdn.pbl6.main.services;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bkdn.pbl6.main.entities.AccountEntity;
import bkdn.pbl6.main.entities.ChatEntity;
import bkdn.pbl6.main.entities.GroupChatEntity;
import bkdn.pbl6.main.entities.GroupChatMemberEntity;
import bkdn.pbl6.main.enums.Type;
import bkdn.pbl6.main.models.Chat;
import bkdn.pbl6.main.models.GroupChat;
import bkdn.pbl6.main.models.Member;
import bkdn.pbl6.main.repositories.AccountRepository;
import bkdn.pbl6.main.repositories.ChatRepository;
import bkdn.pbl6.main.repositories.GroupChatMemberRepository;
import bkdn.pbl6.main.repositories.GroupChatRepository;

@Service
public class ChatServiceImpl implements ChatService {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private ChatRepository chatRepository;

	@Autowired
	private GroupChatRepository groupChatRepository;

	@Autowired
	private GroupChatMemberRepository groupMemberRepository;

	@Override
	public ArrayList<GroupChat> getAllGroup(String username) throws Exception {
		AccountEntity accountEntity = accountRepository.findByUsername(username);

		ArrayList<GroupChatMemberEntity> groupChatMemberEntities = groupMemberRepository
				.findByIdAccount(accountEntity.getId());

		ArrayList<GroupChat> groupChats = new ArrayList<>(groupChatMemberEntities.size());

		TreeMap<String, AccountEntity> tree = new TreeMap<>();
		tree.put(accountEntity.getId(), accountEntity);

		for (GroupChatMemberEntity groupChatMemberEntity : groupChatMemberEntities) {
			try {
				GroupChatEntity groupChatEntity = groupChatRepository.findById(groupChatMemberEntity.getIdGroup())
						.get();

				if (groupChatEntity.getType() == Type.Pm) {
					GroupChatMemberEntity to = groupMemberRepository
							.findByIdAndIdAccountNotLike(groupChatEntity.getId(), accountEntity.getId());
					String toId = to.getIdAccount();
					if (tree.containsKey(toId)) {
						groupChatEntity.setName(tree.get(toId).getName());
					} else {
						AccountEntity toAccount = accountRepository.findById(toId).get();
						tree.put(toId, toAccount);
						groupChatEntity.setName(toAccount.getName());
					}
				}

				GroupChat groupChat = new GroupChat(groupChatEntity);
				groupChats.add(groupChat);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				continue;
			}
		}

		return groupChats;
	}

	@Override
	public GroupChat newGroup(GroupChat groupChat) throws Exception {
		GroupChatEntity groupChatEntity = new GroupChatEntity();
		groupChatEntity.setLength(0);

		ArrayList<GroupChatMemberEntity> groupChatMemberEntities = new ArrayList<>(groupChat.getMembers().size());

		for (Member member : groupChat.getMembers()) {
			AccountEntity accountEntity = accountRepository.findByUsername(member.getUsername());
			if (accountEntity == null) {
				throw new Exception(member.getUsername() + " does not exist!");
			}

			GroupChatMemberEntity groupChatMemberEntity = new GroupChatMemberEntity();
			groupChatMemberEntity.setIdAccount(accountEntity.getId());
			groupChatMemberEntity.setSeenIndex(-1);
			groupChatMemberEntities.add(groupChatMemberEntity);
		}

		if (groupChat.getName() == null) {
			if (groupChat.getMembers().size() == 2) {
				groupChatEntity.setType(Type.Pm);
				groupChatEntity.setName(null);
			} else {
				groupChatEntity.setType(Type.Group);
				String name = groupChat.getMembers().get(0).getUsername();
				for (int i = 1; i < groupChat.getMembers().size(); ++i) {
					name += ", " + groupChat.getMembers().get(1).getUsername();
				}
				groupChatEntity.setName(name);
			}
		} else {
			groupChatEntity.setType(Type.Group);
			groupChatEntity.setName(groupChat.getName());
		}

		groupChatEntity = groupChatRepository.save(groupChatEntity);

		for (GroupChatMemberEntity groupChatMemberEntity : groupChatMemberEntities) {
			groupChatMemberEntity.setIdGroup(groupChatEntity.getId());
			groupMemberRepository.save(groupChatMemberEntity);
		}

		return new GroupChat(groupChatEntity);
	}

	@Override
	public GroupChat getGroup(GroupChat groupChat) throws Exception {

		Optional<GroupChatEntity> optional = groupChatRepository.findById(groupChat.getId());
		if (optional.isEmpty()) {
			throw new Exception("Group don't exits!");
		}

		groupChat = new GroupChat(optional.get());

		ArrayList<GroupChatMemberEntity> groupChatMemberEntities = groupMemberRepository
				.findByIdGroup(optional.get().getId());
		ArrayList<Member> members = new ArrayList<>(groupChatMemberEntities.size());
		for (GroupChatMemberEntity groupChatMemberEntity : groupChatMemberEntities) {
			try {
				AccountEntity accountEntity = accountRepository.findById(groupChatMemberEntity.getIdAccount()).get();
				Member member = new Member(accountEntity.getUsername(), groupChatMemberEntity.getSeenIndex());
				members.add(member);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				continue;
			}
		}
		groupChat.setMembers(members);

		return groupChat;
	}

	@Override
	public Chat sendChat(Chat chat) throws Exception {

		Optional<GroupChatEntity> optional = groupChatRepository.findById(chat.getIdGroup());
		if (optional.isEmpty()) {
			throw new Exception("Group don't exits!");
		}
		GroupChatEntity groupChatEntity = optional.get();

		AccountEntity accountEntity = accountRepository.findByUsername(chat.getUsername());

		ChatEntity chatEntity = new ChatEntity();
		chatEntity.setContent(chat.getContent());
		chatEntity.setIdAccount(accountEntity.getId());
		chatEntity.setSendTime(Timestamp.from(new Date().toInstant()));
		chatEntity.setIndex(groupChatEntity.getLength() + 1);

		chatEntity = chatRepository.save(chatEntity);

		groupChatEntity.setLength(chatEntity.getIndex());
		groupChatRepository.save(groupChatEntity);

		chat.setSendTime(chatEntity.getSendTime());
		chat.setIndex(chatEntity.getIndex());

		return chat;
	}

}
