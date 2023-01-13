package org.noahsark;

import org.noahsark.protostuff.Person;
import org.noahsark.protostuff.ProtostuffUtils;
import org.noahsark.protostuff.Result;

public class ProtostuffUtilsTest {

    public static void main(String[] args) {
        // 构造 Person 对象
        Person person = new Person();

        // Person 对象赋值
        person.setId(1);
        person.setName("john");
        person.setMobile("13845678912");
        person.setEmail("john@gmail.com");

        // 序列化数据
        byte [] message = ProtostuffUtils.serialize(person);

        // 反序列化数据
        Person john = ProtostuffUtils.deserialize(message, Person.class);

        // 输出 Person 对象数据
        System.out.println("person:\n" + john);

        testResult(person);

    }

    private static void testResult(Person person) {
        Result result = new Result(1,"success", person);

        // 序列化数据
        byte [] message = ProtostuffUtils.serialize(result);

        // 反序列化数据
        Result desResult = ProtostuffUtils.deserialize(message, Result.class);

        // 输出 Person 对象数据
        System.out.println("result:\n" + desResult);
    }
}
