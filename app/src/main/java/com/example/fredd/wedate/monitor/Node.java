package com.example.fredd.wedate.monitor;

import java.util.ArrayList;

public class Node {
    String name;
    ArrayList<Node> next;

    public Node(String name) {
        this.name = name;
        next = new ArrayList<>();
    }

    public void addLeaf(Node node) {
        next.add(node);
    }


}
