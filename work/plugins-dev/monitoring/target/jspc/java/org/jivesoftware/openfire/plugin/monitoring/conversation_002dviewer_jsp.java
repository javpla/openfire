package org.jivesoftware.openfire.plugin.monitoring;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.jivesoftware.openfire.plugin.MonitoringPlugin;
import org.jivesoftware.openfire.archive.ArchivedMessage;
import org.jivesoftware.openfire.archive.Conversation;
import org.jivesoftware.openfire.archive.ConversationManager;
import org.jivesoftware.openfire.XMPPServer;
import org.xmpp.packet.JID;
import java.util.HashMap;
import java.util.Map;
import org.jivesoftware.util.*;
import java.util.Collection;

public final class conversation_002dviewer_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {


     Map<String, String> colorMap = new HashMap<String, String>();


    String getColor(JID jid){
        String color = colorMap.get(jid.toBareJID());
        if(color == null){
            Log.debug("Unable to find "+jid.toBareJID()+" using "+colorMap);
        }
        return color;
    }

  private static java.util.List _jspx_dependants;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    JspFactory _jspxFactory = null;
    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;


    try {
      _jspxFactory = JspFactory.getDefaultFactory();
      response.setContentType("text/html");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\n\n\n\n\n\n\n\n\n");
      out.write('\n');

    // Get handle on the Monitoring plugin
    MonitoringPlugin plugin = (MonitoringPlugin)XMPPServer.getInstance().getPluginManager().getPlugin(
        "monitoring");

    ConversationManager conversationManager = (ConversationManager)plugin.getModule(ConversationManager.class);

    long conversationID = ParamUtils.getLongParameter(request, "conversationID", -1);

    Conversation conversation = null;
    if (conversationID > -1) {
        try {
            conversation = new Conversation(conversationManager, conversationID);
        }
        catch (NotFoundException nfe) {
            Log.error(nfe);
        }
    }

    Map<String, String> colorMap = new HashMap<String, String>();

    if (conversation != null) {
        Collection<JID> set = conversation.getParticipants();
        int count = 0;
        for (JID jid : set) {
            if (count == 0) {
                colorMap.put(jid.toBareJID(), "blue");
            }
            else {
                colorMap.put(jid.toBareJID(), "red");
            }
            count++;
        }
    }


      out.write("\n\n<html>\n<head>\n    <meta name=\"decorator\" content=\"none\"/>\n\n    <title>Conversation Viewer</title>\n\n    <style type=\"text/css\">\n\t    @import \"style/style.css\";\n    </style>\n</head>\n\n<body>\n\n");

    if (conversation != null) {

      out.write("\n\n<table width=\"100%\">\n    ");
 for (ArchivedMessage message : conversation.getMessages()) { 
      out.write("\n    <tr valign=\"top\">\n        <td width=\"1%\" nowrap class=\"jive-description\" style=\"color:");
      out.print( getColor(message.getFromJID()) );
      out.write("\">\n            [");
      out.print( JiveGlobals.formatTime(message.getSentDate()));
      out.write(']');
      out.write(' ');
      out.print( message.getFromJID().getNode());
      out.write(":</td>\n        <td><span class=\"jive-description\">");
      out.print( StringUtils.escapeHTMLTags(message.getBody()));
      out.write("</span></td>\n    </tr>\n    ");
}
      out.write("\n\n</table>\n\n");
 }
else { 
      out.write("\nNo conversation could be found.\n");
 } 
      out.write('\n');
      out.write('\n');
      out.write('\n');
      out.write("\n</body>\n</html>");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
      }
    } finally {
      if (_jspxFactory != null) _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
