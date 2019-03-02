#!/bin/bash

# test for /trip/lastTripsCreated?limit=200

#Comandi di default per modificare colore shell
red=`tput setaf 1`
yellow=`tput setaf 3`
green=`tput setaf 2`
reset=`tput sgr0`

echo -e ""

#---------------------------------------------------------------
#Test One

echo -e "Test One: localhost:8095/trip/lastTripsCreated?limit=1 \n"
 
echo -e "${yellow}Result:"

curl http://localhost:8095/trip/lastTripsCreated?limit=1

echo -e " ${reset}"
echo -e "${green}Expected:"
echo -e "Json array with all trips order by create date desc ${reset}"
echo -e "-------------------------------------\n"

#---------------------------------------------------------------
#Test Two

echo -e "Test Two: localhost:8095/trip/lastTripsCreated?limit=5 \n"
 
echo -e "${yellow}Result:"

curl http://localhost:8095/trip/lastTripsCreated?limit=5

echo -e " ${reset}"
echo -e "${green}Expected:"
echo -e "Json array with all trips order by create date desc ${reset}"
echo -e "-------------------------------------\n"

#---------------------------------------------------------------
#Test Three

echo -e "Test Three: localhost:8095/trip/lastTripsCreated?limit=10 \n"
 
echo -e "${yellow}Result:"

curl http://localhost:8095/trip/lastTripsCreated?limit=10

echo -e " ${reset}"
echo -e "${green}Expected:"
echo -e "Json array with all trips order by create date desc ${reset}"
echo -e "-------------------------------------\n"

#---------------------------------------------------------------
#Test Four

echo -e "Test Four: localhost:8095/trip/lastTripsCreated?limit=20 \n"
 
echo -e "${yellow}Result:"

curl http://localhost:8095/trip/lastTripsCreated?limit=20

echo -e " ${reset}"
echo -e "${green}Expected:"
echo -e "Json array with all trips order by create date desc ${reset}"
echo -e "-------------------------------------\n"

#---------------------------------------------------------------
#Test Five

echo -e "Test Five: localhost:8095/trip/lastTripsCreated?limit=200 \n"
 
echo -e "${yellow}Result:"

curl http://localhost:8095/trip/lastTripsCreated?limit=200

echo -e " ${reset}"
echo -e "${green}Expected:"
echo -e "Json array with all trips order by create date desc ${reset}"
echo -e "-------------------------------------\n"

#---------------------------------------------------------------
#Test Six

echo -e "Test Six: localhost:8095/trip/lastTripsCreated?limit=200 \n"
 
echo -e "${yellow}Result:"

curl http://localhost:8095/trip/lastTripsCreated?limit=200

echo -e " ${reset}"
echo -e "${green}Expected:"
echo -e "Json array with all trips order by create date desc ${reset}"
echo -e "-------------------------------------\n"

#---------------------------------------------------------------
#Test Seven

echo -e "Test Seven: localhost:8095/trip/lastTripsCreated?limit=200 \n"
 
echo -e "${yellow}Result:"

curl http://localhost:8095/trip/lastTripsCreated?limit=200

echo -e " ${reset}"
echo -e "${green}Expected:"
echo -e "Json array with all trips order by create date desc ${reset}"
echo -e "-------------------------------------\n"

#---------------------------------------------------------------
#Test Eight

echo -e "Test Eight: localhost:8095/trip/lastTripsCreated?limit=200 \n"
 
echo -e "${yellow}Result:"

curl http://localhost:8095/trip/lastTripsCreated?limit=200

echo -e " ${reset}"
echo -e "${green}Expected:"
echo -e "Json array with all trips order by create date desc ${reset}"
echo -e "-------------------------------------\n"

#---------------------------------------------------------------
#Test Nine

echo -e "Test Nine: localhost:8095/trip/lastTripsCreated?limit=200 \n"
 
echo -e "${yellow}Result:"

curl http://localhost:8095/trip/lastTripsCreated?limit=200

echo -e " ${reset}"
echo -e "${green}Expected:"
echo -e "Json array with all trips order by create date desc ${reset}"
echo -e "-------------------------------------\n"

#---------------------------------------------------------------
#Test Ten

echo -e "Test Ten: localhost:8095/trip/lastTripsCreated?limit=200 \n"
 
echo -e "${yellow}Result:"

curl http://localhost:8095/trip/lastTripsCreated?limit=200

echo -e " ${reset}"
echo -e "${green}Expected:"
echo -e "Json array with all trips order by create date desc ${reset}"
echo -e "-------------------------------------\n"

#---------------------------------------------------------------

