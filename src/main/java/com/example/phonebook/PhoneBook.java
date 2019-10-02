package com.example.phonebook;

import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.util.Map;
import java.util.TreeMap;


@Getter
@Setter
public class PhoneBook {

    TreeMap<String, String> phoneBook;

    //creating a new phone book from a text file
    public void createPhoneBook(File file) throws IOException {
        phoneBook = new TreeMap<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        String line = null;

        while ((line = br.readLine()) != null) {
            String[] lineArgs = line.split(" ", -1);
            String number = normalizePhoneNumber(lineArgs[1]);
            if (number != null) {
                addPhonePair(lineArgs[0], number);
            }
        }
        br.close();
    }

    //adding a pair
    public void addPhonePair(String name, String phoneNumber) {
        phoneBook.put(name, phoneNumber);
    }

    //removing a pair for a given name
    public void removePhonePairByName(String name) {
        phoneBook.remove(name);
    }

    //accessing the phone number for a given name
    public String getPhoneNumberByName(String name) {
        return phoneBook.get(name);
    }

    //printing all pairs, ordered by name
    public void printPhoneBook() {
        for (Map.Entry<String, String> phonePair : phoneBook.entrySet()) {
            System.out.println(phonePair.getKey() + " " + phonePair.getValue());
        }
    }

    public String normalizePhoneNumber(String number) {
        //normalized Bulgarian phone  +359878123456

        //last 7 digits are number > 2 000 000
        Integer sevenDigits = Integer.valueOf(number.substring(number.length() - 7, number.length()));
        if (sevenDigits < 2000000) {
            return null;
        }

        //the next 2 digits are the mobile operator’s code and are one of the following sequences: 87, 88, 89
        Integer operator = Integer.valueOf(number.substring(number.length() - 9, number.length() - 7));
        if (!(operator == 87 || operator == 87 || operator == 87)) {
            return null;
        }
//        //country code
        String code = number.substring(0, number.length() - 9);
        if (number.startsWith("00") && code.length() == 5) {
            number = number.replaceFirst("00", "+");
        } else if (number.startsWith("0") && code.length() == 1) {
            number = number.replaceFirst("0", "+359");
        } else if (!number.startsWith("+359")) {
            return null;
        }
        return number;
    }

    public static void main(String[] args) throws IOException {

        File phoneBookFile = new File("src/main/phoneBookFile/phoneBook.txt");

        //creating a new phone book from a text file
        PhoneBook phoneBook = new PhoneBook();
        phoneBook.createPhoneBook(phoneBookFile);

        System.out.println("Phone Book:");
        phoneBook.printPhoneBook();

        File outgoingCallsFile = new File("src/main/phoneBookFile/outgoingCalls.txt");
        OutgoingCalls o = new OutgoingCalls();
        o.cretateOutgoingCallList(outgoingCallsFile);

        System.out.println("\nTop outgoing calls:");
        o.printTopOutgoingCalls();
    }
}
