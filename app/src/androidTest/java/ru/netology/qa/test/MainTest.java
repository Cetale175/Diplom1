package ru.netology.qa.test;

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
import ru.netology.qa.steps.NewsSteps;
import ru.netology.qa.steps.QuotesSteps;


@RunWith(AllureAndroidJUnit4.class)
@Epic("Основная страница «Main»")
public class MainTest {

    @Rule
    public ActivityScenarioRule<AppActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);

    AuthorizationSteps authPage = new AuthorizationSteps();
    MainSteps mainSteps = new MainSteps();
    NewsSteps newsSteps = new NewsSteps();
    AboutSteps aboutSteps = new AboutSteps();
    QuotesSteps quotesSteps = new QuotesSteps();

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
    }

    @Test
    @DisplayName("Навигация к странице News со страницы Main")
    public void shouldGoToNewsPageFromMainPage() {
        mainSteps.clickOnHamburgerMenu();
        mainSteps.clickOnNews();
        newsSteps.showNewsManagementButton();
    }

    @Test
    @DisplayName("Навигация к странице About со страницы Main")
    public void shouldGoToAboutPageFromMainPage() {
        mainSteps.clickOnHamburgerMenu();
        mainSteps.clickOnAbout();
        aboutSteps.backButtonVisibility();
    }

    @Test
    @DisplayName("Перейти на страницу Our Mission со страницы Main")
    public void shouldGoToOurMissionFromMainPage() {
        mainSteps.openPageWithQuotes();
        quotesSteps.visibilityTitleLoveIsAll();
    }

    @Test
    @DisplayName("Выход из учетной записи со страницы управления новостями")
    public void shouldLogOutOfAccountOnTheMainPage() {
        authPage.clickOnProfileImage();
        authPage.clickOnLogout();
        authPage.assertLoginPage();
    }

    @Test
    @DisplayName("Аккордеон списка новостей на основной странице Main")
    public void checkMainNewsAccordion() {
        mainSteps.expandMaterialButton();
        mainSteps.allNewsItemNotDisplayed();
        mainSteps.expandMaterialButton();
        mainSteps.theAllNewsItemIsDisplayed();
    }

    @Test
    @DisplayName("Перейти на страницу News нажав на кнопку-ссылку ALL NEWS")
    public void goToTheButtonAllNews() {
        mainSteps.clickOnAllNews();
        newsSteps.showNewsManagementButton();
    }
}
