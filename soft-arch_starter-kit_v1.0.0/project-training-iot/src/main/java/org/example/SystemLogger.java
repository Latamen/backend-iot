package org.example;

import java.util.ArrayList;
import java.util.List;

public class SystemLogger {
    private final List<String> logs = new ArrayList<>();

    public void addLog(String message) {
        logs.add(message);
        System.out.println(message);
    }

}
