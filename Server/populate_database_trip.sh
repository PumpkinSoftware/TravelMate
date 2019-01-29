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

curl -H "Content-Type: application/json" -X POST -d '{"name":"Road To Milan","description":"best travel to milan","departure":"rome","destination":"milan","budget":"123.50","startDate":"12/12/2018","endDate":"01/01/2019","pets":"true","maxPartecipant":"10"}' http://localhost:8095/trip/newTrip

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{"name":"bourem is bourem","description":"who is bourem","departure":"milan","destination":"bourem","budget":"450.20","startDate":"10/12/2018","endDate":"10/24/2018","pets":"false","maxPartecipant":"9"}' http://localhost:8095/trip/newTrip

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{"name":"game game game","description":"lv by night","departure":"new york","destination":"las vegas","budget":"1238.13","startDate":"02/15/2019","endDate":"02/20/2019","pets":"true","maxPartecipant":"50"}' http://localhost:8095/trip/newTrip

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{"name":"brazil, i want you!","description":"amazing brazil","departure":"bari","destination":"fortaleza","budget":"543.50","startDate":"01/01/2019","endDate":"01/20/2019","pets":"false","maxPartecipant":"2"}' http://localhost:8095/trip/newTrip

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{"name":"tea time","description":"london everywhere","departure":"paris","destination":"london","budget":"234.00","startDate":"02/01/2019","endDate":"02/10/2019","pets":"true","maxPartecipant":"10"}' http://localhost:8095/trip/newTrip

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{"name":"California, i love you","description":"california in 360°","departure":"rome","destination":"san francisco","budget":"1543.50","startDate":"01/15/2019","endDate":"01/20/2019","pets":"true","maxPartecipant":"10"}' http://localhost:8095/trip/newTrip

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{"name":"lourdes, we are coming!","description":"god is with us","departure":"naples","destination":"lourdes","budget":"143.50","startDate":"01/23/2019","endDate":"01/27/2019","pets":"false","maxPartecipant":"20"}' http://localhost:8095/trip/newTrip

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{"name":"pakistan, seriously?","description":"i do no why","departure":"milan","destination":"karachi","budget":"2343.50","startDate":"04/30/2019","endDate":"05/10/2019","pets":"true","maxPartecipant":"7"}' http://localhost:8095/trip/newTrip

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{"description": "Quis amet eiusmod dolore nostrud officia duis ut occaecat dolor id fugiat proident. Minim est eiusmod labore excepteur aliqua proident ut tempor occaecat. Dolor sunt exercitation consectetur nisi proident laborum voluptate qui fugiat dolor ad nostrud mollit veniam.","departure": "Maxville","destination": "Gila","budget": 504.8,"startDate": "12/14/2018","endDate": "01/01/2019","pets": false,"maxPartecipant": 91,"name": "Gila,i love it!"}' http://localhost:8095/trip/newTrip

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{"description": "Dolor proident nostrud qui pariatur minim irure fugiat exercitation irure id dolore ipsum ut. Velit magna occaecat pariatur nisi sint culpa aliqua magna. Eu exercitation laborum ex do velit aliqua do ex elit quis qui. Occaecat eiusmod magna elit irure excepteur Lorem proident ex incididunt est incididunt excepteur. Lorem proident minim qui voluptate. Veniam esse Lorem voluptate proident consequat reprehenderit commodo non ullamco dolore.","departure": "Cotopaxi","destination": "Grahamtown","budget": 548.46,"startDate": "12/06/2018","endDate": "01/10/2019","pets": true,"maxPartecipant": 35,"name": "Grahamtown,i love it!"}' http://localhost:8095/trip/newTrip

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{"description": "Dolore exercitation sit culpa excepteur eiusmod eiusmod exercitation. Id et laboris qui elit eiusmod sint dolore exercitation in commodo amet. Culpa sit anim do ipsum laborum officia adipisicing ullamco labore non velit eu aliqua minim.","departure": "Movico","destination": "Hasty","budget": 550.42,"startDate": "12/30/2018","endDate": "01/10/2019","pets": true,"maxPartecipant": 33,"name": "Hasty,xoxo"}' http://localhost:8095/trip/newTrip

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{"description": "Minim dolore Lorem anim consectetur amet officia. Ea magna minim duis velit ipsum eiusmod cupidatat esse sit voluptate fugiat. Officia proident nulla sunt laboris cillum tempor officia nisi consectetur aliqua in proident dolore officia. Nulla consequat Lorem fugiat labore excepteur quis ullamco excepteur ea adipisicing.","departure": "Dowling","destination": "Eden","budget": 785.66,"startDate": "12/06/2018","endDate": "01/10/2019","pets": true,"maxPartecipant": 15,"name": "Eden,let s go!"}' http://localhost:8095/trip/newTrip

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{"description": "In ea anim nisi minim nulla velit sunt velit qui amet sunt nisi. Lorem voluptate nulla voluptate consectetur esse enim do proident duis fugiat quis et qui deserunt. Esse aliquip veniam eiusmod pariatur. Ea deserunt aute duis nulla fugiat Lorem laborum dolore amet quis in nisi nulla. Laboris dolore esse nulla pariatur ad eu cillum labore nostrud velit.","departure": "Boomer","destination": "Lund","budget": 768.83,"startDate": "12/30/2018","endDate": "01/01/2019","pets": false,"maxPartecipant": 28,"name": "Lund,let s go!"}' http://localhost:8095/trip/newTrip

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{"description": "Ea ad ut duis ex ullamco anim non occaecat non in nisi velit. Nostrud do enim exercitation proident laboris esse mollit aliquip. Duis in duis reprehenderit labore commodo ad culpa culpa magna cupidatat mollit. Fugiat incididunt officia exercitation fugiat nostrud sit in irure reprehenderit officia. Adipisicing sunt duis magna laborum consectetur anim reprehenderit deserunt veniam ut magna aliqua elit. Non nostrud consequat aliquip irure adipisicing deserunt nulla labore eu.","departure": "Mahtowa","destination": "Oneida","budget": 422.95,"startDate": "12/07/2018","endDate": "01/20/2019","pets": false,"maxPartecipant": 97,"name": "Oneida,i love it!"}' http://localhost:8095/trip/newTrip

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{"description": "Fugiat cillum magna amet pariatur sit exercitation esse labore. Eu consequat velit magna ipsum eu. Consectetur magna id eu minim esse minim aute. In veniam ipsum do tempor sint laborum cillum esse. In labore eu consequat dolor culpa laboris. Magna ipsum voluptate elit nostrud amet officia. Ad qui fugiat ut amet dolore nulla.","departure": "Gilmore","destination": "Leming","budget": 804.79,"startDate": "12/30/2018","endDate": "01/20/2019","pets": false,"maxPartecipant": 3,"name": "Leming,let s go!"}' http://localhost:8095/trip/newTrip

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{"description": "Do sunt consectetur qui ullamco. Nisi labore in aliqua ad eu duis tempor nisi esse aliquip ullamco sunt adipisicing. Ex ad nulla deserunt deserunt laborum laborum et ea cillum. Lorem excepteur laboris ex amet. Tempor laboris proident amet ullamco officia eiusmod veniam ad ea mollit. Qui incididunt nulla consectetur nisi voluptate fugiat consequat cupidatat officia velit ipsum mollit incididunt.","departure": "Omar","destination": "Dotsero","budget": 372.51,"startDate": "12/06/2018","endDate": "01/01/2019","pets": false,"maxPartecipant": 50,"name": "Dotsero,xoxo"}' http://localhost:8095/trip/newTrip

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{"description": "Duis et consectetur id quis laborum. Reprehenderit in duis proident exercitation enim fugiat aute non amet magna pariatur. Tempor deserunt deserunt ullamco elit et in nostrud ex ea et ullamco dolore culpa. Adipisicing laboris esse ullamco cillum adipisicing minim do labore occaecat veniam aute tempor incididunt nulla.","departure": "Twilight","destination": "Montura","budget": 960.4,"startDate": "12/14/2018","endDate": "01/02/2019","pets": true,"maxPartecipant": 19,"name": "Montura,xoxo"}' http://localhost:8095/trip/newTrip
  
echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{"description": "Eiusmod qui qui aute proident aliquip. Aliqua anim ea elit dolor proident tempor sint do sunt pariatur ex excepteur minim commodo. Elit minim minim esse quis consectetur non duis ea et laborum.","departure": "Eagleville","destination": "Freetown","budget": 804.42,"startDate": "12/14/2018","endDate": "01/20/2019","pets": true,"maxPartecipant": 71,"name": "Freetown,i love it!"}' http://localhost:8095/trip/newTrip

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{"description": "Nulla excepteur sint nulla cupidatat officia ex. Proident laborum ipsum mollit veniam dolore laborum. Consequat incididunt aliqua et Lorem id velit Lorem cupidatat aute mollit magna magna. Est enim aliqua dolor commodo ut sint est in deserunt enim nisi dolore reprehenderit. Aliqua consectetur ad labore non non est esse ad aute incididunt. Labore irure elit commodo sunt minim nisi sunt nostrud aliquip.","departure": "Trona","destination": "Newry","budget": 381.65,"startDate": "12/06/2018","endDate": "01/02/2019","pets": true,"maxPartecipant": 86,"name": "Newry,xoxo"}' http://localhost:8095/trip/newTrip

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{"description": "Cupidatat id non et non nulla fugiat do et ea. Aliqua incididunt irure dolor tempor nulla Lorem consectetur ex nostrud nostrud id eu magna. Labore voluptate excepteur nostrud enim ipsum nisi fugiat sunt aliqua. Labore ipsum aute reprehenderit tempor cupidatat et non labore culpa ea cillum irure nostrud nulla. Do adipisicing consequat dolore ad dolore aliquip.","departure": "Hilltop","destination": "Manila","budget": 140.61,"startDate": "12/06/2018","endDate": "01/01/2019","pets": false,"maxPartecipant": 51,"name": "Manila,let s go!"}' http://localhost:8095/trip/newTrip

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{"description": "Aliquip dolore non deserunt amet esse consectetur eu sunt ex duis qui. Ullamco aliquip eiusmod laborum eu sit do laboris. Aliqua do ullamco irure do minim deserunt non id aliquip cupidatat deserunt Lorem reprehenderit qui.","departure": "Balm","destination": "Sharon","budget": 480.9,"startDate": "12/14/2018","endDate": "01/02/2019","pets": true,"maxPartecipant": 63,"name": "Sharon,let s go!"}' http://localhost:8095/trip/newTrip

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{"description": "Reprehenderit ex labore fugiat cupidatat. Adipisicing voluptate magna ad magna nulla exercitation cupidatat. Ut adipisicing mollit Lorem commodo nostrud elit deserunt do labore ea. Sunt irure ex adipisicing veniam do consequat est sit ut veniam excepteur quis irure. Aliqua nulla officia irure commodo deserunt sunt velit anim dolor veniam labore.","departure": "Innsbrook","destination": "Jennings","budget": 308.17,"startDate": "12/14/2018","endDate": "01/10/2019","pets": true,"maxPartecipant": 45,"name": "Jennings,i love it!"}' http://localhost:8095/trip/newTrip


echo -e " ${reset}"

echo -e "Script end"





