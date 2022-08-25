package org.quickstart.vavr;

import io.vavr.Function1;
import io.vavr.Function2;
import io.vavr.control.Option;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class VavrTest {

    @Test
    public void testList(){
        // of 方法是 Java9 开始提供的静态工厂
        java.util.List.of(1, 2, 3, 4, 5) //
            .stream() //
            .filter(i -> i > 3) //
            .map(i -> i * 2) //
            .collect(Collectors.toList());

        // 使用Java 8的代码
        Arrays.asList(1, 2, 3).stream().reduce((i, j) -> i + j);
        IntStream.of(1, 2, 3).sum();

        // 使用vavr实现相同的功能，则更加直接：
        //io.vavr.collection.List
        io.vavr.collection.List.of(1, 2, 3).sum();
    }

    @Test
    public void testCompose() {
        //使用andThen
        Function1<Integer, Integer> plusOne = a -> a + 1;
        Function1<Integer, Integer> multiplyByTwo = a -> a * 2;
        Function1<Integer, Integer> add1AndMultiplyBy2 = plusOne.andThen(multiplyByTwo);
        Assert.assertEquals(6, add1AndMultiplyBy2.apply(2).intValue());

        //使用compose
        Function1<Integer, Integer> add1AndMultiplyBy2WithCompose = multiplyByTwo.compose(plusOne);
        Assert.assertEquals(6, add1AndMultiplyBy2WithCompose.apply(2).intValue());
    }

    @Test
    public void testLifting() {
        Function2<Integer, Integer, Integer> divide = (a, b) -> a / b;
        Function2<Integer, Integer, Option<Integer>> safeDivide = Function2.lift(divide);

        // = None
        Option<Integer> i1 = safeDivide.apply(1, 0);

        System.out.println(i1.get());

        Assert.assertEquals("None", i1.toString());

        // = Some(2)
        Option<Integer> i2 = safeDivide.apply(4, 2);
        Assert.assertEquals(2, i2.get().intValue());
    }
}
