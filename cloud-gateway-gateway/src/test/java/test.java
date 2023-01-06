import org.junit.Test;

import java.time.ZonedDateTime;

/**
 * @author SHshuo
 * @data 2023/1/6--19:03
 */
public class test {

    /***
     * 获取默认时区
     * @param args
     */
    @Test
    public static void main(String[] args) {
        ZonedDateTime time = ZonedDateTime.now();
        System.out.println(time);
    }
}
