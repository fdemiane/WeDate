package com.example.fredd.wedate.monitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class Graph {
    ArrayList<Node> root = new ArrayList<>();
    ArrayList<Boolean> groups = new ArrayList<>();
    HashMap<String, Set<String>> attributesMap = new HashMap<>();

    public void addRoot(String name) throws Exception {
        if (checkContains(name))
            throw new Exception("The graph already contains the name: " + name);
        root.add(new Node(name));
        groups.add(false);
    }

    public void add(String name, String predecessor) throws Exception {
        Node temp = getNode(predecessor);
        if (checkContains(name))
            throw new Exception("The graph already contains the name: " + name);
        temp.addLeaf(new Node(name));
    }

    public void print() {
        for (int i = 0; i < root.size(); i++)
            print(root.get(i));

    }

    public void addAttribute(String name, String attribute) {
        Set<String> set = attributesMap.get(name);
        if (set == null)
            set = new HashSet<>();
        set.add(attribute);
        attributesMap.put(name, set);

    }

    public Set<String> getAttributes(String name) {
        Set<String> set = getDominating(name);
        Set<String> attributes = new HashSet<>();
        for (String str : set) {
            Set<String> temp = attributesMap.get(str);
            if (temp == null)
                continue;
            attributes.addAll(temp);

        }

        return attributes;
    }

    private boolean checkContains(String name) {
        for (int i = 0; i < root.size(); i++)
            if (checkContains(name, root.get(i)))
                return true;

        return false;
    }

    private boolean checkContains(String name, Node leaf) {
        if (leaf == null)
            return false;

        if (leaf.name.equals(name))
            return true;

        for (int i = 0; i < leaf.next.size(); i++)
            if (checkContains(name, leaf.next.get(i)))
                return true;

        return false;
    }

    private void print(Node leaf) {
        if (leaf == null)
            return;
        System.out.println(leaf.name);

        for (int i = 0; i < leaf.next.size(); i++)
            print(leaf.next.get(i));


    }

    private Node getNode(String name) {
        for (int i = 0; i < root.size(); i++) {
            Node temp = root.get(i);
            if (temp.name.equals(name))
                return temp;
            temp = getNode(name, temp);
            if (temp != null)
                return temp;

        }

        return null;
    }

    private Node getNode(String name, Node leaf) {
        for (int i = 0; i < leaf.next.size(); i++) {
            Node temp = leaf.next.get(i);
            if (temp.name.equals(name))
                return temp;
            temp = getNode(name, leaf.next.get(i));
            if (temp != null)
                return temp;
        }

        return null;

    }

    public Set<String> getDominating(String name) {
        Set<String> result;
        Node temp = getNode(name);
        result = getDominating(temp);
        return result;
    }


    public Set<String> getDominating(Node leaf) {
        if (leaf == null)
            return null;

        Set<String> set = new TreeSet<>();
        set.add(leaf.name);
        for (int i = 0; i < leaf.next.size(); i++) {
            Set<String> temp = getDominating(leaf.next.get(i));
            if (temp == null)
                continue;
            for (String str : temp) {
                set.add(str);
            }

        }

        return set;
    }

    public void createGroup(String groupName) throws Exception {
        if (checkContains(groupName))
            throw new Exception("The graph already contains the name: " + groupName);
        root.add(new Node(groupName));
        groups.add(true);
    }

    public String getGroupName(String name) {
        for (int i = 0; i < root.size(); i++) {
            Node temp = root.get(i);
            if (temp.name.equals(name) && groups.get(i))
                return root.get(i).name;
            temp = getNode(name, temp);
            if (temp != null && groups.get(i))
                return root.get(i).name;

        }

        return null;
    }

    public boolean checkIfBelongsToSameGroup(String name1, String name2) {
        String g1 = getGroupName(name1);
        if (g1 == null)
            return false;
        String g2 = getGroupName(name2);
        if (g2 == null)
            return false;
        return g1.equals(g2);
    }


    public void linkGroupToNode(String group1, String node) throws Exception {
        Node g1 = null;
        for (int i = 0; i < root.size(); i++)
            if (root.get(i).name.equals(group1))
                g1 = root.get(i);


        Node temp = getNode(node);
        if (g1 == null)
            throw new Exception("Group does not exist");
        temp.addLeaf(g1);


    }

    public String getGroup(String name) {
        for (int i = 0; i < root.size(); i++) {
            if (groups.get(i)) {
                Node temp = getNode(name , root.get(i));
                if(temp!=null)
                    return root.get(i).name;
            }
        }

        return null;
    }

    public static void main(String[] args) throws Exception {
        Graph graph = new Graph();
        graph.createGroup("students");
        graph.createGroup("csstudents");
        graph.add("csjunior", "csstudents");
        graph.linkGroupToNode("csstudents", "students");
        graph.addRoot("president");
        graph.add("vice1", "president");
        graph.add("vice2", "president");
        graph.linkGroupToNode("students", "president");

        System.out.println(graph.getGroup("csjunior"));

       /* Set<String> set = graph.getDominating("president");
        for (String str : set)
            System.out.println(str);*/


    }

}
