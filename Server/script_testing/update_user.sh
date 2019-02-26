#!/bin/bash

#Use this script to update users for testing

#Comandi di default per modificare colore shell
red=`tput setaf 1`
yellow=`tput setaf 3`
green=`tput setaf 2`
reset=`tput sgr0`

echo -e ""

#---------------------------------------------------------------

#Starting to update some users

echo -e "Starting to update a trip"

echo -e "${green}"
                                                            
curl -H "Content-Type: application/json" -X POST -d '{"userId":"5c704e965057c01c27460c70", "name":"Caione", "age":21}' http://localhost:8095/user/updateUser

echo -e ""
echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{"userId":"5c704e965057c01c27460c71", "surname":"betaB"}' http://localhost:8095/user/updateUser

echo -e ""
echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{"userId":"5c704e965057c01c27460c73", "relationship":"Single", "sumReview":1}' http://localhost:8095/user/updateUser



echo -e " ${reset}"

echo -e "Script end" 
