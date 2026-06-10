package repository;

import java.util.List;
import model.Finance;
import model.User;

public interface UserRepository {
    List<User> findAll();
    List<Finance> findFinances();
    void saveAll(List<User> users);
    void deleteAll();
}
