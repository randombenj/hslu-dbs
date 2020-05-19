import os

from bson.json_util import dumps
from flask import Flask, send_from_directory, jsonify, current_app
from flask_pymongo import PyMongo


# create the flask object
app = Flask(__name__)

# add mongo url to flask config, so that flask_pymongo can use it to make connection
app.config['MONGO_URI'] = os.environ["DB"]
app.mongo = PyMongo(app)


@app.route("/")
def index():
    return send_from_directory("static", "index.html")


@app.route("/static/<path:path>")
def files(path):
    return send_from_directory("static", path)


@app.route("/api/lethal")
def lethal_accidents():
    total_accident_count = current_app.mongo.db.accidents.count_documents({})
    lethal_accident_count = current_app.mongo.db.accidents.count_documents({
        "$expr": {"$eq": ["$severity.id", "as1"]}
    })
    return dumps({"data": {
        "total": total_accident_count,
        "lethal": {
            "absolute": lethal_accident_count,
            "percentage": lethal_accident_count / total_accident_count
        }
    }})


@app.route("/api/lethal-by-year")
def lethal_accidents_by_year():
    total_accident_by_year = current_app.mongo.db.accidents.aggregate([
        {"$group" : {"_id": "$timestamp.year", "count": {"$sum": 1}}}
    ])
    total_lethal_accident_by_year = current_app.mongo.db.accidents.aggregate([
        {"$match": {"severity.id": {"$eq": "as1"}}},
        {"$group" : {"_id": "$timestamp.year", "count": {"$sum": 1}}}
    ])

    return dumps({"data": [{
        l["_id"]: {
            "absolute": l["count"],
            "percentage": l["count"] / next((y for y in total_accident_by_year if y["_id"] == l["_id"]), 1)["count"]
        }
    } for l in total_lethal_accident_by_year]})



@app.route("/api/accident-involvment")
def accident_involvment():
    total_accident_count = current_app.mongo.db.accidents.count_documents({})
    pedestrian_count = current_app.mongo.db.accidents.count_documents({
        "$expr": {"$eq": ["$involving_pedestrian", True]}
    })
    bicycle_count = current_app.mongo.db.accidents.count_documents({
        "$expr": {"$eq": ["$involving_bicycle", True]}
    })
    motorcycle_count = current_app.mongo.db.accidents.count_documents({
        "$expr": {"$eq": ["$involving_motorcycle", True]}
    })
    car_count = current_app.mongo.db.accidents.count_documents({
        "$and": [
            {"$expr": {"$eq": ["$involving_pedestrian",  False]}},
            {"$expr": {"$eq": ["$involving_bicycle",  False]}},
            {"$expr": {"$eq": ["$involving_motorcycle",  False]}}
        ]
    })

    return dumps({"data": [{
        "pedestrian": {
            "absolute": pedestrian_count,
            "percentage": pedestrian_count / total_accident_count
        },
        "motorcycle": {
            "absolute": motorcycle_count,
            "percentage": motorcycle_count / total_accident_count
        },
        "bicycle": {
            "absolute": bicycle_count,
            "percentage": bicycle_count / total_accident_count
        },
        "car": {
            "absolute": car_count,
            "percentage": car_count / total_accident_count
        },
        "total": total_accident_count,
    }]}), 200


if __name__ == "__main__":
    app.run("0.0.0.0", debug=True)
