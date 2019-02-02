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
6. (optional) use ```./populate_database_trip.js``` to populate database with trips example
7. (optional) if you want more trip for testing, you can use api ```http://localhost:8095/trip/loadExample``` for 15000 trips!
8. When you have finished, use ```sudo docker-compose -f docker-compose-database.yml stop``` to stop all containers or use ```sudo docker-compose -f docker-compose-database.yml down``` to stop and remove containers


#### Production Mode

1. Go to Server/
2. ``` sudo docker-compose up -d ```
3. now you can test all api implemented (use http://localhost:8095 or go to Server/ and use ``` ./test_api.sh ```)
4. When you have finished, use ```sudo docker-compose stop``` to stop all containers or use ```sudo docker-compose down``` to stop and remove containers

***

#### Api Documentation

This is a simple list, for more information read the [documentation]()

##### General
- Test that components work

  <table style="width:100%">
  <tr>
    <td></td>
    <td>TYPE</td>
    <td>API</td>
  </tr>
  <tr>
    <td>app_router.js</td>
    <td>Get</td>
    <td><code>http://localhost:8095/</code></td> 
  </tr>
  <tr>
    <td>trip_api.js</td>
    <td>Get</td>
    <td><code>http://localhost:8095/trip/</code></td> 
  </tr>
   <tr>
    <td>user_api.js</td>
     <td>Get</td>
     <td><code>http://localhost:8095/user/</code></td> 
  </tr>
  <tr>
    <td>15.000 trips for testing</td>
     <td>Get</td>
     <td><code>http://localhost:8095/trip/loadExample</code></td> 
  </tr>
</table>

##### User

##### Trip

<table style="width:100%">
  <tr>
    <td></td>
    <td>TYPE</td>
    <td>API</td>
  </tr>
  <tr>
    <td>add new trip</td>
    <td>Post</td>
    <td><code>http://localhost:8095/trip/newTrip</code></td> 
  </tr>
  <tr>
    <td>update trip</td>
    <td>Post</td>
    <td><code>http://localhost:8095/trip/updateTrip</code></td> 
  </tr>
  <tr>
    <td>delete trip</td>
    <td>get</td>
    <td><code>http://localhost:8095/trip/deleteTrip</code></td> 
  </tr>
  <tr>
    <tr>
    <td>add partecipant to trip</td>
    <td>Post</td>
    <td><code>http://localhost:8095/trip/addPartecipant</code></td> 
  </tr>
  <tr>
    <td>remove partecipant to trip</td>
    <td>Post</td>
    <td><code>http://localhost:8095/trip/removePartecipant</code></td> 
  </tr>
  <tr>
    <td>get all trips</td>
    <td>Get</td>
    <td><code>http://localhost:8095/trip/allTrips</code></td> 
  </tr>
  <tr>
    <td>get trips with filter</td>
    <td>Get</td>
    <td><code>http://localhost:8095/trip/getTripsWithFilter</code></td> 
  </tr>
  <tr>
    <td>get one trip by id</td>
    <td>Get</td>
    <td><code>http://localhost:8095/trip/getTripById</code></td> 
  </tr>
</table>
