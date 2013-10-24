package org.jivesoftware.openfire.plugin.fastpath;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.jivesoftware.openfire.fastpath.history.AgentChatSession;
import org.jivesoftware.openfire.fastpath.history.ChatSession;
import org.jivesoftware.openfire.fastpath.history.ChatTranscriptManager;
import org.jivesoftware.xmpp.workgroup.utils.ModelUtil;
import java.util.Iterator;
import java.util.List;

public final class chat_002dconversation_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write("\n\n\n<html>\n    <head>\n        <title>Chat Conversation</title>\n        <meta name=\"pageID\" content=\"chat-summary\"/>\n      <style type=\"text/css\">\n          .conversation-label1 {\n              color: blue;\n              font-size: 10px;\n              font-family: Verdana, Arial, sans-serif;\n          }\n\n          .conversation-label2 {\n              color: red;\n              font-size: 10px;\n              font-family: Verdana, Arial, sans-serif;\n          }\n\n          .notification-label {\n              color: #060;\n              font-size: 10px;\n              font-family: Verdana, Arial, sans-serif;\n          }\n\n          .conversation-body {\n               color: black;\n               font-size: 11px;\n               font-family: Verdana, Arial, sans-serif;\n           }\n    </style>\n    </head>\n    <body>\n");

    String sessionID = request.getParameter("sessionID");
    ChatSession chatSession = ChatTranscriptManager.getChatSession(sessionID);
    AgentChatSession initial = chatSession.getFirstSession();
    if (initial == null) {
        if (chatSession.getState() == 0) {
            out.println("User Cancelled");
        }
        else if (chatSession.getState() == 1) {
            out.println("User could not be routed");
        }
        else {
            out.println("Agent never joined");
        }
    }
    else {

    }

    List questionList = chatSession.getMetadata().get("question");
    String question = "No question was asked";
    if (questionList != null && questionList.size() > 0) {
        question = (String)questionList.get(0);
        chatSession.getMetadata().remove("question");
    }


      out.write("\n<div  class=\"jive-contentBox\">\n    <table cellpadding=\"3\" cellspacing=\"0\" border=\"0\" width=\"70%\">\n        <h4>Conversation Metadata</h4>\n        <tr>\n            <td  colspan=1 class=\"conversation-body\">\n                <b>Question:</b>\n            </td>\n            <td colspan=4 class=\"conversation-body\">\n                ");
      out.print( question );
      out.write("\n            </td>\n        </tr>\n");

    int counter = 0;
    Iterator<String> metaIter = chatSession.getMetadata().keySet().iterator();
    while (metaIter.hasNext()) {
        String metaname = metaIter.next();
        String metavalue = "";
        metavalue = org.jivesoftware.xmpp.workgroup.request.Request
                .encodeMetadataValue(chatSession.getMetadata().get(metaname));

        counter++;

      out.write("\n            <tr>\n                <td nowrap class=\"conversation-body\">\n                    ");
      out.print( metaname );
      out.write("\n                </td>\n                <td colspan=\"3\" class=\"conversation-body\">\n                    ");
      out.print( metavalue );
      out.write("\n            </tr>\n");


        }

      out.write('\n');

        String transcript = chatSession.getTranscript();

      out.write("\n</table>\n   </div>\n<br/>\n<div  class=\"jive-contentBox\">\n <table  cellpadding=\"3\" cellspacing=\"0\" border=\"0\" width=\"70%\">\n        <tr class=\"jive-even\" >\n            <td colspan=4>\n             <h4>Chat Transcripts</h4>\n               </td>\n        </tr>\n        <tr>\n          <td>");
      out.print( transcript );
      out.write("</td>\n        </tr>\n    </table>\n");

    if (!ModelUtil.hasLength(chatSession.getTranscript())) {

      out.write("\n        <table class=\"jive-table\" cellpadding=\"3\" cellspacing=\"1\" border=\"0\">\n            <tr>\n                <td class=\"c1\" colspan=4>\n                    <tr>\n                        <td>\n                            No Chats have occured in this workgroup.\n                        </td>\n                    </tr>\n                </td>\n            </tr>\n        </table>\n");

    }

      out.write("\n    </body>\n</html>\n");
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
