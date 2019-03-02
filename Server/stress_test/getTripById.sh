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

echo -e "Test One: localhost:8095/trip/getTripsById \n"
 
echo -e "${yellow}Result:"

curl http://localhost:8095/trip/getTripById?id=5c79b80f4a8b77336e1a332a

echo -e "-------------------------------------\n"

#---------------------------------------------------------------
#Test Two

echo -e "Test Two: localhost:8095/trip/getTripById \n"
 
echo -e "${yellow}Result:"

curl http://localhost:8095/trip/getTripById?id=5c79b80e4a8b77336e1a3329

echo -e "-------------------------------------\n"

#---------------------------------------------------------------
#Test Three

echo -e "Test Three: localhost:8095/trip/getTripById \n"
 
echo -e "${yellow}Result:"

curl http://localhost:8095/trip/getTripById?id=5c79b7e04a8b77336e1a3328

echo -e "-------------------------------------\n"

#---------------------------------------------------------------
#Test Four

echo -e "Test Four: localhost:8095/trip/getTripById \n"
 
echo -e "${yellow}Result:"

curl http://localhost:8095/trip/getTripById?id=5c79b7e04a8b77336e1a3327

echo -e "-------------------------------------\n"

#---------------------------------------------------------------
#Test Five

echo -e "Test Five: localhost:8095/trip/getTripById \n"
 
echo -e "${yellow}Result:"

curl http://localhost:8095/trip/getTripById?id=5c79b7e04a8b77336e1a3326

echo -e "-------------------------------------\n"

#---------------------------------------------------------------
#Test Six

echo -e "Test Six: localhost:8095/trip/getTripById \n"
 
echo -e "${yellow}Result:"

curl http://localhost:8095/trip/getTripById?id=5c79b7e04a8b77336e1a3325

echo -e "-------------------------------------\n"

#---------------------------------------------------------------
#Test Seven

echo -e "Test Seven: localhost:8095/trip/getTripById \n"
 
echo -e "${yellow}Result:"

curl http://localhost:8095/trip/getTripById?id=5c79b7e04a8b77336e1a3324

echo -e "-------------------------------------\n"

#---------------------------------------------------------------
#Test Eight

echo -e "Test Eight: localhost:8095/trip/getTripById \n"
 
echo -e "${yellow}Result:"

curl http://localhost:8095/trip/getTripById?id=5c79b7e04a8b77336e1a3323

echo -e "-------------------------------------\n"

#---------------------------------------------------------------
#Test Nine

echo -e "Test Nine: localhost:8095/trip/getTripById \n"
 
echo -e "${yellow}Result:"

curl http://localhost:8095/trip/getTripById?id=5c79b7e04a8b77336e1a3322

echo -e "-------------------------------------\n"

#---------------------------------------------------------------
#Test Ten

echo -e "Test Ten: localhost:8095/trip/getTripById \n"
 
echo -e "${yellow}Result:"

curl http://localhost:8095/trip/getTripById?id=5c79b7e04a8b77336e1a3321

echo -e " ${reset}"
echo -e "-------------------------------------\n"

#---------------------------------------------------------------

