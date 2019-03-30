#!/bin/bash

#Comandi di default per modificare colore shell
red=`tput setaf 1`
yellow=`tput setaf 3`
green=`tput setaf 2`
reset=`tput sgr0`

#Pulizia trips
echo -e "Starting to clean collections of users and trips"

echo -e "${green}"
curl https://localhost:8095/trip/deleteAll
echo -e ""

#Pulizia users
curl https://localhost:8095/user/deleteAll
echo -e ""
echo -e " ${reset}"

echo -e "Script end"

#Caricamento delle occorrenze
bash simulator.sh
