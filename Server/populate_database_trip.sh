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
  "image": "https://firebasestorage.googleapis.com/v0/b/travelmate-ee239.appspot.com/o/tripImage%2FBirra_Sound_Leverano_Puglia.jpg?alt=media&token=0e8e4430-fdbc-41a0-a8af-4153a9a6cce5",
  "maxPartecipant": "25",
  "name": "festival birra e sound",
  "owner": "Tm8WrtKtVzco0him7RlnB9jUMmL2",
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
  "image": "https://firebasestorage.googleapis.com/v0/b/travelmate-ee239.appspot.com/o/tripImage%2Fcss.jpg?alt=media&token=3f432612-8f8a-40be-b6d6-71c27c282964",
  "maxPartecipant": "10",
  "name": "css day 2019",
  "vehicle": "auto",
  "tag":"tecnologia",
  "owner":"RaHUVBSgljOzWvKNSGLl3QeSThh1",
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
  "image": "https://firebasestorage.googleapis.com/v0/b/travelmate-ee239.appspot.com/o/tripImage%2Fla_sagra_del_raviolo_scapolese_a_scapoli_in_molise.jpg?alt=media&token=f23377d3-2945-4521-860a-ad5f631d6248",
  "maxPartecipant": "15",
  "name": "la sagra del raviolo scapolese",
  "tag":"cultura",
  "vehicle":"auto",
  "owner":"kbz4sNCZtHMlfw3w3xCW3TTBco53",
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
  "image": "https://firebasestorage.googleapis.com/v0/b/travelmate-ee239.appspot.com/o/tripImage%2Firama.png?alt=media&token=c6db7b44-a30a-4fcd-a933-4a2d82c86a56",
  "maxPartecipant": "25",
  "name": "irama, live 2019",
  "tag":"musica",
  "vehicle":"treno",
  "owner":"pIfUG1Qpmwasqrdu77d9nake9HM2",
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
  "image": "https://firebasestorage.googleapis.com/v0/b/travelmate-ee239.appspot.com/o/tripImage%2Fpierino-e-il-lupo-riccione.jpg?alt=media&token=8799da11-a8f5-4a6a-a30e-911309749e4f",
  "maxPartecipant": "16",
  "name": "pierino e il lupo",
  "tag":"cultura",
  "vehicle":"treno",
  "owner":"7hjchdyNdrO2itvdMRRj3b85wff2",
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
  "image": "https://firebasestorage.googleapis.com/v0/b/travelmate-ee239.appspot.com/o/tripImage%2Frobert%20capa-2.jpg?alt=media&token=daa2a428-481e-45ca-8ca4-d923bedb504d",
  "maxPartecipant": "4",
  "name": "robert capa alla mole",
  "tag":"cultura",
  "vehicle":"auto",
  "owner":"Ip5ooeVpugPGm6I0SY5Y2TUNIki2",
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
  "image": "https://firebasestorage.googleapis.com/v0/b/travelmate-ee239.appspot.com/o/tripImage%2Fomarpedrini-4-2.jpg?alt=media&token=8edacc1a-d7af-4660-ae97-7bf4f2406c8c",
  "maxPartecipant": "15",
  "name": "omar pedrini allo spazio 211",
  "tag":"intrattenimento",
  "vehicle":"treno",
  "owner":"GutGWyaTg9OdhjecP1wzd35e0l83",
  "startDate": "06/03/2019"
}' http://localhost:8095/trip/newTrip

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{
  "id":"5c79b7e04a8b77336e1a3323",
  "budget": "90",
  "departure": "bari",
  "description": "Ultimo arriva sul palco del Mediolanum Forum con il suo Colpa delle Favole Tour 2019, durante il quale porterà live i brani del suo ultimo album.",
  "destination": "assago",
  "endDate": "05/16/2019",
  "image": "https://firebasestorage.googleapis.com/v0/b/travelmate-ee239.appspot.com/o/tripImage%2FUltimo-20-01-2018-Panucci-CON-LOGO-27--720x490.jpg?alt=media&token=56e1af38-7520-4713-b0ea-8ce0d3cd8508",
  "maxPartecipant": "25",
  "name": "concerto di ultimo",
  "tag":"musica",
  "vehicle":"treno",
  "owner":"3ENghkkewyPdttrz00ImV0F0QpH2",
  "startDate": "05/15/2019"
}' http://localhost:8095/trip/newTrip

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{
  "id":"5c79b7e04a8b77336e1a3322",
  "budget": 79,
  "departure": "venezia",
  "description": "Leonardo da Vinci Parade è una vera e propria sfilata, una celebrazione in occasione di una ricorrenza importante, in cui si esibiscono in parata sul palco del museo alcuni modelli storici ispirati ai disegni di Leonardo, oltre che ad alcuni affreschi, in un accostamento insolito di arte e scienza.",
  "destination": "milano",
  "endDate": "10/02/2019",
  "image": "https://firebasestorage.googleapis.com/v0/b/travelmate-ee239.appspot.com/o/tripImage%2Fmodello02-3.jpg?alt=media&token=3c3548e8-a076-4104-b8d9-87b02d922775",
  "maxPartecipant": 17,
  "name": "Leonardo da Vinci Parade",
  "tag":"cultura",
  "vehicle":"treno",
  "owner":"sSiOgobodkOlLkyzbIqnqxLQQq93",
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
  "image": "https://firebasestorage.googleapis.com/v0/b/travelmate-ee239.appspot.com/o/tripImage%2F88583-Romamor_01_Daniele_Molajoli.jpg?alt=media&token=c8aa78ff-1c49-4329-bd84-76d736e2846c",
  "maxPartecipant": 4,
  "name": "anne e patrick poirier. romamor",
  "tag":"cultura",
  "vehicle":"auto",
  "owner":"pfcytk2YLdb7qQ2a224J420vZB03",
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
  "image": "https://firebasestorage.googleapis.com/v0/b/travelmate-ee239.appspot.com/o/tripImage%2F74ee009363d999690b76b4c59bfbd1cb23fff7b7.jpeg?alt=media&token=df21382a-046c-40bd-96ea-31871681fccb",
  "maxPartecipant": 7,
  "name": "code beam ite italy 2019",
  "tag":"tecnologia",
  "vehicle":"treno",
  "owner":"Tm8WrtKtVzco0him7RlnB9jUMmL2",
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
  "image": "https://firebasestorage.googleapis.com/v0/b/travelmate-ee239.appspot.com/o/tripImage%2Fcarl-brave-gen-loc18.jpg?alt=media&token=5da84ba1-fffe-47b4-b02a-e8a4430b92cd",
  "maxPartecipant": 25,
  "name": "notti brave tour 2019",
  "tag":"musica",
  "vehicle":"auto",
  "owner":"RaHUVBSgljOzWvKNSGLl3QeSThh1",
  "startDate": "07/27/2019"
}' http://localhost:8095/trip/newTrip

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{
  "id":"5c8a254ac537bc12849274e1",
  "budget": 25,
  "departure": "roma",
  "description": "Il percorso espositivo, progettato da Fabio Fornasari, propone una chiave di lettura basata sui concetti di Personaggio, Storia e Mondo, tre elementi fondamentali per realizzare un grande film.",
  "destination": "roma",
  "endDate": "01/20/2019",
  "image": "https://firebasestorage.googleapis.com/v0/b/travelmate-ee239.appspot.com/o/tripImage%2FPixar%201-2.jpg?alt=media&token=71f4feb0-392d-43a5-b2c4-b4e063d11eff",
  "maxPartecipant": 5,
  "name": "Da ToyStory a Nemo: PIXAR",
  "tag":"cultura",
  "vehicle":"auto",
  "owner":"Tm8WrtKtVzco0him7RlnB9jUMmL2",
  "startDate": "01/20/2019"
}' http://localhost:8095/trip/newTrip

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{
  "id":"5c8a2726c537bc12849274e3",
  "budget": 35,
  "departure": "torino",
  "description": "La mostra presenta circa 60 immagini e fa parte del progetto History & Photography che ha per obiettivo raccontare la Storia con la Fotografia e la Storia della Fotografia",
  "destination": "milano",
  "endDate": "02/10/2019",
  "image": "https://firebasestorage.googleapis.com/v0/b/travelmate-ee239.appspot.com/o/tripImage%2FHuman-5.jpg?alt=media&token=9e1e1f4f-2935-4d16-a1cf-3f28db2cbe88",
  "maxPartecipant": 7,
  "name": "Human Rights: la storia dell’Onu",
  "tag":"cultura",
  "vehicle":"treno",
  "owner":"RaHUVBSgljOzWvKNSGLl3QeSThh1",
  "startDate": "02/10/2019"
}' http://localhost:8095/trip/newTrip

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{
  "id":"5c8a29fdc537bc12849274e9",
  "budget": 77,
  "departure": "venezia",
  "description": "Partenza ore 15:00 dal terminal di Venezia. Si arriva a Torino intorno alle 20:00. Dopo il concerto ripartiamo.",
  "destination": "torino",
  "endDate": "02/24/2019",
  "image": "https://firebasestorage.googleapis.com/v0/b/travelmate-ee239.appspot.com/o/tripImage%2Fnegramaro-torino-2018-633x400.jpg?alt=media&token=f95d5073-6712-4617-aca4-dbee1b4d6d53",
  "maxPartecipant": 8,
  "name": "Negramaro in concerto",
  "tag":"musica",
  "vehicle":"treno",
  "owner":"pfcytk2YLdb7qQ2a224J420vZB03",
  "startDate": "02/23/2019"
}' http://localhost:8095/trip/newTrip

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{
  "id":"5c8a2959c537bc12849274e5",
  "budget": 10,
  "departure": "rozzano",
  "description": "Mercoledì 13 marzo incontreremo Silvia Pizzi e Amedeo Bellodi, di H-FARM, per parlare di big data e social influencing",
  "destination": "milano",
  "endDate": "03/13/2019",
  "image": "https://firebasestorage.googleapis.com/v0/b/travelmate-ee239.appspot.com/o/tripImage%2Fhttps___cdn.evbuc.com_images_55975992_227675092115_1_original.jpeg?alt=media&token=ce452db5-c21a-4331-b9e1-019ec47563ee",
  "maxPartecipant": 4,
  "name": "Big data e social influencing",
  "tag":"informatica",
  "vehicle":"auto",
  "owner":"7hjchdyNdrO2itvdMRRj3b85wff2",
  "startDate": "03/13/2019"
}' http://localhost:8095/trip/newTrip

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{
  "id":"5c8a29c4c537bc12849274e7",
  "budget": 15,
  "departure": "rapallo",
  "description": "L Auditorium San Francesco di Chiavari ospiterà il concerto. Partenza ore 18:00 in piazza del censimento",
  "destination": "genova",
  "endDate": "02/02/2019",
  "image": "https://firebasestorage.googleapis.com/v0/b/travelmate-ee239.appspot.com/o/tripImage%2Fsax%20jazz-2.jpg?alt=media&token=bd7c6e90-6edf-4732-bad2-e270d93cc7fb",
  "maxPartecipant": 10,
  "name": "Winter Jazz #01",
  "tag":"musica",
  "vehicle":"auto",
  "owner":"kbz4sNCZtHMlfw3w3xCW3TTBco53",
  "startDate": "02/02/2019"
}' http://localhost:8095/trip/newTrip

echo -e ""

echo -e " ${reset}"

echo -e "Script end"





