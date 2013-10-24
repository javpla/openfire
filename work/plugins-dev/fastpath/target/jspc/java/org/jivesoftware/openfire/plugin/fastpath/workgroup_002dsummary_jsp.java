package org.jivesoftware.openfire.plugin.fastpath;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.jivesoftware.util.*;
import java.util.Iterator;
import org.jivesoftware.xmpp.workgroup.WorkgroupResultFilter;
import org.jivesoftware.xmpp.workgroup.Workgroup;
import org.jivesoftware.xmpp.workgroup.WorkgroupAdminManager;
import org.jivesoftware.openfire.muc.MultiUserChatService;
import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.cluster.ClusterManager;
import org.jivesoftware.util.cache.CacheFactory;
import org.jivesoftware.openfire.container.GetAdminConsoleInfoTask;
import org.jivesoftware.util.StringUtils;
import org.jivesoftware.util.Base64;
import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.auth.AuthToken;

public final class workgroup_002dsummary_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/check-cluster.jspf");
  }

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

      out.write("\n\n\n\n<!-- Define Administration Bean -->\n");

    WorkgroupAdminManager webManager = new WorkgroupAdminManager();
    webManager.init(pageContext);

      out.write('\n');
      out.write('\n');

    // Get muc server
    MultiUserChatService mucService = null;
    for (MultiUserChatService service : webManager.getMultiUserChatManager().getMultiUserChatServices()) {
        if (!service.isRoomCreationRestricted()) {
            mucService = service;
        }
    }


      out.write('\n');
      out.write("\n\n\n\n\n\n\n");

    if (!ClusterManager.isSeniorClusterMember()) {

        GetAdminConsoleInfoTask info = (GetAdminConsoleInfoTask) CacheFactory.doSynchronousClusterTask(
                new GetAdminConsoleInfoTask(), ClusterManager.getSeniorClusterMember().toByteArray());

        AuthToken authToken = (AuthToken) session.getAttribute("jive.admin.authToken");

        if (info != null && authToken != null && info.getBindInterface() != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(request.isSecure() ? "https" : "http");
            sb.append("://");
            sb.append(info.getBindInterface());
            sb.append(":");
            sb.append(request.isSecure() ? info.getAdminSecurePort() : info.getAdminPort());
            sb.append("/login.jsp?login=true&username=").append(authToken.getUsername());
            sb.append("&secret=").append(StringUtils.hash(info.getAdminSecret()));
            sb.append("&nodeID=")
                    .append(Base64.encodeBytes(XMPPServer.getInstance().getNodeID().toByteArray(), Base64.URL_SAFE));
            sb.append("&url=/plugins/fastpath/workgroup-summary.jsp");

            response.sendRedirect(sb.toString());
            return;
        }
    }

      out.write("\n<html>\n    <head>\n        <title>Workgroup Summary</title>\n        <meta name=\"pageID\" content=\"workgroup-summary\"/>\n        <!--<meta name=\"helpPage\" content=\"get_around_in_the_admin_console.html\"/>-->\n    </head>\n    <body>\n    <style type=\"text/css\">\n        @import \"style/style.css\";\n    </style>\n");
 if(mucService == null){ 
      out.write("\n    <div class=\"warning\">\n        Fastpath needs a Group Conference service set up so rooms can be created on the server without restriction. Please set up a Group Conference service with permissions <a href=\"/muc-service-summary.jsp\">here</a>.\n    </div>\n\n");
 } 
      out.write('\n');
      out.write('\n');
      out.write('\n');

    boolean deleted = ParamUtils.getParameter(request, "deleted") != null;

      out.write('\n');
 if(deleted){
      out.write("\n    <div class=\"success\">\n       Workgroup has been deleted!\n     </div><br>\n");
 } 
      out.write('\n');

    int start = ParamUtils.getIntParameter(request, "start", 0);
    int range = ParamUtils.getIntParameter(request, "range", 15);
    webManager.setStart(start);
    webManager.setRange(range);

    int numPages = (int)Math.ceil((double)webManager.getWorkgroupManager().getWorkgroupCount()/(double)range);
    int curPage = (start/range) + 1;

      out.write("\n<table cellpadding=\"3\" cellspacing=\"0\" border=\"0\">\n  <tr>\n    <td colspan=\"8\">\nBelow is the list of workgroups in the system. A workgroup is an alias for contacting a group of members and is made up of one or more queues.</td>\n  </tr>\n</table>\n<table cellpadding=\"3\" cellspacing=\"0\" border=\"0\">\n  <tr>\n    <td colspan=\"8\" class=\"text\">Total Workgroups: ");
      out.print( webManager.getWorkgroupManager().getWorkgroupCount());
      out.write(".\n     ");
 if(webManager.getNumPages() > 1) { 
      out.write("\n        Showing ");
      out.print( webManager.getStart() + 1);
      out.write(' ');
      out.write('-');
      out.write(' ');
      out.print( webManager.getStart() + webManager.getRange());
      out.write("\n      ");
 } 
      out.write("\n      <br/><br/>\n      ");
 if(webManager.getNumPages() > 1){ 
      out.write("\n        <table border=\"0\" cellpadding=\"3\" cellspacing=\"0\">\n        <tr>\n          <td colspan=\"8\" class=\"text\">Pages: [\n            ");
   for (int pageIndex=0; pageIndex<numPages; pageIndex++) {

            String sep = ((pageIndex+1)<numPages) ? " " : "";

            boolean isCurrent = (pageIndex+1) == curPage;

    
      out.write("\n            <a href=\"workgroup-summary.jsp?start=");
      out.print( (pageIndex*range) );
      out.write("\" class=\"");
      out.print( ((isCurrent) ? "jive-current" : "") );
      out.write("\">\n              ");
      out.print(  (pageIndex+1) );
      out.write("\n            </a>\n            ");
      out.print(  sep );
      out.write("\n            ");
   } 
      out.write("]\n          </td>\n        </tr>\n        </table>\n      ");
 } 
      out.write("\n    </td>\n  </tr>\n</table>\n<table class=\"jive-table\" cellpadding=\"3\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n  <thead>\n    <tr>\n      <th nowrap align=\"left\" colspan=\"2\">Name</th>\n      <th nowrap>Status</th>\n      <th nowrap>Members (Active/Total) </th>\n      <th nowrap>Queues</th>\n      <th nowrap>Users in Queues</th>\n      <th nowrap>Edit</th>\n      <th nowrap>Delete</th>\n    </tr>\n  </thead>\n    ");
   // Print the list of workgroups

    WorkgroupResultFilter filter = new WorkgroupResultFilter();

    filter.setStartIndex(start);

    filter.setNumResults(range);

    Iterator workgroups = webManager.getWorkgroupManager().getWorkgroups(filter);

    if (!workgroups.hasNext()) {


      out.write("\n    <tr>\n      <td align=\"center\" colspan=\"8\">\n        <br/>No workgroups --\n        <a href=\"workgroup-create.jsp\">create workgroup<a>.\n            <br/>\n            <br/>\n          </a>\n        </a>\n      </td>\n    </tr>\n    ");


    }

    int i = start;

    while (workgroups.hasNext()) {

        Workgroup workgroup = (Workgroup)workgroups.next();

        i++;


      out.write("\n    <tr class=\"c1\">\n      <td width=\"39%\" colspan=\"2\">\n        <a href=\"workgroup-queues.jsp?wgID=");
      out.print( workgroup.getJID().toString() );
      out.write("\">\n            <b>");
      out.print(  workgroup.getJID().getNode() );
      out.write("</b>\n          </a>\n        ");
   if (workgroup.getDescription() != null) { 
      out.write("\n        <span class=\"jive-description\">\n          <br/>\n          ");
      out.print(  workgroup.getDescription() );
      out.write("\n        </span>\n        ");
   } 
      out.write("\n      </td>\n      <td width=\"10%\" align=\"center\" nowrap>\n      <table>\n         <tr>\n             <td width=\"14\">\n                 ");
   if (workgroup.getStatus() == Workgroup.Status.OPEN) { 
      out.write("\n                 <img src=\"images/bullet-green-14x14.gif\" width=\"14\" height=\"14\" border=\"0\" title=\"Workgroup is currently open, active and accepting requests.\" alt=\"\"/>\n                 </td><td nowrap>Open\n                     ");
   } else if (workgroup.getStatus() == Workgroup.Status.READY) { 
      out.write("\n                 <img src=\"images/bullet-yellow-14x14.gif\" width=\"14\" height=\"14\" border=\"0\" title=\"Workgroup is currently ready to open when a member is available.\" alt=\"\"/>\n                 </td><td nowrap>Waiting for member\n                         ");
   } else { 
      out.write("\n                 <img src=\"images/bullet-red-14x14.gif\" width=\"14\" height=\"14\" border=\"0\" title=\"Workgroup is currently closed.\" alt=\"\"/>\n                 </td><td nowrap>Closed\n                 ");
   } 
      out.write("\n         </td>\n        </tr></table>\n      </td>\n      <td width=\"10%\" align=\"center\">\n        <a href=\"workgroup-agents-status.jsp?wgID=");
      out.print( workgroup.getJID().toString() );
      out.write("\">\n          ");
      out.print(  webManager.getActiveAgentMemberCount(workgroup) );
      out.write('/');
      out.print(  webManager.getAgentsInWorkgroup(workgroup).size() );
      out.write("\n        </a>\n      </td>\n      <td width=\"10%\" align=\"center\">\n        ");
      out.print(  workgroup.getRequestQueueCount() );
      out.write("\n      </td>\n      <td width=\"10%\" align=\"center\">\n        ");
      out.print(  webManager.getWaitingCustomerCount(workgroup) );
      out.write("\n      </td>\n      <td width=\"10%\" align=\"center\">\n        <a href=\"workgroup-queues.jsp?wgID=");
      out.print( workgroup.getJID().toString() );
      out.write("\" title=\"Click to edit...\">\n          <img src=\"images/edit-16x16.gif\" width=\"16\" height=\"16\" border=\"0\" alt=\"\"/>\n        </a>\n      </td>\n      <td width=\"10%\" align=\"center\">\n        <a href=\"workgroup-delete.jsp?wgID=");
      out.print( workgroup.getJID().toString() );
      out.write("\" title=\"Click to delete...\">\n          <img src=\"images/delete-16x16.gif\" width=\"16\" height=\"16\" border=\"0\" alt=\"\"/>\n        </a>\n      </td>\n    </tr>\n    ");


    }


      out.write("\n  </thead>\n</table>\n");
 if(numPages > 1){ 
      out.write("\n  <p>Pages: [\n    ");
   for (int pageIndex=0; pageIndex<numPages; pageIndex++) {

            String sep = ((pageIndex+1)<numPages) ? " " : "";

            boolean isCurrent = (pageIndex+1) == curPage;

    
      out.write("\n    <a href=\"workgroup-summary.jsp?start=");
      out.print( (pageIndex*range) );
      out.write("\" class=\"");
      out.print( ((isCurrent) ? "jive-current" : "") );
      out.write("\">\n      ");
      out.print(  (pageIndex+1) );
      out.write("\n    </a>\n    ");
      out.print(  sep );
      out.write("\n    ");
   } 
      out.write("]\n  </p>\n");
 } 
      out.write("\n\n    </body>\n</html>");
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
