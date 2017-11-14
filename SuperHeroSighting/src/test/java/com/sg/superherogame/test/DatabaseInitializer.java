/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherogame.test;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author jeffolupo
 */
public class DatabaseInitializer {
    
public static void setKnownGoodState() {

        String url = "jdbc:mysql://localhost:3306/SuperHeroTest";

        Properties props = new Properties();
        props.put("user", "root");
        props.put("password", "root");
        props.put("serverTimezone", "UTC");

        List<String> statements = getStatements();

        try (Connection conn = DriverManager.getConnection(url, props)) {

            Statement command = conn.createStatement();
            for (String sql : statements) {
                if (sql.trim().length() > 0) {
                    command.execute(sql);
                }
            }

        } catch (SQLException ex) {
            throw new RuntimeException("It broke really bad", ex);
        }
    }

    private static List<String> getStatements() {

        URL scriptUrl = ClassLoader.getSystemResource("known-state.sql");

        List<String> statements = null;
        try {
            statements = Files.readAllLines(Paths.get(scriptUrl.getPath()));
        } catch (IOException ex) {
            throw new RuntimeException("It broke", ex);
        }

        return statements;
    }
}