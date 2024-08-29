package ru.netology;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class RegistrationInfo {
    private String login;
    private String password;
    private String status;
}