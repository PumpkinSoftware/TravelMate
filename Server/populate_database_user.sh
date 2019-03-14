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
  "uid":"Tm8WrtKtVzco0him7RlnB9jUMmL2",
  "gender": "Uomo",
  "relationship": "Single",
  "email": "PioPirozzi@travelmate.com",
  "description":"Adoro la tecnologia, sono socievole e simpatico",
  "avatar":"https://firebasestorage.googleapis.com/v0/b/travelmate-ee239.appspot.com/o/userImage%2FPioPirozzi%40travelmate.com%2F56738634b70e821b49150d13b92ffd73_400x400.jpeg?alt=media&token=3bf14bec-891b-4210-b057-9a1109131860",
  "cover":"https://firebasestorage.googleapis.com/v0/b/travelmate-ee239.appspot.com/o/userImage%2FPioPirozzi%40travelmate.com%2Fpexels-photo-1146708.jpeg?alt=media&token=8d20fe7f-a0d2-4f2d-be05-2309f055d7d8"
}' http://localhost:8095/user/newUser

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{
  "id":"5c7ad36f56c9ff0d78213ef9",
  "surname": "Longo",
  "name": "Fernanda",
  "birthday": "11/28/1996",
  "gender": "Donna",
  "uid":"RaHUVBSgljOzWvKNSGLl3QeSThh1",
  "relationship": "Fidanzata",
  "email": "FernandaLongo@travelmate.com",
  "description":"Se vuoi farmi felice, portami a mangiare",
  "avatar":"https://firebasestorage.googleapis.com/v0/b/travelmate-ee239.appspot.com/o/userImage%2FFernandaLongo%40travelmate.com%2F1-kopija3.jpg?alt=media&token=c858a12c-83a0-42e1-a0a9-99ffd444118c",
  "cover":"https://firebasestorage.googleapis.com/v0/b/travelmate-ee239.appspot.com/o/userImage%2FFernandaLongo%40travelmate.com%2F1_Sri-Lanka-country-650x450.jpg?alt=media&token=1fd8971b-eefb-48b8-bf90-0f05170d639c"
}' http://localhost:8095/user/newUser

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{
  "id":"5c7ad36f56c9ff0d78213efa",
  "surname": "Milanesi",
  "name": "Alvaro",
  "birthday": "08/06/1998",
  "gender": "Uomo",
  "uid":"kbz4sNCZtHMlfw3w3xCW3TTBco53",
  "relationship": "Single",
  "email": "AlvaroMilanesi@travelmate.com",
  "description":"Adoro la musica metallara, non mi stanco mai di camminare e mangio kebaba",
  "avatar":"https://firebasestorage.googleapis.com/v0/b/travelmate-ee239.appspot.com/o/userImage%2FAlvaroMilanesi%40travelmate.com%2F7908162c8773e904cacf36cbea572096.jpg?alt=media&token=ae2384d5-1a4c-4288-81ee-4d66661abd46",
  "cover":"https://firebasestorage.googleapis.com/v0/b/travelmate-ee239.appspot.com/o/userImage%2FAlvaroMilanesi%40travelmate.com%2FJ9962travel%20claims750%20x550.png?alt=media&token=8b80554d-596d-4c45-a127-998c49803f64"
}' http://localhost:8095/user/newUser

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{
  "id":"5c7ad36f56c9ff0d78213efb",
  "surname": "Trentino",
  "name": "Cordelia",
  "birthday": "03/12/2000",
  "gender": "Donna",
  "uid":"pIfUG1Qpmwasqrdu77d9nake9HM2",
  "relationship": "Fidanzata",
  "email": "TrentinoCordelia@travelmate.com",
  "description":"Cliente abituale del Mc Donald... Se viaggi con me sei obbligato a venire",
  "avatar":"https://firebasestorage.googleapis.com/v0/b/travelmate-ee239.appspot.com/o/userImage%2FTrentinoCordelia%40travelmate.com%2Fgirls-pictures-5-valuable-design-ideas-the-25-best-tumblr-on-pinterest-selfies.jpg?alt=media&token=3fca1864-40ea-4bb8-82ac-a66c4cffed5b",
  "cover":"https://firebasestorage.googleapis.com/v0/b/travelmate-ee239.appspot.com/o/userImage%2FTrentinoCordelia%40travelmate.com%2Fpeople-2585415_960_720.jpg?alt=media&token=359cae42-a9fa-4bf2-92ae-07ab0b00eaf9"
}' http://localhost:8095/user/newUser

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{
  "id":"5c7ad36f56c9ff0d78213efc",
  "surname": "Asta",
  "name": "Andrea",
  "birthday": "08/21/1997",
  "gender": "Uomo",
  "uid":"7hjchdyNdrO2itvdMRRj3b85wff2",
  "relationship": "Fidanzato",
  "email": "AstaAndrea@travelmate.com",
  "description":"There is no place like 127.0.0.1",
  "avatar":"https://firebasestorage.googleapis.com/v0/b/travelmate-ee239.appspot.com/o/userImage%2FAstaAndrea%40travelmate.com%2F21077532_1438053249612166_2564380775655528061_n.jpg?alt=media&token=03da483a-0c8e-4dac-966f-30c05608ad6d",
  "cover":"https://firebasestorage.googleapis.com/v0/b/travelmate-ee239.appspot.com/o/userImage%2FAstaAndrea%40travelmate.com%2Fcollina%20Windows_MGTHUMB-INTERNA.jpg?alt=media&token=62826b16-efae-439d-a85a-2afbabf81b48"
}' http://localhost:8095/user/newUser

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{
  "id":"5c7ad36f56c9ff0d78213efd",
  "surname": "Marcelo",
  "name": "Teresa",
  "birthday": "01/16/2001",
  "gender": "Donna",
  "uid":"Ip5ooeVpugPGm6I0SY5Y2TUNIki2",
  "relationship": "Single",
  "email": "MarceloTeresa@travelmate.com",
  "description":"Tecnologia, tecnologia, tecnologia... L ho già detto tecnologia?",
  "avatar":"https://firebasestorage.googleapis.com/v0/b/travelmate-ee239.appspot.com/o/userImage%2FMarceloTeresa%40travelmate.com%2Fimages.jpeg?alt=media&token=cd751310-3b18-4c29-b10e-90cd153d4176",
  "cover":"https://firebasestorage.googleapis.com/v0/b/travelmate-ee239.appspot.com/o/userImage%2FMarceloTeresa%40travelmate.com%2Ftollwood-2017-hp-01.jpg?alt=media&token=ded8aa4e-e6b8-42c0-9e4c-b78945145e82"
}' http://localhost:8095/user/newUser

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{
  "id":"5c7ad36f56c9ff0d78213efe",
  "surname": "Nestore",
  "name": "Bruno",
  "birthday": "02/08/1999",
  "gender": "Uomo",
  "relationship": "Fidanzato",
  "uid":"GutGWyaTg9OdhjecP1wzd35e0l83",
  "email": "NestoreBruno@travelmate.com",
  "description":"Assiduo frequentatore di discoteche... Vieni con me e non smetterai mai di ballare",
  "avatar":"https://firebasestorage.googleapis.com/v0/b/travelmate-ee239.appspot.com/o/userImage%2FNestoreBruno%40travelmate.com%2Ftumblr_na261xr0Qg1rwya4go1_1280.jpg?alt=media&token=83be07f1-44ce-4bf5-9f81-9daaa39e80b1",
  "cover":"https://firebasestorage.googleapis.com/v0/b/travelmate-ee239.appspot.com/o/userImage%2FNestoreBruno%40travelmate.com%2Fevent-distribution.png?alt=media&token=7fa1eb10-2e98-4b55-aed7-aa481094c020"
}' http://localhost:8095/user/newUser

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{
  "id":"5c7ad36f56c9ff0d78213eff",
  "surname": "Fiorentini",
  "name": "Daphne",
  "birthday": "11/20/2000",
  "gender": "Donna",
  "uid":"3ENghkkewyPdttrz00ImV0F0QpH2",
  "relationship": "Single",
  "email": "FiorentiniDaphne@travelmate.com",
  "description":"Esistono tre modi per non apprezzare l’Arte. Il primo consiste nel non apprezzarla. Il secondo nell’apprezzarla con razionalità. Il terzo è apprezzarla con me",
  "avatar":"https://firebasestorage.googleapis.com/v0/b/travelmate-ee239.appspot.com/o/userImage%2FFiorentiniDaphne%40travelmate.com%2Fhqdefault.jpg?alt=media&token=ffba2255-2d3b-4272-a0bb-3345e997dfb6",
  "cover":"https://firebasestorage.googleapis.com/v0/b/travelmate-ee239.appspot.com/o/userImage%2FFiorentiniDaphne%40travelmate.com%2FSeptember-Oktoberfest-Munich-Germany-2.jpg?alt=media&token=55cccedb-3a81-404d-bd04-634e9a47dc24"
}' http://localhost:8095/user/newUser

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{
  "id":"5c7ad36f56c9ff0d78213f00",
  "surname": "Romani",
  "name": "Diego",
  "birthday": "06/08/1993",
  "gender": "Uomo",
  "uid":"sSiOgobodkOlLkyzbIqnqxLQQq93",
  "relationship": "Single",
  "email": "RomaniDiego@travelmate.com",
  "description":"Adoro tutti gli eventi... Per me l importante è viaggiare in compagnia, il resto non conta.",
  "avatar":"https://firebasestorage.googleapis.com/v0/b/travelmate-ee239.appspot.com/o/userImage%2FRomaniDiego%40travelmate.com%2Fruss_thebarber-classic-short-haircut-for-guys.jpg?alt=media&token=c8bd8537-3200-4aec-82ea-b099883a884d",
  "cover":"https://firebasestorage.googleapis.com/v0/b/travelmate-ee239.appspot.com/o/userImage%2FRomaniDiego%40travelmate.com%2Fpexels-photo-414105.jpeg?alt=media&token=17894b5e-6338-4842-9b86-a0fa22942eb1"
}' http://localhost:8095/user/newUser

echo -e ""

curl -H "Content-Type: application/json" -X POST -d '{
  "id":"5c7ad36f56c9ff0d78213f01",
  "surname": "Siciliani",
  "name": "Frediana",
  "uid":"pfcytk2YLdb7qQ2a224J420vZB03",
  "birthday": "09/28/1994",
  "gender": "Donna",
  "relationship": "Fidanzata",
  "email": "SicilianiFrediana@travelmate.com",
  "description":"Amo i robot e la musica rock, sono socievole e mi adatto facilmente a tutto",
  "avatar":"https://firebasestorage.googleapis.com/v0/b/travelmate-ee239.appspot.com/o/userImage%2FSicilianiFrediana%40travelmate.com%2Fgirls-images_292560625.jpg?alt=media&token=3bbc5ab1-7dc7-44c9-859c-44bba38d2fe2",
  "cover":"https://firebasestorage.googleapis.com/v0/b/travelmate-ee239.appspot.com/o/userImage%2FSicilianiFrediana%40travelmate.com%2FWhat-Makes-Xsaga-Different.jpg?alt=media&token=b3826e17-1c81-48b4-aa3f-74a48dcd0125"
}' http://localhost:8095/user/newUser

echo -e " ${reset}"

echo -e "Script end"





