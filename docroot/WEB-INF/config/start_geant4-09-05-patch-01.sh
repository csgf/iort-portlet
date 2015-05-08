#!/bin/bash

echo;echo "Running on "`hostname -f`

export VO_NAME=$(voms-proxy-info -vo)
export VO_VARNAME=$(echo $VO_NAME | sed s/"\."/"_"/g | sed s/"-"/"_"/g | awk '{ print toupper($1) }')
export VO_SWPATH_NAME="VO_"$VO_VARNAME"_SW_DIR"
export VO_SWPATH_CONTENT=$(echo $VO_SWPATH_NAME | awk '{ cmd=sprintf("echo $%s",$1); system(cmd); }')
export MACRO_FILE=`basename $1`

echo;echo "Multi infrastructure settings:"
echo "-------------------------------"
echo "VO_NAME           : "$VO_NAME
echo "VO_VARNAME        : "$VO_VARNAME
echo "VO_SWPATH_NAME    : "$VO_SWPATH_NAME
echo "VO_SWPATH_CONTENT : "$VO_SWPATH_CONTENT
echo "MACRO_FILE 	  : "$MACRO_FILE

echo;echo "[ SOURCING environment variables ] "
source ${VO_SWPATH_CONTENT}/GEANT4-9.5.1-patch-01/bin/iort-geant4.sh
env | grep -i geant

echo;echo "[ CHECHING Software ... ]"
tree -L 3  ${VO_SWPATH_CONTENT}/GEANT4-9.5.1-patch-01/

echo;echo "[ STARTING Iort_Therapy application with macro = "$MACRO_FILE " ]"
echo ${VO_SWPATH_CONTENT}/GEANT4-9.5.1-patch-01/iort_therapy $MACRO_FILE
${VO_SWPATH_CONTENT}/GEANT4-9.5.1-patch-01/iort_therapy $MACRO_FILE

echo;echo "[ CHECHING for results ... ]"
tree -L 3  $PWD/

if [ -f energyD ] ; then
tar zcf results.tar.gz *.rndm *.out energyD
else
tar zcf results.tar.gz *.rndm *.out
fi

cat <<EOF >> output.README
#
# README - IORT_THERAPY
#
# Giuseppe LA ROCCA, INFN Catania
# <mailto:giuseppe.larocca@ct.infn.it>
#

This application allows Science Gateway users to address typical needs related to the 
IntraOperative Radio-Therapy (IORT) technique based on GEANT4 software.

If the job has been successfully executed, the following files will be produced:
~ std.txt:	        the standard output file;
~ std.err:  	        the standard error file;
~ results.tar.gz:       a tarball containing the results of the simulation.

The list of files produced during the run are the following:
~ currentEvent.rndm;
~ currentRun.rndm;
~ Dose.out;
~ Energy_MeV.out.
EOF

