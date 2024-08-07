package view;

import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupViewModel;
import use_case.signup.SignupInputBoundary;

import javax.swing.*;

import java.awt.AWTException;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

class SignupViewTest {

    @org.junit.jupiter.api.Test
    void testIt() {
        SignupInputBoundary sib = null;
        SignupController controller = new SignupController(sib);
        SignupViewModel viewModel = new SignupViewModel();
        JPanel signupView = new SignupView(controller, viewModel);
        JFrame jf = new JFrame();
        jf.setContentPane(signupView);
        jf.pack();
        jf.show();

        LabelTextPanel panel = (LabelTextPanel) signupView.getComponent(2);
        Point panelLoc = panel.getLocationOnScreen();
        System.out.println(panelLoc);

        JPasswordField pwdField = (JPasswordField) panel.getComponent(1);
        Point p = pwdField.getLocationOnScreen();
        System.out.println(p);

        int mouse = InputEvent.BUTTON1_MASK;
        try {
            Robot bot = new Robot();
            bot.mouseMove(p.x, p.y);
            bot.mousePress(mouse); // focus on the cursor
            bot.keyPress(KeyEvent.VK_Y);
            bot.keyPress(KeyEvent.VK_Z);
            Thread.sleep(1000);
        } catch (AWTException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("field |" + pwdField.getText());
        System.out.println("viewmodel |" + viewModel.getState().getPassword());
    }

    @org.junit.jupiter.api.Test
    void testIt2() throws InterruptedException {
        SignupInputBoundary sib = null;
        SignupController controller = new SignupController(sib);
        SignupViewModel viewModel = new SignupViewModel();
        JPanel signupView = new SignupView(controller, viewModel);
        JFrame jf = new JFrame();
        jf.setContentPane(signupView);
    jf.pack();
    jf.show();
        LabelTextPanel panel = (LabelTextPanel) signupView.getComponent(2);

        JPasswordField pwdField = (JPasswordField) panel.getComponent(1);
//        System.out.println(pwdField.requestFocusInWindow());

        KeyEvent event = new KeyEvent(
                pwdField,
                KeyEvent.KEY_TYPED,
                System.currentTimeMillis(),
                0,
                KeyEvent.VK_UNDEFINED,
                'Z');
        pwdField.dispatchEvent(event);
        Thread.sleep(200);

        event = new KeyEvent(
                pwdField,
                KeyEvent.KEY_TYPED,
                System.currentTimeMillis(),
                0,
                KeyEvent.VK_UNDEFINED,
                'Y');
        pwdField.dispatchEvent(event);
        Thread.sleep(200);

        System.out.println("field |" + pwdField.getText());
        System.out.println("viewmodel |" + viewModel.getState().getPassword());
    }
}
