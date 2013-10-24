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
import org.xmpp.packet.Presence;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public final class workgroup_002dagents_002dstatus_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      			"/error.jsp", true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\n\n\n\n\n");

    String wgID = ParamUtils.getParameter(request, "wgID");
    int start = ParamUtils.getIntParameter(request, "start", 0);
    int range = ParamUtils.getIntParameter(request, "range", 15);
    String filter = ParamUtils.getParameter(request, "filter");
    if (filter == null) {
        filter = "all";
    }

    // Load the workgroup
    final WorkgroupManager workgroupManager = WorkgroupManager.getInstance();
    Workgroup workgroup = workgroupManager.getWorkgroup(new JID(wgID));

    Collection<Agent> agents = new ArrayList<Agent>(workgroup.getAgents());
    if ("connected".equals(filter)) {
        for (Agent agent : agents) {
            agent.getAgentSession();
        }
    }
    else if ("available".equals(filter)) {
        for (Iterator<Agent> it=agents.iterator(); it.hasNext();) {
            AgentSession agentSession = it.next().getAgentSession();
            if (!agentSession.isAvailableToChat()) {
                it.remove();
            }
        }
    }

    int numPages = (int)Math.ceil((double)agents.size()/(double)range);
    int curPage = (start/range) + 1;

      out.write("\n<html>\n    <head>\n        <title>Status of Agents</title>\n        <meta name=\"pageID\" content=\"workgroup-summary\"/>\n    </head>\n    <body>\n<table cellpadding=\"3\" cellspacing=\"0\" border=\"0\">\n  <tr>\n    <td colspan=\"8\">\nBelow is a list of agents related to the workgroup <a href=\"workgroup-properties.jsp?wgID=");
      out.print( workgroup.getJID().toString() );
      out.write('"');
      out.write('>');
      out.write('\n');
      out.print(  workgroup.getJID().getNode() );
      out.write("</a>. The list includes the status of the agent and the number of chats that the agent is having at the moment.</td>\n  </tr>\n</table>\n<table cellpadding=\"3\" cellspacing=\"0\" border=\"0\">\n  <tr>\n    <td colspan=\"8\" class=\"text\">\n    <table cellpadding=\"3\" cellspacing=\"0\" border=\"0\">\n    <tr>\n        <td>Total Agents: <a href=\"workgroup-agents-status.jsp?wgID=");
      out.print( workgroup.getJID().toString() );
      out.write("\">\n            ");
 if ("all".equals(filter)) { 
      out.write("\n                <b>");
      out.print( workgroup.getAgents().size());
      out.write("</b>\n            ");
 } else { 
      out.write("\n               ");
      out.print( workgroup.getAgents().size());
      out.write("\n            ");
 } 
      out.write("</a>\n        </td>\n        <td>Connected: <a href=\"workgroup-agents-status.jsp?wgID=");
      out.print( workgroup.getJID().toString() );
      out.write("&filter=connected\">\n            ");
 if ("connected".equals(filter)) { 
      out.write("\n                <b>");
      out.print( workgroup.getAgentSessions().size());
      out.write("</b>\n            ");
 } else { 
      out.write("\n                ");
      out.print( workgroup.getAgentSessions().size());
      out.write("\n            ");
 } 
      out.write("</a>\n        </td>\n        <td>Available: <a href=\"workgroup-agents-status.jsp?wgID=");
      out.print( workgroup.getJID().toString() );
      out.write("&filter=available\">\n            ");
 if ("available".equals(filter)) { 
      out.write("\n                <b>");
      out.print( workgroup.getAgentAvailableSessions().size());
      out.write("</b>\n            ");
 } else { 
      out.write("\n                ");
      out.print( workgroup.getAgentAvailableSessions().size());
      out.write("\n            ");
 } 
      out.write("</a>\n        </td>\n    </tr>\n    ");
  if (numPages > 1) { 
      out.write("\n    <tr>\n        Showing:\n        [\n        ");
  for (int i=0; i<numPages; i++) {
                String sep = ((i+1)<numPages) ? " " : "";
                boolean isCurrent = (i+1) == curPage;
        
      out.write("\n            <a href=\"workgroup-agents-status.jsp?start=");
      out.print( (i*range) );
      out.write("&wgID=");
      out.print( workgroup.getJID().toString() );
      out.write("\"\n             class=\"");
      out.print( ((isCurrent) ? "jive-current" : "") );
      out.write("\"\n             >");
      out.print( (i+1) );
      out.write("</a>");
      out.print( sep );
      out.write("\n\n        ");
  } 
      out.write("\n        ]\n    </tr>\n    ");
  } 
      out.write("\n    </table>\n    </td>\n  </tr>\n</table>\n<table class=\"jive-table\" cellpadding=\"3\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n  <thead>\n    <tr>\n      <th nowrap align=\"left\" colspan=\"2\">Agent</th>\n      <th nowrap align=\"left\">Nickname</th>\n      <th nowrap colspan=\"2\">Status</th>\n      <th nowrap>Current chats</th>\n      <th nowrap>Max chats</th>\n    </tr>\n  </thead>\n    ");
   // Print the list of agents
    if (agents.size() == 0) {


      out.write("\n    <tr>\n      <td align=\"center\" colspan=\"8\">\n        <br/>No agents found\n      </td>\n    </tr>\n    ");


    }

    int i = start;

    int stop = i + range;

    int counter = 0;
    for (Agent agent : agents) {
        AgentSession agentSession = agent.getAgentSession();

        counter++;
        if(counter < i){
            continue;
        }

        if(counter == stop){
            break;
        }
        i++;


      out.write("\n    <tr class=\"c1\">\n      <td width=\"1%\">\n        ");
      out.print(  i );
      out.write(".\n      </td>\n      <td>\n        ");
      out.print(  agent.getAgentJID() );
      out.write("\n      </td>\n      <td>\n       ");
   if (agent.getNickname() != null) { 
      out.write("\n          ");
      out.print(  agent.getNickname() );
      out.write("\n        ");
   } 
      out.write("\n      </td>\n        ");
 if (agentSession == null) { 
      out.write("\n        <td width=\"1%\"\n            ><img src=\"images/bullet-clear-16x16.png\" border=\"0\" title=\"Not Available\" alt=\"\"\n            ></td>\n        <td width=\"46%\">\n            Not Available\n        </td>\n        ");

        } else {
            Presence.Show _show = agentSession.getPresence().getShow();
            String _stat = agentSession.getPresence().getStatus();
            if (_show == Presence.Show.away) {
        
      out.write("\n        <td width=\"1%\"\n            ><img src=\"images/bullet-yellow-14x14.gif\" width=\"14\" height=\"14\" border=\"0\" title=\"Away\" alt=\"\"\n            ></td>\n        <td width=\"46%\">\n            ");
  if (_stat != null) { 
      out.write("\n\n                ");
      out.print( _stat );
      out.write("\n\n            ");
  } else { 
      out.write("\n\n                Away\n\n            ");
  } 
      out.write("\n        </td>\n\n    ");
  } else if (_show == Presence.Show.chat) { 
      out.write("\n\n        <td width=\"1%\"\n            ><img src=\"images/bullet-green-14x14.gif\" width=\"14\" height=\"14\" border=\"0\" title=\"Available to Chat\" alt=\"\"\n            ></td>\n        <td width=\"46%\">\n            Available to Chat\n        </td>\n\n    ");
  } else if (_show == Presence.Show.dnd) { 
      out.write("\n\n        <td width=\"1%\"\n            ><img src=\"images/bullet-red-14x14.gif\" width=\"14\" height=\"14\" border=\"0\" title=\"Do Not Disturb\" alt=\"\"\n            ></td>\n        <td width=\"46%\">\n            ");
  if (_stat != null) { 
      out.write("\n\n                ");
      out.print( _stat );
      out.write("\n\n            ");
  } else { 
      out.write("\n\n                Do Not Disturb\n\n            ");
  } 
      out.write("\n        </td>\n\n    ");
  } else if (_show == null) { 
      out.write("\n\n        <td width=\"1%\"\n            ><img src=\"images/bullet-green-14x14.gif\" width=\"14\" height=\"14\" border=\"0\" title=\"Online\" alt=\"\"\n            ></td>\n        <td width=\"46%\">\n            Online\n        </td>\n\n    ");
  } else if (_show == Presence.Show.xa) { 
      out.write("\n\n        <td width=\"1%\"\n            ><img src=\"images/bullet-yellow-14x14.gif\" width=\"14\" height=\"14\" border=\"0\" title=\"Extended Away\" alt=\"\"\n            ></td>\n        <td width=\"46%\">\n            ");
  if (_stat != null) { 
      out.write("\n\n                ");
      out.print( _stat );
      out.write("\n\n            ");
  } else { 
      out.write("\n\n                Extended Away\n\n            ");
  } 
      out.write("\n        </td>\n\n    ");
  } else { 
      out.write("\n\n        <td colspan=\"2\" width=\"46%\">\n            Unknown/Not Recognized\n        </td>\n\n    ");
  } } 
      out.write("\n      <td width=\"10%\" align=\"center\">\n        ");
 if (agentSession == null) { 
      out.write("\n            0\n        ");
 } else { 
      out.write("\n            <a href=\"workgroup-agent-chats.jsp?wgID=");
      out.print( workgroup.getJID().toString() );
      out.write("&agentJID=");
      out.print( agent.getAgentJID() );
      out.write('"');
      out.write('>');
      out.print(  agentSession.getCurrentChats(workgroup) );
      out.write("</a>\n        ");
 } 
      out.write("\n      </td>\n      <td width=\"10%\" align=\"center\">\n        ");
 if (agentSession != null) { 
      out.write("\n            ");
      out.print(  agentSession.getMaxChats(workgroup) );
      out.write("\n        ");
 } else { 
      out.write("\n            ");
      out.print(  workgroup.getMaxChats() );
      out.write("\n        ");
 } 
      out.write("\n      </td>\n    </tr>\n    ");
 } 
      out.write("\n</table>\n");
  if (numPages > 1) { 
      out.write("\n\n    <p>\n    Pages:\n    [\n    ");
  for (i=0; i<numPages; i++) {
            String sep = ((i+1)<numPages) ? " " : "";
            boolean isCurrent = (i+1) == curPage;
    
      out.write("\n        <a href=\"workgroup-agents-status.jsp?start=");
      out.print( (i*range) );
      out.write("&wgID=");
      out.print( workgroup.getJID().toString() );
      out.write("\"\n         class=\"");
      out.print( ((isCurrent) ? "jive-current" : "") );
      out.write("\"\n         >");
      out.print( (i+1) );
      out.write("</a>");
      out.print( sep );
      out.write("\n\n    ");
  } 
      out.write("\n    ]\n    </p>\n\n");
  } 
      out.write("\n\n</body>\n</html>\n");
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
