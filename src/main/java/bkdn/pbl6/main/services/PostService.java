package bkdn.pbl6.main.services;

import java.util.ArrayList;

import bkdn.pbl6.main.models.Post;

public interface PostService {

	public Post newPost(Post post) throws Exception;

	public ArrayList<Post> getAll();

}
