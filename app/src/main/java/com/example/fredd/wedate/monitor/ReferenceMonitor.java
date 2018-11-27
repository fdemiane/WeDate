package com.example.fredd.wedate.monitor;

import android.util.Log;

import com.example.fredd.wedate.shared.DataFlow;

import java.util.Set;

public class ReferenceMonitor {
    private Graph hierarchyGraph = new Graph();

    public ReferenceMonitor() throws Exception {
        initReferenceTree();
    }

    public void setAttributes(User user)
    {
        String tag = user.getTag();
        Set<String> dominated = hierarchyGraph.getDominating(tag);
        Set<String> attributes = hierarchyGraph.getAttributes(tag);
        DataFlow.user = user;
        DataFlow.attributes.setCanComment(dominated);
        DataFlow.attributes.setCanSeeWhoLiked(dominated);
        DataFlow.attributes.setCanMath(attributes.contains("matchmake"));
        DataFlow.attributes.setCanSpy(attributes.contains("spy"));
        DataFlow.attributes.setCanSuperLike(attributes.contains("superlike"));
        Log.d("TAG" , ""+DataFlow.attributes.isCanMath() +" "+DataFlow.attributes.isCanSpy() + " "+DataFlow.attributes.isCanSuperLike());

    }

    private void initReferenceTree() throws Exception {
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
        hierarchyGraph.add("csfreshman", "cssophomore");

        hierarchyGraph.add("bsgraduate", "bsstudents");
        hierarchyGraph.add("bssenior", "bsgraduate");
        hierarchyGraph.add("bsjunior", "bssenior");
        hierarchyGraph.add("bssophomore", "bssenior");
        hierarchyGraph.add("bsfreshman", "bssophomore");

        hierarchyGraph.add("artgraduate", "artstudents");
        hierarchyGraph.add("artsenior", "artgraduate");
        hierarchyGraph.add("artjunior", "artsenior");
        hierarchyGraph.add("artsophomore", "artsenior");
        hierarchyGraph.add("artfreshman", "artsophomore");

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

        hierarchyGraph.addAttribute("dos" , "superlike");
        hierarchyGraph.addAttribute("vice-president" , "spy");
        hierarchyGraph.addAttribute("president" , "matchmake");





    }
}
