package org.example.entity;

public class Student {
    private int id;
    private String name;
    private double gpa;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(Double gpa) {
        this.gpa = gpa;
    }

    public static StudentBuilder createBuilder() {
        return new StudentBuilder();
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gpa=" + gpa +
                '}';
    }

    public static class StudentBuilder {
        private int id;
        private String name;
        private double gpa;

        public Student build() {
            var student = new Student();

            student.setId(id);
            student.setName(name);
            student.setGpa(gpa);

            return student;
        }

        public StudentBuilder name(String name) {
            this.name = name;

            return this;
        }

        public StudentBuilder id(int id) {
            this.id = id;

            return this;
        }

        public StudentBuilder gpa(double gpa) {
            this.gpa = gpa;

            return this;
        }

    }

}
