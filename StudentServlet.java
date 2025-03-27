package com.example.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import com.example.model.Student;

public class StudentServlet extends HttpServlet {
    private final List<Student> students = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(1);
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        String pathInfo = request.getPathInfo();
        
        if (pathInfo == null || pathInfo.equals("/")) {
            // Получить всех студентов
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(gson.toJson(students));
        } else {
            // Получить студента по ID
            try {
                Long id = Long.parseLong(pathInfo.substring(1));
                Student student = students.stream()
                    .filter(s -> s.getId().equals(id))
                    .findFirst()
                    .orElse(null);
                
                if (student != null) {
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write(gson.toJson(student));
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().write("{\"error\": \"Студент не найден\"}");
                }
            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\": \"Неверный формат ID\"}");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        try {
            StringBuilder body = new StringBuilder();
            String line;
            while ((line = request.getReader().readLine()) != null) {
                body.append(line);
            }
            
            Student student = gson.fromJson(body.toString(), Student.class);
            
            // Устанавливаем ID для нового студента
            student.setId(idCounter.getAndIncrement());
            
            // Валидация
            if (student.getName() == null || student.getName().trim().isEmpty() ||
                student.getLastName() == null || student.getLastName().trim().isEmpty() ||
                student.getFirstName() == null || student.getFirstName().trim().isEmpty() ||
                student.getYearBirth() == null || student.getYearBirth() < 1900 || student.getYearBirth() > 2024) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\": \"Неверные данные студента\"}");
                return;
            }
            
            students.add(student);
            
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(gson.toJson(student));
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Ошибка при создании студента\"}");
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"ID студента не указан\"}");
            return;
        }

        try {
            Long id = Long.parseLong(pathInfo.substring(1));
            StringBuilder body = new StringBuilder();
            String line;
            while ((line = request.getReader().readLine()) != null) {
                body.append(line);
            }
            
            Student updatedStudent = gson.fromJson(body.toString(), Student.class);
            
            // Валидация
            if (updatedStudent.getName() == null || updatedStudent.getName().trim().isEmpty() ||
                updatedStudent.getLastName() == null || updatedStudent.getLastName().trim().isEmpty() ||
                updatedStudent.getFirstName() == null || updatedStudent.getFirstName().trim().isEmpty() ||
                updatedStudent.getYearBirth() == null || updatedStudent.getYearBirth() < 1900 || updatedStudent.getYearBirth() > 2024) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\": \"Неверные данные студента\"}");
                return;
            }
            
            boolean found = false;
            for (int i = 0; i < students.size(); i++) {
                if (students.get(i).getId().equals(id)) {
                    updatedStudent.setId(id);
                    students.set(i, updatedStudent);
                    found = true;
                    break;
                }
            }
            
            if (found) {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(gson.toJson(updatedStudent));
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"error\": \"Студент не найден\"}");
            }
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Неверный формат ID\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Ошибка при обновлении студента\"}");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"ID студента не указан\"}");
            return;
        }

        try {
            Long id = Long.parseLong(pathInfo.substring(1));
            boolean removed = students.removeIf(student -> student.getId().equals(id));
            
            if (removed) {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"error\": \"Студент не найден\"}");
            }
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Неверный формат ID\"}");
        }
    }
} 