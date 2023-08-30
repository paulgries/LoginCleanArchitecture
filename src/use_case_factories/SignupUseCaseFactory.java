package use_case_factories;

import data_access.FileUserDataAccessObject;
import data_access.UserSignupDataAccessInterface;
import entity.CommonUserFactory;
import entity.PasswordValidator;
import entity.PasswordValidatorService;
import entity.UserFactory;
import interface_adapter.*;
import use_case.SignupInputBoundary;
import use_case.SignupInteractor;
import use_case.SignupOutputBoundary;
import view.SignupView;

import javax.swing.*;
import java.io.IOException;

public class SignupUseCaseFactory {
    public static SignupView create(ViewManagerModel viewManagerModel, LoginViewModel loginViewModel) {
        // The data for the views, such as username and password. This
        // will be changed by a presenter object that is reporting the
        // results from the use case. This is an observable, and will
        // be observed by the layout manager.

        try {
            SignupViewModel signupViewModel = new SignupViewModel();
            SignupController signupController = createUserSignupUseCase(viewManagerModel, signupViewModel, loginViewModel);
            return new SignupView(signupController, signupViewModel);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Could not open user data file.");
        }

        return null;
    }

    private static SignupController createUserSignupUseCase(ViewManagerModel viewManagerModel, SignupViewModel signupViewModel, LoginViewModel loginViewModel) throws IOException {
        UserSignupDataAccessInterface userDataAccessObject = new FileUserDataAccessObject("./users.csv");
        SignupOutputBoundary signupOutputBoundary = new SignupPresenter(viewManagerModel, signupViewModel, loginViewModel);
        UserFactory userFactory = new CommonUserFactory();
        SignupInputBoundary userSignupInteractor = new SignupInteractor(
                userDataAccessObject, signupOutputBoundary, userFactory);

        return new SignupController(userSignupInteractor);
    }
}
