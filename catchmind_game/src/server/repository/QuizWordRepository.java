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
    /*private final List<String> wordList = Arrays.asList(
            "사과", "바나나", "자동차", "비행기", "컴퓨터",
            "축구", "피아노", "고양이", "강아지", "아이스크림",
            "우산", "시계", "안경", "모자", "자전거"
    );*/
	
	public String getRandomWord() {

	    String wordData = null;

	    try (Connection conn = DBConnector.connectDB()) {
	    	
	        int columnCount = 0;
	        String metaSql = "SELECT * FROM catchmind_words LIMIT 1";

	        try (PreparedStatement pstmt = conn.prepareStatement(metaSql);
	             ResultSet rs = pstmt.executeQuery()) {

	            ResultSetMetaData meta = rs.getMetaData();
	            columnCount = meta.getColumnCount();
	        }

	        int randomIndex = new Random().nextInt(columnCount);
	        String columnName = "column" + randomIndex;

	        String sql = "SELECT " + columnName + " FROM catchmind_words";

	        try (PreparedStatement pstmt2 = conn.prepareStatement(sql);
	             ResultSet rs2 = pstmt2.executeQuery()) {

	            if (rs2.next()) {
	                wordData = rs2.getString(columnName);
	            }
	        }

	    } catch (SQLException e) {
	        System.out.println("단어 가져오기 실패");
	        e.printStackTrace();
	    }

	    return wordData;
	}
}
