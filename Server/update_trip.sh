#!/bin/bash

#Use this script to populate database of trips for testing

#Comandi di default per modificare colore shell
red=`tput setaf 1`
yellow=`tput setaf 3`
green=`tput setaf 2`
reset=`tput sgr0`

echo -e ""

#---------------------------------------------------------------

#Starting to populate database of trips

echo -e "Starting to update a trip"

echo -e "${green}"
                                                            
curl -H "Content-Type: application/json" -X POST -d '{"_id":"5c4f7592905848234cb02ac3", "name":"brazil, i want you!"}' http://localhost:8095/trip/updateTrip

echo -e ""
echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{"_id":"5c4f7592905848234cb02ac6", "name":"pakistan, seriouslyyyy?", "budget":"2344"}' http://localhost:8095/trip/updateTrip

echo -e ""

echo -e " ${reset}"

echo -e "Script end"





 
