import os
import uuid
import time
from datetime import datetime
from pathlib import Path

import praw
import pymongo

import json


DATA_PATH = Path(__file__).resolve().parent / "data"

def insert_data():
    db = pymongo.MongoClient("mongodb://localhost").dbs

    raw_data = json.loads((DATA_PATH / "accidents.json").read_text())
    data_count = len(raw_data["features"])
    processed_count = 0

    for raw_accident in raw_data["features"]:
        db.accidents.insert({
            "involving_pedestrian": raw_accident["properties"]["AccidentInvolvingPedestrian"].lower() == "true",
            "involving_motorcycle": raw_accident["properties"]["AccidentInvolvingBicycle"].lower() == "true",
            "involving_bicycle": raw_accident["properties"]["AccidentInvolvingMotorcycle"].lower() == "true",
            "type": {
                "id": raw_accident["properties"]["AccidentType"],
                "name": raw_accident["properties"]["AccidentType_de"]
            },
            "location": {
                "canton": raw_accident["properties"]["CantonCode"],
                "coordinates": raw_accident["geometry"]["coordinates"]
            },
            "severity": {
                "id": raw_accident["properties"]["AccidentSeverityCategory"],
                "name": raw_accident["properties"]["AccidentSeverityCategory_de"]
            },
            "road_type": {
                "id": raw_accident["properties"]["RoadType"],
                "name": raw_accident["properties"]["RoadType_de"]
            },
            "timestamp": {
                "year": raw_accident["properties"]["AccidentYear"],
                "month": raw_accident["properties"]["AccidentMonth"],
                "hour": raw_accident["properties"]["AccidentHour"],
                "weekday": raw_accident["properties"]["AccidentWeekDay_en"]
            }
        })

        processed_count = processed_count + 1
        print('{}/{}\r'.format(processed_count, data_count), end="")


if __name__ == "__main__":
    insert_data()
