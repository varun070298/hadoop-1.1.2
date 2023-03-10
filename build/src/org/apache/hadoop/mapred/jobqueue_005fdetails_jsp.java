package org.apache.hadoop.mapred;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.Vector;
import java.util.Collection;
import org.apache.hadoop.http.HtmlQuoting;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.StringUtils;
import org.apache.hadoop.util.ServletUtil;

public final class jobqueue_005fdetails_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {


private static final long serialVersionUID = 526456771152222127L; 

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
      response.setContentType("text/html; charset=UTF-8");
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

  JobTracker tracker = 
    (JobTracker) application.getAttribute("job.tracker");
  String trackerName = 
    StringUtils.simpleHostname(tracker.getJobTrackerMachine());
  String queueName = request.getParameter("queueName");
  TaskScheduler scheduler = tracker.getTaskScheduler();
  Collection<JobInProgress> jobs = scheduler.getJobs(queueName);
  JobQueueInfo schedInfo = tracker.getQueueInfo(queueName);

      out.write("\n<html>\n<head>\n<title>Queue details for\n");
      out.print(queueName!=null?queueName:"(Given queue name was 'null')");
      out.write(" </title>\n<link rel=\"stylesheet\" type=\"text/css\" href=\"/static/hadoop.css\">\n<script type=\"text/javascript\" src=\"/static/jobtracker.js\"></script>\n</head>\n<body>\n");
 JSPUtil.processButtons(request, response, tracker); 
      out.write('\n');

  String schedulingInfoString = schedInfo.getSchedulingInfo();

      out.write("\n<h1>Hadoop Job Queue Scheduling Information on \n  <a href=\"jobtracker.jsp\">");
      out.print(trackerName);
      out.write("</a>\n</h1>\n<div>\nState : ");
      out.print( schedInfo.getQueueState() );
      out.write(" <br/>\nScheduling Information :\n");
      out.print( HtmlQuoting.quoteHtmlChars(schedulingInfoString).replaceAll("\n","<br/>") );
      out.write("\n</div>\n<hr/>\n");

if(jobs == null || jobs.isEmpty()) {

      out.write("\n<center>\n<h2> No Jobs found for the Queue ::\n");
      out.print(queueName!=null?queueName:"");
      out.write(" </h2>\n<hr/>\n</center>\n");

}else {

      out.write("\n<center>\n<h2> Job Summary for the Queue ::\n");
      out.print(queueName!=null?queueName:"" );
      out.write(" </h2>\n</center>\n<div style=\"text-align: center;text-indent: center;font-style: italic;\">\n(In the order maintained by the scheduler)\n</div>\n<br/>\n<hr/>\n");
      out.print(
  JSPUtil.generateJobTable("Job List", jobs, 30, 0, tracker.conf)
);
      out.write("\n<hr>\n");
 } 
      out.write('\n');
      out.write('\n');

out.println(ServletUtil.htmlFooter());

      out.write('\n');
      out.write('\n');
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
