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

curl -H "Content-Type: application/json" -X POST -d '{
  "budget": "123.50",
  "departure": "rome",
  "description": "best travel to milan",
  "destination": "milan",
  "endDate": "04/10/2019",
  "image": "https://github.com/PumpkinSoftware/TravelMate/blob/Back-End/Logo/trip.jpg?raw=true",
  "maxPartecipant": "10",
  "name": "Road To Milan",
  "owner": "5c7a7a9dfee5d31cc967dce5",
  "startDate": "04/04/2019",
  "tag": "tecnologia",
  "vehicle": "auto"
}' http://localhost:8095/trip/newTrip

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{
  "budget": "450.20",
  "departure": "milan",
  "description": "who is bourem",
  "destination": "bourem",
  "endDate": "10/24/2019",
  "image": "https://github.com/PumpkinSoftware/TravelMate/blob/Back-End/Logo/trip.jpg?raw=true",
  "maxPartecipant": "9",
  "name": "bourem is bourem",
  "vehicle": "treno",
  "tag":"tecnologia",
  "owner":"5c7a7a9dfee5d31cc967dce6",
  "startDate": "10/12/2019"
}' http://localhost:8095/trip/newTrip

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{
  "budget": "1238.13",
  "departure": "new york",
  "description": "lv by night",
  "destination": "las vegas",
  "endDate": "02/20/2019",
  "image": "https://github.com/PumpkinSoftware/TravelMate/blob/Back-End/Logo/trip.jpg?raw=true",
  "maxPartecipant": "50",
  "name": "game game game",
  "tag":"musica",
  "vehicle":"treno",
  "owner":"5c7a7a9dfee5d31cc967dce7",
  "startDate": "02/15/2019"
}' http://localhost:8095/trip/newTrip

echo -e ""


curl -H "Content-Type: application/json" -X POST -d '{
  "budget": "543.50",
  "departure": "bari",
  "description": "amazing brazil",
  "destination": "fortaleza",
  "endDate": "06/20/2019",
  "image": "https://github.com/PumpkinSoftware/TravelMate/blob/Back-End/Logo/trip.jpg?raw=true",
  "maxPartecipant": "2",
  "name": "brazil, i want you!",
  "tag":"intrattenimento",
  "vehicle":"auto",
  "owner":"5c7a7a9dfee5d31cc967dce8",
  "startDate": "05/01/2019"
}' http://localhost:8095/trip/newTrip

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{
  "budget": "234.00",
  "departure": "paris",
  "description": "london everywhere",
  "destination": "london",
  "endDate": "03/10/2019",
  "image": "https://github.com/PumpkinSoftware/TravelMate/blob/Back-End/Logo/trip.jpg?raw=true",
  "maxPartecipant": "10",
  "name": "tea time",
  "tag":"cultura",
  "vehicle":"treno",
  "owner":"5c7a7a9dfee5d31cc967dce9",
  "startDate": "03/01/2019"
}' http://localhost:8095/trip/newTrip

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{
  "budget": "1543.50",
  "departure": "rome",
  "description": "california in 360°",
  "destination": "san francisco",
  "endDate": "05/20/2019",
  "image": "https://github.com/PumpkinSoftware/TravelMate/blob/Back-End/Logo/trip.jpg?raw=true",
  "maxPartecipant": "10",
  "name": "California, i love you",
  "tag":"tecnologia",
  "vehicle":"auto",
  "owner":"5c7a7a9dfee5d31cc967dcea",
  "startDate": "04/15/2019"
}' http://localhost:8095/trip/newTrip

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{
  "budget": "143.50",
  "departure": "naples",
  "description": "god is with us",
  "destination": "lourdes",
  "endDate": "07/27/2019",
  "image": "https://github.com/PumpkinSoftware/TravelMate/blob/Back-End/Logo/trip.jpg?raw=true",
  "maxPartecipant": "20",
  "name": "lourdes, we are coming!",
  "tag":"musica",
  "vehicle":"treno",
  "owner":"5c7a7a9dfee5d31cc967dceb",
  "startDate": "07/23/2019"
}' http://localhost:8095/trip/newTrip

echo -e ""

echo -e " ${reset}"

echo -e "Script end"





