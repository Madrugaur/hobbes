package com.bradenlittle.hobbes.util;


import net.dv8tion.jda.api.entities.Member;

import java.sql.*;
import java.util.ArrayList;

public class SQL {
    public static int INVALID_DATE_TIME_FORMAT = 1;
    public static int GOOD_ENTRY = 0;
    private Connection connection;
    private String table_name;

    public SQL(String connection_string, String table_name) {
        this.table_name = table_name;
        this.connection = IO.getSQLConnection(
                InformationBucket.fromPackage("sql.url")
                        .replace("{resource_dir}", IO.getResourceDir()));
    }

    public boolean execute(String command) {
        try {
            return execute(connection.prepareStatement(command));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public boolean execute(PreparedStatement statement) {
        try {
            statement.execute();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public ResultSet query(String cmd, Object[] args) {
        cmd = String.format(cmd, args);
        try {
            return query(connection.prepareStatement(cmd));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    public ResultSet query(PreparedStatement statement) {
        try {
            return statement.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    private Statement blankStatement() throws SQLException {
        return connection.createStatement();
    }

}
