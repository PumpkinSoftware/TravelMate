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

curl -H "Content-Type: application/json" -X POST -d '{"name":"Caio","surname":"Rossi","nickname":"cai_ros","age":"20","gender":"Male","birthday":"01/12/1999","relationship":"single", "email":"ciao@gmail.com"}' http://localhost:8095/user/new_user

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{"name":"Sempronio","surname":"Beta","nickname":"sem_bet","age":"21","gender":"Male","birthday":"01/12/1998","relationship":"single", "email":"ciao@gmail.com"}' http://localhost:8095/user/new_user

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{"name":"Adamo","surname":"Filida","nickname":"ada_fil","age":"22","gender":"Male","birthday":"01/12/1997","relationship":"married", "email":"ciao@gmail.com"}' http://localhost:8095/user/new_user

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{"name":"Sofia","surname":"Rossi","nickname":"sof_ros","age":"23","gender":"Female","birthday":"01/12/1996","relationship":"married", "email":"ciao@gmail.com"}' http://localhost:8095/user/new_user

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{"name":"Ginevra","surname":"Mela","nickname":"gin_mel","age":"24","gender":"Female","birthday":"01/12/1995","relationship":"single", "email":"ciao@gmail.com"}' http://localhost:8095/user/new_user


echo -e " ${reset}"

echo -e "Script end"





