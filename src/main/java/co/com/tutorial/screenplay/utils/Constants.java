package co.com.tutorial.screenplay.utils;


import java.util.List;

public class Constants {

    private Constants() {
        throw new IllegalStateException("Utility class");
    }

    public static class OrangeLogin {

        private OrangeLogin() {
        }

        public static final String INVALID_CREDENTIALS = "Invalid credentials";
    }

    public static class ReqresInService {

        private ReqresInService() {
        }

        public static final List<Integer> ID_USERS_PER_PAGE = List.of(1, 2, 3, 4, 5, 6);
    }
}
