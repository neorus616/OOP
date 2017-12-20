# OOP Project
The main purpose of this project is to develop complex system that allows gathering geographic information,
producing insights from this information and displaying the information in graphic tools.
we will analyze info from a given CSV files to match the user conditions.
the CSV files that we analyze are from an android application called "WiGLE WiFi", the files contains parameters such as location(lat, lon and alt), time(in YYYY-MM-dd HH:mm:ss format), ID(model of the phone usually) and the WiFi AP points which contain the SSID, MAC address, frequency and signal strength.

## Libraries:
Commons CSV by Apache Commons - http://commons.apache.org/proper/commons-csv/download_csv.cgi to read the CSV files. <br>
JAK - https://labs.micromata.de/projects/jak/download.html to write a KML file(to use in Google Earth). <br>

## Instructions:
To create a combined CSV file, use the function ImportCSV.validPath with the parameter "Folder path" (for example ImportCSV.validPath("C:\\CSV\\")).<br>
The combined file will be located in the source folder where the original CSV files are located.<br>

To create a KML file, use the function ImportCombinedCSV.filterCSV with the parameters "Folder path" and a filter, the function can filter the given CSV file by **ID**, **date** and **location**. <br>
:iphone:**ID:** to filter all the AP that have been scanned from the phone "ONEPLUS A3003" for example, run the function ImportCombinedCSV.filterCSV("C:\CSV\CombCSV.csv", "ID = ONEPLUS A3003"). <br>
:clock1130::date:**Date:** to filter all the AP that have been scanned from 2017-10-27 16:27:03 until 2017-10-27 16:37:03 for example, run the function ImportCombinedCSV.filterCSV("C:\CSV\CombCSV.csv", "date = 2017-10-27 16:27:03,date = 2017-10-27 16:37:03"). <br>
:globe_with_meridians:**location:** to filter all the AP that have been scanned around 32.16876665,34.81320794 within radius of 100 meters for example, run the function ImportCombinedCSV.filterCSV("C:\CSV\CombCSV.csv", "location = 32.16876665,34.81320794,100"). <br>
If you get an exception then maybe you run it with incorrect parameters. (:trollface:) <br>
The KML file is located in the source folder where the Combined CSV file, you can run it on [Google Earth website](https://earth.google.com/web/) via Chrome(or their software to see also the timeline). <br>

To use the first alghoritm to find approx location of AP, use the function EstimateLoc.apEstimateLoc with the parameters "Database path", "save path" and an int(how much similar AP points to check), for example EstimateLoc.apEstimateLoc("CSVLoc\\testing\\_comb_all_BM2_.csv","CSVLoc\\testing\\MYAlgo1_BM2_4.csv", 4).<br>
To use the second alghoritm to find approx location of user, use the function EstimateLoc.userEstimateLoc with the parameters "Database path", "user scan without gps", "save path", and an int(how much similar lines of AP points to check), for example EstimateLoc.userEstimateLoc("CSVLoc\\testing\\_comb_all_BM2_.csv","CSVLoc\\testing\\_comb_no-GPS_TS2.csv" ,"CSVLoc\\MYAlgo2_BM2_4.csv", 4).<br>



## :wrench:Assigments:wrench::
- [x] Design and implement the code for Assigment 0
- [x] Write javadoc\Descriptions
- [x] Create Github  and upload our repository for Assigment 1
- [X] Upload CSV and KML samples
- [X] Write README.md
- [X] Add algorithms for Assigment 2 
- [X] Write function to execute the algorithms
- [ ] Rewrite the CSV reader to read for many purposes
