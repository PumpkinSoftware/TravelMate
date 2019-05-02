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
3. Go to Server/main_server/
4. ``` npm start ```
5. now you can test all api implemented (use http://localhost:8095 or go to Server/ and use ``` ./test_api.sh ```)
6. (optional) use ```./populate_database_trip.js``` to populate database with trips example
7. (optional) use http://localhost:8081 to open mongo express to use the MongoDB GUI
8. When you have finished, use ```sudo docker-compose -f docker-compose-database.yml stop``` to stop all containers or use ```sudo docker-compose -f docker-compose-database.yml down``` to stop and remove containers


#### Production Mode

1. Go to Server/
2. ``` sudo docker-compose up -d ```
3. now you can test all api implemented (use http://localhost:8095 or go to Server/ and use ``` ./test_api.sh ```)
4. When you have finished, use ```sudo docker-compose stop``` to stop all containers or use ```sudo docker-compose down``` to stop and remove containers

***

#### Api Documentation

This is a simple list, for more information read the [documentation](https://github.com/PumpkinSoftware/TravelMate/wiki)

##### General

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
</table>

##### User

<table style="width:100%">
  <tr>
    <td></td>
    <td>TYPE</td>
    <td>API</td>
  </tr>
  <tr>
    <td>add new user</td>
    <td>Post</td>
    <td><code>http://localhost:8095/user/newUser</code></td> 
  </tr>
  <tr>
    <td>user by email</td>
    <td>Get</td>
    <td><code>http://localhost:8095/user/getUserByEmail</code></td> 
  </tr>
  <tr>
    <td>user by uid</td>
    <td>Get</td>
    <td><code>http://localhost:8095/user/getUserByUid</code></td> 
  </tr>
  <tr>
    <td>user by id</td>
    <td>Get</td>
    <td><code>http://localhost:8095/user/getUserById</code></td> 
  </tr>
  <tr>
    <td>update user</td>
    <td>Post</td>
    <td><code>http://localhost:8095/user/updateUser</code></td> 
  </tr>
  <tr>
    <td>users by trip</td>
    <td>Get</td>
    <td><code>http://localhost:8095/user/getUsersByTrip</code></td> 
  </tr>
  <tr>
    <td>add trip to user</td>
    <td>Post</td>
    <td><code>http://localhost:8095/user/addTrip</code></td> 
  </tr>
  <tr>
    <td>remove trip to user</td>
    <td>Post</td>
    <td><code>http://localhost:8095/user/removeTrip</code></td> 
  </tr>
  <tr>
    <td>remove trip by owner</td>
    <td>Post</td>
    <td><code>http://localhost:8095/user/removeTripByOwner</code></td> 
  </tr>
  <tr>
    <td>delete user</td>
    <td>Get</td>
    <td><code>http://localhost:8095/user/deleteUser</code></td> 
  </tr>
  <tr>
    <td>trips by user</td>
    <td>Get</td>
    <td><code>http://localhost:8095/user/getTripsByUser</code></td> 
  </tr>
  <tr>
    <td>trips by user with split</td>
    <td>Get</td>
    <td><code>http://localhost:8095/user/getTripsByUserSplit</code></td> 
  </tr>
  <tr>
    <td>passed trips by user</td>
    <td>Get</td>
    <td><code>http://localhost:8095/user/getPassedTripsByUser</code></td> 
  </tr>
  <tr>
    <td>progress trips by user</td>
    <td>Get</td>
    <td><code>http://localhost:8095/user/getProgressTripsByUser</code></td> 
  </tr>
  <tr>
    <td>change owner and remove user</td>
    <td>Post</td>
    <td><code>http://localhost:8095/user/changeOwnerAndRemoveLast</code></td> 
  </tr>
  <tr>
    <td>add review to user</td>
    <td>Post</td>
    <td><code>http://localhost:8095/user/addReview</code></td> 
  </tr>
  <tr>
    <td>left reviews by user</td>
    <td>Get</td>
    <td><code>http://localhost:8095/user/leftReviews</code></td> 
  </tr>
  <tr>
    <td>number of left reviews by user</td>
    <td>Get</td>
    <td><code>http://localhost:8095/user/leftReviewsNumbers</code></td> 
  </tr>
</table>

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
    <td>last trip created</td>
    <td>Get</td>
    <td><code>http://localhost:8095/trip/lastTripsCreatedWithUser</code></td> 
  </tr>
  <tr>
    <td>trips filter</td>
    <td>Get</td>
    <td><code>http://localhost:8095/trip/getTripsWithFilter</code></td> 
  </tr>
  <tr>
    <td>update trip</td>
    <td>Post</td>
    <td><code>http://localhost:8095/trip/updateTrip</code></td> 
  </tr>
  <tr>
    <td>delete trip</td>
    <td>Get</td>
    <td><code>http://localhost:8095/trip/deleteTrip</code></td> 
  </tr>
  <tr>
    <td>trips with user</td>
    <td>Get</td>
    <td><code>http://localhost:8095/trip/getTripByIdWithUsers</code></td> 
  </tr>
  <tr>
    <td>get one trip by id</td>
    <td>Get</td>
    <td><code>http://localhost:8095/trip/getTripById</code></td> 
  </tr>
</table>
