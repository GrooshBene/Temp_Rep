package kr.applepi.phonecall;

/**
 * Created by user on 2015-08-09.
 */
public class Person {
    private String name;
    private String phoneNum;
    private String age;
    private String subject;

    public String getName() {
        return this.name;
    }

    public String getPhoneNum() {
        return this.phoneNum;
    }

    public String getAge() {
        return this.age;
    }

    public String getSubject() {
        return this.subject;
    }

    public Person(String name, String phoneNum, String age, String subject) {
        this.name = name;
        this.phoneNum = phoneNum;
        this.age = age;
        this.subject = subject;
    }
}

