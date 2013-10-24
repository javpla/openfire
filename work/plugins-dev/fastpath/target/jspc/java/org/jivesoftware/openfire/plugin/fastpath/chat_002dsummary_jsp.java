package org.jivesoftware.openfire.plugin.fastpath;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.jivesoftware.openfire.fastpath.history.AgentChatSession;
import org.jivesoftware.openfire.fastpath.history.ChatSession;
import org.jivesoftware.openfire.fastpath.history.ChatTranscriptManager;
import org.jivesoftware.xmpp.workgroup.Workgroup;
import org.jivesoftware.xmpp.workgroup.WorkgroupManager;
import org.jivesoftware.xmpp.workgroup.utils.ModelUtil;
import org.jivesoftware.database.DbConnectionManager;
import org.jivesoftware.util.Log;
import org.jivesoftware.util.ParamUtils;
import org.xmpp.packet.JID;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import org.jivesoftware.openfire.user.UserManager;
import org.jivesoftware.openfire.user.User;

public final class chat_002dsummary_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write("\n\n\n\n\n\n\n\n\n\n\n\n\n<html>\n<head>\n    <title>Chat Summary</title>\n    <meta name=\"pageID\" content=\"chat-summary\"/>\n    <style type=\"text/css\">@import url( /js/jscalendar/calendar-win2k-cold-1.css );</style>\n    <script type=\"text/javascript\" src=\"/js/jscalendar/calendar.js\"></script>\n    <script type=\"text/javascript\" src=\"/js/jscalendar/i18n.jsp\"></script>\n    <script type=\"text/javascript\" src=\"/js/jscalendar/calendar-setup.js\"></script>\n\n    <style type=\"text/css\">\n        .textfield {\n            font-size: 11px;\n            font-family: verdana;\n            padding: 3px 2px;\n            background: #efefef;\n        }\n\n        .text {\n            font-size: 11px;\n            font-family: verdana;\n        }\n    </style>\n    <!--<meta name=\"helpPage\" content=\"view_chat_transcript_reports_for_a_workgroup.html\"/>-->\n</head>\n\n<body>\n<style type=\"text/css\">\n    @import \"style/style.css\";\n</style>\n");

    // Get a workgroup manager
    WorkgroupManager wgManager = WorkgroupManager.getInstance();

      out.write('\n');
 // Get parameters //
    boolean cancel = request.getParameter("cancel") != null;
    String queueName = ParamUtils.getParameter(request, "queueName");
    if (queueName == null) {
        queueName = "Default Queue";
    }
    // Handle a cancel
    if (cancel) {
        response.sendRedirect("workgroup-summary.jsp");
        return;
    }

    final String sess = request.getParameter("sessionID");
    final String delete = request.getParameter("delete");
    if (ModelUtil.hasLength(sess) && ModelUtil.hasLength(delete)) {
        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = DbConnectionManager.getConnection();
            pstmt = con.prepareStatement("delete from fpSession where sessionID=?");
            pstmt.setString(1, sess);
            pstmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            DbConnectionManager.closeConnection(pstmt, con);
        }

        try {
            con = DbConnectionManager.getConnection();
            pstmt = con.prepareStatement("delete from fpSessionProp where sessionID=?");
            pstmt.setString(1, sess);
            pstmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            DbConnectionManager.closeConnection(pstmt, con);
        }

        try {
            con = DbConnectionManager.getConnection();
            pstmt = con.prepareStatement("delete from fpSessionMetadata where sessionID=?");
            pstmt.setString(1, sess);
            pstmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            DbConnectionManager.closeConnection(pstmt, con);
        }

        try {
            con = DbConnectionManager.getConnection();
            pstmt = con.prepareStatement("delete from fpAgentSession where sessionID=?");
            pstmt.setString(1, sess);
            pstmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            DbConnectionManager.closeConnection(pstmt, con);
        }

    }

    boolean submit = request.getParameter("submit") != null;

    boolean errors = false;
    String errorMessage = "";

    String start = request.getParameter("startDate");
    String end = request.getParameter("endDate");

    Date startDate = null;
    Date endDate = null;

    if (submit) {

        DateFormat formatter = new SimpleDateFormat("MM/dd/yy");

        if (start == null || "".equals(start)) {
            errors = true;
            start = "";
            errorMessage = "Please specify a valid start date.";
        }
        else {
            try {
                startDate = formatter.parse(start);
            }
            catch (Exception e) {
                errors = true;
                start = "";
                errorMessage = "Please specify a valid start date.";
                Log.error(e);
            }
        }

        if (end == null || "".equals(end)) {
            errors = true;
            end = "";
            errorMessage = "Please specify a valid end date.";
        }
        else {
            try {
                endDate = formatter.parse(end);
            }
            catch (Exception e) {
                errors = true;
                end = "";
                errorMessage = "Please specify a valid end date.";
                Log.error(e);
            }
        }
    }



      out.write('\n');
      out.write('\n');
 if(errors){ 
      out.write("\n<div class=\"error\">\n    ");
      out.print( errorMessage);
      out.write("\n</div>\n");
 } 
      out.write('\n');
      out.write('\n');
   if (ModelUtil.hasLength(sess) && ModelUtil.hasLength(delete)) { 
      out.write("\n<div class=\"success\">\n    Conversation has been removed.\n</div>\n");
 } 
      out.write("\n\n<p>\n   Allows for specific report retrieval of previous conversations during two specified dates.\n</p>\n\n<div  class=\"jive-contentBox\">\n<form name=\"workgroupForm\" method=\"post\" action=\"chat-summary.jsp\">\n  <h4>Chat Transcripts</h4>\n        <table cellpadding=\"3\" cellspacing=\"1\" border=\"0\">\n            <tr>\n                <td width=\"1%\" nowrap>\n                    <span class=\"text\">Select Workgroup:</span>\n                </td>\n                <td>\n                    <select name=\"workgroupBox\" class=\"text\">\n                        ");

                            String wgroup = request.getParameter("workgroupBox");
                            for (Workgroup w : wgManager.getWorkgroups()) {
                                String selectionID = "";
                                if (wgroup != null && wgroup.equals(w.getJID().toString())) {
                                    selectionID = "selected";
                                }
                        
      out.write("\n                        <option value=\"");
      out.print( w.getJID().toString() );
      out.write('"');
      out.write(' ');
      out.print( selectionID );
      out.write(">\n                            ");
      out.print( w.getJID().toString() );
      out.write("</option>\n                        ");

                            }
                        
      out.write("\n                    </select>\n                </td>\n            </tr>\n            <tr>\n                <td class=\"text\" width=\"1%\" nowrap>\n                    Choose Date:\n                </td>\n                <td nowrap>\n                    <!-- Start of Date -->\n                    <TABLE border=\"0\">\n                        <tr valign=\"top\">\n                            <td width=\"1%\" nowrap class=\"text\">\n                                From:\n                            </td>\n                            <td width=\"1%\" nowrap class=\"text\"><input type=\"text\" name=\"startDate\" id=\"startDate\" size=\"15\" value=\"");
      out.print( start != null ? start : "");
      out.write("\"/><br/>\n                            Use mm/dd/yy</td>\n                            <td width=\"1%\" nowrap>&nbsp;<img src=\"images/icon_calendarpicker.gif\" vspace=\"3\" id=\"startDateTrigger\"></td>\n\n\n                            <TD width=\"1%\" nowrap class=\"text\">\n                                To:\n                            </td>\n                            <td width=\"1%\" nowrap class=\"text\"><input type=\"text\" name=\"endDate\" id=\"endDate\" size=\"15\" value=\"");
      out.print( end != null ? end : "" );
      out.write("\"/><br/>\n                             Use mm/dd/yy</td>\n                            <td>&nbsp;<img src=\"images/icon_calendarpicker.gif\" vspace=\"3\" id=\"endDateTrigger\"></td>\n                         </TR>\n                    </TABLE>\n                    <!-- End Of Date -->\n                </td>\n            </tr>\n            <tr>\n                <td>\n                    <input type=\"submit\" name=\"submit\" value=\"View Chat Transcripts\"/>\n                </td>\n                <td align=\"left\">\n                    &nbsp;\n                </td>\n            </tr>\n        </table>\n</form>\n</div>\n");

    StringBuffer buf = new StringBuffer();
    final String workgroupName = request.getParameter("workgroupBox");

      out.write('\n');
 if (ModelUtil.hasLength(workgroupName) && !errors) { 
      out.write('\n');

    final Workgroup g = wgManager.getWorkgroup(new JID(workgroupName));

      out.write("\n<br>\n<table class=\"jive-table\"  cellspacing=\"0\" border=\"0\" width=\"100%\">\n    <th nowrap>\n        Customer\n    </th>\n    <th>\n        Agent\n    </th>\n     <th>\n        Question\n    </th>\n    <th>\n        Date/Time\n    </th>\n    <th>\n        Options\n    </th>\n    ");

        Collection list = ChatTranscriptManager.getChatSessionsForWorkgroup(g, startDate, endDate);
        Iterator citer = list.iterator();
        while (citer.hasNext()) {
            ChatSession chatSession = (ChatSession)citer
                    .next();
            if (chatSession.getStartTime() == 0) {
                continue;
            }
            String sessionID = chatSession.getSessionID();
    
      out.write("\n    <tr>\n         <td nowrap width=\"1%\" class=\"conversation-body\">\n               ");

                String email = chatSession.getEmail();
                if (email.indexOf('@') != -1) {
            
      out.write(" <a href=\"mailto:");
      out.print(email);
      out.write("\">  ");
      out.print( chatSession.getCustomerName() );
      out.write(" </a>");

        }
        else {
        
      out.write(' ');
      out.print( chatSession.getCustomerName());
      out.write(' ');

            }
        
      out.write("\n\n        </td>\n        <td nowrap>\n            ");

                AgentChatSession initial = chatSession.getFirstSession();
                if (initial == null) {
                    out.println("<font color=red>");
                    if (chatSession.getState() == 0) {
                        out.println("User left the queue.");
                    }
                    else if (chatSession.getState() == 1) {
                        out.println("No agent picked up request.");
                    }
                    else {
                        out.println("Agent never joined");
                    }
                    out.println("</font>");
                }
                else {
                    JID jid = new JID(initial.getAgentJID());
                    User user = UserManager.getInstance().getUser(jid.getNode());

                    out.println("<a href=\"/user-properties.jsp?username="+user.getName()+"\">"+user.getName()+"</a>");
                }
                final SimpleDateFormat dayFormatter = new SimpleDateFormat("MM/dd/yyyy h:mm:ss a");

                final String displayDate = dayFormatter
                    .format(new Date(chatSession.getStartTime()));
            
      out.write("\n        </td>\n        <td>\n            ");
      out.print( chatSession.getQuestion() );
      out.write("\n        </td>\n        <td nowrap>");
      out.print( displayDate  );
      out.write("\n        </td>\n        <td nowrap>\n            <a href=\"chat-conversation.jsp?sessionID=");
      out.print( sessionID );
      out.write("\">View</a>\n            <a href=\"chat-summary.jsp?");
      out.print(buf.toString() );
      out.write("&workgroupBox=");
      out.print( workgroupName);
      out.write("&delete=true&sessionID=");
      out.print(sessionID);
      out.write("&startDate=");
      out.print(start);
      out.write("&endDate=");
      out.print(end);
      out.write("&submit=true\">Delete</a>\n        </td>\n\n    </tr>\n    ");
 } 
      out.write("</table>\n");

    if (list.size() == 0) {


      out.write("\n<table class=\"jive-table\" cellpadding=\"3\" cellspacing=\"1\" border=\"0\" width=\"100%\">\n    <tr>\n        <td class=\"c1\" colspan=6>\n            <tr><td class=\"text\">No Chats have occured in this workgroup.</td></tr>\n        </td>\n    </tr>\n</table>\n");
 } 
      out.write('\n');
 } 
      out.write("\n\n\n<script type=\"text/javascript\">\n    function catcalc(cal) {\n        var endDateField = $('endDate');\n        var startDateField = $('startDate');\n\n        var endTime = new Date(endDateField.value);\n        var startTime = new Date(startDateField.value);\n        if (endTime.getTime() < startTime.getTime()) {\n            alert(\"Dates do not match\");\n            startDateField.value = \"\";\n            endDateField.value= \"\";\n        }\n    }\n\n    Calendar.setup(\n    {\n        inputField  : \"startDate\",         // ID of the input field\n        ifFormat    : \"%m/%d/%y\",    // the date format\n        button      : \"startDateTrigger\",       // ID of the button\n        onUpdate    :  catcalc\n    });\n\n    Calendar.setup(\n    {\n        inputField  : \"endDate\",         // ID of the input field\n        ifFormat    : \"%m/%d/%y\",    // the date format\n        button      : \"endDateTrigger\",       // ID of the button\n        onUpdate    :  catcalc\n    });\n</script>\n</body>\n</html>\n");
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
