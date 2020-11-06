package com.ace.business.entity;

public class OperButton {
    private String label;//标签名称
    private String className;//标签样式
    private String clickName;//点击事件名称

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClickName() {
        return clickName;
    }

    public void setClickName(String clickName) {
        this.clickName = clickName;
    }

    public OperButton(String label, String className, String clickName) {
        this.label = label;
        this.className = className;
        this.clickName = clickName;
    }
    public OperButton(){}
}
