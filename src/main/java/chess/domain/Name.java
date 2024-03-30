package chess.domain;

import java.util.Objects;

public class Name {
    private final String value;

    public Name(final String value) {
        validate(value);
        this.value = value;
    }

    private void validate(final String value) {
        if (value.length() < 1 || value.length() > 10) {
            throw new IllegalArgumentException("이름은 1자 이상, 10자 이하만 가능합니다.");
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Name name = (Name) o;
        return Objects.equals(value, name.value);
    }

    public String getValue() {
        return value;
    }
}
