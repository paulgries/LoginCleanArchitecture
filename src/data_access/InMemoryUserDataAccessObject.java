package data_access;

import use_case.SignupDsData;

import java.util.HashMap;
import java.util.Map;

public class InMemoryUserDataAccessObject implements UserSignupDataAccessInterface {

    final private Map<String, SignupDsData> users = new HashMap<>();

    /**
     * @param identifier the user's username
     * @return whether the user exists
     */
    @Override
    public boolean existsByName(String identifier) {
        return users.containsKey(identifier);
    }

    /**
     * @param requestModel the data to save
     */
    @Override
    public void save(SignupDsData requestModel) {
        users.put(requestModel.getUsername(), requestModel);
    }
}
