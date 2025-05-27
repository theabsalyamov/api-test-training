package cinescope.api.utils;

import java.sql.*;


public class DBUtils {

    private static final String DB_URL = "jdbc:postgresql://147.45.143.178:31200/db_movies";
    private static final String DB_USER = "test";     // укажи свой
    private static final String DB_PASSWORD = "test"; // укажи свой

    public static String getUserIdByEmail(String email) {
        String userId = null;
        String query = "SELECT id FROM public.users WHERE email = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                userId = rs.getString("id");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userId;
    }

}
