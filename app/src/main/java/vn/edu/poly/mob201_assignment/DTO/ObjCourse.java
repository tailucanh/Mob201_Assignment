package vn.edu.poly.mob201_assignment.DTO;

import java.io.Serializable;

public class ObjCourse implements Serializable {
    private int id;
    private String courseName;
    private String schedule;
    private String testSchedule;
    private int idCoursePhoto;
    private boolean checkRegistration;

    public static final String TABLE_NAME = "tbCourse";
    public static final String COL_ID_COURSE = "idCourse";
    public static final String COL_NAME_COURSE = "courseName";
    public static final String COL_SCHEDULE= "schedule";
    public static final String COL_TEST_SCHEDULE= "testSchedule";
    public static final String COL_PHOTO = "idCoursePhoto";


    public ObjCourse() {
    }

    public ObjCourse(int id, String courseName, String schedule, String testSchedule, int idCoursePhoto) {
        this.id = id;
        this.courseName = courseName;
        this.schedule = schedule;
        this.testSchedule = testSchedule;
        this.idCoursePhoto = idCoursePhoto;
    }
    public ObjCourse(int id, String courseName, String schedule, String testSchedule, int idCoursePhoto, boolean checkRegistration) {
        this.id = id;
        this.courseName = courseName;
        this.schedule = schedule;
        this.testSchedule = testSchedule;
        this.idCoursePhoto = idCoursePhoto;
        this.checkRegistration = checkRegistration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getTestSchedule() {
        return testSchedule;
    }

    public void setTestSchedule(String testSchedule) {
        this.testSchedule = testSchedule;
    }

    public int getIdCoursePhoto() {
        return idCoursePhoto;
    }

    public void setIdCoursePhoto(int idCoursePhoto) {
        this.idCoursePhoto = idCoursePhoto;
    }

    public boolean isCheckRegistration() {
        return checkRegistration;
    }

    public void setCheckRegistration(boolean checkRegistration) {
        this.checkRegistration = checkRegistration;
    }
}
