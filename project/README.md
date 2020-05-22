# DBS PROJECT

First you need to setup the environment, for this you need [pipenv](https://pipenv.pypa.io/en/latest/).
After this you can set up the project:

```
pipenv install
pipenv shell
```


## Import Data

Simply run `python import.py` in the set up environment.


## Present Slides

To present the slides directly in jupyter run:

```
jupyter nbconvert data-mining.ipynb --to slides --no-prompt --TagRemovePreprocessor.remove_input_tags={\"to_remove\"} --post serve
```
