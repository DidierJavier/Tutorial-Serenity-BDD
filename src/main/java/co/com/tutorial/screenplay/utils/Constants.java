package co.com.tutorial.screenplay.utils;


public class Constants {

    private Constants() {
        throw new IllegalStateException("Utility class");
    }

    public static class OrangeLogin {

        private OrangeLogin() {
        }

        public static final String INVALID_CREDENTIALS = "Invalid credentials";
    }
}
