#!/bin/bash

#Comandi di default per modificare colore shell
red=`tput setaf 1`
yellow=`tput setaf 3`
green=`tput setaf 2`
reset=`tput sgr0`

#Caricamento degli user
bash populate_database_user.sh

#Caricamento dei trip
bash populate_database_trip.sh

#Caricamento dei partecipanti

echo -e "Starting to populate trips with partecipants"

echo -e "${green}"

#Viaggio 1 IdViaggio:5c79b80f4a8b77336e1a332a IdCreatore:5c7ad36f56c9ff0d78213ef8 maxPartecipant:25
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b80f4a8b77336e1a332a","userId":"5c7ad36f56c9ff0d78213efb"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b80f4a8b77336e1a332a","userId":"5c7ad36f56c9ff0d78213efc"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b80f4a8b77336e1a332a","userId":"5c7ad36f56c9ff0d78213efd"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b80f4a8b77336e1a332a","userId":"5c7ad36f56c9ff0d78213efe"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b80f4a8b77336e1a332a","userId":"5c7ad36f56c9ff0d78213eff"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b80f4a8b77336e1a332a","userId":"5c7ad36f56c9ff0d78213f00"}' http://localhost:8095/user/addTrip
echo -e ""

#Viaggio 2 IdViaggio:5c79b80e4a8b77336e1a3329 IdCreatore:5c7ad36f56c9ff0d78213ef9 maxPartecipant:10
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b80e4a8b77336e1a3329","userId":"5c7ad36f56c9ff0d78213efe"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b80e4a8b77336e1a3329","userId":"5c7ad36f56c9ff0d78213f01"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b80e4a8b77336e1a3329","userId":"5c7ad36f56c9ff0d78213efb"}' http://localhost:8095/user/addTrip
echo -e ""

#Viaggio 3 IdViaggio:5c79b7e04a8b77336e1a3328 IdCreatore:5c7ad36f56c9ff0d78213efa maxPartecipant:15
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b7e04a8b77336e1a3328","userId":"5c7ad36f56c9ff0d78213ef8"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b7e04a8b77336e1a3328","userId":"5c7ad36f56c9ff0d78213ef9"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b7e04a8b77336e1a3328","userId":"5c7ad36f56c9ff0d78213f00"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b7e04a8b77336e1a3328","userId":"5c7ad36f56c9ff0d78213efe"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b7e04a8b77336e1a3328","userId":"5c7ad36f56c9ff0d78213efc"}' http://localhost:8095/user/addTrip
echo -e ""

#Viaggio 4 IdViaggio:5c79b7e04a8b77336e1a3327 IdCreatore:5c7ad36f56c9ff0d78213efb maxPartecipant:25
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b7e04a8b77336e1a3327","userId":"5c7ad36f56c9ff0d78213efa"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b7e04a8b77336e1a3327","userId":"5c7ad36f56c9ff0d78213ef9"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b7e04a8b77336e1a3327","userId":"5c7ad36f56c9ff0d78213f01"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b7e04a8b77336e1a3327","userId":"5c7ad36f56c9ff0d78213f00"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b7e04a8b77336e1a3327","userId":"5c7ad36f56c9ff0d78213eff"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b7e04a8b77336e1a3327","userId":"5c7ad36f56c9ff0d78213efe"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b7e04a8b77336e1a3327","userId":"5c7ad36f56c9ff0d78213efd"}' http://localhost:8095/user/addTrip
echo -e ""

#Viaggio 5 IdViaggio:5c79b7e04a8b77336e1a3326 IdCreatore:5c7ad36f56c9ff0d78213efc maxPartecipant:16
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b7e04a8b77336e1a3326","userId":"5c7ad36f56c9ff0d78213f00"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b7e04a8b77336e1a3326","userId":"5c7ad36f56c9ff0d78213efe"}' http://localhost:8095/user/addTrip
echo -e ""

#Viaggio 6 IdViaggio:5c79b7e04a8b77336e1a3325 IdCreatore:5c7ad36f56c9ff0d78213efd maxPartecipant:4
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b7e04a8b77336e1a3325","userId":"5c7ad36f56c9ff0d78213efa"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b7e04a8b77336e1a3325","userId":"5c7ad36f56c9ff0d78213ef9"}' http://localhost:8095/user/addTrip
echo -e ""

#Viaggio 7 IdViaggio:5c79b7e04a8b77336e1a3324 IdCreatore:5c7ad36f56c9ff0d78213efe maxPartecipant:15
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b7e04a8b77336e1a3324","userId":"5c7ad36f56c9ff0d78213eff"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b7e04a8b77336e1a3324","userId":"5c7ad36f56c9ff0d78213ef8"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b7e04a8b77336e1a3324","userId":"5c7ad36f56c9ff0d78213ef9"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b7e04a8b77336e1a3324","userId":"5c7ad36f56c9ff0d78213efc"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b7e04a8b77336e1a3324","userId":"5c7ad36f56c9ff0d78213efa"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b7e04a8b77336e1a3324","userId":"5c7ad36f56c9ff0d78213f01"}' http://localhost:8095/user/addTrip
echo -e ""

#Viaggio 8 IdViaggio:5c79b7e04a8b77336e1a3323 IdCreatore:5c7ad36f56c9ff0d78213eff maxPartecipant:25
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b7e04a8b77336e1a3323","userId":"5c7ad36f56c9ff0d78213f00"}' http://localhost:8095/user/addTrip
echo -e ""

#Viaggio 9 IdViaggio:5c79b7e04a8b77336e1a3322 IdCreatore:5c7ad36f56c9ff0d78213f00 maxPartecipant:17
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b7e04a8b77336e1a3322","userId":"5c7ad36f56c9ff0d78213eff"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b7e04a8b77336e1a3322","userId":"5c7ad36f56c9ff0d78213efa"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b7e04a8b77336e1a3322","userId":"5c7ad36f56c9ff0d78213efd"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b7e04a8b77336e1a3322","userId":"5c7ad36f56c9ff0d78213ef9"}' http://localhost:8095/user/addTrip
echo -e ""

#Viaggio 10 IdViaggio:5c79b7e04a8b77336e1a3321 IdCreatore:5c7ad36f56c9ff0d78213f01 maxPartecipant:4
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b7e04a8b77336e1a3321","userId":"5c7ad36f56c9ff0d78213efb"}' http://localhost:8095/user/addTrip
echo -e ""

#Viaggio 11 IdViaggio:5c79b7e04a8b77336e1a3320 IdCreatore:5c7ad36f56c9ff0d78213ef8 maxPartecipant:7
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b7e04a8b77336e1a3320","userId":"5c7ad36f56c9ff0d78213ef9"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b7e04a8b77336e1a3320","userId":"5c7ad36f56c9ff0d78213efe"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b7e04a8b77336e1a3320","userId":"5c7ad36f56c9ff0d78213f00"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b7e04a8b77336e1a3320","userId":"5c7ad36f56c9ff0d78213efc"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b7e04a8b77336e1a3320","userId":"5c7ad36f56c9ff0d78213f01"}' http://localhost:8095/user/addTrip
echo -e ""

#Viaggio 12 IdViaggio:5c79b7e04a8b77336e1a331f IdCreatore:5c7ad36f56c9ff0d78213ef9 maxPartecipant:25
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b7e04a8b77336e1a331f","userId":"5c7ad36f56c9ff0d78213efa"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b7e04a8b77336e1a331f","userId":"5c7ad36f56c9ff0d78213f00"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b7e04a8b77336e1a331f","userId":"5c7ad36f56c9ff0d78213efd"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b7e04a8b77336e1a331f","userId":"5c7ad36f56c9ff0d78213eff"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b7e04a8b77336e1a331f","userId":"5c7ad36f56c9ff0d78213f01"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b7e04a8b77336e1a331f","userId":"5c7ad36f56c9ff0d78213efb"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b7e04a8b77336e1a331f","userId":"5c7ad36f56c9ff0d78213efc"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b7e04a8b77336e1a331f","userId":"5c7ad36f56c9ff0d78213efe"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b7e04a8b77336e1a331f","userId":"5c7ad36f56c9ff0d78213ef8"}' http://localhost:8095/user/addTrip
echo -e ""

echo -e " ${reset}"

echo -e "Script end"
