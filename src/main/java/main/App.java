package main;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

class Student implements Serializable {
    private static final long serialVersionUID = 1L;

    private String studentName;
    private ArrayList<Grade> studentGrades;
    private int studentCode;

    public Student(String studentName, int studentCode) {
        this.studentName = studentName;
        this.studentGrades = new ArrayList<>();
        this.studentCode = studentCode;
    }

    public int getStudentCode() {
        return studentCode;
    }

    public String getStudentName() {
        return studentName;
    }

    public void addGrade(String courseName, int courseGrade) {
        Grade newGrade = new Grade(courseName, courseGrade);
        studentGrades.add(newGrade);
        System.out.println("Kurssisuoritus lisätty opiskelijalle " + studentName + ": " + newGrade);
    }

    public ArrayList<Grade> getGrades() {
        return studentGrades;
    }

    @Override
    public String toString() {
        return studentCode + ": " + studentName;
    }
}

class Grade implements Serializable {
    private static final long serialVersionUID = 1L;

    private String courseName;
    private int courseGrade;

    public Grade(String courseName, int courseGrade) {
        this.courseName = courseName;
        this.courseGrade = courseGrade;
    }

    public int getCourseGrade() {
        return courseGrade;
    }

    @Override
    public String toString() {
        return courseName + ": " + courseGrade;
    }
}

class Calculator {
    public double getAverageGrade(Student student) {
        ArrayList<Grade> grades = student.getGrades();
        if (grades.isEmpty()) {
            return 0.0;
        }

        double sum = 0.0;
        for (Grade grade : grades) {
            sum += grade.getCourseGrade();
        }
        return sum / grades.size();
    }
}

public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean exit = false;
        ArrayList<Student> students = new ArrayList<>();
        Calculator calculator = new Calculator();

        while (!exit) {
            System.out.println("1) Lisää opiskelija, 2) Listaa opiskelijat, 3) Lisää opiskelijalle suorite, " +
            "4) Listaa opiskelijan suoritteet, 5) Laske opiskelijan suoritusten keskiarvo, " +
            "6) Laske opiskelijan suoritusten mediaani, 7) Tallenna opiskelijat tiedostoon, " +
            "8) Lataa opiskelijat tiedostosta, 0) Lopeta ohjelma");
            
                int choice;
                if (sc.hasNextInt()) {
                    choice = sc.nextInt();
                    sc.nextLine(); // Tyhjennä rivinvaihto
                } else {
                    System.out.println("Virheellinen syöte, yritä uudelleen.");
                    sc.nextLine(); // Tyhjennä rivinvaihto
                    continue;
                }
            
                switch (choice) {
                case 1:
                    System.out.println("Anna opiskelijan nimi? ");
                    String studentName = sc.nextLine();
                    System.out.println("Anna opiskelijan opiskelijanumero: ");
                    int studentCode = sc.nextInt();
                    Student student = new Student(studentName, studentCode);
                    students.add(student);

                    break;

                case 2:
                    System.out.println("Opiskelijat:");
                    for (int i = 0; i < students.size(); i++) {
                        Student s = students.get(i);
                        System.out.println(s.getStudentCode() + ": " + s.getStudentName());
                    }
                    break;

                case 3:
                    System.out.println("Opiskelijat:");
                    for (int i = 0; i < students.size(); i++) {
                        Student s = students.get(i);
                        System.out.println(i + ": " + s.getStudentName());
                    }

                    System.out.println("Mille opiskelijalle suorite lisätään? ");
                    int selectedStudentIndex = sc.nextInt();
                    sc.nextLine(); 

                    if (selectedStudentIndex >= 0 && selectedStudentIndex < students.size()) {
                        Student selectedStudent = students.get(selectedStudentIndex);

                        System.out.println("Mille kurssille suorite lisätään? ");
                        String courseName = sc.nextLine();

                        System.out.println("Mikä arvosana kurssille annetaan? ");
                        int grade = sc.nextInt();

                        selectedStudent.addGrade(courseName, grade);

                    } else {
                        System.out.println("Virheellinen opiskelijan indeksi.");
                    }
                    break;

                case 4:
                    System.out.println("Opiskelijat:");
                    for (int i = 0; i < students.size(); i++) {
                        Student s = students.get(i);
                        System.out.println(i + ": " + s.getStudentName());
                    }
                    System.out.println("Minkä opiskelijan suoritteet listataan? ");
                    int selectedStudentIndexForGrades = sc.nextInt();
                    sc.nextLine(); 

                    if (selectedStudentIndexForGrades >= 0 && selectedStudentIndexForGrades < students.size()) {
                        Student selectedStudentForGrades = students.get(selectedStudentIndexForGrades);

                        for (Grade grade : selectedStudentForGrades.getGrades()) {
                            System.out.println(grade);
                        }
                    } else {
                        System.out.println("Virheellinen opiskelijan indeksi.");
                    }
                    break;

                case 5:
                    System.out.println("Opiskelijat:");
                    for (int i = 0; i < students.size(); i++) {
                        Student s = students.get(i);
                        System.out.println(i + ": " + s.getStudentName());
                    }
                    System.out.println("Minkä opiskelijan suoritteiden keskiarvo lasketaan? ");
                    int selectedStudentIndexForAverage = sc.nextInt();
                    sc.nextLine();  // Tyhjennetään rivinvaihto

                    if (selectedStudentIndexForAverage >= 0 && selectedStudentIndexForAverage < students.size()) {
                        Student selectedStudentForAverage = students.get(selectedStudentIndexForAverage);
                        double average = calculator.getAverageGrade(selectedStudentForAverage);
                        System.out.println("Keskiarvo on " + average);
                    } else {
                        System.out.println("Virheellinen opiskelijan indeksi.");
                    }
                    break;

                case 6:
                    System.out.println("Sorgen, ei pyge\n");
                    break;

                case 7:
                    saveToFile(students);
                    break;

                case 8:
                    loadFromFile(students);
                    break;

                case 0:
                    System.out.println("Kiitos ohjelman käytöstä.");
                    exit = true;
                    break;

                default:
                    System.out.println("Syöte oli väärä!");
            }
        }
        sc.close();
    }

    private static Student findStudent(String name, ArrayList<Student> students) {
        for (Student student : students) {
            if (student != null && student.getStudentName().equalsIgnoreCase(name)) {
                return student;
            }
        }
        return null;
    }

    private static void saveToFile(ArrayList<Student> students) {
        try (FileWriter writer = new FileWriter("students.txt")) {
            for (Student student : students) {
                writer.write(student.getStudentCode() + ": " + student.getStudentName() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Tiedostoon tallentaminen epäonnistui.");
        }
    }

    private static void loadFromFile(ArrayList<Student> students) {
        try (BufferedReader reader = new BufferedReader(new FileReader("students.txt"))) {
            String line;
            students.clear();
    
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                int studentCode = Integer.parseInt(parts[0].trim());
                Student student = new Student(parts[1].trim(), studentCode);
                students.add(student);
            }
    
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
            System.out.println("Tiedoston lukeminen epäonnistui.");
        }
    }
}