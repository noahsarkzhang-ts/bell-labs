package org.noahsark.consistenthash;

/**
 * @author daofeng.xjf
 * @date 2019/2/15
 */
public class Invocation {
    public Invocation() {
    }

    public Invocation(String hashKey) {
        this.hashKey = hashKey;
    }

    private String hashKey;

    public String getHashKey() {
        return hashKey;
    }

    public void setHashKey(String hashKey) {
        this.hashKey = hashKey;
    }
}
