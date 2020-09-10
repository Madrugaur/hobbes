package com.bradenlittle.hobbes.util;

import net.dv8tion.jda.api.entities.Member;

public class Remember {
    public static boolean remember(Member member, String[] args){
        String event = args[0];
        String date = args[1];
        switch (event) {
            case "birthday":
                return birthday(member, date);
            default:
                return false;
        }
    }
    private static boolean birthday(Member member, String date){
        return SQL.addEntry(member, date) == SQL.GOOD_ENTRY;
    }

}
