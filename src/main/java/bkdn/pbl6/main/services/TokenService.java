package bkdn.pbl6.main.services;

public interface TokenService {

	public String newToken(Integer id);

	public Integer getId(String token);
	
	public Boolean isHasId(Integer id);

}
