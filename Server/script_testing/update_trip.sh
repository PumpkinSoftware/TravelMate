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
                                                            
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c561612c22be2586a57ee74", "name":"road to Milanaaaaaaaaa", "budget":3432,"destination":"Cazzun"}' http://localhost:8095/trip/updateTrip

echo -e ""
echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{"_id":"5c4f7ef9683d0037cfb064e0", "name":"Pakistan, seriouslY?", "budget": 2345}' http://localhost:8095/trip/updateTrip

echo -e ""
echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{"_id":"5c3e6045db391f1ab001d106", "name":"Lourdes, We AAre CominG!", "budget": 150}' http://localhost:8095/trip/updateTrip


echo -e " ${reset}"

echo -e "Script end"





 
