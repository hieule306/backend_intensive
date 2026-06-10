package service;

import java.util.ArrayList;
import java.util.List;
import model.Finance;
import model.User;
import repository.UserRepository;

public class UserService {

    private final UserRepository userRepository;
    
    private List<User> users;
    private List<Finance> finances;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.users = userRepository.findAll();
        this.finances = userRepository.findFinances();
    }

    public void register(String firstName, String lastName, String dob, String username, String password) {
        for (User existing : users) {
            if (existing.username.equalsIgnoreCase(username.trim())) {
                throw new IllegalArgumentException("Username exists");
            }
        }

        users.add(new User(firstName, lastName, dob, username, password, false));
        userRepository.saveAll(users);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> findByName(String query) {
        String normalized = query == null ? "" : query.trim().toLowerCase();
        List<User> matches = new ArrayList<>();

        for (User user : users) {
            if (user.username.toLowerCase().contains(normalized)
                    || user.username.toLowerCase().contains(normalized)) {
                matches.add(user);
            }
        }

        return matches;
    }

    public AuthResult login(String username, String enteredPassword, int attemptsLeftBeforeAttempt) {
        for (User user : users) {
            if (user.username.equals(username.trim())) {
                if (user.locked) {
                    return AuthResult.locked();
                }

                if (user.password.equals(enteredPassword)) {
                    return AuthResult.success();
                }

                if (attemptsLeftBeforeAttempt <= 1) {
                    user.locked = true;
                    userRepository.saveAll(users);
                    return AuthResult.lockedNow();
                }

                return AuthResult.invalidCredentials();
            }
        }

        return AuthResult.userNotFound();
    }

    public void resetDatabase() {
        userRepository.deleteAll();
    }

    public User getUser(String username){
        for(User existing: users){
            if(existing.username.equalsIgnoreCase(username.trim()))
                return existing;
        }
        return new User();
    }

    public Finance getFinance(String username){
        for(Finance existing: finances){
            if(existing.username.equalsIgnoreCase(username.trim()))
                return existing;
        }
        return new Finance();
    }

    // ------------------- AuthStatus ----------
    public static final class AuthResult {
        public enum Status {
            SUCCESS,
            USER_NOT_FOUND,
            LOCKED,
            LOCKED_NOW,
            INVALID_CREDENTIALS
        }

        private final Status status;

        private AuthResult(Status status) {
            this.status = status;
        }

        public Status getStatus() {
            return status;
        }

        public static AuthResult success() {
            return new AuthResult(Status.SUCCESS);
        }

        public static AuthResult userNotFound() {
            return new AuthResult(Status.USER_NOT_FOUND);
        }

        public static AuthResult locked() {
            return new AuthResult(Status.LOCKED);
        }

        public static AuthResult lockedNow() {
            return new AuthResult(Status.LOCKED_NOW);
        }

        public static AuthResult invalidCredentials() {
            return new AuthResult(Status.INVALID_CREDENTIALS);
        }
    }
}
