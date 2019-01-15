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

echo -e "Starting to populate database of trips"

echo -e "${green}"

curl -H "Content-Type: application/json" -X POST -d '{"name":"road to milan","description":"best travel to milan","departure":"rome","destination":"milan","budget":"123.50","startDate":"12/12/2018","endDate":"01/01/2019"}' http://localhost:8095/trip/new_trip

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{"name":"bourem is bourem","description":"who is bourem","departure":"milan","destination":"bourem","budget":"450.20","startDate":"10/12/2018","endDate":"17/24/2018"}' http://localhost:8095/trip/new_trip

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{"name":"game game game","description":"lv by night","departure":"new york","destination":"las vegas","budget":"1238.13","startDate":"02/15/2019","endDate":"02/20/2019"}' http://localhost:8095/trip/new_trip

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{"name":"brazil, i want you!","description":"amazing brazil","departure":"bari","destination":"fortaleza","budget":"543.50","startDate":"01/01/2019","endDate":"01/20/2019"}' http://localhost:8095/trip/new_trip

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{"name":"tea time","description":"london everywhere","departure":"paris","destination":"london","budget":"234.00","startDate":"02/01/2019","endDate":"02/10/2019"}' http://localhost:8095/trip/new_trip

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{"name":"California, i love you","description":"california in 360°","departure":"rome","destination":"san francisco","budget":"1543.50","startDate":"01/15/2019","endDate":"01/20/2019"}' http://localhost:8095/trip/new_trip

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{"name":"lourdes, we are coming!","description":"god is with us","departure":"naples","destination":"lourdes","budget":"143.50","startDate":"01/23/2019","endDate":"01/27/2019"}' http://localhost:8095/trip/new_trip

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{"name":"pakistan, seriously?","description":"i do no why","departure":"milan","destination":"karachi","budget":"2343.50","startDate":"04/30/2019","endDate":"05/10/2019"}' http://localhost:8095/trip/new_trip

echo -e " ${reset}"

echo -e "Script end"





