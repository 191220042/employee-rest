package org.springframework.hateoas.examples;
import java.util.Random;
public class PlayerControllerTests {
    public static void main(String[] args) {
        Random random = new Random(47);
        int a= random.nextInt(10);
        int b= random.nextInt(10);
        int c= random.nextInt(10);
        int d= random.nextInt(10);
        String mycode=""+a+b+c+d;
        System.out.println(mycode);

    }
}