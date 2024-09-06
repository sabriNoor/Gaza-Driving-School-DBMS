package com.example.dvs;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class instructorHome implements Initializable {

    @FXML
    private PieChart course;

    @FXML
    private Label coursecount;

    @FXML
    private Label enrollcount;

    @FXML
    private PieChart enrollment;

    int enrollmentCount = 0;
    int enrollmentCountbyins = 0;
    int uniqueCourseCount=0;
    int courseCount = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            // Query to count enrollments for a specific instructor
            String countEnrollmentsByInstructorQuery = "SELECT COUNT(*) AS enrollment_count FROM driving_school.enrollment WHERE instructorid = ?";
            try (PreparedStatement countEnrollmentsByInstructorStatement = DBconn.con.prepareStatement(countEnrollmentsByInstructorQuery)) {
                System.out.println(userInfo.ssn);
                String instructorId = userInfo.ssn;
                countEnrollmentsByInstructorStatement.setString(1, userInfo.issn); // Replace with the actual instructor ID

                try (ResultSet countEnrollmentsByInstructorResult = countEnrollmentsByInstructorStatement.executeQuery()) {
                    if (countEnrollmentsByInstructorResult.next()) {
                        enrollmentCountbyins = countEnrollmentsByInstructorResult.getInt("enrollment_count");
                        System.out.println("Total Enrollments for Instructor: " + enrollmentCountbyins);
                    }
                }
            }

            // Query to count all enrollments
            String countAllEnrollmentsQuery = "SELECT COUNT(*) AS enrollment_count FROM driving_school.enrollment";
            try (PreparedStatement countAllEnrollmentsStatement = DBconn.con.prepareStatement(countAllEnrollmentsQuery)) {
                try (ResultSet countAllEnrollmentsResult = countAllEnrollmentsStatement.executeQuery()) {
                    if (countAllEnrollmentsResult.next()) {
                        enrollmentCount = countAllEnrollmentsResult.getInt("enrollment_count");
                        System.out.println("Total Enrollments: " + enrollmentCount);
                    }
                }
            }

            System.out.println("enrollmentCountbyins: " + enrollmentCountbyins);
            System.out.println("enrollmentCount: " + enrollmentCount);
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }

        try {
            String countUniqueCoursesQuery = "SELECT COUNT(DISTINCT courseid) AS unique_course_count FROM driving_school.enrollment WHERE instructorid = ?";
            try (PreparedStatement countUniqueCoursesStatement = DBconn.con.prepareStatement(countUniqueCoursesQuery)) {
                String instructorId = userInfo.ssn;  // Replace with your actual instructor ID
                countUniqueCoursesStatement.setString(1,userInfo.issn);

                try (ResultSet countUniqueCoursesResult = countUniqueCoursesStatement.executeQuery()) {
                    if (countUniqueCoursesResult.next()) {
                        uniqueCourseCount = countUniqueCoursesResult.getInt("unique_course_count");
                        System.out.println("Unique Courses Enrolled: " + uniqueCourseCount);
                    }
                }
            }
            String countAllEnrollmentsQuery = "SELECT COUNT(*) AS course_count FROM driving_school.course";
            try (PreparedStatement countAllEnrollmentsStatement = DBconn.con.prepareStatement(countAllEnrollmentsQuery)) {
                try (ResultSet countAllEnrollmentsResult = countAllEnrollmentsStatement.executeQuery()) {
                    if (countAllEnrollmentsResult.next()) {
                        courseCount = countAllEnrollmentsResult.getInt("course_count");
                        System.out.println("Total course: " + courseCount);
                    }
                }
            }

            System.out.println("courseCounttotal: " + courseCount);
            System.out.println("coursementCount: " + uniqueCourseCount);


        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }




        enrollment.getData().clear();

        // Add new data
        enrollment.getData().add(new PieChart.Data("Enrollments by Instructor: " + enrollmentCountbyins, enrollmentCountbyins));
        enrollment.getData().add(new PieChart.Data("Total Enrollments: " + enrollmentCount, enrollmentCount));
        // Set additional properties if needed
        enrollment.setTitle("Enrollment Statistics");
        course.getData().clear();

        // Add new data
        course.getData().add(new PieChart.Data("Course Instructor learn: " + uniqueCourseCount, uniqueCourseCount));
        course.getData().add(new PieChart.Data("Total Courses: " + courseCount, courseCount));
        // Set additional properties if needed
        course.setTitle("Course Statistics");
        String countcourse= String.valueOf(uniqueCourseCount);
        String countenrollment= String.valueOf(enrollmentCountbyins);
        enrollcount.setText(countenrollment);
        coursecount.setText(countcourse);
    }

}
