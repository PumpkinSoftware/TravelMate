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
  "owner": "USERBYID1234",
  "startDate": "04/04/2019",
  "tag": "tecnologia",
  "vehicle": "auto",
  "partecipants":"0"
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
  "owner":"GETUSERBYID12233445",
  "startDate": "10/12/2019"
  "partecipants":"0"
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
  "owner":"GETUSERBYID1234",
  "startDate": "02/15/2019",
  "partecipants":"0"
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
  "owner":"GETUSERBYID4567",
  "startDate": "05/01/2019",
  "partecipants":"0"
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
  "owner":"GETUSERBYID567",
  "startDate": "03/01/2019",
  "partecipants":"0"
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
  "owner":"GETUSERBYID234",
  "startDate": "04/15/2019",
  "partecipants":"0"
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
  "owner":"GETUSERBYID123",
  "startDate": "07/23/2019",
  "partecipants":"0"
}' http://localhost:8095/trip/newTrip

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{
  "budget": "2343.50",
  "departure": "milan",
  "description": "i do no why",
  "destination": "karachi",
  "endDate": "05/10/2019",
  "image": "https://github.com/PumpkinSoftware/TravelMate/blob/Back-End/Logo/trip.jpg?raw=true",
  "maxPartecipant": "7",
  "name": "pakistan, seriously?",
  "tag":"musica",
  "vehicle":"auto",
  "owner":"GETUSERBYID567",
  "startDate": "04/30/2019",
  "partecipants":"0"
}' http://localhost:8095/trip/newTrip

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{
  "budget": 504.8,
  "departure": "Maxville",
  "description": "Quis amet eiusmod dolore nostrud officia duis ut occaecat dolor id fugiat proident.",
  "destination": "Gila",
  "endDate": "06/22/2019",
  "image": "https://github.com/PumpkinSoftware/TravelMate/blob/Back-End/Logo/trip.jpg?raw=true",
  "maxPartecipant": 91,
  "name": "Gila,i love it!",
  "tag":"intrattenimento",
  "vehicle":"auto",
  "owner":"GETUSERBYID098",
  "startDate": "06/14/2019",
  "partecipants":"0"
}' http://localhost:8095/trip/newTrip

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{
  "budget": 548.46,
  "departure": "Cotopaxi",
  "description": "Dolor proident nostrud qui pariatur minim irure fugiat exercitation irure id dolore ipsum ut. Velit magna occaecat pariatur nisi sint culpa aliqua magna.",
  "destination": "Grahamtown",
  "endDate": "01/10/2020",
  "image": "https://github.com/PumpkinSoftware/TravelMate/blob/Back-End/Logo/trip.jpg?raw=true",
  "maxPartecipant": 35,
  "name": "Grahamtown,i love it!",
  "tag":"cultura",
  "vehicle":"auto",
  "owner":"GETUSERBYID765",
  "startDate": "12/06/2019",
  "partecipants":"0"
}' http://localhost:8095/trip/newTrip

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{
  "budget": 550.42,
  "departure": "Movico",
  "description": "Dolore exercitation sit culpa excepteur eiusmod eiusmod exercitation. Id et laboris qui elit eiusmod sint dolore exercitation in commodo amet.",
  "destination": "Hasty",
  "endDate": "01/10/2020",
  "image": "https://github.com/PumpkinSoftware/TravelMate/blob/Back-End/Logo/trip.jpg?raw=true",
  "maxPartecipant": 33,
  "name": "Hasty,xoxo",
  "tag":"cultura",
  "vehicle":"treno",
  "owner":"GETUSERBYID432",
  "startDate": "12/30/2019",
  "partecipants":"0"
}' http://localhost:8095/trip/newTrip

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{
  "budget": 785.66,
  "departure": "Dowling",
  "description": "Minim dolore Lorem anim consectetur amet officia. Ea magna minim duis velit ipsum eiusmod cupidatat esse sit voluptate fugiat.",
  "destination": "Eden",
  "endDate": "01/10/2020",
  "image": "https://github.com/PumpkinSoftware/TravelMate/blob/Back-End/Logo/trip.jpg?raw=true",
  "maxPartecipant": 15,
  "name": "Eden,let s go!",
  "tag":"tecnologia",
  "vehicle":"treno",
  "owner":"GETUSERBYID432",
  "startDate": "12/06/2018",
  "partecipants":"0"
}' http://localhost:8095/trip/newTrip

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{
  "budget": 768.83,
  "departure": "Boomer",
  "description": "In ea anim nisi minim nulla velit sunt velit qui amet sunt nisi. Lorem voluptate nulla voluptate consectetur esse enim do proident duis fugiat quis et qui deserunt.",
  "destination": "Lund",
  "endDate": "01/01/2020",
  "image": "https://github.com/PumpkinSoftware/TravelMate/blob/Back-End/Logo/trip.jpg?raw=true",
  "maxPartecipant": 28,
  "name": "Lund,let s go!",
  "tag":"musica",
  "vehicle":"auto",
  "owner":"GETUSERBYID432",
  "startDate": "12/30/2019",
  "partecipants":"0"
}' http://localhost:8095/trip/newTrip

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{
  "budget": 422.95,
  "departure": "Mahtowa",
  "description": "Ea ad ut duis ex ullamco anim non occaecat non in nisi velit. Nostrud do enim exercitation proident laboris esse mollit aliquip.",
  "destination": "Oneida",
  "endDate": "01/20/2020",
  "image": "https://github.com/PumpkinSoftware/TravelMate/blob/Back-End/Logo/trip.jpg?raw=true",
  "maxPartecipant": 97,
  "name": "Oneida,i love it!",
  "tag":"intrattenimento",
  "vehicle":"treno",
  "owner":"GETUSERBYID432",
  "startDate": "12/07/2019",
  "partecipants":"0"
}' http://localhost:8095/trip/newTrip

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{
  "budget": 804.79,
  "departure": "Gilmore",
  "description": "Fugiat cillum magna amet pariatur sit exercitation esse labore. Eu consequat velit magna ipsum eu. Consectetur magna id eu minim esse minim aute.",
  "destination": "Leming",
  "endDate": "01/20/2020",
  "image": "https://github.com/PumpkinSoftware/TravelMate/blob/Back-End/Logo/trip.jpg?raw=true",
  "maxPartecipant": 3,
  "name": "Leming,let s go!",
  "tag":"cultura",
  "vehicle":"treno",
  "owner":"GETUSERBYID432",
  "startDate": "12/30/2019",
  "partecipants":"0"
}' http://localhost:8095/trip/newTrip

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{
  "budget": 372.51,
  "departure": "Omar",
  "description": "Do sunt consectetur qui ullamco. Nisi labore in aliqua ad eu duis tempor nisi esse aliquip ullamco sunt adipisicing.",
  "destination": "Dotsero",
  "endDate": "01/01/2020",
  "image": "https://github.com/PumpkinSoftware/TravelMate/blob/Back-End/Logo/trip.jpg?raw=true",
  "maxPartecipant": 50,
  "name": "Dotsero,xoxo",
  "tag":"tecnologia",
  "vehicle":"treno",
  "owner":"GETUSERBYID321",
  "startDate": "12/06/2019",
  "partecipants":"0"
}' http://localhost:8095/trip/newTrip

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{
  "budget": 960.4,
  "departure": "Twilight",
  "description": "Duis et consectetur id quis laborum. Reprehenderit in duis proident exercitation enim fugiat aute non amet magna pariatur. Tempor deserunt deserunt ullamco elit et in nostrud ex ea et ullamco dolore culpa.",
  "destination": "Montura",
  "endDate": "01/02/2020",
  "image": "https://github.com/PumpkinSoftware/TravelMate/blob/Back-End/Logo/trip.jpg?raw=true",
  "maxPartecipant": 19,
  "name": "Montura,xoxo",
  "tag":"musica",
  "vehicle":"treno",
  "owner":"GETUSERBYID321",
  "startDate": "12/14/2019",
  "partecipants":"0"
}' http://localhost:8095/trip/newTrip
  
echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{
  "budget": 804.42,
  "departure": "Eagleville",
  "description": "Eiusmod qui qui aute proident aliquip. Aliqua anim ea elit dolor proident tempor sint do sunt pariatur ex excepteur minim commodo.",
  "destination": "Freetown",
  "endDate": "01/20/2020",
  "image": "https://github.com/PumpkinSoftware/TravelMate/blob/Back-End/Logo/trip.jpg?raw=true",
  "maxPartecipant": 71,
  "name": "Freetown,i love it!",
  "tag":"tecnologia",
  "vehicle":"auto",
  "owner":"GETUSERBYID432",
  "startDate": "12/14/2019",
  "partecipants":"0"
}' http://localhost:8095/trip/newTrip

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{
  "budget": 381.65,
  "departure": "Trona",
  "description": "Nulla excepteur sint nulla cupidatat officia ex. Proident laborum ipsum mollit veniam dolore laborum. Consequat incididunt aliqua et Lorem id velit Lorem cupidatat aute mollit magna magna.",
  "destination": "Newry",
  "endDate": "01/02/2020",
  "image": "https://github.com/PumpkinSoftware/TravelMate/blob/Back-End/Logo/trip.jpg?raw=true",
  "maxPartecipant": 86,
  "name": "Newry,xoxo",
  "tag":"musica",
  "vehicle":"treno",
  "owner":"GETUSERBYID321",
  "startDate": "12/06/2019",
  "partecipants":"0"
}' http://localhost:8095/trip/newTrip

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{
  "budget": 140.61,
  "departure": "Hilltop",
  "description": "Cupidatat id non et non nulla fugiat do et ea. Aliqua incididunt irure dolor tempor nulla Lorem consectetur ex nostrud nostrud id eu magna. Labore voluptate excepteur nostrud enim ipsum nisi fugiat sunt aliqua.",
  "destination": "Manila",
  "endDate": "01/01/2020",
  "image": "https://github.com/PumpkinSoftware/TravelMate/blob/Back-End/Logo/trip.jpg?raw=true",
  "maxPartecipant": 51,
  "name": "Manila,let s go!",
  "tag":"intrattenimento",
  "vehicle":"treno",
  "owner":"GETUSERBYID765",
  "startDate": "12/06/2019",
  "partecipants":"0"
}' http://localhost:8095/trip/newTrip

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{
  "budget": 480.9,
  "departure": "Balm",
  "description": "Aliquip dolore non deserunt amet esse consectetur eu sunt ex duis qui. Ullamco aliquip eiusmod laborum eu sit do laboris. Aliqua do ullamco irure do minim deserunt non id aliquip cupidatat deserunt Lorem reprehenderit qui.",
  "destination": "Sharon",
  "endDate": "01/02/2020",
  "image": "https://github.com/PumpkinSoftware/TravelMate/blob/Back-End/Logo/trip.jpg?raw=true",
  "maxPartecipant": 63,
  "name": "Sharon,let s go!",
  "tag":"tecnologia",
  "vehicle":"auto",
  "owner":"GETUSERBYID654",
  "startDate": "12/14/2019",
  "partecipants":"0"
}' http://localhost:8095/trip/newTrip

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{
  "budget": 308.17,
  "departure": "Innsbrook",
  "description": "Reprehenderit ex labore fugiat cupidatat. Adipisicing voluptate magna ad magna nulla exercitation cupidatat. Ut adipisicing mollit Lorem commodo nostrud elit deserunt do labore ea. Sunt irure ex adipisicing veniam do consequat est sit ut veniam excepteur quis irure. Aliqua nulla officia irure commodo deserunt sunt velit anim dolor veniam labore.",
  "destination": "Jennings",
  "endDate": "01/10/2020",
  "image": "https://github.com/PumpkinSoftware/TravelMate/blob/Back-End/Logo/trip.jpg?raw=true",
  "maxPartecipant": 45,
  "name": "Jennings,i love it!",
  "tag":"intrattenimento",
  "vehicle":"treno",
  "owner":"GETUSERBYID422",
  "startDate": "12/14/2019",
  "partecipants":"0"
}' http://localhost:8095/trip/newTrip


echo -e " ${reset}"

echo -e "Script end"





