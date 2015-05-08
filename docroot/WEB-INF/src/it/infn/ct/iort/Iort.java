/*
*************************************************************************
Copyright (c) 2011-2013:
Istituto Nazionale di Fisica Nucleare (INFN), Italy
Consorzio COMETA (COMETA), Italy

See http://www.infn.it and and http://www.consorzio-cometa.it for details on
the copyright holders.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

@author <a href="mailto:giuseppe.larocca@ct.infn.it">Giuseppe La Rocca</a>
***************************************************************************
*/
package it.infn.ct.iort;

// import liferay libraries
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.io.OutputStreamWriter;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.User;
import com.liferay.portal.theme.ThemeDisplay;

// import DataEngine libraries
import com.liferay.portal.util.PortalUtil;
import it.infn.ct.GridEngine.InformationSystem.BDII;
import it.infn.ct.GridEngine.Job.*;

// import generic Java libraries
import it.infn.ct.GridEngine.UsersTracking.UsersTrackingDBInterface;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.net.InetAddress;
import java.net.URISyntaxException;
import java.net.URI;

// import portlet libraries
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.NamingException;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletException;
import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

// Importing Apache libraries
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.portlet.PortletFileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Iort extends GenericPortlet {

    private static Log log = LogFactory.getLog(Iort.class);   

    @Override
    protected void doEdit(RenderRequest request,
            RenderResponse response)
            throws PortletException, IOException
    {

        PortletPreferences portletPreferences =
                (PortletPreferences) request.getPreferences();

        response.setContentType("text/html");
        
        // Getting the LATO INFRASTRUCTURE from the portlet preferences
        String lato_iort_INFRASTRUCTURE = portletPreferences.getValue("lato_iort_INFRASTRUCTURE", "N/A");
        // Getting the login credential from the portlet preferences for LATO
        String lato_iort_LOGIN = portletPreferences.getValue("lato_iort_LOGIN", "N/A");
        // Getting the password credential from the portlet preferences for LATO
        String lato_iort_PASSWD = portletPreferences.getValue("lato_iort_PASSWD", "N/A");
        // Getting the cluster hostname(s) from the portlet preferences for LATO
        String[] lato_iort_WMS = portletPreferences.getValues("lato_iort_WMS", new String[5]);
        // Getting the ETOKENSERVER from the portlet preferences for LATO
        String lato_iort_ETOKENSERVER = portletPreferences.getValue("lato_iort_ETOKENSERVER", "N/A");
        // Getting the MYPROXYSERVER from the portlet preferences for LATO
        String lato_iort_MYPROXYSERVER = portletPreferences.getValue("lato_iort_MYPROXYSERVER", "N/A");
        // Getting the PORT from the portlet preferences for LATO
        String lato_iort_PORT = portletPreferences.getValue("lato_iort_PORT", "N/A");
        // Getting the ROBOTID from the portlet preferences for LATO
        String lato_iort_ROBOTID = portletPreferences.getValue("lato_iort_ROBOTID", "N/A");
        // Getting the ROLE from the portlet preferences for LATO
        String lato_iort_ROLE = portletPreferences.getValue("lato_iort_ROLE", "N/A");
        // Getting the RENEWAL from the portlet preferences for LATO
        String lato_iort_RENEWAL = portletPreferences.getValue("lato_iort_RENEWAL", "checked");
        // Getting the DISABLEVOMS from the portlet preferences for LATO
        String lato_iort_DISABLEVOMS = portletPreferences.getValue("lato_iort_DISABLEVOMS", "unchecked");

        // Getting the IORT INFRASTRUCTURE from the portlet preferences for the GRIDIT VO
        String gridit_iort_INFRASTRUCTURE = portletPreferences.getValue("gridit_iort_INFRASTRUCTURE", "N/A");
        // Getting the IORT VONAME from the portlet preferences for the GRIDIT VO
        String gridit_iort_VONAME = portletPreferences.getValue("gridit_iort_VONAME", "N/A");
        // Getting the IORT TOPPBDII from the portlet preferences for the GRIDIT VO
        String gridit_iort_TOPBDII = portletPreferences.getValue("gridit_iort_TOPBDII", "N/A");
        // Getting the IORT WMS from the portlet preferences for the GRIDIT VO
        String[] gridit_iort_WMS = portletPreferences.getValues("gridit_iort_WMS", new String[5]);
        // Getting the IORT ETOKENSERVER from the portlet preferences for the GRIDIT VO
        String gridit_iort_ETOKENSERVER = portletPreferences.getValue("gridit_iort_ETOKENSERVER", "N/A");
        // Getting the IORT MYPROXYSERVER from the portlet preferences for the GRIDIT VO
        String gridit_iort_MYPROXYSERVER = portletPreferences.getValue("gridit_iort_MYPROXYSERVER", "N/A");
        // Getting the IORT PORT from the portlet preferences for the GRIDIT VO
        String gridit_iort_PORT = portletPreferences.getValue("gridit_iort_PORT", "N/A");
        // Getting the IORT ROBOTID from the portlet preferences for the GRIDIT VO
        String gridit_iort_ROBOTID = portletPreferences.getValue("gridit_iort_ROBOTID", "N/A");
        // Getting the IORT ROLE from the portlet preferences for the GRIDIT VO
        String gridit_iort_ROLE = portletPreferences.getValue("gridit_iort_ROLE", "N/A");
        // Getting the IORT RENEWAL from the portlet preferences for the GRIDIT VO
        String gridit_iort_RENEWAL = portletPreferences.getValue("gridit_iort_RENEWAL", "checked");
        // Getting the IORT DISABLEVOMS from the portlet preferences for the GRIDIT VO
        String gridit_iort_DISABLEVOMS = portletPreferences.getValue("gridit_iort_DISABLEVOMS", "unchecked");

        // Getting the IORT INFRASTRUCTURE from the portlet preferences for the EUMED VO
        String eumed_iort_INFRASTRUCTURE = portletPreferences.getValue("eumed_iort_INFRASTRUCTURE", "N/A");
        // Getting the IORT VONAME from the portlet preferences for the EUMED VO
        String eumed_iort_VONAME = portletPreferences.getValue("eumed_iort_VONAME", "N/A");
        // Getting the IORT TOPPBDII from the portlet preferences for the EUMED VO
        String eumed_iort_TOPBDII = portletPreferences.getValue("eumed_iort_TOPBDII", "N/A");
        // Getting the IORT WMS from the portlet preferences for the EUMED VO
        String[] eumed_iort_WMS = portletPreferences.getValues("eumed_iort_WMS", new String[5]);
        // Getting the IORT ETOKENSERVER from the portlet preferences for the EUMED VO
        String eumed_iort_ETOKENSERVER = portletPreferences.getValue("eumed_iort_ETOKENSERVER", "N/A");
        // Getting the IORT MYPROXYSERVER from the portlet preferences for the EUMED VO
        String eumed_iort_MYPROXYSERVER = portletPreferences.getValue("eumed_iort_MYPROXYSERVER", "N/A");
        // Getting the IORT PORT from the portlet preferences for the EUMED VO
        String eumed_iort_PORT = portletPreferences.getValue("eumed_iort_PORT", "N/A");
        // Getting the IORT ROBOTID from the portlet preferences for the EUMED VO
        String eumed_iort_ROBOTID = portletPreferences.getValue("eumed_iort_ROBOTID", "N/A");
        // Getting the IORT ROLE from the portlet preferences for the EUMED VO
        String eumed_iort_ROLE = portletPreferences.getValue("eumed_iort_ROLE", "N/A");
        // Getting the IORT RENEWAL from the portlet preferences for the EUMED VO
        String eumed_iort_RENEWAL = portletPreferences.getValue("eumed_iort_RENEWAL", "checked");
        // Getting the IORT DISABLEVOMS from the portlet preferences for the EUMED VO
        String eumed_iort_DISABLEVOMS = portletPreferences.getValue("eumed_iort_DISABLEVOMS", "unchecked");

        // Getting the IORT INFRASTRUCTURE from the portlet preferences for the BIOMED VO
        String biomed_iort_INFRASTRUCTURE = portletPreferences.getValue("biomed_iort_INFRASTRUCTURE", "N/A");
        // Getting the IORT VONAME from the portlet preferences for the BIOMED VO
        String biomed_iort_VONAME = portletPreferences.getValue("biomed_iort_VONAME", "N/A");
        // Getting the IORT TOPPBDII from the portlet preferences for the BIOMED VO
        String biomed_iort_TOPBDII = portletPreferences.getValue("biomed_iort_TOPBDII", "N/A");
        // Getting the IORT WMS from the portlet preferences for the BIOMED VO
        String[] biomed_iort_WMS = portletPreferences.getValues("biomed_iort_WMS", new String[5]);
        // Getting the IORT ETOKENSERVER from the portlet preferences for the BIOMED VO
        String biomed_iort_ETOKENSERVER = portletPreferences.getValue("biomed_iort_ETOKENSERVER", "N/A");
        // Getting the IORT MYPROXYSERVER from the portlet preferences for the BIOMED VO
        String biomed_iort_MYPROXYSERVER = portletPreferences.getValue("biomed_iort_MYPROXYSERVER", "N/A");
        // Getting the IORT PORT from the portlet preferences for the BIOMED VO
        String biomed_iort_PORT = portletPreferences.getValue("biomed_iort_PORT", "N/A");
        // Getting the IORT ROBOTID from the portlet preferences for the BIOMED VO
        String biomed_iort_ROBOTID = portletPreferences.getValue("biomed_iort_ROBOTID", "N/A");
        // Getting the IORT ROLE from the portlet preferences for the BIOMED VO
        String biomed_iort_ROLE = portletPreferences.getValue("biomed_iort_ROLE", "N/A");
        // Getting the IORT RENEWAL from the portlet preferences for the BIOMED VO
        String biomed_iort_RENEWAL = portletPreferences.getValue("biomed_iort_RENEWAL", "checked");
        // Getting the IORT DISABLEVOMS from the portlet preferences for the BIOMED VO
        String biomed_iort_DISABLEVOMS = portletPreferences.getValue("biomed_iort_DISABLEVOMS", "unchecked");

        // Getting the IORT APPID from the portlet preferences
        String iort_APPID = portletPreferences.getValue("iort_APPID", "N/A");
        // Get the LOG LEVEL from the portlet preferences
        String iort_LOGLEVEL = portletPreferences.getValue("iort_LOGLEVEL", "INFO");
        // Getting the IORT OUTPUT_PATH from the portlet preferences
        String iort_OUTPUT_PATH = portletPreferences.getValue("iort_OUTPUT_PATH", "/tmp");
        // Getting the IORT SFOTWARE from the portlet preferences
        String iort_SOFTWARE = portletPreferences.getValue("iort_SOFTWARE", "N/A");
        // Getting the TRACKING_DB_HOSTNAME from the portlet preferences
        String TRACKING_DB_HOSTNAME = portletPreferences.getValue("TRACKING_DB_HOSTNAME", "N/A");
        // Getting the TRACKING_DB_USERNAME from the portlet preferences
        String TRACKING_DB_USERNAME = portletPreferences.getValue("TRACKING_DB_USERNAME", "N/A");
        // Getting the TRACKING_DB_PASSWORD from the portlet preferences
        String TRACKING_DB_PASSWORD = portletPreferences.getValue("TRACKING_DB_PASSWORD", "N/A");
        // Getting the SMTP_HOST from the portlet preferences
        String SMTP_HOST = portletPreferences.getValue("SMTP_HOST", "N/A");
        // Getting the SENDER MAIL from the portlet preferences
        String SENDER_MAIL = portletPreferences.getValue("SENDER_MAIL", "N/A");
        // Get the list of enabled Infrastructures
        String[] infras = portletPreferences.getValues("iort_ENABLEINFRASTRUCTURE", new String[3]);

        // Set the default portlet preferences
        request.setAttribute("gridit_iort_INFRASTRUCTURE", gridit_iort_INFRASTRUCTURE.trim());
        request.setAttribute("gridit_iort_VONAME", gridit_iort_VONAME.trim());
        request.setAttribute("gridit_iort_TOPBDII", gridit_iort_TOPBDII.trim());
        request.setAttribute("gridit_iort_WMS", gridit_iort_WMS);
        request.setAttribute("gridit_iort_ETOKENSERVER", gridit_iort_ETOKENSERVER.trim());
        request.setAttribute("gridit_iort_MYPROXYSERVER", gridit_iort_MYPROXYSERVER.trim());
        request.setAttribute("gridit_iort_PORT", gridit_iort_PORT.trim());
        request.setAttribute("gridit_iort_ROBOTID", gridit_iort_ROBOTID.trim());
        request.setAttribute("gridit_iort_ROLE", gridit_iort_ROLE.trim());
        request.setAttribute("gridit_iort_RENEWAL", gridit_iort_RENEWAL);
        request.setAttribute("gridit_iort_DISABLEVOMS", gridit_iort_DISABLEVOMS);
        
        request.setAttribute("lato_iort_INFRASTRUCTURE", lato_iort_INFRASTRUCTURE.trim());
        request.setAttribute("lato_iort_LOGIN", lato_iort_LOGIN.trim());
        request.setAttribute("lato_iort_PASSWD", lato_iort_PASSWD.trim());
        request.setAttribute("lato_iort_WMS", lato_iort_WMS);
        request.setAttribute("lato_iort_ETOKENSERVER", lato_iort_ETOKENSERVER.trim());
        request.setAttribute("lato_iort_MYPROXYSERVER", lato_iort_MYPROXYSERVER.trim());
        request.setAttribute("lato_iort_PORT", lato_iort_PORT.trim());
        request.setAttribute("lato_iort_ROBOTID", lato_iort_ROBOTID.trim());
        request.setAttribute("lato_iort_ROLE", lato_iort_ROLE.trim());
        request.setAttribute("lato_iort_RENEWAL", lato_iort_RENEWAL);
        request.setAttribute("lato_iort_DISABLEVOMS", lato_iort_DISABLEVOMS);

        request.setAttribute("eumed_iort_INFRASTRUCTURE", eumed_iort_INFRASTRUCTURE.trim());
        request.setAttribute("eumed_iort_VONAME", eumed_iort_VONAME.trim());
        request.setAttribute("eumed_iort_TOPBDII", eumed_iort_TOPBDII.trim());
        request.setAttribute("eumed_iort_WMS", eumed_iort_WMS);
        request.setAttribute("eumed_iort_ETOKENSERVER", eumed_iort_ETOKENSERVER.trim());
        request.setAttribute("eumed_iort_MYPROXYSERVER", eumed_iort_MYPROXYSERVER.trim());
        request.setAttribute("eumed_iort_PORT", eumed_iort_PORT.trim());
        request.setAttribute("eumed_iort_ROBOTID", eumed_iort_ROBOTID.trim());
        request.setAttribute("eumed_iort_ROLE", eumed_iort_ROLE.trim());
        request.setAttribute("eumed_iort_RENEWAL", eumed_iort_RENEWAL);
        request.setAttribute("eumed_iort_DISABLEVOMS", eumed_iort_DISABLEVOMS);

        request.setAttribute("biomed_iort_INFRASTRUCTURE", biomed_iort_INFRASTRUCTURE.trim());
        request.setAttribute("biomed_iort_VONAME", biomed_iort_VONAME.trim());
        request.setAttribute("biomed_iort_TOPBDII", biomed_iort_TOPBDII.trim());
        request.setAttribute("biomed_iort_WMS", biomed_iort_WMS);
        request.setAttribute("biomed_iort_ETOKENSERVER", biomed_iort_ETOKENSERVER.trim());
        request.setAttribute("biomed_iort_MYPROXYSERVER", biomed_iort_MYPROXYSERVER.trim());
        request.setAttribute("biomed_iort_PORT", biomed_iort_PORT.trim());
        request.setAttribute("biomed_iort_ROBOTID", biomed_iort_ROBOTID.trim());
        request.setAttribute("biomed_iort_ROLE", biomed_iort_ROLE.trim());
        request.setAttribute("biomed_iort_RENEWAL", biomed_iort_RENEWAL);
        request.setAttribute("biomed_iort_DISABLEVOMS", biomed_iort_DISABLEVOMS);

        request.setAttribute("iort_ENABLEINFRASTRUCTURE", infras);
        request.setAttribute("iort_APPID", iort_APPID.trim());
        request.setAttribute("iort_LOGLEVEL", iort_LOGLEVEL.trim());
        request.setAttribute("iort_OUTPUT_PATH", iort_OUTPUT_PATH.trim());
        request.setAttribute("iort_SOFTWARE", iort_SOFTWARE.trim());
        request.setAttribute("TRACKING_DB_HOSTNAME", TRACKING_DB_HOSTNAME.trim());
        request.setAttribute("TRACKING_DB_USERNAME", TRACKING_DB_USERNAME.trim());
        request.setAttribute("TRACKING_DB_PASSWORD", TRACKING_DB_PASSWORD.trim());
        request.setAttribute("SMTP_HOST", SMTP_HOST.trim());
        request.setAttribute("SENDER_MAIL", SENDER_MAIL.trim());
        
        if (iort_LOGLEVEL.trim().equals("VERBOSE")) {
        log.info("\nStarting the EDIT mode...with this settings"
        + "\nlato_iort_INFRASTRUCTURE: " + lato_iort_INFRASTRUCTURE
        + "\nlato_iort_LOGIN: " + lato_iort_LOGIN
        + "\nlato_iort_PASSWD: " + lato_iort_PASSWD                    
        + "\nlato_iort_ETOKENSERVER: " + lato_iort_ETOKENSERVER
        + "\nlato_iort_MYPROXYSERVER: " + lato_iort_MYPROXYSERVER
        + "\nlato_iort_PORT: " + lato_iort_PORT
        + "\nlato_iort_ROBOTID: " + lato_iort_ROBOTID
        + "\nlato_iort_ROLE: " + lato_iort_ROLE
        + "\nlato_iort_RENEWAL: " + lato_iort_RENEWAL
        + "\nlato_iort_DISABLEVOMS: " + lato_iort_DISABLEVOMS
                
        + "\ngridit_iort_INFRASTRUCTURE: " + gridit_iort_INFRASTRUCTURE
        + "\ngridit_iort_VONAME: " + gridit_iort_VONAME
        + "\ngridit_iort_TOPBDII: " + gridit_iort_TOPBDII                    
        + "\ngridit_iort_ETOKENSERVER: " + gridit_iort_ETOKENSERVER
        + "\ngridit_iort_MYPROXYSERVER: " + gridit_iort_MYPROXYSERVER
        + "\ngridit_iort_PORT: " + gridit_iort_PORT
        + "\ngridit_iort_ROBOTID: " + gridit_iort_ROBOTID
        + "\ngridit_iort_ROLE: " + gridit_iort_ROLE
        + "\ngridit_iort_RENEWAL: " + gridit_iort_RENEWAL
        + "\ngridit_iort_DISABLEVOMS: " + gridit_iort_DISABLEVOMS

        + "\n\neumed_iort_INFRASTRUCTURE: " + eumed_iort_INFRASTRUCTURE
        + "\neumed_iort_VONAME: " + eumed_iort_VONAME
        + "\neumed_iort_TOPBDII: " + eumed_iort_TOPBDII                    
        + "\neumed_iort_ETOKENSERVER: " + eumed_iort_ETOKENSERVER
        + "\neumed_iort_MYPROXYSERVER: " + eumed_iort_MYPROXYSERVER
        + "\neumed_iort_PORT: " + eumed_iort_PORT
        + "\neumed_iort_ROBOTID: " + eumed_iort_ROBOTID
        + "\neumed_iort_ROLE: " + eumed_iort_ROLE
        + "\neumed_iort_RENEWAL: " + eumed_iort_RENEWAL
        + "\neumed_iort_DISABLEVOMS: " + eumed_iort_DISABLEVOMS

        + "\n\nbiomed_iort_INFRASTRUCTURE: " + biomed_iort_INFRASTRUCTURE
        + "\nbiomed_iort_VONAME: " + biomed_iort_VONAME
        + "\nbiomed_iort_TOPBDII: " + biomed_iort_TOPBDII                   
        + "\nbiomed_iort_ETOKENSERVER: " + biomed_iort_ETOKENSERVER
        + "\nbiomed_iort_MYPROXYSERVER: " + biomed_iort_MYPROXYSERVER
        + "\nbiomed_iort_PORT: " + biomed_iort_PORT
        + "\nbiomed_iort_ROBOTID: " + biomed_iort_ROBOTID
        + "\nbiomed_iort_ROLE: " + biomed_iort_ROLE
        + "\nbiomed_iort_RENEWAL: " + biomed_iort_RENEWAL
        + "\nbiomed_iort_DISABLEVOMS: " + biomed_iort_DISABLEVOMS
        
        + "\niort_APPID: " + iort_APPID
        + "\niort_LOGLEVEL: " + iort_LOGLEVEL
        + "\niort_OUTPUT_PATH: " + iort_OUTPUT_PATH
        + "\niort_SOFTWARE: " +iort_SOFTWARE
        + "\nTracking_DB_Hostname: " + TRACKING_DB_HOSTNAME
        + "\nTracking_DB_Username: " + TRACKING_DB_USERNAME
        + "\nTracking_DB_Password: " + TRACKING_DB_PASSWORD
        + "\nSMTP Server: " + SMTP_HOST
        + "\nSender: " + SENDER_MAIL);
        }

        PortletRequestDispatcher dispatcher =
                getPortletContext().getRequestDispatcher("/edit.jsp");

        dispatcher.include(request, response);
    }

    @Override
    protected void doHelp(RenderRequest request, RenderResponse response)
            throws PortletException, IOException {
        //super.doHelp(request, response);

        response.setContentType("text/html");

        log.info("\nStarting the HELP mode...");
        PortletRequestDispatcher dispatcher =
                getPortletContext().getRequestDispatcher("/help.jsp");

        dispatcher.include(request, response);
    }

    @Override
    protected void doView(RenderRequest request, RenderResponse response)
            throws PortletException, IOException {

        PortletPreferences portletPreferences =
                (PortletPreferences) request.getPreferences();

        response.setContentType("text/html");

        //java.util.Enumeration listPreferences = portletPreferences.getNames();
        PortletRequestDispatcher dispatcher = null;
        
        String lato_iort_PASSWD = "";
        String lato_iort_LOGIN = "";
        String gridit_iort_TOPBDII = "";
        String gridit_iort_VONAME = "";
        String eumed_iort_TOPBDII = "";
        String eumed_iort_VONAME = "";
        String biomed_iort_TOPBDII = "";
        String biomed_iort_VONAME = "";
        
        String lato_iort_ENABLEINFRASTRUCTURE = "";
        String gridit_iort_ENABLEINFRASTRUCTURE = "";
        String eumed_iort_ENABLEINFRASTRUCTURE = "";
        String biomed_iort_ENABLEINFRASTRUCTURE = "";
        String[] infras = new String[4];
        
        String[] lato_iort_WMS = new String [5];
        
        String[] iort_INFRASTRUCTURES = 
                portletPreferences.getValues("iort_ENABLEINFRASTRUCTURE", new String[4]);
        
        for (int i=0; i<iort_INFRASTRUCTURES.length; i++) {            
            if (iort_INFRASTRUCTURES[i]!=null && iort_INFRASTRUCTURES[i].equals("lato")) 
                { lato_iort_ENABLEINFRASTRUCTURE = "checked"; log.info ("\n LATO!"); }
            if (iort_INFRASTRUCTURES[i]!=null && iort_INFRASTRUCTURES[i].equals("gridit")) 
                { gridit_iort_ENABLEINFRASTRUCTURE = "checked"; log.info ("\n GRIDIT!"); }
            if (iort_INFRASTRUCTURES[i]!=null && iort_INFRASTRUCTURES[i].equals("eumed")) 
                { eumed_iort_ENABLEINFRASTRUCTURE = "checked"; log.info ("\n EUMED!"); }
            if (iort_INFRASTRUCTURES[i]!=null && iort_INFRASTRUCTURES[i].equals("biomed")) 
                { biomed_iort_ENABLEINFRASTRUCTURE = "checked"; log.info ("\n BIOMED!"); }            
        }
        
        // Getting the IORT ENABLEINFRASTRUCTURE from the portlet preferences
        //iort_ENABLEINFRASTRUCTURE = portletPreferences.getValue("iort_ENABLEINFRASTRUCTURE", "NULL");
        // Getting the IORT APPID from the portlet preferences
        String iort_APPID = portletPreferences.getValue("iort_APPID", "N/A");
        // Get the LOGLEVEL from the portlet preferences
        String iort_LOGLEVEL = portletPreferences.getValue("iort_LOGLEVEL", "INFO");
        // Getting the IORT OUTPUT_PATH from the portlet preferences
        String iort_OUTPUT_PATH = portletPreferences.getValue("iort_OUTPUT_PATH", "/tmp");
        // Getting the IORT SOFTWARE from the portlet preferences
        String iort_SOFTWARE = portletPreferences.getValue("iort_SOFTWARE", "N/A");
        // Getting the TRACKING_DB_HOSTNAME from the portlet preferences
        String TRACKING_DB_HOSTNAME = portletPreferences.getValue("TRACKING_DB_HOSTNAME", "N/A");
        // Getting the TRACKING_DB_USERNAME from the portlet preferences
        String TRACKING_DB_USERNAME = portletPreferences.getValue("TRACKING_DB_USERNAME", "N/A");
        // Getting the TRACKING_DB_PASSWORD from the portlet preferences
        String TRACKING_DB_PASSWORD = portletPreferences.getValue("TRACKING_DB_PASSWORD", "N/A");
        // Getting the SMTP_HOST from the portlet preferences
        String SMTP_HOST = portletPreferences.getValue("SMTP_HOST", "N/A");
        // Getting the SENDER_MAIL from the portlet preferences
        String SENDER_MAIL = portletPreferences.getValue("SENDER_MAIL", "N/A");
        
        if (lato_iort_ENABLEINFRASTRUCTURE.equals("checked"))
        {
            infras[0]="lato";
            // Getting the IORT INFRASTRUCTURE from the portlet preferences for LATO
            String lato_iort_INFRASTRUCTURE = portletPreferences.getValue("lato_iort_INFRASTRUCTURE", "N/A");
            // Getting the IORT VONAME from the portlet preferences for LATO
            lato_iort_LOGIN = portletPreferences.getValue("lato_iort_LOGIN", "N/A");
            // Getting the IORT TOPPBDII from the portlet preferences for LATO
            lato_iort_PASSWD = portletPreferences.getValue("lato_iort_PASSWD", "N/A");
            // Getting the IORT WMS from the portlet preferences for LATO
            lato_iort_WMS = portletPreferences.getValues("lato_iort_WMS", new String[5]);
            // Getting the IORT ETOKENSERVER from the portlet preferences for LATO
            String lato_iort_ETOKENSERVER = portletPreferences.getValue("lato_iort_ETOKENSERVER", "N/A");
            // Getting the IORT MYPROXYSERVER from the portlet preferences for LATO
            String lato_iort_MYPROXYSERVER = portletPreferences.getValue("lato_iort_MYPROXYSERVER", "N/A");
            // Getting the IORT PORT from the portlet preferences for LATO
            String lato_iort_PORT = portletPreferences.getValue("lato_iort_PORT", "N/A");
            // Getting the IORT ROBOTID from the portlet preferences for LATO
            String lato_iort_ROBOTID = portletPreferences.getValue("gridit_iort_ROBOTID", "N/A");
            // Getting the IORT ROLE from the portlet preferences for LATO
            String lato_iort_ROLE = portletPreferences.getValue("lato_iort_ROLE", "N/A");
            // Getting the IORT RENEWAL from the portlet preferences for LATO
            String lato_iort_RENEWAL = portletPreferences.getValue("lato_iort_RENEWAL", "checked");
            // Getting the IORT DISABLEVOMS from the portlet preferences for LATO
            String lato_iort_DISABLEVOMS = portletPreferences.getValue("lato_iort_DISABLEVOMS", "unchecked");
            
            // Fetching all the WMS Endpoints for LATO
            String lato_WMS = "";
            if (lato_iort_ENABLEINFRASTRUCTURE.equals("checked")) {
                if (lato_iort_WMS!=null) {
                    //log.info("length="+lato_iort_WMS.length);
                    for (int i = 0; i < lato_iort_WMS.length; i++)
                        if (!(lato_iort_WMS[i].trim().equals("N/A")) ) 
                            lato_WMS += lato_iort_WMS[i] + " ";                        
                } else { log.info("WMS not set for LATO!"); lato_iort_ENABLEINFRASTRUCTURE="unchecked"; }
            }
            
            // Save the portlet preferences
            request.setAttribute("lato_iort_INFRASTRUCTURE", lato_iort_INFRASTRUCTURE.trim());
            request.setAttribute("lato_iort_LOGIN", lato_iort_LOGIN.trim());
            request.setAttribute("lato_iort_PASSWD", lato_iort_PASSWD.trim());
            request.setAttribute("lato_iort_WMS", lato_WMS);
            request.setAttribute("lato_iort_ETOKENSERVER", lato_iort_ETOKENSERVER.trim());
            request.setAttribute("lato_iort_MYPROXYSERVER", lato_iort_MYPROXYSERVER.trim());
            request.setAttribute("lato_iort_PORT", lato_iort_PORT.trim());
            request.setAttribute("lato_iort_ROBOTID", lato_iort_ROBOTID.trim());
            request.setAttribute("lato_iort_ROLE", lato_iort_ROLE.trim());
            request.setAttribute("lato_iort_RENEWAL", lato_iort_RENEWAL);
            request.setAttribute("lato_iort_DISABLEVOMS", lato_iort_DISABLEVOMS);
            
            //request.setAttribute("iort_ENABLEINFRASTRUCTURE", iort_ENABLEINFRASTRUCTURE);
            request.setAttribute("iort_APPID", iort_APPID.trim());
            request.setAttribute("iort_LOGLEVEL", iort_LOGLEVEL.trim());
            request.setAttribute("iort_SOFTWARE", iort_SOFTWARE.trim());
            request.setAttribute("TRACKING_DB_HOSTNAME", TRACKING_DB_HOSTNAME.trim());
            request.setAttribute("TRACKING_DB_USERNAME", TRACKING_DB_USERNAME.trim());
            request.setAttribute("TRACKING_DB_PASSWORD", TRACKING_DB_PASSWORD.trim());
            request.setAttribute("SMTP_HOST", SMTP_HOST.trim());
            request.setAttribute("SENDER_MAIL", SENDER_MAIL.trim());
        }
        
        if (gridit_iort_ENABLEINFRASTRUCTURE.equals("checked"))
        {
            infras[1]="gridit";
            // Getting the IORT INFRASTRUCTURE from the portlet preferences for the GRIDIT VO
            String gridit_iort_INFRASTRUCTURE = portletPreferences.getValue("gridit_iort_INFRASTRUCTURE", "N/A");
            // Getting the IORT VONAME from the portlet preferences for the GRIDIT VO
            gridit_iort_VONAME = portletPreferences.getValue("gridit_iort_VONAME", "N/A");
            // Getting the IORT TOPPBDII from the portlet preferences for the GRIDIT VO
            gridit_iort_TOPBDII = portletPreferences.getValue("gridit_iort_TOPBDII", "N/A");
            // Getting the IORT WMS from the portlet preferences for the GRIDIT VO
            String[] gridit_iort_WMS = portletPreferences.getValues("gridit_iort_WMS", new String[5]);
            // Getting the IORT ETOKENSERVER from the portlet preferences for the GRIDIT VO
            String gridit_iort_ETOKENSERVER = portletPreferences.getValue("gridit_iort_ETOKENSERVER", "N/A");
            // Getting the IORT MYPROXYSERVER from the portlet preferences for the GRIDIT VO
            String gridit_iort_MYPROXYSERVER = portletPreferences.getValue("gridit_iort_MYPROXYSERVER", "N/A");
            // Getting the IORT PORT from the portlet preferences for the GRIDIT VO
            String gridit_iort_PORT = portletPreferences.getValue("gridit_iort_PORT", "N/A");
            // Getting the IORT ROBOTID from the portlet preferences for the GRIDIT VO
            String gridit_iort_ROBOTID = portletPreferences.getValue("gridit_iort_ROBOTID", "N/A");
            // Getting the IORT ROLE from the portlet preferences for the GRIDIT VO
            String gridit_iort_ROLE = portletPreferences.getValue("gridit_iort_ROLE", "N/A");
            // Getting the IORT RENEWAL from the portlet preferences for the GRIDIT VO
            String gridit_iort_RENEWAL = portletPreferences.getValue("gridit_iort_RENEWAL", "checked");
            // Getting the IORT DISABLEVOMS from the portlet preferences for the GRIDIT VO
            String gridit_iort_DISABLEVOMS = portletPreferences.getValue("gridit_iort_DISABLEVOMS", "unchecked");
            
            // Fetching all the WMS Endpoints for the GRIDIT VO
            String gridit_WMS = "";
            if (gridit_iort_ENABLEINFRASTRUCTURE.equals("checked")) {            
                if (gridit_iort_WMS!=null) {
                    //log.info("length="+gridit_iort_WMS.length);
                    for (int i = 0; i < gridit_iort_WMS.length; i++)
                        if (!(gridit_iort_WMS[i].trim().equals("N/A")) ) 
                            gridit_WMS += gridit_iort_WMS[i] + " ";                        
                } else { log.info("WMS not set for GRIDIT!"); gridit_iort_ENABLEINFRASTRUCTURE="unchecked"; }
            }
            
            // Save the portlet preferences
            request.setAttribute("gridit_iort_INFRASTRUCTURE", gridit_iort_INFRASTRUCTURE.trim());
            request.setAttribute("gridit_iort_VONAME", gridit_iort_VONAME.trim());
            request.setAttribute("gridit_iort_TOPBDII", gridit_iort_TOPBDII.trim());
            request.setAttribute("gridit_iort_WMS", gridit_WMS);
            request.setAttribute("gridit_iort_ETOKENSERVER", gridit_iort_ETOKENSERVER.trim());
            request.setAttribute("gridit_iort_MYPROXYSERVER", gridit_iort_MYPROXYSERVER.trim());
            request.setAttribute("gridit_iort_PORT", gridit_iort_PORT.trim());
            request.setAttribute("gridit_iort_ROBOTID", gridit_iort_ROBOTID.trim());
            request.setAttribute("gridit_iort_ROLE", gridit_iort_ROLE.trim());
            request.setAttribute("gridit_iort_RENEWAL", gridit_iort_RENEWAL);
            request.setAttribute("gridit_iort_DISABLEVOMS", gridit_iort_DISABLEVOMS);
            
            //request.setAttribute("iort_ENABLEINFRASTRUCTURE", iort_ENABLEINFRASTRUCTURE);
            request.setAttribute("iort_APPID", iort_APPID.trim());
            request.setAttribute("iort_LOGLEVEL", iort_LOGLEVEL.trim());
            request.setAttribute("iort_OUTPUT_PATH", iort_OUTPUT_PATH.trim());
            request.setAttribute("iort_SOFTWARE", iort_SOFTWARE.trim());
            request.setAttribute("TRACKING_DB_HOSTNAME", TRACKING_DB_HOSTNAME.trim());
            request.setAttribute("TRACKING_DB_USERNAME", TRACKING_DB_USERNAME.trim());
            request.setAttribute("TRACKING_DB_PASSWORD", TRACKING_DB_PASSWORD.trim());
            request.setAttribute("SMTP_HOST", SMTP_HOST.trim());
            request.setAttribute("SENDER_MAIL", SENDER_MAIL.trim());
        }
        
        if (eumed_iort_ENABLEINFRASTRUCTURE.equals("checked"))
        {
            infras[2]="eumed";
            // Getting the IORT INFRASTRUCTURE from the portlet preferences for the EUMED VO
            String eumed_iort_INFRASTRUCTURE = portletPreferences.getValue("eumed_iort_INFRASTRUCTURE", "N/A");
            // Getting the IORT VONAME from the portlet preferences for the EUMED VO
            eumed_iort_VONAME = portletPreferences.getValue("eumed_iort_VONAME", "N/A");
            // Getting the IORT TOPPBDII from the portlet preferences for the EUMED VO
            eumed_iort_TOPBDII = portletPreferences.getValue("eumed_iort_TOPBDII", "N/A");
            // Getting the IORT WMS from the portlet preferences for the EUMED VO
            String[] eumed_iort_WMS = portletPreferences.getValues("eumed_iort_WMS", new String[5]);
            // Getting the IORT ETOKENSERVER from the portlet preferences for the EUMED VO
            String eumed_iort_ETOKENSERVER = portletPreferences.getValue("eumed_iort_ETOKENSERVER", "N/A");
            // Getting the IORT MYPROXYSERVER from the portlet preferences for the EUMED VO
            String eumed_iort_MYPROXYSERVER = portletPreferences.getValue("eumed_iort_MYPROXYSERVER", "N/A");
            // Getting the IORT PORT from the portlet preferences for the EUMED VO
            String eumed_iort_PORT = portletPreferences.getValue("eumed_iort_PORT", "N/A");
            // Getting the IORT ROBOTID from the portlet preferences for the EUMED VO
            String eumed_iort_ROBOTID = portletPreferences.getValue("eumed_iort_ROBOTID", "N/A");
            // Getting the IORT ROLE from the portlet preferences for the EUMED VO
            String eumed_iort_ROLE = portletPreferences.getValue("eumed_iort_ROLE", "N/A");
            // Getting the IORT RENEWAL from the portlet preferences for the EUMED VO
            String eumed_iort_RENEWAL = portletPreferences.getValue("eumed_iort_RENEWAL", "checked");
            // Getting the IORT DISABLEVOMS from the portlet preferences for the EUMED VO
            String eumed_iort_DISABLEVOMS = portletPreferences.getValue("eumed_iort_DISABLEVOMS", "unchecked");
                                    
            // Fetching all the WMS Endpoints for the EUMED VO
            String eumed_WMS = "";
            if (eumed_iort_ENABLEINFRASTRUCTURE.equals("checked")) {            
                if (eumed_iort_WMS!=null) {
                    //log.info("length="+eumed_iort_WMS.length);
                    for (int i = 0; i < eumed_iort_WMS.length; i++)
                        if (!(eumed_iort_WMS[i].trim().equals("N/A")) ) 
                            eumed_WMS += eumed_iort_WMS[i] + " ";                        
                } else { log.info("WMS not set for EUMED!"); eumed_iort_ENABLEINFRASTRUCTURE="unchecked"; }
            }
            
            // Save the portlet preferences
            request.setAttribute("eumed_iort_INFRASTRUCTURE", eumed_iort_INFRASTRUCTURE.trim());
            request.setAttribute("eumed_iort_VONAME", eumed_iort_VONAME.trim());
            request.setAttribute("eumed_iort_TOPBDII", eumed_iort_TOPBDII.trim());
            request.setAttribute("eumed_iort_WMS", eumed_WMS);
            request.setAttribute("eumed_iort_ETOKENSERVER", eumed_iort_ETOKENSERVER.trim());
            request.setAttribute("eumed_iort_MYPROXYSERVER", eumed_iort_MYPROXYSERVER.trim());
            request.setAttribute("eumed_iort_PORT", eumed_iort_PORT.trim());
            request.setAttribute("eumed_iort_ROBOTID", eumed_iort_ROBOTID.trim());
            request.setAttribute("eumed_iort_ROLE", eumed_iort_ROLE.trim());
            request.setAttribute("eumed_iort_RENEWAL", eumed_iort_RENEWAL);
            request.setAttribute("eumed_iort_DISABLEVOMS", eumed_iort_DISABLEVOMS);

            //request.setAttribute("iort_ENABLEINFRASTRUCTURE", iort_ENABLEINFRASTRUCTURE);
            request.setAttribute("iort_APPID", iort_APPID.trim());
            request.setAttribute("iort_LOGLEVEL", iort_LOGLEVEL.trim());
            request.setAttribute("iort_OUTPUT_PATH", iort_OUTPUT_PATH.trim());
            request.setAttribute("iort_SOFTWARE", iort_SOFTWARE.trim());
            request.setAttribute("TRACKING_DB_HOSTNAME", TRACKING_DB_HOSTNAME.trim());
            request.setAttribute("TRACKING_DB_USERNAME", TRACKING_DB_USERNAME.trim());
            request.setAttribute("TRACKING_DB_PASSWORD", TRACKING_DB_PASSWORD.trim());
            request.setAttribute("SMTP_HOST", SMTP_HOST.trim());
            request.setAttribute("SENDER_MAIL", SENDER_MAIL.trim());
        }

        if (biomed_iort_ENABLEINFRASTRUCTURE.equals("checked"))
        {
            infras[3]="biomed";
            // Getting the IORT INFRASTRUCTURE from the portlet preferences for the BIOMED VO
            String biomed_iort_INFRASTRUCTURE = portletPreferences.getValue("biomed_iort_INFRASTRUCTURE", "N/A");
            // Getting the IORT VONAME from the portlet preferences for the biomed VO
            biomed_iort_VONAME = portletPreferences.getValue("biomed_iort_VONAME", "N/A");
            // Getting the IORT TOPPBDII from the portlet preferences for the BIOMED VO
            biomed_iort_TOPBDII = portletPreferences.getValue("biomed_iort_TOPBDII", "N/A");
            // Getting the IORT WMS from the portlet preferences for the BIOMED VO
            String[] biomed_iort_WMS = portletPreferences.getValues("biomed_iort_WMS", new String[5]);
            // Getting the IORT ETOKENSERVER from the portlet preferences for the BIOMED VO
            String biomed_iort_ETOKENSERVER = portletPreferences.getValue("biomed_iort_ETOKENSERVER", "N/A");
            // Getting the IORT MYPROXYSERVER from the portlet preferences for the BIOMED VO
            String biomed_iort_MYPROXYSERVER = portletPreferences.getValue("biomed_iort_MYPROXYSERVER", "N/A");
            // Getting the IORT PORT from the portlet preferences for the BIOMED VO
            String biomed_iort_PORT = portletPreferences.getValue("biomed_iort_PORT", "N/A");
            // Getting the IORT ROBOTID from the portlet preferences for the BIOMED VO
            String biomed_iort_ROBOTID = portletPreferences.getValue("biomed_iort_ROBOTID", "N/A");
            // Getting the IORT ROLE from the portlet preferences for the BIOMED VO
            String biomed_iort_ROLE = portletPreferences.getValue("biomed_iort_ROLE", "N/A");
            // Getting the IORT RENEWAL from the portlet preferences for the BIOMED VO
            String biomed_iort_RENEWAL = portletPreferences.getValue("biomed_iort_RENEWAL", "checked");
            // Getting the IORT DISABLEVOMS from the portlet preferences for the BIOMED VO
            String biomed_iort_DISABLEVOMS = portletPreferences.getValue("biomed_iort_DISABLEVOMS", "unchecked");              
            
            // Fetching all the WMS Endpoints for the BIOMED VO
            String biomed_WMS = "";
            if (biomed_iort_ENABLEINFRASTRUCTURE.equals("checked")) {            
                if (biomed_iort_WMS!=null) {
                    //log.info("length="+biomed_iort_WMS.length);
                    for (int i = 0; i < biomed_iort_WMS.length; i++)
                        if (!(biomed_iort_WMS[i].trim().equals("N/A")) ) 
                            biomed_WMS += biomed_iort_WMS[i] + " ";                        
                } else { log.info("WMS not set for BIOMED!"); biomed_iort_ENABLEINFRASTRUCTURE="unchecked"; }
            }
            
            // Save the portlet preferences
            request.setAttribute("biomed_iort_INFRASTRUCTURE", biomed_iort_INFRASTRUCTURE.trim());
            request.setAttribute("biomed_iort_VONAME", biomed_iort_VONAME.trim());
            request.setAttribute("biomed_iort_TOPBDII", biomed_iort_TOPBDII.trim());
            request.setAttribute("biomed_iort_WMS", biomed_WMS);
            request.setAttribute("biomed_iort_ETOKENSERVER", biomed_iort_ETOKENSERVER.trim());
            request.setAttribute("biomed_iort_MYPROXYSERVER", biomed_iort_MYPROXYSERVER.trim());
            request.setAttribute("biomed_iort_PORT", biomed_iort_PORT.trim());
            request.setAttribute("biomed_iort_ROBOTID", biomed_iort_ROBOTID.trim());
            request.setAttribute("biomed_iort_ROLE", biomed_iort_ROLE.trim());
            request.setAttribute("biomed_iort_RENEWAL", biomed_iort_RENEWAL);
            request.setAttribute("biomed_iort_DISABLEVOMS", biomed_iort_DISABLEVOMS);

            //request.setAttribute("iort_ENABLEINFRASTRUCTURE", iort_ENABLEINFRASTRUCTURE);
            request.setAttribute("iort_APPID", iort_APPID.trim());
            request.setAttribute("iort_LOGLEVEL", iort_LOGLEVEL.trim());
            request.setAttribute("iort_OUTPUT_PATH", iort_OUTPUT_PATH.trim());
            request.setAttribute("iort_SOFTWARE", iort_SOFTWARE.trim());
            request.setAttribute("TRACKING_DB_HOSTNAME", TRACKING_DB_HOSTNAME.trim());
            request.setAttribute("TRACKING_DB_USERNAME", TRACKING_DB_USERNAME.trim());
            request.setAttribute("TRACKING_DB_PASSWORD", TRACKING_DB_PASSWORD.trim());
            request.setAttribute("SMTP_HOST", SMTP_HOST.trim());
            request.setAttribute("SENDER_MAIL", SENDER_MAIL.trim());
        }
        
        // Save in the preferences the list of supported infrastructures 
        request.setAttribute("iort_ENABLEINFRASTRUCTURE", infras);

        HashMap<String,Properties> GPS_table = new HashMap<String, Properties>();
        HashMap<String,Properties> GPS_queue = new HashMap<String, Properties>();

        // ********************************************************
        List<String> CEqueues_lato = null;        
        List<String> CEqueues_gridit = null;
        List<String> CEqueues_eumed = null;
        List<String> CEqueues_biomed = null;
        
        List<String> CEs_list_lato = null;        
        List<String> CEs_list_gridit = null;        
        List<String> CEs_list_eumed = null;
        List<String> CEs_list_biomed = null;        
        
        BDII bdii = null;

        try {
                if (lato_iort_ENABLEINFRASTRUCTURE.equals("checked") && 
                   (!lato_iort_PASSWD.equals("N/A")) ) 
                {
                    log.info("-----*FETCHING*THE*<LATO>*RESOURCES*-----");
                    CEs_list_lato = new ArrayList();                    
                    CEqueues_lato = new ArrayList();
                    
                    // Fetching all the WMS Endpoints for LATO                    
                    if (lato_iort_WMS!=null) {
                        for (int i = 0; i < lato_iort_WMS.length; i++)
                            if (!(lato_iort_WMS[i].trim().equals("N/A")) ) {                                    
                                CEqueues_lato.add(lato_iort_WMS[i].trim());
                                CEs_list_lato.add(lato_iort_WMS[i].trim().replace("ssh://", ""));                                    
                            }
                    } 
                }
             
                if (gridit_iort_ENABLEINFRASTRUCTURE.equals("checked") && 
                   (!gridit_iort_TOPBDII.equals("N/A")) ) 
                {
                    log.info("-----*FETCHING*THE*<GRIDIT>*RESOURCES*-----");
                    bdii = new BDII(new URI(gridit_iort_TOPBDII));
                    CEs_list_gridit = 
                               getListofCEForSoftwareTag(gridit_iort_VONAME,
                                                         gridit_iort_TOPBDII,
                                                         iort_SOFTWARE);
                    
                    CEqueues_gridit = 
                            bdii.queryCEQueues(gridit_iort_VONAME);
                }
                
                if (eumed_iort_ENABLEINFRASTRUCTURE.equals("checked") && 
                   (!eumed_iort_TOPBDII.equals("N/A")) ) 
                {
                    log.info("-----*FETCHING*THE*<EUMED>*RESOURCES*-----");
                    bdii = new BDII(new URI(eumed_iort_TOPBDII));
                    CEs_list_eumed = 
                            getListofCEForSoftwareTag(eumed_iort_VONAME,
                                                      eumed_iort_TOPBDII,
                                                      iort_SOFTWARE);
                    
                    CEqueues_eumed = 
                            bdii.queryCEQueues(eumed_iort_VONAME);
                }
                
                if (biomed_iort_ENABLEINFRASTRUCTURE.equals("checked") &&
                   (!biomed_iort_TOPBDII.equals("N/A")) ) 
                {
                    log.info("-----*FETCHING*THE*<BIOMED>*RESOURCES*-----");
                    bdii = new BDII(new URI(biomed_iort_TOPBDII));
                    CEs_list_biomed = 
                            getListofCEForSoftwareTag(biomed_iort_VONAME,
                                                      biomed_iort_TOPBDII,
                                                      iort_SOFTWARE);
                    
                    CEqueues_biomed = 
                            bdii.queryCEQueues(biomed_iort_VONAME);
                }
                
                // Merging the list of CEs and queues
                List<String> CEs_list_TOT = new ArrayList<String>();
                if (lato_iort_ENABLEINFRASTRUCTURE.equals("checked"))
                        CEs_list_TOT.addAll(CEs_list_lato);
                if (gridit_iort_ENABLEINFRASTRUCTURE.equals("checked"))
                        CEs_list_TOT.addAll(CEs_list_gridit);
                if (eumed_iort_ENABLEINFRASTRUCTURE.equals("checked"))
                        CEs_list_TOT.addAll(CEs_list_eumed);
                if (biomed_iort_ENABLEINFRASTRUCTURE.equals("checked"))
                        CEs_list_TOT.addAll(CEs_list_biomed);
                
                List<String> CEs_queue_TOT = new ArrayList<String>();
                if (lato_iort_ENABLEINFRASTRUCTURE.equals("checked"))
                    CEs_queue_TOT.addAll(CEqueues_lato);
                if (gridit_iort_ENABLEINFRASTRUCTURE.equals("checked"))
                    CEs_queue_TOT.addAll(CEqueues_gridit);
                if (eumed_iort_ENABLEINFRASTRUCTURE.equals("checked"))
                    CEs_queue_TOT.addAll(CEqueues_eumed);
                if (biomed_iort_ENABLEINFRASTRUCTURE.equals("checked"))
                    CEs_queue_TOT.addAll(CEqueues_biomed);                
                
                //=========================================================
                // IMPORTANT: INSTANCIATE THE UsersTrackingDBInterface
                //            CLASS USING THE EMPTY CONSTRUCTOR WHEN
                //            WHEN THE PORTLET IS DEPLOYED IN PRODUCTION!!!
                //=========================================================
                UsersTrackingDBInterface DBInterface =
                        new UsersTrackingDBInterface(
                                TRACKING_DB_HOSTNAME.trim(),
                                TRACKING_DB_USERNAME.trim(),
                                TRACKING_DB_PASSWORD.trim());
                
                /*UsersTrackingDBInterface DBInterface =
                        new UsersTrackingDBInterface();*/
                    
                if ( (CEs_list_TOT != null) && (!CEs_list_TOT.isEmpty()) )
                {
                    log.info("NOT EMPTY LIST!");
                    // Fetching the list of CEs publushing the SW
                    for (String CE:CEs_list_TOT) 
                    {
                        log.info("Fetching the CE="+CE);
                        Properties coordinates = new Properties();
                        Properties queue = new Properties();

                        float coords[] = DBInterface.getCECoordinate(CE);                        

                        String GPS_LAT = Float.toString(coords[0]);
                        String GPS_LNG = Float.toString(coords[1]);

                        coordinates.setProperty("LAT", GPS_LAT);
                        coordinates.setProperty("LNG", GPS_LNG);

                        // Fetching the Queues
                        for (String CEqueue:CEs_queue_TOT) {
                                if (CEqueue.contains(CE))
                                    queue.setProperty("QUEUE", CEqueue);
                        }

                        // Saving the GPS location in a Java HashMap
                        GPS_table.put(CE, coordinates);

                        // Saving the queue in a Java HashMap
                        GPS_queue.put(CE, queue);
                    }
                } else log.info ("EMPTY LIST!");
             } catch (URISyntaxException ex) {
               Logger.getLogger(Iort.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NamingException e) {}

            // Checking the HashMap
            Set set = GPS_table.entrySet();
            Iterator iter = set.iterator();
            while ( iter.hasNext() )
            {
                Map.Entry entry = (Map.Entry)iter.next();
                log.info(" - GPS location of the CE " +
                           entry.getKey() + " => " + entry.getValue());
            }

            // Checking the HashMap
            set = GPS_queue.entrySet();
            iter = set.iterator();
            while ( iter.hasNext() )
            {
                Map.Entry entry = (Map.Entry)iter.next();
                log.info(" - Queue " +
                           entry.getKey() + " => " + entry.getValue());
            }

            Gson gson = new GsonBuilder().create();
            request.setAttribute ("GPS_table", gson.toJson(GPS_table));
            request.setAttribute ("GPS_queue", gson.toJson(GPS_queue));

            // ********************************************************
            dispatcher = getPortletContext().getRequestDispatcher("/view.jsp");       
            dispatcher.include(request, response);
    }

    // The init method will be called when installing for the first time the portlet
    // This is the right time to setup the default values into the preferences
    @Override
    public void init() throws PortletException {
        super.init();
    }

    @Override
    public void processAction(ActionRequest request,
                              ActionResponse response)
                throws PortletException, IOException {

        String action = "";

        // Getting the action to be processed from the request
        action = request.getParameter("ActionEvent");

        // Determine the username and the email address
        ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);        
        User user = themeDisplay.getUser();
        String username = user.getScreenName();
        String emailAddress = user.getDisplayEmailAddress();        

        PortletPreferences portletPreferences =
                (PortletPreferences) request.getPreferences();

        if (action.equals("CONFIG_IORT_PORTLET")) {
            log.info("\nPROCESS ACTION => " + action);
            
            // Getting the IORT APPID from the portlet request
            String iort_APPID = request.getParameter("iort_APPID");
            // Get the LOGLEVEL from the portlet request
            String iort_LOGLEVEL = request.getParameter("iort_LOGLEVEL");
            // Getting the IORT OUTPUT_PATH from the portlet request
            String iort_OUTPUT_PATH = request.getParameter("iort_OUTPUT_PATH");
            // Getting the IORT SOFTWARE from the portlet request
            String iort_SOFTWARE = request.getParameter("iort_SOFTWARE");
            // Getting the TRACKING_DB_HOSTNAME from the portlet request
            String TRACKING_DB_HOSTNAME = request.getParameter("TRACKING_DB_HOSTNAME");
            // Getting the TRACKING_DB_USERNAME from the portlet request
            String TRACKING_DB_USERNAME = request.getParameter("TRACKING_DB_USERNAME");
            // Getting the TRACKING_DB_PASSWORD from the portlet request
            String TRACKING_DB_PASSWORD = request.getParameter("TRACKING_DB_PASSWORD");
            // Getting the SMTP_HOST from the portlet request
            String SMTP_HOST = request.getParameter("SMTP_HOST");
            // Getting the SENDER_MAIL from the portlet request
            String SENDER_MAIL = request.getParameter("SENDER_MAIL");
            String[] infras = new String[4];
            
            String lato_iort_ENABLEINFRASTRUCTURE = "unchecked";
            String gridit_iort_ENABLEINFRASTRUCTURE = "unchecked";
            String eumed_iort_ENABLEINFRASTRUCTURE = "unchecked";
            String biomed_iort_ENABLEINFRASTRUCTURE = "unchecked";
            
            String[] iort_INFRASTRUCTURES = request.getParameterValues("iort_ENABLEINFRASTRUCTURES");         

            if (iort_INFRASTRUCTURES != null) {
                Arrays.sort(iort_INFRASTRUCTURES);
                lato_iort_ENABLEINFRASTRUCTURE =
                    Arrays.binarySearch(iort_INFRASTRUCTURES, "lato") >= 0 ? "checked" : "unchecked";
                gridit_iort_ENABLEINFRASTRUCTURE =
                    Arrays.binarySearch(iort_INFRASTRUCTURES, "gridit") >= 0 ? "checked" : "unchecked";
                eumed_iort_ENABLEINFRASTRUCTURE =
                    Arrays.binarySearch(iort_INFRASTRUCTURES, "eumed") >= 0 ? "checked" : "unchecked";
                biomed_iort_ENABLEINFRASTRUCTURE =
                    Arrays.binarySearch(iort_INFRASTRUCTURES, "biomed") >= 0 ? "checked" : "unchecked";
            }           
            
            if (lato_iort_ENABLEINFRASTRUCTURE.equals("checked"))
            {
                infras[0]="lato";
                 // Getting the IORT INFRASTRUCTURE from the portlet request for LATO
                String lato_iort_INFRASTRUCTURE = request.getParameter("lato_iort_INFRASTRUCTURE");
                // Getting the IORT VONAME from the portlet request for LATO
                String lato_iort_LOGIN = request.getParameter("lato_iort_LOGIN");
                // Getting the IORT TOPBDII from the portlet request for LATO
                String lato_iort_PASSWD = request.getParameter("lato_iort_PASSWD");
                // Getting the IORT WMS from the portlet request for LATO
                String[] lato_iort_WMS = request.getParameterValues("lato_iort_WMS");
                // Getting the IORT ETOKENSERVER from the portlet request for LATO
                String lato_iort_ETOKENSERVER = request.getParameter("lato_iort_ETOKENSERVER");
                // Getting the IORT MYPROXYSERVER from the portlet request for LATO
                String lato_iort_MYPROXYSERVER = request.getParameter("lato_iort_MYPROXYSERVER");
                // Getting the IORT PORT from the portlet request for LATO
                String lato_iort_PORT = request.getParameter("lato_iort_PORT");
                // Getting the IORT ROBOTID from the portlet request for LATO
                String lato_iort_ROBOTID = request.getParameter("lato_iort_ROBOTID");
                // Getting the IORT ROLE from the portlet request for LATO
                String lato_iort_ROLE = request.getParameter("lato_iort_ROLE");
                // Getting the IORT OPTIONS from the portlet request for LATO
                String[] lato_iort_OPTIONS = request.getParameterValues("lato_iort_OPTIONS");

                String lato_iort_RENEWAL = "";
                String lato_iort_DISABLEVOMS = "";

                if (lato_iort_OPTIONS == null){
                    lato_iort_RENEWAL = "checked";
                    lato_iort_DISABLEVOMS = "unchecked";
                } else {
                    Arrays.sort(lato_iort_OPTIONS);
                    // Getting the IORT RENEWAL from the portlet preferences for LATO
                    lato_iort_RENEWAL = Arrays.binarySearch(lato_iort_OPTIONS, "enableRENEWAL") >= 0 ? "checked" : "unchecked";
                    // Getting the IORT DISABLEVOMS from the portlet preferences for LATO
                    lato_iort_DISABLEVOMS = Arrays.binarySearch(lato_iort_OPTIONS, "disableVOMS") >= 0 ? "checked" : "unchecked";
                }
                
                int nmax=0;                
                for (int i = 0; i < lato_iort_WMS.length; i++)
                    if ( lato_iort_WMS[i]!=null && (!lato_iort_WMS[i].trim().equals("N/A")) )                        
                        nmax++;
                
                log.info("\n\nLength="+nmax);
                String[] lato_iort_WMS_trimmed = new String[nmax];
                for (int i = 0; i < nmax; i++)
                {
                    lato_iort_WMS_trimmed[i]=lato_iort_WMS[i].trim();
                    log.info ("\n\nLATO [" + i + "] WMS=[" + lato_iort_WMS_trimmed[i] + "]");
                }
                
                // Set the portlet preferences
                portletPreferences.setValue("lato_iort_INFRASTRUCTURE", lato_iort_INFRASTRUCTURE.trim());
                portletPreferences.setValue("lato_iort_LOGIN", lato_iort_LOGIN.trim());
                portletPreferences.setValue("lato_iort_PASSWD", lato_iort_PASSWD.trim());
                portletPreferences.setValues("lato_iort_WMS", lato_iort_WMS_trimmed);
                portletPreferences.setValue("lato_iort_ETOKENSERVER", lato_iort_ETOKENSERVER.trim());
                portletPreferences.setValue("lato_iort_MYPROXYSERVER", lato_iort_MYPROXYSERVER.trim());
                portletPreferences.setValue("lato_iort_PORT", lato_iort_PORT.trim());
                portletPreferences.setValue("lato_iort_ROBOTID", lato_iort_ROBOTID.trim());
                portletPreferences.setValue("lato_iort_ROLE", lato_iort_ROLE.trim());
                portletPreferences.setValue("lato_iort_RENEWAL", lato_iort_RENEWAL);
                portletPreferences.setValue("lato_iort_DISABLEVOMS", lato_iort_DISABLEVOMS);                
                
                portletPreferences.setValue("iort_APPID", iort_APPID.trim());
                portletPreferences.setValue("iort_LOGLEVEL", iort_LOGLEVEL.trim());
                portletPreferences.setValue("iort_OUTPUT_PATH", iort_OUTPUT_PATH.trim());
                portletPreferences.setValue("iort_SOFTWARE", iort_SOFTWARE.trim());
                portletPreferences.setValue("TRACKING_DB_HOSTNAME", TRACKING_DB_HOSTNAME.trim());
                portletPreferences.setValue("TRACKING_DB_USERNAME", TRACKING_DB_USERNAME.trim());
                portletPreferences.setValue("TRACKING_DB_PASSWORD", TRACKING_DB_PASSWORD.trim());
                portletPreferences.setValue("SMTP_HOST", SMTP_HOST.trim());
                portletPreferences.setValue("SENDER_MAIL", SENDER_MAIL.trim());
                
                if (iort_LOGLEVEL.trim().equals("VERBOSE")) {
                log.info("\n\nPROCESS ACTION => " + action
                    + "\n- Storing the IORT portlet preferences ..."
                    + "\nlato_iort_INFRASTRUCTURE: " + lato_iort_INFRASTRUCTURE
                    + "\nlato_iort_LOGIN: " + lato_iort_LOGIN
                    + "\nlato_iort_PASSWD: " + lato_iort_PASSWD                    
                    + "\nlato_iort_ETOKENSERVER: " + lato_iort_ETOKENSERVER
                    + "\nlato_iort_MYPROXYSERVER: " + lato_iort_MYPROXYSERVER
                    + "\nlato_iort_PORT: " + lato_iort_PORT
                    + "\nlato_iort_ROBOTID: " + lato_iort_ROBOTID
                    + "\nlato_iort_ROLE: " + lato_iort_ROLE
                    + "\nlato_iort_RENEWAL: " + lato_iort_RENEWAL
                    + "\nlato_iort_DISABLEVOMS: " + lato_iort_DISABLEVOMS
                        
                    + "\n\niort_ENABLEINFRASTRUCTURE: " + "lato"
                    + "\niort_APPID: " + iort_APPID
                    + "\niort_LOGLEVEL: " + iort_LOGLEVEL
                    + "\niort_OUTPUT_PATH: " + iort_OUTPUT_PATH
                    + "\niort_SOFTWARE: " + iort_SOFTWARE
                    + "\nTracking_DB_Hostname: " + TRACKING_DB_HOSTNAME
                    + "\nTracking_DB_Username: " + TRACKING_DB_USERNAME
                    + "\nTracking_DB_Password: " + TRACKING_DB_PASSWORD
                    + "\nSMTP_HOST: " + SMTP_HOST
                    + "\nSENDER_MAIL: " + SENDER_MAIL);
                }
            }

            if (gridit_iort_ENABLEINFRASTRUCTURE.equals("checked"))
            {
                infras[1]="gridit";
                 // Getting the IORT INFRASTRUCTURE from the portlet request for the GRIDIT VO
                String gridit_iort_INFRASTRUCTURE = request.getParameter("gridit_iort_INFRASTRUCTURE");
                // Getting the IORT VONAME from the portlet request for the GRIDIT VO
                String gridit_iort_VONAME = request.getParameter("gridit_iort_VONAME");
                // Getting the IORT TOPBDII from the portlet request for the GRIDIT VO
                String gridit_iort_TOPBDII = request.getParameter("gridit_iort_TOPBDII");
                // Getting the IORT WMS from the portlet request for the GRIDIT VO
                String[] gridit_iort_WMS = request.getParameterValues("gridit_iort_WMS");
                // Getting the IORT ETOKENSERVER from the portlet request for the GRIDIT VO
                String gridit_iort_ETOKENSERVER = request.getParameter("gridit_iort_ETOKENSERVER");
                // Getting the IORT MYPROXYSERVER from the portlet request for the GRIDIT VO
                String gridit_iort_MYPROXYSERVER = request.getParameter("gridit_iort_MYPROXYSERVER");
                // Getting the IORT PORT from the portlet request for the GRIDIT VO
                String gridit_iort_PORT = request.getParameter("gridit_iort_PORT");
                // Getting the IORT ROBOTID from the portlet request for the GRIDIT VO
                String gridit_iort_ROBOTID = request.getParameter("gridit_iort_ROBOTID");
                // Getting the IORT ROLE from the portlet request for the GRIDIT VO
                String gridit_iort_ROLE = request.getParameter("gridit_iort_ROLE");
                // Getting the IORT OPTIONS from the portlet request for the GRIDIT VO
                String[] gridit_iort_OPTIONS = request.getParameterValues("gridit_iort_OPTIONS");

                String gridit_iort_RENEWAL = "";
                String gridit_iort_DISABLEVOMS = "";

                if (gridit_iort_OPTIONS == null){
                    gridit_iort_RENEWAL = "checked";
                    gridit_iort_DISABLEVOMS = "unchecked";
                } else {
                    Arrays.sort(gridit_iort_OPTIONS);
                    // Getting the IORT RENEWAL from the portlet preferences for the GRIDIT VO
                    gridit_iort_RENEWAL = Arrays.binarySearch(gridit_iort_OPTIONS, "enableRENEWAL") >= 0 ? "checked" : "unchecked";
                    // Getting the IORT DISABLEVOMS from the portlet preferences for the GRIDIT VO
                    gridit_iort_DISABLEVOMS = Arrays.binarySearch(gridit_iort_OPTIONS, "disableVOMS") >= 0 ? "checked" : "unchecked";
                }
                
                int nmax=0;                
                for (int i = 0; i < gridit_iort_WMS.length; i++)
                    if ( gridit_iort_WMS[i]!=null && (!gridit_iort_WMS[i].trim().equals("N/A")) )                        
                        nmax++;
                
                log.info("\n\nLength="+nmax);
                String[] gridit_iort_WMS_trimmed = new String[nmax];
                for (int i = 0; i < nmax; i++)
                {
                    gridit_iort_WMS_trimmed[i]=gridit_iort_WMS[i].trim();
                    log.info ("\n\nLATO [" + i + "] WMS=[" + gridit_iort_WMS_trimmed[i] + "]");
                }
                
                // Set the portlet preferences
                portletPreferences.setValue("gridit_iort_INFRASTRUCTURE", gridit_iort_INFRASTRUCTURE.trim());
                portletPreferences.setValue("gridit_iort_VONAME", gridit_iort_VONAME.trim());
                portletPreferences.setValue("gridit_iort_TOPBDII", gridit_iort_TOPBDII.trim());
                portletPreferences.setValues("gridit_iort_WMS", gridit_iort_WMS_trimmed);
                portletPreferences.setValue("gridit_iort_ETOKENSERVER", gridit_iort_ETOKENSERVER.trim());
                portletPreferences.setValue("gridit_iort_MYPROXYSERVER", gridit_iort_MYPROXYSERVER.trim());
                portletPreferences.setValue("gridit_iort_PORT", gridit_iort_PORT.trim());
                portletPreferences.setValue("gridit_iort_ROBOTID", gridit_iort_ROBOTID.trim());
                portletPreferences.setValue("gridit_iort_ROLE", gridit_iort_ROLE.trim());
                portletPreferences.setValue("gridit_iort_RENEWAL", gridit_iort_RENEWAL);
                portletPreferences.setValue("gridit_iort_DISABLEVOMS", gridit_iort_DISABLEVOMS);                
                
                portletPreferences.setValue("iort_APPID", iort_APPID.trim());
                portletPreferences.setValue("iort_LOGLEVEL", iort_LOGLEVEL.trim());
                portletPreferences.setValue("iort_OUTPUT_PATH", iort_OUTPUT_PATH.trim());
                portletPreferences.setValue("iort_SOFTWARE", iort_SOFTWARE.trim());
                portletPreferences.setValue("TRACKING_DB_HOSTNAME", TRACKING_DB_HOSTNAME.trim());
                portletPreferences.setValue("TRACKING_DB_USERNAME", TRACKING_DB_USERNAME.trim());
                portletPreferences.setValue("TRACKING_DB_PASSWORD", TRACKING_DB_PASSWORD.trim());
                portletPreferences.setValue("SMTP_HOST", SMTP_HOST.trim());
                portletPreferences.setValue("SENDER_MAIL", SENDER_MAIL.trim());
                
                if (iort_LOGLEVEL.trim().equals("VERBOSE")) {
                log.info("\n\nPROCESS ACTION => " + action
                    + "\n- Storing the IORT portlet preferences ..."
                    + "\ngridit_iort_INFRASTRUCTURE: " + gridit_iort_INFRASTRUCTURE
                    + "\ngridit_iort_VONAME: " + gridit_iort_VONAME
                    + "\ngridit_iort_TOPBDII: " + gridit_iort_TOPBDII                    
                    + "\ngridit_iort_ETOKENSERVER: " + gridit_iort_ETOKENSERVER
                    + "\ngridit_iort_MYPROXYSERVER: " + gridit_iort_MYPROXYSERVER
                    + "\ngridit_iort_PORT: " + gridit_iort_PORT
                    + "\ngridit_iort_ROBOTID: " + gridit_iort_ROBOTID
                    + "\ngridit_iort_ROLE: " + gridit_iort_ROLE
                    + "\ngridit_iort_RENEWAL: " + gridit_iort_RENEWAL
                    + "\ngridit_iort_DISABLEVOMS: " + gridit_iort_DISABLEVOMS
                        
                    + "\n\niort_ENABLEINFRASTRUCTURE: " + "gridit"
                    + "\niort_APPID: " + iort_APPID
                    + "\niort_LOGLEVEL: " + iort_LOGLEVEL
                    + "\niort_OUTPUT_PATH: " + iort_OUTPUT_PATH
                    + "\niort_SOFTWARE: " + iort_SOFTWARE
                    + "\nTracking_DB_Hostname: " + TRACKING_DB_HOSTNAME
                    + "\nTracking_DB_Username: " + TRACKING_DB_USERNAME
                    + "\nTracking_DB_Password: " + TRACKING_DB_PASSWORD
                    + "\nSMTP_HOST: " + SMTP_HOST
                    + "\nSENDER_MAIL: " + SENDER_MAIL);
                }
            }

            if (eumed_iort_ENABLEINFRASTRUCTURE.equals("checked"))
            {
                infras[2]="eumed";
                // Getting the IORT INFRASTRUCTURE from the portlet request for the EUMED VO
                String eumed_iort_INFRASTRUCTURE = request.getParameter("eumed_iort_INFRASTRUCTURE");
                // Getting the IORT VONAME from the portlet request for the EUMED VO
                String eumed_iort_VONAME = request.getParameter("eumed_iort_VONAME");
                // Getting the IORT TOPBDII from the portlet request for the EUMED VO
                String eumed_iort_TOPBDII = request.getParameter("eumed_iort_TOPBDII");
                // Getting the IORT WMS from the portlet request for the EUMED VO
                String[] eumed_iort_WMS = request.getParameterValues("eumed_iort_WMS");
                // Getting the IORT ETOKENSERVER from the portlet request for the EUMED VO
                String eumed_iort_ETOKENSERVER = request.getParameter("eumed_iort_ETOKENSERVER");
                // Getting the IORT MYPROXYSERVER from the portlet request for the EUMED VO
                String eumed_iort_MYPROXYSERVER = request.getParameter("eumed_iort_MYPROXYSERVER");
                // Getting the IORT PORT from the portlet request for the EUMED VO
                String eumed_iort_PORT = request.getParameter("eumed_iort_PORT");
                // Getting the IORT ROBOTID from the portlet request for the EUMED VO
                String eumed_iort_ROBOTID = request.getParameter("eumed_iort_ROBOTID");
                // Getting the IORT ROLE from the portlet request for the EUMED VO
                String eumed_iort_ROLE = request.getParameter("eumed_iort_ROLE");
                // Getting the IORT OPTIONS from the portlet request for the EUMED VO
                String[] eumed_iort_OPTIONS = request.getParameterValues("eumed_iort_OPTIONS");

                String eumed_iort_RENEWAL = "";
                String eumed_iort_DISABLEVOMS = "";

                if (eumed_iort_OPTIONS == null){
                    eumed_iort_RENEWAL = "checked";
                    eumed_iort_DISABLEVOMS = "unchecked";
                } else {
                    Arrays.sort(eumed_iort_OPTIONS);
                    // Getting the IORT RENEWAL from the portlet preferences for the EUMED VO
                    eumed_iort_RENEWAL = Arrays.binarySearch(eumed_iort_OPTIONS, "enableRENEWAL") >= 0 ? "checked" : "unchecked";
                    // Getting the IORT DISABLEVOMS from the portlet preferences for the GRIDIT VO
                    eumed_iort_DISABLEVOMS = Arrays.binarySearch(eumed_iort_OPTIONS, "disableVOMS") >= 0 ? "checked" : "unchecked";
                }
                
                int nmax=0;                
                for (int i = 0; i < eumed_iort_WMS.length; i++)
                    if ( eumed_iort_WMS[i]!=null && (!eumed_iort_WMS[i].trim().equals("N/A")) )                        
                        nmax++;
                
                log.info("\n\nLength="+nmax);
                String[] eumed_iort_WMS_trimmed = new String[nmax];
                for (int i = 0; i < nmax; i++)
                {
                    eumed_iort_WMS_trimmed[i]=eumed_iort_WMS[i].trim();
                    log.info ("\n\nEUMED [" + i + "] WMS=[" + eumed_iort_WMS_trimmed[i] + "]");
                }
                
                // Set the portlet preferences
                portletPreferences.setValue("eumed_iort_INFRASTRUCTURE", eumed_iort_INFRASTRUCTURE.trim());
                portletPreferences.setValue("eumed_iort_VONAME", eumed_iort_VONAME.trim());
                portletPreferences.setValue("eumed_iort_TOPBDII", eumed_iort_TOPBDII.trim());
                portletPreferences.setValues("eumed_iort_WMS", eumed_iort_WMS_trimmed);
                portletPreferences.setValue("eumed_iort_ETOKENSERVER", eumed_iort_ETOKENSERVER.trim());
                portletPreferences.setValue("eumed_iort_MYPROXYSERVER", eumed_iort_MYPROXYSERVER.trim());
                portletPreferences.setValue("eumed_iort_PORT", eumed_iort_PORT.trim());
                portletPreferences.setValue("eumed_iort_ROBOTID", eumed_iort_ROBOTID.trim());
                portletPreferences.setValue("eumed_iort_ROLE", eumed_iort_ROLE.trim());
                portletPreferences.setValue("eumed_iort_RENEWAL", eumed_iort_RENEWAL);
                portletPreferences.setValue("eumed_iort_DISABLEVOMS", eumed_iort_DISABLEVOMS); 
                
                portletPreferences.setValue("iort_APPID", iort_APPID.trim());
                portletPreferences.setValue("iort_LOGLEVEL", iort_LOGLEVEL.trim());
                portletPreferences.setValue("iort_OUTPATH_PATH", iort_OUTPUT_PATH.trim());
                portletPreferences.setValue("iort_SOFTWARE", iort_SOFTWARE.trim());
                portletPreferences.setValue("TRACKING_DB_HOSTNAME", TRACKING_DB_HOSTNAME.trim());
                portletPreferences.setValue("TRACKING_DB_USERNAME", TRACKING_DB_USERNAME.trim());
                portletPreferences.setValue("TRACKING_DB_PASSWORD", TRACKING_DB_PASSWORD.trim());
                portletPreferences.setValue("SMTP_HOST", SMTP_HOST.trim());
                portletPreferences.setValue("SENDER_MAIL", SENDER_MAIL.trim());
                
                if (iort_LOGLEVEL.trim().equals("VERBOSE")) {
                log.info("\n\nPROCESS ACTION => " + action
                    + "\n- Storing the IORT portlet preferences ..."                    
                    + "\n\neumed_iort_INFRASTRUCTURE: " + eumed_iort_INFRASTRUCTURE
                    + "\neumed_iort_VONAME: " + eumed_iort_VONAME
                    + "\neumed_iort_TOPBDII: " + eumed_iort_TOPBDII                    
                    + "\neumed_iort_ETOKENSERVER: " + eumed_iort_ETOKENSERVER
                    + "\neumed_iort_MYPROXYSERVER: " + eumed_iort_MYPROXYSERVER
                    + "\neumed_iort_PORT: " + eumed_iort_PORT
                    + "\neumed_iort_ROBOTID: " + eumed_iort_ROBOTID
                    + "\neumed_iort_ROLE: " + eumed_iort_ROLE
                    + "\neumed_iort_RENEWAL: " + eumed_iort_RENEWAL
                    + "\neumed_iort_DISABLEVOMS: " + eumed_iort_DISABLEVOMS

                    + "\n\niort_ENABLEINFRASTRUCTURE: " + "eumed"
                    + "\niort_APPID: " + iort_APPID
                    + "\niort_LOGLEVEL: " + iort_LOGLEVEL
                    + "\niort_OUTPUT_PATH: " + iort_OUTPUT_PATH
                    + "\niort_SOFTWARE: " + iort_SOFTWARE
                    + "\nTracking_DB_Hostname: " + TRACKING_DB_HOSTNAME
                    + "\nTracking_DB_Username: " + TRACKING_DB_USERNAME
                    + "\nTracking_DB_Password: " + TRACKING_DB_PASSWORD
                    + "\nSMTP_HOST: " + SMTP_HOST
                    + "\nSENDER_MAIL: " + SENDER_MAIL);
                }
            }

            if (biomed_iort_ENABLEINFRASTRUCTURE.equals("checked"))
            {
                infras[3]="biomed";
                // Getting the IORT INFRASTRUCTURE from the portlet request for the BIOMED VO
                String biomed_iort_INFRASTRUCTURE = request.getParameter("biomed_iort_INFRASTRUCTURE");
                // Getting the IORT VONAME from the portlet request for the BIOMED VO
                String biomed_iort_VONAME = request.getParameter("biomed_iort_VONAME");
                // Getting the IORT TOPBDII from the portlet request for the BIOMED VO
                String biomed_iort_TOPBDII = request.getParameter("biomed_iort_TOPBDII");
                // Getting the IORT WMS from the portlet request for the BIOMED VO
                String[] biomed_iort_WMS = request.getParameterValues("biomed_iort_WMS");
                // Getting the IORT ETOKENSERVER from the portlet request for the BIOMED VO
                String biomed_iort_ETOKENSERVER = request.getParameter("biomed_iort_ETOKENSERVER");
                // Getting the IORT MYPROXYSERVER from the portlet request for the BIOMED VO
                String biomed_iort_MYPROXYSERVER = request.getParameter("biomed_iort_MYPROXYSERVER");
                // Getting the IORT PORT from the portlet request for the BIOMED VO
                String biomed_iort_PORT = request.getParameter("biomed_iort_PORT");
                // Getting the IORT ROBOTID from the portlet request for the BIOMED VO
                String biomed_iort_ROBOTID = request.getParameter("biomed_iort_ROBOTID");
                // Getting the IORT ROLE from the portlet request for the BIOMED VO
                String biomed_iort_ROLE = request.getParameter("biomed_iort_ROLE");
                // Getting the IORT OPTIONS from the portlet request for the BIOMED VO
                String[] biomed_iort_OPTIONS = request.getParameterValues("biomed_iort_OPTIONS");

                String biomed_iort_RENEWAL = "";
                String biomed_iort_DISABLEVOMS = "";

                if (biomed_iort_OPTIONS == null){
                    biomed_iort_RENEWAL = "checked";
                    biomed_iort_DISABLEVOMS = "unchecked";
                } else {
                    Arrays.sort(biomed_iort_OPTIONS);
                    // Get the IORT RENEWAL from the portlet preferences for the BIOMED VO
                    biomed_iort_RENEWAL = Arrays.binarySearch(biomed_iort_OPTIONS, "enableRENEWAL") >= 0 ? "checked" : "unchecked";
                    // Get the IORT DISABLEVOMS from the portlet preferences for the BIOMED VO
                    biomed_iort_DISABLEVOMS = Arrays.binarySearch(biomed_iort_OPTIONS, "disableVOMS") >= 0 ? "checked" : "unchecked";
                }
                
                int nmax=0;                
                for (int i = 0; i < biomed_iort_WMS.length; i++)
                    if ( biomed_iort_WMS[i]!=null && (!biomed_iort_WMS[i].trim().equals("N/A")) )                        
                        nmax++;
                
                log.info("\n\nLength="+nmax);
                String[] biomed_iort_WMS_trimmed = new String[nmax];
                for (int i = 0; i < nmax; i++)
                {
                    biomed_iort_WMS_trimmed[i]=biomed_iort_WMS[i].trim();
                    log.info ("\n\nBIOMED [" + i + "] WMS=[" + biomed_iort_WMS_trimmed[i] + "]");
                }

                // Set the portlet preferences
                portletPreferences.setValue("biomed_iort_INFRASTRUCTURE", biomed_iort_INFRASTRUCTURE.trim());
                portletPreferences.setValue("biomed_iort_VONAME", biomed_iort_VONAME.trim());
                portletPreferences.setValue("biomed_iort_TOPBDII", biomed_iort_TOPBDII.trim());
                portletPreferences.setValues("biomed_iort_WMS", biomed_iort_WMS_trimmed);
                portletPreferences.setValue("biomed_iort_ETOKENSERVER", biomed_iort_ETOKENSERVER.trim());
                portletPreferences.setValue("biomed_iort_MYPROXYSERVER", biomed_iort_MYPROXYSERVER.trim());
                portletPreferences.setValue("biomed_iort_PORT", biomed_iort_PORT.trim());
                portletPreferences.setValue("biomed_iort_ROBOTID", biomed_iort_ROBOTID.trim());
                portletPreferences.setValue("biomed_iort_ROLE", biomed_iort_ROLE.trim());
                portletPreferences.setValue("biomed_iort_RENEWAL", biomed_iort_RENEWAL);
                portletPreferences.setValue("biomed_iort_DISABLEVOMS", biomed_iort_DISABLEVOMS);
                
                portletPreferences.setValue("iort_APPID", iort_APPID.trim());
                portletPreferences.setValue("iort_LOGLEVEL", iort_LOGLEVEL.trim());
                portletPreferences.setValue("iort_OUTPUT_PATH", iort_OUTPUT_PATH.trim());
                portletPreferences.setValue("iort_SOFTWARE", iort_SOFTWARE.trim());
                portletPreferences.setValue("TRACKING_DB_HOSTNAME", TRACKING_DB_HOSTNAME.trim());
                portletPreferences.setValue("TRACKING_DB_USERNAME", TRACKING_DB_USERNAME.trim());
                portletPreferences.setValue("TRACKING_DB_PASSWORD", TRACKING_DB_PASSWORD.trim());
                portletPreferences.setValue("SMTP_HOST", SMTP_HOST.trim());
                portletPreferences.setValue("SENDER_MAIL", SENDER_MAIL.trim());
                
                if (iort_LOGLEVEL.trim().equals("VERBOSE")) {
                log.info("\n\nPROCESS ACTION => " + action
                    + "\n- Storing the IORT portlet preferences ..."                    
                    + "\n\nbiomed_iort_INFRASTRUCTURE: " + biomed_iort_INFRASTRUCTURE
                    + "\nbiomed_iort_VONAME: " + biomed_iort_VONAME
                    + "\nbiomed_iort_TOPBDII: " + biomed_iort_TOPBDII                    
                    + "\nbiomed_iort_ETOKENSERVER: " + biomed_iort_ETOKENSERVER
                    + "\nbiomed_iort_MYPROXYSERVER: " + biomed_iort_MYPROXYSERVER
                    + "\nbiomed_iort_PORT: " + biomed_iort_PORT
                    + "\nbiomed_iort_ROBOTID: " + biomed_iort_ROBOTID
                    + "\nbiomed_iort_ROLE: " + biomed_iort_ROLE
                    + "\nbiomed_iort_RENEWAL: " + biomed_iort_RENEWAL
                    + "\nbiomed_iort_DISABLEVOMS: " + biomed_iort_DISABLEVOMS

                    + "\n\niort_ENABLEINFRASTRUCTURE: " + "biomed"
                    + "\niort_APPID: " + iort_APPID
                    + "\niort_LOGLEVEL: " + iort_LOGLEVEL
                    + "\niort_OUTPUT_PATH: " + iort_OUTPUT_PATH
                    + "\niort_SOFTWARE: " + iort_SOFTWARE
                    + "\nTracking_DB_Hostname: " + TRACKING_DB_HOSTNAME
                    + "\nTracking_DB_Username: " + TRACKING_DB_USERNAME
                    + "\nTracking_DB_Password: " + TRACKING_DB_PASSWORD
                    + "\nSMTP_HOST: " + SMTP_HOST
                    + "\nSENDER_MAIL: " + SENDER_MAIL);
                }
            }
            
            for (int i=0; i<infras.length; i++)
            log.info("\n - Infrastructure Enabled = " + infras[i]);
            portletPreferences.setValues("iort_ENABLEINFRASTRUCTURE", infras);
            portletPreferences.setValue("lato_iort_ENABLEINFRASTRUCTURE",infras[0]);            
            portletPreferences.setValue("gridit_iort_ENABLEINFRASTRUCTURE",infras[1]);            
            portletPreferences.setValue("eumed_iort_ENABLEINFRASTRUCTURE",infras[2]);
            portletPreferences.setValue("biomed_iort_ENABLEINFRASTRUCTURE",infras[3]);            

            portletPreferences.store();
            response.setPortletMode(PortletMode.VIEW);
        } // end PROCESS ACTION [ CONFIG_IORT_PORTLET ]
        

        if (action.equals("SUBMIT_IORT_PORTLET")) {
            log.info("\nPROCESS ACTION => " + action);            
            InfrastructureInfo infrastructures[] = new InfrastructureInfo[4];
            int MAX=0;
            
            // Getting the IORT APPID from the portlet preferences
            String iort_APPID = portletPreferences.getValue("iort_APPID", "N/A");
            // Get the LOGLEVEL from the portlet preferences
            String iort_LOGLEVEL = portletPreferences.getValue("iort_LOGLEVEL", "INFO");
            // Getting the IORT OUTPUT_PATH from the portlet preferences
            String iort_OUTPUT_PATH = portletPreferences.getValue("iort_OUTPUT_PATH", "/tmp");
            // Getting the IORT SOFTWARE from the portlet preferences
            String iort_SOFTWARE = portletPreferences.getValue("iort_SOFTWARE", "N/A");
            // Getting the TRACKING_DB_HOSTNAME from the portlet request
            String TRACKING_DB_HOSTNAME = portletPreferences.getValue("TRACKING_DB_HOSTNAME", "N/A");
            // Getting the TRACKING_DB_USERNAME from the portlet request
            String TRACKING_DB_USERNAME = portletPreferences.getValue("TRACKING_DB_USERNAME", "N/A");
            // Getting the TRACKING_DB_PASSWORD from the portlet request
            String TRACKING_DB_PASSWORD = portletPreferences.getValue("TRACKING_DB_PASSWORD","N/A");
            // Getting the SMTP_HOST from the portlet request
            String SMTP_HOST = portletPreferences.getValue("SMTP_HOST","N/A");
            // Getting the SENDER_MAIL from the portlet request
            String SENDER_MAIL = portletPreferences.getValue("SENDER_MAIL","N/A");
            
            String lato_iort_ENABLEINFRASTRUCTURE =
                    portletPreferences.getValue("lato_iort_ENABLEINFRASTRUCTURE","null");
            String gridit_iort_ENABLEINFRASTRUCTURE =
                    portletPreferences.getValue("gridit_iort_ENABLEINFRASTRUCTURE","null");
            String eumed_iort_ENABLEINFRASTRUCTURE =
                    portletPreferences.getValue("eumed_iort_ENABLEINFRASTRUCTURE","null");
            String biomed_iort_ENABLEINFRASTRUCTURE =
                    portletPreferences.getValue("biomed_iort_ENABLEINFRASTRUCTURE","null");
            
            if (lato_iort_ENABLEINFRASTRUCTURE != null &&
                lato_iort_ENABLEINFRASTRUCTURE.equals("lato"))
            {
                MAX++;
                // Getting the IORT VONAME from the portlet preferences for LATO
                String lato_iort_INFRASTRUCTURE = portletPreferences.getValue("lato_iort_INFRASTRUCTURE", "N/A");
                // Getting the IORT VONAME from the portlet preferences for LATO
                String lato_iort_LOGIN = portletPreferences.getValue("lato_iort_LOGIN", "N/A");
                // Getting the IORT TOPPBDII from the portlet preferences for LATO
                String lato_iort_PASSWD = portletPreferences.getValue("lato_iort_PASSWD", "N/A");
                // Getting the IORT WMS from the portlet preferences for LATO                
                String[] lato_iort_WMS = portletPreferences.getValues("lato_iort_WMS", new String[5]);
                // Getting the IORT ETOKENSERVER from the portlet preferences for LATO
                String lato_iort_ETOKENSERVER = portletPreferences.getValue("lato_iort_ETOKENSERVER", "N/A");
                // Getting the IORT MYPROXYSERVER from the portlet preferences for LATO
                String lato_iort_MYPROXYSERVER = portletPreferences.getValue("lato_iort_MYPROXYSERVER", "N/A");
                // Getting the IORT PORT from the portlet preferences for LATO
                String lato_iort_PORT = portletPreferences.getValue("lato_iort_PORT", "N/A");
                // Getting the IORT ROBOTID from the portlet preferences for LATO
                String lato_iort_ROBOTID = portletPreferences.getValue("lato_iort_ROBOTID", "N/A");
                // Getting the IORT ROLE from the portlet preferences for LATO
                String lato_iort_ROLE = portletPreferences.getValue("lato_iort_ROLE", "N/A");
                // Getting the IORT RENEWAL from the portlet preferences for LATO
                String lato_iort_RENEWAL = portletPreferences.getValue("lato_iort_RENEWAL", "checked");
                // Getting the IORT DISABLEVOMS from the portlet preferences for LATO
                String lato_iort_DISABLEVOMS = portletPreferences.getValue("lato_iort_DISABLEVOMS", "unchecked");
                
                if (iort_LOGLEVEL.trim().equals("VERBOSE")) {
                log.info("\n- Getting the IOR portlet preferences ..."
                    + "\nlato_iort_INFRASTRUCTURE: " + lato_iort_INFRASTRUCTURE
                    + "\nlato_iort_LOGIN: " + lato_iort_LOGIN
                    + "\nlato_iort_PASSWD: " + lato_iort_PASSWD                    
                    + "\nlato_iort_ETOKENSERVER: " + lato_iort_ETOKENSERVER
                    + "\nlato_iort_MYPROXYSERVER: " + lato_iort_MYPROXYSERVER
                    + "\nlato_iort_PORT: " + lato_iort_PORT
                    + "\nlato_iort_ROBOTID: " + lato_iort_ROBOTID
                    + "\nlato_iort_ROLE: " + lato_iort_ROLE
                    + "\nlato_iort_RENEWAL: " + lato_iort_RENEWAL
                    + "\nlato_iort_DISABLEVOMS: " + lato_iort_DISABLEVOMS
                   
                    + "\n\niort_ENABLEINFRASTRUCTURE: " + lato_iort_ENABLEINFRASTRUCTURE
                    + "\niort_APPID: " + iort_APPID
                    + "\niort_LOGLEVEL: " + iort_LOGLEVEL
                    + "\niort_OUTPUT_PATH: " + iort_OUTPUT_PATH
                    + "\niort_SOFTWARE: " + iort_SOFTWARE
                    + "\nTracking_DB_Hostname: " + TRACKING_DB_HOSTNAME
                    + "\nTracking_DB_Username: " + TRACKING_DB_USERNAME
                    + "\nTracking_DB_Password: " + TRACKING_DB_PASSWORD
                    + "\nSMTP_HOST: " + SMTP_HOST
                    + "\nSENDER_MAIL: " + SENDER_MAIL);
                }
                
                // Defining the WMS list for the "LATO" Infrastructure
                int nmax=0;
                for (int i = 0; i < lato_iort_WMS.length; i++)
                    if ((lato_iort_WMS[i]!=null) && (!lato_iort_WMS[i].equals("N/A"))) nmax++;

                String lato_wmsList[] = new String [nmax];                
                    for (int i = 0; i < nmax; i++)
                    {
                        if (lato_iort_WMS[i]!=null) {
                        lato_wmsList[i]=lato_iort_WMS[i].trim();
                        log.info ("\n\n[" + nmax
                                          + "] Submitting to LATO ["
                                          + i
                                          + "] using WMS=["
                                          + lato_wmsList[i]
                                          + "]");
                        }
                    }
                
                infrastructures[0] = new InfrastructureInfo(
                        "SSH",
                        "ssh",
                        lato_iort_LOGIN,
                        lato_iort_PASSWD,
                        lato_wmsList);               
            }
                        
            if (gridit_iort_ENABLEINFRASTRUCTURE != null &&
                gridit_iort_ENABLEINFRASTRUCTURE.equals("gridit"))
            {
                MAX++;
                // Getting the IORTVONAME from the portlet preferences for the GRIDIT VO
                String gridit_iort_INFRASTRUCTURE = portletPreferences.getValue("gridit_iort_INFRASTRUCTURE", "N/A");
                // Getting the IORT VONAME from the portlet preferences for the GRIDIT VO
                String gridit_iort_VONAME = portletPreferences.getValue("gridit_iort_VONAME", "N/A");
                // Getting the IORT TOPPBDII from the portlet preferences for the GRIDIT VO
                String gridit_iort_TOPBDII = portletPreferences.getValue("gridit_iort_TOPBDII", "N/A");
                // Getting the IORT WMS from the portlet preferences for the GRIDIT VO                
                String[] gridit_iort_WMS = portletPreferences.getValues("gridit_iort_WMS", new String[5]);
                // Getting the IORT ETOKENSERVER from the portlet preferences for the GRIDIT VO
                String gridit_iort_ETOKENSERVER = portletPreferences.getValue("gridit_iort_ETOKENSERVER", "N/A");
                // Getting the IORT MYPROXYSERVER from the portlet preferences for the GRIDIT VO
                String gridit_iort_MYPROXYSERVER = portletPreferences.getValue("gridit_iort_MYPROXYSERVER", "N/A");
                // Getting the IORT PORT from the portlet preferences for the GRIDIT VO
                String gridit_iort_PORT = portletPreferences.getValue("gridit_iort_PORT", "N/A");
                // Getting the IORT ROBOTID from the portlet preferences for the GRIDIT VO
                String gridit_iort_ROBOTID = portletPreferences.getValue("gridit_iort_ROBOTID", "N/A");
                // Getting the IORT ROLE from the portlet preferences for the GRIDIT VO
                String gridit_iort_ROLE = portletPreferences.getValue("gridit_iort_ROLE", "N/A");
                // Getting the IORT RENEWAL from the portlet preferences for the GRIDIT VO
                String gridit_iort_RENEWAL = portletPreferences.getValue("gridit_iort_RENEWAL", "checked");
                // Getting the IORT DISABLEVOMS from the portlet preferences for the GRIDIT VO
                String gridit_iort_DISABLEVOMS = portletPreferences.getValue("gridit_iort_DISABLEVOMS", "unchecked");
                
                if (iort_LOGLEVEL.trim().equals("VERBOSE")) {
                log.info("\n- Getting the IORT portlet preferences ..."
                    + "\ngridit_iort_INFRASTRUCTURE: " + gridit_iort_INFRASTRUCTURE
                    + "\ngridit_iort_VONAME: " + gridit_iort_VONAME
                    + "\ngridit_iort_TOPBDII: " + gridit_iort_TOPBDII                    
                    + "\ngridit_iort_ETOKENSERVER: " + gridit_iort_ETOKENSERVER
                    + "\ngridit_iort_MYPROXYSERVER: " + gridit_iort_MYPROXYSERVER
                    + "\ngridit_iort_PORT: " + gridit_iort_PORT
                    + "\ngridit_iort_ROBOTID: " + gridit_iort_ROBOTID
                    + "\ngridit_iort_ROLE: " + gridit_iort_ROLE
                    + "\ngridit_iort_RENEWAL: " + gridit_iort_RENEWAL
                    + "\ngridit_iort_DISABLEVOMS: " + gridit_iort_DISABLEVOMS
                   
                    + "\n\niort_ENABLEINFRASTRUCTURE: " + gridit_iort_ENABLEINFRASTRUCTURE
                    + "\niort_APPID: " + iort_APPID
                    + "\niort_LOGLEVEL: " + iort_LOGLEVEL
                    + "\niort_OUTPUT_PATH: " + iort_OUTPUT_PATH
                    + "\niort_SOFTWARE: " + iort_SOFTWARE
                    + "\nTracking_DB_Hostname: " + TRACKING_DB_HOSTNAME
                    + "\nTracking_DB_Username: " + TRACKING_DB_USERNAME
                    + "\nTracking_DB_Password: " + TRACKING_DB_PASSWORD
                    + "\nSMTP_HOST: " + SMTP_HOST
                    + "\nSENDER_MAIL: " + SENDER_MAIL);
                }
                
                // Defining the WMS list for the "GRIDIT" Infrastructure
                int nmax=0;
                for (int i = 0; i < gridit_iort_WMS.length; i++)
                    if ((gridit_iort_WMS[i]!=null) && (!gridit_iort_WMS[i].equals("N/A"))) nmax++;

                String wmsList[] = new String [nmax];
                for (int i = 0; i < nmax; i++)
                {
                    if (gridit_iort_WMS[i]!=null) {
                    wmsList[i]=gridit_iort_WMS[i].trim();
                    log.info ("\n\n[" + nmax
                                      + "] Submitting to GRIDIT ["
                                      + i
                                      + "] using WMS=["
                                      + wmsList[i]
                                      + "]");
                    }
                }

                infrastructures[1] = new InfrastructureInfo(
                    gridit_iort_VONAME,
                    gridit_iort_TOPBDII,
                    wmsList,
                    gridit_iort_ETOKENSERVER,
                    gridit_iort_PORT,
                    gridit_iort_ROBOTID,
                    gridit_iort_VONAME,
                    gridit_iort_ROLE,
                    "VO-" + gridit_iort_VONAME + "-" + iort_SOFTWARE);
            }
            
            if (eumed_iort_ENABLEINFRASTRUCTURE != null &&
                eumed_iort_ENABLEINFRASTRUCTURE.equals("eumed"))
            {
                MAX++;
                // Getting the IORT VONAME from the portlet preferences for the EUMED VO
                String eumed_iort_INFRASTRUCTURE = portletPreferences.getValue("eumed_iort_INFRASTRUCTURE", "N/A");
                // Getting the IORT VONAME from the portlet preferences for the EUMED VO
                String eumed_iort_VONAME = portletPreferences.getValue("eumed_iort_VONAME", "N/A");
                // Getting the IORT TOPPBDII from the portlet preferences for the EUMED VO
                String eumed_iort_TOPBDII = portletPreferences.getValue("eumed_iort_TOPBDII", "N/A");
                // Getting the IORT WMS from the portlet preferences for the EUMED VO
                String[] eumed_iort_WMS = portletPreferences.getValues("eumed_iort_WMS", new String[5]);
                // Getting the IORT ETOKENSERVER from the portlet preferences for the EUMED VO
                String eumed_iort_ETOKENSERVER = portletPreferences.getValue("eumed_iort_ETOKENSERVER", "N/A");
                // Getting the IORT MYPROXYSERVER from the portlet preferences for the EUMED VO
                String eumed_iort_MYPROXYSERVER = portletPreferences.getValue("eumed_iort_MYPROXYSERVER", "N/A");
                // Getting the IORT PORT from the portlet preferences for the EUMED VO
                String eumed_iort_PORT = portletPreferences.getValue("eumed_iort_PORT", "N/A");
                // Getting the IORT ROBOTID from the portlet preferences for the EUMED VO
                String eumed_iort_ROBOTID = portletPreferences.getValue("eumed_iort_ROBOTID", "N/A");
                // Getting the IORT ROLE from the portlet preferences for the EUMED VO
                String eumed_iort_ROLE = portletPreferences.getValue("eumed_iort_ROLE", "N/A");
                // Getting the IORT RENEWAL from the portlet preferences for the EUMED VO
                String eumed_iort_RENEWAL = portletPreferences.getValue("eumed_iort_RENEWAL", "checked");
                // Getting the IORT DISABLEVOMS from the portlet preferences for the EUMED VO
                String eumed_iort_DISABLEVOMS = portletPreferences.getValue("eumed_iort_DISABLEVOMS", "unchecked");
                
                if (iort_LOGLEVEL.trim().equals("VERBOSE")) {
                log.info("\n- Getting the IOR portlet preferences ..."
                    + "\n\neumed_iort_INFRASTRUCTURE: " + eumed_iort_INFRASTRUCTURE
                    + "\neumed_iort_VONAME: " + eumed_iort_VONAME
                    + "\neumed_iort_TOPBDII: " + eumed_iort_TOPBDII                    
                    + "\neumed_iort_ETOKENSERVER: " + eumed_iort_ETOKENSERVER
                    + "\neumed_iort_MYPROXYSERVER: " + eumed_iort_MYPROXYSERVER
                    + "\neumed_iort_PORT: " + eumed_iort_PORT
                    + "\neumed_iort_ROBOTID: " + eumed_iort_ROBOTID
                    + "\neumed_iort_ROLE: " + eumed_iort_ROLE
                    + "\neumed_iort_RENEWAL: " + eumed_iort_RENEWAL
                    + "\neumed_iort_DISABLEVOMS: " + eumed_iort_DISABLEVOMS

                    + "\n\niort_ENABLEINFRASTRUCTURE: " + eumed_iort_ENABLEINFRASTRUCTURE
                    + "\niort_APPID: " + iort_APPID
                    + "\niort_LOGLEVEL: " + iort_LOGLEVEL
                    + "\niort_OUTPUT_PATH: " + iort_OUTPUT_PATH
                    + "\niort_SOFTWARE: " + iort_SOFTWARE
                    + "\nTracking_DB_Hostname: " + TRACKING_DB_HOSTNAME
                    + "\nTracking_DB_Username: " + TRACKING_DB_USERNAME
                    + "\nTracking_DB_Password: " + TRACKING_DB_PASSWORD
                    + "\nSMTP_HOST: " + SMTP_HOST
                    + "\nSENDER_MAIL: " + SENDER_MAIL);
                }
                
                // Defining the WMS list for the "EUMED" Infrastructure
                int nmax=0;
                for (int i = 0; i < eumed_iort_WMS.length; i++)
                    if ((eumed_iort_WMS[i]!=null) && (!eumed_iort_WMS[i].equals("N/A"))) nmax++;
                
                String wmsList[] = new String [nmax];
                for (int i = 0; i < nmax; i++)
                {
                    if (eumed_iort_WMS[i]!=null) {
                    wmsList[i]=eumed_iort_WMS[i].trim();
                    log.info ("\n\n[" + nmax
                                      + "] Submitting to EUMED ["
                                      + i
                                      + "] using WMS=["
                                      + wmsList[i]
                                      + "]");
                    }
                }

                infrastructures[2] = new InfrastructureInfo(
                    eumed_iort_VONAME,
                    eumed_iort_TOPBDII,
                    wmsList,
                    eumed_iort_ETOKENSERVER,
                    eumed_iort_PORT,
                    eumed_iort_ROBOTID,
                    eumed_iort_VONAME,
                    eumed_iort_ROLE,
                    "VO-" + eumed_iort_VONAME + "-" + iort_SOFTWARE);
            }

            if (biomed_iort_ENABLEINFRASTRUCTURE != null &&
                biomed_iort_ENABLEINFRASTRUCTURE.equals("biomed")) 
            {
                MAX++;
                // Getting the IORT VONAME from the portlet preferences for the BIOMED VO
                String biomed_iort_INFRASTRUCTURE = portletPreferences.getValue("biomed_iort_INFRASTRUCTURE", "N/A");
                // Getting the IORT VONAME from the portlet preferences for the BIOMED VO
                String biomed_iort_VONAME = portletPreferences.getValue("biomed_iort_VONAME", "N/A");
                // Getting the IORT TOPPBDII from the portlet preferences for the BIOMED VO
                String biomed_iort_TOPBDII = portletPreferences.getValue("biomed_iort_TOPBDII", "N/A");
                // Getting the IORT WMS from the portlet preferences for the BIOMED VO
                String[] biomed_iort_WMS = portletPreferences.getValues("biomed_iort_WMS", new String[5]);
                // Getting the IORT ETOKENSERVER from the portlet preferences for the BIOMED VO
                String biomed_iort_ETOKENSERVER = portletPreferences.getValue("biomed_iort_ETOKENSERVER", "N/A");
                // Getting the IORT MYPROXYSERVER from the portlet preferences for the BIOMED VO
                String biomed_iort_MYPROXYSERVER = portletPreferences.getValue("biomed_iort_MYPROXYSERVER", "N/A");
                // Getting the IORT PORT from the portlet preferences for the BIOMED VO
                String biomed_iort_PORT = portletPreferences.getValue("biomed_iort_PORT", "N/A");
                // Getting the IORT ROBOTID from the portlet preferences for the BIOMED VO
                String biomed_iort_ROBOTID = portletPreferences.getValue("biomed_iort_ROBOTID", "N/A");
                // Getting the IORT ROLE from the portlet preferences for the BIOMED VO
                String biomed_iort_ROLE = portletPreferences.getValue("biomed_iort_ROLE", "N/A");
                // Getting the IORT RENEWAL from the portlet preferences for the BIOMED VO
                String biomed_iort_RENEWAL = portletPreferences.getValue("biomed_iort_RENEWAL", "checked");
                // Getting the IORT DISABLEVOMS from the portlet preferences for the BIOMED VO
                String biomed_iort_DISABLEVOMS = portletPreferences.getValue("biomed_iort_DISABLEVOMS", "unchecked");
                
                if (iort_LOGLEVEL.trim().equals("VERBOSE")) {
                log.info("\n- Getting the IORT portlet preferences ..."
                + "\n\nbiomed_iort_INFRASTRUCTURE: " + biomed_iort_INFRASTRUCTURE
                + "\nbiomed_iort_VONAME: " + biomed_iort_VONAME
                + "\nbiomed_iort_TOPBDII: " + biomed_iort_TOPBDII                        
                + "\nbiomed_iort_ETOKENSERVER: " + biomed_iort_ETOKENSERVER
                + "\nbiomed_iort_MYPROXYSERVER: " + biomed_iort_MYPROXYSERVER
                + "\nbiomed_iort_PORT: " + biomed_iort_PORT
                + "\nbiomed_iort_ROBOTID: " + biomed_iort_ROBOTID
                + "\nbiomed_iort_ROLE: " + biomed_iort_ROLE
                + "\nbiomed_iort_RENEWAL: " + biomed_iort_RENEWAL
                + "\nbiomed_iort_DISABLEVOMS: " + biomed_iort_DISABLEVOMS

                + "\n\niort_ENABLEINFRASTRUCTURE: " + biomed_iort_ENABLEINFRASTRUCTURE
                + "\niort_APPID: " + iort_APPID
                + "\niort_LOGLEVEL: " + iort_LOGLEVEL                        
                + "\niort_OUTPUT_PATH: " + iort_OUTPUT_PATH                        
                + "\niort_SOFTWARE: " + iort_SOFTWARE
                + "\nTracking_DB_Hostname: " + TRACKING_DB_HOSTNAME
                + "\nTracking_DB_Username: " + TRACKING_DB_USERNAME
                + "\nTracking_DB_Password: " + TRACKING_DB_PASSWORD
                + "\nSMTP_HOST: " + SMTP_HOST
                + "\nSENDER_MAIL: " + SENDER_MAIL);
                }
                
                // Defining the WMS list for the "BIOMED" Infrastructure
                int nmax=0;
                for (int i = 0; i < biomed_iort_WMS.length; i++)
                    if ((biomed_iort_WMS[i]!=null) && (!biomed_iort_WMS[i].equals("N/A"))) nmax++;
                
                String wmsList[] = new String [biomed_iort_WMS.length];
                for (int i = 0; i < biomed_iort_WMS.length; i++)
                {
                    if (biomed_iort_WMS[i]!=null) {
                    wmsList[i]=biomed_iort_WMS[i].trim();
                    log.info ("\n\nSubmitting for BIOMED [" + i + "] using WMS=[" + wmsList[i] + "]");
                    }
                }

                infrastructures[3] = new InfrastructureInfo(
                    biomed_iort_VONAME,
                    biomed_iort_TOPBDII,
                    wmsList,
                    biomed_iort_ETOKENSERVER,
                    biomed_iort_PORT,
                    biomed_iort_ROBOTID,
                    biomed_iort_VONAME,
                    biomed_iort_ROLE,
                    "VO-" + biomed_iort_VONAME + "-" + iort_SOFTWARE);
            }

            String[] IORT_Parameters = new String [6];

            // Upload the input settings for the application
            IORT_Parameters = uploadIortSettings( request, response, username );

            log.info ("\n\nPreparing to start a IORT simulation with these parameters. ");
            log.info("\n- Input Parameters: ");
            log.info("\n- ASCII or Text = " + IORT_Parameters[0]);
            log.info("\n- Description = " + IORT_Parameters[1]);
            log.info("\n- IORT_CE = " + IORT_Parameters[2]);
            log.info("\n- Jobs = " + IORT_Parameters[3]);
            log.info("\n- Max CPU Time = " + IORT_Parameters[5]);
            log.info("\n- Enable Notification = " + IORT_Parameters[4]);
            
            // Preparing to submit SONIFICATION jobs in different grid infrastructure..
            //=============================================================
            // IMPORTANT: INSTANCIATE THE MultiInfrastructureJobSubmission
            //            CLASS USING THE EMPTY CONSTRUCTOR WHEN
            //            WHEN THE PORTLET IS DEPLOYED IN PRODUCTION!!!
            //=============================================================
            MultiInfrastructureJobSubmission IortMultiJobSubmission =
            new MultiInfrastructureJobSubmission(TRACKING_DB_HOSTNAME,
                                                 TRACKING_DB_USERNAME,
                                                 TRACKING_DB_PASSWORD);
            
            /*MultiInfrastructureJobSubmission IortMultiJobSubmission =
                new MultiInfrastructureJobSubmission();*/

            // Set the list of infrastructure(s) activated for the SONIFICATION portlet           
            if (infrastructures[0]!=null) {
                if (iort_LOGLEVEL.trim().equals("VERBOSE"))
                    log.info("\n- Adding the LATO Infrastructure.");
                 IortMultiJobSubmission.addInfrastructure(infrastructures[0]); 
            }            
            if (infrastructures[1]!=null) {
                if (iort_LOGLEVEL.trim().equals("VERBOSE"))
                    log.info("\n- Adding the GRIDIT Infrastructure.");
                 IortMultiJobSubmission.addInfrastructure(infrastructures[1]); 
            }
            if (infrastructures[2]!=null) {
                if (iort_LOGLEVEL.trim().equals("VERBOSE"))
                    log.info("\n- Adding the EUMED Infrastructure.");
                 IortMultiJobSubmission.addInfrastructure(infrastructures[2]);
            }
            if (infrastructures[3]!=null) {
                if (iort_LOGLEVEL.trim().equals("VERBOSE"))
                    log.info("\n- Adding the BIOMED Infrastructure.");
                 IortMultiJobSubmission.addInfrastructure(infrastructures[3]);
            }
            
            String IortFilesPath = getPortletContext().getRealPath("/") +
                                    "WEB-INF/config";                        
            
            // Set the Output path forresults
            //IortMultiJobSubmission.setOutputPath("/tmp");
            IortMultiJobSubmission.setOutputPath(iort_OUTPUT_PATH);
                        
            // Set the StandardOutput for IORT
            IortMultiJobSubmission.setJobOutput(".std.txt");

            // Set the StandardError for IORT
            IortMultiJobSubmission.setJobError(".std.err");
            
            // Set the Executable for IORT
            IortMultiJobSubmission.setExecutable("start_geant4-09-05-patch-01.sh");

            String Arguments = IORT_Parameters[0];
            // Set the list of Arguments for IORT
            IortMultiJobSubmission.setArguments(Arguments);
                
            String InputSandbox = IortFilesPath + 
                                  "/start_geant4-09-05-patch-01.sh" +
                                  "," +                                   
                                  IORT_Parameters[0];

            // Set InputSandbox files (string with comma separated list of file names)
            IortMultiJobSubmission.setInputFiles(InputSandbox);                                

            // OutputSandbox (string with comma separated list of file names)
            String README = "output.README";            
            String IortFiles="results.tar.gz";

            // Set the OutputSandbox files (string with comma separated list of file names)
            IortMultiJobSubmission.setOutputFiles(IortFiles + "," + README);
            
            int NMAX = Integer.parseInt(IORT_Parameters[3]);                        
            
            // Adding the proper requirements to run more than 24h
            String jdlRequirements[] = new String[1];
            jdlRequirements[0] = "JDLRequirements=(other.GlueCEPolicyMaxCPUTime>1440)";
            IortMultiJobSubmission.setJDLRequirements(jdlRequirements);

            InetAddress addr = InetAddress.getLocalHost();           
            
            Company company;
            try {
                company = PortalUtil.getCompany(request);
                String gateway = company.getName();
                
                // Send a notification email to the user if enabled.
                if (IORT_Parameters[4]!=null)
                    if ( (SMTP_HOST==null) || 
                         (SMTP_HOST.trim().equals("")) ||
                         (SMTP_HOST.trim().equals("N/A")) ||
                         (SENDER_MAIL==null) || 
                         (SENDER_MAIL.trim().equals("")) ||
                         (SENDER_MAIL.trim().equals("N/A"))
                       )
                    log.info ("\nThe Notification Service is not properly configured!!");
                else {
                            // Enabling Job's notification via email
                            IortMultiJobSubmission.setUserEmail(emailAddress);
                        
                            sendHTMLEmail(username, 
                                       emailAddress, 
                                       SENDER_MAIL, 
                                       SMTP_HOST, 
                                       "IORT", 
                                       gateway);
                }                                
                
            } catch (PortalException ex) {
                Logger.getLogger(Iort.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SystemException ex) {
                Logger.getLogger(Iort.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            // Submitting...
            for (int i=1; i<=NMAX; i++) 
            { 
                InfrastructureInfo infrastructure = 
                    IortMultiJobSubmission.getInfrastructure();
                
                String Middleware = null;
                if (infrastructure.getMiddleware().equals("glite"))
                    Middleware = "glite";
                
                if (infrastructure.getMiddleware().equals("wsgram"))
                    Middleware = "wsgram";
                
                if (infrastructure.getMiddleware().equals("ssh"))
                    Middleware = "ssh";
            
                log.info("\n- Selected Infrastructure = " + infrastructure.getName());
                log.info("\n- Enabled Middleware = " + Middleware);
            
                // Check if more than one infrastructure have been enabled                
                if (MAX==1) 
                {
                    String iort_VONAME = "";
                    String iort_TOPBDII = "";
                    String RANDOM_CE = "";                    
                    int MAXCpuTime = Integer.parseInt(IORT_Parameters[5]);
                    
                    if (lato_iort_ENABLEINFRASTRUCTURE != null &&
                        lato_iort_ENABLEINFRASTRUCTURE.equals("lato")) 
                    {
                        log.info("\n- Submitting GEANT4 simulation(s) to LATO in progress...");
                    }
                    
                    if (gridit_iort_ENABLEINFRASTRUCTURE != null &&
                        gridit_iort_ENABLEINFRASTRUCTURE.equals("gridit")) 
                    {
                        // Getting the IORT VONAME from the portlet preferences for the GRIDIT VO
                        iort_VONAME = portletPreferences.getValue("gridit_iort_VONAME", "N/A");
                        // Getting the IORT TOPPBDII from the portlet preferences for the GRIDIT VO
                        iort_TOPBDII = portletPreferences.getValue("gridit_iort_TOPBDII", "N/A");
                        
                        // Set the queue if it is defined by the user
                        if (!IORT_Parameters[2].isEmpty()) {
                        log.info("\n- Submitting of the job [ #" + i + " ] to the selected CE in progress...");
                        RANDOM_CE = getRandomCE(iort_VONAME, iort_TOPBDII, iort_SOFTWARE, MAXCpuTime, IORT_Parameters[2]);
                        IortMultiJobSubmission.setJobQueue(RANDOM_CE);
                        } else { 
                        // Get the random CE for the IORT portlet
                        RANDOM_CE = getRandomCE(iort_VONAME, iort_TOPBDII, iort_SOFTWARE, MAXCpuTime, "");
                        log.info("\n- Submitting of the job [ #" + i + " ] to the CE [ " + RANDOM_CE + " ] in progress...");                
                        IortMultiJobSubmission.setJobQueue(RANDOM_CE.toString().trim());
                        }
                    }
                    
                    if (eumed_iort_ENABLEINFRASTRUCTURE != null &&
                        eumed_iort_ENABLEINFRASTRUCTURE.equals("eumed")) 
                    {
                        // Getting the IORT VONAME from the portlet preferences for the EUMED VO
                        iort_VONAME = portletPreferences.getValue("eumed_iort_VONAME", "N/A");
                        // Getting the IORT TOPPBDII from the portlet preferences for the EUMED VO
                        iort_TOPBDII = portletPreferences.getValue("eumed_iort_TOPBDII", "N/A");
                        
                        // Set the queue if it is defined by the user
                        if (!IORT_Parameters[2].isEmpty()) {
                        log.info("\n- Submitting of the job [ #" + i + " ] to the selected CE in progress...");
                        RANDOM_CE = getRandomCE(iort_VONAME, iort_TOPBDII, iort_SOFTWARE, MAXCpuTime, IORT_Parameters[2]);
                        IortMultiJobSubmission.setJobQueue(RANDOM_CE);
                        } else { 
                        // Get the random CE for the IORT portlet
                        RANDOM_CE = getRandomCE(iort_VONAME, iort_TOPBDII, iort_SOFTWARE, MAXCpuTime, "");
                        log.info("\n- Submitting of the job [ #" + i + " ] to the CE [ " + RANDOM_CE + " ] in progress...");                
                        IortMultiJobSubmission.setJobQueue(RANDOM_CE.toString().trim());
                        }
                    }
                    
                    if (biomed_iort_ENABLEINFRASTRUCTURE != null &&
                        biomed_iort_ENABLEINFRASTRUCTURE.equals("biomed")) 
                    {
                        // Getting the IORT VONAME from the portlet preferences for the BIOMED VO
                        iort_VONAME = portletPreferences.getValue("biomed_iort_VONAME", "N/A");
                        // Getting the IORT TOPPBDII from the portlet preferences for the BIOMED VO
                        iort_TOPBDII = portletPreferences.getValue("biomed_iort_TOPBDII", "N/A");
                        
                        // Set the queue if it is defined by the user
                        if (!IORT_Parameters[2].isEmpty()) {
                        log.info("\n- Submitting of the job [ #" + i + " ] to the selected CE in progress...");
                        RANDOM_CE = getRandomCE(iort_VONAME, iort_TOPBDII, iort_SOFTWARE, MAXCpuTime, IORT_Parameters[2]);
                        IortMultiJobSubmission.setJobQueue(RANDOM_CE);
                        } else { 
                        // Get the random CE for the IORT portlet
                        RANDOM_CE = getRandomCE(iort_VONAME, iort_TOPBDII, iort_SOFTWARE, MAXCpuTime, "");
                        log.info("\n- Submitting of the job [ #" + i + " ] to the CE [ " + RANDOM_CE + " ] in progress...");                
                        IortMultiJobSubmission.setJobQueue(RANDOM_CE.toString().trim());
                        }
                    }                    
                }
                
                IortMultiJobSubmission.submitJobAsync(
                        infrastructure,
                        username,
                        addr.getHostAddress()+":8162",
                        Integer.valueOf(iort_APPID),
                        IORT_Parameters[1]);
            }            
        } // end PROCESS ACTION [ SUBMIT_IORT_PORTLET ]
    }

    @Override
    public void serveResource(ResourceRequest request, ResourceResponse response)
                throws PortletException, IOException
    {
        //super.serveResource(request, response);

        PortletPreferences portletPreferences = (PortletPreferences) request.getPreferences();

        final String action = (String) request.getParameter("action");

        if (action.equals("get-ratings")) {
            //Get CE Ratings from the portlet preferences
            String iort_CE = (String) request.getParameter("iort_CE");

            String json = "{ \"avg\":\"" + 
                    portletPreferences.getValue(iort_CE+"_avg", "0.0") +
                    "\", \"cnt\":\"" + portletPreferences.getValue(iort_CE+"_cnt", "0") + "\"}";

            response.setContentType("application/json");
            response.getPortletOutputStream().write( json.getBytes() );

        } else if (action.equals("set-ratings")) {

            String iort_CE = (String) request.getParameter("iort_CE");
            int vote = Integer.parseInt(request.getParameter("vote"));

             double avg = Double.parseDouble(portletPreferences.getValue(iort_CE+"_avg", "0.0"));
             long cnt = Long.parseLong(portletPreferences.getValue(iort_CE+"_cnt", "0"));

             portletPreferences.setValue(iort_CE+"_avg", Double.toString(((avg*cnt)+vote) / (cnt +1)));
             portletPreferences.setValue(iort_CE+"_cnt", Long.toString(cnt+1));

             portletPreferences.store();
        }
    }


    // Upload IORT input files
    public String[] uploadIortSettings(ActionRequest actionRequest,
                                        ActionResponse actionResponse, String username)
    {
        String[] IORT_Parameters = new String [6];
        boolean status;

        // Check that we have a file upload request
        boolean isMultipart = PortletFileUpload.isMultipartContent(actionRequest);

        if (isMultipart) {
            // Create a factory for disk-based file items.
            DiskFileItemFactory factory = new DiskFileItemFactory();

            // Set factory constrains
            File IORT_Repository = new File ("/tmp");
            if (!IORT_Repository.exists()) status = IORT_Repository.mkdirs();
            factory.setRepository(IORT_Repository);

            // Create a new file upload handler.
            PortletFileUpload upload = new PortletFileUpload(factory);

            try {
                    // Parse the request
                    List items = upload.parseRequest(actionRequest);

                    // Processing items
                    Iterator iter = items.iterator();

                    while (iter.hasNext())
                    {
                        FileItem item = (FileItem) iter.next();

                        String fieldName = item.getFieldName();
                        
                        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                        String timeStamp = dateFormat.format(Calendar.getInstance().getTime());

                        // Processing a regular form field
                        if ( item.isFormField() )
                        {                                                        
                            if (fieldName.equals("iort_textarea")) 
                            {
                                IORT_Parameters[0]=
                                        IORT_Repository +
                                        "/" + timeStamp +
                                        "_" + username +
                                        ".txt";
                            
                                // Store the textarea in a ASCII file
                                storeString(IORT_Parameters[0], 
                                            item.getString());                               
                            }
                            
                            if (fieldName.equals("iort_desc"))
                                if (item.getString().equals("Please, insert here a description for your job"))
                                    IORT_Parameters[1]="Iort Therapy Simulation Started";
                                else
                                    IORT_Parameters[1]=item.getString();                            
                                                        
                            if (fieldName.equals("iort_CE"))
                                IORT_Parameters[2]=item.getString();
                            
                            if (fieldName.equals("iort_nmax"))
                                IORT_Parameters[3]=item.getString();
                            
                        } else {
                            // Processing a file upload
                            if (fieldName.equals("iort_file"))
                            {                                                               
                                log.info("\n- Uploading the following user's file: "
                                       + "\n[ " + item.getName() + " ]"
                                       + "\n[ " + item.getContentType() + " ]"
                                       + "\n[ " + item.getSize() + "KBytes ]"
                                       );                                                                

                                // Writing the file to disk
                                String uploadIortFile = 
                                        IORT_Repository +
                                        "/" + timeStamp +
                                        "_" + username +
                                        "_" + item.getName();

                                log.info("\n- Writing the user's file: [ "
                                        + uploadIortFile.toString()
                                        + " ] to disk");

                                item.write(new File(uploadIortFile));
                                
                                // Writing the file to disk
                                String uploadIortFile_stripped = 
                                        IORT_Repository +
                                        "/" + timeStamp +
                                        "_" + username +
                                        "_macro.mac";                                                                
                                
                                IORT_Parameters[0]=
                                        RemoveCarriageReturn(
                                        uploadIortFile, 
                                        uploadIortFile_stripped
                                        );
                            }
                        }
                        
                        if (fieldName.equals("EnableNotification"))
                                IORT_Parameters[4]=item.getString(); 
                        
                        if (fieldName.equals("iort_maxcputime"))
                                IORT_Parameters[5]=item.getString(); 
                        
                    } // end while
            } catch (FileUploadException ex) {
              Logger.getLogger(Iort.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
              Logger.getLogger(Iort.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return IORT_Parameters;
    }
    
    // Retrieve a random Computing Element
    // matching the Software Tag for the application    
    public String getRandomCE(String iort_VONAME,
                              String iort_TOPBDII,
                              String iort_SOFTWARE,
                              Integer iort_MaxCPUTime,
                              String selected)
                              throws PortletException, IOException
    {
        String randomCE = null;
        BDII bdii = null;    
        List<String> CEqueues = null;
        boolean flag = true;        
                        
        log.info("\n- Querying the Information System [ " + 
                  iort_TOPBDII + 
                  " ] and retrieving a random CE matching the SW tag [ VO-" + 
                  iort_VONAME +
                  "-" +
                  iort_SOFTWARE + " ]");  

       try {               

                bdii = new BDII( new URI(iort_TOPBDII) );               
                
                // Get the list of the available queues
                CEqueues = bdii.queryCEQueues(iort_VONAME);
                                
                // Get the list of the Computing Elements for the given SW tag
                randomCE = bdii.getRandomCEForSWTag("VO-" + 
                           iort_VONAME + 
                           "-" +
                           iort_SOFTWARE);
                
                /*randomCE = bdii.getRandomCEFromSWTag_MaxCPUTime(
                        "VO-" + iort_VONAME + "-" + iort_SOFTWARE, 
                        iort_VONAME, 
                        iort_MaxCPUTime);*/
                                    
                /*if (!selected.isEmpty())
                while (flag) {
                    // Fetching the Queues
                    for (String CEqueue:CEqueues) {
                        // Selecting only the "infinite" queue
                            if (CEqueue.contains(selected)) {
                                randomCE=CEqueue;                    
                                flag=false;
                            }                        
                    }
                }*/
                
                // Fetching the Queues
                for (String CEqueue:CEqueues) {
                    if (CEqueue.contains(randomCE))
                        randomCE=CEqueue;
                }

        } catch (URISyntaxException ex) {
                Logger.getLogger(Iort.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
                Logger.getLogger(Iort.class.getName()).log(Level.SEVERE, null, ex);
        }                   
        
        if (!selected.isEmpty()) log.info("\n- Selected the following computing farm =  " + randomCE);
        else log.info("\n- Selected *randomly* the following computing farm =  " + randomCE);
        return randomCE;
    }        
    
    public String RemoveCarriageReturn (String InputFileName, String OutputFileName)             
    {
        // Remove the carriage return char from a named file.                                
        FileInputStream fis;
        try {
            
            fis = new FileInputStream(InputFileName);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            
            File fout = new File(OutputFileName);
            FileOutputStream fos = new FileOutputStream(fout);                                
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            
            // The pattern matches control characters
            Pattern p = Pattern.compile("\r");
            Matcher m = p.matcher("");
            String aLine = null;

            try {
                while((aLine = in.readLine()) != null) {
                    m.reset(aLine);
                    //Replaces control characters with an empty string.
                    String result = m.replaceAll("");                    
                    out.write(result);
                    out.newLine();
                }
                out.close();                
            } catch (IOException ex) {
                Logger.getLogger(Iort.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Iort.class.getName()).log(Level.SEVERE, null, ex);
        }                                                                                
        
        log.info("\n- Writing the user's stripped file: [ " + 
                  OutputFileName.toString() + " ] to disk");
        
        return OutputFileName;
    }


    // Retrieve the list of Computing Elements
    // matching the Software Tag for the IORT application    
    public List<String> getListofCEForSoftwareTag(String iort_VONAME,
                                                  String iort_TOPBDII,
                                                  String iort_SOFTWARE)
                                throws PortletException, IOException
    {
        List<String> CEs_list = null;
        BDII bdii = null;        
        
        log.info("\n- Querying the Information System [ " + 
                  iort_TOPBDII + 
                  " ] and looking for CEs matching SW tag [ VO-" + 
                  iort_VONAME +
                  "-" +
                  iort_SOFTWARE + " ]");  

       try {               

                bdii = new BDII( new URI(iort_TOPBDII) );                
                CEs_list = bdii.queryCEForSWTag(
                           "VO-" +
                           iort_VONAME +
                           "-" +
                           iort_SOFTWARE);

        } catch (URISyntaxException ex) {
                Logger.getLogger(Iort.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
                Logger.getLogger(Iort.class.getName()).log(Level.SEVERE, null, ex);
        }

        return CEs_list;
    }

    // Get the GPS location of the given grid resource
    public String[] getCECoordinate(RenderRequest request,
                                    String CE)
                                    throws PortletException, IOException
    {
        String[] GPS_locations = null;
        BDII bdii = null;

        PortletPreferences portletPreferences =
                (PortletPreferences) request.getPreferences();

        // Getting the IORT TOPPBDII from the portlet preferences
        String gridit_iort_TOPBDII = 
                portletPreferences.getValue("gridit_iort_TOPBDII", "N/A");
        String eumed_iort_TOPBDII = 
                portletPreferences.getValue("eumed_iort_TOPBDII", "N/A");
        String biomed_iort_TOPBDII = 
                portletPreferences.getValue("biomed_iort_TOPBDII", "N/A");
        
        // Getting the IORT ENABLEINFRASTRUCTURE from the portlet preferences
        String iort_ENABLEINFRASTRUCTURE = 
                portletPreferences.getValue("iort_ENABLEINFRASTRUCTURE", "N/A");

            try {
                if ( iort_ENABLEINFRASTRUCTURE.equals("gridit") )
                     bdii = new BDII( new URI(gridit_iort_TOPBDII) );

                if ( iort_ENABLEINFRASTRUCTURE.equals("eumed") )
                     bdii = new BDII( new URI(eumed_iort_TOPBDII) );

                if ( iort_ENABLEINFRASTRUCTURE.equals("biomed") )
                    bdii = new BDII( new URI(biomed_iort_TOPBDII) );

                GPS_locations = bdii.queryCECoordinate("ldap://" + CE + ":2170");

            } catch (URISyntaxException ex) {
                Logger.getLogger(Iort.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(Iort.class.getName()).log(Level.SEVERE, null, ex);
            }

            return GPS_locations;
    }
    
    private void storeString (String fileName, String fileContent) 
                              throws IOException 
    { 
        log.info("\n- Writing textarea in a ASCII file [ " + fileName + " ]");        
        // Removing the Carriage Return (^M) from text
        String pattern = "[\r]";
        String stripped = fileContent.replaceAll(pattern, "");        
        
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));        
        writer.write(stripped);
        writer.write("\n");
        writer.close();
    }
    
    private void sendHTMLEmail (String USERNAME,
                                String TO, 
                                String FROM, 
                                String SMTP_HOST, 
                                String ApplicationAcronym,
                                String GATEWAY)
    {
                
        log.info("\n- Sending email notification to the user " + USERNAME + " [ " + TO + " ]");
        
        log.info("\n- SMTP Server = " + SMTP_HOST);
        log.info("\n- Sender = " + FROM);
        log.info("\n- Receiver = " + TO);
        log.info("\n- Application = " + ApplicationAcronym);
        log.info("\n- Gateway = " + GATEWAY);        
        
        // Assuming you are sending email from localhost
        String HOST = "localhost";
        
        // Get system properties
        Properties properties = System.getProperties();
        properties.setProperty(SMTP_HOST, HOST);
        
        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties);
        
        try {
         // Create a default MimeMessage object.
         MimeMessage message = new MimeMessage(session);

         // Set From: header field of the header.
         message.setFrom(new InternetAddress(FROM));

         // Set To: header field of the header.
         message.addRecipient(Message.RecipientType.TO, new InternetAddress(TO));
         //message.addRecipient(Message.RecipientType.CC, new InternetAddress(FROM));

         // Set Subject: header field
         message.setSubject(" [liferay-sg-gateway] - [ " + GATEWAY + " ] ");

	 Date currentDate = new Date();
	 currentDate.setTime (currentDate.getTime());

         // Send the actual HTML message, as big as you like
         message.setContent(
	 "<br/><H4>" +         
         "<img src=\"http://fbcdn-profile-a.akamaihd.net/hprofile-ak-snc6/195775_220075701389624_155250493_n.jpg\" width=\"100\">Science Gateway Notification" +
	 "</H4><hr><br/>" +
         "<b>Description:</b> Notification for the application <b>[ " + ApplicationAcronym + " ]</b><br/><br/>" +         
         "<i>The application has been successfully submitted from the [ " + GATEWAY + " ]</i><br/><br/>" +
         "<b>TimeStamp:</b> " + currentDate + "<br/><br/>" +
	 "<b>Disclaimer:</b><br/>" +
	 "<i>This is an automatic message sent by the Science Gateway based on Liferay technology.<br/>" + 
	 "If you did not submit any jobs through the Science Gateway, please " +
         "<a href=\"mailto:" + FROM + "\">contact us</a></i>",
	 "text/html");

         // Send message
         Transport.send(message);         
      } catch (MessagingException mex) { mex.printStackTrace(); }        
    }
}
