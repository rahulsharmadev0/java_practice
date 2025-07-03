package design_patterns.builder_method;

public class UserProfileDemo {

    public static void main(String[] args) {
        User user = new UserBuilder().setName("Alice").setAddress(
                "123 Main St, Springfield, USA"
        ).setAge("30").setEmail(
                "free@gmail.com"
        ).setPhone("987654310").build();

        System.out.println(user);
    }
}


/*
 * Build a User Profile
 * --------------------------
 *   Create a User class using the Builder pattern.
 *   Fields: name, age, email, phone, address
 *   Make the object immutable
 *   Allow flexible creation: set only name and email or all fields
 *   Add .build() method to return the object
 * */

interface Builder<T> {
    public T build();
}

class User {
    private final String name, age, email, phone, address;

    User(String name, String age, String email, String phone, String address) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                '}';

    }
}

class UserBuilder implements Builder<User> {
    String name, age, email, phone, address;

    public UserBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public UserBuilder setAge(String age) {
        this.age = age;
        return this;
    }

    public UserBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public UserBuilder setAddress(String address) {
        this.address = address;
        return this;
    }

    public User build() {
        return new User(name, age, email, phone, address);
    }

}