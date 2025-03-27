package com.example.model;

@SuppressWarnings("unused")
public class Student {
    private Long id;
    private String name;
    private String lastName;
    private String firstName;
    private Integer yearBirth;

    public Student() {
    }

    public Student(String name, String lastName, String firstName, Integer yearBirth) {
        this.name = name;
        this.lastName = lastName;
        this.firstName = firstName;
        this.yearBirth = yearBirth;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Integer getYearBirth() {
        return yearBirth;
    }

    public void setYearBirth(Integer yearBirth) {
        this.yearBirth = yearBirth;
    }
} 