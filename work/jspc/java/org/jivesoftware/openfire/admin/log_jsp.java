package org.jivesoftware.openfire.admin;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;
import org.jivesoftware.util.ParamUtils;
import org.jivesoftware.util.Log;
import org.jivesoftware.util.StringUtils;

public final class log_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {


    static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd kk:mm:ss");

    private static String parseDate(String input) {
        if (input == null || "".equals(input)) {
            return input;
        }
        if (input.length() < 19) {
            return input;
        }
        String d = input.substring(0,19);
        // try to parse it
        try {
            StringBuffer buf = new StringBuffer(input.length());
            synchronized (formatter) {
                Date date = formatter.parse(d);
                buf.append("<span class=\"date\" title=\"").append(formatter.format(date))
                        .append("\">");
            }
            buf.append(d).append("</span>");
            buf.append(input.substring(19,input.length()));
            return buf.toString();
        }
        catch (ParseException pe) {
            return input;
        }
    }

    private static String hilite(String input) {
        if (input == null || "".equals(input)) {
            return input;
        }
        if (input.indexOf("org.jivesoftware.") > -1) {
            StringBuffer buf = new StringBuffer();
            buf.append("<span class=\"hilite\">").append(input).append("</span>");
            return buf.toString();
        }
        else if (input.trim().startsWith("---") && input.trim().endsWith("---")) {
            StringBuffer buf = new StringBuffer();
            buf.append("<span class=\"hilite-marker\">").append(input).append("</span>");
            return buf.toString();
        }
        return input;
    }

  private static java.util.List _jspx_dependants;

  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_fmt_message_key_nobody;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _jspx_tagPool_fmt_message_key_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _jspx_tagPool_fmt_message_key_nobody.release();
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
      			"error.jsp", true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n\r\n\r\n\r\n\r\n");
      org.jivesoftware.admin.AdminPageBean pageinfo = null;
      synchronized (request) {
        pageinfo = (org.jivesoftware.admin.AdminPageBean) _jspx_page_context.getAttribute("pageinfo", PageContext.REQUEST_SCOPE);
        if (pageinfo == null){
          pageinfo = new org.jivesoftware.admin.AdminPageBean();
          _jspx_page_context.setAttribute("pageinfo", pageinfo, PageContext.REQUEST_SCOPE);
        }
      }
      out.write("\r\n\r\n");
      out.write("\r\n\r\n");

    // Get parameters
    String log = ParamUtils.getParameter(request,"log");
    String numLinesParam = ParamUtils.getParameter(request,"lines");
    int numLines = ParamUtils.getIntParameter(request,"lines",50);
    String mode = ParamUtils.getParameter(request,"mode");

    // Only allow requests for valid log file names.
    if (!("debug".equals(log) || "warn".equals(log) || "info".equals(log) || "error".equals(log))) {
        log = null;
    }

    // Set defaults
    if (log == null) {
        log = "error";
    }
    if (mode == null) {
        mode = "asc";
    }
    if (numLinesParam == null) {
        numLinesParam = "50";
    }

    // Other vars
    File logDir = new File(Log.getLogDirectory());
    String filename = log + ".log";
    File logFile = new File(logDir, filename);
    
    String lines[] = new String[0];
    int start = 0;
    try {
	    BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(logFile), "UTF-8"));
	    String line;
	    int totalNumLines = 0;
	    while ((line=in.readLine()) != null) {
	        totalNumLines++;
	    }
	    in.close();
	    // adjust the 'numLines' var to match totalNumLines if 'all' was passed in:
	    if ("All".equals(numLinesParam)) {
	        numLines = totalNumLines;
	    }
	    lines = new String[numLines];
	    in = new BufferedReader(new InputStreamReader(new FileInputStream(logFile), "UTF-8"));
	    // skip lines
	    start = totalNumLines - numLines;
	    if (start < 0) { start = 0; }
	    for (int i=0; i<start; i++) {
	        in.readLine();
	    }
	    int i = 0;
	    if ("asc".equals(mode)) {
	        while ((line=in.readLine()) != null && i<numLines) {
	            line = StringUtils.escapeHTMLTags(line);
	            line = parseDate(line);
	            line = hilite(line);
	            lines[i] = line;
	            i++;
	        }
	    }
	    else {
	        int end = lines.length-1;
	        while ((line=in.readLine()) != null && i<numLines) {
	            line = StringUtils.escapeHTMLTags(line);
	            line = parseDate(line);
	            line = hilite(line);
	            lines[end-i] = line;
	            i++;
	        }
	    }
	    numLines = start + i;
    } catch (FileNotFoundException ex) {
    	Log.info("Could not open (log)file.", ex);
    }

      out.write("\r\n\r\n<html>\r\n<head>\r\n    <title>");
      out.print( log );
      out.write("</title>\r\n    <meta name=\"decorator\" content=\"none\"/>\r\n    <style type=\"text/css\">\r\n    .log TABLE {\r\n        border : 1px #ccc solid;\r\n    }\r\n    .log TH {\r\n        font-family : verdana, arial, sans-serif;\r\n        font-weight : bold;\r\n        font-size : 8pt;\r\n    }\r\n    .log TR TH {\r\n        background-color : #ddd;\r\n        border-bottom : 1px #ccc solid;\r\n        padding-left : 2px;\r\n        padding-right : 2px;\r\n        text-align : left;\r\n    }\r\n    .log .head-num {\r\n        border-right : 1px #ccc solid;\r\n    }\r\n    .log TD {\r\n        font-family : courier new,monospace;\r\n        font-size : 9pt;\r\n        background-color : #ffe;\r\n    }\r\n    .log .num {\r\n        width : 1%;\r\n        background-color : #eee !important;\r\n        border-right : 1px #ccc solid;\r\n        padding-left : 2px;\r\n        padding-right : 2px;\r\n    }\r\n    .log .line {\r\n        padding-left : 10px;\r\n    }\r\n    .hilite {\r\n        color : #900;\r\n    }\r\n    .hilite-marker {\r\n        background-color : #ff0;\r\n        color : #000;\r\n        font-weight : bold;\r\n");
      out.write("    }\r\n    </style>\r\n</head>\r\n<body>\r\n\r\n<div class=\"log\">\r\n<table cellpadding=\"1\" cellspacing=\"0\" border=\"0\" width=\"100%\">\r\n<tr>\r\n    <th class=\"head-num\">");
      if (_jspx_meth_fmt_message_0(_jspx_page_context))
        return;
      out.write("</th>\r\n    <th>&nbsp;</th>\r\n</tr>\r\n<tr>\r\n    <td width=\"1%\" nowrap class=\"num\">\r\n        ");
  if ("asc".equals(mode)) { 
      out.write("\r\n            ");
  for (int j=start+1; j<=numLines; j++) { 
      out.write("\r\n                ");
      out.print( j );
      out.write("<br>\r\n            ");
  } 
      out.write("\r\n        ");
  } else { 
      out.write("\r\n            ");
  for(int j=numLines; j>=start+1; j--) { 
      out.write("\r\n                ");
      out.print( j );
      out.write("<br>\r\n            ");
  } 
      out.write("\r\n        ");
  } 
      out.write("\r\n    </td>\r\n    <td width=\"99%\" class=\"line\">\r\n        ");
 for (String line1 : lines) {
            if (line1 != null) {
        
      out.write("\r\n        <nobr>");
      out.print( line1 );
      out.write("\r\n        </nobr>\r\n        <br>\r\n\r\n        ");
 }
        }
        
      out.write("\r\n    </td>\r\n</tr>\r\n</table>\r\n</div>\r\n\r\n</body>\r\n</html>");
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
    _jspx_th_fmt_message_0.setKey("log.line");
    int _jspx_eval_fmt_message_0 = _jspx_th_fmt_message_0.doStartTag();
    if (_jspx_th_fmt_message_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_0);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_0);
    return false;
  }
}
