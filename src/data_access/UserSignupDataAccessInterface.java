package data_access;

import use_case.SignupDsData;

public interface UserSignupDataAccessInterface {
    boolean existsByName(String identifier);

    void save(SignupDsData requestModel);
}
