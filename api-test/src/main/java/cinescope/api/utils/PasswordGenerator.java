package cinescope.api.utils;

import net.datafaker.Faker;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class PasswordGenerator {

    public static final Faker faker = new Faker(new Locale("ru"));
    private static final String SPECIAL_CHARS = "?@#$%^&*_\\-+()[]{}><\\\\/\\\\|\"'.,:;";

    public static String generateValidPassword() {
        String letterPart = faker.letterify("????");     // минимум одна буква
        String digitPart = faker.numerify("##");         // минимум одна цифра
        char specialChar = SPECIAL_CHARS.charAt(new Random().nextInt(SPECIAL_CHARS.length()));

        String base = letterPart + digitPart + specialChar;

        int remainingLength = 8 + new Random().nextInt(13) - base.length(); // до 20 символов

        for (int i = 0; i < remainingLength; i++) {
            base += faker.regexify("[a-zA-Zа-яА-Я0-9" + Pattern.quote(SPECIAL_CHARS) + "]");
        }

        List<Character> chars = base.chars().mapToObj(c -> (char) c).collect(Collectors.toList());
        Collections.shuffle(chars);

        StringBuilder password = new StringBuilder();
        chars.forEach(password::append);

        return password.toString();
    }
}
