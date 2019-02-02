#!/bin/bash

#Use this script to add/remove partecipant in a trip

#Comandi di default per modificare colore shell
red=`tput setaf 1`
yellow=`tput setaf 3`
green=`tput setaf 2`
reset=`tput sgr0`

echo -e ""

#---------------------------------------------------------------

#Scripts

echo -e "Starting mio_prova.sh"

echo -e "${green}"
                                                           
curl -H "Content-Type: application/json" -X POST -d '{"trip_id":"5c54e1140b884752a7b8af6a", "email":"example@gmail.com"}' http://localhost:8095/trip/addParticipant

curl -H "Content-Type: application/json" -X POST -d '{"trip_id":"5c54e1140b884752a7b8af69", "email":"example@gmail.com"}' http://localhost:8095/trip/removeParticipant

echo -e ""

echo -e " ${reset}"

echo -e "Script end"







 
