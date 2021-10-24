package ru.netology.web;

import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;


public class OrderCardTest3 {

    public String getToDate(int day) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        Date newDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(newDate);
        cal.add(Calendar.DATE, day);
        Date date = cal.getTime();
        return formatter.format(date);
    }

    public int getToDay(int day) {
        Date newDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(newDate);
        cal.add(Calendar.DATE, day);
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    @Test
    void shouldRegisterByOrderCardCalendar() {
        int term = 7;
        int nextDay = getToDay(term);
        int today = getToDay(0);
        String expectationDay = getToDate(7);
        String nextDayString = Integer.toString(nextDay);

        open("http://localhost:9999");
        $("[data-test-id='city'] .input__control").setValue("Москва");

        if (today > nextDay) {
            $(".input__icon [type='button']").click();
            $("[data-step='1']").click();
        } else {
            $(".input__icon").click();
        }
        $$("[data-day]").find(exactText(nextDayString)).click();
        $("[data-test-id='date'] .input__control").shouldHave(value(expectationDay));

        $("[data-test-id='name'] .input__control").setValue("Николай Иванов-Петров");
        $("[data-test-id='phone'] .input__control").setValue("+79299942766");
        $("[data-test-id='agreement']").click();
        $(withText("Забронировать")).click();
        $("[data-test-id='notification'] .notification__title").shouldHave(exactText("Успешно!"), Duration.ofSeconds(15));
    }
}
