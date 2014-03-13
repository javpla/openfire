package cl.clayster.exi;

import org.apache.mina.common.ByteBuffer;
import org.apache.mina.common.IoFilterAdapter;
import org.apache.mina.common.IoSession;

/**
 * This class recognizes EXI Alternative Binding. There is only two possible messages that can be received, otherwise the filter will be eliminated from 
 * the current session. In other words, the alternative binding requires EXI messages from the very start. 
 * @author Javier Placencio
 *
 */
public class EXIAlternativeBindingFilter extends IoFilterAdapter {
	
	public static final String filterName = "altBindFilter";
	
	public EXIAlternativeBindingFilter(){}

	@Override
	public void messageReceived(NextFilter nextFilter, IoSession session, Object message) throws Exception {
		// Decode the bytebuffer and print it to the stdout
        if (message instanceof ByteBuffer) {
            ByteBuffer byteBuffer = (ByteBuffer) message;
            // Keep current position in the buffer
            int currentPos = byteBuffer.position();
            
            if(!EXIProcessor.hasEXICookie(byteBuffer.array())){
            	// Reset to old position in the buffer
                byteBuffer.position(currentPos);
                session.getFilterChain().remove(EXIAlternativeBindingFilter.filterName);
            }
            else{
            	String xml = EXIProcessor.decodeSchemaless(byteBuffer.array());
            	String hostName = EXIUtils.getAttributeValue(xml, "to"); 
System.out.println("EXIDECODED schemaless (" + session.hashCode() + "): " + xml);
                if(xml.startsWith("<exi:setup ")){
                	System.out.println("Se recibi� <exi:setup>");
                }
                else if(xml.startsWith("<exi:streamStart ")){
                	
                	String streamStart = "<exi:streamStart xmlns:exi='http://jabber.org/protocol/compress/exi'"
                			+ " version='1.0'"
                			+ " from='"
                			+ hostName
                			+ "' xml:lang='en'"
                			+ " xmlns:xml='http://www.w3.org/XML/1998/namespace' >"
                			+ " <exi:xmlns prefix='stream' namespace='http://etherx.jabber.org/streams' />"
                			+ " <exi:xmlns prefix='' namespace='jabber:client' />"
                			+ " <exi:xmlns prefix='xml' namespace='http://www.w3.org/XML/1998/namespace' />"
                			+ " </exi:streamStart>";
                	byte[] response = EXIProcessor.encodeSchemaless(streamStart, true);
                	session.write(ByteBuffer.wrap(response));
                	throw new Exception("exi:streamStart");
                }
                message = xml;
            }
        }
        // Pass the message to the next filter
		super.messageReceived(nextFilter, session, message);
	}
}
