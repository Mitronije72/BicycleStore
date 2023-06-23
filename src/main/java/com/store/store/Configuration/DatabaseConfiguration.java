package com.store.store.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import javax.sql.DataSource;

@Component
public class DatabaseConfiguration {
    @Autowired
    private DataSource dataSource;

    @PostConstruct
    public void initializeData() {
        // Read the contents of import.sql file
        try {
            Path path = Paths.get("src/main/resources/imports.sql");
            String sql = new String(Files.readAllBytes(path));

            // Execute the SQL statements
            // Replace 'yourDataSource' with your actual data source bean name or autowire it
            dataSource.getConnection().createStatement().execute(sql);
        } catch (IOException | SQLException e) {
            // Handle any exceptions
            e.printStackTrace();
        }
    }
}
