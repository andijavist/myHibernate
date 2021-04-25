import java.util.Objects;
@NameTable(name = "person")
public class Person {
    @NameColumn(name = "name")
    String personName = "Stepan";
    @NameColumn(name = "lastName")
    String personLastName = "Stepanov";

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPersonLastName() {
        return personLastName;
    }

    public void setPersonLastName(String personLastName) {
        this.personLastName = personLastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(personName, person.personName) && Objects.equals(personLastName, person.personLastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personName, personLastName);
    }
}
