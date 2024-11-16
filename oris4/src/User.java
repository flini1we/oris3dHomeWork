public class User {

    private final Long id;
    private final String firstName;
    private final String lastName;
    private final Integer age;
    private final String email;
    private final String phoneNumber;
    private final String address;

    public User(Long id,
                String firstName,
                String lastName,
                Integer age,
                String email,
                String phoneNumber,
                String address
    ) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Integer getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }
}