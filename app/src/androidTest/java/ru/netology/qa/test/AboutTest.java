package ru.netology.qa.test;

import androidx.test.espresso.intent.Intents;
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
import ru.netology.qa.steps.AboutSteps;
import ru.netology.qa.steps.AuthorizationSteps;
import ru.netology.qa.steps.MainSteps;

@RunWith(AllureAndroidJUnit4.class)
@Epic(value = "Страница о приложении «About»")
public class AboutTest {

    @Rule
    public ActivityScenarioRule<AppActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);

    AuthorizationSteps authPage = new AuthorizationSteps();
    AboutSteps aboutSteps = new AboutSteps();
    MainSteps mainSteps = new MainSteps();

    @Before
    public void setUp() {
        try {
            authPage.verifySignInButtonVisible();
        } catch (Exception e) {
            authPage.clickOnProfileImage();
            authPage.clickOnLogout();
        }
        authPage.fillInTheAuthorizationFields(Data.VALID_LOGIN, Data.VALID_PASSWORD);
        authPage.clickOnSignIn();
        mainSteps.clickOnHamburgerMenu();
        mainSteps.clickOnAbout();
    }

    @Test
    @DisplayName("Выход из раздела About кнопкой назад в приложении")
    public void checkingAllElemInAbout() {
        aboutSteps.clickOnBack();
        mainSteps.theAllNewsItemIsDisplayed();
    }

    @Test
    @DisplayName("Отображение лейбла приложения")
    public void shouldDisplayTheAppLabel() {
        aboutSteps.assertDisplayApplicationName();
    }

    @Test
    @DisplayName("Отображение текста версии приложения")
    public void displayTheApplicationVersionText() {
        aboutSteps.appVersionTextApproval();
    }

    @Test
    @DisplayName("Отображение номера версии приложения")
    public void displayTheApplicationVersionNumber() {
        aboutSteps.appVersionNumberApproval();
    }

    @Test
    @DisplayName("Отображение метки политики конфиденциальности")
    public void shouldDisplayPrivacyPolicyLabel() {
        aboutSteps.assertDisplayOfPrivacyPolicyLabel();
    }

    @Test
    @DisplayName("Отображение метки условия эксплуатации")
    public void shouldDisplayTermsOfUseLabel() {
        aboutSteps.assertDisplayOfTermsOfUseLabel();
    }

    @Test
    @DisplayName("Отображение лейбла компании")
    public void shouldDisplayTheCompanyNameLabel() {
        aboutSteps.assertDisplayTheCompanyNameLabel();
    }

    @Test
    @DisplayName("Должен иметь правильный URL Политика конфиденциальности")
    public void thereMustBeCorrectPrivacyPolicyUrl() {
        Intents.init();
        aboutSteps.clickOnPrivacyPolicy();
        aboutSteps.verifyIntent(Data.PRIVACY_POLICY_URL);
        Intents.release();
    }

    @Test
    @DisplayName("Должен иметь правильный URL Условия эксплуатации")
    public void thereMustBeCorrectTermsOfUseUrl() {
        Intents.init();
        aboutSteps.clickOnTermsOfUse();
        aboutSteps.verifyIntent(Data.TERMS_OF_USE_URL);
        Intents.release();
    }
}
