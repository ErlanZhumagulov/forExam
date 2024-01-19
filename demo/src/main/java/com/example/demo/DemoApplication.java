package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
		String jdbcUrl = "jdbc:sqlserver://mssql-server:1433;databaseName=master;user=sa;password=1234Eg4321;encrypt=false";


		try (Connection connection = DriverManager.getConnection(jdbcUrl);
			 Statement statement = connection.createStatement()) {

			String databaseName = "Java_dock";
			String sql = "CREATE DATABASE " + databaseName;

			statement.executeUpdate(sql);
			System.out.println("Database '" + databaseName + "' created successfully.");

			System.out.println("НЕ ХУЙ");

		} catch (SQLException e) {
			System.out.println("ХУЙ ");
			e.printStackTrace();
		}


        SpringApplication.run(DemoApplication.class, args);
    }

}






