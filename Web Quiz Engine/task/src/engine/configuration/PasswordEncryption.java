package engine.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Random;

@Component
public class PasswordEncryption {

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        String salt = "SALT";
        return new BCryptPasswordEncoder(12, new SecureRandom(salt.getBytes()));
    }

    @Bean
    public static String randomPassowrd(){
        String saltChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder stringBuilder = new StringBuilder();

        Random random = new Random();
        while (stringBuilder.length() < 18){
            int i = random.nextInt() * saltChars.length();
            stringBuilder.append(i);
        }
        return stringBuilder.toString();
    }
}
