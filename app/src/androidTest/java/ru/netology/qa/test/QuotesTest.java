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
@Epic("Cтраница c тематическими цитатами «Quotes»")
public class QuotesTest {

    @Rule
    public ActivityScenarioRule<AppActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);

    AuthorizationSteps authPage = new AuthorizationSteps();
    QuotesSteps quotesSteps = new QuotesSteps();
    MainSteps mainSteps = new MainSteps();
    NewsSteps newsSteps = new NewsSteps();
    AboutSteps aboutSteps = new AboutSteps();

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
        mainSteps.openPageWithQuotes();
    }

    @Test
    @DisplayName("Навигация к странице Main со страницы с цитатами")
    public void shouldGoToMainPageFromQuotePage() {
        mainSteps.clickOnHamburgerMenu();
        mainSteps.clickOnMain();
        mainSteps.theAllNewsItemIsDisplayed();
    }

    @Test
    @DisplayName("Навигация к странице News со страницы c цитатами")
    public void shouldGoToNewsPageFromQuotePage() {
        mainSteps.clickOnHamburgerMenu();
        mainSteps.clickOnNews();
        newsSteps.showNewsManagementButton();
    }

    @Test
    @DisplayName("Навигация к странице About со страницы с цитатами")
    public void shouldGoToAboutPageFromQuotePage() {
        mainSteps.clickOnHamburgerMenu();
        mainSteps.clickOnAbout();
        aboutSteps.backButtonVisibility();
    }

    @Test
    @DisplayName("Выход из учетной записи со страницы с цитатами")
    public void shouldLogOutOfAccountOnTheQuotePage() {
        authPage.clickOnProfileImage();
        authPage.clickOnLogout();
        authPage.assertLoginPage();
    }

    @Test
    @DisplayName("Отображение заголовока 'Love is all' на странице с цитатами")
    public void shouldDisplayThePageTitle() {
        quotesSteps.visibilityTitleLoveIsAll();
    }

    @Test
    @DisplayName("Отображение заголовков цитат")
    public void verifyQuoteTitle() {
        quotesSteps.assertDisplayOfAllQuoteTitles();
    }

    @Test
    @DisplayName("Отображение описания развернутой цитаты 1")
    public void shouldDisplayDescriptionOfQuotes1() {
        quotesSteps.expandQuoteByPosition(0);
        quotesSteps.scrollToQuotePosition(0);
        quotesSteps.checkQuoteDescription(Data.QUOTE_1_DESCRIPTION);
    }

    @Test
    @DisplayName("Отображение описания развернутой цитаты 2")
    public void shouldDisplayDescriptionOfQuotes2() {
        quotesSteps.expandQuoteByPosition(1);
        quotesSteps.scrollToQuotePosition(1);
        quotesSteps.checkQuoteDescription(Data.QUOTE_2_DESCRIPTION);
    }

    @Test
    @DisplayName("Отображение описания развернутой цитаты 3")
    public void shouldDisplayDescriptionOfQuotes3() {
        quotesSteps.expandQuoteByPosition(2);
        quotesSteps.scrollToQuotePosition(2);
        quotesSteps.checkQuoteDescription(Data.QUOTE_3_DESCRIPTION);
    }

    @Test
    @DisplayName("Отображение описания развернутой цитаты 4")
    public void shouldDisplayDescriptionOfQuotes4() {
        quotesSteps.expandQuoteByPosition(3);
        quotesSteps.scrollToQuotePosition(3);
        quotesSteps.checkQuoteDescription(Data.QUOTE_4_DESCRIPTION);
    }

    @Test
    @DisplayName("Отображение описания развернутой цитаты 5")
    public void shouldDisplayDescriptionOfQuotes5() {
        quotesSteps.expandQuoteByPosition(4);
        quotesSteps.scrollToQuotePosition(4);
        quotesSteps.checkQuoteDescription(Data.QUOTE_5_DESCRIPTION);
    }

    @Test
    @DisplayName("Отображение описания развернутой цитаты 6")
    public void shouldDisplayDescriptionOfQuotes6() {
        quotesSteps.expandQuoteByPosition(5);
        quotesSteps.scrollToQuotePosition(5);
        quotesSteps.checkQuoteDescription(Data.QUOTE_6_DESCRIPTION);
    }

    @Test
    @DisplayName("Отображение описания развернутой цитаты 7")
    public void shouldDisplayDescriptionOfQuotes7() {
        quotesSteps.expandQuoteByPosition(6);
        quotesSteps.scrollToQuotePosition(6);
        quotesSteps.checkQuoteDescription(Data.QUOTE_7_DESCRIPTION);
    }

    @Test
    @DisplayName("Отображение описания развернутой цитаты 8")
    public void shouldDisplayDescriptionOfQuotes8() {
        quotesSteps.expandQuoteByPosition(7);
        quotesSteps.scrollToQuotePosition(7);
        quotesSteps.checkQuoteDescription(Data.QUOTE_8_DESCRIPTION);
    }
}
