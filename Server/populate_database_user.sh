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

curl -H "Content-Type: application/json" -X POST -d '{
  "id":"5c7ad36f56c9ff0d78213ef8",
  "surname": "Pirozzi",
  "name": "Pio",
  "birthday": "04/23/1995",
  "gender": "uomo",
  "relationship": "single",
  "email": "PioPirozzi@travelmate.com",
  "description":"Adoro la tecnologia, ",
  "avatar":"https://pbs.twimg.com/profile_images/378800000266689071/56738634b70e821b49150d13b92ffd73_400x400.jpeg",
  "cover":"https://images.pexels.com/photos/1146708/pexels-photo-1146708.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"
}' http://localhost:8095/user/newUser

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{
  "id":"5c7ad36f56c9ff0d78213ef9",
  "surname": "Longo",
  "name": "Fernanda",
  "birthday": "11/28/1996",
  "gender": "donna",
  "relationship": "fidanzata",
  "email": "FernandaLongo@travelmate.com",
  "description":"",
  "avatar":"https://dzentlmenis.lv/wp-content/uploads/2016/04/1-kopija3.jpg",
  "cover":"https://static2-viaggi.corriereobjects.it/wp-content/uploads/2019/01/1_Sri-Lanka-country-650x450.jpg"
}' http://localhost:8095/user/newUser

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{
  "id":"5c7ad36f56c9ff0d78213efa",
  "surname": "Milanesi",
  "name": "Alvaro",
  "birthday": "08/06/1998",
  "gender": "uomo",
  "relationship": "single",
  "email": "AlvaroMilanesi@travelmate.com",
  "description":"",
  "avatar":"https://i.pinimg.com/originals/79/08/16/7908162c8773e904cacf36cbea572096.jpg",
  "cover":"https://www.qbe.com/au/-/media/Australia/Images/travel-insurance/J9962travel%20claims750%20x550.png?h=550&la=en&w=750&hash=0CD78A3585E919F1F136263EBF36F282D751BFD7"
}' http://localhost:8095/user/newUser

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{
  "id":"5c7ad36f56c9ff0d78213efb",
  "surname": "Trentino",
  "name": "Cordelia",
  "birthday": "03/12/2000",
  "gender": "donna",
  "relationship": "fidanzata",
  "email": "TrentinoCordelia@travelmate.com",
  "description":"",
  "avatar":"http://higher-ed.us/wp-content/uploads/2017/12/girls-pictures-5-valuable-design-ideas-the-25-best-tumblr-on-pinterest-selfies.jpg",
  "cover":"https://cdn.pixabay.com/photo/2017/08/05/20/38/people-2585415_960_720.jpg"
}' http://localhost:8095/user/newUser

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{
  "id":"5c7ad36f56c9ff0d78213efc",
  "surname": "Asta",
  "name": "Andrea",
  "birthday": "08/21/1997",
  "gender": "uomo",
  "relationship": "fidanzato",
  "email": "AstaAndrea@travelmate.com",
  "description":"",
  "avatar":"https://scontent-fco1-1.xx.fbcdn.net/v/t1.0-9/21077532_1438053249612166_2564380775655528061_n.jpg?_nc_cat=101&_nc_ht=scontent-fco1-1.xx&oh=53d5b0db58052997a76a01c955c0b1fc&oe=5D26977F",
  "cover":"https://www.corriere.it/methode_image/2014/04/01/Tecnologia/Foto%20Gallery/I%27iconica%20collina%20Windows_MGTHUMB-INTERNA.jpg"
}' http://localhost:8095/user/newUser

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{
  "id":"5c7ad36f56c9ff0d78213efd",
  "surname": "Marcelo",
  "name": "Teresa",
  "birthday": "01/16/2001",
  "gender": "donna",
  "relationship": "single",
  "email": "MarceloTeresa@travelmate.com",
  "description":"",
  "avatar":"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTWqfOeHoDA9hwJvRWDGGhxD4oCO9E6ZdxY_OM9PjXg9SaZusPA",
  "cover":"https://cdn.muenchen-p.de/.imaging/stk/responsive/image980/dms/va-2017/tollwood-2017/teaser/tollwood-2017-hp-01/document/tollwood-2017-hp-01.jpg"
}' http://localhost:8095/user/newUser

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{
  "id":"5c7ad36f56c9ff0d78213efe",
  "surname": "Nestore",
  "name": "Bruno",
  "birthday": "02/08/1999",
  "gender": "uomo",
  "relationship": "fidanzato",
  "email": "NestoreBruno@travelmate.com",
  "description":"",
  "avatar":"https://hairstyleonpoint.com/wp-content/uploads/2014/11/tumblr_na261xr0Qg1rwya4go1_1280.jpg",
  "cover":"https://blogmedia.evbstatic.com/wp-content/uploads/wpmulti/sites/3/2017/06/29103043/event-distribution.png"
}' http://localhost:8095/user/newUser

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{
  "id":"5c7ad36f56c9ff0d78213eff",
  "surname": "Fiorentini",
  "name": "Daphne",
  "birthday": "11/20/2000",
  "gender": "donna",
  "relationship": "single",
  "email": "FiorentiniDaphne@travelmate.com",
  "description":"",
  "avatar":"https://i.ytimg.com/vi/hYvkSHYh_WQ/hqdefault.jpg",
  "cover":"http://backpackertours.co.uk/images/September-Oktoberfest-Munich-Germany-2.jpg"
}' http://localhost:8095/user/newUser

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{
  "id":"5c7ad36f56c9ff0d78213f00",
  "surname": "Romani",
  "name": "Diego",
  "birthday": "06/08/1993",
  "gender": "uomo",
  "relationship": "single",
  "email": "RomaniDiego@travelmate.com",
  "description":"",
  "avatar":"https://www.menshairstyletrends.com/wp-content/uploads/2018/05/russ_thebarber-classic-short-haircut-for-guys.jpg",
  "cover":"https://images.pexels.com/photos/414105/pexels-photo-414105.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"
}' http://localhost:8095/user/newUser

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{
  "id":"5c7ad36f56c9ff0d78213f01",
  "surname": "Siciliani",
  "name": "Frediana",
  "birthday": "09/28/1994",
  "gender": "donna",
  "relationship": "fidanzata",
  "email": "SicilianiFrediana@travelmate.com",
  "description":"",
  "avatar":"https://edecorati.com/data/out/215/girls-images_292560625.jpg",
  "cover":"http://www.eventelephant.com/wp-content/uploads/2019/01/What-Makes-Xsaga-Different.jpg"
}' http://localhost:8095/user/newUser

echo -e " ${reset}"

echo -e "Script end"





