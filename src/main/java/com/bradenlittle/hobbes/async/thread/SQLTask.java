package com.bradenlittle.hobbes.async.thread;

import java.util.ArrayList;

public class SQLTask {
    private final ArrayList<String> statements;
    private final ArrayList<Object[]> arguments;
    private boolean query;

    public SQLTask() {
        statements = new ArrayList<>();
        arguments = new ArrayList<>();
        query = false;
    }

    public void addStatement(String command, Object... args) {
        statements.add(command);
        arguments.add(args);
    }

    public void isQuery(boolean isQuery) {
        this.query = isQuery;
    }

    public boolean isQuery() {
        return query;
    }

    public ArrayList<String> getStatements() {
        return statements;
    }

    public ArrayList<Object[]> getArguments() {
        return arguments;
    }

    public int size() {
        return statements.size();
    }
}
