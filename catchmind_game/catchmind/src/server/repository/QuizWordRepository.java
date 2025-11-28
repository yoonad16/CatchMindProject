package server.repository;

import server.domain.QuizWord;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class QuizWordRepository {
   
	public String getRandomWord() {
	    String wordData = null;

	    try (Connection conn = DBConnector.connectDB()) {

	        int rowCount = 0;
	        String countSql = "SELECT COUNT(*) FROM catchmind_words";

	        try (PreparedStatement pstmt = conn.prepareStatement(countSql);
	             ResultSet rs = pstmt.executeQuery()) {

	            if (rs.next()) {
	                rowCount = rs.getInt(1);
	            }
	        }

	        if (rowCount == 0) {
	            return null;
	        }

	        int randomIndex = new Random().nextInt(rowCount);

	        String sql = "SELECT name FROM catchmind_words LIMIT 1 OFFSET ?";

	        try (PreparedStatement pstmt2 = conn.prepareStatement(sql)) {
	            pstmt2.setInt(1, randomIndex);

	            try (ResultSet rs2 = pstmt2.executeQuery()) {
	                if (rs2.next()) {
	                    wordData = rs2.getString("name");
	                }
	            }
	        }

	    } catch (SQLException e) {
	        System.out.println("단어 가져오기 실패");
	        e.printStackTrace();
	    }

	    return wordData;
	}
}