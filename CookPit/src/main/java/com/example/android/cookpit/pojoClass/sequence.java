package com.example.android.cookpit.pojoClass;

import java.io.Serializable;

/**
 * Created by alexandrelevieux on 15/09/2017.
 */

public class sequence {
    private long id;
    private long level;
    private String rootA;
    private long rootB;
    private String rootC;

    public sequence() {
    }

    public sequence(long id, long level, String rootA, long rootB, String rootC) {
        this.id = id;
        this.level = level;
        this.rootA = rootA;
        this.rootB = rootB;
        this.rootC = rootC;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getLevel() {
        return level;
    }

    public void setLevel(long level) {
        this.level = level;
    }

    public String getRootA() {
        return rootA;
    }

    public void setRootA(String rootA) {
        this.rootA = rootA;
    }

    public long getRootB() {
        return rootB;
    }

    public void setRootB(long rootB) {
        this.rootB = rootB;
    }

    public String getRootC() {
        return rootC;
    }

    public void setRootC(String rootC) {
        this.rootC = rootC;
    }
}
