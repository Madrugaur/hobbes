package com.bradenlittle.hobbes.async.thread;

import com.bradenlittle.hobbes.util.IO;
import com.bradenlittle.hobbes.util.InformationBucket;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

public class SQLTask {
    private ArrayList<String> statements;
    private ArrayList<Object[]> arguments;
    private boolean query;
    public SQLTask(){
        statements = new ArrayList<>();
        arguments = new ArrayList<>();
        query = false;
    }
    public void addStatement(String command, Object[] args){
        statements.add(command);
        arguments.add(args);
    }
    public void isQuery(boolean isQuery){
        this.query = isQuery;
    }
    public boolean isQuery(){
        return query;
    }
    public ArrayList<String> getStatements() {
        return statements;
    }
    public ArrayList<Object[]> getArguments() {
        return arguments;
    }
    public int size() { return statements.size(); }
}
