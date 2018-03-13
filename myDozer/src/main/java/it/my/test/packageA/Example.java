package it.my.test.packageA;

import java.util.List;

public class Example {

	private String foo;
	private Integer bar;
	private Boolean baz;
	private SubClass sub;
	private SubClass bus;
	private List<SubClass> subList;
	private List<Item> itemList;

	public String getFoo() {
		return foo;
	}

	public void setFoo(String foo) {
		this.foo = foo;
	}

	public Integer getBar() {
		return bar;
	}

	public void setBar(Integer bar) {
		this.bar = bar;
	}

	public Boolean getBaz() {
		return baz;
	}

	public void setBaz(Boolean baz) {
		this.baz = baz;
	}

	public SubClass getSub() {
		return sub;
	}

	public void setSub(SubClass sub) {
		this.sub = sub;
	}

	public SubClass getBus() {
		return bus;
	}

	public void setBus(SubClass bus) {
		this.bus = bus;
	}

	public List<SubClass> getSubList() {
		return subList;
	}

	public void setSubList(List<SubClass> subList) {
		this.subList = subList;
	}

	public List<Item> getItemList() {
		return itemList;
	}

	public void setItemList(List<Item> itemList) {
		this.itemList = itemList;
	}

}