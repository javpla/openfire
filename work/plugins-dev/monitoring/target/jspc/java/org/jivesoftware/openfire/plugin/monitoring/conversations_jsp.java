package org.jivesoftware.openfire.plugin.monitoring;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;
import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.plugin.MonitoringPlugin;
import org.jivesoftware.openfire.archive.ConversationManager;
import org.jivesoftware.openfire.archive.Conversation;
import org.jivesoftware.util.JiveGlobals;
import org.xmpp.packet.JID;
import org.jivesoftware.openfire.user.UserManager;
import java.net.URLEncoder;
import org.jivesoftware.util.StringUtils;

public final class conversations_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static java.util.List _jspx_dependants;

  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_fmt_message_key_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_fmt_message_key;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_fmt_param_value_nobody;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _jspx_tagPool_fmt_message_key_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_fmt_message_key = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_fmt_param_value_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _jspx_tagPool_fmt_message_key_nobody.release();
    _jspx_tagPool_fmt_message_key.release();
    _jspx_tagPool_fmt_param_value_nobody.release();
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

      out.write("\n\n\n\n\n\n\n\n\n\n\n\n\n");

    // Get handle on the Monitoring plugin
    MonitoringPlugin plugin = (MonitoringPlugin)XMPPServer.getInstance().getPluginManager().getPlugin(
            "monitoring");
    ConversationManager conversationManager = (ConversationManager)plugin.getModule(
            ConversationManager.class);

    XMPPServer server = XMPPServer.getInstance();
    UserManager userManager = UserManager.getInstance();

      out.write("\n\n<html>\n    <head>\n        <title>Conversations</title>\n        <meta name=\"pageID\" content=\"active-conversations\"/>\n        <script src=\"/js/prototype.js\" type=\"text/javascript\"></script>\n        <script src=\"/js/scriptaculous.js\" type=\"text/javascript\"></script>\n        <script src=\"/plugins/monitoring/dwr/engine.js\" type=\"text/javascript\" ></script>\n        <script src=\"/plugins/monitoring/dwr/util.js\" type=\"text/javascript\" ></script>\n        <script src=\"/plugins/monitoring/dwr/interface/conversations.js\" type=\"text/javascript\"></script>\n    </head>\n    <body>\n\n<style type=\"text/css\">\n\t@import \"style/style.css\";\n</style>\n<script type=\"text/javascript\">\nDWREngine.setErrorHandler(handleError);\nwindow.onerror = handleError;\nfunction handleError() {\n    // swallow errors: probably caused by the server being down\n}\n\nvar peConversations = new PeriodicalExecuter(conversationUpdater, 10);\n\nfunction conversationUpdater() {\n    try {\n        conversations.getConversations(updateConversations, true);\n    } catch(err) {\n");
      out.write("        // swallow errors\n    }\n}\n\nfunction updateConversations(data) {\n    conversationsTable = $('conversations');\n    rows = conversationsTable.getElementsByTagName(\"tr\");\n    // loop over existing rows in the table\n    var rowsToDelete = new Array();\n    for (i = 0; i < rows.length; i++) {\n        // is this a conversation row?\n        if (rows[i].id == 'noconversations') {\n            rowsToDelete.push(i);\n        } else if (rows[i].id != '') {\n            // does the conversation exist in update we received?\n            convID = rows[i].id.replace('conversation-', '');\n            if (data[convID] != undefined) {\n\n                row = rows[i];\n                cells = row.getElementsByTagName('td');\n                conversation = data[convID];\n                if (cells[3].innerHTML != conversation.messageCount) {\n                    users = conversation.participant1 + '<br />' + conversation.participant2;\n                    cells[0].innerHTML = users;\n                    cells[1].innerHTML = conversation.duration;\n");
      out.write("                    cells[2].innerHTML = conversation.lastActivity;\n                    cells[3].innerHTML = conversation.messageCount;\n                    new Effect.Highlight(row, {duration: 3.0});\n                }\n            // doesn't exist in update, delete from table\n            } else {\n                rowsToDelete.push(i);\n            }\n        }\n    }\n\n    for (i=0; i<rowsToDelete.length; i++) {\n        conversationsTable.deleteRow(rowsToDelete[i]);\n    }\n\n\n    // then add any new conversations from the update\n    counter = 0;\n    for (var c in data) {\n        counter++;\n        // does this conversation already exist?\n        if ($('conversation-' + c) == undefined) {\n            conversation = data[c];\n            users = conversation.participant1 + '<br />' + conversation.participant2;\n            var newTR = document.createElement(\"tr\");\n            newTR.setAttribute('id', 'conversation-' + c)\n            conversationsTable.appendChild(newTR);\n            var TD = document.createElement(\"TD\");\n");
      out.write("            TD.innerHTML = users;\n            newTR.appendChild(TD);\n\n            TD = document.createElement(\"TD\");\n            TD.innerHTML = conversation.duration;\n            newTR.appendChild(TD);\n\n            TD = document.createElement(\"TD\");\n            TD.innerHTML = conversation.lastActivity;\n            newTR.appendChild(TD);\n\n            TD = document.createElement(\"TD\");\n            TD.innerHTML = conversation.messageCount;\n            newTR.appendChild(TD);\n        }\n    }\n\n    // update activeConversations number\n    $('activeConversations').innerHTML = counter;\n}\n\n</script>\n\n<!-- <a href=\"#\" onclick=\"conversationUpdater(); return false;\">click me</a> -->\n<p>\n    ");
      if (_jspx_meth_fmt_message_0(_jspx_page_context))
        return;
      out.write("\n    <span id=\"activeConversations\">");
      out.print( conversationManager.getConversationCount() );
      out.write("</span\n</p>\n\n");

    Collection<Conversation> conversations = conversationManager.getConversations();

      out.write("\n\n\n<div class=\"jive-table\">\n<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" id=\"conversations\">\n<thead>\n    <tr>\n        <th nowrap>");
      if (_jspx_meth_fmt_message_1(_jspx_page_context))
        return;
      out.write("</th>\n        <th nowrap>");
      if (_jspx_meth_fmt_message_2(_jspx_page_context))
        return;
      out.write("</th>\n        <th nowrap>");
      if (_jspx_meth_fmt_message_3(_jspx_page_context))
        return;
      out.write("</th>\n        <th nowrap>");
      if (_jspx_meth_fmt_message_4(_jspx_page_context))
        return;
      out.write("</th>\n    </tr>\n</thead>\n<tbody>\n    ");

        if (conversations.isEmpty()) {
    
      out.write("\n        <tr id=\"noconversations\">\n            <td colspan=\"4\">\n                ");
      if (_jspx_meth_fmt_message_5(_jspx_page_context))
        return;
      out.write("\n            </td>\n        </tr>\n\n    ");
  } 
      out.write("\n    ");

        for (Conversation conversation : conversations) {
            Collection<JID> participants = conversation.getParticipants();
    
      out.write("\n    <tr id=\"conversation-");
      out.print( conversation.getConversationID());
      out.write("\">\n        <td>\n            ");
 if (conversation.getRoom() == null) { 
      out.write("\n                ");
 for (JID jid : participants) { 
      out.write("\n                    ");
 if (server.isLocal(jid) && userManager.isRegisteredUser(jid.getNode())) { 
      out.write("\n                        <a href=\"/user-properties.jsp?username=");
      out.print( jid.getNode() );
      out.write('"');
      out.write('>');
      out.print( jid );
      out.write("</a><br />\n                    ");
 } else { 
      out.write("\n                        ");
      out.print( jid.toBareJID() );
      out.write("<br/>\n                    ");
 } 
      out.write("\n                ");
 } 
      out.write("\n            ");
 } else { 
      out.write("\n                ");
      //  fmt:message
      org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_6 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
      _jspx_th_fmt_message_6.setPageContext(_jspx_page_context);
      _jspx_th_fmt_message_6.setParent(null);
      _jspx_th_fmt_message_6.setKey("archive.group_conversation");
      int _jspx_eval_fmt_message_6 = _jspx_th_fmt_message_6.doStartTag();
      if (_jspx_eval_fmt_message_6 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        if (_jspx_eval_fmt_message_6 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
          out = _jspx_page_context.pushBody();
          _jspx_th_fmt_message_6.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
          _jspx_th_fmt_message_6.doInitBody();
        }
        do {
          out.write("\n                    ");
          //  fmt:param
          org.apache.taglibs.standard.tag.rt.fmt.ParamTag _jspx_th_fmt_param_0 = (org.apache.taglibs.standard.tag.rt.fmt.ParamTag) _jspx_tagPool_fmt_param_value_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.ParamTag.class);
          _jspx_th_fmt_param_0.setPageContext(_jspx_page_context);
          _jspx_th_fmt_param_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fmt_message_6);
          _jspx_th_fmt_param_0.setValue( "<a href='../../muc-room-occupants.jsp?roomJID=" + URLEncoder.encode(conversation.getRoom().toBareJID(), "UTF-8") + "'>" );
          int _jspx_eval_fmt_param_0 = _jspx_th_fmt_param_0.doStartTag();
          if (_jspx_th_fmt_param_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_0);
            return;
          }
          _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_0);
          out.write("\n                    ");
          //  fmt:param
          org.apache.taglibs.standard.tag.rt.fmt.ParamTag _jspx_th_fmt_param_1 = (org.apache.taglibs.standard.tag.rt.fmt.ParamTag) _jspx_tagPool_fmt_param_value_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.ParamTag.class);
          _jspx_th_fmt_param_1.setPageContext(_jspx_page_context);
          _jspx_th_fmt_param_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fmt_message_6);
          _jspx_th_fmt_param_1.setValue( "</a>" );
          int _jspx_eval_fmt_param_1 = _jspx_th_fmt_param_1.doStartTag();
          if (_jspx_th_fmt_param_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_1);
            return;
          }
          _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_1);
          out.write("\n                ");
          int evalDoAfterBody = _jspx_th_fmt_message_6.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
        if (_jspx_eval_fmt_message_6 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE)
          out = _jspx_page_context.popBody();
      }
      if (_jspx_th_fmt_message_6.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_fmt_message_key.reuse(_jspx_th_fmt_message_6);
        return;
      }
      _jspx_tagPool_fmt_message_key.reuse(_jspx_th_fmt_message_6);
      out.write("\n            ");
 } 
      out.write("\n        </td>\n        ");

            long duration = conversation.getLastActivity().getTime() -
                    conversation.getStartDate().getTime();
        
      out.write("\n        <td>");
      out.print( StringUtils.getTimeFromLong(duration) );
      out.write("</td>\n        <td>");
      out.print( JiveGlobals.formatTime(conversation.getLastActivity()) );
      out.write("</td>\n        <td>");
      out.print( conversation.getMessageCount() );
      out.write("</td>\n    </tr>\n    ");
  } 
      out.write("\n</tbody>\n</table>\n</div>\n\n</body>\n</html>");
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

  private boolean _jspx_meth_fmt_message_0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_0 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_0.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_0.setParent(null);
    _jspx_th_fmt_message_0.setKey("archive.conversations");
    int _jspx_eval_fmt_message_0 = _jspx_th_fmt_message_0.doStartTag();
    if (_jspx_th_fmt_message_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_0);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_0);
    return false;
  }

  private boolean _jspx_meth_fmt_message_1(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_1 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_1.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_1.setParent(null);
    _jspx_th_fmt_message_1.setKey("archive.conversations.users");
    int _jspx_eval_fmt_message_1 = _jspx_th_fmt_message_1.doStartTag();
    if (_jspx_th_fmt_message_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_1);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_1);
    return false;
  }

  private boolean _jspx_meth_fmt_message_2(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_2 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_2.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_2.setParent(null);
    _jspx_th_fmt_message_2.setKey("archive.conversations.duration");
    int _jspx_eval_fmt_message_2 = _jspx_th_fmt_message_2.doStartTag();
    if (_jspx_th_fmt_message_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_2);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_2);
    return false;
  }

  private boolean _jspx_meth_fmt_message_3(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_3 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_3.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_3.setParent(null);
    _jspx_th_fmt_message_3.setKey("archive.conversations.lastactivity");
    int _jspx_eval_fmt_message_3 = _jspx_th_fmt_message_3.doStartTag();
    if (_jspx_th_fmt_message_3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_3);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_3);
    return false;
  }

  private boolean _jspx_meth_fmt_message_4(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_4 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_4.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_4.setParent(null);
    _jspx_th_fmt_message_4.setKey("archive.conversations.messages");
    int _jspx_eval_fmt_message_4 = _jspx_th_fmt_message_4.doStartTag();
    if (_jspx_th_fmt_message_4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_4);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_4);
    return false;
  }

  private boolean _jspx_meth_fmt_message_5(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_5 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_5.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_5.setParent(null);
    _jspx_th_fmt_message_5.setKey("archive.converations.no_conversations");
    int _jspx_eval_fmt_message_5 = _jspx_th_fmt_message_5.doStartTag();
    if (_jspx_th_fmt_message_5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_5);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_5);
    return false;
  }
}
