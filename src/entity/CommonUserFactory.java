package entity;

public class CommonUserFactory implements UserFactory {
    /**
     * Requires: password is valid.
     * @param name
     * @param password
     * @return
     */
    @Override
    public User create(String name, String password) {
        return new CommonUser(name, password);
    }
}
