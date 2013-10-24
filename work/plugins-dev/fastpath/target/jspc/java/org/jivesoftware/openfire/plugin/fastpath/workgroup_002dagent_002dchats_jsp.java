package org.jivesoftware.openfire.plugin.fastpath;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.jivesoftware.xmpp.workgroup.Agent;
import org.jivesoftware.xmpp.workgroup.AgentSession;
import org.jivesoftware.xmpp.workgroup.Workgroup;
import org.jivesoftware.xmpp.workgroup.WorkgroupManager;
import org.jivesoftware.util.ParamUtils;
import org.xmpp.packet.JID;
import org.xmpp.packet.Message;
import org.xmpp.packet.Packet;
import java.text.DateFormat;
import java.util.*;
import org.jivesoftware.util.StringUtils;

public final class workgroup_002dagent_002dchats_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

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

      out.write("\n\n\n\n\n\n<!-- Define Administration Bean -->\n");

    String wgID = ParamUtils.getParameter(request, "wgID");
    String agentJID = ParamUtils.getParameter(request, "agentJID");
    String roomID = ParamUtils.getParameter(request, "roomID");

    // Load the workgroup
    final WorkgroupManager workgroupManager = WorkgroupManager.getInstance();
    Workgroup workgroup = workgroupManager.getWorkgroup(new JID(wgID));
    Agent agent = workgroupManager.getAgentManager().getAgent(new JID(agentJID));
    AgentSession agentSession = agent.getAgentSession();
    List<AgentSession.ChatInfo> chatsInfo = new ArrayList<AgentSession.ChatInfo>(agentSession.getChatsInfo(workgroup));
    Collections.sort(chatsInfo);

    Map<Packet, java.util.Date> transcript = null;
    if (roomID != null) {
        transcript = new HashMap<Packet, java.util.Date>(workgroup.getTranscript(roomID));
    }

    DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT);

      out.write("\n<html>\n    <head>\n        <title>Current chats of Agent</title>\n        <meta name=\"pageID\" content=\"workgroup-summary\"/>\n    </head>\n    <body>\n<table cellpadding=\"3\" cellspacing=\"0\" border=\"0\">\n  <tr>\n    <td colspan=\"8\">\nBelow is a list of current chats the agent <b>");
      out.print( agentJID );
      out.write("</b> is having.</td>\n  </tr>\n</table>\n<table cellpadding=\"3\" cellspacing=\"0\" border=\"0\">\n  <tr>\n    <td colspan=\"8\" class=\"text\">\n    Total Chats: ");
      out.print( chatsInfo.size());
      out.write("\n    </td>\n  </tr>\n</table>\n<table class=\"jive-table\" cellpadding=\"3\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n  <thead>\n    <tr>\n      <th nowrap align=\"left\">Date</th>\n      <th nowrap>User ID</th>\n      <th nowrap>User JID</th>\n      <th nowrap>Room ID</th>\n      <th nowrap>Messages</th>\n    </tr>\n  </thead>\n    ");
   // Print the list of chats
    if (chatsInfo.size() == 0) {

      out.write("\n    <tr>\n      <td align=\"center\" colspan=\"8\">\n        <br/>Agent is not having chats at the moment\n      </td>\n    </tr>\n    ");

    }

    for (AgentSession.ChatInfo chatInfo : chatsInfo) {

      out.write("\n    <tr class=\"c1\">\n      <td width=\"30%\">\n        ");
      out.print( formatter.format(chatInfo.getDate()) );
      out.write("\n      </td>\n      <td width=\"20%\" align=\"center\">\n        ");
      out.print( chatInfo.getUserID());
      out.write("</td>\n      <td width=\"20%\" align=\"center\">\n        ");
      out.print( chatInfo.getUserJID().toString() );
      out.write("</td>\n      <td width=\"20%\" align=\"center\">\n        ");
      out.print( chatInfo.getSessionID() );
      out.write("\n      </td>\n      <td width=\"10%\" align=\"center\">\n        ");
 int count = 0;
        for (Packet packet : chatInfo.getPackets().keySet()) {
            if (packet instanceof Message) {
                count++;
            }
        }
      out.write("\n        <a href=\"workgroup-agent-chats.jsp?wgID=");
      out.print( workgroup.getJID().toString() );
      out.write("&agentJID=");
      out.print( agent.getAgentJID() );
      out.write("&roomID=");
      out.print( chatInfo.getSessionID());
      out.write('"');
      out.write('>');
      out.print( count );
      out.write("</a>\n      </td>\n    </tr>\n    ");
 } 
      out.write("\n  </thead>\n</table>\n\n\n");
 if (transcript != null) { 
      out.write("\n<br>\n<table cellpadding=\"3\" cellspacing=\"0\" border=\"0\">\n  <tr>\n    <td colspan=\"8\">Below is the chat transcript of the room <b>");
      out.print( roomID );
      out.write("</b>.</td>\n  </tr>\n</table>\n<table class=\"jive-table\" cellpadding=\"3\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n  <thead>\n    <tr>\n      <th nowrap align=\"left\">Date</th>\n      <th nowrap>Sender</th>\n      <th nowrap>Message</th>\n    </tr>\n  </thead>\n    ");
   // Print the list of chats
    if (transcript.size() == 0) {

      out.write("\n    <tr>\n      <td align=\"center\" colspan=\"8\">\n        <br/>No messages in the room where found\n      </td>\n    </tr>\n    ");
 }

    SortedMap<Date, Packet> sortedTranscript = new TreeMap<Date, Packet>();
    for (Packet packet : transcript.keySet()) {
        sortedTranscript.put(transcript.get(packet), packet);
    }

    for (Date date : sortedTranscript.keySet()) {
        Packet packet = sortedTranscript.get(date);
        if (!(packet instanceof Message)) {
            continue;
        }

      out.write("\n    <tr class=\"c1\">\n      <td width=\"20%\">\n        ");
      out.print( formatter.format(date) );
      out.write("\n      </td>\n      <td width=\"10%\" align=\"center\">\n        ");
      out.print( StringUtils.escapeForXML(packet.getFrom().getResource()) );
      out.write("</td>\n      <td width=\"70%\" align=\"center\">\n        ");
      out.print( StringUtils.escapeForXML(((Message)packet).getBody()) );
      out.write("</td>\n    </tr>\n    ");
 } 
      out.write("\n  </thead>\n</table>\n\n");
 } 
      out.write("\n</body>\n</html>\n");
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
