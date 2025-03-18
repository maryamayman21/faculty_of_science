package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class CalculateGPA extends AppCompatActivity {


    private LinearLayout subjectsContainer;
    private TextView textViewResult;

    // Grade values corresponding to letter grades
    private final String[] letterGrades = {"A", "A-", "B+", "B", "C+", "C", "D", "F"};
    private final double[] gradeValues = {4.0, 3.67, 3.33, 3.0, 2.67, 2.33, 2.0, 0.0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_gpa);


        subjectsContainer = findViewById(R.id.subjectsContainer);
        textViewResult = findViewById(R.id.textViewResult);

        // Add a subject input initially
        addSubjectInput();


    }

    public void addSubjectInput() {
        View subjectView = LayoutInflater.from(this).inflate(R.layout.subject_layout, subjectsContainer, false);
        subjectsContainer.addView(subjectView);

    }

    public void calculateGPA(View view) {
        double totalGradePoints = 0;
        double totalHours = 0;
        boolean isValid = true;
        String errorMessage = "";

        for (int i = 0; i < subjectsContainer.getChildCount(); i++) {
            View subjectView = subjectsContainer.getChildAt(i);
            EditText editTextName = subjectView.findViewById(R.id.editTextSubject);
            EditText editTextDegrees = subjectView.findViewById(R.id.editTextDegrees);
            EditText editTextHours = subjectView.findViewById(R.id.editTextHours);
            EditText editTextGrade = subjectView.findViewById(R.id.editTextGrade);

            String name = editTextName.getText().toString().trim();
            String degreesString = editTextDegrees.getText().toString();
            String hoursString = editTextHours.getText().toString();
            String grade = editTextGrade.getText().toString().toUpperCase();

            if (name.isEmpty() || degreesString.isEmpty() || hoursString.isEmpty() || grade.isEmpty()) {
                isValid = false;
                errorMessage = "Please enter values for all fields.";
                break;
            }

            double degrees;
            double hours;

            try {
                degrees = Double.parseDouble(degreesString);
                hours = Double.parseDouble(hoursString);
            } catch (NumberFormatException e) {
                isValid = false;
                errorMessage = "Invalid input. Please enter valid numbers.";
                break;
            }


            double gradeValue = getGradeValue(grade, letterGrades, gradeValues); //return points in gradeVlaues for specific Grade


            if (gradeValue == degrees){     //to be sure user inputs points valid to a grade

                totalGradePoints += (degrees * hours);
                totalHours += hours;
            }

            else {
                isValid = false;
                errorMessage = "Invalid points Entered : " + degrees;
                break;

            }


        }

        if (isValid) {
            if (totalHours > 0) {
                // Calculate GPA
                double gpa = totalGradePoints / totalHours;
                // Format GPA output to display up to two decimal places
                String formattedGPA = String.format("%.2f", gpa);

                // Determine final grade based on GPA
                String finalGrade = getFinalGrade(gpa);
                textViewResult.setText("Overall GPA: " + formattedGPA + "\nFinal Grade: " + finalGrade);
            } else {
                textViewResult.setText("No subjects entered.");
            }
        } else {
            textViewResult.setText(errorMessage);
        }
    }
    public void deleteSubject(View view) {
        // Remove the subject view when delete button is clicked
        subjectsContainer.removeView((View) view.getParent());
    }

    // Helper method to determine final grade based on GPA
    private String getFinalGrade(double gpa) {
        if (gpa > 3.67) {
            return "A";
        } else if (gpa > 3.33) {
            return "A-";
        } else if (gpa >= 3.0) {
            return "B+";
        } else if (gpa > 2.67) {
            return "B";
        } else if (gpa >= 2.33) {
            return "C+";
        } else if (gpa > 2.0) {
            return "C";
        } else if (gpa == 2.0) {
            return "D";
        } else {
            return "F";
        }
    }

    // Helper method to get grade value
    private double getGradeValue(String grade, String[] letterGrades, double[] gradeValues) {
        for (int j = 0; j < letterGrades.length; j++) {
            if (letterGrades[j].equals(grade)) {
                return gradeValues[j];
            }
        }
        return 0.0; // Default value if grade is not found
    }

    public void addSubject(View view) {
        addSubjectInput();
    }
}