package dao;

import db.MySqlConnection;
import model.Schedule;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ScheduleDAO {
    public void saveSchedule(Schedule schedule) {
        String sql = "INSERT INTO schedules (event_name, event_type, location, duration, participant_count) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = MySqlConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
    
            stmt.setString(1, schedule.getEventName());
            stmt.setString(2, schedule.getEventType());
            stmt.setString(3, schedule.getLocation());
            stmt.setString(4, schedule.getDuration()); // Pastikan hanya mengirimkan angka, bukan string
            stmt.setInt(5, schedule.getParticipantCount());
    
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error: Gagal menyimpan jadwal ke database");
            e.printStackTrace();
        }
    }
    

    public List<Schedule> getAllSchedules() {
        List<Schedule> schedules = new ArrayList<>();
        String sql = "SELECT * FROM schedules";

        try (Connection conn = MySqlConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String eventName = rs.getString("event_name");
                String eventType = rs.getString("event_type");
                String location = rs.getString("location");
                String duration = rs.getString("duration");
                int participantCount = rs.getInt("participant_count");

                schedules.add(new Schedule(eventName, eventType, location, duration, participantCount));
            }
        } catch (SQLException e) {
            System.out.println("Error: Gagal mengambil jadwal dari database.");
            e.printStackTrace();
        }

        return schedules;
    }
}
