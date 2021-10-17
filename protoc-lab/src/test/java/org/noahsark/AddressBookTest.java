package org.noahsark;

import com.google.protobuf.InvalidProtocolBufferException;
import org.noahsark.tutorial.protos.AddressBook;
import org.noahsark.tutorial.protos.Person;

public class AddressBookTest {

    public static void main(String[] args) throws InvalidProtocolBufferException {

        // 构造 AddressBook 对象并序列化数据
        byte [] message = marshal();

        // 反序列化 AddressBook 对象
        AddressBook addressBook = unmarshal(message);

        // 输出 AddressBook 对象
        listAddressBook(addressBook);

    }

    private static byte [] marshal() {
        // 构造 Person 对象
        Person john =
                Person.newBuilder()
                        .setId(1234)
                        .setName("John Doe")
                        .setEmail("jdoe@example.com")
                        .addPhones(
                                Person.PhoneNumber.newBuilder()
                                        .setNumber("555-4321")
                                        .setType(Person.PhoneType.HOME))
                        .build();

        // 构造 AddressBook 对象
        AddressBook addressBook = AddressBook.newBuilder()
                .addPeoples(john)
                .putPeopleMap(john.getName(),john).build();

        // 序列化 AddressBook 对象
        byte [] message = addressBook.toByteArray();

        return message;
    }

    private static AddressBook unmarshal(byte [] message) throws InvalidProtocolBufferException {
        AddressBook addressBook = AddressBook.parseFrom(message);

        return addressBook;
    }

    private static void listAddressBook(AddressBook addressBook) {
        for (Person person: addressBook.getPeoplesList()) {

            System.out.println("Person ID: " + person.getId());
            System.out.println("  Name: " + person.getName());
            System.out.println("  E-mail address: " + person.getEmail());

            for (Person.PhoneNumber phoneNumber : person.getPhonesList()) {
                switch (phoneNumber.getType()) {
                    case MOBILE:
                        System.out.print("  Mobile phone #: ");
                        break;
                    case HOME:
                        System.out.print("  Home phone #: ");
                        break;
                    case WORK:
                        System.out.print("  Work phone #: ");
                        break;
                }
                System.out.println(phoneNumber.getNumber());
            }
        }
    }

    private static void addPerson() {

    }

}
