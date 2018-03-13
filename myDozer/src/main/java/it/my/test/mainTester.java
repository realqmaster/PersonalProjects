package it.my.test;

import java.util.ArrayList;
import java.util.List;

import it.my.test.packageA.Example;
import it.my.test.packageA.Item;
import it.my.test.packageA.SubClass;

public class mainTester {

	
	public static void main(String[] args) {
		
		Example exampleA = new Example();
		it.my.test.pakacgeB.Example exampleB = new it.my.test.pakacgeB.Example();
		
		exampleA.setFoo("Pippo");
		SubClass sub = new SubClass();
		sub.setSubField("Pluto");
		exampleA.setSub(sub);
		
		List<SubClass> subList = new ArrayList<SubClass>();
		subList.add(sub);
		subList.add(sub);
		subList.add(sub);
		
		List<Item> itemList = new ArrayList<Item>();
		Item item = new Item();
		item.setItemName("Paperino");
		itemList.add(item);
		itemList.add(item);
		itemList.add(item);
		itemList.add(item);
		itemList.add(item);
		
		exampleA.setItemList(itemList);
		
		exampleA.setSubList(subList);
				
		DozerMapper dz = new DozerMapper();
		
		
		exampleB = (it.my.test.pakacgeB.Example) dz.map(exampleA, exampleB);
		
		
		System.out.println(exampleB.getPrefix_sub().getPrefix_subField());
		System.out.println(exampleB.getPrefix_itemList().get(3).getPrefix_itemName());
		
	}
}
