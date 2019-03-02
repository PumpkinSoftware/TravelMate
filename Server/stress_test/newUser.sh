#!/bin/bash

# test for /user/newUser

#Comandi di default per modificare colore shell
red=`tput setaf 1`
yellow=`tput setaf 3`
green=`tput setaf 2`
reset=`tput sgr0`

echo -e ""

#---------------------------------------------------------------
#Test One

echo -e "Test One: localhost:8095/user/newUser \n"
 
echo -e "${yellow}Result:"

curl -H "Content-Type: application/json" -X POST -d '{
  "surname": "Rossi",
  "name": "Mario",
  "birthday": "01/20/1995",
  "gender": "uomo",
  "relationship": "single",
  "email": "mario.rossi@travelmate.com",
  "description":"Solare, armonioso... Il mio unico credo è andare al bagno",
  "avatar":"http://1.bp.blogspot.com/-RGFpvv4SnoM/TeSX1OlaCQI/AAAAAAAAARE/2Ktw5E-vOmU/s1600/steven_strait_1216948500.jpg",
  "cover":"https://cdn.pixabay.com/photo/2016/04/15/04/02/water-1330252__340.jpg"
}' http://localhost:8095/user/newUser

echo -e " ${reset}"
echo -e "${green}Expected:"
echo -e "{'status':'success','message':'User mario created!'} ${reset}"

echo -e "-------------------------------------\n"

#---------------------------------------------------------------
#Test Two

echo -e "Test Two: localhost:8095/user/newUser \n"
 
echo -e "${yellow}Result:"

curl -H "Content-Type: application/json" -X POST -d '{
  "surname": "Del Conte",
  "name": "Laura",
  "birthday": "03/19/2000",
  "gender": "donna",
  "relationship": "fidanzata",
  "email": "mario.rossi@travelmate.com",
  "description":"Sono una ragazza difficile da gestire, ma posso assicurarti che il divertimento è assicurato!",
  "avatar":"https://freedesignfile.com/upload/2017/01/A-young-female-student-sitting-at-the-desk-HD-picture.jpg",
  "cover":"https://www.google.com/url?sa=i&source=images&cd=&ved=2ahUKEwiZx6muxN7gAhVH26QKHaDOBU8QjRx6BAgBEAU&url=https%3A%2F%2Fwww.pexels.com%2Fsearch%2Fbackground%2F&psig=AOvVaw275erqyzWlOVNVdkCNyWnw&ust=1551446908316018"
}' http://localhost:8095/user/newUser

echo -e " ${reset}"
echo -e "${green}Expected:"
echo -e "{'status':'error','type':'-10'} ${reset}"

echo -e "-------------------------------------\n"

#---------------------------------------------------------------
#Test Three

echo -e "Test Three: localhost:8095/user/newUser \n"
 
echo -e "${yellow}Result:"

curl -H "Content-Type: application/json" -X POST -d '{
  "surname": "De Giacomo",
  "name": "Luca",
  "birthday": "02/06/1999",
  "gender": "uomo",
  "relationship": "single",
  "email": "degiacomo.luca12@travelmate.com",
  "description":"Sono un ragazzo simpatico, a volte anche socievole. Fatemi arrabbiare e ... niente.",
  "avatar":"http://www.machovibes.com/wp-content/uploads/2018/03/Best-Selfie-Poses-for-Guys-to-Look-Charming-2.jpg"
}' http://localhost:8095/user/newUser

echo -e " ${reset}"
echo -e "${green}Expected:"
echo -e "{'status':'success','message':'User luca created!'} ${reset}"
echo -e "-------------------------------------\n"

#---------------------------------------------------------------


