package model;

public class Schedule {
    private String eventName;
    private String eventType;
    private String location;
    private String duration;
    private int participantCount;

    // Constructor
    public Schedule(String eventName, String eventType, String location, String duration, int participantCount) {
        this.eventName = eventName;
        this.eventType = eventType;
        this.location = location;
        this.duration = duration;
        this.participantCount = participantCount;
    }

    // Getter and Setter
    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getParticipantCount() {
        return participantCount;
    }

    public void setParticipantCount(int participantCount) {
        this.participantCount = participantCount;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "eventName='" + eventName + '\'' +
                ", eventType='" + eventType + '\'' +
                ", location='" + location + '\'' +
                ", duration='" + duration + '\'' +
                ", participantCount=" + participantCount +
                '}';
    }
}
