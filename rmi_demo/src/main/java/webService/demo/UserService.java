package webService.demo;

import java.util.List;

import javax.jws.WebService;
@WebService
//@Path("users")
public interface UserService {//服务端提供的服务
	List<User> getUsers();
	Response delete(String id);
	Response add(User user);
}
