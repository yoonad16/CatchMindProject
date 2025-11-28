package server.repository;

import java.sql.*;
import java.util.Scanner;

public class PlayerRepository {
    private final Connection conn;
    
    public PlayerRepository(Connection conn) {
        this.conn = conn;
    }
    
    public void insertPlayer(String id) {

        String sql = "INSERT INTO player_info (id, score) VALUES (?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.setInt(2, 0);
            pstmt.executeUpdate();
            System.out.println("플레이어 정보가 추가되었습니다.");

        } catch (SQLIntegrityConstraintViolationException e) {
            //중복된 id일 경우
            System.err.println("플레이어 추가 실패: 이미 존재하는 ID입니다.");

        } catch (SQLException e) {
            System.err.println("플레이어 추가 실패: " + e.getMessage());
        }
    }


    public void listPlayers() {
        String sql = "SELECT * FROM player_info ORDER BY no ASC";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n[플레이어 목록]");
            while (rs.next()) {
                int no = rs.getInt("no");
                String id = rs.getString("ID");  
                int score = rs.getInt("score");
                System.out.printf("%2d | %10s | %s%n", no, id, score);
            }
        } catch (SQLException e) {
            System.err.println("플레이어 목록 조회 실패: " + e.getMessage());
        }
    }

    public void updatePlayerId(String oldId, String newId) {

        String sql = "UPDATE player_info SET id = ? WHERE id = ?"; // UPDATE student SET id = ? WHERE id = ? 로 써야 할 수도 있음

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newId);
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("플레이어 정보가 수정되었습니다.");
            } else {
                System.out.println("해당 ID의 플레이어가 없습니다.");
            }
        } catch (SQLException e) {
            System.err.println("플레이어 정보 수정 실패: " + e.getMessage());
        }
    }

    public void deletePlayer(String id) {
        String sql = "DELETE FROM player_info WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("플레이어 정보가 삭제되었습니다.");
            } else {
                System.out.println("해당 ID의 플레이어가 없습니다.");
            }
        } catch (SQLException e) {
            System.err.println("플레이어 삭제 실패: " + e.getMessage());
        }
    }
}