package simpleranks.utils;

public class JavaTools {

    public static boolean isLong(String s) {
        try {
            Long.valueOf(s);
            return true;
        } catch (Exception e) {}
        return false;
    }

    public static boolean isInteger(String s) {
        try {
            Integer.valueOf(s);
            return true;
        } catch (Exception e) {}
        return false;
    }
}
