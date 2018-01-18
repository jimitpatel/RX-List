package app.jimit.www.rxlist.data.local.models;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * Created by jimit on 18-01-2018.
 */

public class Meta implements Serializable {

    @Expose private String previous;
    @Expose private String next;
    @Expose private int offset;
    @Expose private int count;
    @Expose private int limit;

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
