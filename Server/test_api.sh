#!/bin/bash

#Comandi di default per modificare colore shell
red=`tput setaf 1`
yellow=`tput setaf 3`
green=`tput setaf 2`
reset=`tput sgr0`

echo -e ""

#---------------------------------------------------------------

#Test One: root app_router.js 

echo -e "Test One: localhost:8095 \n"
 
echo -e "${yellow}Result:"

curl http://localhost:8095

echo -e " ${reset}"
echo -e "${green}Expected:"
echo -e "Mi trovo in app_router.js ${reset}"
echo -e "-------------------------------------\n"

#---------------------------------------------------------------

#Test Two: root user_api.js 

echo -e "Test Two: localhost:8095/user \n"
 
echo -e "${yellow}Result:"

curl http://localhost:8095/user

echo -e " ${reset}"
echo -e "${green}Expected:"
echo -e "Mi trovo in user_api.js ${reset}"
echo -e "-------------------------------------\n"

#---------------------------------------------------------------

#Test Three: root trip_api.js 

echo -e "Test Three: localhost:8095/trip \n"
 
echo -e "${yellow}Result:"

curl http://localhost:8095/trip

echo -e " ${reset}"
echo -e "${green}Expected:"
echo -e "Mi trovo in trip_api.js ${reset}"
echo -e "-------------------------------------\n"

#---------------------------------------------------------------

