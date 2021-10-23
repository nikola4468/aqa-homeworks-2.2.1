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
        $("[data-test-id='name'] .input__control").setValue("Николай");
        $("[data-test-id='phone'] .input__control").setValue("+79299942766");
        $("[data-test-id='agreement']").click();
        $(withText("Забронировать")).click();
        $("[data-test-id='notification'] .notification__title").shouldHave(exactText("Успешно!"), Duration.ofSeconds(15));
    }
}

