package com.mei;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// Deleted:import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;

/**
 * 启动程序
 *
 * @author MEI
 */
@SpringBootApplication
@MapperScan({"com.mei.**.mapper"})
public class MEIApplication
{
    public static void main(String[] args)
    {
        System.setProperty("java.awt.headless", "true");
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(MEIApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  mei启动成功   ლ(´ڡ`ლ)ﾞ  \n" +
                " .-------.       ____     __        \n" +
                " |  _ _   \\      \\   \\   /  /    \n" +
                " | ( ' )  |       \\  _. /  '       \n" +
                " |(_ o _) /        _( )_ .'         \n" +
                " | (_,_).' __  ___(_ o _)'          \n" +
                " |  |\\ \\  |  ||   |(_,_)'         \n" +
                " |  | \\ `'   /|   `-'  /           \n" +
                " |  |  \\    /  \\      /           \n" +
                " ''-'   `'-'    `-..-'              ");
    }
}
