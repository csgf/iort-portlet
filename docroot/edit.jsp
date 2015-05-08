<%
/**************************************************************************
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
****************************************************************************/
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="javax.portlet.*"%>
<%@page import="java.util.Arrays"%>
<%@page import="com.liferay.portal.kernel.util.WebKeys" %>
<%@taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<portlet:defineObjects/>

<%
  //
  // IORT 1.2.3 portlet preferences for the GirdEngine interaction
  //
  // These parameters are:  
  // o *_iort_INFRASTRUCTURE  - The Infrastructure Acronym to be enabled
  // o *_iort_TOPBDII         - The TopBDII hostname for accessing the Infrastructure
  // o *_iort_WMS             - The WMProxy hostname for accessing the Infrastructure
  // o *_iort_VONAME          - The VO name
  // o *_iort_ETOKENSERVER    - The eTokenServer hostname to be contacted for 
  //                            requesting grid proxies
  // o *_iort_MYPROXYSERVER   - The MyProxyServer hostname for requesting 
  //                            long-term grid proxies
  // o *_iort_PORT            - The port on which the eTokenServer is listening
  // o *_iort_ROBOTID         - The robotID to generate the grid proxy
  // o *_iort_ROLE            - The FQAN for the grid proxy (if any)
  // o *_iort_REWAL           - Enable the creation of a long-term proxy to a 
  //                            MyProxy Server (default 'yes')
  // o *_iort_DISABLEVOMS     - Disable the creation of a VOMS proxy (default 'no')
  //
  // o iort_APPID             - The ApplicationID for the IORT application
  // o iort_LOGLEVEL          - The portlet log level (INFO, VERBOSE)
  // o iort_OUTPUT_PATH       - The ApplicationID for the IORT results
  // o iort_SOFTWARE          - The Application Software requested by the application
  // o TRACKING_DB_HOSTNAME   - The Tracking DB server hostname 
  // o TRACKING_DB_USERNAME   - The username credential for login
  // o TRACKING_DB_PASSWORD   - The password for login
%>

<jsp:useBean id="lato_iort_INFRASTRUCTURE" class="java.lang.String" scope="request"/>
<jsp:useBean id="lato_iort_PASSWD" class="java.lang.String" scope="request"/>
<jsp:useBean id="lato_iort_WMS" class="java.lang.String[]" scope="request"/>
<jsp:useBean id="lato_iort_LOGIN" class="java.lang.String" scope="request"/>
<jsp:useBean id="lato_iort_ETOKENSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="lato_iort_MYPROXYSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="lato_iort_PORT" class="java.lang.String" scope="request"/>
<jsp:useBean id="lato_iort_ROBOTID" class="java.lang.String" scope="request"/>
<jsp:useBean id="lato_iort_ROLE" class="java.lang.String" scope="request"/>
<jsp:useBean id="lato_iort_RENEWAL" class="java.lang.String" scope="request"/>
<jsp:useBean id="lato_iort_DISABLEVOMS" class="java.lang.String" scope="request"/>

<jsp:useBean id="gridit_iort_INFRASTRUCTURE" class="java.lang.String" scope="request"/>
<jsp:useBean id="gridit_iort_TOPBDII" class="java.lang.String" scope="request"/>
<jsp:useBean id="gridit_iort_WMS" class="java.lang.String[]" scope="request"/>
<jsp:useBean id="gridit_iort_VONAME" class="java.lang.String" scope="request"/>
<jsp:useBean id="gridit_iort_ETOKENSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="gridit_iort_MYPROXYSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="gridit_iort_PORT" class="java.lang.String" scope="request"/>
<jsp:useBean id="gridit_iort_ROBOTID" class="java.lang.String" scope="request"/>
<jsp:useBean id="gridit_iort_ROLE" class="java.lang.String" scope="request"/>
<jsp:useBean id="gridit_iort_RENEWAL" class="java.lang.String" scope="request"/>
<jsp:useBean id="gridit_iort_DISABLEVOMS" class="java.lang.String" scope="request"/>

<jsp:useBean id="eumed_iort_INFRASTRUCTURE" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_iort_TOPBDII" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_iort_WMS" class="java.lang.String[]" scope="request"/>
<jsp:useBean id="eumed_iort_VONAME" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_iort_ETOKENSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_iort_MYPROXYSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_iort_PORT" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_iort_ROBOTID" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_iort_ROLE" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_iort_RENEWAL" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_iort_DISABLEVOMS" class="java.lang.String" scope="request"/>

<jsp:useBean id="biomed_iort_INFRASTRUCTURE" class="java.lang.String" scope="request"/>
<jsp:useBean id="biomed_iort_TOPBDII" class="java.lang.String" scope="request"/>
<jsp:useBean id="biomed_iort_WMS" class="java.lang.String[]" scope="request"/>
<jsp:useBean id="biomed_iort_VONAME" class="java.lang.String" scope="request"/>
<jsp:useBean id="biomed_iort_ETOKENSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="biomed_iort_MYPROXYSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="biomed_iort_PORT" class="java.lang.String" scope="request"/>
<jsp:useBean id="biomed_iort_ROBOTID" class="java.lang.String" scope="request"/>
<jsp:useBean id="biomed_iort_ROLE" class="java.lang.String" scope="request"/>
<jsp:useBean id="biomed_iort_RENEWAL" class="java.lang.String" scope="request"/>
<jsp:useBean id="biomed_iort_DISABLEVOMS" class="java.lang.String" scope="request"/>

<jsp:useBean id="iort_ENABLEINFRASTRUCTURE" class="java.lang.String[]" scope="request"/>
<jsp:useBean id="iort_APPID" class="java.lang.String" scope="request"/>
<jsp:useBean id="iort_LOGLEVEL" class="java.lang.String" scope="request"/>
<jsp:useBean id="iort_OUTPUT_PATH" class="java.lang.String" scope="request"/>
<jsp:useBean id="iort_SOFTWARE" class="java.lang.String" scope="request"/>
<jsp:useBean id="TRACKING_DB_HOSTNAME" class="java.lang.String" scope="request"/>
<jsp:useBean id="TRACKING_DB_USERNAME" class="java.lang.String" scope="request"/>
<jsp:useBean id="TRACKING_DB_PASSWORD" class="java.lang.String" scope="request"/>

<jsp:useBean id="SMTP_HOST" class="java.lang.String" scope="request"/>
<jsp:useBean id="SENDER_MAIL" class="java.lang.String" scope="request"/>

<script type="text/javascript">
    
    //var EnabledInfrastructure = "<%= iort_ENABLEINFRASTRUCTURE %>";    
    //console.log(EnabledInfrastructure);        
            
    $(document).ready(function() { 
        
        var EnabledInfrastructure = null;           
        var infrastructures = new Array();  
        var i = 0;    
    
        <c:forEach items="<%= iort_ENABLEINFRASTRUCTURE %>" var="current">
        <c:choose>
        <c:when test="${current!=null}">
            infrastructures[i] = '<c:out value='${current}'/>';       
            i++;  
        </c:when>
        </c:choose>
        </c:forEach>
        
        for (var i = 0; i < infrastructures.length; i++) {
        console.log("Reading array = " + infrastructures[i]);
        if (infrastructures[i]=="lato") EnabledInfrastructure='lato';        
        if (infrastructures[i]=="gridit") EnabledInfrastructure='gridit';        
        if (infrastructures[i]=="eumed")  EnabledInfrastructure='eumed';
        if (infrastructures[i]=="biomed") EnabledInfrastructure='biomed';
        }
        
        var NMAX = infrastructures.length;
        console.log (NMAX);
        console.log (EnabledInfrastructure);
                
        var lato_inputs = 1;        
        // ADDING a new WMS enpoint for the COMETA infrastructure (MAX. 5)
        $('#adding_WMS_lato').click(function() {        
            ++lato_inputs;        
            if (lato_inputs>1 && lato_inputs<6) {
            var c = $('.cloned_lato_iort_WMS:first').clone(true);            
            c.children(':text').attr('name','lato_iort_WMS' );
            c.children(':text').attr('id','lato_iort_WMS' );
            $('.cloned_lato_iort_WMS:last').after(c);
        }        
        });
        
        // REMOVING a new WMS enpoint for the COMETA infrastructure
        $('.btnDel_lato').click(function() {
        if (lato_inputs > 1)
            if (confirm('Do you really want to delete the item ?')) {
            --lato_inputs;
            $(this).closest('.cloned_lato_iort_WMS').remove();
            $('.btnDel_lato').attr('disabled',($('.cloned_lato_iort_WMS').length  < 2));
        }
        });
        
        $('.btnDel_lato2').click(function() {            
            if (confirm('Do you really want to delete the item ?')) {
            --lato_inputs;
            $(this).closest('.cloned_cached_latoWMS').remove();
            $('.btnDel_lato2').attr('disabled',($('.cloned_cached_latoWMS').length  < 2));
        }
        });
                
        var gridit_inputs = 1;        
        // ADDING a new WMS enpoint for the GRIDIT infrastructure (MAX. 5)
        $('#adding_WMS_gridit').click(function() {        
            ++gridit_inputs;        
            if (gridit_inputs>1 && gridit_inputs<6) {
            var c = $('.cloned_gridit_iort_WMS:first').clone(true);            
            c.children(':text').attr('name','gridit_iort_WMS' );
            c.children(':text').attr('id','gridit_iort_WMS' );
            $('.cloned_gridit_iort_WMS:last').after(c);
        }        
        });
        
        // REMOVING a new WMS enpoint for the GRIDIT infrastructure
        $('.btnDel_gridit').click(function() {
        if (gridit_inputs > 1)
            if (confirm('Do you really want to delete the item ?')) {
            --gridit_inputs;
            $(this).closest('.cloned_gridit_iort_WMS').remove();
            $('.btnDel_gridit').attr('disabled',($('.cloned_gridit_iort_WMS').length  < 2));
        }
        });
        
        $('.btnDel_gridit2').click(function() {            
            if (confirm('Do you really want to delete the item ?')) {
            --gridit_inputs;
            $(this).closest('.cloned_cached_griditWMS').remove();
            $('.btnDel_gridit2').attr('disabled',($('.cloned_cached_griditWMS').length  < 2));
        }
        });
                        
        
        var eumed_inputs = 1;        
        // ADDING a new WMS enpoint for the EUMED infrastructure (MAX. 5)
        $('#adding_WMS_eumed').click(function() {        
            ++eumed_inputs;        
            if (eumed_inputs>1 && eumed_inputs<6) {
            var c = $('.cloned_eumed_iort_WMS:first').clone(true);            
            c.children(':text').attr('name','eumed_iort_WMS' );
            c.children(':text').attr('id','eumed_iort_WMS' );
            $('.cloned_eumed_iort_WMS:last').after(c);
        }        
        });
        
        // REMOVING a new WMS enpoint for the EUMED infrastructure
        $('.btnDel_eumed').click(function() {
        if (eumed_inputs > 1)
            if (confirm('Do you really want to delete the item ?')) {
            --eumed_inputs;
            $(this).closest('.cloned_eumed_iort_WMS').remove();
            $('.btnDel_eumed').attr('disabled',($('.cloned_eumed_iort_WMS').length  < 2));
        }
        });
                
        $('.btnDel_eumed2').click(function() {            
            if (confirm('Do you really want to delete the item ?')) {
            --eumed_inputs;
            $(this).closest('.cloned_cached_eumedWMS').remove();
            $('.btnDel_eumed2').attr('disabled',($('.cloned_cached_eumedWMS').length  < 2));
        }
        });
        
        var biomed_inputs = 1;        
        // ADDING a new WMS enpoint for the BIOMED infrastructure (MAX. 5)
        $('#adding_WMS_biomed').click(function() {        
            ++biomed_inputs;        
            if (biomed_inputs>1 && biomed_inputs<6) {
            var c = $('.cloned_biomed_iort_WMS:first').clone(true);            
            c.children(':text').attr('name','biomed_iort_WMS' );
            c.children(':text').attr('id','biomed_iort_WMS' );
            $('.cloned_biomed_iort_WMS:last').after(c);
        }        
        });
        
        // REMOVING a new WMS enpoint for the BIOMED infrastructure
        $('.btnDel_biomed').click(function() {
        if (biomed_inputs > 1)
            if (confirm('Do you really want to delete the item ?')) {
            --biomed_inputs;
            $(this).closest('.cloned_biomed_iort_WMS').remove();
            $('.btnDel_biomed').attr('disabled',($('.cloned_biomed_iort_WMS').length  < 2));
        }
        });
        
        $('.btnDel_biomed2').click(function() {            
            if (confirm('Do you really want to delete the item ?')) {
            --biomed_inputs;
            $(this).closest('.cloned_cached_biomedWMS').remove();
            $('.btnDel_biomed2').attr('disabled',($('.cloned_cached_biomedWMS').length  < 2));
        }
        });

        // Validate input form
        $('#IortEditForm').validate({
            rules: {
                lato_iort_INFRASTRUCTURE: {
                    required: true              
                },
                lato_iort_LOGIN: {
                    required: true              
                },
                lato_iort_PASSWD: {
                    required: true
                },
                lato_iort_WMS: {
                    required: true
                },
                /*lato_iort_MYPROXYSERVER: {
                    required: true
                },
                lato_iort_ETOKENSERVER: {
                    required: true
                },                
                lato_iort_PORT: {
                    required: true
                },
                lato_iort_ROBOTID: {
                    required: true
                },*/
                
                
                gridit_iort_INFRASTRUCTURE: {
                    required: true              
                },
                gridit_iort_VONAME: {
                    required: true              
                },
                gridit_iort_TOPBDII: {
                    required: true
                },
                gridit_iort_WMS: {
                    required: true
                },
                gridit_iort_MYPROXYSERVER: {
                    required: true
                },
                gridit_iort_ETOKENSERVER: {
                    required: true
                },                
                gridit_iort_PORT: {
                    required: true
                },
                gridit_iort_ROBOTID: {
                    required: true
                },
                
                                
                eumed_iort_INFRASTRUCTURE: {
                    required: true              
                },
                eumed_iort_VONAME: {
                    required: true              
                },
                eumed_iort_TOPBDII: {
                    required: true
                },
                eumed_iort_WMS: {
                    required: true
                },
                eumed_iort_MYPROXYSERVER: {
                    required: true
                },
                eumed_iort_ETOKENSERVER: {
                    required: true
                },                
                eumed_iort_PORT: {
                    required: true
                },
                eumed_iort_ROBOTID: {
                    required: true
                },
                
                
                biomed_iort_INFRASTRUCTURE: {
                    required: true              
                },
                biomed_iort_VONAME: {
                    required: true              
                },
                biomed_iort_TOPBDII: {
                    required: true
                },
                biomed_iort_WMS: {
                    required: true
                },
                biomed_iort_MYPROXYSERVER: {
                    required: true
                },
                biomed_iort_ETOKENSERVER: {
                    required: true
                },                
                biomed_iort_PORT: {
                    required: true
                },
                biomed_iort_ROBOTID: {
                    required: true
                },                
                
                iort_APPID: {
                    required: true              
                },
                iort_LOGLEVEL: {
                    required: true              
                },
                iort_OUTPUT_PATH: {
                    required: true              
                }
            },
            
            invalidHandler: function(form, validator) {
                var errors = validator.numberOfInvalids();
                if (errors) {
                    $("#error_message").empty();
                    var message = errors == 1
                    ? ' You missed 1 field. It has been highlighted'
                    : ' You missed ' + errors + ' fields. They have been highlighted';                    
                    $('#error_message').append("<img width='30' src='<%=renderRequest.getContextPath()%>/images/Warning.png' border='0'>"+message);
                    $("#error_message").show();
                } else $("#error_message").hide();                
            },
            
            submitHandler: function(form) {
                   form.submit();
            }
        });
        
        $("#IortEditForm").bind('submit', function () {            
            // Check if the IORT OPTIONS are NULL
            if ( !$('#lato_iort_RENEWAL').is(':checked') && 
                 !$('#lato_iort_DISABLEVOMS').is(':checked') 
             ) $('#lato_iort_OPTIONS').val('NULL');
                 
            if ( !$('#gridit_iort_RENEWAL').is(':checked') && 
                 !$('#gridit_iort_DISABLEVOMS').is(':checked') 
             ) $('#gridit_iort_OPTIONS').val('NULL');                 
                 
            if ( !$('#eumed_iort_RENEWAL').is(':checked') && 
                  !$('#eumed_iort_DISABLEVOMS').is(':checked') 
             ) $('#eumed_iort_OPTIONS').val('NULL');
                 
            if ( !$('#biomed_iort_RENEWAL').is(':checked') && 
                  !$('#biomed_iort_DISABLEVOMS').is(':checked') 
             ) $('#biomed_iort_OPTIONS').val('NULL');                             
                 
             //("#iort_ENABLEINFRASTRUCTURE").val(EnabledInfrastructure);             
             
             /*if (EnabledInfrastructure=="gridit")
                if ($('#gridit_iort_INFRASTRUCTURE').val(' N/A') ||
                    $('#gridit_iort_VONAME').val(' N/A') ||
                    $('#gridit_iort_TOPBDII').val(' N/A') ||
                    $('#gridit_iort_WMS').val(' N/A') ||         
                    $('#gridit_iort_MYPROXYSERVER').val(' N/A') ||
                    $('#gridit_iort_ETOKENSERVER').val(' N/A') ||                
                    $('#gridit_iort_PORT').val(' N/A') ||
                    $('#gridit_iort_ROBOTID').val(' N/A')) {             
                    $("#error_message").empty();
                    var message = 'You have specified empty or not valid settings for the Infrastructure';                    
                    $('#error_message').append("<img width='30' src='<%=renderRequest.getContextPath()%>/images/Warning.png' border='0'>"+message);
                    $("#error_message").show();
                    
                    }*/
       });                
    });    
    
    /*function resetIORTSettings()
    {            
        $('#lato_iort_INFRASTRUCTURE').val('N/A');
        $('#lato_iort_PASSWD').val('N/A');
        $('#lato_iort_WMS').val('N/A');
        $('#lato_iort_LOGIN').val('N/A');        
        $('#lato_iort_ETOKENSERVER').val('N/A');
        $('#lato_iort_MYPROXYSERVER').val('N/A');
        $('#lato_iort_PORT').val('N/A');
        $('#lato_iort_ROBOTID').val('N/A');
        $('#lato_iort_ROLE').val('N/A');        
        $('input[type=\'checkbox\'][name=\'#lato_iort_RENEWAL\']').attr ('checked', true);
        $('input[type=\'checkbox\'][name=\'#lato_iort_DISABLEVOMS\']').attr ('checked', false);
        
        $('#gridit_iort_INFRASTRUCTURE').val('N/A');
        $('#gridit_iort_TOPBDII').val('N/A');
        $('#gridit_iort_WMS').val('N/A');
        $('#gridit_iort_VONAME').val('N/A');        
        $('#gridit_iort_ETOKENSERVER').val('N/A');
        $('#gridit_iort_MYPROXYSERVER').val('N/A');
        $('#gridit_iort_PORT').val('N/A');
        $('#gridit_iort_ROBOTID').val('N/A');
        $('#gridit_iort_ROLE').val('N/A');        
        $('input[type=\'checkbox\'][name=\'#gridit_iort_RENEWAL\']').attr ('checked', true);
        $('input[type=\'checkbox\'][name=\'#gridit_iort_DISABLEVOMS\']').attr ('checked', false);        
                
        $('#eumed_iort_INFRASTRUCTURE').val('N/A');
        $('#eumed_iort_TOPBDII').val('N/A');
        $('#eumed_iort_WMS').val('N/A');
        $('#eumed_iort_VONAME').val('N/A');        
        $('#eumed_iort_ETOKENSERVER').val('N/A');
        $('#eumed_iort_MYPROXYSERVER').val('N/A');
        $('#eumed_iort_PORT').val('N/A');
        $('#eumed_iort_ROBOTID').val('N/A');
        $('#eumed_iort_ROLE').val('N/A');        
        $('input[type=\'checkbox\'][name=\'#eumed_iort_RENEWAL\']').attr ('checked', true);
        $('input[type=\'checkbox\'][name=\'#eumed_iort_DISABLEVOMS\']').attr ('checked', false);
        
        $('#biomed_iort_INFRASTRUCTURE').val('N/A');
        $('#biomed_iort_TOPBDII').val('N/A');
        $('#biomed_iort_WMS').val('N/A');
        $('#biomed_iort_VONAME').val('N/A');        
        $('#biomed_iort_ETOKENSERVER').val('N/A');
        $('#biomed_iort_MYPROXYSERVER').val('N/A');
        $('#biomed_iort_PORT').val('N/A');
        $('#biomed_iort_ROBOTID').val('N/A');
        $('#biomed_iort_ROLE').val('N/A');        
        $('input[type=\'checkbox\'][name=\'#biomed_iort_RENEWAL\']').attr ('checked', true);
        $('input[type=\'checkbox\'][name=\'#biomed_iort_DISABLEVOMS\']').attr ('checked', false);
        
        $("#iort_ENABLEINFRASTRUCTURE").removeAttr("checked");
        $('#iort_APPID').val('N/A');
        $('#iort_SOFTWARE').val('N/A');
        $('#TRACKING_DB_HOSTNAME').val('N/A');
        $('#TRACKING_DB_USERNAME').val('N/A');
        $('#TRACKING_DB_PASSWORD').val('N/A');
        $('#SMTP_HOST').val('N/A');
        $('#SENDER_MAIL').val('N/A');
        return false;
    }*/
        
</script>

<br/>
<p style="width:690px; font-family: Tahoma,Verdana,sans-serif,Arial; font-size: 14px;">
    Please, select the IORT Grid Settings before to start</p>  

<!DOCTYPE html>
<form id="IortEditForm"
      name="IortEditForm"
      action="<portlet:actionURL><portlet:param name="ActionEvent" value="CONFIG_IORT_PORTLET"/></portlet:actionURL>" 
      method="POST">

<fieldset>
<legend>Portlet Settings</legend>
<div style="margin-left:15px; font-family: Tahoma,Verdana,sans-serif,Arial; font-size: 14px;" id="error_message"></div>
<br/>
<table id="results" border="0" width="620" style="width:690px; font-family: Tahoma,Verdana,sans-serif,Arial; font-size: 14px;">

<!-- LATO -->
<tr></tr>
<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%=renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="Enable the Infrastructure Acronym" />
   
        <label for="iort_ENABLEINFRASTRUCTURE">Enabled<em>*</em></label>
    </td>
    
    <td>        
        <c:forEach var="enabled" items="<%= iort_ENABLEINFRASTRUCTURE %>">
            <c:if test="${enabled=='lato'}">
                <c:set var="results_lato" value="true"></c:set>
            </c:if>
        </c:forEach>
                
        <c:choose>
        <c:when test="${results_lato=='true'}">
        <input type="checkbox" 
               id="lato_iort_ENABLEINFRASTRUCTURE"
               name="iort_ENABLEINFRASTRUCTURES"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="48px;"
               value="lato"
               checked="checked"/>
        </c:when>
        <c:otherwise>
        <input type="checkbox" 
               id="lato_iort_ENABLEINFRASTRUCTURE"
               name="iort_ENABLEINFRASTRUCTURES"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="48px;"
               value="lato"/>
        </c:otherwise>
        </c:choose>
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%=renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The Infrastructure Acronym" />
   
        <label for="lato_iort_INFRASTRUCTURE">Infrastructure<em>*</em></label>
    </td>    
    <td>
        <input type="text" 
               id="lato_iort_INFRASTRUCTURE"
               name="lato_iort_INFRASTRUCTURE"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value="LATO" />        
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The login credential to access the LATO Infrastructure" />
   
        <label for="lato_iort_LOGIN">Login<em>*</em></label> 
    </td>
    <td>
        <input type="text" 
               id="lato_iort_LOGIN"
               name="lato_iort_LOGIN"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="15px;" 
               value=" <%= lato_iort_LOGIN %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The password credential to access the LATO Infrastructure" />
   
        <label for="lato_iort_PASSWD">Password<em>*</em></label>
    </td>    
    <td>
        <input type="password" 
               id="lato_iort_PASSWD"
               name="lato_iort_PASSWD"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value=" <%= lato_iort_PASSWD %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"         
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The Cluster server hostname to access the Infrastructure" />
   
        <label for="lato_iort_WMS">Cluster<em>*</em></label>
    </td>
    <td>          
        <c:forEach var="wms" items="<%= lato_iort_WMS %>">
            <c:if test="${(!empty wms && wms!='N/A')}">
            <div style="margin-bottom:4px;" class="cloned_cached_latoWMS">
            <input type="text"                
                   name="lato_iort_WMS"
                   class="textfield ui-widget ui-widget-content ui-state-focus required"
                   size="50px;"               
                   value=" <c:out value="${wms}"/>" />
            <img type="button" class="btnDel_lato2" width="18"
                 src="<%= renderRequest.getContextPath()%>/images/remove.png" 
                 border="0" title="Remove a WMS Endopoint" />
            </div>
            </c:if>
        </c:forEach>        
        
        <div style="margin-bottom:4px;" class="cloned_lato_iort_WMS">
        <input type="text"                
               name="lato_iort_WMS"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;"               
               value=" N/A"/>
        <img type="button" id="adding_WMS_lato" width="18"
             src="<%= renderRequest.getContextPath()%>/images/plus_orange.png" 
             border="0" title="Add a new WMS Endopoint" />
        <img type="button" class="btnDel_lato" width="18"
             src="<%= renderRequest.getContextPath()%>/images/remove.png" 
             border="0" title="Remove a WMS Endopoint" />
        </div>
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The MyProxyServer hostname for requesting long-term grid proxies" />
   
        <label for="lato_iort_MYPROXYSERVER">MyProxyServer</label>
    </td>
    <td>
        <input type="text" 
               id="lato_iort_MYPROXYSERVER"
               name="lato_iort_MYPROXYSERVER"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               size="50px;" 
               value=" <%= lato_iort_MYPROXYSERVER %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The eTokenServer hostname to be contacted for requesting grid proxies" />
   
        <label for="lato_iort_ETOKENSERVER">eTokenServer</label>
    </td>
    <td>
        <input type="text" 
               id="lato_iort_ETOKENSERVER"
               name="lato_iort_ETOKENSERVER"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               size="50px;" 
               value=" <%= lato_iort_ETOKENSERVER %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The eTokenServer port" />
   
        <label for="lato_iort_PORT">Port</label>
    </td>
    <td>
        <input type="text" 
               id="lato_iort_PORT"
               name="lato_iort_PORT"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               size="15px;" 
               value=" <%= lato_iort_PORT %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The certificate serial number to generate proxies" />
   
        <label for="lato_iort_ROBOTID">Serial Number</label>
    </td>
    <td>
        <input type="text" 
               id="lato_iort_ROBOTID"
               name="lato_iort_ROBOTID"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               size="50px;" 
               value=" <%= lato_iort_ROBOTID %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The FQANs for the grid proxy (if any)" />
   
        <label for="lato_iort_ROLE">Role</label>
    </td>
    <td>
        <input type="text" 
               id="lato_iort_ROLE"
               name="lato_iort_ROLE"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               size="50px;" 
               value=" <%= lato_iort_ROLE %>" />            
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="Enable the creation of a long-term proxy to a MyProxy Server" />
   
        <label for="lato_iort_RENEWAL">Proxy Renewal</label>
    </td>
    <td>
        <input type="checkbox" 
               id="lato_iort_RENEWAL"
               name="lato_iort_OPTIONS"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               size="50px;" 
               <%= lato_iort_RENEWAL %> 
               value="enableRENEWAL" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="Disable the creation of a VOMS proxy" />
   
        <label for="lato_iort_DISABLEVOMS">Disable VOMS</label>
    </td>
    <td>
        <input type="checkbox" 
               id="lato_iort_DISABLEVOMS"
               name="lato_iort_OPTIONS"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               <%= lato_iort_DISABLEVOMS %>
               size="50px;" 
               value="disableVOMS" />
    </td>    
</tr>
    
<!-- GRIDIT -->  
<tr></tr>
<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%=renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="Enable the Infrastructure Acronym" />
   
        <label for="iort_ENABLEINFRASTRUCTURE">Enabled<em>*</em></label>
    </td>    
    <td>
        
        <c:forEach var="enabled" items="<%= iort_ENABLEINFRASTRUCTURE %>">
            <c:if test="${enabled=='gridit'}">
                <c:set var="results_gridit" value="true"></c:set>
            </c:if>
        </c:forEach>
        
        <c:choose>
        <c:when test="${results_gridit=='true'}">
        <input type="checkbox" 
               id="iort_ENABLEINFRASTRUCTURE"
               name="iort_ENABLEINFRASTRUCTURES"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="48px;"
               value="gridit"               
               checked="checked"/>
        </c:when>
        <c:otherwise>
        <input type="checkbox" 
               id="iort_ENABLEINFRASTRUCTURE"
               name="iort_ENABLEINFRASTRUCTURES"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="48px;"
               value="gridit"/>
        </c:otherwise>
        </c:choose>
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%=renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The Infrastructure Acronym" />
   
        <label for="gridit_iort_INFRASTRUCTURE">Infrastructure<em>*</em></label>
    </td>    
    <td>
        <input type="text" 
               id="gridit_iort_INFRASTRUCTURE"
               name="gridit_iort_INFRASTRUCTURE"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value="GRIDIT" />        
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The VO name" />
   
        <label for="gridit_iort_VONAME">VOname<em>*</em></label> 
    </td>
    <td>
        <input type="text" 
               id="gridit_iort_VONAME"
               name="gridit_iort_VONAME"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="15px;" 
               value=" <%= gridit_iort_VONAME %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The TopBDII hostname for accessing the Infrastructure" />
   
        <label for="gridit_iort_TOPBDII">TopBDII<em>*</em></label>
    </td>    
    <td>
        <input type="text" 
               id="gridit_iort_TOPBDII"
               name="gridit_iort_TOPBDII"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value=" <%= gridit_iort_TOPBDII %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"         
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The WMProxy hostname for accessing the Infrastructure" />
   
        <label for="gridit_iort_WMS">WMS Endpoint<em>*</em></label>
    </td>
    <td>          
        <c:forEach var="wms" items="<%= gridit_iort_WMS %>">
            <c:if test="${(!empty wms && wms!='N/A')}">
            <div style="margin-bottom:4px;" class="cloned_cached_griditWMS">
            <input type="text"                
                   name="gridit_iort_WMS"
                   class="textfield ui-widget ui-widget-content ui-state-focus required"
                   size="50px;"               
                   value=" <c:out value="${wms}"/>" />
            <img type="button" class="btnDel_gridit2" width="18"
                 src="<%= renderRequest.getContextPath()%>/images/remove.png" 
                 border="0" title="Remove a WMS Endopoint" />
            </div>
            </c:if>
        </c:forEach>        
        
        <div style="margin-bottom:4px;" class="cloned_gridit_iort_WMS">
        <input type="text"                
               name="gridit_iort_WMS"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;"               
               value=" N/A"/>
        <img type="button" id="adding_WMS_gridit" width="18"
             src="<%= renderRequest.getContextPath()%>/images/plus_orange.png" 
             border="0" title="Add a new WMS Endopoint" />
        <img type="button" class="btnDel_gridit" width="18"
             src="<%= renderRequest.getContextPath()%>/images/remove.png" 
             border="0" title="Remove a WMS Endopoint" />
        </div>
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The MyProxyServer hostname for requesting long-term grid proxies" />
   
        <label for="gridit_iort_MYPROXYSERVER">MyProxyServer<em>*</em></label>
    </td>
    <td>
        <input type="text" 
               id="gridit_iort_MYPROXYSERVER"
               name="gridit_iort_MYPROXYSERVER"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value=" <%= gridit_iort_MYPROXYSERVER %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The eTokenServer hostname to be contacted for requesting grid proxies" />
   
        <label for="gridit_iort_ETOKENSERVER">eTokenServer<em>*</em></label>
    </td>
    <td>
        <input type="text" 
               id="gridit_iort_ETOKENSERVER"
               name="gridit_iort_ETOKENSERVER"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value=" <%= gridit_iort_ETOKENSERVER %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The eTokenServer port" />
   
        <label for="gridit_iort_PORT">Port<em>*</em></label>
    </td>
    <td>
        <input type="text" 
               id="gridit_iort_PORT"
               name="gridit_iort_PORT"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="15px;" 
               value=" <%= gridit_iort_PORT %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The certificate serial number to generate proxies" />
   
        <label for="gridit_iort_ROBOTID">Serial Number<em>*</em></label>
    </td>
    <td>
        <input type="text" 
               id="gridit_iort_ROBOTID"
               name="gridit_iort_ROBOTID"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value=" <%= gridit_iort_ROBOTID %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The FQANs for the grid proxy (if any)" />
   
        <label for="gridit_iort_ROLE">Role</label>
    </td>
    <td>
        <input type="text" 
               id="gridit_iort_ROLE"
               name="gridit_iort_ROLE"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               size="50px;" 
               value=" <%= gridit_iort_ROLE %>" />            
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="Enable the creation of a long-term proxy to a MyProxy Server" />
   
        <label for="gridit_iort_RENEWAL">Proxy Renewal</label>
    </td>
    <td>
        <input type="checkbox" 
               id="gridit_iort_RENEWAL"
               name="gridit_iort_OPTIONS"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               size="50px;" 
               <%= gridit_iort_RENEWAL %> 
               value="enableRENEWAL" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="Disable the creation of a VOMS proxy" />
   
        <label for="gridit_iort_DISABLEVOMS">Disable VOMS</label>
    </td>
    <td>
        <input type="checkbox" 
               id="gridit_iort_DISABLEVOMS"
               name="gridit_iort_OPTIONS"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               <%= gridit_iort_DISABLEVOMS %>
               size="50px;" 
               value="disableVOMS" />
    </td>    
</tr>

<!-- EUMED -->
<tr></tr>
<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="Enable the Infrastructure Acronym" />
   
        <label for="iort_ENABLEINFRASTRUCTURE">Enabled<em>*</em></label>
    </td>
    
    <td>        
        <c:forEach var="enabled" items="<%= iort_ENABLEINFRASTRUCTURE %>">
            <c:if test="${enabled=='eumed'}">
                <c:set var="results_eumed" value="true"></c:set>
            </c:if>
        </c:forEach>
                
        <c:choose>
        <c:when test="${results_eumed=='true'}">
        <input type="checkbox" 
               id="iort_ENABLEINFRASTRUCTURE"
               name="iort_ENABLEINFRASTRUCTURES"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="48px;"
               value="eumed"               
               checked="checked"/>
        </c:when>
        <c:otherwise>
        <input type="checkbox" 
               id="iort_ENABLEINFRASTRUCTURE"
               name="iort_ENABLEINFRASTRUCTURES"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="48px;"
               value="eumed"/>
        </c:otherwise>
        </c:choose>
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The Infrastructure Acronym" />
   
        <label for="EUMED_iort_INFRASTRUCTURE">Infrastructure<em>*</em></label>
    </td>    
    <td>
        <input type="text" 
               id="eumed_iort_INFRASTRUCTURE"
               name="eumed_iort_INFRASTRUCTURE"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value="EUMED" />        
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The VO name" />
   
        <label for="eumed_iort_VONAME">VOname<em>*</em></label> 
    </td>
    <td>
        <input type="text" 
               id="eumed_iort_VONAME"
               name="eumed_iort_VONAME"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="15px;" 
               value=" <%= eumed_iort_VONAME %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The TopBDII hostname for accessing the Infrastructure" />
   
        <label for="eumed_iort_TOPBDII">TopBDII<em>*</em></label>
    </td>    
    <td>
        <input type="text" 
               id="eumed_iort_TOPBDII"
               name="eumed_iort_TOPBDII"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value=" <%= eumed_iort_TOPBDII %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The WMProxy hostname for accessing the Infrastructure" />
   
        <label for="eumed_iort_WMS">WMS Endpoint<em>*</em></label>
    </td>
    <td>
        <c:forEach var="wms" items="<%= eumed_iort_WMS %>">
            <c:if test="${(!empty wms && wms!='N/A')}">
            <div style="margin-bottom:4px;" class="cloned_cached_eumedWMS">
            <input type="text"                
                   name="eumed_iort_WMS"
                   id="eumed_iort_WMS"
                   class="textfield ui-widget ui-widget-content ui-state-focus required"
                   size="50px;"               
                   value=" <c:out value="${wms}"/>" />
            <img type="button" class="btnDel_eumed2" width="18"
                 src="<%= renderRequest.getContextPath()%>/images/remove.png" 
                 border="0" title="Remove a WMS Endopoint" />
            </div>
            </c:if>
        </c:forEach>        
        
        <div style="margin-bottom:4px;" class="cloned_eumed_iort_WMS">
        <input type="text" 
               id="eumed_iort_WMS"
               name="eumed_iort_WMS"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;"               
               value=" N/A"/>
        <img type="button" id="adding_WMS_eumed" width="18"
             src="<%= renderRequest.getContextPath()%>/images/plus_orange.png" 
             border="0" title="Add a new WMS Endopoint" />
        <img type="button" class="btnDel_eumed" width="18"
             src="<%= renderRequest.getContextPath()%>/images/remove.png" 
             border="0" title="Remove a WMS Endopoint" />
        </div>                     
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The MyProxyServer hostname for requesting long-term grid proxies" />
   
        <label for="eumed_iort_MYPROXYSERVER">MyProxyServer<em>*</em></label>
    </td>
    <td>
        <input type="text" 
               id="eumed_iort_MYPROXYSERVER"
               name="eumed_iort_MYPROXYSERVER"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value=" <%= eumed_iort_MYPROXYSERVER %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The eTokenServer hostname to be contacted for requesting grid proxies" />
   
        <label for="eumed_iort_ETOKENSERVER">eTokenServer<em>*</em></label>
    </td>
    <td>
        <input type="text" 
               id="eumed_iort_ETOKENSERVER"
               name="eumed_iort_ETOKENSERVER"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value=" <%= eumed_iort_ETOKENSERVER %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The eTokenServer port" />
   
        <label for="eumed_iort_PORT">Port<em>*</em></label>
    </td>
    <td>
        <input type="text" 
               id="eumed_iort_PORT"
               name="eumed_iort_PORT"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="15px;" 
               value=" <%= eumed_iort_PORT %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The certificate serial number to generate proxies" />
   
        <label for="eumed_iort_ROBOTID">Serial Number<em>*</em></label>
    </td>
    <td>
        <input type="text" 
               id="eumed_iort_ROBOTID"
               name="eumed_iort_ROBOTID"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value=" <%= eumed_iort_ROBOTID %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The FQANs for the grid proxy (if any)" />
   
        <label for="eumed_iort_ROLE">Role</label>
    </td>
    <td>
        <input type="text" 
               id="eumed_iort_ROLE"
               name="eumed_iort_ROLE"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               size="50px;" 
               value=" <%= eumed_iort_ROLE %>" />            
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="Enable the creation of a long-term proxy to a MyProxy Server" />
   
        <label for="eumed_iort_RENEWAL">Proxy Renewal</label>
    </td>
    <td>
        <input type="checkbox" 
               id="eumed_iort_RENEWAL"
               name="eumed_iort_OPTIONS"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               size="50px;" 
               <%= eumed_iort_RENEWAL %> 
               value="enableRENEWAL" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="Disable the creation of a VOMS proxy" />
   
        <label for="eumed_iort_DISABLEVOMS">Disable VOMS</label>
    </td>
    <td>
        <input type="checkbox" 
               id="eumed_iort_DISABLEVOMS"
               name="eumed_iort_OPTIONS"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               <%= eumed_iort_DISABLEVOMS %>
               size="50px;" 
               value="disableVOMS" />
    </td>    
</tr>

<!-- BIOMED -->
<tr></tr>
<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="Enable the Infrastructure Acroym" />
   
        <label for="iort_ENABLEINFRASTRUCTURE">Enabled<em>*</em></label>
    </td>
    
    <td>
        <c:forEach var="enabled" items="<%= iort_ENABLEINFRASTRUCTURE %>">
            <c:if test="${enabled=='biomed'}">
                <c:set var="results_biomed" value="true"></c:set>
            </c:if>
        </c:forEach>
        
        <c:choose>
        <c:when test="${results_biomed=='true'}">
        <input type="checkbox" 
               id="iort_ENABLEINFRASTRUCTURE"
               name="iort_ENABLEINFRASTRUCTURES"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="48px;"
               value="biomed"               
               checked="checked"/>
        </c:when>
        <c:otherwise>
        <input type="checkbox" 
               id="iort_ENABLEINFRASTRUCTURE"
               name="iort_ENABLEINFRASTRUCTURES"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="48px;"
               value="biomed"/>
        </c:otherwise>
        </c:choose>
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The Infrastructure Acronym" />
   
        <label for="BIOMED_iort_INFRASTRUCTURE">Infrastructure<em>*</em></label>
    </td>    
    <td>
        <input type="text" 
               id="biomed_iort_INFRASTRUCTURE"
               name="biomed_iort_INFRASTRUCTURE"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value="BIOMED" />
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The VO name" />
   
        <label for="biomed_iort_VONAME">VOname<em>*</em></label> 
    </td>
    <td>
        <input type="text" 
               id="biomed_iort_VONAME"
               name="biomed_iort_VONAME"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="15px;" 
               value=" <%= biomed_iort_VONAME %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The TopBDII hostname for accessing the Infrastructure" />
   
        <label for="biomed_iort_TOPBDII">TopBDII<em>*</em></label>
    </td>    
    <td>
        <input type="text" 
               id="biomed_iort_TOPBDII"
               name="biomed_iort_TOPBDII"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value=" <%= biomed_iort_TOPBDII %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The WMProxy hostname for accessing the Infrastructure" />
   
        <label for="biomed_iort_WMS">WMS Endpoint<em>*</em></label>
    </td>
    <td>
        
        <c:forEach var="wms" items="<%= biomed_iort_WMS %>">
            <c:if test="${(!empty wms && wms!='N/A')}">
            <div style="margin-bottom:4px;" class="cloned_cached_biomedWMS">
            <input type="text"                
                   name="biomed_iort_WMS"
                   id="biomed_iort_WMS"
                   class="textfield ui-widget ui-widget-content ui-state-focus required"
                   size="50px;"               
                   value=" <c:out value="${wms}"/>" />
            <img type="button" class="btnDel_biomed2" width="18"
                 src="<%= renderRequest.getContextPath()%>/images/remove.png" 
                 border="0" title="Remove a WMS Endopoint" />
            </div>
            </c:if>
        </c:forEach>
        
        <div style="margin-bottom:4px;" class="cloned_biomed_iort_WMS">
        <input type="text" 
               id="biomed_iort_WMS"
               name="biomed_iort_WMS"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;"               
               value=" N/A"/>
        <img type="button" id="adding_WMS_biomed" width="18"
             src="<%= renderRequest.getContextPath()%>/images/plus_orange.png" 
             border="0" title="Add a new WMS Endopoint" />
        <img type="button" class="btnDel_biomed" width="18"
             src="<%= renderRequest.getContextPath()%>/images/remove.png" 
             border="0" title="Remove a WMS Endopoint" />
        </div>                     
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The MyProxyServer hostname for requesting long-term grid proxies" />
   
        <label for="biomed_iort_MYPROXYSERVER">MyProxyServer<em>*</em></label>
    </td>
    <td>
        <input type="text" 
               id="biomed_iort_MYPROXYSERVER"
               name="biomed_iort_MYPROXYSERVER"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value=" <%= biomed_iort_MYPROXYSERVER %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The eTokenServer hostname to be contacted for requesting grid proxies" />
   
        <label for="biomed_iort_ETOKENSERVER">eTokenServer<em>*</em></label>
    </td>
    <td>
        <input type="text" 
               id="biomed_iort_ETOKENSERVER"
               name="biomed_iort_ETOKENSERVER"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value=" <%= biomed_iort_ETOKENSERVER %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The eTokenServer port" />
   
        <label for="biomed_iort_PORT">Port<em>*</em></label>
    </td>
    <td>
        <input type="text" 
               id="biomed_iort_PORT"
               name="biomed_iort_PORT"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="15px;" 
               value=" <%= biomed_iort_PORT %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The certificate serial number to generate proxies" />
   
        <label for="biomed_iort_ROBOTID">Serial Number<em>*</em></label>
    </td>
    <td>
        <input type="text" 
               id="biomed_iort_ROBOTID"
               name="biomed_iort_ROBOTID"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value=" <%= biomed_iort_ROBOTID %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The FQANs for the grid proxy (if any)" />
   
        <label for="biomed_iort_ROLE">Role</label>
    </td>
    <td>
        <input type="text" 
               id="biomed_iort_ROLE"
               name="biomed_iort_ROLE"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               size="50px;" 
               value=" <%= biomed_iort_ROLE %>" />            
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="Enable the creation of a long-term proxy to a MyProxy Server" />
   
        <label for="biomed_iort_RENEWAL">Proxy Renewal</label>
    </td>
    <td>
        <input type="checkbox" 
               id="biomed_iort_RENEWAL"
               name="biomed_iort_OPTIONS"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               size="50px;" 
               <%= biomed_iort_RENEWAL %> 
               value="enableRENEWAL" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="Disable the creation of a VOMS proxy" />
   
        <label for="biomed_iort_DISABLEVOMS">Disable VOMS</label>
    </td>
    <td>
        <input type="checkbox" 
               id="biomed_iort_DISABLEVOMS"
               name="biomed_iort_OPTIONS"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               <%= biomed_iort_DISABLEVOMS %>
               size="50px;" 
               value="disableVOMS" />
    </td>    
</tr>

<!-- LAST -->
<tr></tr>
<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The ApplicationID for IORT" />
   
        <label for="iort_APPID">AppID<em>*</em></label> 
    </td>
    <td>
        <input type="text" 
               id="iort_APPID"
               name="iort_APPID"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="15px;" 
               value=" <%= iort_APPID %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The Log Level of the portlet (E.g.: VERBOSE, INFO)" />
   
        <label for="wrf_LOGLEVEL">Log Level<em>*</em></label> 
    </td>
    <td>
        <input type="text" 
               id="iort_LOGLEVEL"
               name="iort_LOGLEVEL"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="15px;" 
               value=" <%= iort_LOGLEVEL %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The output path of the server's file-system where download results" />
   
        <label for="iort_OUTPUT_PATH">Output Path<em>*</em></label> 
    </td>
    <td>
        <input type="text" 
               id="iort_OUTPUT_PATH"
               name="iort_OUTPUT_PATH"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value=" <%= iort_OUTPUT_PATH %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The Application Software TAG" />
   
        <label for="iort_SOFTWARE">SoftwareTAG</label>
    </td>
    <td>
        <input type="text" 
               id="iort_SOFTWARE"
               name="iort_SOFTWARE"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               size="50px;" 
               value=" <%= iort_SOFTWARE %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The Tracking DB Server Hostname" />
   
        <label for="TRACKING_DB_HOSTNAME">HostName</label>
    </td>
    <td>
        <input type="text" 
               id="TRACKING_DB_HOSTNAME"
               name="TRACKING_DB_HOSTNAME"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               size="50px;" 
               value=" <%= TRACKING_DB_HOSTNAME %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The username credential for login the Tracking DB" />
   
        <label for="TRACKING_DB_USERNAME">UserName</label>
    </td>
    <td>
        <input type="text" 
               id="TRACKING_DB_USERNAME"
               name="TRACKING_DB_USERNAME"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               size="50px;" 
               value=" <%= TRACKING_DB_USERNAME %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The password credential for login  the Tracking DB" />
   
        <label for="TRACKING_DB_PASSWORD">Password</label>
    </td>
    <td>
        <input type="password" 
               id="TRACKING_DB_PASSWORD"
               name="TRACKING_DB_PASSWORD"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               size="50px;" 
               value=" <%= TRACKING_DB_PASSWORD %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The SMTP Server for sending notification" />
   
        <label for="SMTP_HOST">SMTP</label>
    </td>
    <td>
        <input type="text" 
               id="SMTP_HOST"
               name="SMTP_HOST"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               size="50px;" 
               value=" <%= SMTP_HOST %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The email address for sending notification" />
   
        <label for="Sender">Sender</label>
    </td>
    <td>
        <input type="text" 
               id="SENDER_MAIL"
               name="SENDER_MAIL"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               size="50px;" 
               value=" <%= SENDER_MAIL %>" />
    </td>    
</tr>

<!-- Buttons -->
<tr>            
    <tr><td>&nbsp;</td></tr>
    <td align="left">    
    <input type="image" src="<%= renderRequest.getContextPath()%>/images/save.png"
           width="50"
           name="Submit" title="Save the portlet settings" />        
    </td>
</tr>  

</table>
<br/>
<div id="pageNavPosition" style="width:690px; font-family: Tahoma,Verdana,sans-serif,Arial; font-size: 14px;">   
</div>
</fieldset>
           
<script type="text/javascript">
    var pager = new Pager('results', 13); 
    pager.init(); 
    pager.showPageNav('pager', 'pageNavPosition'); 
    pager.showPage(1);
</script>
</form>
