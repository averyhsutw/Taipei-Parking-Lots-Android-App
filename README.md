# Android App - Taipei Parking Lots

###### 台北市停車場資訊App

### Preliminaries

- Tools:
    - Google Maps Api
    - Firebase Authentication
- A Must-Know before implementation：
    - Add maps_api_key into your local.properties file. Please goto [Maps SDK for Android Quickstart](https://developers.google.com/maps/documentation/android-sdk/start) to create one.


### Features

- Show all the parking lots in Taipei via Google Map interface, which is got from [DATA.GOV.TW/資料集/臺北市停車場資訊](https://data.gov.tw/dataset/128435)
  <img src="https://i.imgur.com/xg79ONQ.png" style= "zoom:70%;"/>


- Tap on a tag, the information window of the parking lot will pop up.
  <img src="https://i.imgur.com/q5gTOL4.png" style= "zoom:70%;"/>


- Register and login, or you can login as anonymous guest.


| <img src="https://i.imgur.com/oI7q8LX.png" style= "zoom:50%;"/>| <img src="https://i.imgur.com/PSvOZJm.png" style= "zoom:50%;"/> | 
| -------- | -------- | 
| Register Page    | Login Page    | 

- Add to favorites: a user who has registered can add some parking lots in their **favorites**.
  (building)