import os
import uuid
import time
from datetime import datetime
from pathlib import Path
import argparse

import praw
import pymongo

import json
import elevation

DATA_PATH = Path(__file__).resolve().parent / "data"


def insert_data(client):
    loader = elevation.ElevationLoader()

    db = client.dbs

    raw_data = json.loads((DATA_PATH / "accidents.json").read_text())
    data_count = len(raw_data["features"])
    processed_count = 0

    for raw_accident in raw_data["features"]:
        raw_coords = raw_accident["geometry"]["coordinates"]
        height = loader.get_height(raw_coords[0], raw_coords[1])
        cords = [raw_coords[0], raw_coords[1], height]
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
                "coordinates": cords
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
    parser = argparse.ArgumentParser()
    parser.add_argument("--drop-db", help="Drop all existing data before inserting new data", action="store_true")
    args = parser.parse_args()

    client = pymongo.MongoClient("mongodb://localhost")

    if args.drop_db:
        client.drop_database('dbs')

    insert_data(client)
