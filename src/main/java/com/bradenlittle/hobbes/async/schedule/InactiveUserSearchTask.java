package com.bradenlittle.hobbes.async.schedule;

import com.bradenlittle.hobbes.async.thread.SQLTask;
import com.bradenlittle.hobbes.async.thread.SQLTaskExecutor;
import com.bradenlittle.hobbes.util.Clock;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

public class InactiveUserSearchTask extends TimerTask {

    @Override
    public void run() {
        SQLTask search = new SQLTask();
        search.addStatement("SELECT user_id FROM messages WHERE date < %s", new Object[] { new Date().getTime() - Clock.getSeconds(0) });
        try {
            ResultSet results = SQLTaskExecutor.query(search).get();
            while (results.next()){
                System.out.println(results.getLong(1));
            }
        } catch (InterruptedException | SQLException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

}
