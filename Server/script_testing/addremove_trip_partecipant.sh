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
                                                           
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c577f51255a911d5292319f", "userId":"xxxxxxxxxx"}' http://localhost:8095/trip/addParticipant

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c561612c22be2586a57ee75", "userId":"xxxxxxxxxx"}' http://localhost:8095/trip/addParticipant

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c577f51255a911d5292319f", "userId":"xxxxxxxxxx"}' http://localhost:8095/trip/removeParticipant

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c561612c22be2586a57ee75", "userId":"xxxxxxxxxx"}' http://localhost:8095/trip/removeParticipant

echo -e ""


echo -e " ${reset}"

echo -e "Script end"







 
