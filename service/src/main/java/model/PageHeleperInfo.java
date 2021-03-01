package model;

import dao.model.ContentList;

import java.util.List;

public class PageHeleperInfo<T>{
    private List<T> data;
    private boolean hasNext;
    private boolean hasPre;
    private int nextPage;
    private int prePage;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public boolean isHasPre() {
        return hasPre;
    }

    public void setHasPre(boolean hasPre) {
        this.hasPre = hasPre;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public int getPrePage() {
        return prePage;
    }

    public void setPrePage(int prePage) {
        this.prePage = prePage;
    }

    public PageHeleperInfo(List<T> data, boolean hasNext, boolean hasPre, int nextPage, int prePage) {
        this.data = data;
        this.hasNext = hasNext;
        this.hasPre = hasPre;
        this.nextPage = nextPage;
        this.prePage = prePage;
    }
    public PageHeleperInfo(){}
}
