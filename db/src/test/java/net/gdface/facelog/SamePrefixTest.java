package net.gdface.facelog;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.Test;

/**
 * @author guyadong
 *
 */
public class SamePrefixTest {
	public class Table{
		String name;
		Table(String name){
			this.name=name;
		}
		protected String getName() {
			return name;
		}

		protected void setName(String name) {
			this.name = name;
		}
	}
	Vector<Table>tables=new Vector<Table>(){

		private static final long serialVersionUID = -5125960844838488464L;

		{
			add(new Table("fl_log"));
			add(new Table("fl_device"));
			add(new Table("fl_persion"));
			add(new Table("fl_image"));
			add(new Table("fl_store"));
		}
	};
	private String getSamePrefix()  {
		int index=-1;
		if(0==this.tables.size()){
			return "";
		}
		String first=this.tables.get(0).getName();
		try{
			for(int i=0;i<first.length();++i){
				for(int j=1;j<this.tables.size();++j){
					String c=this.tables.get(j).getName();
					if(c.charAt(i)!=first.charAt(i)){
						throw new IndexOutOfBoundsException();
					}
				}
				index=i;
			}
		}catch(IndexOutOfBoundsException e){			
		}
		return index<0?"":first.substring(0, index+1);
	}
	@Test
	public void test() {
		//String str="hello";
		//System.out.println(str.replaceFirst("", ""));
		System.out.println(getSamePrefix());
	}

}
