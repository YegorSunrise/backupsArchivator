package util;

public class TimePass {

    private static long start;

    public static void mark() {
        start = System.currentTimeMillis();
    }

    public static long getPassedTime() {
        return (System.currentTimeMillis() - start)/1000;
    }

}

