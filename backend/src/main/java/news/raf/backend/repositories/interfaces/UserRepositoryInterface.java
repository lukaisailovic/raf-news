package news.raf.backend.repositories.interfaces;

import news.raf.backend.entities.User;

import java.util.List;

public interface UserRepositoryInterface {
     void save(User user);
     List<User> all();
     User find(String id);
     User findByEmail(String email);
}
