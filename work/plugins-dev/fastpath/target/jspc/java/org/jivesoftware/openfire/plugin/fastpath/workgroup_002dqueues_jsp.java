package org.jivesoftware.openfire.plugin.fastpath;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.jivesoftware.xmpp.workgroup.RequestQueue;
import org.jivesoftware.xmpp.workgroup.Workgroup;
import org.jivesoftware.util.ParamUtils;
import org.jivesoftware.xmpp.workgroup.WorkgroupManager;
import org.jivesoftware.xmpp.workgroup.WorkgroupAdminManager;
import org.xmpp.packet.JID;
import org.jivesoftware.openfire.fastpath.dataforms.FormManager;
import org.jivesoftware.xmpp.workgroup.routing.RoutingManager;
import org.jivesoftware.xmpp.workgroup.routing.RoutingRule;
import org.jivesoftware.openfire.fastpath.dataforms.WorkgroupForm;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import org.jivesoftware.xmpp.workgroup.utils.ModelUtil;
import org.jivesoftware.util.StringUtils;
import org.jivesoftware.util.NotFoundException;
import org.jivesoftware.util.Log;
import org.jivesoftware.openfire.fastpath.dataforms.FormUtils;
import org.xmpp.forms.FormField;
import org.xmpp.forms.DataForm;

public final class workgroup_002dqueues_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write("\n\n\n\n");

    String wgID = ParamUtils.getParameter(request, "wgID");
    long qID = ParamUtils.getLongParameter(request, "qID", -1L);
    boolean createQueue = request.getParameter("createQueue") != null;
    String name = ParamUtils.getParameter(request, "name");
    String description = ParamUtils.getParameter(request, "description");
    boolean delete = ParamUtils.getBooleanParameter(request, "delete");

    String errorMessage = "";

      out.write('\n');
      out.write('\n');

    // Get a workgroup manager
    WorkgroupManager wgManager = WorkgroupManager.getInstance();
    WorkgroupAdminManager adminManager = new WorkgroupAdminManager();

    // If the workgroup manager is null, service is down so redirect:
    if (wgManager == null) {
        response.sendRedirect("error-serverdown.jsp");
        return;
    }

      out.write('\n');
      out.write('\n');
 // Get parameters //

    // Load the workgroup
    Workgroup workgroup = wgManager.getWorkgroup(new JID(wgID));

    if (createQueue) {
        RequestQueue queue = workgroup.createRequestQueue(name);
        queue.setDescription(description);
        response.sendRedirect("workgroup-queues.jsp?wgID=" + wgID);
        return;
    }

    if (delete) {
        RequestQueue queue = workgroup.getRequestQueue(qID);
        workgroup.deleteRequestQueue(queue);
        response.sendRedirect("workgroup-queues.jsp?wgID=" + wgID + "&deletesuccess=true");
        return;
    }

      out.write('\n');
      out.write('\n');
      out.write('\n');

    final WorkgroupManager workgroupManager = WorkgroupManager.getInstance();
    FormManager formManager = FormManager.getInstance();
    DataForm dataForm = formManager.getDataForm(workgroup);

    RoutingManager routingManager = RoutingManager.getInstance();

    WorkgroupForm form = formManager.getWebForm(workgroup);

    Collection<RoutingRule> rules = routingManager.getRoutingRules(workgroup);
    boolean errors = false;

    boolean edit = request.getParameter("edit") != null;
    int pos = ParamUtils.getIntParameter(request, "pos", -1);
    String editVariable = request.getParameter("editVariable");
    String editValue = request.getParameter("editValue");
    String editQuery = request.getParameter("editQueryField");
    boolean editAdvancedQuery = request.getParameter("editAdvancedQuery") != null;
    long editQueueID = ParamUtils.getLongParameter(request, "editQueueID", 0);


    String variable = request.getParameter("variable");
    String value = request.getParameter("value");

    if (value == null) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < 50; i++) {
            String tempValue = request.getParameter("value" + i);
            if (tempValue != null) {
                buf.append(variable + ":" + tempValue);
                if (request.getParameter("value" + (i + 1)) != null) {
                    buf.append(" AND ");
                }
            }
        }

        if (buf.length() > 0) {
            value = buf.toString();
            variable = "";
        }
    }
    long queueID = ParamUtils.getLongParameter(request, "queueID", -1);


    String query = "";

    boolean advancedBuilder = "advancedBuilder".equals(request.getParameter("selector"));
    String advancedQuery = request.getParameter("advancedQuery");

    if (edit) {
        List routers = (ArrayList)routingManager.getRoutingRules(workgroup);
        RoutingRule rule = (RoutingRule)routers.get(pos - 1);

        StringTokenizer tkn = new StringTokenizer(rule.getQuery(), ":");
        if (tkn.countTokens() == 2) {
            variable = tkn.nextToken();
            value = StringUtils.escapeForXML(tkn.nextToken());
        }
        else {
            advancedBuilder = true;
            variable = StringUtils.escapeForXML(rule.getQuery());
        }


        queueID = rule.getQueueID();
    }


    boolean handleEditForm = request.getParameter("editRule") != null;

    if (handleEditForm) {
        if (editAdvancedQuery) {
            if (!org.jivesoftware.xmpp.workgroup.utils.ModelUtil.hasLength(editQuery)) {
                errors = true;
                errorMessage = "Your query cannot be empty";
            }

            if (!errors) {
                int editPos = ParamUtils.getIntParameter(request, "editPos", -1);
                routingManager.removeRoutingRule(workgroup, editPos);

                routingManager.addRoutingRule(workgroup, editQueueID, editPos, editQuery);
            }

        }

        else {

            if (!ModelUtil.hasLength(editValue)) {
                errors = true;
                errorMessage = "Please specify a value to map to the form variable.";
            }

            if (!errors) {
                int editPos = ParamUtils.getIntParameter(request, "editPos", -1);
                routingManager.removeRoutingRule(workgroup, editPos);

                query = editVariable + ":" + editValue;
                routingManager.addRoutingRule(workgroup, editQueueID, editPos, query);
            }
        }
    }


    boolean submit = request.getParameter("submit") != null;
    if (submit) {
        if (!ModelUtil.hasLength(value) && !advancedBuilder) {
            errors = true;
            errorMessage = "Please specify a value to map to the form variable.";
        }
        else if (advancedBuilder && !ModelUtil.hasLength(advancedQuery)) {
            errors = true;
            errorMessage = "Specify a valid query.";
        }

        if (!errors) {
            // Add Rule
            if (!advancedBuilder) {
                if (variable.length() > 0) {
                    query = variable + ":" + value;
                }
                else {
                    query = value;
                }
                routingManager.addRoutingRule(workgroup, queueID, rules.size() + 1, query);
            }
            else {
                routingManager.addRoutingRule(workgroup, queueID, rules.size() + 1, advancedQuery);
            }
        }
    }


    boolean changePos = request.getParameter("changePos") != null;
    boolean remove = request.getParameter("remove") != null;


    if (changePos) {
        boolean up = request.getParameter("up") != null;
        boolean down = request.getParameter("down") != null;
        String index = request.getParameter("pos");
        int routerIndex = Integer.parseInt(index);

        RoutingRule moveUpRule = null;
        RoutingRule moveDownRule = null;
        if (up) {
            // Change selected router index to pos - 1 and
            // change pos - 1 to pos + 1 and save.
            for (RoutingRule rule : rules) {
                if (rule.getPosition() == routerIndex) {
                    moveUpRule = rule;
                }

                if (rule.getPosition() == routerIndex - 1) {
                    moveDownRule = rule;
                }
            }

            // Delete both rules and reapply
            routingManager.removeRoutingRule(workgroup, routerIndex);
            routingManager.removeRoutingRule(workgroup, routerIndex - 1);

            // Add new rules
            moveUpRule.setPosition(routerIndex - 1);
            moveDownRule.setPosition(routerIndex);

        }
        else if (down) {
            for (RoutingRule rule : rules) {
                if (rule.getPosition() == routerIndex) {
                    moveUpRule = rule;
                }

                if (rule.getPosition() == routerIndex + 1) {
                    moveDownRule = rule;
                }
            }

            // Delete both rules and reapply
            routingManager.removeRoutingRule(workgroup, routerIndex);
            routingManager.removeRoutingRule(workgroup, routerIndex + 1);

            // Add new rules
            moveUpRule.setPosition(routerIndex + 1);
            moveDownRule.setPosition(routerIndex);
        }

        routingManager.addRoutingRule(workgroup, moveUpRule.getQueueID(), moveUpRule.getPosition(),
                moveUpRule.getQuery());
        routingManager.addRoutingRule(workgroup, moveDownRule.getQueueID(),
                moveDownRule.getPosition(), moveDownRule.getQuery());
    }

    if (remove) {
        String index = request.getParameter("pos");
        int routerIndex = Integer.parseInt(index);
        routingManager.removeRoutingRule(workgroup, routerIndex);
        for (RoutingRule rule : rules) {
            if (rule.getPosition() > routerIndex) {
                routingManager
                        .updateRoutingRule(workgroup, rule.getPosition(), rule.getPosition() - 1);
            }
        }

    }


    rules = routingManager.getRoutingRules(workgroup);


      out.write("\n<html>\n<head>\n    <title>");
      out.print( "Workgroup Queues for " + wgID);
      out.write("</title>\n    <meta name=\"subPageID\" content=\"workgroup-queues\"/>\n    <meta name=\"extraParams\" content=\"");
      out.print( "wgID="+wgID );
      out.write("\"/>\n    <!--<meta name=\"helpPage\" content=\"create_a_queue.html\"/>-->\n\n    <script type=\"text/javascript\">\n        function enableDefault() {\n\n            document.getElementById('advancedField').disabled = true;\n        }\n\n        function enableAdvanced() {\n\n            document.getElementById('advancedField').disabled = false;\n        }\n\n        function updateForm(selectbox){\n          window.location.href = \"workgroup-queues.jsp?wgID=");
      out.print( wgID);
      out.write("&fElement=\"+selectbox.value;\n        }\n    </script>\n</head>\n\n<body>\n");

    boolean added = ParamUtils.getBooleanParameter(request, "queueaddsuccess");
    boolean deleted = ParamUtils.getBooleanParameter(request, "deletesuccess");


      out.write('\n');
 if (errors) { 
      out.write("\n<div class=\"error\">\n    ");
      out.print( errorMessage);
      out.write("\n</div><br/>\n");
 } 
      out.write('\n');
      out.write('\n');
  if (added) { 
      out.write("\n<div class=\"success\">\n    A new Request Queue has been added.\n</div><br>\n");
  }
else if (deleted) { 
      out.write("\n\n<div class=\"success\">\n    Request Queue has been removed.\n</div><br>\n");
  } 
      out.write('\n');
      out.write('\n');
 if (handleEditForm && !errors) { 
      out.write("\n<div class=\"success\">\n    Routing rules have been updated.\n</div>\n");
 } 
      out.write('\n');
      out.write('\n');
      out.write('\n');
 if (!errors && submit) { 
      out.write("\n<div class=\"success\">\n    New routing rule has been added.\n</div>\n");
 } 
      out.write("\n\n<p>\n    A request queue handles incoming client support requests. To add members to a queue, click on an available queue below.\n</p>\n\n<table>\n    <tr>\n        <td>\n            <a href=\"workgroup-queue-create.jsp?wgID=");
      out.print( wgID );
      out.write("\"><img src=\"/images/add-16x16.gif\" width=\"16\" height=\"16\" border=\"0\"></a>\n        </td>\n        <td>\n            <a href=\"workgroup-queue-create.jsp?wgID=");
      out.print( wgID );
      out.write("\">Add Queue</a>\n        </td>\n    </tr>\n</table>\n\n<br/>\n\n\n<table class=\"jive-table\" cellpadding=\"3\" cellspacing=\"0\" border=\"0\">\n    <tr>\n        <th nowrap align=\"left\" colspan=\"2\">Name/Description</th>\n        <th nowrap>Agents (active/total)</th>\n        <th nowrap>In Queue</th>\n        <th nowrap>Avg. Wait Time (sec)</th>\n        <th nowrap>Edit</th>\n        <th nowrap>Delete</th>\n    </tr>\n    ");

        int requestCount = workgroup.getRequestQueueCount();
        if (requestCount == 0) {
    
      out.write("\n    <tr>\n        <td colspan=\"98\">\n            No queues.\n        </td>\n    </tr>\n    ");

        }
        int i = 0;
        for (RequestQueue requestQueue : workgroup.getRequestQueues()) {
            i++;
    
      out.write("\n    <tr>\n        <td width=\"1%\" valign=\"top\" nowrap>\n            ");
      out.print( i );
      out.write(".\n        </td>\n        <td width=\"37%\">\n            <a href=\"workgroup-queue-agents.jsp?wgID=");
      out.print( wgID );
      out.write("&qID=");
      out.print( requestQueue.getID() );
      out.write("\"\n               title=\"Click to add/remove Agents and Groups.\"\n                ><b>");
      out.print( requestQueue.getName() );
      out.write("</b></a>\n\n            ");
  if (requestQueue.getDescription() != null) { 
      out.write("\n\n            <br>\n            <span class=\"jive-description\">\n                ");
      out.print( requestQueue.getDescription() );
      out.write("\n                </span>\n\n            ");
  } 
      out.write("\n        </td>\n        <td width=\"15%\" align=\"center\">\n            ");
      out.print( requestQueue.getAgentSessionList().getAvailableAgentCount() );
      out.write("\n            /\n            ");
      out.print( requestQueue.getMemberCount() );
      out.write("\n        </td>\n        <td width=\"15%\" align=\"center\">\n            ");
      out.print( requestQueue.getRequestCount() );
      out.write("\n        </td>\n        <td width=\"15%\" align=\"center\">\n            ");
      out.print( requestQueue.getAverageTime() );
      out.write("\n        </td>\n        <td width=\"1%\" align=\"center\">\n            <a href=\"workgroup-queue-agents.jsp?wgID=");
      out.print( wgID );
      out.write("&qID=");
      out.print( requestQueue.getID() );
      out.write("\"\n               title=\"Click to manage this queue...\"\n                ><img src=\"images/edit-16x16.gif\" width=\"16\" height=\"16\" border=\"0\"></a>\n        </td>\n        <td width=\"1%\" align=\"center\">\n            <a href=\"workgroup-queues.jsp?wgID=");
      out.print( wgID );
      out.write("&qID=");
      out.print( requestQueue.getID() );
      out.write("&delete=true\"\n               title=\"Click to delete this queue...\"\n               onclick=\"return confirm('Are you sure you want to delete this queue?');\"\n                ><img src=\"images/delete-16x16.gif\" width=\"16\" height=\"16\" border=\"0\"></a>\n        </td>\n    </tr>\n\n    ");
  } 
      out.write("\n\n</table>\n\n<br/><br/>\n<b>Routing Rules</b>\n<br/>\n\n<p>\n    Specify which queue to route to based on the values assigned to the form variables in the Web Chat Client.\n</p>\n\n<table class=\"jive-table\" cellspacing=\"0\" width=\"100%\">\n    <th>Order</th><th>Query</th><th>Routes to Queue</th><th>Move</th><th>Edit</th><th>Delete</th>\n\n    <tr style=\"border-left: none;\">\n\n    </tr>\n\n\n    ");
 for (RoutingRule rule : rules) {
        RequestQueue rq = null;
        try {
            rq = workgroup.getRequestQueue(rule.getQueueID());
        }
        catch (NotFoundException e) {
            Log.error(e);
        }

        // Do not show rule.
        if (rq == null) {
            for (RequestQueue q : workgroup.getRequestQueues()) {
                rq = q;
                break;
            }
        }

        int rulePosition = rule.getPosition();
    
      out.write("\n    <tr>\n        <td>");
      out.print( rule.getPosition());
      out.write(".</td>\n        <td>\n            ");
      out.print( rule.getQuery());
      out.write("\n        </td>\n        <td>\n            ");
      out.print( rq.getName() );
      out.write("\n        </td>\n        <td nowrap>\n            ");
  if ((rule.getPosition()) < rules.size()) { 
      out.write("\n            <a href=\"workgroup-queues.jsp?wgID=");
      out.print( wgID );
      out.write("&changePos=true&down=true&pos=");
      out.print( rule.getPosition() );
      out.write("\"\n                ><img src=\"images/arrow_down.gif\" width=\"16\" height=\"16\" alt=\"Move this router down.\" border=\"0\"></a>\n            ");
  } else { 
      out.write("\n            <img src=\"images/blank.gif\" width=\"16\" height=\"16\" border=\"0\" alt=\"\"/>\n            ");
  } 
      out.write("\n\n            ");
  if (rule.getPosition() != 1) { 
      out.write("\n            <a href=\"workgroup-queues.jsp?wgID=");
      out.print( wgID );
      out.write("&changePos=true&up=true&pos=");
      out.print( rule.getPosition() );
      out.write("\"\n                ><img src=\"images/arrow_up.gif\" width=\"16\" height=\"16\" alt=\"Move this router up.\" border=\"0\"></a>\n            ");
  } else { 
      out.write("\n            <img src=\"images/blank.gif\" width=\"16\" height=\"16\" border=\"0\" alt=\"\"/>\n            ");
  } 
      out.write("\n        <td align=\"center\">\n            <a href=\"workgroup-queues.jsp?edit=true&wgID=");
      out.print( wgID );
      out.write("&pos=");
      out.print( rule.getPosition() );
      out.write("\"\n                ><img src=\"images/edit-16x16.gif\" width=\"16\" height=\"16\" alt=\"Edit the properties of this Router\" border=\"0\"\n                ></a>\n        </td>\n        <td align=\"center\">\n            <a href=\"workgroup-queues.jsp?remove=true&wgID=");
      out.print( wgID );
      out.write("&pos=");
      out.print( rule.getPosition());
      out.write("\"\n                ><img src=\"images/delete-16x16.gif\" width=\"16\" height=\"16\" alt=\"Delete this Router\" border=\"0\"\n                ></a>\n        </td>\n\n    </tr>\n    ");
 } 
      out.write("\n\n\n    ");
 if (rules.size() == 0) { 
      out.write("\n    <tr>\n        <td colspan=\"7\" align=\"center\">There are no routing rules defined for this workgroup.</td>\n    </tr>\n    ");
} 
      out.write("\n\n</table>\n<br/>\n\n<div id=\"editform\" style=\"");
      out.print( edit ? "" : "display:none;" );
      out.write("\">\n    <fieldset>\n        <legend>Edit Routing Rule</legend>\n        <table cellspacing=\"0\" cellpadding=\"3\">\n            <form action=\"workgroup-queues.jsp\" method=\"post\">\n                <input type=\"hidden\" name=\"editPos\" value=\"");
      out.print( pos );
      out.write("\"/>\n                <tr>\n                    <td colspan=\"3\">\n                        Update routing rule.\n                        <br/><br/>\n                    </td>\n                </tr>\n                ");
 if (!advancedBuilder) { 
      out.write("\n                <tr>\n                    <td>\n                        Form Variable:\n                    </td>\n                    <td>\n                        <select name=\"editVariable\">\n                            ");
 for (FormField field : dataForm.getFields()) { 
      out.write("\n                            <option value=\"");
      out.print( field.getVariable());
      out.write('"');
      out.write(' ');
      out.print( field.getVariable().equals(variable) ? "selected" : "");
      out.write('>');
      out.print( field.getVariable());
      out.write("</option>\n                            ");
 } 
      out.write("\n\n                        </select>\n                    </td>\n                </tr><tr>\n                <td>Form Value:</td>\n                <td>\n                    <input type=\"text\" name=\"editValue\" size=\"30\" value=\"");
      out.print( value != null ? value : "" );
      out.write("\"/>\n                </td>\n            </tr>\n\n                ");
 }
                else { 
      out.write("\n                <tr>\n                    <input type=\"hidden\" name=\"editAdvancedQuery\" value=\"true\"/>\n                    <td>Edit Query:</td>\n                    <td><input type=\"text\" name=\"editQueryField\" size=\"40\" value=\"");
      out.print( variable);
      out.write("\"></td>\n                </tr>\n\n                ");
 } 
      out.write("\n\n\n                <tr>\n\n                    <td>Route To Queue:</td>\n                    <td>\n                        <select name=\"editQueueID\">\n                            ");
 for (RequestQueue queue : workgroup.getRequestQueues()) { 
      out.write("\n                            <option value=\"");
      out.print( queue.getID());
      out.write('"');
      out.write(' ');
      out.print( queue.getID() == queueID ? "selected" : "");
      out.write('>');
      out.print( queue.getName());
      out.write("</option>\n                            ");
 } 
      out.write("\n                        </select>\n                    </td>\n                    <td>\n                        <input type=\"submit\" name=\"editRule\" value=\"Update\"/>\n                    </td>\n                </tr>\n\n                <input type=\"hidden\" name=\"wgID\" value=\"");
      out.print( wgID);
      out.write("\"/>\n            </form>\n        </table>\n    </fieldset>\n    <br/>\n</div>\n");

    String formElement = request.getParameter("fElement");

      out.write("\n\n<div style=\"");
      out.print( edit ? "display:none;" : "" );
      out.write("\" class=\"jive-contentBox\">\n\t  <h4>Create New Routing Rule</h4>\n\n    <table cellspacing=\"0\" cellpadding=\"3\">\n        <form action=\"workgroup-queues.jsp\" method=\"post\">\n            <tr>\n                <td colspan=\"3\">\n                   Routing rules allow searches against incoming chat request metadata and allow for the routing to specific queues within this workgroup<br/><br/>\n                </td>\n            </tr>\n            <tr>\n                <td colspan=\"3\">\n\n                    <table>\n                        <tr>\n                            <td><input type=\"radio\" name=\"selector\" value=\"queryBuilder\" checked onclick=\"enableDefault();\"></td>\n                            <td colspan=\"2\"><b>Form Field Matcher</b></td>\n                        </tr>\n                        <tr>\n                            <td></td>\n                            <td>\n                                Form Variable:\n                            </td>\n                            <td>\n                                <select name=\"variable\" onchange=\"updateForm(this);\">\n");
      out.write("                                    ");
 for (FormField field : dataForm.getFields()) {
                                            if(formElement == null){
                                                formElement = field.getVariable();
                                            }
                                        String selected = field.getVariable().equals(formElement) ? "selected" : "";
                                    
      out.write("\n\n                                    <option value=\"");
      out.print( field.getVariable());
      out.write('"');
      out.write(' ');
      out.print( selected );
      out.write('>');
      out.print( field.getVariable());
      out.write("</option>\n                                    ");
 } 
      out.write("\n\n                                </select>\n                            </td>\n                        </tr><tr valign=\"top\">\n                        <td></td>\n                        <td>Form Value:</td>\n                        <td>\n                            ");

                                for (org.jivesoftware.openfire.fastpath.dataforms.FormElement ele : form
                                        .getFormElements()) {
                                    if (formElement.equals(ele.getVariable())) {
                                        out.println(FormUtils.createAnswers(ele, "value"));
                                    }
                                }
                            
      out.write("\n\n                        </td>\n                    </tr>\n                    </table>\n\n                </td>\n\n\n\n                <tr>\n                    <td colspan=\"3\">\n                        <div id=\"advanced\">\n\n                            <table width=\"600\">\n                                <tr>\n                                    <td><input type=\"radio\" name=\"selector\" value=\"advancedBuilder\" onclick=\"enableAdvanced();\"></td>\n                                    <td colspan=\"2\"><b>Query Builder</b></td>\n                                </tr>\n                                <tr>\n                                    <td></td>\n                                    <td width=\"1%\" nowrap>Query:</td>\n                                    <td><input type=\"text\" name=\"advancedQuery\" size=\"40\" id=\"advancedField\"/></td>\n                                </tr>\n                                <tr>\n                                    <td></td>\n                                    <td colspan=\"2\"><span class=\"jive-description\">Uses Lucene search syntax to search metadata. To search for\n");
      out.write("                                    a match in the username as well as in a question, use the following syntax: <i>username:derek AND question:chat</i>.<br>Please refer to the\n                                    <a href=\"http://lucene.apache.org/java/docs/queryparsersyntax.html\" target=\"_blank\">Lucene Query Parser Syntax</a>&nbsp;tutorial for proper syntax.</span></td>\n                                </tr>\n                            </table>\n\n                        </div>\n                    </td>\n                </tr>\n\n\n                <td>\n                    <table>\n                        <tr>\n                            <td>Route To Queue:</td>\n                            <td>\n                                <select name=\"queueID\">\n                                    ");
 for (RequestQueue queue : workgroup.getRequestQueues()) { 
      out.write("\n                                    <option value=\"");
      out.print( queue.getID());
      out.write('"');
      out.write('>');
      out.print( queue.getName());
      out.write("</option>\n                                    ");
 } 
      out.write("\n                                </select>\n                            </td>\n                            <td>\n\n                                <input type=\"submit\" name=\"submit\" value=\"Add\"/>\n                            </td>\n                        </tr>\n                    </table>\n                </td>\n\n\n                <input type=\"hidden\" name=\"wgID\" value=\"");
      out.print( wgID);
      out.write("\"/>\n        </form>\n    </table>\n\n</div>\n\n\n<script type=\"text/javascript\">\n    enableDefault();\n</script>\n\n\n</body>\n</html>\n");
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
