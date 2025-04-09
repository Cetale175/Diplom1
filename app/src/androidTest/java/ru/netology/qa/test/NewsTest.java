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
import ru.netology.qa.steps.AboutSteps;
import ru.netology.qa.steps.AuthorizationSteps;
import ru.netology.qa.steps.EditorialSteps;
import ru.netology.qa.steps.MainSteps;
import ru.netology.qa.steps.NewsSteps;
import ru.netology.qa.steps.QuotesSteps;


@RunWith(AllureAndroidJUnit4.class)
@Epic(value = "Приветствующая страница «News»")
public class NewsTest {

    @Rule
    public ActivityScenarioRule<AppActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);

    AuthorizationSteps authPage = new AuthorizationSteps();
    NewsSteps newsSteps = new NewsSteps();
    MainSteps mainSteps = new MainSteps();
    AboutSteps aboutSteps = new AboutSteps();
    QuotesSteps quotesSteps = new QuotesSteps();
    EditorialSteps editorialSteps = new EditorialSteps();

    private View decorView;

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
        mainSteps.clickOnNews();
    }


    @Test
    @DisplayName("Навигация к странице Main с приветствующей страницы 'News'")
    public void shouldGoToMainPageFromNewsPage() {
        mainSteps.clickOnHamburgerMenu();
        mainSteps.clickOnMain();
        mainSteps.theAllNewsItemIsDisplayed();
    }

    @Test // Тест не проходит! Кнопка "About" в гамбургер-меню не активна с приветствующей страницы "News"
    @DisplayName("Навигация к странице About с приветствующей страницы 'News'")
    public void shouldGoToAboutPageFromNewsPage() {
        mainSteps.clickOnHamburgerMenu();
        mainSteps.clickOnAbout();
        aboutSteps.backButtonVisibility();
    }

    @Test
    @DisplayName("Навигация к странице Our Mission с приветствующей страницы 'News'")
    public void shouldGoToOurMissionPageFromNewsPage() {
        mainSteps.openPageWithQuotes();
        quotesSteps.visibilityTitleLoveIsAll();
    }

    @Test
    @DisplayName("Выход из учетной записи с приветствующей страницы 'News'")
    public void shouldLogOutOfAccountOnTheNewsPage() {
        authPage.clickOnProfileImage();
        authPage.clickOnLogout();
        authPage.assertLoginPage();
    }

    @Test
    @DisplayName("Ввод поочередно каждой категории в фильтре новостей")
    public void theFieldShouldAcceptAllNewsCategories() {
        newsSteps.openNewsFilter();
        editorialSteps.verifySelectedCategories();
    }

    @Test
    @DisplayName("Фильтрация новостей по диапазону дат")
    public void shouldFilterNewsWithinDateRange() {
        newsSteps.openNewsFilter();
        newsSteps.enterFromWhatDate(-7); // Дней назад
        newsSteps.enterUntilWhatDate(7); // Дней вперед
        newsSteps.clickOnApplyFilterButton();
        newsSteps.checkAllNewsDateRange(-7, 7);
    }

    @Test
    @DisplayName("Отмена применения фильтра новостей")
    public void shouldCancelNewsFilterApplication() {
        newsSteps.openNewsFilter();
        editorialSteps.pressCancel();
        newsSteps.verifyTextAfterCancelingFilter(Data.NEWS_TEXT);
    }
}
