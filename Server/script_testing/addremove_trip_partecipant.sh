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
                                                           
curl -H "Content-Type: application/json" -X POST -d '{"_id":"5c53429c05240714470f19ef", "email":"example@email.com"}' http://localhost:8095/trip/addPartecipant

echo -e ""

echo -e " ${reset}"

echo -e "Script end"







 
