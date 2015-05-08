<%
/**************************************************************************
Copyright (c) 2011-2013:
Istituto Nazionale di Fisica Nucleare (INFN), Italy
Consorzio COMETA (COMETA), Italy
    
See http://www.infn.it and and http://www.consorzio-lato.it for details 
on the copyright holders.
    
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
**************************************************************************/
%>
<%@ page import="com.liferay.portal.kernel.util.WebKeys" %>
<%@ page import="com.liferay.portal.util.PortalUtil" %>
<%@ page import="com.liferay.portal.model.Company" %>
<%@ page import="javax.portlet.*" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<portlet:defineObjects/>

<%
  Company company = PortalUtil.getCompany(request);
  String gateway = company.getName();
%>

<jsp:useBean id="GPS_table" class="java.lang.String" scope="request"/>
<jsp:useBean id="GPS_queue" class="java.lang.String" scope="request"/>

<jsp:useBean id="lato_iort_INFRASTRUCTURE" class="java.lang.String" scope="request"/>
<jsp:useBean id="lato_iort_LOGIN" class="java.lang.String" scope="request"/>
<jsp:useBean id="lato_iort_PASSWD" class="java.lang.String" scope="request"/>
<jsp:useBean id="lato_iort_WMS" class="java.lang.String" scope="request"/>
<jsp:useBean id="lato_iort_MYPROXYSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="lato_iort_ETOKENSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="lato_iort_PORT" class="java.lang.String" scope="request"/>
<jsp:useBean id="lato_iort_ROBOTID" class="java.lang.String" scope="request"/>
<jsp:useBean id="lato_iort_ROLE" class="java.lang.String" scope="request"/>
<jsp:useBean id="lato_iort_RENEWAL" class="java.lang.String" scope="request"/>
<jsp:useBean id="lato_iort_DISABLEVOMS" class="java.lang.String" scope="request"/>

<jsp:useBean id="gridit_iort_INFRASTRUCTURE" class="java.lang.String" scope="request"/>
<jsp:useBean id="gridit_iort_VONAME" class="java.lang.String" scope="request"/>
<jsp:useBean id="gridit_iort_TOPBDII" class="java.lang.String" scope="request"/>
<jsp:useBean id="gridit_iort_WMS" class="java.lang.String" scope="request"/>
<jsp:useBean id="gridit_iort_MYPROXYSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="gridit_iort_ETOKENSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="gridit_iort_PORT" class="java.lang.String" scope="request"/>
<jsp:useBean id="gridit_iort_ROBOTID" class="java.lang.String" scope="request"/>
<jsp:useBean id="gridit_iort_ROLE" class="java.lang.String" scope="request"/>
<jsp:useBean id="gridit_iort_RENEWAL" class="java.lang.String" scope="request"/>
<jsp:useBean id="gridit_iort_DISABLEVOMS" class="java.lang.String" scope="request"/>

<jsp:useBean id="eumed_iort_INFRASTRUCTURE" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_iort_VONAME" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_iort_TOPBDII" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_iort_WMS" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_iort_MYPROXYSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_iort_ETOKENSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_iort_PORT" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_iort_ROBOTID" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_iort_ROLE" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_iort_RENEWAL" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_iort_DISABLEVOMS" class="java.lang.String" scope="request"/>

<jsp:useBean id="biomed_iort_INFRASTRUCTURE" class="java.lang.String" scope="request"/>
<jsp:useBean id="biomed_iort_TOPBDII" class="java.lang.String" scope="request"/>
<jsp:useBean id="biomed_iort_WMS" class="java.lang.String" scope="request"/>
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
<jsp:useBean id="iort_OUTPUT_PATH" class="java.lang.String" scope="request"/>
<jsp:useBean id="iort_SOFTWARE" class="java.lang.String" scope="request"/>
<jsp:useBean id="TRACKING_DB_HOSTNAME" class="java.lang.String" scope="request"/>
<jsp:useBean id="TRACKING_DB_USERNAME" class="java.lang.String" scope="request"/>
<jsp:useBean id="TRACKING_DB_PASSWORD" class="java.lang.String" scope="request"/>
<jsp:useBean id="SMTP_HOST" class="java.lang.String" scope="request"/>
<jsp:useBean id="SENDER_MAIL" class="java.lang.String" scope="request"/>

<script type="text/javascript">
    var latlng2markers = [];
    var icons = [];
    
    icons["plus"] = new google.maps.MarkerImage(
          '<%= renderRequest.getContextPath()%>/images/plus_new.png',
          new google.maps.Size(16,16),
          new google.maps.Point(0,0),
          new google.maps.Point(8,8));
    
    icons["minus"] = new google.maps.MarkerImage(
          '<%= renderRequest.getContextPath()%>/images/minus_new.png',
          new google.maps.Size(16,16),
          new google.maps.Point(0,0),
          new google.maps.Point(8,8));
    
    icons["ce"] = new google.maps.MarkerImage(
            '<%= renderRequest.getContextPath()%>/images/ce-run.png',
            new google.maps.Size(16,16),
            new google.maps.Point(0,0),
            new google.maps.Point(8,8));
    
    function hideMarkers(markersMap,map) 
    {
            for( var k in markersMap) 
            {
                if (markersMap[k].markers.length >1) {
                    var n = markersMap[k].markers.length;
                    var centerMark = new google.maps.Marker({
                        title: "Coordinates:" + markersMap[k].markers[0].getPosition().toString(),
                        position: markersMap[k].markers[0].getPosition(),
                        icon: icons["plus"]
                    });
                    for ( var i=0 ; i<n ; i++ ) 
                        markersMap[k].markers[i].setVisible(false);
                    
                    centerMark.setMap(map);
                    google.maps.event.addListener(centerMark, 'click', function() {
                        var markersMap = latlng2markers;
                        var k = this.getPosition().toString();
                        var visibility = markersMap[k].markers[0].getVisible();
                        if (visibility == false ) {
                            splitMarkersOnCircle(markersMap[k].markers,
                            markersMap[k].connectors,
                            this.getPosition(),
                            map
                        );
                            this.setIcon(icons["minus"]);
                        }
                        else {
                            var n = markersMap[k].markers.length;
                            for ( var i=0 ; i<n ; i++ ) {
                                markersMap[k].markers[i].setVisible(false);
                                markersMap[k].connectors[i].setMap(null);
                            }
                            markersMap[k].connectors = [];
                            this.setIcon(icons["plus"]);
                        }
                    });
                }
            }
    }
    
    function splitMarkersOnCircle(markers, connectors, center, map) 
    {
            var z = map.getZoom();
            var r = 64.0 / (z*Math.exp(z/2));
            var n = markers.length;
            var dtheta = 2.0 * Math.PI / n
            var theta = 0;
            
            for ( var i=0 ; i<n ; i++ ) 
            {
                var X = center.lat() + r*Math.cos(theta);
                var Y = center.lng() + r*Math.sin(theta);
                markers[i].setPosition(new google.maps.LatLng(X,Y));
                markers[i].setVisible(true);
                theta += dtheta;
                
                var line = new google.maps.Polyline({
                    path: [center,new google.maps.LatLng(X,Y)],
                    clickable: false,
                    strokeColor: "#0000ff",
                    strokeOpacity: 1,
                    strokeWeight: 2
                });
                
                line.setMap(map);
                connectors.push(line);
            }
    }
    
    function updateAverage(name) {
        
        $.getJSON('<portlet:resourceURL><portlet:param name="action" value="get-ratings"/></portlet:resourceURL>&iort_CE='+name,
        function(data) {                                               
            console.log(data.avg);
            $("#fake-stars-on").width(Math.round(parseFloat(data.avg)*20)); // 20 = 100(px)/5(stars)
            $("#fake-stars-cap").text(new Number(data.avg).toFixed(2) + " (" + data.cnt + ")");
        });                
    }
    
    // Create the Google Map JavaScript APIs V3
    function initialize(lat, lng, zoom) {
        console.log(lat);
        console.log(lng);
        console.log(zoom);
        
        var myOptions = {
            zoom: zoom,
            center: new google.maps.LatLng(lat, lng),
            mapTypeId: google.maps.MapTypeId.ROADMAP
        }
        
        var map = new google.maps.Map(document.getElementById('map_canvas'), myOptions);  
        var image = new google.maps.MarkerImage('<%= renderRequest.getContextPath() %>/images/ce-run.png');                
        
        var strVar="";
        strVar += "<table>";
        strVar += "<tr>";
        strVar += "<td>";
        strVar += "Vote the resource availability";
        strVar += "<\/td>";
        strVar += "<\/tr>";
        strVar += "<tr><td>\&nbsp;\&nbsp;";
        strVar += "<\/td><\/tr>";
        
        strVar += "<tr>";
        strVar += "<td>";
        strVar += "Rating: <span id=\"stars-cap\"><\/span>";
        strVar += "<div id=\"stars-wrapper2\">";
        strVar += "<select name=\"selrate\">";
        strVar += "<option value=\"1\">Very poor<\/option>";
        strVar += "<option value=\"2\">Not that bad<\/option>";
        strVar += "<option value=\"3\" selected=\"selected\">Average<\/option>";
        strVar += "<option value=\"4\">Good<\/option>";
        strVar += "<option value=\"5\">Perfect<\/option>";
        strVar += "<\/select>";
        strVar += "<\/div>";
        strVar += "<\/td>";
        strVar += "<\/tr>";

        strVar += "<tr>";        
        strVar += "<td>";
        strVar += "Average: <span id=\"fake-stars-cap\"><\/span>";
        strVar += "";
        strVar += "<div id=\"fake-stars-off\" class=\"stars-off\" style=\"width:100px\">";
        strVar += "<div id=\"fake-stars-on\" class=\"stars-on\"><\/div>";
        strVar += "";
        strVar += "<\/div>";
        strVar += "<\/td>";
        strVar += "<\/tr>";
        strVar += "<\/table>";
    
        var data = <%= GPS_table %>;
        var queues = <%= GPS_queue %>;
        
        var infowindow = new google.maps.InfoWindow();
        google.maps.event.addListener(infowindow, 'closeclick', function() {
            this.setContent('');
        });
        
        var infowindowOpts = { 
            maxWidth: 200
        };
               
       infowindow.setOptions(infowindowOpts);       
        
        var markers = [];
        for( var key in data ){
                        
            var LatLong = new google.maps.LatLng(parseFloat(data[key]["LAT"]), 
                                                 parseFloat(data[key]["LNG"]));                    
            
            // Identify locations on the map
            var marker = new google.maps.Marker ({
                animation: google.maps.Animation.DROP,
                position: LatLong,
                icon: image,
                title : key
            });    
            
            // Add the market to the map
            marker.setMap(map);
  
            var latlngKey=marker.position.toString();
            if ( latlng2markers[latlngKey] == null )
                latlng2markers[latlngKey] = {markers:[], connectors:[]};
            
            latlng2markers[latlngKey].markers.push(marker);
            markers.push(marker);                         
        
            google.maps.event.addListener(marker, 'click', function() {
             
            var ce_hostname = this.title;
            
            google.maps.event.addListenerOnce(infowindow, 'domready', function() {
                                        
                    $("#stars-wrapper2").stars({
                        cancelShow: false, 
                        oneVoteOnly: true,
                        inputType: "select",
                        callback: function(ui, type, value)
                        { 
                            $.getJSON('<portlet:resourceURL><portlet:param name="action" value="set-ratings"/></portlet:resourceURL>' +
                                '&iort_CE=' + ce_hostname + 
                                '&vote=' + value);
                            
                            updateAverage(ce_hostname);                      
                        }
                    });
                    
                    updateAverage(ce_hostname);               
                });              
                                                
                infowindow.setContent('<h3>' + ce_hostname + '</h3><br/>' + strVar);
                infowindow.open(map, this);
                                           
                var CE_queue = (queues[ce_hostname]["QUEUE"]);
                $('#iort_CE').val(CE_queue);
                                
                marker.setMap(map);
            }); // function                             
        } // for
        
        hideMarkers(latlng2markers,map);
        var markerCluster = new MarkerClusterer(map, markers, {maxZoom: 3, gridSize: 20});
    }    
</script>

<script type="text/javascript">
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
    //console.log (NMAX);   

    $(document).ready(function() 
    {           
        var lat; var lng; var zoom;
        var found=0;                
        
        if (parseInt(NMAX)>1) { 
            console.log ("More than one infrastructure has been configured!");
            $("#error_infrastructure img:last-child").remove();
            $('#error_infrastructure').append("<img width='70' src='<%= renderRequest.getContextPath()%>/images/world.png' border='0'> More than one infrastructure has been configured!");
            lat=19;lng=14;zoom=2; found=1;
        } else if (EnabledInfrastructure=='lato') {
            console.log ("Start up: enabled the lato VO!");
            $('#lato_iort_ENABLEINFRASTRUCTURE').attr('checked','checked');
            /*$('#lato_enabled').show();
            $('#gridit_enabled').hide();
            $('#eumed_enabled').hide();
            $('#biomed_enabled').hide();      
            $('#error_infrastructure').hide();*/
            lat=37,57;lng=14,28;zoom=7;
            found=1;
        } 
        else if (EnabledInfrastructure=='gridit') {
            console.log ("Start up: enabled the gridit VO!");
            $('#gridit_iort_ENABLEINFRASTRUCTURE').attr('checked','checked');
            /*$('#gridit_enabled').show();
            $('#lato_enabled').hide();
            $('#eumed_enabled').hide();
            $('#biomed_enabled').hide();      
            $('#error_infrastructure').hide();*/
            lat=42;lng=12;zoom=5;
            found=1;
        }
        else if (EnabledInfrastructure=='eumed') {
            console.log ("Start up: enabled the eumed VO!");
            $('#eumed_iort_ENABLEINFRASTRUCTURE').attr('checked','checked');
            /*$('#eumed_enabled').show();
            $('#gridit_enabled').hide();
            $('#lato_enabled').hide();            
            $('#biomed_enabled').hide();
            $('#error_infrastructure').hide();*/
            lat=34;lng=20;zoom=4;
            found=1;
        } 
        else if (EnabledInfrastructure=='biomed') {
            console.log ("Start up: enabled the biomed VO!");
            $('#biomed_iort_ENABLEINFRASTRUCTURE').attr('checked','checked');
            /*$('#biomed_enabled').show();
            $('#lato_enabled').hide();
            $('#gridit_enabled').hide();
            $('#eumed_enabled').hide();
            $('#error_infrastructure').hide();*/
            lat=48;lng=16;zoom=4;
            found=1;
        } 
        
        if (found==0) { console.log ("None of the grid infrastructures have been configured!");
            $("#error_infrastructure img:last-child").remove();
            $('#error_infrastructure').append("<img width='35' src='<%= renderRequest.getContextPath()%>/images/Warning.png' border='0'> None of the available grid infrastructures have been configured!");
        }
        
        var accOpts = {
            change: function(e, ui) {                       
                $("<div style='width:650px; font-family: Tahoma,Verdana,sans-serif,Arial; font-size: 14px;'>").addClass("notify ui-corner-all").text(ui.newHeader.find("a").text() +
                    " was activated... ").appendTo("#error_message").fadeOut(2500, function(){ $(this).remove(); });               
                // Get the active option                
                var active = $("#accordion").accordion("option", "active");
                if (active==1) initialize(lat, lng, zoom);
            },
            autoHeight: false
        };
        
        // Create the accordions
        //$("#accordion").accordion({ autoHeight: false });
        $("#accordion").accordion(accOpts);
        
        // Create the sliders
        $( "#slider-iort-nmax" ).slider({
            orientation: "horizontal",
            range: "min",
            value: 0,
            min: 1,
            max: 100,
            slide: function( event, ui ) {
                $( "#iort_nmax" ).val( ui.value );
                $( "input[type=hidden][name='iort_nmax']").val( ui.value);
            }
        });
        $( "#iort_nmax" ).val( $( "#slider-iort-nmax" ).slider( "value" ) );
        
        $( "#slider-iort-maxcputime" ).slider({
            orientation: "horizontal",
            range: "min",
            value: 1440,
            min: 480,
            max: 2800,
            slide: function( event, ui ) {
                $( "#iort_maxcputime" ).val( ui.value );
                $( "input[type=hidden][name='iort_maxcputime']").val( ui.value);
            }
        });
        $( "#iort_maxcputime" ).val( $( "#slider-iort-maxcputime" ).slider( "value" ) );
          
        // Validate input form
        $('#commentForm').validate({
        rules: {
                iort_nmax: {
                    required: true,
                    min: 1,
                    max: 100                    
                }
            }
        });
        
        // Check file input size with jQuery (Max. 2.5MB)
        $('input[type=file][name=\'iort_file\']').bind('change', function() {
            if (this.files[0].size/1000 > 25600) {     
                // Remove the img and text (if any)
                $("#error_message img:last-child").remove();
                $("#error_message").empty();
                $('#error_message').append("<img width='35' src='<%= renderRequest.getContextPath()%>/images/Warning.png' border='0'> The user demo file must be less than 2.5MB");
                $("#error_message").css({"color":"red","font-size":"14px"});
                // Removing the input file
                $('input[type=\'file\'][name=\'iort_file\']').val('');
                return false;
            }           
        });                
        
        $("#commentForm").bind('submit', function() {        
                        
            var flag=true;
            // Remove the img and text error (if any)
            $("#error_message img:last-child").remove();
            $("#error_message").empty();
            
            // Check if the uploaded file is a .txt file.
            if ($('input:checked[type=\'radio\'][name=\'iort_demo\']').val() == "iort_ASCII")
            {
                var ext = ($('input[type=file][name=\'iort_file\']').val().split('.').pop().toLowerCase());                
                if($.inArray(ext, ['mac']) == -1) {                    
                    $('#error_message').append("<img width='35' src='<%= renderRequest.getContextPath()%>/images/Warning.png' border='0'> Invalid file format");
                    $("#error_message").css({"color":"red","font-size":"14px"});                   
                    return false;
                    flag=false;
                }                
            } else
                      
            // Check if the input settings are valid before to
            // display the warning message.
            if ( (($('input:checked[type=\'radio\'][name=\'iort_demo\']').val() == "iort_ASCII") &&
                 ($('input[type=file][name=iort_file]').val() == "")) ||
                
                 (($('input:checked[type=\'radio\'][name=\'iort_demo\']').val() == "iort_TEXTAREA") &&
                 $('#iort_textarea').val() == "") ||
                
                 (($('input:checked[type=\'radio\'][name=\'iort_demo\']').val() != "iort_ASCII") &&
                 ($('input:checked[type=\'radio\'][name=\'iort_demo\']').val() != "iort_TEXTAREA")) ) 
            {            
                // Display the warning message  
                $('#error_message').append("<img width='35' src='<%= renderRequest.getContextPath()%>/images/Warning.png' border='0'> You missed many settings! They have been highlighted below.");
                $("#error_message").css({"color":"red","font-size":"14px"});
                flag=false;
            }
            
            if (flag) {
                $("#error_message").css({"color":"red","font-size":"14px", "font-family": "Tahoma,Verdana,sans-serif,Arial"});                
                $('#error_message').append("<img width='30' src='<%= renderRequest.getContextPath()%>/images/button_ok.png' border='0'> Submission in progress...")(30000, function(){ $(this).remove(); });                                
            }            
        });
                   
        // Roller
        $('#iort_footer').rollchildren({
            delay_time         : 3000,
            loop               : true,
            pause_on_mouseover : true,
            roll_up_old_item   : true,
            speed              : 'slow'   
        });
        
        $("#stars-wrapper1").stars({
            cancelShow: false,
            captionEl: $("#stars-cap"),
            callback: function(ui, type, value)
            {
                $.getJSON("ratings.php", {rate: value}, function(json)
                {                                        
                    $("#fake-stars-on").width(Math.round( $("#fake-stars-off").width()/ui.options.items*parseFloat(json.avg) ));
                    $("#fake-stars-cap").text(json.avg + " (" + json.votes + ")");
                });
            }
        });                
    });

    function enable_IortDemo() {        
        //if ($('input:checked[type=\'radio\'][name=\'iort_demo\']',f).val() == "iort_ASCII") {
        if ($('input[name=iort_demo]:checked', "#commentForm").val() == "iort_ASCII") {
            // Enabling the uploading of the user ASCII file
            $('input[type=\'file\'][name=\'iort_file\']').removeAttr('disabled');
            // Disabling the specification of the iort text via textarea
            $('#iort_textarea').attr('disabled','disabled');
        } else {        
            // Disabling the uploading of the user file
            $('input[type=\'file\'][name=\'iort_file\']').attr('disabled','disabled');
            // Enabling the specification of the iort text via textarea
            $('#iort_textarea').val('');
            $('#iort_textarea').removeAttr('disabled');
        }     
    }        
</script>

<br/>
<form enctype="multipart/form-data" 
      id="commentForm" 
      action="<portlet:actionURL><portlet:param name="ActionEvent" 
      value="SUBMIT_IORT_PORTLET"/></portlet:actionURL>"      
      method="POST">

<fieldset>
<legend>Iort Input Form</legend>
<div style="margin-left:15px" id="error_message"></div>

<!-- Accordions -->
<div id="accordion" style="width:650px; font-family: Tahoma,Verdana,sans-serif,Arial; font-size: 14px;">
<h3><a href="#">
    <img width="32" src="<%=renderRequest.getContextPath()%>/images/glass_numbers_1.png" />
    <b>Display Settings</b>
    </a>
</h3>
<div> <!-- Inizio primo accordion -->
<p>The current IORT_THERAPY portlet has been configured for:</p>
<table id="results" border="0" width="600">
    
<!-- LATO -->
<tr></tr>
<tr>
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
               name="lato_iort_ENABLEINFRASTRUCTURE"
               size="55px;"
               checked="checked"
               class="textfield ui-widget ui-widget-content required"
               disabled="disabled"/> The LATO Infrastructure
        
        <img width="120" 
                 border="0"
                 align="absmiddle"
                 src="<%= renderRequest.getContextPath()%>/images/LATO2.png" 
                 title="LATO - Laboratorio di Tecnologie Oncologiche, HSR Giglio"/>            
        </c:when>
        </c:choose>
    </td>
</tr>

<!-- GRIDIT -->
<tr></tr>
<tr>
    <td>
        <c:forEach var="enabled" items="<%= iort_ENABLEINFRASTRUCTURE %>">
            <c:if test="${enabled=='gridit'}">
                <c:set var="results_gridit" value="true"></c:set>
            </c:if>
        </c:forEach>

        <c:choose>
        <c:when test="${results_gridit=='true'}">
        <input type="checkbox" 
               id="gridit_iort_ENABLEINFRASTRUCTURE"
               name="gridit_iort_ENABLEINFRASTRUCTURE"
               size="55px;"
               checked="checked"
               class="textfield ui-widget ui-widget-content required"
               disabled="disabled"/> The Italian Grid Infrastructure
        
        <!--img width="20"
             id="gridit_enabled"
             style="display:none"
             src="<%= renderRequest.getContextPath()%>/images/button_ok.png" 
             border="0"/-->
        </c:when>
        </c:choose>
    </td>
</tr>

<!-- EUMED -->
<tr></tr>
<tr>
    <td>
        <c:forEach var="enabled" items="<%= iort_ENABLEINFRASTRUCTURE %>">
            <c:if test="${enabled=='eumed'}">
                <c:set var="results_eumed" value="true"></c:set>
            </c:if>
        </c:forEach>

        <c:choose>
        <c:when test="${results_eumed=='true'}">
        <input type="checked"
               id="eumed_iort_ENABLEINFRASTRUCTURE"
               name="eumed_iort_ENABLEINFRASTRUCTURE"
               size="55px;"
               checked="checked"
               class="textfield ui-widget ui-widget-content required"
               disabled="disabled" /> The Mediterranean Grid Infrastructure
        
        <!--img width="20"
             id="eumed_enabled"
             style="display:none"
             src="<%= renderRequest.getContextPath()%>/images/button_ok.png" 
             border="0"/-->
        </c:when>
        </c:choose>
    </td>
</tr>

<!-- BIOMED -->
<tr></tr>
<tr>
    <td>
        <c:forEach var="enabled" items="<%= iort_ENABLEINFRASTRUCTURE %>">
            <c:if test="${enabled=='biomed'}">
                <c:set var="results_biomed" value="true"></c:set>
            </c:if>
        </c:forEach>

        <c:choose>
        <c:when test="${results_biomed=='true'}">
        <input type="checkbox"
               id="biomed_iort_ENABLEINFRASTRUCTURE"
               name="biomed_iort_ENABLEINFRASTRUCTURE"
               size="55px;"
               checked="checked"
               class="textfield ui-widget ui-widget-content required"
               disabled="disabled" /> The BIOMED Grid Infrastructure
        
        <!--img width="20"
             id="biomed_enabled" 
             style="display:none" 
             src="<%= renderRequest.getContextPath()%>/images/button_ok.png" 
             border="0"/-->
        </c:when>
        </c:choose>
    </td>
</tr>
</table>
<br/>
<div style="margin-left:15px" 
     id="error_infrastructure" 
     style="width:690px; font-family: Tahoma,Verdana,sans-serif,Arial; font-size: 14px; display:none;">    
</div>
<br/>

<p align="center">
<img width="120" src="<%=renderRequest.getContextPath()%>/images/separatore.gif"/>
</p>

<p align="justify">
Instructions for users:<br/>
~ This portlet aims to address typical needs related to the IntraOperative Radio-Therapy (IORT) technique.
  This technique delivers a single dose of radiation directly to the tumor bed, or to the exposed tumor, during surgery.<br/>
  <img width="20" src="<%=renderRequest.getContextPath()%>/images/help.png" title="Read the Help"/>
   For further details, please click
   <a href="<portlet:renderURL portletMode='HELP'><portlet:param name='action' value='./help.jsp' />
            </portlet:renderURL>" >here</a>
   <br/><br/>
</p>
  
The portlet takes as input:<br/>
~ a GEANT4 macro file (.mac);<br/>
~ the CPU Time needed by the simulations (optional);<br/>
~ the number of simulations to be executed ([0, 100]).<br/><br/>

Each run will produce:<br/>
~ .std.txt: the standard output file;<br/>
~ .std.err: the standard error file;<br/>
~ results.tar.gz: containing the results of the generic Monte Carlo simulation.<br/><br/>

For further information, please refer to the output.README file produced during the run.<br/><br/>

<p>If you need to change some preferences, please contact the
<a href="mailto:credentials-admin@ct.infn.it?Subject=Request for Technical Support [<%=gateway%> Science Gateway]&Body=Describe Your Problems&CC=sg-licence@ct.infn.it"> administrator</a>
</p>

<liferay-ui:ratings
    className="<%= it.infn.ct.iort.Iort.class.getName()%>"
    classPK="<%= request.getAttribute(WebKeys.RENDER_PORTLET).hashCode()%>" />

<!--div id="pageNavPosition"></div-->
</div> <!-- Fine Primo Accordion -->

<h3><a href="#">
    <img width="32" src="<%=renderRequest.getContextPath()%>/images/glass_numbers_2.png" />
    <b>Worldwide Software Distribution</b>
    </a>
</h3>           
<div> <!-- Inizio Terzo accordion -->
    <p align="justify">
    See with the Google Map API where the software has been successfully installed.
    Select the GPS location of the grid resource where you want run your demo
    <u>OR, BETTER,</u> let the Science Gateway to choose the best one for you!<br/>
    This option is <u>NOT SUPPORTED</u> if more than one infrastructure is enabled!
    </p>

    <table border="0">
        <tr>
            <td><legend>Legend</legend></td>
            <td>&nbsp;<img src="<%=renderRequest.getContextPath()%>/images/plus_new.png"/></td>
            <td>&nbsp;Split close sites&nbsp;</td>
        
            <td><img src="<%=renderRequest.getContextPath()%>/images/minus_new.png"/></td>
            <td>&nbsp;Unsplit close sites&nbsp;</td>
            
            <td><img src="<%=renderRequest.getContextPath()%>/images/ce-run.png"/></td>
            <td>&nbsp;Computing resource&nbsp;</td>
        </tr>    
        <tr><td>&nbsp;</td></tr>
    </table>

    <legend>
        <div id="map_canvas" style="width:570px; height:600px;"></div>
    </legend>

    <input type="hidden" 
           name="iort_CE" 
           id="iort_CE"
           size="25px;" 
           class="textfield ui-widget ui-widget-content"
           value=""/>                  
</div> <!-- Fine Secondo Accordion -->        

<h3><a href="#">
    <img width="32" src="<%=renderRequest.getContextPath()%>/images/glass_numbers_3.png" />
    <b>Specify your Input Settings</b>
    </a>
</h3>           
<div> <!-- Inizio Quarto accordion -->
<p>Please, paste the macro you want to run in the textarea below, <u>OR</u> upload it in an ASCII file</p>
<table border="0">
    <tr>
        
        <td width="160">
        <img width="30" 
             align="absmiddle"
             src="<%= renderRequest.getContextPath()%>/images/question.png" 
             border="0" title="Upload your GEANT4 macro (ASCII file, Max 2,5MB) "/>

        <input type="radio" 
               name="iort_demo"
               id="iort_demo"
               value="iort_ASCII"
               class="required"
               onchange="enable_IortDemo();"/>Upload macro<em>*</em>  
        </td>
        
        <td width="410">
        <input type="file"
               name="iort_file" 
               style="padding-left: 1px; border-style: solid; border-color: gray; border-width: 1px; padding-left: 1px;" 
               class="required" 
               disabled="disabled"/>
        </td>
    </tr>
    
    <tr>
        <td width="160">
        <img width="30" 
             align="absmiddle"
             src="<%= renderRequest.getContextPath()%>/images/question.png" 
             border="0" title="Insert your GEANT4 macro in the texta-area "/>
        
        <input type="radio" 
               name="iort_demo" 
               id="iort_demo"
               style="padding-left: 1px; border-style: solid; border-color: grey; border-width: 1px; padding-left: 1px;"
               value="iort_TEXTAREA" 
               class="required"
               onchange="enable_IortDemo();"/>Insert macro<em>*</em>
        </td>
        
        <td width="300">            
            <textarea id="iort_textarea" 
                      name="iort_textarea"
                      style="padding-left: 1px; border-style: solid; border-color: grey; border-width: 1px; padding-left: 1px;"
                      class="required"
                      disabled="disabled"
                      rows="5" cols="50">                
            </textarea>            
        </td>        
    </tr>                    
        
    <tr>
        <td width="160">
        <img width="30" 
             align="absmiddle"
             src="<%= renderRequest.getContextPath()%>/images/question.png" 
             border="0" title="Please, insert here a short description "/>
        
        <label for="iort_desc">Description</label>
        </td>
        
        <td width="410">
        <input type="text" 
               id="iort_desc"
               name="iort_desc"
               style="padding-left: 1px; border-style: solid; border-color: grey; border-width: 1px; padding-left: 1px;"
               value="Please, insert here a description for your job"
               size="51" />
        </td>           
    </tr>
        
    
    <tr>
        <td width="160">
        <img width="30" 
             align="absmiddle"
             src="<%= renderRequest.getContextPath()%>/images/question.png" 
             border="0" title="Enable email notification to the user"/>
                                       
        <c:set var="enabled_SMTP" value="<%= SMTP_HOST %>" />
        <c:set var="enabled_SENDER" value="<%= SENDER_MAIL %>" />
        <c:choose>
        <c:when test="${empty enabled_SMTP || empty enabled_SENDER}">        
        <input type="checkbox" 
               name="EnableNotification"
               disabled="disabled"
               value="yes" /> Notification        
        </c:when>
        <c:otherwise>
        <input type="checkbox" 
               name="EnableNotification"               
               value="yes" /> Notification
        </c:otherwise>
        </c:choose>
        </td>

        <td>
        <img width="70"
             id="EnableNotificationid"             
             src="<%= renderRequest.getContextPath()%>/images/mailing2.png" 
             border="0"/>
        </td>
        <td></td>
    </tr>
          
    <tr>
    <td width="160">
        <img width="30" 
             align="absmiddle"
             src="<%= renderRequest.getContextPath()%>/images/question.png" 
             border="0" title="Select the MAX CPU Time requested by each simulation" />

        <label for="iort_maxcputime">CPU Time</label>
    </td>

    <td width="410">
    <div align="absmiddle" id="slider-iort-maxcputime" style="width:390px; margin:15px;"></div>
        <input type="hidden" name="iort_maxcputime" value="1440"/>
            
        &nbsp;&nbsp;Max CPU Time per simulation (in min.): 
        <input type="text" 
               id="iort_maxcputime"
               value="1440"
               disabled="disabled"                  
               style="width:40px; border:0; background:#C9C9C9; color:blue; font-weight:bold; font-size:12;"
               class="textfield ui-widget ui-widget-content ui-state-focus"/>
   </td>
   </tr>
   
   <tr>
    <td width="160">
        <img width="30" 
             align="absmiddle"
             src="<%= renderRequest.getContextPath()%>/images/question.png" 
             border="0" title="Select the number of jobs to run. Range value [ 1, 100 ] " />

        <label for="iort_nmax">N<em>*</em></label>                        
    </td>

    <td width="410">
    <div align="absmiddle" id="slider-iort-nmax" style="width:390px; margin:15px;"></div>
        <input type="hidden" name="iort_nmax" value="1" class="required"/>
            
        &nbsp;&nbsp;Number of GEANT4 simulations to run: 
        <input type="text" 
               id="iort_nmax"
               value="1"
               disabled="disabled"                  
               style="width:30px; border:0; background:#C9C9C9; color:blue; font-weight:bold; font-size:12;"
               class="textfield ui-widget ui-widget-content ui-state-focus"/>
   </td>
   </tr>
      
   <tr>                    
   <td align="left">
        <input type="image" 
               src="<%= renderRequest.getContextPath()%>/images/start-icon.png"
               width="60"                   
               name="submit"
               id ="submit" 
               title="Run your Simulation!" />                    
   </td>
   </tr>
</table>    
</div>	<!-- Fine Terzo Accordion -->
</div> <!-- Fine Accordions -->
</fieldset>    
</form>                                                                         

<div id="iort_footer" style="width:690px; font-family: Tahoma,Verdana,sans-serif,Arial; font-size: 14px;">
    <div>IORT_THERAPY portlet ver. 1.2.3</div>
    <div>Istituto Nazionale di Fisica Nucleare (INFN) and Consorzio COMETA, Italy, Copyright © 2013</div>
    <div>All rights reserved</div>
    <div>This work has been partially supported by
        <a href="http://www.egi.eu/projects/egi-inspire/">
            <img width="35" 
                 border="0"
                 src="<%= renderRequest.getContextPath()%>/images/EGI_Logo_RGB_315x250px.png" 
                 title="EGI - The European Grid Infrastructure" />
        </a>
    </div>
</div>
                 
<!--script type="text/javascript">
    var pager = new Pager('results', 13); 
    pager.init(); 
    pager.showPageNav('pager', 'pageNavPosition'); 
    pager.showPage(1);
</script-->
