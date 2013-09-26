package org.jivesoftware.openfire.admin;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.jivesoftware.util.ParamUtils;
import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.container.Plugin;
import org.jivesoftware.openfire.container.PluginManager;
import org.jivesoftware.openfire.update.Update;
import org.jivesoftware.openfire.update.UpdateManager;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.io.*;
import org.jivesoftware.util.JiveGlobals;
import org.jivesoftware.util.Log;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;

public final class plugin_002dadmin_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n");
      org.jivesoftware.util.WebManager webManager = null;
      synchronized (_jspx_page_context) {
        webManager = (org.jivesoftware.util.WebManager) _jspx_page_context.getAttribute("webManager", PageContext.PAGE_SCOPE);
        if (webManager == null){
          webManager = new org.jivesoftware.util.WebManager();
          _jspx_page_context.setAttribute("webManager", webManager, PageContext.PAGE_SCOPE);
        }
      }
      out.write('\r');
      out.write('\n');
 webManager.init(request, response, session, application, out ); 
      out.write("\r\n\r\n");

    String deletePlugin = ParamUtils.getParameter(request, "deleteplugin");
    String reloadPlugin = ParamUtils.getParameter(request, "reloadplugin");
    boolean showReadme = ParamUtils.getBooleanParameter(request, "showReadme", false);
    boolean showChangelog = ParamUtils.getBooleanParameter(request, "showChangelog", false);
    boolean downloadRequested = request.getParameter("download") != null;
    boolean uploadPlugin = request.getParameter("uploadplugin") != null;
    String url = request.getParameter("url");
    Boolean uploadEnabled = JiveGlobals.getBooleanProperty("plugins.upload.enabled", true);

    final PluginManager pluginManager = webManager.getXMPPServer().getPluginManager();

    List<Plugin> plugins = new ArrayList<Plugin>(pluginManager.getPlugins());

    UpdateManager updateManager = XMPPServer.getInstance().getUpdateManager();

    if (plugins != null) {
        Collections.sort(plugins, new Comparator<Plugin>() {
            public int compare(Plugin p1, Plugin p2) {
                return pluginManager.getName(p1).compareTo(pluginManager.getName(p2));
            }
        });
    }

    if (downloadRequested) {
        // Download and install new version of plugin
        updateManager.downloadPlugin(url);
        // Log the event
        webManager.logEvent("downloaded plugin from "+url, null);
    }

    if (deletePlugin != null) {
        File pluginDir = pluginManager.getPluginDirectory(pluginManager.getPlugin(deletePlugin));
        File pluginJar = new File(pluginDir.getParent(), pluginDir.getName() + ".jar");
        // Also try the .war extension.
        if (!pluginJar.exists()) {
            pluginJar = new File(pluginDir.getParent(), pluginDir.getName() + ".war");
        }
        pluginJar.delete();
        pluginManager.unloadPlugin(pluginDir.getName());
        // Log the event
        webManager.logEvent("deleted plugin "+deletePlugin, null);
        response.sendRedirect("plugin-admin.jsp?deletesuccess=true");
        return;
    }

    if (reloadPlugin != null) {
        for (Plugin plugin : plugins) {
            File pluginDir = pluginManager.getPluginDirectory(plugin);
            if (reloadPlugin.equals(pluginDir.getName())) {
                pluginManager.unloadPlugin(reloadPlugin);
                // Log the event
                webManager.logEvent("reloaded plugin "+reloadPlugin, null);
                response.sendRedirect("plugin-admin.jsp?reloadsuccess=true");
                return;
            }
        }
    }

    if (uploadEnabled && uploadPlugin) {
        Boolean installed = false;

        // Create a factory for disk-based file items
        FileItemFactory factory = new DiskFileItemFactory();

        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);

        try {
            // Parse the request
            List items = upload.parseRequest(request);

            for (Object objItem : items) {
                FileItem item = (FileItem)objItem;
                String fileName = item.getName();
                if (fileName != null) {
                    InputStream is = item.getInputStream();
                    if (is != null) {
                        installed = XMPPServer.getInstance().getPluginManager().installPlugin(is, fileName);
                        if (!installed) {
                            Log.error("Plugin manager failed to install plugin: " + fileName);
                        }
                        is.close();
                        // Log the event
                        webManager.logEvent("uploaded plugin "+fileName, null);
                    }
                    else {
                        Log.error("Unable to open file stream for uploaded file: " + fileName);
                    }
                }
                else {
                    Log.error("No filename specified for file upload.");
                }
            }
        }
        catch (FileUploadException e) {
            Log.error("Unable to upload plugin file.", e);
        }
        if (installed) {
            response.sendRedirect("plugin-admin.jsp?uploadsuccess=true");
            return;
        } else {
            response.sendRedirect("plugin-admin.jsp?uploadsuccess=false");
            return;
        }
    }

      out.write("\r\n\r\n");
 if (showReadme) {
    String pluginName = ParamUtils.getParameter(request, "plugin");
    Plugin plugin = pluginManager.getPlugin(pluginName);
    if (plugin != null) {
        File readme = new File(pluginManager.getPluginDirectory(plugin), "readme.html");
        if (readme.exists()) {
            BufferedReader in = null;
            try {
                in = new BufferedReader(new FileReader(readme));
                String line;
                while ((line = in.readLine()) != null) {

      out.write('\r');
      out.write('\n');
      out.print( line );
      out.write('\r');
      out.write('\n');

                    }
                }
                catch (IOException ioe) {
                    ioe.printStackTrace();
                }
                finally {
                    if (in != null) {
                        try {
                            in.close();
                        }
                        catch (Exception e) {
                        }
                    }
                }
            }
        }
        return;
    }

      out.write('\r');
      out.write('\n');
 if (showChangelog) {
    String pluginName = ParamUtils.getParameter(request, "plugin");
    Plugin plugin = pluginManager.getPlugin(pluginName);
    if (plugin != null) {
        File changelog = new File(pluginManager.getPluginDirectory(plugin), "changelog.html");
        if (changelog.exists()) {
            BufferedReader in = null;
            try {
                in = new BufferedReader(new FileReader(changelog));
                String line;
                while ((line = in.readLine()) != null) {

      out.write('\r');
      out.write('\n');
      out.print( line );
      out.write('\r');
      out.write('\n');

                    }
                }
                catch (IOException ioe) {

                }
                finally {
                    if (in != null) {
                        try {
                            in.close();
                        }
                        catch (Exception e) {
                        }
                    }
                }
            }
        }
        return;
    }

      out.write("\r\n\r\n<html>\r\n<head>\r\n<title>");
      if (_jspx_meth_fmt_message_0(_jspx_page_context))
        return;
      out.write("</title>\r\n<meta name=\"pageID\" content=\"plugin-settings\"/>\r\n<meta name=\"helpPage\" content=\"manage_system_plugins.html\"/>\r\n<script src=\"dwr/engine.js\" type=\"text/javascript\"></script>\r\n<script src=\"dwr/util.js\" type=\"text/javascript\"></script>\r\n<script src=\"dwr/interface/downloader.js\" type=\"text/javascript\"></script>\r\n\r\n<script type=\"text/javascript\" >\r\n    DWREngine.setErrorHandler(handleError);\r\n\r\n    function handleError(error) {\r\n    }\r\n</script>\r\n\r\n<style type=\"text/css\">\r\n\r\n.textfield {\r\n    font-size: 11px;\r\n    font-family: verdana;\r\n    padding: 3px 2px;\r\n    background: #efefef;\r\n}\r\n\r\n.text {\r\n    font-size: 11px;\r\n    font-family: verdana, arial, helvetica, sans-serif;\r\n}\r\n\r\n.small-label {\r\n    font-size: 11px;\r\n    font-weight: bold;\r\n    font-family: verdana, arial, helvetica, sans-serif;\r\n}\r\n\r\n.small-label-link {\r\n    font-size: 11px;\r\n    font-weight: bold;\r\n    font-family: verdana;\r\n    text-decoration: underline;\r\n}\r\n\r\n.light-gray-border {\r\n    border-color: #ccc;\r\n    border-style: solid;\r\n    border-width: 1px 1px 1px 1px;\r\n");
      out.write("    padding: 5px;\r\n\t-moz-border-radius: 3px;\r\n}\r\n\r\n.light-gray-border-bottom {\r\n    border-color: #dcdcdc;\r\n    border-style: solid;\r\n    border-width: 0px 0px 1px 0px;\r\n}\r\n\r\n.table-header {\r\n    text-align: left;\r\n    font-family: verdana, arial, helvetica, sans-serif;\r\n    font-size: 8pt;\r\n    font-weight: bold;\r\n    border-color: #ccc;\r\n    border-style: solid;\r\n    border-width: 1px 0 1px 0;\r\n    padding: 5px;\r\n}\r\n\r\n.table-header-left {\r\n    text-align: left;\r\n    font-family: verdana, arial, helvetica, sans-serif;\r\n    font-size: 8pt;\r\n    font-weight: bold;\r\n    border-color: #ccc;\r\n    border-style: solid;\r\n    border-width: 1px 0 1px 1px;\r\n    padding: 5px;\r\n\r\n}\r\n\r\n.table-header-right {\r\n    text-align: left;\r\n    font-family: verdana, arial, helvetica, sans-serif;\r\n    font-size: 8pt;\r\n    font-weight: bold;\r\n    border-color: #ccc;\r\n    border-style: solid;\r\n    border-width: 1px 1px 1px 0;\r\n    padding: 5px;\r\n}\r\n\r\n.table-font {\r\n    font-family: verdana, arial, helvetica, sans-serif;\r\n    font-size: 8pt;\r\n");
      out.write("}\r\n\r\n.update {\r\n    font-family: verdana, arial, helvetica, sans-serif;\r\n    font-size: 8pt;\r\n    background: #E7FBDE;\r\n    border-color: #73CB73;\r\n    border-style: solid;\r\n    border-width: 0 1px 1px 1px;\r\n    padding: 5px;\r\n}\r\n\r\n.update-bottom {\r\n    text-align: left;\r\n    font-family: verdana, arial, helvetica, sans-serif;\r\n    font-size: 8pt;\r\n    font-weight: bold;\r\n    background: #E7FBDE;\r\n    border-color: #73CB73;\r\n    border-style: solid;\r\n    border-width: 0 0 1px 0;\r\n    padding: 5px;\r\n}\r\n\r\n.update-bottom-left {\r\n    text-align: left;\r\n    font-family: verdana, arial, helvetica, sans-serif;\r\n    font-size: 8pt;\r\n    font-weight: bold;\r\n    background: #E7FBDE;\r\n    border-color: #73CB73;\r\n    border-style: solid;\r\n    border-width: 0 0 1px 1px;\r\n    padding: 5px;\r\n}\r\n\r\n.update-bottom-right {\r\n    text-align: left;\r\n    font-family: verdana, arial, helvetica, sans-serif;\r\n    font-size: 8pt;\r\n    font-weight: bold;\r\n    background: #E7FBDE;\r\n    border-color: #73CB73;\r\n    border-style: solid;\r\n    border-width: 0 1px 1px 0;\r\n");
      out.write("    padding: 5px;\r\n}\r\n\r\n.update-top {\r\n    text-align: left;\r\n    font-family: verdana, arial, helvetica, sans-serif;\r\n    font-size: 9pt;\r\n    background: #E7FBDE;\r\n    border-color: #73CB73;\r\n    border-style: solid;\r\n    border-width: 1px 0px 0px 0px;\r\n    padding: 5px;\r\n}\r\n\r\n.update-right {\r\n    text-align: left;\r\n    font-family: verdana, arial, helvetica, sans-serif;\r\n    font-size: 8pt;\r\n    font-weight: bold;\r\n    background: #E7FBDE;\r\n    border-color: #73CB73;\r\n    border-style: solid;\r\n    border-width: 1px 1px 0px 0px;\r\n    padding: 5px;\r\n}\r\n\r\n.line-bottom-border {\r\n    text-align: left;\r\n    font-family: verdana, arial, helvetica, sans-serif;\r\n    font-size: 9pt;\r\n    border-color: #e3e3e3;\r\n    border-style: solid;\r\n    border-width: 0px 0px 1px 0px;\r\n    padding: 5px;\r\n}\r\n</style>\r\n\r\n\r\n<script type=\"text/javascript\">\r\n    function download(url, hashCode) {\r\n        document.getElementById(hashCode + \"-row\").style.display = 'none';\r\n        document.getElementById(hashCode + \"-update\").style.display = '';\r\n");
      out.write("        downloader.downloadPlugin(downloadComplete, url);\r\n    }\r\n\r\n    function downloadComplete(update) {\r\n        document.getElementById(update.hashCode + \"-row\").style.display = 'none';\r\n        document.getElementById(update.hashCode + \"-update\").style.display = '';\r\n        document.getElementById(update.hashCode + \"-image\").innerHTML = '<img src=\"images/success-16x16.gif\" border=\"0\" alt=\"\"/>';\r\n        document.getElementById(update.hashCode + \"-text\").innerHTML = '");
      if (_jspx_meth_fmt_message_1(_jspx_page_context))
        return;
      out.write("';\r\n    }\r\n</script>\r\n</head>\r\n\r\n<body>\r\n\r\n");
 if ("true".equals(request.getParameter("deletesuccess"))) { 
      out.write("\r\n\r\n<div class=\"success\">\r\n   ");
      if (_jspx_meth_fmt_message_2(_jspx_page_context))
        return;
      out.write("\r\n</div>\r\n<br>\r\n\r\n");
 }
else if ("false".equals(request.getParameter("deletesuccess"))) { 
      out.write("\r\n\r\n<div class=\"error\">\r\n    ");
      if (_jspx_meth_fmt_message_3(_jspx_page_context))
        return;
      out.write("\r\n</div>\r\n<br>\r\n\r\n");
 } 
      out.write("\r\n\r\n");
 if ("true".equals(request.getParameter("reloadsuccess"))) { 
      out.write("\r\n\r\n<div class=\"success\">\r\n   ");
      if (_jspx_meth_fmt_message_4(_jspx_page_context))
        return;
      out.write("\r\n</div>\r\n<br>\r\n\r\n");
 } 
      out.write("\r\n\r\n");
 if ("true".equals(request.getParameter("uploadsuccess"))) { 
      out.write("\r\n\r\n<div class=\"success\">\r\n   ");
      if (_jspx_meth_fmt_message_5(_jspx_page_context))
        return;
      out.write("\r\n</div>\r\n<br>\r\n\r\n");
 }
else if ("false".equals(request.getParameter("uploadsuccess"))) { 
      out.write("\r\n\r\n<div class=\"error\">\r\n    ");
      if (_jspx_meth_fmt_message_6(_jspx_page_context))
        return;
      out.write("\r\n</div>\r\n<br>\r\n\r\n");
 } 
      out.write("\r\n\r\n<p>\r\n    ");
      if (_jspx_meth_fmt_message_7(_jspx_page_context))
        return;
      out.write("\r\n</p>\r\n\r\n<p>\r\n\r\n<div class=\"light-gray-border\" style=\"padding:10px;\">\r\n<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\r\n <tr style=\"background:#eee;\">\r\n\r\n    <td nowrap colspan=\"3\" class=\"table-header-left\">");
      if (_jspx_meth_fmt_message_8(_jspx_page_context))
        return;
      out.write("</td>\r\n    <td nowrap class=\"table-header\">");
      if (_jspx_meth_fmt_message_9(_jspx_page_context))
        return;
      out.write("</td>\r\n    <td nowrap class=\"table-header\">");
      if (_jspx_meth_fmt_message_10(_jspx_page_context))
        return;
      out.write("</td>\r\n    <td nowrap class=\"table-header\">");
      if (_jspx_meth_fmt_message_11(_jspx_page_context))
        return;
      out.write("</td>\r\n    <td nowrap class=\"table-header\">");
      if (_jspx_meth_fmt_message_12(_jspx_page_context))
        return;
      out.write("</td>\r\n    <td nowrap class=\"table-header-right\">");
      if (_jspx_meth_fmt_message_13(_jspx_page_context))
        return;
      out.write("</td>\r\n</tr>\r\n\r\n<tbody>\r\n\r\n\r\n");

    // If only the admin plugin is installed, show "none".
    if (plugins.size() == 1) {

      out.write("\r\n<tr>\r\n    <td align=\"center\" colspan=\"8\" style=\"padding:5px;\">");
      if (_jspx_meth_fmt_message_14(_jspx_page_context))
        return;
      out.write("</td>\r\n</tr>\r\n");

    }

    int count = 0;
    for (Plugin plugin : plugins) {
        String dirName = pluginManager.getPluginDirectory(plugin).getName();
        // Skip the admin plugin.
        if (!"admin".equals(dirName)) {
            count++;
            String pluginName = pluginManager.getName(plugin);
            String pluginDescription = pluginManager.getDescription(plugin);
            String pluginAuthor = pluginManager.getAuthor(plugin);
            String pluginVersion = pluginManager.getVersion(plugin);
            File pluginDir = pluginManager.getPluginDirectory(plugin);
            File icon = new File(pluginDir, "logo_small.png");
            if (!icon.exists()) {
                icon = new File(pluginDir, "logo_small.gif");
            }
            // Check if there is an update for this plugin
            Update update = updateManager.getPluginUpdate(pluginName, pluginVersion);

      out.write("\r\n\r\n<tr valign=\"top\">\r\n    <td width=\"1%\" class=\"");
      out.print( update != null ? "update-top-left" : "line-bottom-border");
      out.write("\">\r\n        ");
 if (icon.exists()) { 
      out.write("\r\n        <img src=\"geticon?plugin=");
      out.print( URLEncoder.encode(pluginDir.getName(), "utf-8") );
      out.write("&showIcon=true&decorator=none\" width=\"16\" height=\"16\" alt=\"Plugin\">\r\n        ");
 }
        else { 
      out.write("\r\n        <img src=\"images/plugin-16x16.gif\" width=\"16\" height=\"16\" alt=\"Plugin\">\r\n        ");
 } 
      out.write("\r\n    </td>\r\n    <td width=\"20%\" nowrap valign=\"top\" class=\"");
      out.print( update != null ? "update-top" : "line-bottom-border");
      out.write("\">\r\n        ");
      out.print( (pluginName != null ? pluginName : dirName) );
      out.write(" &nbsp;\r\n        ");


            boolean readmeExists = new File(pluginDir, "readme.html").exists();
            boolean changelogExists = new File(pluginDir, "changelog.html").exists();
        
      out.write("\r\n\r\n\r\n    </td>\r\n    <td nowrap valign=\"top\" class=\"");
      out.print( update != null ? "update-top" : "line-bottom-border");
      out.write("\">\r\n        <p>");
 if (readmeExists) { 
      out.write("\r\n            <a href=\"plugin-admin.jsp?plugin=");
      out.print( URLEncoder.encode(pluginDir.getName(), "utf-8") );
      out.write("&showReadme=true&decorator=none\"\r\n                    ><img src=\"images/doc-readme-16x16.gif\" width=\"16\" height=\"16\" border=\"0\" alt=\"README\"></a>\r\n            ");
 }
            else { 
      out.write(" &nbsp; ");
 } 
      out.write("\r\n            ");
 if (changelogExists) { 
      out.write("\r\n            <a href=\"plugin-admin.jsp?plugin=");
      out.print( URLEncoder.encode(pluginDir.getName(), "utf-8") );
      out.write("&showChangelog=true&decorator=none\"\r\n                    ><img src=\"images/doc-changelog-16x16.gif\" width=\"16\" height=\"16\" border=\"0\" alt=\"changelog\"></a>\r\n            ");
 }
            else { 
      out.write(" &nbsp; ");
 } 
      out.write("</p>\r\n    </td>\r\n    <td width=\"60%\" valign=\"top\" class=\"");
      out.print( update != null ? "update-top" : "line-bottom-border");
      out.write("\">\r\n        ");
      out.print( pluginDescription != null ? pluginDescription : "" );
      out.write("\r\n    </td>\r\n    <td width=\"5%\" align=\"center\" valign=\"top\" class=\"");
      out.print( update != null ? "update-top" : "line-bottom-border");
      out.write("\">\r\n        <p>");
      out.print( pluginVersion != null ? pluginVersion : "" );
      out.write("</p>\r\n\r\n    </td>\r\n    <td width=\"15%\" nowrap valign=\"top\" class=\"");
      out.print( update != null ? "update-top" : "line-bottom-border");
      out.write("\">\r\n        ");
      out.print( pluginAuthor != null ? pluginAuthor : "" );
      out.write("  &nbsp;\r\n    </td>\r\n    <td width=\"1%\" align=\"center\" valign=\"top\" class=\"");
      out.print( update != null ? "update-top" : "line-bottom-border");
      out.write("\">\r\n        <a href=\"plugin-admin.jsp?reloadplugin=");
      out.print( dirName );
      out.write("\"\r\n           title=\"");
      if (_jspx_meth_fmt_message_15(_jspx_page_context))
        return;
      out.write("\"\r\n                ><img src=\"images/refresh-16x16.gif\" width=\"16\" height=\"16\" border=\"0\" alt=\"");
      if (_jspx_meth_fmt_message_16(_jspx_page_context))
        return;
      out.write("\"></a>\r\n    </td>\r\n    <td width=\"1%\" align=\"center\" valign=\"top\" class=\"");
      out.print( update != null ? "update-right" : "line-bottom-border");
      out.write("\">\r\n        <a href=\"#\" onclick=\"if (confirm('");
      if (_jspx_meth_fmt_message_17(_jspx_page_context))
        return;
      out.write("')) { location.replace('plugin-admin.jsp?deleteplugin=");
      out.print( dirName );
      out.write("'); } \"\r\n           title=\"");
      if (_jspx_meth_fmt_message_18(_jspx_page_context))
        return;
      out.write("\"\r\n                ><img src=\"images/delete-16x16.gif\" width=\"16\" height=\"16\" border=\"0\" alt=\"");
      if (_jspx_meth_fmt_message_19(_jspx_page_context))
        return;
      out.write("\"></a>\r\n    </td>\r\n</tr>\r\n\r\n");
 if (update != null) { 
      out.write("\r\n\r\n<!-- Has Updates, show show -->\r\n");

    String updateURL = update.getURL();
    if (updateURL.endsWith(".jar") || updateURL.endsWith(".zip") || updateURL.endsWith(".war")) {
        // Change it so that the server downloads and installs the new version of the plugin
        updateURL = "plugin-admin.jsp?download=true&url=" + updateURL;
    }

      out.write("\r\n<tr id=\"");
      out.print( update.hashCode() );
      out.write("-row\">\r\n    <td class=\"update-bottom-left\">&nbsp;</td>\r\n    <td class=\"update-bottom\" nowrap>\r\n        <span class=\"small-label\">\r\n            ");
      //  fmt:message
      org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_20 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
      _jspx_th_fmt_message_20.setPageContext(_jspx_page_context);
      _jspx_th_fmt_message_20.setParent(null);
      _jspx_th_fmt_message_20.setKey("plugin.admin.version.available");
      int _jspx_eval_fmt_message_20 = _jspx_th_fmt_message_20.doStartTag();
      if (_jspx_eval_fmt_message_20 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        if (_jspx_eval_fmt_message_20 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
          out = _jspx_page_context.pushBody();
          _jspx_th_fmt_message_20.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
          _jspx_th_fmt_message_20.doInitBody();
        }
        do {
          out.write("\r\n                ");
          //  fmt:param
          org.apache.taglibs.standard.tag.rt.fmt.ParamTag _jspx_th_fmt_param_0 = (org.apache.taglibs.standard.tag.rt.fmt.ParamTag) _jspx_tagPool_fmt_param_value_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.ParamTag.class);
          _jspx_th_fmt_param_0.setPageContext(_jspx_page_context);
          _jspx_th_fmt_param_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fmt_message_20);
          _jspx_th_fmt_param_0.setValue( update.getLatestVersion());
          int _jspx_eval_fmt_param_0 = _jspx_th_fmt_param_0.doStartTag();
          if (_jspx_th_fmt_param_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_0);
            return;
          }
          _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_0);
          out.write("\r\n            ");
          int evalDoAfterBody = _jspx_th_fmt_message_20.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
        if (_jspx_eval_fmt_message_20 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE)
          out = _jspx_page_context.popBody();
      }
      if (_jspx_th_fmt_message_20.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_fmt_message_key.reuse(_jspx_th_fmt_message_20);
        return;
      }
      _jspx_tagPool_fmt_message_key.reuse(_jspx_th_fmt_message_20);
      out.write("\r\n            </span>\r\n    </td>\r\n    <td nowrap class=\"update-bottom\">\r\n        ");
 if (update.getChangelog() != null) { 
      out.write("\r\n        <span class=\"text\">(<a href=\"");
      out.print( update.getChangelog());
      out.write('"');
      out.write('>');
      if (_jspx_meth_fmt_message_21(_jspx_page_context))
        return;
      out.write("</a>)</span>\r\n        ");
 }
        else { 
      out.write("\r\n        &nbsp;\r\n        ");
 } 
      out.write("\r\n    </td>\r\n    <td class=\"update-bottom\">\r\n        <table>\r\n            <tr>\r\n                <td><a href=\"javascript:download('");
      out.print( update.getURL());
      out.write("', '");
      out.print(update.hashCode());
      out.write("')\"><img src=\"images/icon_update-16x16.gif\" width=\"16\" height=\"16\" border=\"0\" alt=\"changelog\"></a></td>\r\n                <td><a href=\"javascript:download('");
      out.print( update.getURL());
      out.write("', '");
      out.print(update.hashCode());
      out.write("')\"><span class=\"small-label\">");
      if (_jspx_meth_fmt_message_22(_jspx_page_context))
        return;
      out.write("</span></a></td>\r\n            </tr>\r\n        </table>\r\n    </td>\r\n    <td class=\"update-bottom\" colspan=\"3\">&nbsp;</td>\r\n    <td class=\"update-bottom-right\" colspan=\"3\">&nbsp;</td>\r\n</tr>\r\n\r\n    <tr id=\"");
      out.print( update.hashCode());
      out.write("-update\" style=\"display:none;\">\r\n        <td colspan=\"8\" align=\"center\" class=\"update\">\r\n            <table>\r\n                <tr>\r\n                    <td id=\"");
      out.print( update.hashCode());
      out.write("-image\"><img src=\"images/working-16x16.gif\" border=\"0\" alt=\"\"/></td>\r\n                    <td id=\"");
      out.print( update.hashCode());
      out.write("-text\" class=\"table-font\">");
      if (_jspx_meth_fmt_message_23(_jspx_page_context))
        return;
      out.write("</td>\r\n                </tr>\r\n            </table>\r\n        </td>\r\n    </tr>\r\n\r\n\r\n");
 } 
      out.write("\r\n<tr><td></td></tr>\r\n\r\n<!-- End of update section -->\r\n");

        }
    }

      out.write("\r\n</tbody>\r\n</table>\r\n</div>\r\n\r\n");
 if (uploadEnabled) { 
      out.write("\r\n<br /><br />\r\n\r\n<div>\r\n    <h3>");
      if (_jspx_meth_fmt_message_24(_jspx_page_context))
        return;
      out.write("</h3>\r\n    <p>");
      if (_jspx_meth_fmt_message_25(_jspx_page_context))
        return;
      out.write("</p>\r\n    <form action=\"plugin-admin.jsp?uploadplugin\" enctype=\"multipart/form-data\" method=\"post\">\r\n        <input type=\"file\" name=\"uploadfile\" />\r\n        <input type=\"submit\" value=\"");
      if (_jspx_meth_fmt_message_26(_jspx_page_context))
        return;
      out.write("\" />\r\n    </form>\r\n</div>\r\n");
 } 
      out.write("\r\n\r\n</body>\r\n</html>");
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
    _jspx_th_fmt_message_0.setKey("plugin.admin.title");
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
    _jspx_th_fmt_message_1.setKey("plugin.admin.update.complete");
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
    _jspx_th_fmt_message_2.setKey("plugin.admin.deleted_success");
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
    _jspx_th_fmt_message_3.setKey("plugin.admin.deleted_failure");
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
    _jspx_th_fmt_message_4.setKey("plugin.admin.reload_success");
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
    _jspx_th_fmt_message_5.setKey("plugin.admin.uploaded_success");
    int _jspx_eval_fmt_message_5 = _jspx_th_fmt_message_5.doStartTag();
    if (_jspx_th_fmt_message_5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_5);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_5);
    return false;
  }

  private boolean _jspx_meth_fmt_message_6(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_6 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_6.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_6.setParent(null);
    _jspx_th_fmt_message_6.setKey("plugin.admin.uploaded_failure");
    int _jspx_eval_fmt_message_6 = _jspx_th_fmt_message_6.doStartTag();
    if (_jspx_th_fmt_message_6.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_6);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_6);
    return false;
  }

  private boolean _jspx_meth_fmt_message_7(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_7 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_7.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_7.setParent(null);
    _jspx_th_fmt_message_7.setKey("plugin.admin.info");
    int _jspx_eval_fmt_message_7 = _jspx_th_fmt_message_7.doStartTag();
    if (_jspx_th_fmt_message_7.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_7);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_7);
    return false;
  }

  private boolean _jspx_meth_fmt_message_8(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_8 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_8.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_8.setParent(null);
    _jspx_th_fmt_message_8.setKey("plugin.admin.name");
    int _jspx_eval_fmt_message_8 = _jspx_th_fmt_message_8.doStartTag();
    if (_jspx_th_fmt_message_8.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_8);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_8);
    return false;
  }

  private boolean _jspx_meth_fmt_message_9(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_9 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_9.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_9.setParent(null);
    _jspx_th_fmt_message_9.setKey("plugin.admin.description");
    int _jspx_eval_fmt_message_9 = _jspx_th_fmt_message_9.doStartTag();
    if (_jspx_th_fmt_message_9.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_9);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_9);
    return false;
  }

  private boolean _jspx_meth_fmt_message_10(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_10 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_10.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_10.setParent(null);
    _jspx_th_fmt_message_10.setKey("plugin.admin.version");
    int _jspx_eval_fmt_message_10 = _jspx_th_fmt_message_10.doStartTag();
    if (_jspx_th_fmt_message_10.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_10);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_10);
    return false;
  }

  private boolean _jspx_meth_fmt_message_11(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_11 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_11.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_11.setParent(null);
    _jspx_th_fmt_message_11.setKey("plugin.admin.author");
    int _jspx_eval_fmt_message_11 = _jspx_th_fmt_message_11.doStartTag();
    if (_jspx_th_fmt_message_11.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_11);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_11);
    return false;
  }

  private boolean _jspx_meth_fmt_message_12(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_12 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_12.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_12.setParent(null);
    _jspx_th_fmt_message_12.setKey("plugin.admin.restart");
    int _jspx_eval_fmt_message_12 = _jspx_th_fmt_message_12.doStartTag();
    if (_jspx_th_fmt_message_12.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_12);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_12);
    return false;
  }

  private boolean _jspx_meth_fmt_message_13(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_13 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_13.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_13.setParent(null);
    _jspx_th_fmt_message_13.setKey("global.delete");
    int _jspx_eval_fmt_message_13 = _jspx_th_fmt_message_13.doStartTag();
    if (_jspx_th_fmt_message_13.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_13);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_13);
    return false;
  }

  private boolean _jspx_meth_fmt_message_14(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_14 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_14.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_14.setParent(null);
    _jspx_th_fmt_message_14.setKey("plugin.admin.no_plugin");
    int _jspx_eval_fmt_message_14 = _jspx_th_fmt_message_14.doStartTag();
    if (_jspx_th_fmt_message_14.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_14);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_14);
    return false;
  }

  private boolean _jspx_meth_fmt_message_15(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_15 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_15.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_15.setParent(null);
    _jspx_th_fmt_message_15.setKey("plugin.admin.click_reload");
    int _jspx_eval_fmt_message_15 = _jspx_th_fmt_message_15.doStartTag();
    if (_jspx_th_fmt_message_15.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_15);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_15);
    return false;
  }

  private boolean _jspx_meth_fmt_message_16(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_16 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_16.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_16.setParent(null);
    _jspx_th_fmt_message_16.setKey("global.refresh");
    int _jspx_eval_fmt_message_16 = _jspx_th_fmt_message_16.doStartTag();
    if (_jspx_th_fmt_message_16.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_16);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_16);
    return false;
  }

  private boolean _jspx_meth_fmt_message_17(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_17 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_17.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_17.setParent(null);
    _jspx_th_fmt_message_17.setKey("plugin.admin.confirm");
    int _jspx_eval_fmt_message_17 = _jspx_th_fmt_message_17.doStartTag();
    if (_jspx_th_fmt_message_17.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_17);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_17);
    return false;
  }

  private boolean _jspx_meth_fmt_message_18(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_18 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_18.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_18.setParent(null);
    _jspx_th_fmt_message_18.setKey("global.click_delete");
    int _jspx_eval_fmt_message_18 = _jspx_th_fmt_message_18.doStartTag();
    if (_jspx_th_fmt_message_18.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_18);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_18);
    return false;
  }

  private boolean _jspx_meth_fmt_message_19(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_19 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_19.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_19.setParent(null);
    _jspx_th_fmt_message_19.setKey("global.delete");
    int _jspx_eval_fmt_message_19 = _jspx_th_fmt_message_19.doStartTag();
    if (_jspx_th_fmt_message_19.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_19);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_19);
    return false;
  }

  private boolean _jspx_meth_fmt_message_21(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_21 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_21.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_21.setParent(null);
    _jspx_th_fmt_message_21.setKey("plugin.admin.changelog");
    int _jspx_eval_fmt_message_21 = _jspx_th_fmt_message_21.doStartTag();
    if (_jspx_th_fmt_message_21.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_21);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_21);
    return false;
  }

  private boolean _jspx_meth_fmt_message_22(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_22 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_22.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_22.setParent(null);
    _jspx_th_fmt_message_22.setKey("plugin.admin.update");
    int _jspx_eval_fmt_message_22 = _jspx_th_fmt_message_22.doStartTag();
    if (_jspx_th_fmt_message_22.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_22);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_22);
    return false;
  }

  private boolean _jspx_meth_fmt_message_23(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_23 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_23.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_23.setParent(null);
    _jspx_th_fmt_message_23.setKey("plugin.admin.updating");
    int _jspx_eval_fmt_message_23 = _jspx_th_fmt_message_23.doStartTag();
    if (_jspx_th_fmt_message_23.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_23);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_23);
    return false;
  }

  private boolean _jspx_meth_fmt_message_24(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_24 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_24.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_24.setParent(null);
    _jspx_th_fmt_message_24.setKey("plugin.admin.upload_plugin");
    int _jspx_eval_fmt_message_24 = _jspx_th_fmt_message_24.doStartTag();
    if (_jspx_th_fmt_message_24.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_24);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_24);
    return false;
  }

  private boolean _jspx_meth_fmt_message_25(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_25 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_25.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_25.setParent(null);
    _jspx_th_fmt_message_25.setKey("plugin.admin.upload_plugin.info");
    int _jspx_eval_fmt_message_25 = _jspx_th_fmt_message_25.doStartTag();
    if (_jspx_th_fmt_message_25.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_25);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_25);
    return false;
  }

  private boolean _jspx_meth_fmt_message_26(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_26 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_26.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_26.setParent(null);
    _jspx_th_fmt_message_26.setKey("plugin.admin.upload_plugin");
    int _jspx_eval_fmt_message_26 = _jspx_th_fmt_message_26.doStartTag();
    if (_jspx_th_fmt_message_26.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_26);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_26);
    return false;
  }
}
