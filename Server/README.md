## How to testing Server and Database :computer:

#### Software Used
- [X] [Docker](https://docs.docker.com/) ([Installation](https://docs.docker.com/install/linux/docker-ce/ubuntu/))
- [X] [Docker-Compose](https://docs.docker.com/compose/) ([Installation](https://docs.docker.com/compose/install/))
- [X] [Npm](https://www.npmjs.com/)  ([Installation](https://www.npmjs.com/get-npm))
- [X] [Node](https://nodejs.org/en/about/) ([Installation](https://www.npmjs.com/get-npm))

***

#### Debug Mode

1. Go to Server/
2. ``` sudo docker-compose -f docker-compose-database.yml up -d ```
3. Go to Server/Node/
4. ``` npm start ```
5. now you can test all api implemented (use http://localhost:8095 or go to Server/ and use ``` ./test_api.sh ```)
6. When you have finished, use ```sudo docker-compose -f docker-compose-database.yml stop``` to stop all containers or use ```sudo docker-compose -f docker-compose-database.yml down``` to stop and remove containers

#### Production Mode

1. Go to Server/
2. ``` sudo docker-compose up -d ```
3. now you can test all api implemented (use http://localhost:8095 or go to Server/ and use ``` ./test_api.sh ```)
4. When you have finished, use ```sudo docker-compose stop``` to stop all containers or use ```sudo docker-compose down``` to stop and remove containers

***

#### Api Documentation

##### General
- Test that components work

  <table style="width:100%">
  <tr>
    <td>app_router.js</td>
    <td><code>http://localhost:8095/</code></td> 
  </tr>
  <tr>
    <td>trip_api.js</td>
    <td><code>http://localhost:8095/trip/</code></td> 
  </tr>
   <tr>
    <td>user_api.js</td>
     <td><code>http://localhost:8095/user/</code></td> 
  </tr>
</table>

##### User

##### Trip
