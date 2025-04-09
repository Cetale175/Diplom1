package ru.netology.qa.test;

import static ru.netology.qa.data.Helper.generateRandomThreeDigitString;
import static ru.netology.qa.data.Helper.getCurrentTime;
import static ru.netology.qa.data.Helper.randomCategory;
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
import ru.netology.qa.steps.AuthorizationSteps;
import ru.netology.qa.steps.EditorialSteps;
import ru.netology.qa.steps.MainSteps;
import ru.netology.qa.steps.NewsSteps;


@RunWith(AllureAndroidJUnit4.class)
@Epic(value = "Страница «News» создание, редактирование, удаление новости")
public class EditorialTest {

    @Rule
    public ActivityScenarioRule<AppActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);

    AuthorizationSteps authPage = new AuthorizationSteps();
    EditorialSteps editorialSteps = new EditorialSteps();
    NewsSteps newsSteps = new NewsSteps();
    MainSteps mainSteps = new MainSteps();
    String randomTitle = Data.NEWS_TITLE_TEXT + generateRandomThreeDigitString();
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

    @Test
    @DisplayName("Ввод поочередно каждой категории при создании новости")
    public void theFieldShouldAcceptAllNewsCategories() {
        editorialSteps.openPageAddingNews();
        editorialSteps.verifySelectedCategories();
    }

    @Test
    @DisplayName("Создание новости")
    public void shouldCreateNews() {
        editorialSteps.openPageAddingNews();
        editorialSteps.enterCategoryNews(randomCategory());
        editorialSteps.enterTitleNews(randomTitle);
        editorialSteps.setDate(0); // Опубликовать через кол-во дней
        editorialSteps.setTime(getCurrentTime());
        editorialSteps.enterNewsDescription("Текст описания");
        editorialSteps.clickOnSave();
        editorialSteps.scrollingThroughTheNewsFeed(randomTitle);
        editorialSteps.checkSearchResultIsDisplayed(randomTitle);
    }

    @Test
    @DisplayName("Не должен создавать новость с незаполненными полями")
    public void shouldNotCreateNewsWithEmptyFields() {
        editorialSteps.openPageAddingNews();
        editorialSteps.clickOnSave();
        editorialSteps.checkErrorMessage();
    }

    @Test
    @DisplayName("Возвращение к созданию новости с сохранением данных")
    public void shouldReturnToNewsCreation() {
        editorialSteps.openPageAddingNews();
        editorialSteps.enterCategoryNews(Data.BIRTHDAY_CATEGORY);
        editorialSteps.pressCancel();
        editorialSteps.pressCancelAlertDialog();
        editorialSteps.checkSearchResultIsDisplayed(Data.BIRTHDAY_CATEGORY);
    }

    @Test
    @DisplayName("Отмена публикации новости")
    public void cancelNewsPublication() {
        editorialSteps.openPageAddingNews();
        editorialSteps.pressCancel();
        editorialSteps.pressOkAlertDialog();
        editorialSteps.checkingAddNewsButton();
    }

    @Test
    @DisplayName("Удаление созданной новости")
    public void deletingCreatedNews() {
        editorialSteps.createNews(Data.MASSAGE_CATEGORY, randomTitle, 1, getCurrentTime(), Data.DESCRIPTION_TEXT);
        editorialSteps.scrollingThroughTheNewsFeed(randomTitle);
        editorialSteps.clickOnDeletingNews(randomTitle);
        editorialSteps.pressOkAlertDialog();
        editorialSteps.checkingTheResultOfDeletingNews(randomTitle);
    }

    @Test
    @DisplayName("Отмена удаления новости")
    public void shouldCancelDeletionOfNews() {
        editorialSteps.createNews(randomCategory(), randomTitle, 8, getCurrentTime(), Data.DESCRIPTION_TEXT);
        editorialSteps.scrollingThroughTheNewsFeed(randomTitle);
        editorialSteps.clickOnDeletingNews(randomTitle);
        editorialSteps.pressCancelAlertDialog();
        editorialSteps.checkingAddNewsButton();
    }

    @Test
    @DisplayName("Ввод поочередно каждой категории при редактировании новости")
    public void enterEachCategoryInTurn() {
        editorialSteps.createNews(randomCategory(), randomTitle, 3, getCurrentTime(), Data.DESCRIPTION_TEXT);
        editorialSteps.scrollingThroughTheNewsFeed(randomTitle);
        editorialSteps.openNewsEditor(randomTitle);
        editorialSteps.verifySelectedCategories();
    }

    @Test
    @DisplayName("Статус отредактированной новости должен быть ACTIVE")
    public void editedNewsStatusShouldBeActive() {
        editorialSteps.createNews(Data.SALARY_CATEGORY, randomTitle, 2, getCurrentTime(), Data.DESCRIPTION_TEXT);
        editorialSteps.scrollingThroughTheNewsFeed(randomTitle);
        editorialSteps.openNewsEditor(randomTitle);
        editorialSteps.clickOnSave();
        editorialSteps.scrollingThroughTheNewsFeed(randomTitle);
        editorialSteps.checkStatusOfEditedNews(randomTitle, STATUS_ACTIVE);
    }

    @Test
    @DisplayName("Статус отредактированной новости должен быть NOT ACTIVE")
    public void editedNewsStatusShouldBeNotActive() {
        editorialSteps.createNews(Data.ANNOUNCEMENT_CATEGORY, randomTitle, 3, getCurrentTime(), Data.DESCRIPTION_TEXT);
        editorialSteps.scrollingThroughTheNewsFeed(randomTitle);
        editorialSteps.openNewsEditor(randomTitle);
        editorialSteps.activityToggle();
        editorialSteps.clickOnSave();
        editorialSteps.scrollingThroughTheNewsFeed(randomTitle);
        editorialSteps.checkStatusOfEditedNews(randomTitle, STATUS_NOT_ACTIVE);
    }

    @Test
    @DisplayName("Возвращение к редактированию новости")
    public void shouldGoBackToEditingTheNews() {
        editorialSteps.createNews(Data.TRADE_UNION_CATEGORY, randomTitle, 4, getCurrentTime(), Data.DESCRIPTION_TEXT);
        editorialSteps.scrollingThroughTheNewsFeed(randomTitle);
        editorialSteps.openNewsEditor(randomTitle);
        editorialSteps.pressCancel();
        editorialSteps.pressCancelAlertDialog();
        editorialSteps.checkSearchResultIsDisplayed(Data.TRADE_UNION_CATEGORY);
    }

    @Test
    @DisplayName("Отмена редактирования новости")
    public void cancelEditingNews() {
        editorialSteps.createNews(Data.NEED_HELP_CATEGORY, randomTitle, 0, getCurrentTime(), Data.DESCRIPTION_TEXT);
        editorialSteps.scrollingThroughTheNewsFeed(randomTitle);
        editorialSteps.openNewsEditor(randomTitle);
        editorialSteps.pressCancel();
        editorialSteps.pressOkAlertDialog();
        editorialSteps.checkingAddNewsButton();
    }
}
