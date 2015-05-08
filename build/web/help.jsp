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
<%@ page import="javax.portlet.*"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<portlet:defineObjects/>

<script type="text/javascript">
    $(document).ready(function() {
              
    $('.slideshow').cycle({
	fx: 'fade' // choose your transition type (fade, scrollUp, shuffle, etc)
    });
    
    // Roller
    $('#iort_footer').rollchildren({
        delay_time         : 3000,
        loop               : true,
        pause_on_mouseover : true,
        roll_up_old_item   : true,
        speed              : 'slow'   
    });
    
    //var $tumblelog = $('#tumblelog');  
    $('#tumblelog').imagesLoaded( function() {
      $tumblelog.masonry({
        columnWidth: 440
      });
    });
});
</script>
                    
<br/>

<fieldset>
<legend>About the project</legend>

<section id="content">

<div id="tumblelog" class="clearfix">
    
  <div class="story col3">
  <table border="0">
  <tr>
  <td>                
    
    <img 
        align="center" width="230" height="120" 
        src="<%= renderRequest.getContextPath()%>/images/geant4.gif"
        border="0"/>
    </a>
  </td>
  <td> &nbsp;&nbsp; </td>
  
  <td>
  <p style="text-align:justify; position: relative;">
  <a href="http://geant4advancedexampleswg.wikispaces.com/iort_therapy">
      <i>iort_therapy</i></a> is an application published in the official 
  <a href="http://geant4.cern.ch/support/ReleaseNotes4.9.5.html">Geant4 9.5</a>
  version release. It is specifically developed to address typical needs related 
  to the IntraOperative Radio-Therapy (IORT) technique. This technique delivers 
  a single dose of radiation directly to the tumor bed, or to the exposed tumor, 
  during surgery.
  <br/><br/>
  </p>
  </td>
  </tr>
  
  <tr>
  <td colspan="3">
  <p style="text-align:justify; position: relative;">
  The idea of <i>iort_therapy</i> is to provide a useful tool for Users interested 
  to radiation dosimetry, dose planning and radio-protection studies in IORT. 
  In fact, the application allows to reconstruct dose distribution curves in water 
  or other materials, to plan dose distribution in the tumor treatment region with 
  different clinical set-up, and to optimize radio-protection of normal patient 
  tissues simulating a composite metallic shielding disc.
  <br/><br/>
  <i>iort_therapy</i> reproduces the collimator beam line system of a typical medical 
  mobile linac, the phantom, the detector and the composite metallic shielding disc.
  <br/><br/>
  Via external macro commands it is possible to change the physic models, the 
  collimator beam line, the phantom, the detector and shielding disc geometries, 
  the visualization, the beam particle characteristics, and to activate the 
  Graphical Users Interface (QT libraries are requested).
  <br/><br/>
  A complete documentation of iort_therapy can be found in the related README file 
  inside the official Geant4 9.5 version release.
  <br/><br/>
  <p>&mdash; <i>iort_therapy</i> is currently used by the G.Russo team for cancer 
  breast clinical and research activities carried out at <a href="http://www.polooncologicocefalu.it/">
  LAboratorio di Tecnologie Oncologiche (LATO) - Fondazione Istituto San Raffaele 
  G.Giglio Hospital</a> (Cefalù, Italy)
  </p>  
  </td>  
  </tr>
  </table>  
  </div>

  <!--div class="story col3">
  
    <table border="0">
    <tr>
    <td>                
        <a href="http://player.vimeo.com/video/8878045">
                <img align="center" width="280" height="210"
                     src="<%= renderRequest.getContextPath()%>/images/sonification1_video.jpg" 
                     border="0"/>
            </a>
    </td>
    <td> &nbsp;&nbsp; </td>
    <td>        
    <p style="text-align:justify;">
        <font style="position: relative;">
        For the first time ever, a modern dance company has performed to music generated from seismic data, recorded from four 
        volcanoes across three continents. This unique event was facilitated by DANTE, the provider of high speed research and 
        education networks, the two distributed computing projects, Enabling Grids for E-sciencE (<a href="http://www.eu-egee.org/">EGEE</a>) and E-science grid 
        facility for Europe and Latin America (<a href="http://www.eu-eela.eu/">EELA</a>), as well as <a href="http://citydance.net/">CityDance Ensemble</a>, a prestigious company based in Washington, DC.<br/>        
        </font>        
    </p>    
    </td>
    </tr>
    <tr>
        <td colspan="3">
        <font style="position: relative;">
        The dance, titled The Mountain, and choreographed by <a hfre="http://www.jasonignacio.com"> Jason Garcia Ignacio</a>, was part of CityDance Ensemble’s Carbon, a 
        work-in-progress about climate change. Originally presented in sold-out performances on 14 and 15 of March 2009 at the Music 
        Centre, Maryland, USA.
        <br/><br/>
        <a href="http://www.volcanodance.org">
        <img src="http://www.volcanodance.org/Site2/Photos_files/TheMountainBanner1.jpg"
            title=""
            style="padding: 5px; border: 1px solid #ccc; background-color: #eee;"
            heigh="400" 
            width="590"/>
        </a>
        </td>
    </tr>    
    </table>    
  </div-->                 
             
  <!--div class="story col3" style="font-family: Tahoma,Verdana,sans-serif,Arial; font-size: 14px;">
      <h3>About Data Audification</h3>      
        <p style="text-align:justify;">
        Data audification is the representation of data by sound signals; it can 
        be considered as the acoustic counterpart of data graphic visualization, 
        a mathematical mapping of information from data sets to sounds. In the 
        past twenty years the word audification has acquired a new meaning in 
        the world of computer music, computer science, and auditory display 
        application development. Data audification is currently used in several 
        fields, for different purposes: science and engineering, education and
        training, in most of the cases to provide a quick and effective data 
        analysis and interpretation tool.<br/>        
        Although most data analysis techniques are exclusively visual in nature 
        (i.e. are based on the possibility of looking at graphical representations), 
        data presentation and exploration systems could benefit greatly from the 
        addition of sonification capabilities. [..] <br/>
        For further information about the sonification process, please download the <a href="http://bit.ly/uaDPAJ">White paper (PDF)</a>
        </p>
 </div-->
                          
  <!--div class="story col3" style="font-family: Tahoma,Verdana,sans-serif,Arial; font-size: 13px;">
  <h2>MIDI Sonification of Mt. Etna Volcano Seismograms</h2>           
  <br/>
  <object height="81" width="100%" style="padding: 5px; border: 1px solid #ccc; background-color: #eee;">
  <param name="wmode" value="transparent">
  <param name="movie" value="http://player.soundcloud.com/player.swf?url=http%3A%2F%2Fapi.soundcloud.com%2Ftracks%2F29302665&amp;g=1&amp;show_comments=false&amp;auto_play=false&amp;color=000000">             
  </param>
  <embed height="81" src="http://player.soundcloud.com/player.swf?url=http%3A%2F%2Fapi.soundcloud.com%2Ftracks%2F29302665&amp;g=1&amp;show_comments=false&amp;auto_play=false&amp;color=000000" type="application/x-shockwave-flash" width="100%"> 
  </embed> 
  </object>                
  </div-->
    
  <!--div class="story col3" style="font-family: Tahoma,Verdana,sans-serif,Arial; font-size: 14px;">
  <table border="0">
  <tr>
  <td-->              
    <!--iframe src="http://www.youtube.com/v/8HjRZ9JWnR0?version=3&feature=player_detailpage" 
            style="padding: 5px; border: 1px solid #ccc; background-color: #eee;"
            width="280" height="210" frameborder="0" 
            webkitAllowFullScreen mozallowfullscreen allowFullScreen>        
    </iframe-->            
    <!--a href="http://www.youtube.com/v/8HjRZ9JWnR0">
        <img align="center" width="280" height="210"
             src="<%= renderRequest.getContextPath()%>/images/sonification2_video.jpg" 
             border="0"/>
    </a>   
  </td-->
  <!--td> &nbsp;&nbsp; </td>
  <td>
      <p style="font-family: Tahoma,Verdana,sans-serif,Arial; font-size: 14px; text-align:justify;">
          The Sonification application won the best poster and demo awards at the 
          <a href="http://cf2012.egi.eu/">EGI Community Forum, Munich, 2012</a>
          <br/><br/>The demo was about how we can create some music from text 
          (or tweets) using the <a href="http://www.egi.eu/">EGI Infrastructure</a>
      </p>
  </td>
  </tr>
  </table>
  </div-->
            
  <!--div class="story col3" style="font-family: Tahoma,Verdana,sans-serif,Arial; font-size: 13px;">
  <h2>3D Image Rendering as Data Representation</h2>
  <table border="0">
  <tr>
    <td> 
    <a href="<%= renderRequest.getContextPath()%>/images/MessageToSpheres1.png">
    <img src="<%= renderRequest.getContextPath()%>/images/MessageToSpheres1.png"
         title="3D rendering of the POVRay source file"
         style="padding: 5px; border: 1px solid #ccc; background-color: #eee;"
         heigh="170" 
         width="137"/>
    </a>
    </td>
    <td>
    <a href="<%= renderRequest.getContextPath()%>/images/MessageToSpheres5.png">
    <img src="<%= renderRequest.getContextPath()%>/images/MessageToSpheres5.png"
         title="3D rendering of the POVRay source file"
         style="padding: 5px; border: 1px solid #ccc; background-color: #eee;"
         heigh="170" 
         width="137"/>
    </a>         
    </td>
    <td>
        <a href="<%= renderRequest.getContextPath()%>/images/MessageToSpheres3.png">
        <img src="<%= renderRequest.getContextPath()%>/images/MessageToSpheres3.png"
         title="3D rendering of the POVRay source file"
         style="padding: 5px; border: 1px solid #ccc; background-color: #eee;"
         heigh="170" 
         width="137"/>
        </a>
    </td>
    <td>
        <a href="<%= renderRequest.getContextPath()%>/images/MessageToSpheres4.png">
        <img src="<%= renderRequest.getContextPath()%>/images/MessageToSpheres4.png"
         title="3D rendering of the POVRay source file"
         style="padding: 5px; border: 1px solid #ccc; background-color: #eee;"
         heigh="170" 
         width="137"/>
        </a>
    </td>
  </tr>
  </table>
  </div-->

  <div class="story col3" style="font-family: Tahoma,Verdana,sans-serif,Arial; font-size: 13px;">      
      <h2><a href="mailto:carlo.casarino@polooncologicocefalu.it">
      <img width="100" src="<%= renderRequest.getContextPath()%>/images/contact6.jpg" border="0" title="Get in touch with the project"/></a>Contacts</h2>
      <h3><u>Main Authors</u></h3><p>
      Giorgio RUSSO <sup>(a,b)</sup>, 
      <a href="mailto:carlo.casarino@polooncologicocefalu.it">Carlo CASARINO</a> <sup>* (c)</sup>, 
      Giuliana Carmela CANDIANO <sup>(c)</sup>,<br/>
      Giuseppe Antonio Pablo CIRRONE <sup>(d)</sup>,
      Francesco ROMANO <sup>(d)</sup>
      </p>
      
      <h3><u>Contributor Author</u></h3>
      <p>Susanna GUATELLI <sup>(e)</sup></p>
      
      <sup>(a)</sup> Fondazione Istituto San Raffaele G.Giglio, Contrada Pietra Pollastra Pisciotto, 90015 Cefalù (Palermo), Italy<br/><br/>
      <sup>(b)</sup> Institute for molecular bio-imaging and physiology (IBFM), National Research Council (CNR), Via F.lli Cervi 93, 20090 Segrate (Milano), Italy<br/><br/>
      <sup>(c)</sup> LAboratorio di Tecnologie Oncologiche (LATO), Contrada Pietra Pollastra Pisciotto, 90015 Cefalù (Palermo), Italy<br/><br/>
      <sup>(d)</sup> Laboratori Nazionali del Sud (LNS), Istituto Nazionale di Fisica Nucleare (INFN), via S.Sofia 62, 95123 Catania, Italy<br/><br/>
      <sup>(e)</sup> Centre for Medical Radiation Physics, School of Engineering Physics, University of Wollongong, NSW 2522 Australia<br/><br/>
      <sup>(*)</sup> corresponding author
      </p>
  </div>               
    
  <div class="story col3" style="font-family: Tahoma,Verdana,sans-serif,Arial; font-size: 13px;">
        <h2>Sponsors</h2>
        <table border="0">                                                
            <tr>
            <td>
            <p align="center">
            <a href="http://www.lns.infn.it/">
            <img align="center" width="100" heigth="100"
                 src="<%= renderRequest.getContextPath()%>/images/INFN-Logo.png" 
                 border="0" title="Istituto Nazionale di Fisica Nucleare - Laboratori Nazionali del Sud"/>
            </a>
            </p>
            </td>
            
            <td>
            <p align="center">
            <a href="http://www.polooncologicocefalu.it/">
            <img align="center" width="220" heigth="170"
                 src="<%= renderRequest.getContextPath()%>/images/LATO2.png" 
                 border="0" title="LATO - Laboratorio di Tecnologie Oncologiche, HSR GIGLIO" />
            </a>
            </p>
            </td>
            
            <td>
            <p align="center">
            <a href="http://www.hsrgiglio.it/">
            <img align="center" width="100"                      
                 src="<%= renderRequest.getContextPath()%>/images/logoSRGiglio.gif" 
                 border="0" title="Fondazione Istituto San Raffaele - G. Giglio di Cefalu'" />
            </a>
            </p>
            </td> 
            </tr>
            
            <tr>
            <td colspan="3">
            <p align="center">
            <a href="http://www.ibfm.cnr.it/">
            <img align="center" width="600" heigth="330"
                 src="<%= renderRequest.getContextPath()%>/images/ibfm_home_r1_c1.gif" 
                 border="0" title="Institute for molecular bio-imaging and physiology (IBMF), National Research Council (CNR)" />
            </a>
            </p>
            </td>
            </tr>
            
            <tr>
                <td colspan="2">
                <p align="center">
                <a href="http://www.polooncologicocefalu.it/">
                <img align="center" width="290" heigth="150"
                     src="<%= renderRequest.getContextPath()%>/images/LogoPoloOncologico.png" 
                     border="0" title="Polo Oncologico Cefalu'"/>
                </a>
                </p>
                </td> 
                
                <td>
                <p align="center">
                <a href="http://www.uow.edu.au/">
                <img align="center" width="220" heigth="150"
                     src="<%= renderRequest.getContextPath()%>/images/img_logo_uow.png" 
                     border="0" title="Centre for Medical Radiation Physics (CMRP)" />
                </a>
                </p>
                </td>                                
            </tr>
            
            <tr>                
                <td>
                <p align="center">
                <a href="http://www.egi.eu/projects/egi-inspire/">
                <img align="center" width="100" 
                     src="<%= renderRequest.getContextPath()%>/images/EGI_Logo_RGB_315x250px.png" 
                 title="EGI - The European Grid Infrastructure" />
                </a>   
                </p>
                </td>
                
                <td>
                <p align="center">
                <a href="http://www.italiangrid.it/">
                <img align="center" width="110" 
                     src="<%= renderRequest.getContextPath()%>/images/garland_logo.png"  
                     border="0" title="IGI - The Italian Grid Initiatives" />
                </a>   
                </p>
                </td>
                
                <td>
                <p align="center">
                <a href="http://www.consorzio-cometa.it/">
                <img align="center" width="120"
                     src="<%= renderRequest.getContextPath()%>/images/cometa.png" 
                     border="0" title="The Consorzio COMETA" />
                </a>
                </p>
                </td>                                
                
            </tr>            
        </table>   
  </div>
</div>
</section>
</fieldset>
           
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