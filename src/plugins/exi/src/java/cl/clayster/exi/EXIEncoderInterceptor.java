package cl.clayster.exi;

import java.io.IOException;
import java.util.HashMap;

import javax.xml.transform.TransformerException;

import org.jivesoftware.openfire.interceptor.PacketInterceptor;
import org.jivesoftware.openfire.interceptor.PacketRejectedException;
import org.jivesoftware.openfire.session.Session;
import org.xml.sax.SAXException;
import org.xmpp.packet.JID;
import org.xmpp.packet.Packet;

import com.siemens.ct.exi.exceptions.EXIException;


public class EXIEncoderInterceptor implements PacketInterceptor{
	
	//private HashMap<String, EXIProcessor> exiProcessors = new HashMap<String, EXIProcessor>();
	private HashMap<JID, EXIProcessor> exiProcessors = new HashMap<JID, EXIProcessor>();	// each EXI-receiver has its own EXIProcessor
	EXIFilter exiFilter;
	
	public EXIEncoderInterceptor(EXIFilter exiFilter) {
		this.exiFilter = exiFilter;
	}
    
	@Override
	public void interceptPacket(Packet packet, Session session, boolean incoming, boolean processed) throws PacketRejectedException {
		if(exiFilter != null){
			if(incoming && processed){
				// TODO: identificar el <setup> stanza para hacer la negociaci�n, y aceptar (marcar sesi�n como EXI) o rechazar la conexi�n EXI
				
				// se crea un nuevo EXIProcessor para esta sesi�n (identificada con el getFrom)
				if(exiFilter.newExiProcessorXsdLocation != null){	
					exiProcessors.put(packet.getFrom(), new EXIProcessor(exiFilter.newExiProcessorXsdLocation));
					//exiProcessors.put(session.getStreamID().getID(), new EXIProcessor(exiFilter.newExiProcessorXsdLocation));
					exiFilter.newExiProcessorXsdLocation = null;
				}
			}
			if(!incoming && !processed && exiProcessors.containsKey(packet.getTo())){
				// codificar
				String exi = null;
				try {
					exi = exiProcessors.get(packet.getTo()).encodeString(packet.toXML());
				} catch (IOException | EXIException | SAXException | TransformerException e) {
					e.printStackTrace();
				} 
				if(exi != null)
					session.deliverRawText(exi);
				System.out.println("XML: " + packet.toXML());
				System.out.println("EXI: " + exi);
				throw new PacketRejectedException("EXI: " + exi);
			}
			
		}
	}
	
	
}
