#!/bin/bash

# test for /trip/updateTrip

#Comandi di default per modificare colore shell
red=`tput setaf 1`
yellow=`tput setaf 3`
green=`tput setaf 2`
reset=`tput sgr0`

echo -e ""

#---------------------------------------------------------------
#Test One

echo -e "Test One: localhost:8095/trip/updateTrip \n"
 
echo -e "${yellow}Result:"

curl -H "Content-Type: application/json" -X POST -d '{
  "tripId":"5c79b80f4a8b77336e1a332a",
  "budget": "600"
}' http://localhost:8095/trip/updateTrip

echo -e "-------------------------------------\n"

#---------------------------------------------------------------
#Test Two

echo -e "Test Two: localhost:8095/trip/updateTrip \n"
 
echo -e "${yellow}Result:"

curl -H "Content-Type: application/json" -X POST -d '{
  "tripId":"5c79b80e4a8b77336e1a3329",
  "departure": "loreto"
}' http://localhost:8095/trip/updateTrip

echo -e "-------------------------------------\n"

#---------------------------------------------------------------
#Test Three

echo -e "Test Three: localhost:8095/trip/updateTrip \n"
 
echo -e "${yellow}Result:"

curl -H "Content-Type: application/json" -X POST -d '{
  "tripId":"5c79b7e04a8b77336e1a3328",
  "vehicle":"treno"
}' http://localhost:8095/trip/updateTrip

echo -e "-------------------------------------\n"

#---------------------------------------------------------------
#Test Four

echo -e "Test Four: localhost:8095/trip/updateTrip \n"
 
echo -e "${yellow}Result:"

curl -H "Content-Type: application/json" -X POST -d '{
  "tripId":"5c79b7e04a8b77336e1a3327",
  "destination": "roma"
}' http://localhost:8095/trip/updateTrip

echo -e "-------------------------------------\n"

#---------------------------------------------------------------
#Test Five

echo -e "Test Five: localhost:8095/trip/updateTrip \n"
 
echo -e "${yellow}Result:"

curl -H "Content-Type: application/json" -X POST -d '{
  "tripId":"5c79b7e04a8b77336e1a3326",
  "budget": "145"
}' http://localhost:8095/trip/updateTrip

echo -e "-------------------------------------\n"

#---------------------------------------------------------------
#Test Six

echo -e "Test Six: localhost:8095/trip/updateTrip \n"
 
echo -e "${yellow}Result:"

curl -H "Content-Type: application/json" -X POST -d '{
  "tripId":"5c79b7e04a8b77336e1a3325",
  "budget": "5500"
}' http://localhost:8095/trip/updateTrip

echo -e "-------------------------------------\n"

#---------------------------------------------------------------
#Test Seven

echo -e "Test Seven: localhost:8095/trip/updateTrip \n"
 
echo -e "${yellow}Result:"

curl -H "Content-Type: application/json" -X POST -d '{
  "tripId":"5c79b7e04a8b77336e1a3324",
  "budget": "143.50",
  "departure": "torino",
  "description": "Inaugurata a Milano “Dream Beasts. Le spettacolari creature di Theo Jansen” al Museo Nazionale della Scienza e della Tecnologia Leonardo da Vinci. Fino a tutto il 19 maggio 2019 le intriganti opere di Jansen saranno esposte nel padiglione Aeronavale del Museo, per raccontare al grande pubblico una ricerca all’insegna dell’innovazione e della sostenibilità.Creature stupefacenti, insetti e animali preistorici dalle grandi dimensioni che si muovono grazie alla spinta del vento, spostandosi con naturalezza e mistero. Le gigantesche installazioni cinetiche Strandbeest hanno reso questo artista famoso ovunque, sono proprio questi incredibili animali da spiaggia che hanno portato il suo nome in tutto il mondo conquistando e raccontando una ricerca con la quale confrontarsi a livello intellettuale ed emotivo.",
  "destination": "milano",
  "endDate": "05/27/2019",
  "image": "https://www.ilturista.info/repo/images/no/Mostre_2019/Milano/Mostra_Teo_Jansen_Milano.jpg",
  "maxPartecipant": "25",
  "name": "mostra su theo jansen",
  "tag":"cultura",
  "vehicle":"auto",
  "owner":"GETUSERBYID123",
  "startDate": "05/15/2019"
}' http://localhost:8095/trip/updateTrip

echo -e "-------------------------------------\n"

#---------------------------------------------------------------
#Test Eight

echo -e "Test Eight: localhost:8095/trip/updateTrip \n"
 
echo -e "${yellow}Result:"

curl -H "Content-Type: application/json" -X POST -d '{
  "tripId":"5c79b7e04a8b77336e1a3323",
  "budget": "25",
  "departure": "latina",
  "description": "Durante tutta la serata, la pagina Spotted: Sapienza - Università di Roma sarà proiettata LIVE sui maxi schermi del locale, in modo che ogni messaggio che manderete sulla pagina verrà visualizzato in tempo reale direttamente dalla persona a cui è indirizzato! Noi vi indichiamo la strada, il resto sta a voi!",
  "destination": "roma",
  "endDate": "03/05/2019",
  "image": "https://scontent.ffco2-1.fna.fbcdn.net/v/t1.0-9/52602253_2168889659836635_5594296036634918912_o.jpg?_nc_cat=101&_nc_ht=scontent.ffco2-1.fna&oh=ee59672dd1097b7886f9c456cf59b204&oe=5D1E7AEE",
  "maxPartecipant": "90",
  "name": "spotted party - festa della donna",
  "tag":"intrattenimento",
  "vehicle":"auto",
  "owner":"GETUSERBYID567",
  "startDate": "03/05/2019"
}' http://localhost:8095/trip/updateTrip

echo -e "-------------------------------------\n"

#---------------------------------------------------------------
#Test Nine

echo -e "Test Nine: localhost:8095/trip/updateTrip \n"
 
echo -e "${yellow}Result:"

curl -H "Content-Type: application/json" -X POST -d '{
  "tripId":"5c79b7e04a8b77336e1a3322",
  "budget": 413,
  "departure": "assisi",
  "description": "A Gallipoli, la splendida località sulla riva salentina, la Settimana Santa è un tripudio di fede e mistero che tocca il culmine nella notte tra il venerdì e il sabato. Come spesso accade nelle zone un tempo sottoposte alla dominazione spagnola, a organizzare i riti e le celebrazioni sono le confraternite del paese, ciascuna differenziata nei compiti e nei costumi. Per il 2019 le manifestazioni inezieranno il 12 aprile, e cluminerano nella notte tra il 19 e il 20 aprile.",
  "destination": "gallipoli",
  "endDate": "04/19/2019",
  "image": "https://www.ilturista.info/repo/images/no/Settimana_Santa_Italia/Settimana%20Santa%20Gallipoli.jpg",
  "maxPartecipant": 91,
  "name": "la settimana santa di gallipoli",
  "tag":"cultura",
  "vehicle":"treno",
  "owner":"GETUSERBYID098",
  "startDate": "04/06/2019"
}' http://localhost:8095/trip/updateTrip

echo -e "-------------------------------------\n"

#---------------------------------------------------------------
#Test Ten

echo -e "Test Ten: localhost:8095/trip/updateTrip \n"
 
echo -e "${yellow}Result:"

curl -H "Content-Type: application/json" -X POST -d '{
  "tripId":"5c79b7e04a8b77336e1a3321",
  "budget": 548.46,
  "departure": "Cotopaxi",
  "description": "Dolor proident nostrud qui pariatur minim irure fugiat exercitation irure id dolore ipsum ut. Velit magna occaecat pariatur nisi sint culpa aliqua magna.",
  "destination": "Grahamtown",
  "endDate": "01/10/2019",
  "image": "https://github.com/PumpkinSoftware/TravelMate/blob/Back-End/Logo/trip.jpg?raw=true",
  "maxPartecipant": 35,
  "name": "Grahamtown,i love it!",
  "tag":"cultura",
  "vehicle":"auto",
  "owner":"GETUSERBYID765",
  "startDate": "12/06/2019"
}' http://localhost:8095/trip/updateTrip

echo -e " ${reset}"
echo -e "-------------------------------------\n"

#---------------------------------------------------------------

