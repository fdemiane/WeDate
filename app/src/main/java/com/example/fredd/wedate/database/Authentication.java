package com.example.fredd.wedate.database;

import com.example.fredd.wedate.monitor.Graph;

public class Authentication {

    Authentication() {

    }

    private void initReferenceTree() throws Exception {
        Graph hierarchyGraph = new Graph();

        //Groups creation
        hierarchyGraph.createGroup("allstudents");
        hierarchyGraph.createGroup("bsstudents");
        hierarchyGraph.createGroup("csstudents");
        hierarchyGraph.createGroup("artstudents");
        hierarchyGraph.linkGroupToNode("bsstudents", "allstudents");
        hierarchyGraph.linkGroupToNode("csstudents", "allstudents");
        hierarchyGraph.linkGroupToNode("artstudents", "allstudents");

        hierarchyGraph.add("csgraduate", "csstudents");
        hierarchyGraph.add("cssenior", "csgraduate");
        hierarchyGraph.add("csjunior", "cssenior");
        hierarchyGraph.add("cssophomore", "cssenior");
        hierarchyGraph.add("csjunior", "cssophomore");

        hierarchyGraph.add("bsgraduate", "bsstudents");
        hierarchyGraph.add("bssenior", "bsgraduate");
        hierarchyGraph.add("bsjunior", "bssenior");
        hierarchyGraph.add("bssophomore", "bssenior");
        hierarchyGraph.add("bsjunior", "bssophomore");

        hierarchyGraph.add("artgraduate", "artstudents");
        hierarchyGraph.add("artsenior", "artgraduate");
        hierarchyGraph.add("artjunior", "artsenior");
        hierarchyGraph.add("artsophomore", "artsenior");
        hierarchyGraph.add("artjunior", "artsophomore");

        //Tree Building
        hierarchyGraph.addRoot("president");
        hierarchyGraph.add("vice-president", "president");
        hierarchyGraph.add("board", "vice-president");
        hierarchyGraph.add("dos", "board");
        hierarchyGraph.linkGroupToNode("allstudents", "dos");

        hierarchyGraph.add("cschair", "board");
        hierarchyGraph.add("csfaculty", "cschair");
        hierarchyGraph.linkGroupToNode("csstudents", "csfaculty");

        hierarchyGraph.add("bschair", "board");
        hierarchyGraph.add("bsfaculty", "bschair");
        hierarchyGraph.linkGroupToNode("bsstudents", "bsfaculty");

        hierarchyGraph.add("artchair", "board");
        hierarchyGraph.add("artfaculty", "artchair");
        hierarchyGraph.linkGroupToNode("artstudents", "artfaculty");


    }

    public static void main(String[] args)
    {
        
    }
}
