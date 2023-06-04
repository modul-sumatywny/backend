package restaurant.model.dto;

import lombok.Data;

import java.util.regex.Pattern;

@Data
public class RegistrationDto {

    private static final String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    private static final Pattern emailRegex = Pattern.compile(regexPattern);

    private String email;

    private String password;

    public static boolean validateEmail(String emailAddress) {
        return emailRegex
                .matcher(emailAddress)
                .matches();
    }
}
