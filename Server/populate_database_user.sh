#!/bin/bash

#Use this script to populate database of users for testing

#Comandi di default per modificare colore shell
red=`tput setaf 1`
yellow=`tput setaf 3`
green=`tput setaf 2`
reset=`tput sgr0`

echo -e ""

#---------------------------------------------------------------

#Starting to populate database of users

echo -e "Starting to populate database of users"

echo -e "${green}"

curl -H "Content-Type: application/json" -X POST -d '{"name":"Caio","surname":"Rossi","age":"20","gender":"Male","birthday":"01/12/1999","relationship":"single", "email":"ciao1@gmail.com", "description":"aaaaaaaaaaaa"}' http://localhost:8095/user/newUser

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{"name":"Sempronio","surname":"Beta","age":"21","gender":"Male","birthday":"01/12/1998","relationship":"single", "email":"ciao2@gmail.com", "description":"bbbbbbbbbb"}' http://localhost:8095/user/newUser

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{"name":"Adamo","surname":"Filida","age":"22","gender":"Male","birthday":"01/12/1997","relationship":"married", "email":"ciao3@gmail.com", "description":"ccccccccccc"}' http://localhost:8095/user/newUser

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{"name":"Sofia","surname":"Rossi","age":"23","gender":"Female","birthday":"01/12/1996","relationship":"married", "email":"ciao4@gmail.com", "description":"dddddddddd"}' http://localhost:8095/user/newUser

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{"name":"Ginevra","surname":"Mela","age":"24","gender":"Female","birthday":"01/12/1995","relationship":"single", "email":"ciao5@gmail.com", "description":"eeeeeeeeee"}' http://localhost:8095/user/newUser

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{"name":"Steve","surname":"Peroni","age":"22","gender":"Male","birthday":"15/02/1997","relationship":"single", "email":"ciao6@gmail.com", "description":"ffffffffff"}' http://localhost:8095/user/newUser

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{"name":"Abdul","surname":"Perone","age":"23","gender":"Male","birthday":"15/02/1996","relationship":"married", "email":"ciao7@gmail.com", "description":"gggggggg"}' http://localhost:8095/user/newUser

echo -e " ${reset}"

echo -e "Script end"





