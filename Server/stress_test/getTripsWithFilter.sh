#!/bin/bash

# test for /trip/getTripsWithFilter

#Comandi di default per modificare colore shell
red=`tput setaf 1`
yellow=`tput setaf 3`
green=`tput setaf 2`
reset=`tput sgr0`

echo -e ""

#---------------------------------------------------------------
#Test One

echo -e "Test One: localhost:8095/trip/getTripsWithFilter \n"
 
echo -e "${yellow}Result:"

curl http://localhost:8095/trip/getTripsWithFilter

echo -e "-------------------------------------\n"

#---------------------------------------------------------------
#Test Two

echo -e "Test Two: localhost:8095/trip/getTripsWithFilter \n"
 
echo -e "${yellow}Result:"

curl http://localhost:8095/trip/getTripsWithFilter?maxBudget=200

echo -e "-------------------------------------\n"

#---------------------------------------------------------------
#Test Three

echo -e "Test Three: localhost:8095/trip/getTripsWithFilter \n"
 
echo -e "${yellow}Result:"

curl http://localhost:8095/trip/getTripsWithFilter?maxBudget=200&tag=cultura

echo -e "-------------------------------------\n"

#---------------------------------------------------------------
#Test Four

echo -e "Test Four: localhost:8095/trip/getTripsWithFilter \n"
 
echo -e "${yellow}Result:"

curl http://localhost:8095/trip/getTripsWithFilter?maxBudget=200&tag=cultura&vehicle=auto

echo -e "-------------------------------------\n"

#---------------------------------------------------------------
#Test Five

echo -e "Test Five: localhost:8095/trip/getTripsWithFilter \n"
 
echo -e "${yellow}Result:"

curl http://localhost:8095/trip/getTripsWithFilter?minBudget=50

echo -e "-------------------------------------\n"

#---------------------------------------------------------------
#Test Six

echo -e "Test Six: localhost:8095/trip/getTripsWithFilter \n"
 
echo -e "${yellow}Result:"

curl http://localhost:8095/trip/getTripsWithFilter?minBudget=50&vehicle=treno

echo -e "-------------------------------------\n"

#---------------------------------------------------------------
#Test Seven

echo -e "Test Seven: localhost:8095/trip/getTripsWithFilter \n"
 
echo -e "${yellow}Result:"

curl http://localhost:8095/trip/getTripsWithFilter?minBudget=50&vehicle=treno&minPartecipant=20

echo -e "-------------------------------------\n"

#---------------------------------------------------------------
#Test Eight

echo -e "Test Eight: localhost:8095/trip/getTripsWithFilter \n"
 
echo -e "${yellow}Result:"

curl http://localhost:8095/trip/getTripsWithFilter?minBudget=50&vehicle=treno&minPartecipant=20&destination=manila

echo -e "-------------------------------------\n"

#---------------------------------------------------------------
#Test Nine

echo -e "Test Nine: localhost:8095/trip/getTripsWithFilter \n"
 
echo -e "${yellow}Result:"

curl http://localhost:8095/trip/getTripsWithFilter?name

echo -e "-------------------------------------\n"

#---------------------------------------------------------------
#Test Ten

echo -e "Test Ten: localhost:8095/trip/getTripsWithFilter \n"
 
echo -e "${yellow}Result:"

curl http://localhost:8095/trip/getTripsWithFilter?minBudget=300

echo -e " ${reset}"
echo -e "-------------------------------------\n"

#---------------------------------------------------------------

