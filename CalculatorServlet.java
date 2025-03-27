package com.example.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class CalculatorServlet extends HttpServlet {
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        try {
            StringBuilder body = new StringBuilder();
            String line;
            while ((line = request.getReader().readLine()) != null) {
                body.append(line);
            }
            
            CalculatorRequest calculatorRequest = gson.fromJson(body.toString(), CalculatorRequest.class);
            int[] numbers = parseNumbers(calculatorRequest.getNumbers(), calculatorRequest.getType());
            
            if (numbers == null || numbers.length == 0) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\": \"Неверный формат чисел\"}");
                return;
            }
            
            int max = findMax(numbers);
            
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"result\": " + max + "}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Ошибка при вычислении\"}");
        }
    }
    
    private int[] parseNumbers(String numbers, String type) {
        try {
            return switch (type) {
                case "string" -> Arrays.stream(numbers.split(","))
                        .map(String::trim)
                        .mapToInt(Integer::parseInt)
                        .toArray();
                case "array" -> gson.fromJson(numbers, int[].class);
                case "list" -> {
                    @SuppressWarnings("unchecked")
                    List<Integer> list = gson.fromJson(numbers, List.class);
                    yield list.stream().mapToInt(Integer::intValue).toArray();
                }
                default -> null;
            };
        } catch (Exception e) {
            return null;
        }
    }
    
    private int findMax(int[] numbers) {
        int max = numbers[0];
        for (int i = 1; i < numbers.length; i++) {
            if (numbers[i] > max) {
                max = numbers[i];
            }
        }
        return max;
    }
    
    @SuppressWarnings("unused")
    private static class CalculatorRequest {
        private String numbers;
        private String type;
        
        public String getNumbers() {
            return numbers;
        }
        
        public void setNumbers(String numbers) {
            this.numbers = numbers;
        }
        
        public String getType() {
            return type;
        }
        
        public void setType(String type) {
            this.type = type;
        }
    }
} 