package ru.netology.qa.test;

import android.view.View;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.Epic;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.ui.AppActivity;
import ru.netology.qa.data.Data;
import ru.netology.qa.steps.AuthorizationSteps;
import ru.netology.qa.steps.MainSteps;


@RunWith(AllureAndroidJUnit4.class)
@Epic("Страница авторизации «Authorization»")
public class AuthorizationTest {

    @Rule
    public ActivityScenarioRule<AppActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);

    AuthorizationSteps authorizationSteps = new AuthorizationSteps();
    MainSteps mainSteps = new MainSteps();
    private View decorView;

    @Before
    public void setUp() {
        mActivityScenarioRule.getScenario().onActivity(activity -> decorView = activity.getWindow().getDecorView());
        try {
            authorizationSteps.verifySignInButtonVisible();
        } catch (Exception e) {
            authorizationSteps.clickOnProfileImage();
            authorizationSteps.clickOnLogout();
        }
    }


    @Test
    @DisplayName("Авторизация зарегистрированного пользователя")
    public void registeredUserAuthorization() {
        authorizationSteps.fillInTheAuthorizationFields(Data.VALID_LOGIN, Data.VALID_PASSWORD);
        authorizationSteps.clickOnSignIn();
        mainSteps.theAllNewsItemIsDisplayed();
    }

    @Test
    @DisplayName("Авторизация не зарегистрированного пользователя")
    public void unregisteredUserAuthorization() {
        authorizationSteps.fillInTheAuthorizationFields(Data.INVALID_LOGIN, Data.INVALID_PASSWORD);
        authorizationSteps.clickOnSignIn();
        authorizationSteps.authorizationErrorMessageDisplay();
    }

    @Test
    @DisplayName("Авторизация с пустым полем Login")
    public void authorizationWithEmptyLogin() {
        authorizationSteps.fillInTheAuthorizationFields(Data.EMPTY_FIELD, Data.VALID_PASSWORD);
        authorizationSteps.clickOnSignIn();
        authorizationSteps.emptyFieldErrorMessageDisplay();
    }

    @Test
    @DisplayName("Авторизация с пустым полем Password")
    public void authorizationWithEmptyPassword() {
        authorizationSteps.fillInTheAuthorizationFields(Data.VALID_LOGIN, Data.EMPTY_FIELD);
        authorizationSteps.clickOnSignIn();
        authorizationSteps.emptyFieldErrorMessageDisplay();
    }

    @Test
    @DisplayName("Авторизация зарегистрированного пользователя c данными в верхнем регистре")
    public void authorizationWithUppercaseData() {
        authorizationSteps.fillInTheAuthorizationFields(Data.INVALID_LOGIN_VALUE, Data.INVALID_PASSWORD_VALUE);
        authorizationSteps.clickOnSignIn();
        authorizationSteps.authorizationErrorMessageDisplay();
    }

    @Test
    @DisplayName("Простая строка SQL инъекции в поле логин")
    public void simpleSqlInjectionInLoginField() {
        authorizationSteps.fillInTheAuthorizationFields(Data.SQL_OR_1_IS_1, Data.VALID_PASSWORD);
        authorizationSteps.clickOnSignIn();
        authorizationSteps.authorizationErrorMessageDisplay();
    }

    @Test
    @DisplayName("Простая строка SQL инъекции в поле пароль")
    public void simpleSqlInjectionInPasswordField() {
        authorizationSteps.fillInTheAuthorizationFields(Data.VALID_LOGIN, Data.SQL_OR_1_IS_1);
        authorizationSteps.clickOnSignIn();
        authorizationSteps.authorizationErrorMessageDisplay();
    }

    @Test
    @DisplayName("Простая строка SQL инъекции в поля логин и пароль одновременно")
    public void simpleSqlInjectionInBothFields() {
        authorizationSteps.fillInTheAuthorizationFields(Data.SQL_OR_1_IS_1, Data.SQL_OR_1_IS_1);
        authorizationSteps.clickOnSignIn();
        authorizationSteps.authorizationErrorMessageDisplay();
    }

    @Test
    @DisplayName("Простая строка XSS инъекции в поле логин")
    public void simpleXSSInjectionInLoginField() {
        authorizationSteps.fillInTheAuthorizationFields(Data.XSS_SCRIPT_ALERT, Data.VALID_PASSWORD);
        authorizationSteps.clickOnSignIn();
        authorizationSteps.authorizationErrorMessageDisplay();
    }

    @Test
    @DisplayName("Простая строка XSS инъекции в поле пароль")
    public void simpleXSSInjectionInPasswordField() {
        authorizationSteps.fillInTheAuthorizationFields(Data.VALID_LOGIN, Data.XSS_SCRIPT_ALERT);
        authorizationSteps.clickOnSignIn();
        authorizationSteps.authorizationErrorMessageDisplay();
    }

    @Test
    @DisplayName("Простая строка XSS инъекции в поля логин и пароль одновременно")
    public void simpleXssInjectionInBothFields() {
        authorizationSteps.fillInTheAuthorizationFields(Data.XSS_SCRIPT_ALERT, Data.XSS_SCRIPT_ALERT);
        authorizationSteps.clickOnSignIn();
        authorizationSteps.authorizationErrorMessageDisplay();
    }
}
