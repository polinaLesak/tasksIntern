import com.opencsv.CSVReader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CsvToDatabase {
    static String url = "jdbc:mysql://localhost:3306/task";
    static String username = "root";
    static String password = "12345678;";

    public static void LoginsToDB() {

        String csvFilePath = "D://универ//2.2//ks//lambda//logins.csv";
        String insertQuery = "INSERT INTO user (Application, AppAccountName, IsActive, JobTitle, Department) " + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, username, password); CSVReader reader = new CSVReader(new FileReader(csvFilePath))) {

            String[] line;
            boolean firstLineSkipped = false;
            PreparedStatement statement = conn.prepareStatement(insertQuery);
            while ((line = reader.readNext()) != null) {
                if (!firstLineSkipped) {
                    firstLineSkipped = true;
                    continue;
                }

                statement.setString(1, line[0]);
                statement.setString(2, line[1]);
                statement.setString(3, line[2]);
                statement.setString(4, line[3]);
                statement.setString(5, line[4]);
                statement.executeUpdate();

            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void ProductsToDB() {

        String csvFilePath = "D://универ//2.2//ks//lambda//postings.csv";
        String insertQuery = "INSERT INTO products (item, MaterialDescription, Quantity, BUn, AmountLC, Crcy, MatDoc) " + "VALUES (?, ?, ?, ?, ?, ?,?)";

        try (Connection conn = DriverManager.getConnection(url, username, password); BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {


            String lines;
            boolean firstLineSkipped = false;
            boolean secondLineSkipped = false;
            PreparedStatement statement = conn.prepareStatement(insertQuery);
            while ((lines = br.readLine()) != null) {

                String[] line = lines.split(";\t");
                if (!firstLineSkipped) {
                    firstLineSkipped = true;
                    continue;
                }
                if (!secondLineSkipped) {
                    secondLineSkipped = true;
                    continue;
                }

                statement.setInt(1, Integer.parseInt(line[1]));
                statement.setString(2, line[4]);
                statement.setInt(3, Integer.parseInt(line[5]));
                statement.setString(4, line[6]);
                statement.setString(5, line[7]);
                statement.setString(6, line[8]);
                statement.setLong(7, Long.parseLong(line[0]));
                statement.executeUpdate();

            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void DeliveriesToDB() {

        String csvFilePath = "D://универ//2.2//ks//lambda//postings.csv";
        String insertQuery = "INSERT INTO deliveries (MatDoc, DocData, PstngData, UserName, Authorised) " + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, username, password); BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {


            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            String lines;
            boolean firstLineSkipped = false;
            boolean secondLineSkipped = false;
            PreparedStatement statement = conn.prepareStatement(insertQuery);
            while ((lines = br.readLine()) != null) {

                String[] line = lines.split(";\t");
                if (!firstLineSkipped) {
                    firstLineSkipped = true;
                    continue;
                }
                if (!secondLineSkipped) {
                    secondLineSkipped = true;
                    continue;
                }


                LocalDate date = LocalDate.parse(line[2], dateFormatter);
                java.sql.Date sqlDate1 = java.sql.Date.valueOf(date);
                LocalDate date2 = LocalDate.parse(line[3], dateFormatter);
                java.sql.Date sqlDate2 = java.sql.Date.valueOf(date2);


                statement.setLong(1, Long.parseLong(line[0]));
                statement.setDate(2, sqlDate1);
                statement.setDate(3, sqlDate2);
                statement.setString(4, line[9]);
                statement.setString(5, "FALSE");
//
                statement.executeUpdate();


            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // устанавливаем соединение с базой данных
            conn = DriverManager.getConnection(url, username, password);
            stmt = conn.createStatement();

            // получаем список AppAccountName и IsActive из таблицы users
            String sql1 = "SELECT AppAccountName, IsActive FROM user";
            rs = stmt.executeQuery(sql1);
            String appAccountName = null;
            String isActive = null;
            while (rs.next()) {
                appAccountName = rs.getString("AppAccountName");
                isActive = rs.getString("IsActive");
                //System.out.println(appAccountName+" | "+isActive);


                // обновляем поле authorized в таблице deliveries, если User Name находится в списке AppAccountName и IsActive
                if (appAccountName != null || isActive == "True") {
                    System.out.println("uuuu");
                    String sql2 = "UPDATE deliveries SET Authorised = 'TRUE' WHERE `UserName` = '" + appAccountName + "'";
                    System.out.println("uuuu");
                    stmt.executeUpdate(sql2);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            // закрываем соединение с базой данных и освобождаем ресурсы
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
