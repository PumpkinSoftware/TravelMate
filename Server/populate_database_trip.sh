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
  "id":"5c79b80f4a8b77336e1a332a",
  "budget": "60",
  "departure": "roma",
  "description": "In questo spazio allestito per l’occasione si potrà rivivere l’atmosfera caratteristica di pub inglesi, danesi, spagnoli, tedeschi, irlandesi, scozzesi e di altre culture birraie e degustare oltre 100 tipologie di birra alla spina e specialità in bottiglia tra bionde, rosse, dolci, fruttate, a doppio malto, artigianali e meditative.",
  "destination": "leverano",
  "endDate": "08/05/2019",
  "image": "https://www.ilturista.info/repo/images/no/AAAEventi/Birra_Sound_Leverano_Puglia.jpg",
  "maxPartecipant": "25",
  "name": "festival birra e sound",
  "owner": "5c7ad36f56c9ff0d78213ef8",
  "startDate": "08/04/2019",
  "tag": "intrattenimento",
  "vehicle": "treno"
}' http://localhost:8095/trip/newTrip

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{
  "id":"5c79b80e4a8b77336e1a3329",
  "budget": "140",
  "departure": "ravenna",
  "description": "Workshop durante il quale i partecipanti si divideranno in team e svilupperanno una vera e propria applicazione web",
  "destination": "faenza",
  "endDate": "03/15/2019",
  "image": "https://img.evbuc.com/https%3A%2F%2Fcdn.evbuc.com%2Fimages%2F42029037%2F238505255268%2F1%2Foriginal.jpg?w=800&auto=compress&rect=0%2C0%2C2500%2C1250&s=79d73bcd8b946d541bb955335a2c06f6",
  "maxPartecipant": "10",
  "name": "css day 2019",
  "vehicle": "auto",
  "tag":"tecnologia",
  "owner":"5c7ad36f56c9ff0d78213ef9",
  "startDate": "03/15/2019"
}' http://localhost:8095/trip/newTrip

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{
  "id":"5c79b7e04a8b77336e1a3328",
  "budget": "65",
  "departure": "palermo",
  "description": "La Raviolata permette di assaggiare il classico raviolo grande come un portafoglio, spesso e irregolare, rigonfio di un ripieno che combina formaggi, pancetta, macinato e spezie, condito con un classico sugo al pomodoro. La manifestazione si svolge in una struttura coperta.",
  "destination": "scapoli",
  "endDate": "05/6/2019",
  "image": "https://www.ilturista.info/myTurista/files/1/la_sagra_del_raviolo_scapolese_a_scapoli_in_molise.jpg",
  "maxPartecipant": "15",
  "name": "la sagra del raviolo scapolese",
  "tag":"cultura",
  "vehicle":"auto",
  "owner":"5c7ad36f56c9ff0d78213efa",
  "startDate": "05/05/2019"
}' http://localhost:8095/trip/newTrip

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{
  "id":"5c79b7e04a8b77336e1a3327",
  "budget": "70",
  "departure": "bari",
  "description": "Posti sul prato. Arriviamo il 5 mattina, mentre ripartiamo il 6 dopo aver fatto colazione",
  "destination": "milano",
  "endDate": "04/06/2019",
  "image": "https://checkinrome.net/wp-content/uploads/2018/08/irama.png",
  "maxPartecipant": "25",
  "name": "irama, live 2019",
  "tag":"musica",
  "vehicle":"treno",
  "owner":"5c7ad36f56c9ff0d78213efb",
  "startDate": "04/05/2019"
}' http://localhost:8095/trip/newTrip

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{
  "id":"5c79b7e04a8b77336e1a3326",
  "budget": "45.00",
  "departure": "pescara",
  "description": "Il quintetto di fiati Wind Academy Quintet si presenterà singolarmente al pubblico facendo risuonare il timbro di ciascuno strumento con motivi certamente riconoscibili.",
  "destination": "riccione",
  "endDate": "04/05/2019",
  "image": "https://ecdn.evensi.com/e289308383?code=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJob3N0Ijoic2NvbnRlbnQtYW1zMy0xLnh4LmZiY2RuLm5ldCIsInBhdGgiOiJcL3ZcL3QxLjAtOVwvNTA1ODQ3MTJfMTI2MTY2NjEyMzk4Njk4OF84MzM4NjE1NTcyODEyMDA1Mzc2X24uanBnIiwicXVlcnkiOiJfbmNfY2F0PTEwOSZfbmNfaHQ9c2NvbnRlbnQtYW1zMy0xLnh4Jm9oPTMzYjMwMjE4NDYzYTgxZGM0ZDU5ZjE3ZTA5NjA3NzJiJm9lPTVEMjU0RDVGIn0.NV_UpKt-48VF_7J3ZHTbZjuUBA93AszdNWL462RW4W4",
  "maxPartecipant": "16",
  "name": "pierino e il lupo",
  "tag":"cultura",
  "vehicle":"treno",
  "owner":"5c7ad36f56c9ff0d78213efc",
  "startDate": "04/05/2019"
}' http://localhost:8095/trip/newTrip

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{
  "id":"5c79b7e04a8b77336e1a3325",
  "budget": "30",
  "departure": "pescara",
  "description": "Robert Capa Retrospective”, a cura di Denis Curti, approda alla Mole Vanvitelliana diAncona",
  "destination": "ancona",
  "endDate": "05/31/2019",
  "image": "https://citynews-anconatoday.stgy.ovh/~media/horizontal-mid/67154301127738/robert-capa-2.jpg",
  "maxPartecipant": "4",
  "name": "robert capa alla mole",
  "tag":"cultura",
  "vehicle":"auto",
  "owner":"5c7ad36f56c9ff0d78213efd",
  "startDate": "05/31/2019"
}' http://localhost:8095/trip/newTrip

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{
  "id":"5c79b7e04a8b77336e1a3324",
  "budget": "45",
  "departure": "milano",
  "description": "Omar Pedrini riporta sui palchi lo storico album dei Timoria uscito nel 1993 di cui è ideatore e autore principale.",
  "destination": "torino",
  "endDate": "06/03/2019",
  "image": "http://2.citynews-torinotoday.stgy.ovh/~media/horizontal-mid/27176914369708/omarpedrini-4-2.jpg",
  "maxPartecipant": "15",
  "name": "omar pedrini allo spazio 211",
  "tag":"intrattenimento",
  "vehicle":"treno",
  "owner":"5c7ad36f56c9ff0d78213efe",
  "startDate": "06/03/2019"
}' http://localhost:8095/trip/newTrip

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{
  "id":"5c79b7e04a8b77336e1a3323",
  "budget": "90",
  "departure": "bari",
  "description": "Ultimo arriva sul palco del Mediolanum Forum con il suo Colpa delle Favole Tour 2019, durante il quale porterà live i brani del suo ultimo album.",
  "destination": "assago",
  "endDate": "05/15/2019",
  "image": "http://www.quirinetta.com/wp-content/uploads/2018/01/Ultimo-20-01-2018-Panucci-CON-LOGO-27--720x490.jpg",
  "maxPartecipant": "25",
  "name": "concerto di ultimo",
  "tag":"musica",
  "vehicle":"treno",
  "owner":"5c7ad36f56c9ff0d78213eff",
  "startDate": "05/16/2019"
}' http://localhost:8095/trip/newTrip

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{
  "id":"5c79b7e04a8b77336e1a3322",
  "budget": 79,
  "departure": "venezia",
  "description": "Leonardo da Vinci Parade è una vera e propria sfilata, una celebrazione in occasione di una ricorrenza importante, in cui si esibiscono in parata sul palco del museo alcuni modelli storici ispirati ai disegni di Leonardo, oltre che ad alcuni affreschi, in un accostamento insolito di arte e scienza.",
  "destination": "milano",
  "endDate": "10/02/2019",
  "image": "http://1.citynews-milanotoday.stgy.ovh/~media/horizontal-mid/22338961547518/modello02-3.jpg",
  "maxPartecipant": 17,
  "name": "Leonardo da Vinci Parade",
  "tag":"cultura",
  "vehicle":"treno",
  "owner":"5c7ad36f56c9ff0d78213f00",
  "startDate": "10/02/2019"
}' http://localhost:8095/trip/newTrip

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{
  "id":"5c79b7e04a8b77336e1a3321",
  "budget": 23,
  "departure": "latina",
  "description": "La prima mostra monografica di Anne e Patrick Poirier in Italia, ROMAMOR",
  "destination": "roma",
  "endDate": "05/12/2019",
  "image": "http://www.arte.it/foto/600x450/80/88583-Romamor_01_Daniele_Molajoli.jpg",
  "maxPartecipant": 4,
  "name": "anne e patrick poirier. romamor",
  "tag":"cultura",
  "vehicle":"auto",
  "owner":"5c7ad36f56c9ff0d78213f01",
  "startDate": "05/12/2019"
}' http://localhost:8095/trip/newTrip

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{
  "id":"5c79b7e04a8b77336e1a3320",
  "budget": 95,
  "departure": "torino",
  "description": "Conferenza su applicazioni open source basate su Erlang, OTP, Elixir, LFE, BEAM e altre tecnologie",
  "destination": "bologna",
  "endDate": "03/22/2019",
  "image": "https://img.evbuc.com/https%3A%2F%2Fcdn.evbuc.com%2Fimages%2F52375141%2F241612882443%2F1%2Foriginal.jpg?w=800&auto=compress&rect=0%2C0%2C2160%2C1080&s=7d35f3931aab2ed3fb9f03e1ac727b18",
  "maxPartecipant": 7,
  "name": "code beam ite italy 2019",
  "tag":"tecnologia",
  "vehicle":"treno",
  "owner":"5c7ad36f56c9ff0d78213ef8",
  "startDate": "03/22/2019"
}' http://localhost:8095/trip/newTrip

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{
  "id":"5c79b7e04a8b77336e1a331f",
  "budget": 45,
  "departure": "campobasso",
  "description": "Carl Brave arriva sul palco del Parco dei Suoni di Riola Sardo (Oristano) il 27 Luglio 2019 con il suo “Notti Brave Tour”, durante il quale porterà live alcuni dei brani estratti dal suo ultimo album",
  "destination": "oristano",
  "endDate": "07/28/2019",
  "image": "https://i2.wp.com/www.hiphopstarztour.com/wp-content/uploads/2018/12/carl-brave-gen-loc18.jpg?w=400",
  "maxPartecipant": 25,
  "name": "notti brave tour 2019",
  "tag":"musica",
  "vehicle":"auto",
  "owner":"5c7ad36f56c9ff0d78213ef9",
  "startDate": "07/27/2019"
}' http://localhost:8095/trip/newTrip

echo -e ""

echo -e " ${reset}"

echo -e "Script end"





