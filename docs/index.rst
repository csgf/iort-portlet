*********************
IORT v1.2.2 Docs
*********************

============
About
============

.. _1: http://www.ro-journal.com/content/8/1/80

IntraOperative Electron Radiotherapy (IOERT) [1_] is a radiotherapy technique that delivers a single dose of radiation directly to the tumor bed, or to the exposed tumor, during surgery. The objective is to achieve a higher dose in the target volume while dose-limiting structures are surgically displaced. IOERT is used for limited-stage breast tumors treatment and also successfully for prostate, colon and sarcoma cancers. For this purpose, a new generation of mobile linear accelerators has been designed to deliver radiation therapy in the operating theater.

As in conventional radio-therapy techniques, the use of Monte Carlo simulations is mandatory to design the beam collimation system and to study radioprotection charactheristics as the radiation leakages. In the clinical activities the simulations can be used to commissioning of the linac and in the optimization of the therapeutic dose and patient radioprotection. 

============
Installation
============
To install this portlet the WAR file has to be deployed into the application server.

As soon as the portlet has been successfully deployed on the Science Gateway the administrator has to configure:

- the list of e-Infrastructures where the application can be executed;

- some additional application settings.

1.) To configure a generic e-Infrastructure, the following settings have to be provided:

**Enabled**: A true/false flag which enables or disable the generic e-Infrastructure;

**Infrastructure**: The acronym to reference the e-Infrastructure;

**VOName**: The VO for this e-Infrastructure;

**TopBDII**: The Top BDII for this e-Infrastructure;

**WMS Endpoint**: A list of WMS endpoint for this e-Infrastructure (max. 10);

**MyProxyServer**: The MyProxyServer for this e-Infrastructure;

**eTokenServer**: The eTokenServer for this e-Infrastructure;

**Port**: The eTokenServer port for this e-Infrastructure;

**Serial Number**: The MD5SUM of the robot certificate to be used for this e-Infrastructure;

In the following figure is shown how the portlet has been configured to run simulation on the Italian e-Infrastructure.

.. image:: images/IORT_settings.jpg
   :align: center

2.) To configure the application, the following settings have to be provided:

**AppID**: The ApplicationID as registered in the UserTracking MySQL database (GridOperations table);

**Software TAG**: The list of software tags requested by the application;

**SMTP Host**: The SMTP server used to send notification to users;

**Sender**: The FROM e-mail address to send notification messages about the jobs execution to users;

.. _GARR: https://sgw.garr.it/

In the figure below is shown how the application settings have been configured to run on the GARR_ Science Gateway.

.. image:: images/IORT_settings2.jpg
   :align: center

============
Usage
============

To run the Monte Carlo simulation the user has to:

- click on the *third* accordion of the portlet,

- upload the macro as ASCII file OR paste its content in the below textarea, and

- select the number of jobs to be executed as shown in the below figure:

.. image:: images/IORT_inputs.jpg
      :align: center

Each simulation will produce:

- *std.txt*: the standard output file;

- *std.err*: the standard error file;

- *.tar.gz*: containing the results of the Monte Carlo simulation.

A typical simulation produces, at the end, the following files:

.. code:: bash

        ]$ tree IortTherapySimulationStarted_646/
        IortTherapySimulationStarted_646/
        ├── AstraStk.err
        ├── AstraStk.out
        ├── bachfugue.wav (8.7M)
        ├── output.README
        └── samples.tar.gz (589M)

============
References
============

.. _10: http://documents.ct.infn.it/record/421/files/Proceedings_Workshop_Finale.pdf
.. _2: http://www.garr.it/eventiGARR/conf09/doc/SelectedPapers_Conf09.pdf
.. _3: https://books.google.it/books?id=fZdGAAAAQBAJ&printsec=frontcover&hl=it
.. _4: http://www.sciencedirect.com/science/article/pii/S187705091000044X

* Final workshop of Grid Projects "Pon Ricerca 2000-2006, Avviso 1575": *"ASTRA Project Achievements: The reconstructed Greek Epigonion with GILDA/ASTRA brings history to life. It takes archaeological findings of extinct musical instruments, and lets us play them again thanks to a virtual digital model running on the GRID.EUMEDGRID on GEANT2/EUMEDCONNECT"* – February 10-12, 2009 Catania, Italy [1_];

* Conferenza GARR: *"ASTRA Project: un ponte fra Arte/Musica e Scienza/Tecnologia - Conferenza GARR"* – September 2009, Napoli, Italy [2_];

* International Symposium on Grid Computing 2009: *"The ASTRA (Ancient instruments Sound/Timbre Reconstruction Application) Project brings history to life"* – March 2010, Taipei, Taiwan [3_];

* Proceedings of the International Conference on Computational Science, ICCS2010, doi:10.1016/j.procs.2010.04.043: *"Data sonification of volcano sesmograms and Sound/Timbre recountruction of ancient musical instruments with Grid infrastructures"* – May, 2010 Amsterdam, The Netherlands [4_];


============
Support
============
Please feel free to contact us any time if you have any questions or comments.

.. _INFN: http://www.ct.infn.it/
.. _INFN_LNS: http://www.lns.infn.it/
.. _DANTE: http://www.dante.net/
.. _MALAGA: http://www.uma.es/
.. _5: http://www.conservatoriocimarosa.org/

:Authors:

 `Carlo CASARINO <mailto:carlo.casarino@polooncologicocefalu.it>`_ - LAboratorio di Tecnologie Oncologiche (LATO), Cefalu' ([_]),
 
 `Giuliana Carmela CANDIANO <mailto:giuliana.candiano@polooncologicocefalu.it>`_ - LAboratorio di Tecnologie Oncologiche (LATO), Cefalu' ([_]),
  
 `Giuseppe Antonio Pablo CIRRONE <mailto:roberto.barbera@ct.infn.it>`_ - Italian National Institute of Nuclear Physics - LNS (),
 
 `Roberto BARBERA <mailto:roberto.barbera@ct.infn.it>`_ - Italian National Institute of Nuclear Physics (INFN_),
 
 `Susanna GUATELLI <mailto:susanna@uow.edu.au>`_ - Centre for Medical Radiation Physics, School of Engineering Physics, University of Wollongong, NSW 2522 Australia (),
 
 `Giuseppe LA ROCCA <mailto:giuseppe.larocca@ct.infn.it>`_ - Italian National Institute of Nuclear Physics (INFN_),
 
:Version: v1.2.2, 2015

:Date: June 1st, 2015 10:17
