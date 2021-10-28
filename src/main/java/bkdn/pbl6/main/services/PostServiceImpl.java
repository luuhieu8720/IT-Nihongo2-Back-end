package bkdn.pbl6.main.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;

import bkdn.pbl6.main.entities.AccountEntity;
import bkdn.pbl6.main.entities.PostEntity;
import bkdn.pbl6.main.enums.Gender;
import bkdn.pbl6.main.models.Post;
import bkdn.pbl6.main.repositories.AccountRepository;
import bkdn.pbl6.main.repositories.PostRepository;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private AccountRepository accountRepository;

	@Override
	public Post newPost(Post post) throws Exception {
		PostEntity postEntity = new PostEntity(post);

		AccountEntity accountEntity = accountRepository.findByUsername(post.getUsername());
		postEntity.setIdUser(accountEntity.getId());

		postRepository.save(postEntity);

		return post;
	}

	@Override
	public ArrayList<Post> getAll() {
		ArrayList<PostEntity> postEntities = postRepository.findAll();
		ArrayList<Post> rs = new ArrayList<>();
		for (PostEntity postEntity : postEntities)
			try {
				Post post = new Post(postEntity);
				AccountEntity accountEntity = accountRepository.findById(postEntity.getIdUser()).get();
				post.setUsername(accountEntity.getUsername());
				rs.add(post);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		return rs;
	}

	@Override
	public Post get(String id) throws Exception {
		Optional<PostEntity> optional = postRepository.findById(id);
		if (optional.isEmpty())
			throw new Exception("Id not found!");
		PostEntity postEntity = optional.get();

		Post post = new Post(postEntity);
		AccountEntity accountEntity = accountRepository.findById(postEntity.getIdUser()).get();
		post.setUsername(accountEntity.getUsername());

		return post;
	}

	@Override
	public ArrayList<Post> find(Post post) throws Exception {

		PostEntity postEntity = new PostEntity(post);
		
//		if (!StringUtils.hasText(post.getCity()))
//			post.setCity(null);
//		if (!StringUtils.hasText(post.getCourse()))
//			post.setCourse(null);
//		// if (!StringUtils.hasText(post.getDetails()))
//		post.setDetails(null);
//		if (!StringUtils.hasText(post.getDistrict()))
//			post.setDistrict(null);
//		// if (!StringUtils.hasText(post.getTitle()))
//		post.setTitle(null);
//		if (!StringUtils.hasText(post.getWard()))
//			post.setWard(null);
		if (postEntity.getGender() == Gender.None)
			postEntity.setGender(null);
//		post.setId(null);
//		// if (post.getSalary() != null && post.getSalary() <= 0)
//		post.setSalary(null);
//		post.setTime(null);

		ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll()
				.withIgnoreCase(true)
				.withStringMatcher(StringMatcher.CONTAINING)
//				.withMatcher("city", GenericPropertyMatcher.of(StringMatcher.CONTAINING, true))
//				.withMatcher("dictrict", GenericPropertyMatcher.of(StringMatcher.CONTAINING, true))
//				.withMatcher("ward", GenericPropertyMatcher.of(StringMatcher.CONTAINING, true))
//				.withMatcher("course", GenericPropertyMatcher.of(StringMatcher.CONTAINING, true))
				.withMatcher("gender", GenericPropertyMatcher.of(StringMatcher.EXACT, false))
				.withIgnorePaths("id", "time", "salary", "title", "detail", "idUser");

		Example<PostEntity> example = Example.of(postEntity, exampleMatcher);

		ArrayList<PostEntity> postEntities = new ArrayList<>(postRepository.findAll(example));

		ArrayList<Post> rs = new ArrayList<>();
		for (PostEntity pE : postEntities)
			try {
				Post p = new Post(pE);
				AccountEntity accountEntity = accountRepository.findById(pE.getIdUser()).get();
				p.setUsername(accountEntity.getUsername());
				rs.add(p);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}

		return rs;
	}

}
