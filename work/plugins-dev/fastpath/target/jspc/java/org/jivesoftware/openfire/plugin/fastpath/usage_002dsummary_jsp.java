package org.jivesoftware.openfire.plugin.fastpath;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.jivesoftware.xmpp.workgroup.Workgroup;
import org.jivesoftware.xmpp.workgroup.WorkgroupManager;
import org.jivesoftware.xmpp.workgroup.spi.ChatHistoryUtils;
import org.jivesoftware.xmpp.workgroup.utils.ModelUtil;
import org.jivesoftware.util.Log;
import org.jivesoftware.util.ParamUtils;
import org.xmpp.packet.JID;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class usage_002dsummary_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write('\n');
      out.write('\n');
      out.write('\n');

    WorkgroupManager wgManager = WorkgroupManager.getInstance();

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


      out.write("\n\n<html>\n<head>\n    <title>Usage Summary</title>\n    <meta name=\"pageID\" content=\"usage-summary\"/>\n    <style type=\"text/css\">@import url( /js/jscalendar/calendar-win2k-cold-1.css );</style>\n    <script type=\"text/javascript\" src=\"/js/jscalendar/calendar.js\"></script>\n    <script type=\"text/javascript\" src=\"/js/jscalendar/i18n.jsp\"></script>\n    <script type=\"text/javascript\" src=\"/js/jscalendar/calendar-setup.js\"></script>\n    <!--<meta name=\"helpPage\" content=\"view_workgroup_usage_reports.html\"/>-->\n\n    <style type=\"text/css\">\n        .textfield {\n            font-size: 11px;\n            font-family: verdana;\n            padding: 3px 2px;\n            background: #efefef;\n        }\n\n        .text {\n            font-size: 11px;\n            font-family: verdana;\n        }\n    </style>\n</head>\n\n<body>\n<style type=\"text/css\">\n    @import \"style/style.css\";\n</style>\n");
 if(errors){ 
      out.write("\n<div class=\"error\">\n    ");
      out.print( errorMessage);
      out.write("\n</div>\n");
 } 
      out.write("\n\n<p>\n    <span class=\"jive-description\">This reports shows historical information on overall usage for all Workgroups.\n    </span>\n</p>\n\n<div  class=\"jive-contentBox\">\n      <h4>Overall Usage Summary</h4>\n    <table class=\"box\" cellpadding=\"3\" cellspacing=\"1\" border=\"0\">\n\n\n        <tr>\n            <td width=\"1%\" class=\"text\" nowrap>\n                Total number of users entering chat queues:\n            </td>\n            <td class=\"text\">\n                ");
      out.print( ChatHistoryUtils.getTotalRequestCountForSystem() );
      out.write("\n            </td>\n        </tr>\n        <tr>\n            <td width=\"1%\" class=\"text\" nowrap>\n                Number of users served by agents:\n            </td>\n            <td class=\"text\">\n                ");
      out.print( ChatHistoryUtils.getTotalChatsInSystem() );
      out.write("\n            </td>\n        </tr>\n        <tr>\n            <td width=\"1%\" class=\"text\" nowrap>\n                Percentage of users served by an agent:\n            </td>\n            <td class=\"text\">\n                ");

                    int totalRequests = ChatHistoryUtils.getTotalRequestCountForSystem();
                    int totalChats = ChatHistoryUtils.getTotalChatsInSystem();
                    DecimalFormat format = new DecimalFormat(".00");
                    double per = (double)totalChats / totalRequests * 100;
                    if (totalChats == 0 || totalRequests == 0) {
                        out.println("Not Available");
                    }
                    else {
                        String percentage = format.format((double)totalChats / totalRequests * 100);
                        out.println(percentage + "%");
                    }
                
      out.write("\n            </td>\n        </tr>\n        <tr>\n            <td width=\"1%\" class=\"text\" nowrap>\n                Average user wait time prior to being served:\n            </td>\n            <td class=\"text\">\n                ");
      out.print( ChatHistoryUtils.getAverageWaitTimeForServer() );
      out.write("\n            </td>\n        </tr>\n        <tr>\n            <td width=\"1%\" class=\"text\" nowrap>\n                Average length of a user chat session:\n            </td>\n            <td class=\"text\">\n                ");
      out.print( ChatHistoryUtils.getDateFromLong(ChatHistoryUtils.getAverageChatLengthForServer()) );
      out.write("\n            </td>\n        </tr>\n        <tr>\n            <td width=\"1%\" class=\"text\" nowrap>\n                Total length of all user chat sessions:\n            </td>\n            <td class=\"text\">\n                ");
      out.print( ChatHistoryUtils.getDateFromLong(ChatHistoryUtils.getTotalTimeForAllChatsInServer()) );
      out.write("\n            </td>\n        </tr>\n\n    </table>\n</div>\n<br/>\n\n<form name=\"workgroupForm\" method=\"post\" action=\"usage-summary.jsp\">\n<div  class=\"jive-contentBox\">\n      <h4>Workgroup Summaries</h4>\n<table class=\"box\" cellpadding=\"3\" cellspacing=\"1\" border=\"0\">\n<tr>\n    <td width=\"1%\" nowrap class=\"text\">\n        Select Workgroup:\n    </td>\n    <td class=\"text\" width=\"1%\" nowrap>\n        <select name=\"workgroupBox\">\n            ");

                String wgroup = request.getParameter("workgroupBox");
                for (Workgroup w : wgManager.getWorkgroups()) {
                    String selectionID = "";
                    if (wgroup != null && wgroup.equals(w.getJID().toString())) {
                        selectionID = "selected";
                    }
            
      out.write("\n            <option value=\"");
      out.print( w.getJID().toString() );
      out.write('"');
      out.write(' ');
      out.print( selectionID );
      out.write(">\n                ");
      out.print( w.getJID().toString() );
      out.write("\n            </option>\n            ");

                }
            
      out.write("\n        </select>\n\n\n    </td>\n</tr>\n<tr>\n<td width=\"1%\" nowrap class=\"text\">\n    Choose Date\n</td>\n    <td width=\"1%\" class=\"text\" nowrap>\n        <!-- Start of Date -->\n        <TABLE border=\"0\">\n            <tr valign=\"top\">\n                <td width=\"1%\" nowrap class=\"text\">\n                    From:\n                </td>\n                <td width=\"1%\" nowrap class=\"text\"><input type=\"text\" name=\"startDate\" id=\"startDate\" size=\"15\" value=\"");
      out.print( start != null ? start : "");
      out.write("\"/><br/>\n                    Use mm/dd/yy</td>\n                <td width=\"1%\" nowrap>&nbsp;<img src=\"images/icon_calendarpicker.gif\" vspace=\"3\" id=\"startDateTrigger\"></td>\n\n\n                <TD width=\"1%\" nowrap class=\"text\">\n                    To:\n                </td>\n                <td width=\"1%\" nowrap class=\"text\"><input type=\"text\" name=\"endDate\" id=\"endDate\" size=\"15\" value=\"");
      out.print( end != null ? end : "" );
      out.write("\"/><br/>\n                    Use mm/dd/yy</td>\n                <td>&nbsp;<img src=\"images/icon_calendarpicker.gif\" vspace=\"3\" id=\"endDateTrigger\"></td>\n            </TR>\n        </TABLE>\n        <!-- End Of Date -->\n    </td>\n</tr>\n</table>\n<!-- End Of Date -->\n<table class=\"box\"  width=\"500\">\n<tr>\n    <td width=\"1%\" colspan=\"2\">\n    <input type=\"submit\" name=\"submit\" value=\"View Statistics\"/>\n</td>\n</tr>\n");

    String workgroupName = ParamUtils.getParameter(request, "workgroupBox");

      out.write("\n\n\n\n");
 if (ModelUtil.hasLength(workgroupName) && !errors) { 
      out.write('\n');
      out.write('\n');
      out.write('\n');

    if (workgroupName != null) {
        final Workgroup g = wgManager.getWorkgroup(new JID(workgroupName));
        String name = g.getJID().toString();

      out.write("\n<tr>\n     <td width=\"1%\" class=\"text\" nowrap colspan=\"2\">Usage Summary for <b>");
      out.print( name );
      out.write("</b> between ");
      out.print( start);
      out.write(" and ");
      out.print( end);
      out.write("<br/><br/></td>\n</tr>\n<tr>\n\n      <td width=\"1%\" class=\"text\" nowrap> Total number of users entering chat queues:</td>\n     <td width=\"1%\" class=\"text\" nowrap>\n        ");
      out.print( ChatHistoryUtils.getNumberOfRequestsForWorkgroup(name, startDate, endDate) );
      out.write("\n    </td>\n</tr>\n<tr>\n      <td width=\"1%\" class=\"text\" nowrap>Number of chat users served by agents:</td>\n     <td width=\"1%\" class=\"text\" nowrap>\n        ");
      out.print( ChatHistoryUtils.getNumberOfChatsAccepted(name, startDate, endDate) );
      out.write("\n    </td>\n</tr>\n<tr>\n      <td width=\"1%\" class=\"text\" nowrap>Number of users cancelling request</td>\n      <td width=\"1%\" class=\"text\" nowrap>\n        ");
      out.print( ChatHistoryUtils.getNumberOfRequestsCancelledByUser(name, startDate, endDate) );
      out.write("\n    </td>\n</tr><tr>\n      <td width=\"1%\" class=\"text\" nowrap>Number of users never picked up by an agent:</td>\n      <td width=\"1%\" class=\"text\" nowrap>\n        ");
      out.print( ChatHistoryUtils.getNumberOfRequestsNeverPickedUp(name, startDate, endDate) );
      out.write("\n    </td>\n</tr>\n\n<tr>\n     <td width=\"1%\" class=\"text\" nowrap>Average user wait time prior to being served</td>\n     <td width=\"1%\" class=\"text\" nowrap>\n        ");
      out.print( ChatHistoryUtils.getDateFromLong(ChatHistoryUtils.getAverageWaitTimeForWorkgroup(name, startDate, endDate)) );
      out.write("\n    </td>\n</tr><tr>\n     <td width=\"1%\" class=\"text\" nowrap>\n        Total length of all customer chat sessions:</td>\n     <td width=\"1%\" class=\"text\" nowrap>\n        ");
      out.print( ChatHistoryUtils.getDateFromLong(ChatHistoryUtils.getTotalChatTimeForWorkgroup(name)));
      out.write("\n    </td>\n</tr>\n");
 } 
      out.write('\n');
 } 
      out.write("\n</table>\n</div>\n</form>\n\n<script type=\"text/javascript\">\n    function catcalc(cal) {\n        var endDateField = $('endDate');\n        var startDateField = $('startDate');\n\n        var endTime = new Date(endDateField.value);\n        var startTime = new Date(startDateField.value);\n        if (endTime.getTime() < startTime.getTime()) {\n            alert(\"Dates do not match\");\n            startDateField.value = \"\";\n            endDateField.value= \"\";\n        }\n    }\n\n    Calendar.setup(\n    {\n        inputField  : \"startDate\",         // ID of the input field\n        ifFormat    : \"%m/%d/%y\",    // the date format\n        button      : \"startDateTrigger\",       // ID of the button\n        onUpdate    :  catcalc\n    });\n\n    Calendar.setup(\n    {\n        inputField  : \"endDate\",         // ID of the input field\n        ifFormat    : \"%m/%d/%y\",    // the date format\n        button      : \"endDateTrigger\",       // ID of the button\n        onUpdate    :  catcalc\n    });\n</script>\n</body>\n</html>\n\n");
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
