package ru.netology.qa.test;

import static org.junit.Assert.assertEquals;
import static ru.netology.qa.data.Helper.generateRandomThreeDigitString;
import static ru.netology.qa.steps.NewsSteps.STATUS_ACTIVE;
import static ru.netology.qa.steps.NewsSteps.STATUS_NOT_ACTIVE;

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
@Epic(value = "Поиск новостей на странице управления «News»")
public class NewsSearchTest {

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
        newsSteps.openNewsManagementPage();
    }

    String randomTitle = Data.NEWS_TITLE_TEXT + generateRandomThreeDigitString();

    @Test
    @DisplayName("Навигация к странице Main со страницы управления новостями")
    public void shouldGoToMainPageFromNewsManagementPage() {
        mainSteps.clickOnHamburgerMenu();
        mainSteps.clickOnMain();
        mainSteps.theAllNewsItemIsDisplayed();
    }

    @Test
    @DisplayName("Навигация к  приветствующей странице News со страницы управления новостями")
    public void shouldGoToNewsPageFromNewsManagementPage() {
        mainSteps.clickOnHamburgerMenu();
        mainSteps.clickOnNews();
        newsSteps.showNewsManagementButton();
    }

    @Test
    @DisplayName("Навигация к странице About со страницы управления новостями")
    public void shouldGoToAboutPageFromNewsManagementPage() {
        mainSteps.clickOnHamburgerMenu();
        mainSteps.clickOnAbout();
        aboutSteps.backButtonVisibility();
    }

    @Test
    @DisplayName("Навигация к странице Our Mission со страницы управления новостями")
    public void shouldGoToOurMissionPageFromNewsManagementPage() {
        mainSteps.openPageWithQuotes();
        quotesSteps.visibilityTitleLoveIsAll();
    }

    @Test
    @DisplayName("Выход из учетной записи со страницы управления новостями")
    public void shouldLogOutOfAccountOnTheNewsManagementPage() {
        authPage.clickOnProfileImage();
        authPage.clickOnLogout();
        authPage.assertLoginPage();
    }

    @Test
    @DisplayName("Ввод поочередно каждой категории в фильтре новостей")
    public void enterAllCategoriesInTheFilterOneByOne() {
        newsSteps.openNewsFilter();
        editorialSteps.verifySelectedCategories();
    }

    @Test
    @DisplayName("Проверить сортировку даты новостей")
    public void verifyNewsDateSorting() {
        String firstDateBeforeSorting = newsSteps.getFirstNewsDate();
        String lastDateBeforeSorting = newsSteps.getLastNewsDate();
        newsSteps.clickOnSortingNews();
        String firstDateAfterSorting = newsSteps.getFirstNewsDate();
        String lastDateAfterSorting = newsSteps.getLastNewsDate();
        assertEquals(lastDateBeforeSorting, firstDateAfterSorting);
        assertEquals(firstDateBeforeSorting, lastDateAfterSorting);
    }

    @Test
    @DisplayName("Фильтрация всех новостей по диапазону дат")
    public void shouldFilterNewsByDateRange() {
        newsSteps.openNewsFilter();
        newsSteps.enterFromWhatDate(-14); // Дней назад
        newsSteps.enterUntilWhatDate(14); // Дней вперед
        newsSteps.clickOnApplyFilterButton();
        newsSteps.checkAllNewsDateRange(-14, 14);
    }

    @Test
    @DisplayName("Поиск новости через фильтр по дате")
    public void shouldSearchForNewsViaFilterForCurrentDate() {
        editorialSteps.createNews(Data.SALARY_CATEGORY, randomTitle, 30, "20:00", Data.DESCRIPTION_TEXT);
        newsSteps.openNewsFilter();
        editorialSteps.enterCategoryNews(Data.SALARY_CATEGORY);
        newsSteps.enterFromWhatDate(30);
        newsSteps.enterUntilWhatDate(30);
        newsSteps.clickOnApplyFilterButton();
        editorialSteps.scrollingThroughTheNewsFeed(randomTitle);
        editorialSteps.checkSearchResultIsDisplayed(randomTitle);
    }

    @Test
    @DisplayName("Фильтрация всех новостей по статусу 'ACTIVE'")
    public void checkAllNewsAreActive() {
        newsSteps.openNewsFilter();
        newsSteps.clickOnCheckBoxNotActive();
        newsSteps.clickOnApplyFilterButton();
        newsSteps.checkAllNewsStatus(STATUS_ACTIVE);
    }

    @Test
    @DisplayName("Фильтрация всех новостей по статусу 'NOT ACTIVE'")
    public void checkAllNewsAreNotActive() {
        newsSteps.openNewsFilter();
        newsSteps.clickOnCheckBoxActive();
        newsSteps.clickOnApplyFilterButton();
        newsSteps.checkAllNewsStatus(STATUS_NOT_ACTIVE);
    }

    @Test
    @DisplayName("Поиск через фильтр новости со статусом NOT ACTIVE")
    public void shouldFilterNewsByStatusNotActive() {
        editorialSteps.theStatusOfTheEditedNewsIsNotActive(randomTitle);
        editorialSteps.scrollingThroughTheNewsFeed(randomTitle);
        newsSteps.openNewsFilter();
        newsSteps.clickOnCheckBoxActive();
        newsSteps.clickOnApplyFilterButton();
        editorialSteps.scrollingThroughTheNewsFeed(randomTitle);
        editorialSteps.checkStatusOfEditedNews(randomTitle, STATUS_NOT_ACTIVE);
    }

    @Test
    @DisplayName("Поиск через фильтр новости со статусом ACTIVE")
    public void shouldFilterNewsByStatusActive() {
        editorialSteps.createNews(Data.MASSAGE_CATEGORY, randomTitle, 0, "20:00", Data.DESCRIPTION_TEXT);
        newsSteps.openNewsFilter();
        newsSteps.clickOnCheckBoxNotActive();
        newsSteps.clickOnApplyFilterButton();
        editorialSteps.scrollingThroughTheNewsFeed(randomTitle);
        editorialSteps.checkStatusOfEditedNews(randomTitle, STATUS_ACTIVE);
    }

    @Test
    @DisplayName("Отмена применения фильтра новостей")
    public void shouldAllowToCancelNewsFilterApplication() {
        newsSteps.openNewsFilter();
        editorialSteps.pressCancel();
        newsSteps.verifyTextAfterCancelingFilter(Data.CONTROL_PANEL_TEXT);
    }
}
