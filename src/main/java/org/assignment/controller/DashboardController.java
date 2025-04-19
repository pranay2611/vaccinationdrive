package org.assignment.controller;

import io.jsonwebtoken.Claims;
import org.assignment.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/admin")
    public ResponseEntity<?> adminDashboard(@RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.replace("Bearer ", "");
            Claims claims = jwtUtil.extractClaims(token);

            if (!"ADMIN".equalsIgnoreCase(claims.get("role").toString())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied");
            }

            return ResponseEntity.ok("Welcome Admin: " + claims.getSubject());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token");
        }
    }

    @GetMapping("/student")
    public ResponseEntity<?> studentDashboard(@RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.replace("Bearer ", "");
            Claims claims = jwtUtil.extractClaims(token);

            if (!"STUDENT".equalsIgnoreCase(claims.get("role").toString())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied");
            }

            return ResponseEntity.ok("Welcome Student: " + claims.getSubject());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token");
        }
    }
}
