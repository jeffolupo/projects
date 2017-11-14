package com.sg.btpblog.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class DatabaseInitializer {

    public static void setKnownGoodState() {

        String url = "jdbc:mysql://localhost:3306/SgBlogCms_test";

        Properties properties = new Properties();
        properties.put("user", "root");
        properties.put("password", "root");
        properties.put("serverTimezone", "UTC");

        List<String> statements = getStatements();

        try (Connection connection
                = DriverManager.getConnection(url, properties)) {
            Statement command = connection.createStatement();
            for (String sql : statements) {
                if (sql.trim().length() > 0) {
                    command.execute(sql);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error: " + e.getMessage(), e);
        }
    }

    private static List<String> getStatements() {

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        ClassLoader.getSystemResourceAsStream("known-good-state.sql")
                )
        );

        List<String> statements = reader.lines().collect(Collectors.toList());
        try {
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException("Error: " + e.getMessage(), e);
        }
        return statements;
    }
}
