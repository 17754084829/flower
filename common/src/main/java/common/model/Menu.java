package common.model;

import java.util.ArrayList;

public class Menu {
    private ArrayList<String> arrayList;
    private int size;

    public Menu(ArrayList<String> arrayList, int size) {
        this.arrayList = arrayList;
        this.size = size;
    }
    public Menu(){}

    public ArrayList<String> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<String> arrayList) {
        this.arrayList = arrayList;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
