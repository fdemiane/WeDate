package com.example.fredd.wedate.monitor;

import java.util.HashSet;
import java.util.Set;

public class Attributes {

    private Set<String> canSeeWhoLiked = new HashSet<>();
    private Set<String> canComment = new HashSet<>();
    private boolean canMath = false;
    private boolean canSuperLike = false;
    private boolean canSpy = false;


    public Set<String> getCanSeeWhoLiked() {
        return canSeeWhoLiked;
    }

    public void setCanSeeWhoLiked(Set<String> canSeeWhoLiked) {
        this.canSeeWhoLiked = canSeeWhoLiked;
    }

    public Set<String> getCanComment() {
        return canComment;
    }

    public void setCanComment(Set<String> canComment) {
        this.canComment = canComment;
    }

    public boolean isCanMath() {
        return canMath;
    }

    public void setCanMath(boolean canMath) {
        this.canMath = canMath;
    }

    public boolean isCanSuperLike() {
        return canSuperLike;
    }

    public void setCanSuperLike(boolean canSuperLike) {
        this.canSuperLike = canSuperLike;
    }

    public boolean isCanSpy() {
        return canSpy;
    }

    public void setCanSpy(boolean canSpy) {
        this.canSpy = canSpy;
    }
}
