# OOP Project
In this project we will analyze info from a given csv files to match the user conditions.
the csv files that we analyze are from an android application called "WiGLE WiFi", the files contains parameters such as location(lat, lon and alt), time(in YYYY-MM-DD HH:mm:ss format), ID(model of the phone usually) and the WiFi AP points which contain the SSID, MAC address, frequency and signal strength.

## Libraries:
Commons CSV by Apache Commons - http://commons.apache.org/proper/commons-csv/download_csv.cgi to read the CSV files.
JAK - https://labs.micromata.de/projects/jak/download.html to write a KML file(to use in Google Earth).

## Instructions:
To create a combined CSV file, use the function XXX with the parameter "Folder path" (for example XXX("C:\CSV\")).
The combined file will be located in the source folder where the original CSV files are located.
To create a KML file, use the function YYY with the parameters "Folder path" and a filter, the function can filter the given CSV file by **ID**, **date** and **location**.
**ID:** to filter all the AP that have been scanned from the phone "ONEPLUS A3003" for example, run the function YYY("C:\CSV\CombCSV.csv", "ID = ONEPLUS A3003").
**Date:** to filter all the AP that have been scanned from 2017-10-27 16:27:03 until 2017-10-27 16:37:03 for example, run the function YYY("C:\CSV\CombCSV.csv", "date = 2017-10-27 16:27:03,date = 2017-10-27 16:37:03").
**location:** to filter all the AP that have been scanned around 32.16876665,34.81320794 within radius of 100 meters for example, run the function YYY("C:\CSV\CombCSV.csv", "location = 32.16876665,34.81320794,100").
