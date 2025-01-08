package ec.com.sofka.aggregate.entities.user.values.objects;

import ec.com.sofka.generics.interfaces.IValueObject;

import java.util.regex.Pattern;

public record Email(String value) implements IValueObject<String> {

    private static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    public Email(final String value) {
        this.value = isValid(value);
    }

    public static Email of(final String value) {
        return new Email(value);
    }

    private String isValid(final String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Email cannot be null or blank.");
        }
        if (!EMAIL_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("Invalid email format.");
        }
        return value;
    }

    @Override
    public String getValue() {
        return this.value;
    }
}
