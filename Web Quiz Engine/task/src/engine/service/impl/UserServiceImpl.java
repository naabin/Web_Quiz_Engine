package engine.service.impl;

import engine.configuration.PasswordEncryption;
import engine.model.User;
import engine.repository.UserRepository;
import engine.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncryption passwordEncryption;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncryption passwordEncryption) {
        this.userRepository = userRepository;
        this.passwordEncryption = passwordEncryption;
    }

    @Override
    public User register(User user) {
        User username = loadUserByUsername(user.getUsername());
        if (username != null){
            return username;
        }
        else {
            user.setPassword(this.passwordEncryption.passwordEncoder().encode(user.getPassword()));
            return this.userRepository.save(user);
        }
    }

    @Override
    public User getByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByEmail(username);
    }
}
