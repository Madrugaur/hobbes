package com.bradenlittle.hobbes.schedule;

import com.bradenlittle.hobbes.util.SQL;

import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.TimerTask;

public class BirthdayPingTask  extends TimerTask {
    @Override
    public void run() {
        LocalDate date = LocalDate.now();
        String date_stamp = String.valueOf(date.getMonthValue()) + "-" + String.valueOf(date.getDayOfMonth());
        System.out.println(date_stamp);
        try {
            String[] ids = SQL.getDiscordIdByBirthdate(date_stamp);
            for (String id : ids){
                System.out.println(id);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
