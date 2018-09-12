package erp;

import static org.junit.Assert.*;

import org.junit.Test;

import com.yc.bean.Acjob;
import com.yc.biz.BaseBiz;

public class TestBaseBiz {

	@Test
	public void test() throws Exception {
		BaseBiz bb=new BaseBiz();
		System.out.println(bb.findCount("select count(*) from acjob", null));
	}
	
	@Test
	public void test1() throws Exception {
		BaseBiz bb=new BaseBiz();
		System.out.println(bb.findList("select * from acjob", null,Acjob.class));
	}
	
	@Test
	public void test2() throws Exception {
		BaseBiz bb=new BaseBiz();
		System.out.println(bb.findByPage("select count(*) from acjob",null, "select * from acjob", null, 1, 10, Acjob.class));
	}
}
