package ru.netology.web;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class OrderCardTest {

    public String getToDate(int day) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        Date newDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(newDate);
        cal.add(Calendar.DATE, day);
        Date date = cal.getTime();
        return formatter.format(date);
    }

    @Test
    void shouldRegisterByOrderCard() {
        String day = getToDate(3);
        open("http://localhost:9999");
        $("[data-test-id='city'] .input__control").setValue("Москва");
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id='date'] .input__control").setValue(day);
        $("[data-test-id='name'] .input__control").setValue("Николай Иванов-Петров");
        $("[data-test-id='phone'] .input__control").setValue("+79299942766");
        $("[data-test-id='agreement']").click();
        $(withText("Забронировать")).click();
        $("[data-test-id='notification'] .notification__title").shouldHave(exactText("Успешно!"), Duration.ofSeconds(15));
    }

    @Test
    void shouldRegisterNoData() {
        $(withText("Забронировать")).click();
        $("[data-test-id='city'] .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldRegisterBadDate() {
        String day = getToDate(0);
        open("http://localhost:9999");
        $("[data-test-id='city'] .input__control").setValue("Москва");
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id='date'] .input__control").setValue(day);
        $("[data-test-id='name'] .input__control").setValue("Николай");
        $("[data-test-id='phone'] .input__control").setValue("+79299942766");
        $("[data-test-id='agreement']").click();
        $(withText("Забронировать")).click();
        $("[data-test-id='date'] .input__sub").shouldHave(exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    void shouldRegisterNoDate() {
        open("http://localhost:9999");
        $("[data-test-id='city'] .input__control").setValue("Москва");
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id='name'] .input__control").setValue("Николай");
        $("[data-test-id='phone'] .input__control").setValue("+79299942766");
        $("[data-test-id='agreement']").click();
        $(withText("Забронировать")).click();
        $("[data-test-id='date'] .input__sub").shouldHave(exactText("Неверно введена дата"));
    }

    @Test
    void shouldRegisterNoCity() {
        String day = getToDate(3);
        open("http://localhost:9999");
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id='date'] .input__control").setValue(day);
        $("[data-test-id='name'] .input__control").setValue("Николай");
        $("[data-test-id='phone'] .input__control").setValue("+79299942766");
        $("[data-test-id='agreement']").click();
        $(withText("Забронировать")).click();
        $("[data-test-id='city'] .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldRegisterBadCity() {
        String day = getToDate(3);
        open("http://localhost:9999");
        $("[data-test-id='city'] .input__control").setValue("Osaka");
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id='date'] .input__control").setValue(day);
        $("[data-test-id='name'] .input__control").setValue("Николай");
        $("[data-test-id='phone'] .input__control").setValue("+79299942766");
        $("[data-test-id='agreement']").click();
        $(withText("Забронировать")).click();
        $("[data-test-id='city'] .input__sub").shouldHave(exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldRegisterBadName() {
        String day = getToDate(3);
        open("http://localhost:9999");
        $("[data-test-id='city'] .input__control").setValue("Москва");
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id='date'] .input__control").setValue(day);
        $("[data-test-id='name'] .input__control").setValue("Nikolay");
        $("[data-test-id='phone'] .input__control").setValue("+79299942766");
        $("[data-test-id='agreement']").click();
        $(withText("Забронировать")).click();
        $("[data-test-id='name'] .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldRegisterNoName() {
        String day = getToDate(3);
        open("http://localhost:9999");
        $("[data-test-id='city'] .input__control").setValue("Москва");
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id='date'] .input__control").setValue(day);
        $("[data-test-id='phone'] .input__control").setValue("+79299942766");
        $("[data-test-id='agreement']").click();
        $(withText("Забронировать")).click();
        $("[data-test-id='name'] .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldRegisterBadPhone() {
        String day = getToDate(3);
        open("http://localhost:9999");
        $("[data-test-id='city'] .input__control").setValue("Москва");
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id='date'] .input__control").setValue(day);
        $("[data-test-id='name'] .input__control").setValue("Николай Иванов-Петров");
        $("[data-test-id='phone'] .input__control").setValue("89299942766");
        $("[data-test-id='agreement']").click();
        $(withText("Забронировать")).click();
        $("[data-test-id='phone'] .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldRegisterNoPhone() {
        String day = getToDate(3);
        open("http://localhost:9999");
        $("[data-test-id='city'] .input__control").setValue("Москва");
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id='date'] .input__control").setValue(day);
        $("[data-test-id='name'] .input__control").setValue("Николай Иванов-Петров");
        $("[data-test-id='agreement']").click();
        $(withText("Забронировать")).click();
        $("[data-test-id='phone'] .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldRegisterNoCheck() {
        String day = getToDate(3);
        open("http://localhost:9999");
        $("[data-test-id='city'] .input__control").setValue("Москва");
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id='date'] .input__control").setValue(day);
        $("[data-test-id='name'] .input__control").setValue("Николай Иванов-Петров");
        $("[data-test-id='phone'] .input__control").setValue("+79299942766");
        $(withText("Забронировать")).click();
        $("[data-test-id='agreement'] .checkbox__text").shouldHave(exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }

}

