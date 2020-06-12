package com.example.vo;

public class ItemVO {
	private int itemno; //ITEMNO
	private String itemname; //ITEMNAME
	private int itemprice; //ITEMPRICE
	private int itemqty; //ITEMQTY
	private String itemdes; //ITEMDES
	private String itemdate; //ITEMDATE
	
	public int getItemno() {
		return itemno;
	}
	public void setItemno(int itemno) {
		this.itemno = itemno;
	}
	public String getItemname() {
		return itemname;
	}
	public void setItemname(String itemname) {
		this.itemname = itemname;
	}
	public int getItemprice() {
		return itemprice;
	}
	public void setItemprice(int itemprice) {
		this.itemprice = itemprice;
	}
	public int getItemqty() {
		return itemqty;
	}
	public void setItemqty(int itemqty) {
		this.itemqty = itemqty;
	}
	public String getItemdes() {
		return itemdes;
	}
	public void setItemdes(String itemdes) {
		this.itemdes = itemdes;
	}
	public String getItemdate() {
		return itemdate;
	}
	public void setItemdate(String itemdate) {
		this.itemdate = itemdate;
	}
	
	@Override
	public String toString() {
		return "ItemVO [itemno=" + itemno + ", itemname=" + itemname + ", itemprice=" + itemprice
				+ ", itemqty=" + itemqty + ", itemdes=" + itemdes + ", itemdate=" + itemdate + "]";
	}
}
