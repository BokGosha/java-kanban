package model;

import java.util.Objects;

public class Coach {

    private final String surname;
    private final String name;
    private final String middleName;

    public Coach(String surname, String name, String middleName) {
        this.surname = surname;
        this.name = name;
        this.middleName = middleName;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public String getMiddleName() {
        return middleName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coach coach = (Coach) o;
        return Objects.equals(this.name, coach.name)
                && Objects.equals(this.surname, coach.surname)
                && Objects.equals(this.middleName, coach.middleName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, middleName);
    }

    @Override
    public String toString() {
        return "Coach{" +
                "surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", middleName='" + middleName + '\'' +
                '}';
    }
}
