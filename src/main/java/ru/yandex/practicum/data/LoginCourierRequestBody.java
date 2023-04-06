package ru.yandex.practicum.data;

public class LoginCourierRequestBody {
    private final String login;
    private final String password;

    public LoginCourierRequestBody(String login, String password) {
        this.login = login;
        this.password = password;
    }
}
