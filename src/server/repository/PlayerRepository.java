package server.repository;

import server.domain.Player;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerRepository {
    private final Connection conn;
    public PlayerRepository () {
       this.conn = DBConnector.connectDB();
        System.out.println("플레이어 레포지토리 DB 연결 성공");
    }

    public Player findPlayerByName(String name) {
        String sql = "SELECT * FROM player WHERE name = ?";
        Player player = null;

        try(PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setString(1, name);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("플레이어 찾음!!!!!");
                    player = new Player();
                    player.setId(rs.getInt("id"));
                    player.setWin(rs.getInt("win"));
                    player.setPlayTime(rs.getInt("playtime"));
                    player.setName(name);
                }
            };
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return player;

    }


    public void savePlayer (Player player) {
        String insertSql = "INSERT into player (name) VALUES (?)";
        try(PreparedStatement pstmt = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);) {
            pstmt.setString(1, player.getName());
            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()){
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    player.setId(id);
                    System.out.println(id + player.getName()+ "플레이어의 정보가 DB에 추가되었습니다.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void updatePlayer (Player player) {
        String updateSql = "UPDATE player SET win = ?, playTime = ? WHERE id = ?";

        try(PreparedStatement pstmt = conn.prepareStatement(updateSql);) {
            pstmt.setInt(1, player.getWin());
            pstmt.setInt(2,player.getPlayTime());
            pstmt.setInt(3,player.getId());
            pstmt.executeUpdate();
            System.out.println(player.getName()+"의 점수가 DB에 업데이트되었습니다.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletePlayer (Player player) {
        String deleteSql = "DELETE FROM player WHERE id = ?";

        try(PreparedStatement pstmt = conn.prepareStatement(deleteSql);) {
            pstmt.setInt(1,player.getId());
            pstmt.executeUpdate();
            System.out.println(player.getName()+"이 DB에서 삭제되었습니다.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
