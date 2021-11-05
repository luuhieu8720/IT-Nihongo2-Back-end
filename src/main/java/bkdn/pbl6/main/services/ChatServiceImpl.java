package bkdn.pbl6.main.services;

import java.util.ArrayList;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bkdn.pbl6.main.entities.AccountEntity;
import bkdn.pbl6.main.entities.GroupChatEntity;
import bkdn.pbl6.main.entities.GroupChatMemberEntity;
import bkdn.pbl6.main.enums.Type;
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
		AccountEntity accountEntity = accountRepository.findByEmail(username);

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

		return new GroupChat(groupChatEntity);
	}

}
