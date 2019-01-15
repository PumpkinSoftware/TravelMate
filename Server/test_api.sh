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

#Test four: new trip in trip_api.js 

echo -e "Test Three: localhost:8095/trip/new_trip \n"
 
echo -e "${yellow}Result:"

curl -H "Content-Type: application/json" -X POST -d '{"name":"road to milan","description":"best travel to milan","departure":"rome","destination":"milan","budget":"123.50","startDate":"12/12/2018","endDate":"01/01/2019"}' http://localhost:8095/trip/new_trip

echo -e " ${reset}"
echo -e "${green}Expected:"
echo -e "Trip road to milan created! ${reset}"
echo -e "-------------------------------------\n"

#---------------------------------------------------------------



