package com.bradenlittle.hobbes.util;


import net.dv8tion.jda.api.entities.Member;
import java.sql.*;
import java.util.ArrayList;

public class SQL {
    public static int INVALID_DATE_TIME_FORMAT = 1;
    public static int GOOD_ENTRY = 0;
    private static Connection connection;
    private static String table_name = "user_birth_dates";
    static {
        try {
            init();
        } catch (SQLException throwables) {
           throwables.printStackTrace();
       }
   }
   public static void init() throws SQLException {
       connection = IO.getSQLConnection();
       execute(String.format("CREATE TABLE IF NOT EXISTS user_birth_dates " +
                             "(id INTEGER PRIMARY KEY, " +
                             "discord_username TEXT, " +
                             "discord_id TEXT NOT NULL, " +
                             "discord_nickname TEXT, " +
                             "birthdate TEXT);",
                             table_name
                            ));
   }
   private static boolean execute(String cmd) throws SQLException {
       return blankStatement().execute(cmd);
   }
   private static boolean execute(PreparedStatement statement) throws SQLException {
        return statement.execute();
   }
   private static ResultSet query(String cmd, Object[] args) throws SQLException {
       cmd = String.format(cmd, args);
       return blankStatement().executeQuery(cmd);
   }
   private static ResultSet query(PreparedStatement statement) throws SQLException {
        return statement.executeQuery();
   }
   private static Statement blankStatement() throws SQLException {
       return connection.createStatement();
   }
   public static String[] getDiscordIdByBirthdate(String birthdate) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT discord_id FROM " + table_name + " WHERE birthdate = ?");
        statement.setString(1, birthdate);
        ResultSet rs = query(statement);
        ArrayList<String> list = new ArrayList<String>();
        while (rs.next()){
            list.add(rs.getString(1));
        }
        return list.toArray(new String[] {});
   }
   public static int addEntry(Member member, String bday){
        if (Date.valueOf(bday) == null) return INVALID_DATE_TIME_FORMAT;
        bday = StringUtil.construct(bday.split("-"), 1, '-');
        bday = bday.replace("0", "");
        String cmd = "SELECT COUNT(*) FROM user_birth_dates WHERE discord_id = %s";
       try {
           ResultSet rs = query(cmd, new String[] {member.getId()});
          if (rs.getInt(1) > 0){
              String update = "UPDATE " + table_name + " SET birthdate = ? WHERE discord_id = ?";
              PreparedStatement statement = connection.prepareStatement(update);
              String[] args = {bday, member.getId()};
              for (int i = 0; i < args.length; i++){
                  statement.setString(i + 1, args[i]);
              }
              execute(statement);
          }else {
              String add = "INSERT INTO " + table_name + " (discord_username, discord_id, discord_nickname, birthdate) VALUES (?, ?, ? ,?)";
              PreparedStatement statement = connection.prepareStatement(add);
              String[] args = {member.getUser().getName(), member.getId(), member.getNickname(), bday};
              for (int i = 0; i < args.length; i++){
                  statement.setString(i + 1, args[i]);
              }
              execute(statement);
          }
       } catch (SQLException throwables) {
           throwables.printStackTrace();
       }
       return 0;
   }
}
