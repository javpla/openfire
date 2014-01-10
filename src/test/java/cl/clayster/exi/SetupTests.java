package cl.clayster.exi;

import org.apache.mina.common.IoSession;
import org.jivesoftware.openfire.nio.NIOConnection;
import org.jivesoftware.openfire.session.LocalClientSession;
import org.junit.BeforeClass;
import org.junit.Test;

import junit.framework.TestCase;


public class SetupTests extends TestCase {
	
	EXIFilter exiFilter;
	IoSession session;
	
	@BeforeClass
	public void init(){
		exiFilter = new EXIFilter();
	}
	
	@Test
	public void correctSetup(){
	}
}
