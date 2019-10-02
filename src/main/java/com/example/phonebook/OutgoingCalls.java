package com.example.phonebook;

import lombok.Getter;
import lombok.Setter;
import javafx.util.Pair;

import java.io.*;
import java.util.*;
import java.util.List;

@Getter
@Setter
public class OutgoingCalls {
    private SortedSet pairs;

    //the count of outgoing calls to numbers from file
    public void cretateOutgoingCallList(File file) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        String line = null;

        pairs = new TreeSet<Pair<String, Integer>>(Comparator.comparing(Pair::getValue));
        while ((line = br.readLine()) != null) {
            String[] lineArgs = line.split(" ", -1);
            Pair<String, Integer> pair = new Pair<String, Integer>(lineArgs[0], Integer.valueOf(lineArgs[1]));
            if (pairs.size() < 5) {
                pairs.add(pair);
            } else if (((Pair<String, Integer>) pairs.first()).getValue() < pair.getValue()) {
                pairs.remove(pairs.first());
                pairs.add(pair);
            }
        }
        br.close();
    }


    public void printTopOutgoingCalls() {
        Iterator<Pair<String, Integer>> iterator = pairs.iterator();

        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }
}